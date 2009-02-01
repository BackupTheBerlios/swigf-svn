package ipod.ui.table;


public interface TableModel {

	public int getColumnCount();
	public int getColumnWidth(int col);
	public int getRowCount();
	public Object getData(int row, int col);
}
