package de.dd.otti21.jkl.mongoloader.msc.persistence.model;

import com.mongodb.BasicDBObject;

/**
 * Track
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class Track extends BasicDBObject {

	private static final long serialVersionUID = 4554597550648962778L;

	private Name name;
	private String location,bitRateInKBPS,encodingType,channels,format;
	private Integer cd,number,lengthInSeconds,sampleRateInHz;
	private Boolean VBR;
	
	public Track() {
		this(new Name(""),"",null,null);
	}

	public Track(Name name, String location, Integer cd,Integer number) {
		setLocation(location);
		setName(name);
		setCd(cd);
		setNumber(number);
	}

	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
		this.put(TrackKeys.KEY_TRACK_LOCATION.getName(), location);
	}
	
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
		this.put(TrackKeys.KEY_TRACK_NAME.getName(), name);
	}

	public Integer getCd() {
		return cd;
	}

	public void setCd(Integer cd) {
		this.cd = cd;

		putNonNullValue(TrackKeys.KEY_TRACK_CD, cd);
	}

	public Integer getNumber() {
		return number;
	}
	
	public void setNumber(Integer number) {
		this.number=number;
		
		putNonNullValue(TrackKeys.KEY_TRACK_NUMBER, number);
	}

	public boolean isLocationSet() {
		return location!=null && location.trim().length()>0;
	}
	
	public boolean isNameSet() {
		return !name.isEmptyOrWhitespace();
	}
	
	public boolean isNumberSet() {
		return number!=null;
	}

	public String getBitRateInKBPS() {
		return bitRateInKBPS;
	}

	public void setBitRateInKBPS(String bitRateInKBPS) {
		this.bitRateInKBPS = bitRateInKBPS;
		putNonNullValue(TrackKeys.KEY_TRACK_BITRATE, bitRateInKBPS);
	}

	public String getEncodingType() {
		return encodingType;
	}

	public void setEncodingType(String encodingType) {
		this.encodingType = encodingType;
		putNonNullValue(TrackKeys.KEY_TRACK_ENCODINGTYPE, encodingType);
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
		putNonNullValue(TrackKeys.KEY_TRACK_CHANNELS, channels);
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
		putNonNullValue(TrackKeys.KEY_TRACK_FORMAT, format);
	}

	public Integer getLengthInSeconds() {
		return lengthInSeconds;
	}

	public void setLengthInSeconds(Integer lengthInSeconds) {
		this.lengthInSeconds = lengthInSeconds;
		putNonNullValue(TrackKeys.KEY_TRACK_LENGTH, lengthInSeconds);
	}

	public Integer getSampleRateInHz() {
		return sampleRateInHz;
	}

	public void setSampleRateInHz(Integer sampleRateInHz) {
		this.sampleRateInHz = sampleRateInHz;
		putNonNullValue(TrackKeys.KEY_TRACK_SAMPLERATE, sampleRateInHz);
	}

	public Boolean isVBR() {
		return VBR;
	}

	public void setVBR(Boolean VBR) {
		this.VBR = VBR;
		putNonNullValue(TrackKeys.KEY_TRACK_VBR, VBR);
	}

	private void putNonNullValue(TrackKeys key,Object value) {
		if(value==null) {
			this.remove(key.getName());
		} else {
			this.put(key.getName(), value);
		}

	}
	
}
