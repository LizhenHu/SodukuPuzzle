package Final;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SudokuPuzzle extends JFrame{
	//create a string array for input the solution from a file,
	//then users do not have to input the answer one by one in the application
	private String[] input;	
	private JTextField[] name;
	private JButton jbt = new JButton("Check");
	
	//create a default constructors 
	public SudokuPuzzle(){
		//create a panel to contain all the textfield to show sudoku
		JPanel p1 = new JPanel(new GridLayout(9,9,5,5));
		
		//create a panel to contain the check button
		JPanel p2 = new JPanel();
		
		//call inputFile method to get the solution from file
		//input = inputFile();
		
		name = new JTextField[81];
		
		// call sudokuGenerator class to generator sudoku
		SudokuGenerator sudokuObj = new SudokuGenerator();
		int[][] sudoku = sudokuObj.getSudoku();
		
		//create 81 JTextFields to display the solution 
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
			 name[i] =new JTextField(Integer.toString(sudoku[i][j]));
			 name[i]. setHorizontalAlignment(JTextField.CENTER);
			 //disable the textfields that with numbers other than 0s
			 if(Integer.parseInt(name[i].getText()) != 0){
				 name[i].setBackground(Color.lightGray);
				 name[i].setDisabledTextColor(Color.BLACK);
				 name[i].setEnabled(false);			 
			 }
			p1.add(name[i]);
			}
		}
		
		
		p2.add(jbt);
		add(p1,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
		//register listener with the button
		jbt.addActionListener(new ButtonListener());
		
	}
	
	//main method, create interface for users
	public static void main(String[] args){
		SudokuPuzzle frame = new SudokuPuzzle();
		frame.setTitle("Sudoku Puzzle");
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	//create a listener for ButtonListener to create the answer users inputted
	private class ButtonListener implements ActionListener{
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

