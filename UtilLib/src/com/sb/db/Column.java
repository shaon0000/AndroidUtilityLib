/**
 * 
 */
package com.sb.db;

/**
 * Class defines a database column, consisting of column name,
 * and column type.
 * 
 * @author Shaon
 *
 */
public class Column {
	
	// Describes the various types of columns we can have.
	// If we want to add a new type of column, we add it here.
	public static enum ColumnType {
		PRIMARY_INT_KEY("INTEGER PRIMARY KEY"),
		INTEGER("INTEGER"),
		LONG("UNSIGNED INT"),
		STRING("TEXT"),
		DOUBLE("TEXT");
		String dbType;
		
		ColumnType(String dbType) {
			this.dbType = dbType;
		}
		
		public String toString() {
			return this.dbType;
		}
	}
	
	private String key;
	private ColumnType type;
	
	public Column(String key, ColumnType type) {
		this.key = key;
		this.type = type;
	}
	
	public String toString() {
		return key + " " + type;
	}
	
	public ColumnType getType() {
		return type;
	}
	
	public String getKey() {
		return key;
	}
}
