import java.util.*;

public class Solver {
    public int algorithm; // 1. UCS, 2.GBFS, 3.A*
    public int count;

    public Tree solve(Tree t){
        PriorityQueue<Tree> pq = new PriorityQueue<>();
        Set<BoardState> visited = new HashSet<>();

        pq.add(t);
        count = 0;
        while(pq.isEmpty()==false){
            count++;
            Tree currentNode = pq.poll();
            if(currentNode.isGoal()){
                return currentNode;
            }

            visited.add(currentNode.getState());
            currentNode.generateChildren();
            for(Tree childNode : currentNode.getChildren()){
                if(visited.contains(childNode.getState())==false){
                    pq.add(childNode);
                }
            }
        }

        return null;
    }

    public static Tree generatePath(Tree goalNode){
        if (goalNode == null) {
            return null;
        }
        ArrayList<Tree> path = new ArrayList<>();
        Tree current = goalNode;
        while(current != null){
            path.add(current);
            current=current.getParent();
        }
        Collections.reverse(path);

        for(int i=0;i<path.size()-1;i++){
            ArrayList<Tree> child = new ArrayList<>();
            child.add(path.get(i+1));
            path.get(i).setChildren(child);
        }
        ArrayList<Tree> nochild = new ArrayList<>();
        path.get(path.size()-1).setChildren(nochild);
        return path.get(0);
    }
}
