import java.util.*;

public class Tree implements Comparable<Tree>{
    private BoardState state;
    private ArrayList<Tree> children;
    private char moveType; // u r d l
    private int fn;
    private Tree parent;
    private int steps;
    private char idMoved;

    // konstruktor
    Tree(BoardState state){
        this.state = state;
        this.children = new ArrayList<Tree>();
        this.moveType = ' ';
        this.fn = Cost.f(state);
        this.parent = null;
    }

    //getter
    public BoardState getState(){return this.state;}
    public ArrayList<Tree> getChildren(){return this.children;}
    public Tree getParent(){return this.parent;}
    public int getFn(){return this.fn;}
    public char getMoveType(){return this.moveType;}

    // setter
    public void updateChildren(BoardState state, int fn, char mt){
        Tree child = new Tree(state);
        child.fn = fn;
        child.moveType = mt;
        child.parent = this;
        this.children.add(child);
    }

    @Override
    public int compareTo(Tree other){
        return Integer.compare(this.fn, other.fn);
    }

    public boolean isGoal(){
        return (state.getPiecesLocation().get('P').r == -1 && state.getPiecesLocation().get('P').c == -1);
    }

    public void generateChildren(){
        Map<Character, ArrayList<Coordinate>> legalMoves = state.generateLegalMoves();
        for(Map.Entry<Character, ArrayList<Coordinate>> entry : legalMoves.entrySet()){
            char currId = entry.getKey();            
            ArrayList<Coordinate> currCoordinates = entry.getValue();

            for(int i=0;i<currCoordinates.size();i++){
                int r = currCoordinates.get(i).c;
                int c = currCoordinates.get(i).r;
                BoardState newState = state.cloneBoardState();

                // cek tipe gerakan
                char mt = ' ';
                int oldR = state.getPiecesLocation().get(currId).c;
                int oldC = state.getPiecesLocation().get(currId).r;
                if(r>oldR){mt = 'd';}
                else if(r<oldR){mt = 'u';}
                else if(c<oldC){mt = 'l';}
                else if(c>oldC){mt = 'r';}

                // movement
                newState.deletePiece(currId);
                newState.addPiece(currId, r, c);
                int fn = Cost.f(newState);
                updateChildren(newState, fn, mt);
            }
        }
    }
    // getter idMoved
    public char getIdMoved(){
        return this.idMoved;
    }
    // setter idMoved
    public void setIdMoved(char idMoved){
        this.idMoved = idMoved;
    }
    // getter steps
    public int getSteps(){
        return this.steps;
    }
    // setter steps
    public void setSteps(int steps){
        this.steps = steps;
    }
}
