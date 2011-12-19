package de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import de.dd.otti21.jkl.mongoloader.msc.persistence.model.Album;
import de.dd.otti21.jkl.mongoloader.msc.persistence.model.AlbumKeys;

/**
 * AlbumManager
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class AlbumManager extends MongoConnection {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private DBCollection songs;

	public AlbumManager() throws UnknownHostException, MongoException {
		super(ConnectionConstants.SERVER_NAME, ConnectionConstants.PORT, ConnectionConstants.DATABASE_NAME);
		songs = getCollection(ConnectionConstants.ALBUM_COLLECTION_NAME);
	}

	public void mergeAlbum(Album album) {
		try {
			DBObject persistedAlbum = songs.findOne(album.getFindQuery());
			if (persistedAlbum != null) {
				UpdateDBObject updateQuery = album.getUpdate();
				boolean updateTaggedValue = ValueParserUtil.getNotNullBoolean(persistedAlbum,AlbumKeys.KEY_ALBUM_TAGGED.getName());
				if(updateTaggedValue) {
					updateQuery.addSetAttribute(AlbumKeys.KEY_ALBUM_TAGGED.getName(), album.isTagged());
				}
				log.debug(updateQuery.toString());

				songs.findAndModify(persistedAlbum, updateQuery.getUpdateQuery());
			} else {
				album.setVersion(0L);
				songs.save(album);
			}
		} catch (MongoException e) {
			log.warn("DB connction lost. changes won't be persisted",e);
		}
	}

}
