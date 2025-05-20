import java.util.Map;

public class Cost {
    public static int algorithm; // 1. UCS, 2.GBFS, 3.A*

    public Cost(int algorithm){
        Cost.algorithm = algorithm;
    }

    public static int f(BoardState state){
        // sesuaikan dengan algo
        return 0;
    }

    public static int g(BoardState stateBefore, BoardState stateAfter){
        /***
         * definisi g(n)
         * g(n) = G(n-1) - G(n)
         * dengan G(n) adalah fungsi pembantu
         * G(n) = jumlah mobil di depan atau di belakang mobil x yang berbeda orientasi dengan mobil x, x adalah seluruh mobil yang ada
         */
        Map<Character, Piece> pieces = BoardState.pieces;
        int GnBefore = 0;
        int GnAfter = 0;
        Map<Character, Coordinate> piecesLocationBefore = stateBefore.piecesLocation;
        Map<Character, Coordinate> piecesLocationAfter = stateAfter.piecesLocation;
        for (Map.Entry<Character, Piece> entry : pieces.entrySet()){
            char id1 = entry.getKey();
            Piece piece1 = entry.getValue();
            // mencari GnBefore.  cek piecesLocationBefore apakah ada piece yang berbeda orientasi dengan piece.id, hitung jumlahnya
            for (Map.Entry<Character, Coordinate> entryBefore : piecesLocationBefore.entrySet()){
                char id2 = entryBefore.getKey();
                Piece piece2 = pieces.get(id2);
                if (piece1.getOrientation() != piece2.getOrientation()){
                    Coordinate coor1 = entryBefore.getValue();
                    Coordinate coor2 = piecesLocationBefore.get(id1);
                    int len2 = piece2.getLength();
                    if (piece2.getOrientation() == Orientation.HORIZONTAL){
                        int coor1y = coor1.c;
                        for (int i = 0; i < len2; i++){
                            int coor2y = coor2.c + i;
                            if (coor1y == coor2y){
                                GnBefore++;
                            }
                        }
                    } else {
                        int coor1x = coor1.r;
                        for (int i = 0; i < len2; i++){
                            int coor2x = coor2.r + i;
                            if (coor1x == coor2x){
                                GnBefore++;
                            }
                        }
                    }
                }
            }
            // mencari GnAfter.  cek piecesLocationAfter apakah ada piece yang berbeda orientasi dengan piece.id, hitung jumlahnya
            for (Map.Entry<Character, Coordinate> entryAfter : piecesLocationAfter.entrySet()){
                char id2 = entryAfter.getKey();
                Piece piece2 = pieces.get(id2);
                if (piece1.getOrientation() != piece2.getOrientation()){
                    Coordinate coor1 = entryAfter.getValue();
                    Coordinate coor2 = piecesLocationAfter.get(id1);
                    int len2 = piece2.getLength();
                    if (piece2.getOrientation() == Orientation.HORIZONTAL){
                        int coor1y = coor1.c;
                        for (int i = 0; i < len2; i++){
                            int coor2y = coor2.c + i;
                            if (coor1y == coor2y){
                                GnAfter++;
                            }
                        }
                    } else {
                        int coor1x = coor1.r;
                        for (int i = 0; i < len2; i++){
                            int coor2x = coor2.r + i;
                            if (coor1x == coor2x){
                                GnAfter++;
                            }
                        }
                    }
                }
            }
        }

        int gn = GnBefore - GnAfter;

        return gn;
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
        if(primCoor.r==-1 && primCoor.c==-1){
            return 0;
        }

        if(primPiece.getOrientation() == Orientation.HORIZONTAL){
            if(primCoor.c < BoardState.exitCol){ // exit di sebelah kanan
                for(int i=0;primCoor.c+primPiece.getLength()+i<BoardState.cols;i++){
                    char checkId = state.getBoard()[primCoor.r][primCoor.c+primPiece.getLength()+i];
                    if(checkId!='.'){
                        res++;
                    }
                }
            } else if(primCoor.c > BoardState.exitCol){ // exit di sebelah kiri
                for(int i=0;primCoor.c-1-i>=0;i++){
                    char checkId = state.getBoard()[primCoor.r][primCoor.c-1-i];
                    if(checkId!='.'){
                        res++;
                    }
                }
            }
        } else{
            if(primCoor.r < BoardState.exitRow){ // exit di sebelah atas
                for(int i=0;primCoor.r-1-i>=0;i++){
                    char checkId = state.getBoard()[primCoor.r-1-i][primCoor.c];
                    if(checkId!='.'){
                        res++;
                    }
                }
            } else if(primCoor.r > BoardState.exitRow){ // exit di sebelah bawah
                for(int i=0;primCoor.r+primPiece.getLength()+i<BoardState.rows;i++){
                    char checkId = state.getBoard()[primCoor.r+primPiece.getLength()+i][primCoor.c];
                    if(checkId!='.'){
                        res++;
                    }
                }
            }
        }

        return res;
    }
}
