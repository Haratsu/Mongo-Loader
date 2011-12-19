package de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

/**
 * MongoConnection
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class MongoConnection {

	private Mongo conn = null;
	private DB db;

	public MongoConnection(String serverName, int port, String databaseName) throws UnknownHostException, MongoException {
		conn = new Mongo(serverName, port);
		WriteConcern w = new WriteConcern(1, 200, true);
		db = conn.getDB(databaseName);
		db.setWriteConcern(w);
	}

	public void closeConnection() {
		if (conn != null) {
			conn.close();
			conn = null;
		}
	
	}

	public DBCollection getCollection(String name) {
		return db.getCollection(name);
	}

}