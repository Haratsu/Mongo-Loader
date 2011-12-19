package de.dd.otti21.jkl.mongoloader.msc.mapreduce;

import java.net.UnknownHostException;

import com.mongodb.MongoException;

import de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity.ConnectionConstants;

/**
 * SongsBasedMROperation
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public abstract class SongsBasedMROperation extends MapReduceOperation {

	public SongsBasedMROperation() throws UnknownHostException, MongoException {
		super();
	}

	@Override
	protected String getDatabaseName() {
		return ConnectionConstants.DATABASE_NAME;
	}

	@Override
	protected int getPort() {
		return ConnectionConstants.PORT;
	}

	@Override
	protected String getHostName() {
		return ConnectionConstants.SERVER_NAME;
	}

	@Override
	protected String getSourceCollectionName() {
		return ConnectionConstants.ALBUM_COLLECTION_NAME;
	}

}