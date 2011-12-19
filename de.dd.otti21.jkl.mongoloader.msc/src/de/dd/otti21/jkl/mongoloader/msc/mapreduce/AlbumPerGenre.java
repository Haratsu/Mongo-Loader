package de.dd.otti21.jkl.mongoloader.msc.mapreduce;

import java.net.UnknownHostException;

import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * AlbumPerGenre
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class AlbumPerGenre extends SongsBasedMROperation{

	private final String mapFunction = 
			"function () {" + 
					"emit(this.genre, 1);" + 
			"}";
	private final String reduceFunction =  "function (key, emits) {" + 
																		"	total = Array.sum(emits);" + 
																		"	return total;}";

	public AlbumPerGenre() throws UnknownHostException, MongoException {
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
		return "mrAlbumPerGenre";
	}

}
