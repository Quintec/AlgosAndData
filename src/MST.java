
import java.util.*;

public class MST {
    
     public static int prim(int n, int[][] adj) {
        //init
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        boolean[] used = new boolean[n];
        int[] parent = new int[n];
        
        //first node
        int size = 1;
        int cost = 0;
        used[0] = true;
        
        int minNode = -1;
        int minDist = Integer.MAX_VALUE;
        
        for (int i = 0; i < n; i++) {
            dist[i] = adj[0][i];
            parent[i] = 0;
            
            //save time out of loop - find min node
            if (dist[i] < minDist && i != 0) {
                minDist = dist[i];
                minNode = i;
            }
        }
        
        //add to tree
        while (size < n) {
            size++;
            cost += dist[minNode];
            used[minNode] = true;
            
            for (int i = 0; i < n; i++) {
                if (dist[i] > adj[i][minNode]) {
                    dist[i] = adj[i][minNode];
                    parent[i] = minNode;
                }
            }
            
            minDist = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (dist[i] < minDist && !used[i]) {
                    minDist = dist[i];
                    minNode = i;
                }
            }
        }
        
        return cost;
    }
    
}
