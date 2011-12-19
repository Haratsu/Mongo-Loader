package de.dd.otti21.jkl.mongoloader.msc.mapreduce;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * UsedGenres
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class UsedGenres extends SongsBasedMROperation{

	private final String mapFunction = 
			"function () {" + 
					"emit(this.genre, 1);" + 
			"}";
	private final String reduceFunction =  "function (key, emits) {" + 
																		"	total = Array.sum(emits);" + 
																		"	return total;}";

	public UsedGenres() throws UnknownHostException, MongoException {
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
		return null; //new BasicDBObject("genre",new BasicDBObject("$exists",1));
	}

	@Override
	protected String getMapReduceCollectionName() {
		return "mrUsedGenre";
	}

	@Override
	@WebMethod
	public void run() {
		super.run();
	}
	
	@WebMethod
	public  List<String> getResultAsList() {
		DBCollection mrResult = super.getResult();
	
		DBCursor c= mrResult.find();
		
		List<String> genres=new ArrayList<String>();
		while(c.hasNext()) {
			genres.add(c.next().toString());
		}
		
		return genres;
	}
}
