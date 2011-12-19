package de.dd.otti21.jkl.mongoloader.msc.test;

import java.net.UnknownHostException;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;

import de.dd.otti21.jkl.mongoloader.msc.mapreduce.AlbumPerYearAndArtist;
import de.dd.otti21.jkl.mongoloader.msc.mapreduce.MapReduceOperation;
import de.dd.otti21.jkl.mongoloader.msc.mapreduce.SongsBasedMROperation;

/**
 * MapReduceTester
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class MapReduceTester {
	public final String fs = System.getProperty("file.separator");
	public final String resourcesDir = "res";

	private final Logger log = LoggerFactory.getLogger(getClass());

	@BeforeTest
	public void init() {
		DOMConfigurator.configure(resourcesDir + fs + "log4j.xml");
	}

//	@Test
//	public void albumPerYearTest() throws UnknownHostException, MongoException {
//		AlbumPerYear apy = new AlbumPerYear();
//		DBCursor mrResult = getMRCollectionCursor(apy);
//		printMRCursor(mrResult);
//	}
//
//	@Test
//	public void albumPerGenreTest() throws UnknownHostException, MongoException {
//		AlbumPerGenre apg = new AlbumPerGenre();
//		DBCursor mrResult = getMRCollectionCursor(apg);
//		printMRCursor(mrResult);
//	}
//
//	@Test
//	public void albumPerYearAndGenreTest() throws UnknownHostException, MongoException {
//		AlbumPerYearAndGenre apg = new AlbumPerYearAndGenre();
//		DBCursor mrResult = getMRCollectionCursor(apg);
//		printMRCursor(mrResult);
//	}
//
//	@Test
//	public void tracksPerBitrateTest() throws UnknownHostException, MongoException {
//		SongsBasedMROperation tpb = new TracksPerBitrate();
//		DBCursor mrResult = getMRCollectionCursor(tpb);
//		printMRCursor(mrResult);
//	}
//	
//	@Test
//	public void usedGenresTest() throws UnknownHostException, MongoException {
//		SongsBasedMROperation tpb = new UsedGenres();
//		DBCursor mrResult = getMRCollectionCursor(tpb);
//		printMRCursor(mrResult);
//	}
//
//	@Test
//	public void usedGenresTest() throws UnknownHostException, MongoException {
//		ReducedGenres tpb = new ReducedGenres();
//		DBCursor mrResult = getMRCollectionCursor(tpb);
//		printMRCursor(mrResult);
//	}

	@Test
	public void albumPerYearAndArtistTest() throws UnknownHostException, MongoException {
		SongsBasedMROperation tpb = new AlbumPerYearAndArtist();
		DBCursor mrResult = getMRCollectionCursor(tpb);
		printMRCursor(mrResult);
	}

	private DBCursor getMRCollectionCursor(MapReduceOperation operation) {
		operation.run();
		DBCollection result = operation.getResult();
		return result.find();
	}

	private void printMRCursor(DBCursor cursor) {
		while (cursor.hasNext()) {
			KeyWithCount albumPerGenre = new KeyWithCount(cursor.next());
			log.info(albumPerGenre.toString());
		}
	}
}
