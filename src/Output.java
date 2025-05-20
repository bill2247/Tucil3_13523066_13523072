import java.util.*;

public class Output {
    private Tree root;
    private char exitPosition; // 'u' 'd' 'l' 'r'
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String NAVY = "\u001B[34m";
    public static final String YELLOW_BG = "\u001B[43m";

    
    public Output(Tree root){
        this.root = root;
        this.exitPosition = setExitPosition();
    }

    private char setExitPosition(){
        int row = BoardState.exitRow;
        int col = BoardState.exitCol;
        if (row == -1){
            return 'u';
        } else if (row == BoardState.rows){
            return 'd';
        } else if (col == -1){
            return 'l';
        } else if (col == BoardState.cols){
            return 'r';
        }
        return ' ';
    }

    public void printBoard(){
        if (root == null){
            System.out.println("Tidak ditemukan solusi!");
            return;
        }
        if (root.getParent() == null){
            printRootNode(root);
        } else {
            System.out.println("Papan Awal");
        }
        printBoardHelper(root.getChildren().get(0), 1);
    }

    private void printBoardHelper(Tree node, int i){
        if (node == null){
            return;
        }
        
        Coordinate primaryPieceCurrent = node.getState().getPiecesLocation().get('P');
        if (primaryPieceCurrent.r == -1 && primaryPieceCurrent.c == -1){
            node.getState().getPiecesLocation().remove('P');
            
            System.out.println();
            System.out.println("Gerakan " + i + ": Primary piece bergerak keluar dari papan!");
            char[][] boardString = addExitPointInBoard(node.getState().getBoard(), new Coordinate(BoardState.exitRow, BoardState.exitCol));
            for (int j = 0; j < boardString.length; j++){
                for (int k = 0; k < boardString[0].length; k++){
                    System.out.print(boardString[j][k]);
                }
                System.out.println();
            }
            return;
        }
        System.out.println("Gerakan " + i + ": " + node.getIdMoved() + "-" + node.getMoveType());
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
        Coordinate piece = node.getState().getPiecesLocation().get(idMoved);
        int length = BoardState.pieces.get(idMoved).getLength();
        char moveType = node.getMoveType();
        int steps = node.getSteps();
        if (exitPosition == 'l'){
            piece.c = piece.c + 1;
        }
        if (exitPosition == 'u'){
            piece.r = piece.r + 1;
        }
        if (moveType == 'u'){
            int start = piece.r;
            int end = piece.r + steps + length - 1;
            for (int i = start; i <= end; i++){
                boardString[i][piece.c] = NAVY + YELLOW_BG + boardString[i][piece.c] + RESET;
            }
        } else if (moveType == 'd'){
            int start = piece.r - steps;
            int end = piece.r + length - 1;
            for (int i = start; i <= end; i++){
                boardString[i][piece.c] = NAVY + YELLOW_BG + boardString[i][piece.c] + RESET;
            }
        } else if (moveType == 'l'){
            int start = piece.c;
            int end = piece.c + steps + length - 1;
            for (int i = start; i <= end; i++){
                boardString[piece.r][i] = NAVY + YELLOW_BG + boardString[piece.r][i] + RESET;
            }
        } else if (moveType == 'r'){
            System.out.println("piece.c: " + piece.c);
            int start = piece.c - steps;
            int end = piece.c + length - 1;
            for (int i = start; i <= end; i++){
                boardString[piece.r][i] = NAVY + YELLOW_BG + boardString[piece.r][i] + RESET;
            }
        }
        System.out.println();
        return boardString;
    }

    private void printRootNode(Tree node){
        if (node.getParent() == null){
            System.out.println("Papan Awal");
            char[][] board = node.getState().getBoard();
            board = addExitPointInBoard(board, new Coordinate(BoardState.exitRow, BoardState.exitCol));
            for (int i = 0; i < board.length; i++){
                for (int j = 0; j < board[0].length; j++){
                    System.out.print(board[i][j]);
                }
                System.out.println();
            }
        }
    }

    private char[][] addExitPointInBoard(char[][] board, Coordinate exitPoint){
        int row = exitPoint.r;
        int col = exitPoint.c;
        if (row == -1){
            char[][] newBoard = new char[BoardState.rows+1][BoardState.cols];
            for (int i = 0; i < BoardState.cols; i++){
                newBoard[0][i] = ' ';
            }
            newBoard[0][col] = 'K';
            for (int i = 1; i < BoardState.rows+1; i++){
                newBoard[i] = board[i-1];
            }
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
}
