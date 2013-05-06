/**
 * 
 */
package com.sb.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * @author Shaon
 *
 */
public abstract class BaseTable<AssociatedClass> {
	
	/**
	 * 
	 * @return name of the table
	 */
	public abstract String getName();
	
	/**
	 * This method is called during initialization. All
	 * columns belonging to this Table must be in the returned
	 * list. Appending new columns later, will be ignored. Additionally
	 * the resulting table will add columns in the same order as this list.
	 * 
	 * @return a list of Columns
	 */
	protected abstract ArrayList<Column> initializeColumns();
	public abstract ContentValues serialize(AssociatedClass obj);
	public abstract AssociatedClass deserialize(Cursor cursor);
	
	private List<Column> immutableColumns = null;
	private Map<String, Integer> columnMap;
	
	public BaseTable() {
		this.columnMap = getColumnMapping();
	}
	
	/**
	 * 
	 * @return A SQL statement, which can be used to generate this table;
	 */
	public final String sqlCreateTable() {
		return new SqlCreateTableGenerator(getName(), getColumns()).build();
	}
	
	/**
	 * 
	 * @return A list of columns, where the data set cannot be
	 * modified.
	 */
	public final List<Column> getColumns() {
		if (immutableColumns == null) {
			immutableColumns = Collections.unmodifiableList(initializeColumns());
		}
		
		return immutableColumns;
	}
	
	private Map<String, Integer> getColumnMapping() {
		HashMap<String, Integer> columnMapping = new HashMap<String, Integer>();
		int i = 0;
		for (Column column: getColumns()) {
			columnMapping.put(column.getKey(), i);
			i++;
		}
		
		return columnMapping;
	}
	
	protected final int getColumnPos(String key) {
		return columnMap.get(key);
	}
	
	/**
	 * 
	 * @param column A column that belongs to this table.
	 * @param cursor The cursor returned from a query.
	 * @return The value of the column from the result of the query, depending on the type of the column.
	 */
	protected final Object extract(Column column, Cursor cursor) {
		int pos = getColumnPos(column.getKey());
		switch(column.getType()) {
			case DOUBLE: {
				return cursor.getDouble(pos);
			} case INTEGER: {
				return cursor.getInt(pos);
			} case LONG: {
				return cursor.getLong(pos);
			} case PRIMARY_INT_KEY: {
				return cursor.getInt(pos);
			} case STRING: {
				return cursor.getString(pos);
			} default: {
				throw new RuntimeException("Unknown type of column");
			}
		}
	}
	
	
	
	
}
