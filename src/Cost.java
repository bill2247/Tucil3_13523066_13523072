public class Cost {
    public static int algorithm; // 1. UCS, 2.GBFS, 3.A*

    public Cost(int algorithm){
        Cost.algorithm = algorithm;
    }

    public static int f(BoardState state){
        // sesuaikan dengan algo
        return 0;
    }

    public static int g(BoardState state){
        
        return 0;
    }

    public static int h(BoardState state){
        int res = 0;
        Piece primPiece = BoardState.pieces.get('P');
        Coordinate primCoor = new Coordinate();
        if (state.piecesLocation.containsKey('P')){
            primCoor = state.piecesLocation.get('P');
        } else{
            return 0;
        }
        if(primCoor.x==-1 && primCoor.y==-1){
            return 0;
        }

        if(primPiece.getOrientation() == Orientation.HORIZONTAL){
            if(primCoor.x < BoardState.exitCol){ // exit di sebelah kanan
                for(int i=0;primCoor.x+primPiece.getLength()+i<BoardState.cols;i++){
                    char checkId = state.board.get(primCoor.y)[primCoor.x+primPiece.getLength()+i];
                    if(checkId!='.'){
                        res++;
                    }
                }
            } else if(primCoor.x > BoardState.exitCol){ // exit di sebelah kiri
                for(int i=0;primCoor.x-1-i>=0;i++){
                    char checkId = state.board.get(primCoor.y)[primCoor.x-1-i];
                    if(checkId!='.'){
                        res++;
                    }
                }
            }
        } else{
            if(primCoor.y < BoardState.exitRow){ // exit di sebelah atas
                for(int i=0;primCoor.y-1-i>=0;i++){
                    char checkId = state.board.get(primCoor.y-1-i)[primCoor.x];
                    if(checkId!='.'){
                        res++;
                    }
                }
            } else if(primCoor.y > BoardState.exitRow){ // exit di sebelah bawah
                for(int i=0;primCoor.y+primPiece.getLength()+i<BoardState.rows;i++){
                    char checkId = state.board.get(primCoor.y+primPiece.getLength()+i)[primCoor.x];
                    if(checkId!='.'){
                        res++;
                    }
                }
            }
        }

        return res;
    }
}
