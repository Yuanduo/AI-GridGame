package utility;

import state.State;
import state.StateInfo;
/**
 * GoalTest class consist of two static methods
 * isGoal method determine if the game come to an end
 * cutOff method determine if the game reaches a depth where should be cut off by the level
 * 
 * @author yuanduochen
 *
 */
public class GoalTest {
	//static variable test if the state reaches a goal
	//when there are 4 consist same items, the player with that item win
	public static final int WIN=4;
	
	/**
	 * determine if the game comes to an end 
	 * @param act action taken to reach the tested state
	 * @param state_info state information needed for the player
	 * @param leave number of left grids in the state
	 * @return integer referring the type of result
	 */
	public static int isGoal(Action act, StateInfo state_info,int leave){
		//judge if the column reaches a goal after the action
		if(state_info.getColunmCount()[act.getColunm()]+1==WIN)
			return 1;
		//judge if the first four grids of the row reaches a goal after the action
		if(act.getColunm()<5)
		{
			if(state_info.getRowFrontCount()[act.getRow()]+1==WIN)
				return 1;
		}
		//judge if the last four grids of the row reaches a goal after the action
		if(act.getColunm()>1)
		{
			if(state_info.getRowBackCount()[act.getRow()]+1==WIN)
				return 1;
		}
		//judge if the line of 45 degree reaches a goal after the action
		if(act.getRow()==act.getColunm())
		{
			if(state_info.get_45degCount()[0]+1==WIN)
				return 1;
		}
		//judge if the other line of 45 degree reaches a goal after the action
		if(act.getRow()+1==act.getColunm())
		{
			if(state_info.get_45degCount()[1]+1==WIN)
				return 1;
		}
		//judge if the line of 135 degree reaches a goal after the action
		if(5-act.getRow()==act.getColunm())
		{
			if(state_info.get_135degCount()[0]+1==WIN)
				return 1;
		}
		//judge if the other line of the 135 degree reaches a goal after the action
		if(5-act.getRow()+1==act.getColunm())
		{
			if(state_info.get_135degCount()[1]+1==WIN)
				return 1;
		}
		
		//if there is only one grid left 
		// return a result of draw game
		if(leave==1)
			return 0;
		
		//else return -1, to recognize the game can still go on
		return -1;
	}
	
	/**
	 * determine if the game reaches a depth where should be cut off by the level
	 * 
	 * @param state state needed to cut off
	 * @return value of utility calculated by a algorithm
	 */
	public static int cutOff(State state){
		int[] x=new int[5];		//data record the number of special state of player
		int[] o=new int[5];		//data record the number of special state of AI agent
		
		//initialize the two data to 0
		for(int i=0;i<5;i++)
		{
			x[i]=0;
			o[i]=0;
		}
		
		//determine the number of special state according to row
		for(int i=1;i<=4;i++){
			if(state.player_1.rowFrontCount[i]==0)
				x[state.player_2.rowFrontCount[i]]++;
			if(state.player_1.rowBackCount[i]==0)
				x[state.player_2.rowBackCount[i]]++;
			if(state.player_2.rowFrontCount[i]==0)
				o[state.player_1.rowFrontCount[i]]++;
			if(state.player_2.rowBackCount[i]==0)
				o[state.player_1.rowBackCount[i]]++;
		}
		//determine the number of special state according to column
		for(int i=0;i<=5;i++){
			if(state.player_1.colunmCount[i]==0)
				x[state.player_2.colunmCount[i]]++;
			if(state.player_2.colunmCount[i]==0)
				o[state.player_1.colunmCount[i]]++;
		}
		
		//determine the number of special state according to 45 degree and 135 degree line
		
		for(int i=0;i<=1;i++){
			if(state.player_1._45degCount[i]==0)
				x[state.player_2._45degCount[i]]++;
			if(state.player_2._45degCount[i]==0)
				o[state.player_1._45degCount[i]]++;
			if(state.player_1._135degCount[i]==0)
				x[state.player_2._135degCount[i]]++;
			if(state.player_2._135degCount[i]==0)
				o[state.player_1._135degCount[i]]++;
		}
		//use a formula to calculate a value for the cut off states
		return (9*x[3]+3*x[2]+x[1]-(9*o[3]+3*o[2]+o[1]));
		
	}
			
	
}
