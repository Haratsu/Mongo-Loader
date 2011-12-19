package de.dd.otti21.jkl.mongoloader.msc;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.dd.otti21.jkl.mongoloader.msc.persistence.InvalidValueException;

/**
 * PathInformationParserHagen
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class PathInformationParserHagen implements PathInformationParser {
	private static final String UNKNOWN_ARTIST_NAME = "Unknown Artist";
	private static final String UNKNOWN_ALBUM_TITLE = "Unknown Album";

	private static final Pattern yearPattern = Pattern.compile("\\d{4}");
	private static final Pattern simpleTrackNumberPattern = Pattern.compile("^\\d{2,3}");
	private static final Pattern extendedTrackNumberPattern = Pattern.compile("^track\\s*\\d{2,3}",
			Pattern.CASE_INSENSITIVE);

	private String path;
	private String filename;
	private int albumTitleEndIndex = 0;

	public PathInformationParserHagen(String path, boolean cd) {
		this.path = path;

		filename = path.substring(path.lastIndexOf(File.separator) + 1, path.lastIndexOf("."));

		if (cd) {
			albumTitleEndIndex = path.lastIndexOf(File.separator); // index of file separator after "CD X"
			albumTitleEndIndex = path.lastIndexOf(File.separator, albumTitleEndIndex - 1);
		} else {
			albumTitleEndIndex = path.lastIndexOf(File.separator);
		}
	}

	@Override
	public Integer getTrackNumberFromPath() throws InvalidValueException {
		Matcher trackMatcher = simpleTrackNumberPattern.matcher(filename);
		if (trackMatcher.find()) {
			String trackNumberAsString = null;
			try {
				trackNumberAsString=trackMatcher.group();
				return Integer.valueOf(trackNumberAsString);
			} catch (NumberFormatException e) {
				throw new InvalidValueException(trackNumberAsString+" is not a number.");
			}
		} else {
			trackMatcher = extendedTrackNumberPattern.matcher(filename);
			if (trackMatcher.find()) {
				String trackNumberAsString = null;
				try {
					trackNumberAsString=trackMatcher.group().substring(5).trim();
					return Integer.valueOf(trackNumberAsString);
				} catch (NumberFormatException e) {
					throw new InvalidValueException(trackNumberAsString+" is not a number.");
				}
			}
		}
		throw new InvalidValueException("No Track number could be parsed");
	}

	@Override
	public String getTrackNameFromPath() {
		String name = filename.replaceAll("\\d{4}", "");

		name = name.replaceAll("^\\d{1,2}", "");

		return name;
	}

	@Override
	public Integer getYearFromPath() throws InvalidValueException {
		Matcher yearMatcher = yearPattern.matcher(path);
		if (yearMatcher.find()) {
			int startIndex = yearMatcher.start();
			String yearAsString = null;
			try {
				int endIndex = startIndex + 4;
				endIndex = endIndex > path.length() ? path.length() : endIndex;
				if (startIndex < 0 || endIndex < 1 || startIndex >= endIndex) {
					throw new InvalidValueException("year could not be parsed.");
				}
				yearAsString = path.substring(startIndex, endIndex);
				return Integer.valueOf(yearAsString);
			} catch (NumberFormatException e) {
				throw new InvalidValueException(yearAsString + "is not a number.");
			}
		}
		throw new InvalidValueException("year was not found.");

	}

	@Override
	public String getArtistFromPath() {
		int endIndex = 0;

		endIndex = path.lastIndexOf(File.separator, albumTitleEndIndex - 1);
		int startIndex = path.lastIndexOf(File.separator, endIndex - 1);

		endIndex = endIndex > path.length() ? path.length() : endIndex;

		if (startIndex < 0 || endIndex < 1 || startIndex >= endIndex) {
			return UNKNOWN_ARTIST_NAME;
		}

		return path.substring(startIndex + 1, endIndex);

	}

	@Override
	public String getAlbumTitleFromPath() {
		int startIndex = path.lastIndexOf(File.separator, albumTitleEndIndex - 1);
		int endIndex = albumTitleEndIndex > path.length() ? path.length() : albumTitleEndIndex;
		if (startIndex < 0 || endIndex < 1 || startIndex >= endIndex) {
			return UNKNOWN_ALBUM_TITLE;
		}
		return path.substring(startIndex + 1, endIndex);
	}

}
