// TC and SC: O(V + E)
// only for DFS TC cannot go beyond V. (all nodes in stack)
class Solution {
    int time;
    List<List<Integer>> result;
    List<List<Integer>> graph;
    int discovery[];
    int lowest[];
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        // discovery - when did i first discover a server or node.- we do not change this at all after first discovery
        // lowest time i can reach that node. for nodes connected in cycle, they all had same value.
        if (n == 0) {
            return new ArrayList<>();
        }

        discovery = new int[n];
        lowest = new int[n];
        Arrays.fill(discovery, -1);
        Arrays.fill(lowest, -1);
        result = new ArrayList<>();
        graph = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            graph.add(new ArrayList<>());
        }

        buildGraph(connections);
        dfs(0, -1);
        return result;
    }

    private void buildGraph(List<List<Integer>> connections) {
        for (int i = 0; i < connections.size(); ++i) {
            int from = connections.get(i).get(0);
            int to = connections.get(i).get(1);
            graph.get(from).add(to);
            graph.get(to).add(from);
        }
    }

    private void dfs(int v, int u) {
        if (discovery[v] != -1) {
            return;
        }
        discovery[v] = time;
        lowest[v] = time;
        time++;

        for (int n : graph.get(v)) {
            if (n == u) {
                continue;
            }

            dfs(n, v);

            if (lowest[n] > discovery[v]) {
                result.add(Arrays.asList(n, v));
            }

            lowest[v] = Math.min(lowest[v], lowest[n]);
        }
    }
}