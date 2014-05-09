package SudokuPuzzle;

import java.util.Random;

public class SudokuGenerator {
	//using a correct sudoku as the seed to generate new sudoku
	private int[][] sudoku=
		   {{5, 3, 4, 6, 7, 8, 9, 1, 2},
			{6, 7, 2, 1, 9, 5, 3, 4, 8}, 
			{1, 9, 8, 3, 4, 2, 5, 6, 7},
			{8, 5, 9, 7, 6, 1, 4, 2, 3}, 
			{4, 2, 6, 8, 5, 3, 7, 9, 1},
			{7, 1, 3, 9, 2, 4, 8, 5, 6}, 
			{9, 6, 1, 5, 3, 7, 2, 8, 4},
			{2, 8, 7, 4, 1, 9, 6, 3, 5},
			{3, 4, 5, 2, 8, 6, 1, 7, 9}};;
	private int[][] sudokuClone = new int[9][9];
	private Random random = new Random();	
	
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	
	public SudokuGenerator(){
		this.generator(EASY);
	}
	public SudokuGenerator(int level){
		this.generator(level);
	}
	
	
	public int[][] getSudoku() {
		return sudoku;
	}
	
	public int[][] generatorSudokuBase(){
		int[] temp =new int[9];
		//random change the rows from a correct sudoku bewteen 
		//1-3 rows, 4-6 rows and 7-9 rows
		for (int row = 0; row < 9; row++){
			if((random.nextInt(9) %2) == 0 && row % 3 != 2){
				for (int col = 0; col < 9; col++){
					temp[col] = sudoku[row][col] ;
					sudoku[row][col] = sudoku[row+1][col];
					sudoku[row+1][col] = temp[col];
				}
			}	
		}
		
		//random change the columns from a correct sudoku bewteen 
				//1-3 columns, 4-6 columns and 7-9 columns
		int randomColumn;
		for(int i = 0; i <20; i++){
			randomColumn = random.nextInt(8) + 1;
			for(int row = 0; row < 9; row ++){
				if(randomColumn % 3 != 0){
					temp[row] = sudoku[row][randomColumn];
					sudoku[row][randomColumn] = sudoku[row][randomColumn -1];
					sudoku[row][randomColumn - 1] = temp[row];
				}
			}
		}
		return sudoku;
	}

	//create a method to generate a sudoku according to difficulty
	public int[][] generator(int level){
		generatorSudokuBase();
		int numsOfRemove = 0;
		switch(level){
			case EASY: numsOfRemove = 40; break;
			case MEDIUM: numsOfRemove = 50; break;
			case HARD: numsOfRemove = 64; break;
			default: break;
		}
		
		for(int i = 0; i < numsOfRemove; i++){
			int x = random.nextInt(9);
			int y = random.nextInt(9);
			sudoku[x][y] = sudokuClone[x][y] = 0;
		}
		
		return sudoku;
	}
	
}
