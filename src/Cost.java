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
                        int coor1y = coor1.y;
                        for (int i = 0; i < len2; i++){
                            int coor2y = coor2.y + i;
                            if (coor1y == coor2y){
                                GnBefore++;
                            }
                        }
                    } else {
                        int coor1x = coor1.x;
                        for (int i = 0; i < len2; i++){
                            int coor2x = coor2.x + i;
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
                        int coor1y = coor1.y;
                        for (int i = 0; i < len2; i++){
                            int coor2y = coor2.y + i;
                            if (coor1y == coor2y){
                                GnAfter++;
                            }
                        }
                    } else {
                        int coor1x = coor1.x;
                        for (int i = 0; i < len2; i++){
                            int coor2x = coor2.x + i;
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
        // ongoing, ghif

        return res;
    }
}
