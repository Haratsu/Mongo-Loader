package de.dd.otti21.jkl.mongoloader.msc.test;

import java.util.Iterator;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.dd.otti21.jkl.mongoloader.msc.PathInformationParserHagen;
import de.dd.otti21.jkl.mongoloader.msc.persistence.InvalidValueException;

/**
 * PatternTester
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class PatternTester implements PatternTest {
	@Override
	@Test
	public void yearPatternTest() {
		PathInformationParserHagen pip = new PathInformationParserHagen("f:\\Zeug\\Musik\\Rock\\Gorillaz\\Gorillaz   Enhanced\\11. 19-2000.mp3", false);
		
		int year = 0;
		try {
			year = pip.getYearFromPath();
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(2000, year);
	}
	
	@Override
	@Test
	public void multiPatternTest() {
		PathInformationParserHagen pip = new PathInformationParserHagen("f:\\Zeug\\Musik\\# Punk Rock\\Ärzte\\Mix\\Die Ärzte - Dauerwelle VS. Minipli.mp3", false);
		Assert.assertEquals("Die Ärzte - Dauerwelle VS. Minipli", pip.getTrackNameFromPath());
		Assert.assertEquals("Ärzte", pip.getArtistFromPath());
		try {
			pip.getYearFromPath();
			Assert.assertFalse(true,"getYearFromPath() should have thrown exception");
		} catch (InvalidValueException e) {
			// expected route
		}

		try {
			pip.getTrackNumberFromPath();
			Assert.assertFalse(true,"getTrackNumberFromPath() should have thrown exception");
		} catch (InvalidValueException e) {
			// expected route
		}
		
		Assert.assertEquals("Mix", pip.getAlbumTitleFromPath());
		
		pip=new PathInformationParserHagen("f:\\Zeug\\Musik\\# Rock\\Yardbirds\\1964 Five Live Yardbirds\\03-Smokestack Lightning.mp3", false);
		Assert.assertEquals("Yardbirds", pip.getArtistFromPath());
		try {
			Assert.assertEquals(Integer.valueOf(3), pip.getTrackNumberFromPath());
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
		Assert.assertEquals("1964 Five Live Yardbirds", pip.getAlbumTitleFromPath());
		
	}
	
	@Override
	@Test
	public void genrePatterntest() {
		Assert.assertTrue("(12)".matches("\\(\\d+\\)"));
	}
	
	@Test
	public void printEnvironment() {
		Map<String, String> environment = System.getenv();
		
		for (Iterator<String> iterator = environment.keySet().iterator(); iterator.hasNext();) {
			String key =  iterator.next();
			System.out.println(key+":\t"+environment.get(key));
		}
	}
}
