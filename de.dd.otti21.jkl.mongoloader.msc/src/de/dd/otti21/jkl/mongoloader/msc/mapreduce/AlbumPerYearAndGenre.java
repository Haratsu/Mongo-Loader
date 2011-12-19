package de.dd.otti21.jkl.mongoloader.msc.mapreduce;

import java.net.UnknownHostException;

import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * AlbumPerYearAndGenre
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class AlbumPerYearAndGenre extends SongsBasedMROperation{

	private final String mapFunction = 
			"function () {" + 
					"genre = this.genre;" +
					"var key = this.year +'-' + genre;" +
					"emit(key, 1);" + 
			"}";
	private final String reduceFunction = "function (key, emits) {" + "	total = Array.sum(emits);"  + "	return total;}";

	public AlbumPerYearAndGenre() throws UnknownHostException, MongoException {
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
		return "mrAlbumPerYearAndGenre";
	}

}
