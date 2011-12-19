package de.dd.otti21.jkl.mongoloader.msc;

import de.dd.otti21.jkl.mongoloader.msc.persistence.InvalidValueException;

/**
 * PathInformationParser
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public interface PathInformationParser {

	public abstract String getAlbumTitleFromPath();

	public abstract String getArtistFromPath();

	public abstract Integer getYearFromPath() throws InvalidValueException;

	public abstract String getTrackNameFromPath();

	public abstract Integer getTrackNumberFromPath() throws InvalidValueException;

}