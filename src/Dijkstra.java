import java.util.*;


public class Dijkstra {
    
    public static Integer[] dijkstra(int[][] adj, int source) {
        //O(n log n) with priority queue
        int n = adj.length;
        
        Integer[] dist = new Integer[n];
        boolean[] visited = new boolean[n];
       // Integer[] prev = new Integer[n];
        
        PriorityQueue<Node> pq = new PriorityQueue<>(n, new NodeComparator());
        
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
          //  prev[i] = -1;
            //pq.add(new Node(i, Integer.MAX_VALUE));
        }
        
        dist[source] = 0;
        pq.add(new Node(source, 0));
        
        while (!pq.isEmpty()) {
            Node min = pq.poll();
           // System.out.println("Looking at node " + min.node);
            visited[min.node] = true;
            if (dist[min.node] == Integer.MAX_VALUE) break;
            
            for (int j = 0; j < n; j++) {
                if (adj[min.node][j] != 0) {
                    int possibleDist = dist[min.node] + adj[min.node][j];
                    if (possibleDist < dist[j]) {
                        dist[j] = possibleDist;
                        //prev[j] = min.node;
                        if (!visited[j])
                            pq.add(new Node(j, possibleDist));
                    }
                }
            }
        }
        
        return dist;
    }


    private static Integer[] dijkn2(int[][] adj, int source) {
        //naive, O(n^2) implementation
        int n = adj.length;
        
        boolean[] visited = new boolean[n];
        Integer[] distance = new Integer[n];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        for (int ia = 0; ia < n; ia++) {
            int curr = Integer.MAX_VALUE;//find shortest non-visited distance
            int index = 0;
            for (int i = 0; i < distance.length; i++) {
                if (distance[i] < curr && !visited[i]) {
                    curr = distance[i];
                    index = i;
                }
            }
            int[] adjToCurr = new int[n]; //INDEX, DISTANCE to curr
            for (int j = 0; j < adj[index].length; j++) {//find all adjacent
                if (adj[index][j] != 0) {
                    adjToCurr[j] = adj[index][j];
                }
            }

            for (int b = 0; b < adjToCurr.length; b++) {//set to min
                if (adjToCurr[b] != 0) {
                    int idx = b;
                    int dist = adjToCurr[b];
                    distance[idx] = Math.min(distance[idx], dist + distance[index]);
                }
            }

            visited[index] = true;
        }

        return distance;
    }
    
    
    
    private static class Node {

        public int node;
        public int cost;

        public Node(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

    }

    private static class NodeComparator implements Comparator<Node> {


        @Override
        public int compare(Node node1, Node node2) {
            return node1.cost - node2.cost;
        }
    }
}
