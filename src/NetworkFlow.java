import java.util.*;

public class NetworkFlow {
  private static final int MAX_V = 40; 
  private static final int INF = 1000000000;
  

  private static int[][] adj = new int[MAX_V][]; 
  private static int maxFlow, flow, source, sink;
  private static ArrayList<Integer> parent = new ArrayList<>();
  
  public static int V;
  
  public static void augmentEdge(int v, int min) {
      if (v == source) {
          flow = min;
      } else if (parent.get(v) != -1) {
          augmentEdge(parent.get(v), Math.min(min, adj[parent.get(v)][v]));
          adj[parent.get(v)][v] -= flow;
          adj[v][parent.get(v)] += flow;
          /*
          augmentEdge(parent.get(v), Math.min(min, adjList.get(parent.get(v)).get(v).weight);
          adjList.get(parent.get(v)).get(v).weight -= flow;
          adjList.get(v).get(parent.get(v)).weight += flow;
          */
      }
  }

  
  public static int maxFlow() {
      maxFlow = 0;
    while (true) { 
      flow = 0;

      LinkedList<Integer> queue = new LinkedList<Integer>();
      ArrayList<Integer> distances = new ArrayList <Integer>();
      
      distances.addAll(Collections.nCopies(V, INF));
      
      queue.offer(source);
      distances.set(source, 0);
      
      parent.clear();
      parent.addAll(Collections.nCopies(V, -1));
      
      while (!queue.isEmpty()) {               
        int curr = queue.poll();
        if (curr == sink)
            break;
        for (int v = 0; v < MAX_V; v++) 
          if (adj[source][v] > 0 && distances.get(v) == INF) { 
            distances.set(v, distances.get(source) + 1);
            queue.offer(v);
            parent.set(v, curr); 
          }
      }
      
      augmentEdge(sink, INF);
      if (flow == 0)
          break; 
      maxFlow += flow;
    }
    return maxFlow;
  }
  
  public static HashMap<Integer, ArrayList<Edge>> adjList;
  
  public static class Edge {
      public int neighbor;
      public int weight;
      public Edge(int n, int w) {
          this.neighbor = n;
          this.weight = w;
      }
  }
  
  public static int minCostMaxFlow() {
      maxFlow = 0;
    while (true) { 
      flow = 0;

      ArrayList<Integer> distances = new ArrayList <Integer>();
      
      distances.addAll(Collections.nCopies(V, INF));
      
      distances.set(source, 0);
      
      parent.clear();
      parent.addAll(Collections.nCopies(V, -1));
      
      
      //bellman fords
      for (int i = 0; i < V - 1; i++) {
          for (int u = 0; u < V; u++) {
              for (int j = 0; j < adjList.get(u).size(); j++){
                  Edge e = adjList.get(u).get(j);
                  if (distances.get(e.neighbor) > distances.get(u) + e.weight) {
                      distances.set(e.neighbor, distances.get(u) + e.weight);
                      parent.set(e.neighbor, u);
                  }
              }
          }
      }
      
      augmentEdge(sink, INF);
      if (flow == 0)
          break; 
      maxFlow += flow;
    }
    return maxFlow;
  }
}
