package de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

/**
 * MongoMax
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class MongoMax<T> extends MongoConnection {

	private String key;
	private String collectionName;

	public MongoMax(String key, String collectionName) throws UnknownHostException, MongoException {
		super(ConnectionConstants.SERVER_NAME,ConnectionConstants.PORT,ConnectionConstants.DATABASE_NAME);
		this.key = key;
		this.collectionName = collectionName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	@SuppressWarnings("unchecked")
	public T getMax() throws EmptyResultException {
		DBCollection col=getCollection(collectionName);
		DBCursor cur =col.find(new BasicDBObject(),new BasicDBObject(key,1)).sort(new BasicDBObject(key,-1));
		if(cur.hasNext()) {
			DBObject result=cur.next();
			return (T) result.get(key);
		}
		throw new EmptyResultException();
	}
}
