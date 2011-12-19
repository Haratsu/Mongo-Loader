package de.dd.otti21.jkl.mongoloader.msc.mapreduce;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceCommand.OutputType;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoException;

import de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity.MongoConnection;

/**
 * MapReduceOperation
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public abstract class MapReduceOperation {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private DBCollection mapReduceSourceCollection;
	private MongoConnection connection;

	public MapReduceOperation() throws UnknownHostException, MongoException {
		connection = new MongoConnection(getHostName(), getPort(), getDatabaseName());

		mapReduceSourceCollection = connection.getCollection(getSourceCollectionName());
	}

	public void run() {
		MapReduceCommand command = new MapReduceCommand(mapReduceSourceCollection, getMapFunction(),
				getReduceFunction(), getMapReduceCollectionName(), OutputType.REPLACE, getSearchQuery());

		MapReduceOutput out = mapReduceSourceCollection.mapReduce(command);

		log.info(out.toString());
	}

	public DBCollection getResult() {
		return connection.getCollection(getMapReduceCollectionName());
	}

	protected abstract String getDatabaseName();

	protected abstract int getPort();

	protected abstract String getHostName();

	protected abstract String getMapFunction();

	protected abstract String getReduceFunction();

	protected abstract DBObject getSearchQuery();

	protected abstract String getMapReduceCollectionName();

	protected abstract String getSourceCollectionName();
}
