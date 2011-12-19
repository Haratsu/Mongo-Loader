package de.dd.otti21.jkl.mongoloader.msc.test;

import java.net.UnknownHostException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mongodb.MongoException;

import de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity.EmptyResultException;
import de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity.MongoMax;

/**
 * MaxTest
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class MaxTest {

	@Test
	public void max() throws UnknownHostException, MongoException, EmptyResultException {
		MongoMax<Integer> m=new MongoMax<Integer>("identifier", "genre");
		Integer i = m.getMax();
		Assert.assertEquals(i.intValue(), 147);
	}
}
