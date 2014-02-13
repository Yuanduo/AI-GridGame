package action_listener;

import game.Game;
import gameUI.GameUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import utility.Action;
import utility.Result;
/**
 * Listener controlling step when player fills a grid
 * 
 * @author yuanduochen
 *
 */
public class StepListener implements ActionListener{
	private Game game;     //object of game 
	private int row;		//for each button , the row of it
	private int colunm;		//for each button, the colunm of it 
	private GameUI gui;		//object of game UI
	
	/**
	 * construct the listener get parameters needed 
	 * 
	 * @param g game object need to be controlled 
	 * @param r the row of the button
	 * @param c the column of the button
	 * @param gui game UI object need to be controlled
	 */
	public StepListener(Game g, int r, int c,GameUI gui){
		game=g;
		row=r;
		colunm=c;
		this.gui=gui;
	}
	@Override
	/**
	 *override the actionPerformed method 
	 *to complete the step and make decision
	 */
	public void actionPerformed(ActionEvent arg0) {
		//count the number of step player has done 
		gui.count++;
		//disable all buttons while computer is making decision
		for(int i=0;i<4;i++)
			for(int j=0;j<5;j++){
				if(game.game_state.board[i+1][j+1]==0)
					gui.grid[i][j].setEnabled(false);
			}
		//set the text of the button to "O"
		//to mark the grid that player has filled
		gui.grid[row][colunm].setText("O");
		//refresh the game UI
		gui.setVisible(true);
		//call play method for game
		Result re=game.play(new Action(row+1,colunm+1));
		/*
		 * judge if the game ends
		 * if result is 9, then game is not over, continue playing
		 * and mark the grid AI agent has made decision "X"
		 * then set the available grid enabled
		 */
		if(re.result==9){
			gui.grid[re.act.getRow()-1][re.act.getColunm()-1].setText("X");
			for(int i=0;i<4;i++)
				for(int j=0;j<5;j++){
					if(game.game_state.board[i+1][j+1]==0)
						gui.grid[i][j].setEnabled(true);
				}
		}
		/*
		 * if the result is not 9, the game over
		 * 1 means player wins
		 * -1 means AI agent wins
		 * 0 means the game come to a draw
		 */
		else{
			if(re.result==1)
				JOptionPane.showMessageDialog(gui, "Congritulations, you win!");
			else if(re.result==-1){
				gui.grid[re.act.getRow()-1][re.act.getColunm()-1].setText("X");
				JOptionPane.showMessageDialog(gui, "So close, you lose!");
				}
			else{
				gui.grid[re.act.getRow()-1][re.act.getColunm()-1].setText("X");
				JOptionPane.showMessageDialog(gui, "Draw!");
				}
		}
		//update the step label in game UI
		gui.step.setText(String.valueOf(gui.count));
		
	}
	
	
}
