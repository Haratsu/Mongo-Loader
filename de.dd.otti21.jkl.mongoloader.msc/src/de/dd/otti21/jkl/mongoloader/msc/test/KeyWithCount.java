package de.dd.otti21.jkl.mongoloader.msc.test;

import com.mongodb.DBObject;

/**
 * KeyWithCount
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class KeyWithCount {
	private String keyValue;
	private Integer count;
	
	public KeyWithCount(DBObject obj) {
		Object keyValue = obj.get("_id");
		this.keyValue = keyValue==null?null:keyValue.toString();
		Object countValue = obj.get("value");
		this.count = countValue==null?null:((Double)countValue).intValue();
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "KeyWithCount [keyValue=" + keyValue + ", count=" + count + "]";
	}

}
