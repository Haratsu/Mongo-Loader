package de.dd.otti21.jkl.mongoloader.msc.persistence.model;

import de.dd.otti21.jkl.mongoloader.msc.persistence.InvalidKeyNameException;

/**
 * AlbumKeys
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public enum AlbumKeys {
	
	KEY_ALBUM_ID("_id",0),KEY_ALBUM_TITLE("title",1),KEY_ALBUM_ARTIST("artist",2),KEY_ALBUM_YEAR("year",3),KEY_ALBUM_TRACKS("tracks",4), KEY_ALBUM_VERSION("version",5), KEY_ALBUM_GENRE("genre",6), KEY_ALBUM_TAGGED("tagged",7);
	
	private String name;
	private int id;
	
	private AlbumKeys(String name, int id) {
		this.name=name;
		this.id=id;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public AlbumKeys getByName(String name) throws InvalidKeyNameException {
		for(AlbumKeys key:values()){
			if(key.getName().equalsIgnoreCase(name)) {
				return key;
			}
		}
		throw new InvalidKeyNameException(name+" is no valid key in enum AlbumKeys");
	}
}