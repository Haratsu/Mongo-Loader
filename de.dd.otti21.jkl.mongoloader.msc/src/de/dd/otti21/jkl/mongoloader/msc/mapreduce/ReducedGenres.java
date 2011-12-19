package de.dd.otti21.jkl.mongoloader.msc.mapreduce;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity.ConnectionConstants;

/**
 * ReducedGenres
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class ReducedGenres extends MapReduceOperation{

	private final String mapFunction = 
			"function () {" + 
			"  var genre = String(this._id);" +
			"  genre = genre.replace(/^\\s+|\\s+$/g, \"\");" + // trims whitespaces
			"  if (genre.charAt(0) === \"(\") {" +
			"    var closingParenthesesIndex = genre.indexOf(\")\");" +
			"    var lastLetterIndex = genre.length - 1;" +
			"    if (lastLetterIndex === closingParenthesesIndex) {" +  //genre is enclosed in parentheses
			"      var genreid = parseInt(genre.substring(0));" +
			"      if (isNaN(genreid)) {" +
			"         genre = genre.substring(1, lastLetterIndex);" +  // parses content between parentheses as genre
			"      } else {" +          // genre is only given by id3 genre identifier
			"        var genrename = db.genre.findOne({'identifier':genreid});" +
			"        if (genrename !== null) {" +
			"          genre = genrename.name;" +
			"        }" +  
			"      }" +
			"    } else {" +  // genre contains of some value in parentheses and some text
			"      genre = genre.substring(closingParenthesesIndex + 1);" +
			"    }" +
			"  } else if (genre.match(/^\\d.*/)) {" +  // match numeric genres to their idv3 identifier 
			"    var genreid = parseInt(genre);" +
			"    genrename = db.genre.findOne({'identifier':genreid});" +
			"    if (genrename !== null) {" +
			"      genre = genrename.name;" +
			"    }" +
			"  }" +
			"  genre = genre.replace(/^\\s+|\\s+$/g, \"\");" +  // trims whitespaces

							// matching several ways to write a genre using collection "genremappings" which contains a document of the following layout
							// genremapping = {
							//									"genre": "Blues",
							//									"matches": ["blues", "BLUES", "BlUeS"]
							//								}
							// be sure to call db.genremappings.ensureIndex({matches:1})
							//
							// example for commandline call to insert a mapping for genre Rock:
							// db.genremappings.save({"genre":"Rock","matches":["Rock","rock","ROCK"]})
						
			"  genrename = db.genremappings.findOne({'matches':genre},{genre:1});" +
			"  if (genrename !== null) {" +
			"    genre = genrename.genre;" +
			"  }" +
		
			"  emit(genre, this.value); " +
			"}";
	private final String reduceFunction =  "function (key, emits) {" + 
																		"	total = Array.sum(emits);" + 
																		"	return total;}";

	public ReducedGenres() throws UnknownHostException, MongoException {
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
		return "mrReducedGenre";
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
	
	@Override
	protected String getDatabaseName() {
		return ConnectionConstants.DATABASE_NAME;
	}
	
	protected String getHostName() {
		return ConnectionConstants.SERVER_NAME;
	}
	
	@Override
	protected int getPort() {
		return ConnectionConstants.PORT;
	}
	
	@Override
	protected String getSourceCollectionName() {
		return "mrUsedGenre";
	}
	
	
}
