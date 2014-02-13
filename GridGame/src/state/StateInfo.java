package state;
/**
 * StateInfo class record the information to reach a goal state
 * 
 * @author yuanduochen
 *
 */
public class StateInfo implements Cloneable{
	
	public int[] rowFrontCount;	//record the count of the first four items of the row
	public int[] rowBackCount;	//record the count of the last four items of the row
	public int[] colunmCount;	//record the count of the column
	public int[] _45degCount;	//record the count of 45 degree line 
	public int[] _135degCount;	//record the count of 135 degree line
	
	/**
	 * construct the stateInfo
	 * initialize the fields of the stateInfo to 0 
	 */
	public StateInfo(){
		rowFrontCount=new int[State.ROW+1];
		for(int i=0;i<State.ROW+1;i++)
			rowFrontCount[i]=0;
		
		rowBackCount=new int[State.ROW+1];
		for(int i=0;i<State.ROW+1;i++)
			rowBackCount[i]=0;
		
		colunmCount=new int[State.COLUNM+1];
		for(int i=0;i<State.COLUNM+1;i++)
			colunmCount[i]=0;
		
		_45degCount=new int[2];
		_135degCount=new int[2];
		for(int i=0;i<2;i++)
		{
			_45degCount[i]=0;
			_135degCount[i]=0;
		}
	}
	
	/**
	 * the getter methods of the fields of stateInfo
	 * 
	 * @return count of front row
	 */
	public int[] getRowFrontCount() {
		return rowFrontCount;
	}
	/**
	 * the getter methods of the fields of stateInfo
	 * @return count of back row
	 */
	public int[] getRowBackCount() {
		return rowBackCount;
	}

	/**
	 * the getter methods of the fields of stateInfo
	 * @return count of column
	 */
	public int[] getColunmCount() {
		return colunmCount;
	}

	/**
	 * the getter methods of the fields of stateInfo
	 * @return count of the 45 degree lines
	 */
	public int[] get_45degCount() {
		return _45degCount;
	}

	/**
	 * the getter methods of the fields of stateInfo
	 * @return count of the 135 degree lines
	 */
	public int[] get_135degCount() {
		return _135degCount;
	}
	
	/**
	 * override the clone method for stateInfo
	 */
	public Object clone(){
		StateInfo copy=null;
		try{
			copy=(StateInfo)super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		
		copy.colunmCount=this.colunmCount.clone();
		copy._135degCount=this._135degCount.clone();
		copy._45degCount=this._45degCount.clone();
		copy.rowBackCount=this.rowBackCount.clone();
		copy.rowFrontCount=this.rowFrontCount.clone();
		return copy;
	}
}
