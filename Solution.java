import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
	
	public static int getPointsForBoard(int[][] board, HashMap<Integer, Integer> boardPoints) {
		int hashValue = Arrays.deepHashCode(board);
		if(boardPoints.containsKey(hashValue)){
				return boardPoints.get(hashValue);
		} else {
			boardPoints.put(hashValue, -1);
		}
		
		int leftMovePoints = 0;
		int rightMovePoints = 0;
		int topMovePoints = 0;
		int bottomMovePoints = 0;
		
		if(isPossibleLeftMove(board, false)){
			int [][]boardLeft = board.clone();
			leftMovePoints = moveLeft(boardLeft);
			int leftBoardPoints = getPointsForBoard(boardLeft, boardPoints);
			leftMovePoints+=leftBoardPoints;
			if(boardPoints.get(hashValue)<leftMovePoints){
				boardPoints.put(hashValue, leftBoardPoints);
			}
			
		}
		
		if(isPossibleRightMove(board, false)){
			int [][]boardRight = board.clone();
			rightMovePoints = moveRight(boardRight);
			int rightBoardPoints = getPointsForBoard(boardRight, boardPoints);
			rightMovePoints+=rightBoardPoints;
			if(boardPoints.get(hashValue)<rightMovePoints){
				boardPoints.put(hashValue, rightMovePoints);
			}
		}
		
		if(isPossibleTopMove(board, false)){
			int [][]boardTop = board.clone();
			topMovePoints = moveTop(boardTop);
			int topBoardPoints = getPointsForBoard(boardTop, boardPoints);
			topMovePoints+=topBoardPoints;
			if(boardPoints.get(hashValue)<topMovePoints){
				boardPoints.put(hashValue, topMovePoints);
			}
		}
		
		if(isPossibleBottomMove(board, false)){
			int [][]boardBottom = board.clone();
			bottomMovePoints = moveBottom(boardBottom);
			int bottomBoardPoints = getPointsForBoard(boardBottom, boardPoints);
			bottomMovePoints+=bottomBoardPoints;
			if(boardPoints.get(hashValue)<bottomMovePoints){
				boardPoints.put(hashValue, bottomMovePoints);
			}
		}
		
		return max(max(leftMovePoints, rightMovePoints), max(topMovePoints, bottomMovePoints));
	}

	public static int max(int a, int b){
		return (a>b)? a: b;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
	    int[][] board = new int[4][4];
	    
	    for (int i = 0; i < 4; i++)
	      for (int j = 0; j < 4; j++)
	         board[i][j] = in.nextInt();
	    
	    HashMap<Integer, Integer> boardPoints = new HashMap<>();
	    int hashValue = Arrays.deepHashCode(board);
	    boardPoints.put(hashValue, -1);
	    
	    // find max element
	    // find eccentricity of the game
	    // top left or top right or bottom left or bottom right
	    // position the max element on one corner if not there already
	    // check if move does not spoil eccentricity
	    
		
		if(isPossibleTopMove(board, true)){
			System.out.println("UP");
		} else if (isPossibleRightMove(board, true)){
			int[][] rightBoard = cloneArray(board);
			int rightBoardPoints = moveRight(rightBoard);
			
			int[][]leftBoard = cloneArray(board);
			int leftBoardPoints = moveLeft(leftBoard);
			
			int orderedElementsLeftBoard=0;
			int orderedElementsRightBoard=0;
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(j+1<4){
						if(leftBoard[i][j+1]==leftBoard[i][j] || leftBoard[i][j+1]==leftBoard[i][j] || leftBoard[i][j+1]==leftBoard[i][j]/2){
							orderedElementsLeftBoard++;
						}
						if(rightBoard[i][j+1]==rightBoard[i][j] || rightBoard[i][j+1]/2==rightBoard[i][j] || rightBoard[i][j+1]==rightBoard[i][j]/2){
							orderedElementsRightBoard++;
						}
					}
					if(i+1<4){
						if(leftBoard[i+1][j]==leftBoard[i][j] || leftBoard[i+1][j]/2==leftBoard[i][j] || leftBoard[i+1][j]==leftBoard[i][j]/2){
							orderedElementsLeftBoard++;
						}
						if(rightBoard[i+1][j]==rightBoard[i][j] || rightBoard[i+1][j]/2==rightBoard[i][j] || rightBoard[i+1][j]==rightBoard[i][j]/2){
							orderedElementsRightBoard++;
						}
					}
				}
			}
			
			if(orderedElementsLeftBoard>orderedElementsRightBoard){
				// check if moving left spoils eccentricity or not?
				int maxElementX = 0;
				int maxElementY = 0;
				for(int i=0;i<4;i++){
					for(int j=0;j<4;j++){
						if(leftBoard[maxElementX][maxElementY]<=leftBoard[i][j]){
							maxElementX = i;
							maxElementY = j;
						}
					}
				}
				if((maxElementX==0 && maxElementY==0) || (maxElementX==0 && maxElementY==3))
					System.out.println("33LEFT");
				else
					System.out.println("44RIGHT");
			} else { 
				int maxElementX = 0;
				int maxElementY = 0;
				for(int i=0;i<4;i++){
					for(int j=0;j<4;j++){
						if(rightBoard[maxElementX][maxElementY]<=rightBoard[i][j]){
							maxElementX = i;
							maxElementY = j;
						}
					}
				}
				
				System.out.println();
				for(int i=0;i<4;i++){
					for(int j=0;j<4;j++){
						System.out.print(rightBoard[i][j]+" ");
					}
					System.out.println();
				}
				if((maxElementX==0 && maxElementY==0) || (maxElementX==0 && maxElementY==3))
					System.out.println("11RIGHT");
				else
					System.out.println("22LEFT");
			}
		} else {
			System.out.println("DOWN");
		}
	    
	    in.close();
	 }
	
	public static boolean isPossibleLeftMove(int[][] board, boolean isFirstMove){
		// check is a move over zero is possible or not?
		boolean isMovePossible = false;
		for(int i=0;i<4 && !isMovePossible;i++){
			int emptyCellPosition = -1;
			int firstFilledCellPosition = -1;
			for(int j=0;j<4;j++){
				if(board[i][j]==0)
					emptyCellPosition = j;
				else
					firstFilledCellPosition = j;
				
				if(emptyCellPosition!=-1 && firstFilledCellPosition!=-1){
					if(emptyCellPosition<firstFilledCellPosition){
						isMovePossible = true;
						break;
					}
				}
			}
		}
		
		// if its a first move, them move over zero is possible
		if(isFirstMove && isMovePossible)
			return true;
		
		// if its not a first move, then move over zero is prohibited!
		if(!isFirstMove && isMovePossible)
			return false;
		
		// now move over zero is not possible, check for a merge??
		for(int i=0;i<4;i++){
			for(int j=1;j<4;j++){
				if(board[i][j]!=0 && board[i][j]==board[i][j-1])
					return true;
			}
		}
		
		return false;
	}
	
	public static boolean isPossibleRightMove(int[][] board, boolean isFirstMove){
		// check is a move over zero is possible or not?
		boolean isMovePossible = false;
		for(int i=0;i<4 && !isMovePossible;i++){
			int emptyCellPosition = -1;
			int firstFilledCellPosition = -1;
			for(int j=3;j>=0;j--){
				if(board[i][j]==0)
					emptyCellPosition = j;
				else
					firstFilledCellPosition = j;
				
				if(emptyCellPosition!=-1 && firstFilledCellPosition!=-1){
					if(emptyCellPosition>firstFilledCellPosition){
						isMovePossible =  true;
						break;
					}
				}
			}
		}
		
		// if its a first move, them move over zero is possible
		if(isFirstMove && isMovePossible)
			return true;
				
		// if its not a first move, then move over zero is prohibited!
		if(!isFirstMove && isMovePossible)
			return false;
				
		// now move over zero is not possible, check for a merge??
		for(int i=0;i<4;i++){
			for(int j=3;j>=1;j--){
				if(board[i][j]!=0 && board[i][j]==board[i][j-1])
					return true;
			}
		}
		
		return false;
	}
	
	public static boolean isPossibleTopMove(int[][] board, boolean isFirstMove){
		// check is a move over zero is possible or not?
		boolean isMovePossible = false;
		for(int j=0;j<4 && !isMovePossible;j++){
			int emptyCellPosition = -1;
			int firstFilledCellPosition = -1;
			for(int i=0;i<4;i++){
				if(board[i][j]==0)
					emptyCellPosition = i;
				else
					firstFilledCellPosition = i;
				
				if(emptyCellPosition!=-1 && firstFilledCellPosition!=-1){
					if(emptyCellPosition<firstFilledCellPosition){
						isMovePossible = true;
						break;
					}
				}
			}
		}
		
		// if its a first move, them move over zero is possible
		if(isFirstMove && isMovePossible)
			return true;
						
		// if its not a first move, then move over zero is prohibited!
		if(!isFirstMove && isMovePossible)
			return false;
						
		// now move over zero is not possible, check for a merge??
		for(int j=0;j<4;j++){
			for(int i=1;i<4;i++){
				if(board[i][j]!=0 && board[i][j]==board[i-1][j])
					return true;
			}
		}
			
		return false;
	}
	
	public static boolean isPossibleBottomMove(int[][] board, boolean isFirstMove){
		// check is a move over zero is possible or not?
		boolean isMovePossible = false;
		for(int j=0;j<4 && !isMovePossible;j++){
			int emptyCellPosition = -1;
			int firstFilledCellPosition = -1;
			for(int i=3;i>=0;i--){
				if(board[i][j]==0)
					emptyCellPosition = i;
				else
					firstFilledCellPosition = i;
				
				if(emptyCellPosition!=-1 && firstFilledCellPosition!=-1){
					if(emptyCellPosition>firstFilledCellPosition){
						isMovePossible = true;
						break;
					}
				}
			}
		}
		
		// if its a first move, them move over zero is possible
		if(isFirstMove && isMovePossible)
			return true;
								
		// if its not a first move, then move over zero is prohibited!
		if(!isFirstMove && isMovePossible)
			return false;
								
		// now move over zero is not possible, check for a merge??
		for(int j=0;j<4;j++){
			for(int i=3;i>=1;i--){
				if(board[i][j]!=0 && board[i][j]==board[i-1][j])
					return true;
			}
		}
				
		return false;
	}
	
	public static int moveLeft(int[][] board){
		int points = 0;
		for(int i=0;i<4;i++){
			for(int j=1;j<4;j++){
				if(board[i][j]==board[i][j-1]){
					board[i][j-1]+=board[i][j];
					board[i][j]=0;
					points+=board[i][j-1];
				}
			}
		}
		
		// shift them left
		for(int i=0;i<4;i++){
			int position = 0;
			for(int j=0;j<4;j++){
				if(board[i][j]!=0){
					board[i][position] = board[i][j];
					if(j!=position)
						board[i][j] = 0;
					position++;
				}
			}
		}
		
		return points;
	}
	
	public static int moveRight(int[][] board){
		int points = 0;
		for(int i=0;i<4;i++){
			for(int j=3;j>=1;j--){
				if(board[i][j]==board[i][j-1]){
					board[i][j]+=board[i][j-1];
					board[i][j-1]=0;
					points+=board[i][j];
				}
			}
		}
		
		// shift them right
		for(int i=0;i<4;i++){
			int position = 3;
			for(int j=3;j>=0;j--){
				if(board[i][j]!=0){
					board[i][position] = board[i][j];
					if(j!=position)
						board[i][j] = 0;
					position--;
				}
			}
		}
		
		return points;
	}
	
	public static int moveTop(int[][] board){
		int points = 0;
		for(int j=0;j<4;j++){
			for(int i=1;i<4;i++){
				if(board[i][j]==board[i-1][j]){
					board[i-1][j]+=board[i][j];
					board[i][j]=0;
					points+=board[i-1][j];
				}
			}
		}
		
		// shift them top
		for(int j=0;j<4;j++){
			int position = 0;
			for(int i=0;i<4;i++){
				if(board[i][j]!=0){
					board[position][j] = board[i][j];
					if(i!=position)
						board[i][j] = 0;
					position++;
				}
			}
		}
		
		return points;
	}
	
	public static int moveBottom(int[][] board){
		int points = 0;
		for(int j=0;j<4;j++){
			for(int i=3;i>=1;i--){
				if(board[i][j]==board[i-1][j]){
					board[i][j]+=board[i-1][j];
					board[i-1][j]=0;
					points+=board[i][j];
				}
			}
		}
		
		// shift them left
		for(int j=0;j<4;j++){
			int position = 3;
			for(int i=3;i>=0;i--){
				if(board[i][j]!=0){
					board[position][j] = board[i][j];
					if(i!=position)
						board[i][j] = 0;
					position--;
				}
			}
		}
		
		return points;
	}
	
	public static int[][] cloneArray(int [][]board){
		int[][] newBoard = new int[4][4];
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++)
				newBoard[i][j] = board[i][j];
		}
		
		return newBoard;
	}
}