package de.dd.otti21.jkl.mongoloader.msc.persistence.model;

/**
 * AlbumWithTrack
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class AlbumWithTrack {
	private Album album;
	private Track track;

	public AlbumWithTrack() {
		this(new Album(),new Track());
	}
	
	public AlbumWithTrack(Album album, Track track) {
		this.album = album;
		this.track = track;
	}
	
	public Track getTrack() {
		return track;
	}
	
	public void setTrack(Track track) {
		this.track = track;
	}
	
	public Album getAlbum() {
		return album;
	}
	
	public void setAlbum(Album album) {
		this.album = album;
	}
}
