package ipod.ui.table;


public interface TableModel {

	public int getColumnCount();
	public int getColumnWidth(int col);
	public int getRowCount();
	public Object getData(int row, int col);
	
	/**
	 * Will be called if the TableView or the user changes data in the table.
	 * @param row
	 * @param col The column of the changed value or -1 if the complete row has changed
	 * @param newValue The new value for the cell or null if a row has been deleted.
	 */
	public void updateData(int row, int col, Object newValue);
}
