package state;

import java.util.ArrayList;

import utility.Action;
import utility.GoalTest;
/**
 * State class 
 * used to record the state of the game and 
 * define the methods for states
 *
 * @author yuanduochen
 *
 */
public class State implements Cloneable{

//create two static variables to record the number of row and colunm in the game
public static final int ROW=4;
public static final int COLUNM=5;  
// matrix record the board of the game 
public int[][] board;
//two stateInfo objects to maintain the grids in lines
public StateInfo player_1;
public StateInfo player_2;
//count the number of grids left
public int leave;

/**
 * construct function of the State
 */
public State(){
	//initialize the matrix
	//set each integer to 0, meaning no players fills in this grid
	board=new int[ROW+1][COLUNM+1];
	for(int i=0;i<ROW+1;i++)
		for(int j=0;j<COLUNM+1;j++)	
			board[i][j]=0;
	//initialize the two stateInfo
	player_1=new StateInfo();
	player_2=new StateInfo();
	//initialize the count of left grids to 20
	leave=20;
}

/**
 * get the total row
 * @return the total row
 */
public static int getRow() {
	return ROW;
}

/**
 * get the total column
 * @return the total column
 */
public static int getColunm() {
	return COLUNM;
}

/**
 * set the board
 * @param board the matrix you want to set 
 */
public void setBoard(int[][] board) {
	this.board = board;
}

/**
 * set the state information to player 1
 * @param player_1 the stateInfo you want to set
 */
public void setPlayer_1(StateInfo player_1) {
	this.player_1 = player_1;
}
/**
 * set the state information to player 2
 * @param player_2 the stateInfo you want to set
 */
public void setPlayer_2(StateInfo player_2) {
	this.player_2 = player_2;
}

/**
 * function to fill the grid with the action provided
 * 
 * @param act action taken to fill the grid
 * @param playerNo which player filling the grid
 */
public void fillGrid(Action act, int playerNo){
	//fill the grid according to the action given
	board[act.getRow()][act.getColunm()]=playerNo;
	/*
	 * update the stateInfo of the state
	 */
	//when the player is human
	if(playerNo==1)
	{   //add the count of column line by one 
		player_1.getColunmCount()[act.getColunm()]++;
		//add the count of line of first four items of the row by one
		if(act.getColunm()<5)
		{
			player_1.getRowFrontCount()[act.getRow()]++;
			
		}
		//add the count of line of last four items of the row by one
		if(act.getColunm()>1)
		{
			player_1.getRowBackCount()[act.getRow()]++;
			
		}
		//add the count of 45 degree line by one 
		if(act.getRow()==act.getColunm())
		{
			player_1.get_45degCount()[0]++;
		
		}
		//add the count of the other 45 degree line by one
		if(act.getRow()+1==act.getColunm())
		{
			player_1.get_45degCount()[1]++;
			
		}
		//add the count of the 135 degree line by one
		if(5-act.getRow()==act.getColunm())
		{
			player_1.get_135degCount()[0]++;
		}
		//add the count of the other 135 degree line by one
		if(5-act.getRow()+1==act.getColunm())
		{
			player_1.get_135degCount()[1]++;
		}
	
	}
	
	//if the player is the Ai agent
	else{	
			//add the count of the column by one  
			player_2.getColunmCount()[act.getColunm()]++;
			//add the count of the first items line of the row by one 
			if(act.getColunm()<5)
			{
				player_2.getRowFrontCount()[act.getRow()]++;
				
			}
			//add the count of the last items line of the row by one 
			if(act.getColunm()>1)
			{
				player_2.getRowBackCount()[act.getRow()]++;
				
			}
			//add the count of 45 degree line by one 
			if(act.getRow()==act.getColunm())
			{
				player_2.get_45degCount()[0]++;
			
			}
			//add the count of the other 45 degree line by one 
			if(act.getRow()+1==act.getColunm())
			{
				player_2.get_45degCount()[1]++;
				
			}
			//add the count of the 135 degree line by one 
			if(5-act.getRow()==act.getColunm())
			{
				player_2.get_135degCount()[0]++;
			}
			//add the count of the other 135 degree line by one
			if(5-act.getRow()+1==act.getColunm())
			{
				player_2.get_135degCount()[1]++;
			}

		}
	//minus the count of left grids by one
	leave--;
	
}


/**
 * get the stateInfo of the player 1
 * 
 * @return the StateInfo of player 1
 */
public StateInfo getPlayer_1() {
	return player_1;
}

/**
 * get the stateInfo of the player 2
 * @return the stateInfo of player 2
 */
public StateInfo getPlayer_2() {
	return player_2;
}

/**
 * get the board
 * @return the board of the state
 */
public int[][] getBoard() {
	return board;
}

/**
 * override the clone method of the State
 */
public Object clone(){
	State copy=null;
	try{
		copy=(State)super.clone();
		
	}catch(CloneNotSupportedException e){
		e.printStackTrace();
	}
	copy.player_1=(StateInfo)this.player_1.clone();
	copy.player_2=(StateInfo)this.player_2.clone();
	copy.board=this.board.clone();
	for(int i=0;i<ROW+1;i++)
			copy.board[i]=this.board[i].clone();
	
	return copy;
	
}

/**
 * get the available action list of the state
 * 
 * @return the list of the actions that available
 */
public ArrayList<Action> getActionList(){
	ArrayList<Action> result=new ArrayList<Action>();
	for(int i=1;i<ROW+1;i++)
		for(int j=1;j<COLUNM+1;j++)
			if(board[i][j]==0)
				result.add(new Action(i,j));
	return result;
}


}