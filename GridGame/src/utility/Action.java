package utility;
/**
 * Action class record the action player or AI agent take
 * consists of two integer variables
 * @author yuanduochen
 *
 */
public class Action {
	private int row;		//record the row of grid of the action	
	private int colunm;		//record the colunm of grid of the action

	/**
	 * construct the Action
	 * @param r row of the action
	 * @param c column of the action
	 */
	public Action(int r,int c){
		row=r;
		colunm=c;
	}
	/**
	 * get the row of the action
	 * @return row of the action
	 */
	public int getRow() {
		return row;
	}
	/**
	 * set the row of the action
	 * @param row the row need to be set 
	 */
	public void setRow(int row) {
		this.row = row;
	}
	/**
	 * get the column of the action
	 * @return the column of the action
	 */
	public int getColunm() {
		return colunm;
	}
	/**
	 * set the column of the action
	 * @param colunm column need to be set
	 */
	public void setColunm(int colunm) {
		this.colunm = colunm;
	}

}
