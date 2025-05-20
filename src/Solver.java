import java.util.*;
// import java.util.function.*;

public class Solver {
    public int algorithm; // 1. UCS, 2.GBFS, 3.A*

    public Tree solve(Tree t){
        PriorityQueue<Tree> pq = new PriorityQueue<>();
        Set<Tree> visited = new HashSet<>();

        pq.add(t);
        while(pq.isEmpty()==false){
            // -------------- debug --------------
            System.out.println("kusanagi1");
            for(Character key : t.getState().getPiecesLocation().keySet()){
               System.out.println(key);
            }
            System.out.println("kusanagi2");
            System.exit(0);
            Tree currentNode = pq.poll();
            if(currentNode.isGoal()){
                return currentNode;
            }

            visited.add(currentNode);
            currentNode.generateChildren();
            for(Tree childNode : currentNode.getChildren()){
                if(visited.contains(childNode)==false){
                    pq.add(childNode);
                }
            }
        }

        return null;
    }

    public static ArrayList<Tree> generatePath(Tree goalNode){
        ArrayList<Tree> path = new ArrayList<>();
        Tree current = goalNode;
        while(current != null){
            path.add(current);
            current=current.getParent();
        }
        Collections.reverse(path);
        return path;
    }
}
