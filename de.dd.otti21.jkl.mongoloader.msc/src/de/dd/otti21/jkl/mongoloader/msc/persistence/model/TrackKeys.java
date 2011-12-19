package de.dd.otti21.jkl.mongoloader.msc.persistence.model;

import de.dd.otti21.jkl.mongoloader.msc.persistence.InvalidKeyNameException;

/**
 * TrackKeys
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public enum TrackKeys {

	KEY_TRACK_NAME("name", 1), KEY_TRACK_CD("cd", 2), KEY_TRACK_NUMBER("number", 3), KEY_TRACK_LOCATION("location", 4), KEY_TRACK_BITRATE(
			"bitrate", 5), KEY_TRACK_ENCODINGTYPE("encodingtype", 6), KEY_TRACK_CHANNELS("channels", 7), KEY_TRACK_FORMAT(
			"format", 8), KEY_TRACK_LENGTH("length", 9), KEY_TRACK_SAMPLERATE("samplerate",10), KEY_TRACK_VBR("vbr",11);

	private String name;
	private int id;

	private TrackKeys(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public TrackKeys getByName(String name) throws InvalidKeyNameException {
		for (TrackKeys key : values()) {
			if (key.getName().equalsIgnoreCase(name)) {
				return key;
			}
		}
		throw new InvalidKeyNameException(name + " is no valid key in enum TrackKeys");
	}
}