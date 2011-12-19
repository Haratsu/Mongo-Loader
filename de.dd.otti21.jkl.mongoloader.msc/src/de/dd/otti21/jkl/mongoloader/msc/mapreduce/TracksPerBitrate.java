package de.dd.otti21.jkl.mongoloader.msc.mapreduce;

import java.net.UnknownHostException;

import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * TracksPerBitrate
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class TracksPerBitrate extends SongsBasedMROperation{

	private final String mapFunction = 
			"function () {" + 
					"this.tracks.forEach(" +
						"function (track) {" +
							"emit(track.bitrate, 1);" +
						"}" +
					")" +
			"}";
	private final String reduceFunction = "function (key, emits) {" + "	total = Array.sum(emits);"+ "	return total}";

	public TracksPerBitrate() throws UnknownHostException, MongoException {
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
		return "mrTracksPerBitrate";
	}

}
