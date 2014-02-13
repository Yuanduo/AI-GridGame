package game;

import java.util.ArrayList;

import state.State;
import utility.Action;
import utility.GoalTest;
import utility.Result;
/**
 * 
 * Class processing the main logic of AI agent 
 * @author yuanduochen
 * 
 */
public class Game {
	
	public static int POSINFINITY=65535;    //positive infinity
	public static int NEGINFINITY=-65535;	//negative infinity

	public State game_state;		//state maintain the state of game
	private int level;				//record the level of AI agent

	/**
	 * construct function of Game class
	 * initialize the parameters: level of AI and who go first
	 * @param level the level of AI
	 * @param first determine who go first
	 * 
	 * 
	 */
	public Game(int level, int first){
		//create a new state when game is construct
		game_state=new State();
		this.level=level;
	
		//when the player choose that computer go first, make the first step of the AI agent
		if(first==2){
			//call makeDecision method to make a decision for AI agent
			Action act=makeDecision();
			//update the game state for the action AI agent choose
			game_state=getResult(game_state,act,2);
		}		
	}
	/**
	 * method called by the upper level 
	 * given an action player choose
	 * give a result including the statue of game and a decision AI agent make
	 * 
	 * @param act the action that player take
	 * @return a Result class contain the type of result and action AI takes if the player does not win
	 */
	public Result play(Action act){
		//test if player take the action, the game come to an end or not
		int flag=GoalTest.isGoal(act, game_state.player_1, game_state.leave);
		//player win
		//create a result and return with the action
		if(flag==1)
			return new Result(act,1);
		//game comes to a draw
		//create a result and return with the action
		if(flag==0)
			return new Result(act,0);
		//if game does not come to an end after the player take this action
		//update the state of game 
		game_state=getResult(game_state,act,1);

	
		//AI agent make a desion to choose which grid should be filled
		act=makeDecision();

		//test if the AI agent take action, the game come to an end or not
		flag=GoalTest.isGoal(act,game_state.player_2,game_state.leave);
		//AI agent win
		//create a result and return with the action
		if(flag==1)
			return new Result(act,-1);
		//game comes to a draw
		//create a result and return with the action
		if(flag==0)
			return new Result(act,0);
		//if game does not come to an end after the AI agent take this action
		game_state=getResult(game_state,act,2);
	
		//game does not come to an end
		//create a result with draw and return with the action
		return new Result(act,9);
			
	}
	/**
	 * function that return a state after the given action
	 * 
	 * @param pre the state before the action take
	 * @param act action taken by the player or the AI agent
	 * @param playerNo a flag that mark if the player is human or AI agent
	 * @return a state transfered by the previous state after taking the action
	 */
	public static State getResult(State pre, Action act,int playerNo){
		State result=(State)pre.clone();
		result.fillGrid(act,playerNo);
		return result;
		
	}
	
	/**
	 * main function processing the alpha beta search
	 * return an action that AI agent choose
	 *
	 * @return return an action that the AI agent determines by alpha-beta search
	 */
	public Action makeDecision(){
		int depth=0;	//initialize the depth to 0 when the function begins, using to record the depth reached
		Action result=null;
		int v=NEGINFINITY;	//initialize the v to negative infinity
		
		//get the action list that available
		ArrayList<Action> list=game_state.getActionList();
		//for each action, search the best action using alpha beta search
		for(int i=0;i<list.size();i++)
		{   
			int value=minValue(game_state,list.get(i),NEGINFINITY,POSINFINITY,depth+1);
			if(value>v)
			{
				result=list.get(i);
				v=value;
			}
			
			
		}
		//return the action AI agent chooses
		return result;
	}
	
	/**
	 * minValue function determine the best action taken by opposite
	 * @param state the state before taking the action to reach this level
	 * @param act the action taken to reach this level
	 * @param alpha	the value of alpha in the search algorithm
	 * @param beta the value of beta in the search algorithm
	 * @param depth	used to record which depth reached 
	 * @return a value of v in the search algorithm
	 */
	public int minValue(State state, Action act, int alpha,int beta,int depth){
		int utility=GoalTest.isGoal(act,state.player_2,state.leave);
		if(utility==0)
			return 0;
		if(utility==1)
			return 153;
		int v=POSINFINITY;
		State r=getResult(state,act,2);
		if(depth==level)
			return GoalTest.cutOff(r);

		ArrayList<Action> list=r.getActionList();
		for(int i=0;i<list.size();i++){
			v=Math.min(v, maxValue(r,list.get(i),alpha,beta,depth+1));
			if(v<=alpha)
				return v;
			beta=Math.min(beta, v);
			
		}
		return v;
	}
	
	/**
	 * maxValue function determine the best action taken by AI agent
	 * 
	 * @param state the state before taking the action to reach this level
	 * @param act the action taken to reach this level
	 * @param alpha the value of alpha in the search algorithm
	 * @param beta the value of beta in the search algorithm
	 * @param depth used to record which depth reached
	 * @return a value of v in the search algorithm
	 */
	public int maxValue(State state, Action act, int alpha,int beta,int depth){
		int utility=GoalTest.isGoal(act,state.player_1,state.leave);
		
		if(utility==0)
			return 0;
		if(utility==1)
			return -153;
		int v=NEGINFINITY;
		State r=getResult(state,act,1);
		
		if(depth==level)
			return GoalTest.cutOff(r);
		
		ArrayList<Action> list=r.getActionList();
		
		for(int i=0;i<list.size();i++){
			v=Math.max(v, minValue(r,list.get(i),alpha,beta,depth+1));
			if(v>=beta)
				return v;
			alpha=Math.max(alpha, v);
			
		}
		return v;
	}
	/*
	public static void main(String[] args){
		Game g=new Game(8,2);
		*/
		/*g.game_state.fillGrid(new Action(1,1), 1);
		g.game_state.fillGrid(new Action(1,2), 2);
		g.game_state.fillGrid(new Action(1,3), 1);
		g.game_state.fillGrid(new Action(1,4), 2);
		g.game_state.fillGrid(new Action(1,5), 1);
		g.game_state.fillGrid(new Action(2,1), 2);
		g.game_state.fillGrid(new Action(2,2), 1);
		g.game_state.fillGrid(new Action(2,3), 2);
		g.game_state.fillGrid(new Action(2,4), 1);
		g.game_state.fillGrid(new Action(2,5), 2);
		g.game_state.fillGrid(new Action(3,1), 1);
		g.game_state.fillGrid(new Action(3,2), 2);
		g.game_state.fillGrid(new Action(3,3), 1);
		g.game_state.fillGrid(new Action(3,4), 2);
		*/
		/*
		long start=System.currentTimeMillis();
		System.out.println(g.play(new Action(4,5)));
		long end=System.currentTimeMillis();
		for(int i=1;i<5;i++)
		{
			for(int j=1;j<6;j++)
				System.out.print(g.game_state.board[i][j]+" ");
			System.out.println();
		}
		System.out.println("time:   "+(end-start));
		System.out.println(g.play(new Action(1,1)));
		for(int i=1;i<5;i++)
		{
			for(int j=1;j<6;j++)
				System.out.print(g.game_state.board[i][j]+" ");
			System.out.println();
		}

	}
	*/
}
