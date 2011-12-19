package de.dd.otti21.jkl.mongoloader.msc.persistence.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity.UpdateDBObject;

/**
 * Album
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class Album extends BasicDBObject {

	private static final long serialVersionUID = -1349791672784567726L;

	private Name title;
	private Name artist;
	private Name genre;
	private Integer year;
	private List<Track> tracks;
	private Long version;
	private ObjectId id;
	private boolean tagged;

	public Album() {
		this(new Name(""),new Name(""), 0, new ArrayList<Track>(),true);
	}

	public Album(Name title, Name artist, Integer year, List<Track> tracks, boolean tagged) {
		super();
		setTitle(title);
		setArtist(artist);
		setYear(year);
		setTracks(tracks);
		setTagged(tagged);
	}

	public Name getTitle() {
		return title;
	}

	public void setTitle(Name title) {
		this.title = title;
		this.put(AlbumKeys.KEY_ALBUM_TITLE.getName(), title);
	}

	public Name getGenre() {
		return genre;
	}

	public void setGenre(Name genre) {
		this.genre = genre;
		putNonNullValue(AlbumKeys.KEY_ALBUM_GENRE, genre);
	}

	public Name getArtist() {
		return artist;
	}

	public void setArtist(Name artist) {
		this.artist = artist;
		this.put(AlbumKeys.KEY_ALBUM_ARTIST.getName(), artist.asString());
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		year=year==0?null:year;
		this.year = year;
		this.put(AlbumKeys.KEY_ALBUM_YEAR.getName(), year);
	}

	public List<?> getTracks() {
		return tracks;
	}

	synchronized public void setTracks(List<Track> tracks) {
		this.tracks = tracks;

		this.put(AlbumKeys.KEY_ALBUM_TRACKS.getName(), tracks.toArray(new Track[tracks.size()]));
	}

	public void addTrack(Track track) {
		this.tracks.add(track);
		setTracks(tracks);
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
		this.put(AlbumKeys.KEY_ALBUM_ID.getName(), id);
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
		this.put(AlbumKeys.KEY_ALBUM_VERSION.getName(), version);
	}

	public DBObject getFindQuery() {
		BasicDBObject query = new BasicDBObject();

		if (this.id != null) {
			query.put(AlbumKeys.KEY_ALBUM_ID.getName(), this.id);
		} else {
			query.put(AlbumKeys.KEY_ALBUM_TITLE.getName(), getTitle().asString());
			query.put(AlbumKeys.KEY_ALBUM_YEAR.getName(), getYear());
			query.put(AlbumKeys.KEY_ALBUM_ARTIST.getName(), getArtist().asString());
			return query;
		}

		if (this.version != null) {
			query.put(AlbumKeys.KEY_ALBUM_VERSION.getName(), this.version);
		}

		return query;
	}

	public UpdateDBObject getUpdate() {

		UpdateDBObject update=new UpdateDBObject();
		update.addSetAttribute(AlbumKeys.KEY_ALBUM_TITLE.getName(), getTitle().asString());

		if(getYear()!=null){
			update.addSetAttribute(AlbumKeys.KEY_ALBUM_YEAR.getName(), getYear());
		} else {
			update.addUnsetAttribute(AlbumKeys.KEY_ALBUM_YEAR.getName());
		}
		if(getGenre()!=null){
			update.addSetAttribute(AlbumKeys.KEY_ALBUM_GENRE.getName(), getGenre().asString());
		} else {
			update.addUnsetAttribute(AlbumKeys.KEY_ALBUM_GENRE.getName());
		}
		update.addSetAttribute(AlbumKeys.KEY_ALBUM_ARTIST.getName(), getArtist().asString());
		update.addIncAttribute(AlbumKeys.KEY_ALBUM_VERSION.getName(), 1);

		for (Track t : tracks) {
			update.pushAddToSetAttribute(AlbumKeys.KEY_ALBUM_TRACKS.getName(), t);
		}

		return update;
	}

	public void addTracks(BasicDBList tracks) {
		for (Object t : tracks) {
			Track tr = new Track();
			tr.setCd(((BasicDBObject) t).getInt(TrackKeys.KEY_TRACK_CD.getName()));
			String nameAsString = ((BasicDBObject) t).getString(TrackKeys.KEY_TRACK_NAME.getName());
			tr.setName(new Name(nameAsString));
			tr.setNumber(((BasicDBObject) t).getInt(TrackKeys.KEY_TRACK_NUMBER.getName()));
			this.tracks.add(tr);
		}
		setTracks(this.tracks);

	}

	public boolean isTagged() {
		return tagged;
	}
	
	public void setTagged(boolean tagged) {
		if(!this.tagged) {
			return; //must not be reset from untagged to tagged
		}
		this.tagged = tagged;
		put(AlbumKeys.KEY_ALBUM_TAGGED.getName(), tagged);
	}
	
	public boolean isTitleSet() {
		return !title.isEmptyOrWhitespace();
	}
	
	public boolean isArtistSet() {
		return !artist.isEmptyOrWhitespace();
	}

	public boolean isYearSet(){
		return year!=null && year>0;
	}

	public boolean isVersionSet(){
		return version!=null;
	}

	public boolean isObjectIdSet(){
		return id!=null;
	}

	public boolean isTracksSet(){
		return tracks!=null;
	}

	private void putNonNullValue(AlbumKeys keyAlbumGenre,Name value) {
		if(value==null) {
			this.remove(keyAlbumGenre.getName());
		} else {
			this.put(keyAlbumGenre.getName(), value.asString());
		}

	}
	
}
