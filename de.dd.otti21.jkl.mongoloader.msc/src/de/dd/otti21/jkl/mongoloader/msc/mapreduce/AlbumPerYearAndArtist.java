package de.dd.otti21.jkl.mongoloader.msc.mapreduce;

import java.net.UnknownHostException;

import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * AlbumPerYearAndArtist
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class AlbumPerYearAndArtist extends SongsBasedMROperation{

	
	private final String mapFunction = 
			"function () {" + 
					"var artist = this.artist;" +
					"artist = artist.replace(/^\\s+|\\s+$/g, \"\");" + // trims whitespaces
					"var mappedArtist = db.artistMappings.findOne({'matches':artist});" +
					"if (mappedArtist !== null) {" +
					"  artist = mappedArtist.artist;" +
					"}" +
					"var key = this.year +'-' + artist;" +
					"emit(key, 1);" + 
			"}";
	// document of artistMappings should be of the following layout:
	// 		doc = { artist: "Simon & Garfunkle", matches: ["Simon&Garfunkel","Simon And Garfunkel","Simon and Garfunkel"]}
	// please ensure index on artistmappings.matches
	
	private final String reduceFunction = "function (key, emits) {" + "	total = Array.sum(emits);"  + "	return total;}";

	public AlbumPerYearAndArtist() throws UnknownHostException, MongoException {
		super();
	}

	@Override
	protected String getMapFunction() {
		return mapFunction;
	}

	@Override
	protected String getReduceFunction() {
		return reduceFunction;
	}

	@Override
	protected DBObject getSearchQuery() {
		return null;
	}

	@Override
	protected String getMapReduceCollectionName() {
		return "mrAlbumPerYearAndArtist";
	}

}
