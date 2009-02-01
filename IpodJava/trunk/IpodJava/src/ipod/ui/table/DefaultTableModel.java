package ipod.ui.table;


public class DefaultTableModel implements TableModel {

	private Object[][] data;
	public DefaultTableModel(Object[][] data) {
		this.data = data;
	}
	@Override
	public int getColumnCount() {
		return data[0].length;
	}

	@Override
	public int getColumnWidth(int col) {
		return 320/getColumnCount();
	}

	@Override
	public Object getData(int row, int col) {
		return data[row][col];
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

}
