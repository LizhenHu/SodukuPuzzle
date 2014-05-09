package SudokuPuzzle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SudokuPuzzle extends JFrame{
	//create a string array for input the solution from a file,
	//then users do not have to input the answer one by one in the application
	private String[] input;	
	private JTextField[] name;
	private JButton jbtStart = new JButton("Start");
	private JButton jbtCheck = new JButton("Check");
	private JComboBox jcb = new JComboBox(new Object[]{"Easy", "Medium","Hard"});
	
	
	//main method, create interface for users
	public static void main(String[] args){
		SudokuPuzzle frame = new SudokuPuzzle();
		frame.setTitle("Sudoku Puzzle");
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	//create a default constructors 
	public SudokuPuzzle(){
		//create a panel to contain all the textfield to show sudoku
		JPanel p1 = new JPanel(new GridLayout(9,9,5,5));
		
		name = new JTextField[81];	
		
		for (int i = 0; i < 81; i ++){
			name[i] = new JTextField();
			p1.add(name[i]);
		}
		 
		//create a panel to contain the check button
		JPanel p2 = new JPanel(new GridLayout(1,3,5,0));
		
		jcb.setForeground(Color.black); 
		jcb.setBackground(Color.white); 
		jcb.setSelectedItem("Easy");
		
		//add select level JCombox, start JButton, check JButton and answer JButton
		// to the panel 
		p2.add(jcb);
		p2.add(jbtStart);
		p2.add(jbtCheck);
		add(p1,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
		//register listener with the button
		jbtCheck.addActionListener(new CheckButtonListener());
		jbtStart.addActionListener(new StartButtonListener());
	}

	private class StartButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			for(int n = 0; n < 81; n++){
				name[n].setText(null);
				name[n].setBackground(Color.white);
				name[n].setEnabled(true);	
			}
			
			//generate soduko according to the selected level
			int level =  jcb.getSelectedIndex();
			SudokuGenerator sudokuObj;
			switch(level){ 
				case 0: sudokuObj = new SudokuGenerator(1);
						break;
				case 1:  sudokuObj = new SudokuGenerator(2);
						break;
				case 2: sudokuObj = new SudokuGenerator(3);
						break;
				default: sudokuObj = new SudokuGenerator();
						break;	
			}

			int[][] sudoku = sudokuObj.getSudoku();
			int n = 0;
			//create 81 JTextFields to display the solution 
			//create 81 JTextFields to display the solution 
			while(n < 81){
			for(int i = 0; i < 9; i++){
				for(int j = 0; j < 9; j++){
				 name[n].setText(Integer.toString(sudoku[i][j]));
				 name[n]. setHorizontalAlignment(JTextField.CENTER);
				 //disable the textfields that with numbers other than 0s
				 if(Integer.parseInt(name[n].getText()) != 0){
					 name[n].setBackground(Color.lightGray);
					 name[n].setDisabledTextColor(Color.BLACK);
					 name[n].setEnabled(false);			 
				 }
				 else{
					 name[n].setText(null);
				 }
				 n++;
				}
			}
			}
			
		}
	}

	//create a listener for ButtonListener to create the answer users inputted
	private class CheckButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			int[] sudoku1 = new int[81];
			for(int i = 0; i < 81; i++){
				sudoku1[i] = Integer.parseInt(name[i].getText());
				
			}
			
			//change the one dimensional array to two dimensional array
			int[][] sudoku = new int[9][9];	
			for(int i = 0; i < 81; i++){
				sudoku[i/9][i%9] = sudoku1[i];			
			}
			
			// call isValis method to check the answer and then display the result
			if(isValid(sudoku))
				JOptionPane.showMessageDialog(null,
						"Congratulations! You are correct!",
						"Check Sudoku",
						JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null,
						"Sorry, You answer is incorrect. Check carefully to revise it",
						"Check Sudoku",
						JOptionPane.INFORMATION_MESSAGE);
				
		}

	
}	
	
	//create a method to check the sudoku
	public static boolean isValid(int[][] sudoku){	
		boolean[] covered =new boolean[9];
		
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if (sudoku[i][j] <= 0 || sudoku[i][j] > 9)
					return false;
			}
		}
		
		
		//check if each row is covered by 1 to 9
		for(int i = 0; i < 9; i++){
			
			//assign each element of covered to false
			for(int k = 0; k < 9; k++)
				covered[k] =false;	
			
			for(int j = 0; j < 9; j++)
				covered[(sudoku[i][j] - 1)] = true;
			
			if(isAllCovered(covered) != true)
				return false;
		}
		
		//check if each column is covered by 1 to 9
		for(int i = 0; i < 9; i++){
			
			//assign each element of covered to false
			for(int k = 0; k < 9; k++)
				covered[k] =false;	
			
			for(int j = 0; j < 9; j++)
				covered[(sudoku[j][i] - 1)] = true;
			
			if(isAllCovered(covered) != true)
				return false;
		}
		
		//check if each small box is covered by 1 to 9
		for(int i = 0; i < 9; i+=3){
			for(int j = 0; j < 9; j+=3){
				
				//assign each element of covered to false
				for(int k = 0; k < 9; k++)
					covered[k] =false;
				
				for(int row = (i/3)*3; row < (i/3)*3+3; row++){
					for(int column = (j/3)*3; column < (j/3)*3+3; column++){
						covered[(sudoku[row][column] - 1)] = true;
					}
				}
				if(isAllCovered(covered) != true)
					return false;
			}
		}
		return true;
	
	}
	
	public static boolean isAllCovered(boolean[] covered){
		for(int i = 0; i < 9; i++){
			if (covered[i] == false)
				return false;
		}
		return true;
	}
	
}

