package de.dd.otti21.jkl.mongoloader.msc.mapreduce;

import java.net.UnknownHostException;

import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * AlbumPerYear
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class AlbumPerYear extends SongsBasedMROperation {

	public AlbumPerYear() throws UnknownHostException, MongoException {
		super();
	}

	private final String mapFunction = "function () {" + "emit(this.year, 1);" + "}";
	private final String reduceFunction = "function (key, emits) {" + "	total = Array.sum(emits);" + "	return total}";

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
		return "mrAlbumPerYear";
	}
}
