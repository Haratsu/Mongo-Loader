package de.dd.otti21.jkl.mongoloader.msc.persistence.connectivity;

import com.mongodb.DBObject;

/**
 * ValueParserUtil
 * 
 * @author Hagen Rahn
 * @version $Revision$
 *
 * $LastChangedDate$
 * $LastChangedBy$
 */
public class ValueParserUtil {

	public static boolean getNotNullBoolean(DBObject bsonObject, String attributeName) {
		Object value = bsonObject.get(attributeName);
		return value==null?false:(Boolean) value;
	}

	public static int getInteger(Object object) {
		if (object instanceof Integer) {
			return (Integer) object;
		} else if(object instanceof Double) {
			return ((Double)object).intValue();
		} else if (object instanceof Float) {
			return ((Float) object).intValue();
		}
		return 0;
	}

}
