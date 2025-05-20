import java.util.*;

public class Output {
    private Tree root;
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String NAVY = "\u001B[34m";
    public static final String YELLOW_BG = "\u001B[43m";


    public Output(Tree root){
        this.root = root;
    }

    public void printBoard(){
        printBoardHelper(root, 0);
    }

    private void printBoardHelper(Tree node, int i){
        if (node == null){
            return;
        }
        if (i == 0){
            System.out.println("Papan Awal");
        } else {
            System.out.println("Gerakan " + i + ": " + node.getIdMoved() + "-" + node.getMoveType());
        }
        String[][] boardString = boardCharToString(node);
        for (int j = 0; j < boardString.length; j++){
            for (int k = 0; k < boardString[0].length; k++){
                System.out.print(boardString[j][k]);
            }
            System.out.println();
        }
        printBoardHelper(node.getChildren().get(0), i+1);
    }

    private String[][] boardCharToString(Tree node){
        Coordinate exitPoint = new Coordinate(BoardState.exitRow, BoardState.exitCol);
        char[][] board = addExitPointInBoard(node.getState().getBoard(), exitPoint);
        String[][] boardString = new String[board.length][board[0].length];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                if (board[i][j] == 'K'){
                    boardString[i][j] = GREEN + String.valueOf(board[i][j]) + RESET;
                } else if (board[i][j] == 'P'){
                    boardString[i][j] = RED + String.valueOf(board[i][j]) + RESET;
                } else {
                boardString[i][j] = String.valueOf(board[i][j]);
                }
            }
        }
        char idMoved = node.getIdMoved();
        Piece piece = BoardState.pieces.get(idMoved);
        char moveType = node.getMoveType();
        int steps = node.getSteps();
        if (moveType == 'u'){
            int start = piece.upLeft.r;
            int end = piece.upLeft.r + steps + piece.length - 1;
            for (int i = start; i <= end; i++){
                boardString[i][piece.upLeft.c] = NAVY + YELLOW_BG + boardString[i][piece.upLeft.c] + RESET;
            }
        } else if (moveType == 'd'){
            int start = piece.upLeft.r - steps;
            int end = piece.upLeft.r + piece.length - 1;
            for (int i = start; i <= end; i++){
                boardString[i][piece.upLeft.c] = NAVY + YELLOW_BG + boardString[i][piece.upLeft.c] + RESET;
            }
        } else if (moveType == 'l'){
            int start = piece.upLeft.c;
            int end = piece.upLeft.c + steps + piece.length - 1;
            for (int i = start; i <= end; i++){
                boardString[piece.upLeft.r][i] = NAVY + YELLOW_BG + boardString[piece.upLeft.r][i] + RESET;
            }
        } else if (moveType == 'r'){
            int start = piece.upLeft.c - steps;
            int end = piece.upLeft.c + piece.length - 1;
            for (int i = start; i <= end; i++){
                boardString[piece.upLeft.r][i] = NAVY + YELLOW_BG + boardString[piece.upLeft.r][i] + RESET;
            }
        }
        return boardString;
    }

    private char[][] addExitPointInBoard(char[][] board, Coordinate exitPoint){
        int row = exitPoint.r;
        int col = exitPoint.c;
        System.out.println("exitPoint: " + row + " " + col);
        if (row == -1){
            char[][] newBoard = new char[BoardState.rows+1][BoardState.cols];
            for (int i = 0; i < BoardState.cols; i++){
                newBoard[0][i] = ' ';
            }
            newBoard[0][col] = 'K';
            for (int i = 1; i < BoardState.rows+1; i++){
                newBoard[i] = board[i-1];
            }
            printBoard(newBoard);
            return newBoard;
        } else if (col == -1){
            char[][] newBoard = new char[BoardState.rows][BoardState.cols+1];
            for (int i = 0; i < BoardState.rows; i++){
                newBoard[i][0] = ' ';
            }
            newBoard[row][0] = 'K';
            for (int i = 0; i < BoardState.rows; i++){
                for (int j = 1; j < BoardState.cols+1; j++){
                    newBoard[i][j] = board[i][j-1];
                }
            }
            return newBoard;
        } else if (row == BoardState.rows){
            char[][] newBoard = new char[BoardState.rows+1][BoardState.cols];
            for (int i = 0; i < BoardState.cols; i++){
                newBoard[BoardState.rows][i] = ' ';
            }
            newBoard[BoardState.rows][col] = 'K';
            for (int i = 0; i < BoardState.rows; i++){
                newBoard[i] = board[i];
            }
            return newBoard;
        } else if (col == BoardState.cols){
            char[][] newBoard = new char[BoardState.rows][BoardState.cols+1];
            for (int i = 0; i < BoardState.rows; i++){
                newBoard[i][BoardState.cols] = ' ';
            }
            newBoard[row][BoardState.cols] = 'K';
            for (int i = 0; i < BoardState.rows; i++){
                for (int j = 0; j < BoardState.cols; j++){
                    newBoard[i][j] = board[i][j];
                }
            }
            return newBoard;
        } 
        return board;
    }

    private void printBoard(char[][] board){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}
