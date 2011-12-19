package de.dd.otti21.jkl.mongoloader.msc;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;

import org.apache.log4j.xml.DOMConfigurator;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoException;

import de.dd.otti21.jkl.mongoloader.msc.persistence.InvalidValueException;
import de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity.AlbumManager;
import de.dd.otti21.jkl.mongoloader.msc.persistence.model.Album;
import de.dd.otti21.jkl.mongoloader.msc.persistence.model.AlbumWithTrack;
import de.dd.otti21.jkl.mongoloader.msc.persistence.model.Name;
import de.dd.otti21.jkl.mongoloader.msc.persistence.model.Track;

/**
 * Loader
 * 
 * @author Jens Klobes
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class Loader implements FileTypes {

	private final Logger log = LoggerFactory.getLogger(getClass());

	public final String fs = System.getProperty("file.separator");

	public final String resourcesDir = "res";

	private boolean cd = false;

	private AlbumManager albumManager;

	public static void main(String[] args) {
		java.util.logging.Logger.getLogger("").setLevel(Level.OFF);
		Loader loader = new Loader();
		loader.init();
		loader.walkin(new File("F:\\Zeug\\Musik"));
		loader.stop();
	}

	private void stop() {
		albumManager.closeConnection();
		log.info("Bye");
	}

	private void init() {
		DOMConfigurator.configure(resourcesDir + fs + "log4j.xml");
		try {
			albumManager = new AlbumManager();
		} catch (UnknownHostException e) {
			log.error(e.getMessage());
		} catch (MongoException e) {
			log.error(e.getMessage());
		}
	}

	private void walkin(File dir) {

		File listFile[] = dir.listFiles();
		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				if (listFile[i].isDirectory()) {
					if (listFile[i].getName().matches(".*[cC][dD]\\s*\\d+.*")) {
						log.debug(listFile[i].getAbsolutePath());
						cd = true;
					} else {
						cd = false;
					}

					walkin(listFile[i]);
				} else {
					if (isFileType(listFile[i].getName(), MP3, OGG, FLAC, WMA, MP4, M4A, MPC, APE, WAV, WV)) {
						// if (cd)
						processAudioFile(listFile[i].getPath());
					} else {
						if (!(isFileType(listFile[i].getName(), MPG, MPEG, MOV, WMV, JPG, JPEG, PNG, GIF, BMP, TIF, TXT, HTM, HTML, CSS, JS, NFO, SFV,
								LOG, PDF, INI, DOC, RTF, M3U, DB, CUE, PLS, URL))) {
							// log.error(listFile[i].getPath());
						}
					}
				}
			}
		}
	}

	private boolean isFileType(String path, String... types) {
		for (String type : types) {
			if (path.toLowerCase().endsWith(type)) {
				return true;
			}
		}
		return false;
	}

	private void processAudioFile(String path) {
		File file = new File(path);
		AudioFile audioFile;
		try {
			log.debug("About to process: " + file.getParent());
			log.debug("About to process: " + path);

			AudioFileIO afio = new AudioFileIO();
			audioFile = afio.readFile(file);
			printAudioHeader(audioFile);

			AlbumWithTrack awt = new AlbumWithTrack();
			awt.getTrack().setLocation(path);

			Tag tag = audioFile.getTag();
			if (!isNullOrEmpty(tag)) {
				awt = getTaggedInformation(awt, tag);

				awt = validateTrackAndAlbumInformation(path, awt);
			} else {
				log.warn("File " + path + " misses tagging information");
				awt = getFileSystemTrackInformation(awt, path);
			}

			awt = addAudioHeader(awt, audioFile);

			if (cd) {
				awt = addCDAttribute(awt, path);
			}

			awt.getAlbum().addTrack(awt.getTrack());
			albumManager.mergeAlbum(awt.getAlbum());
		} catch (CannotReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			e.printStackTrace();
		}
	}

	private AlbumWithTrack addCDAttribute(AlbumWithTrack awt, String path) {
		Track track = awt.getTrack();
		String[] pathElements = path.split("[\\\\/]");

		for (String pathElement : pathElements) {
			if (pathElement.matches("[cC][dD]\\s*\\d+")) {
				try {
					Integer cdNumber = extractNumericTagValue(pathElement.substring(2).trim());
					log.debug("matched CD: " + cdNumber);
					track.setCd(cdNumber);
				} catch (InvalidValueException e) {
					log.warn(e.getMessage());
				}
			}
		}
		return new AlbumWithTrack(awt.getAlbum(), track);
	}

	private AlbumWithTrack addAudioHeader(AlbumWithTrack awt, AudioFile audioFile) {
		Album a = awt.getAlbum();
		Track t = awt.getTrack();

		t.setLengthInSeconds(audioFile.getAudioHeader().getTrackLength());
		t.setBitRateInKBPS(audioFile.getAudioHeader().getBitRate());
		t.setSampleRateInHz(audioFile.getAudioHeader().getSampleRateAsNumber());
		t.setEncodingType(audioFile.getAudioHeader().getEncodingType());
		t.setChannels(audioFile.getAudioHeader().getChannels());
		t.setFormat(audioFile.getAudioHeader().getFormat());
		t.setVBR(audioFile.getAudioHeader().isVariableBitRate());

		log.debug("Encoding Type: " + audioFile.getAudioHeader().getEncodingType());
		log.debug("Channels: " + audioFile.getAudioHeader().getChannels());
		log.debug("Format: " + audioFile.getAudioHeader().getFormat());
		log.debug("Variable Bit Rate: " + audioFile.getAudioHeader().isVariableBitRate());

		return new AlbumWithTrack(a, t);
	}

	private AlbumWithTrack validateTrackAndAlbumInformation(String path, AlbumWithTrack awt) {
		Track track = awt.getTrack();
		Album album = awt.getAlbum();

		PathInformationParserHagen pip = new PathInformationParserHagen(path, cd);

		if (!track.isNameSet()) {
			String trackNameFromPath = pip.getTrackNameFromPath();
			track.setName(new Name(trackNameFromPath));
		}

		if (!track.isNumberSet()) {
			try {
				track.setNumber(pip.getTrackNumberFromPath());
			} catch (InvalidValueException e) {
				log.warn("Omitting track number for " + path + " - Reason " + e.getMessage());
			} finally {
				album.setTagged(false);
			}
		}

		if (!album.isTitleSet()) {
			String albumTitleFromPath = pip.getAlbumTitleFromPath();
			album.setTitle(new Name(albumTitleFromPath));
			album.setTagged(false);
		}

		if (!album.isArtistSet()) {
			String artistFromPath = pip.getArtistFromPath();
			album.setArtist(new Name(artistFromPath));
			album.setTagged(false);
		}

		if (!album.isYearSet()) {
			try {
				album.setYear(pip.getYearFromPath());
			} catch (InvalidValueException e) {
				log.warn("Omitting year for " + path + " - Reason " + e.getMessage());
			} finally {
				album.setTagged(false);
			}
		}

		return new AlbumWithTrack(album, track);
	}

	private AlbumWithTrack getFileSystemTrackInformation(AlbumWithTrack awt, String path) {
		Album album = awt.getAlbum();
		Track track = awt.getTrack();

		PathInformationParserHagen pip = new PathInformationParserHagen(path, cd);

		album.setTagged(false);
		String albumTitleFromPath = pip.getAlbumTitleFromPath();
		album.setTitle(new Name(albumTitleFromPath));
		String artistFromPath = pip.getArtistFromPath();
		album.setArtist(new Name(artistFromPath));
		try {
			album.setYear(pip.getYearFromPath());
		} catch (InvalidValueException e) {
			log.warn("Omitting year for " + path + " - Reason " + e.getMessage());
		}
		String trackNameFromPath = pip.getTrackNameFromPath();
		track.setName(new Name(trackNameFromPath));

		try {
			track.setNumber(pip.getTrackNumberFromPath());
		} catch (InvalidValueException e) {
			log.warn("Omitting track number for " + path + " - Reason " + e.getMessage());
		}
		return awt;
	}

	private AlbumWithTrack getTaggedInformation(AlbumWithTrack awt, Tag tag) {
		for (FieldKey key : FieldKey.values()) {
			try {
				if (tagExists(tag, key)) {
					log.debug(key.name() + ": " + tag.getFirst(key));
					switch (key) {
						case ALBUM: {
							Name title = new Name(tag.getFirst(key));
							awt.getAlbum().setTitle(title);
							break;
						}
						case ALBUM_ARTIST: {
							Name artist = new Name(tag.getFirst(key));
							awt.getAlbum().setArtist(artist);
							break;
						}
						case YEAR: {
							try {
								awt.getAlbum().setYear(extractNumericTagValue(tag.getFirst(key)));
							} catch (InvalidValueException e) {
								log.warn(e.getMessage());
							}

							break;
						}
						case TRACK: {
							try {
								awt.getTrack().setNumber(extractNumericTagValue(tag.getFirst(key)));
							} catch (InvalidValueException e) {
								log.warn(e.getMessage());
							}

							break;
						}
						case TITLE: {
							Name title = new Name(tag.getFirst(key));
							awt.getTrack().setName(title);
							break;
						}
						case GENRE: {
							Name genreName = new Name(tag.getFirst(key));
							awt.getAlbum().setGenre(genreName);
						}
					}

				}
			} catch (NullPointerException e) {
				log.error(key.name() + " n.a.");
			} catch (UnsupportedOperationException e) {
				log.error(key.name() + " unspupported here.");
			}
		}

		return awt;
	}

	private Integer extractNumericTagValue(String numberAsString) throws InvalidValueException {
		if (numberAsString == null || numberAsString.trim().length() == 0) {
			throw new InvalidValueException("Empty or null String can not be parsed as number.");
		}

		try {
			return Integer.valueOf(numberAsString);
		} catch (NumberFormatException e) {
			throw new InvalidValueException(numberAsString + " is not a number. Value will be omitted.");
		}
	}

	private boolean isNullOrEmpty(Tag tag) {
		if (tag == null) {
			return true;
		}

		if (tag.getFieldCount() == 0) {
			return true;
		}

		return false;

	}

	private boolean tagExists(Tag tag, FieldKey key) {
		return tag.getFirst(key) != null && tag.getFirst(key).length() > 0 && !tag.getFirst(key).equals("null");
	}

	private void printAudioHeader(AudioFile audioFile) {
		log.debug("Track Length: " + audioFile.getAudioHeader().getTrackLength() + " seconds");
		log.debug("Bit Rate: " + audioFile.getAudioHeader().getBitRate() + " kbps");
		log.debug("Sample Rate: " + audioFile.getAudioHeader().getSampleRateAsNumber() + " Hz");
		log.debug("Encoding Type: " + audioFile.getAudioHeader().getEncodingType());
		log.debug("Channels: " + audioFile.getAudioHeader().getChannels());
		log.debug("Format: " + audioFile.getAudioHeader().getFormat());
		log.debug("Variable Bit Rate: " + audioFile.getAudioHeader().isVariableBitRate());
	}
}
