package de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity;

import java.io.Serializable;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * UpdateDBObject
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class UpdateDBObject implements Serializable{

	private static final long serialVersionUID = -5114833404798813533L;

	private BasicDBObject sets = new BasicDBObject();
	private BasicDBObject unsets = new BasicDBObject();
	private BasicDBObject addToSets = new BasicDBObject();
	private BasicDBObject incs = new BasicDBObject();

	public void addSetAttribute(String name,Object value) {
		sets.append(name, value);
	}
	
	public void removeSetAttribute(String name) {
		sets.removeField(name);
	}
	
	public void addUnsetAttribute(String name) {
		unsets.append(name, 1);
	}
	
	public void removeUnsetAttribute(String name) {
		unsets.removeField(name);
	}
	
	public void pushAddToSetAttribute(String name,Object value) {
		addToSets.append(name, value);
	}
	
	public void pullAddToSetAttribute(String name) {
		addToSets.removeField(name);
	}
	
	public void addIncAttribute(String name,Object value) {
		incs.append(name, value);
	}

	public void removeIncAttribute(String name) {
		incs.removeField(name);
	}
	
	public DBObject getUpdateQuery() {
		BasicDBObject updates=new BasicDBObject();
		if(!sets.isEmpty()) {
			updates.append("$set", sets);
		}
		if(!unsets.isEmpty()) {
			updates.append("$unset", unsets);
		}
		if(!addToSets.isEmpty()) {
			updates.append("$addToSet", addToSets);
		}
		if(!incs.isEmpty()) {
			updates.append("$inc", incs);
		}
		
		return updates;
	}
}
