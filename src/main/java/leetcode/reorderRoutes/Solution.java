package leetcode.reorderRoutes;
import java.util.*;
/*
https://leetcode.com/problems/reorder-routes-to-make-all-paths-lead-to-the-city-zero/description
Medium

There are n cities numbered from 0 to n - 1 and n - 1 roads such that there is only one way to travel between two different
cities (this network form a tree). Last year, The ministry of transport decided to orient the roads in one direction because 
they are too narrow.

Roads are represented by connections where connections[i] = [ai, bi] represents a road from city ai to city bi.

This year, there will be a big event in the capital (city 0), and many people want to travel to this city.

Your task consists of reorienting some roads such that each city can visit the city 0. Return the minimum number of edges 
changed.

It's guaranteed that each city can reach city 0 after reorder.

 */
public class Solution {
    int count = 0;

    public int minReorder(int n, int[][] connections) {
        // we can do a traversal of the tree as if each connection are bidirectional and count
        // the ones that need to be flipped
        // we can make a list of connections for each city where we use the sign of the value to indicate incoming (incoming
        // and negative for outgoing), then during dfs track visited and count the number of outgoing connections that need 
        // to be converted to incoming
        // array of n sets for connections, but sum of elements in the set is 2*(n-1) so space is O(n)
        // one pass to build it, one pass to dfs, O(n) time and O(n) for the recursion stack
        Map<Integer, Set<Integer>> cities = new HashMap<>();
        for (int k = 0; k < n-1; k++) {
            int i = connections[k][0];
            int j = connections[k][1];
            Set<Integer> c = cities.getOrDefault(i, new HashSet<>());
            c.add(-(j+1)); // i has outgoing road to j
            cities.put(i, c);
            c = cities.getOrDefault(j, new HashSet<>());
            c.add(i+1); // j has incoming road from i
            cities.put(j, c);
        }
        
        boolean[] visited = new boolean[n];
        dfs(cities, 0, visited);
        return count;
    }
    void dfs(Map<Integer, Set<Integer>> cities, int i, boolean[] visited)
     {
        visited[i] = true;
        for (int v : cities.get(i)) {
            boolean isOutgoing = (v < 0);
            int j = Math.abs(v)-1;
            if (!visited[j]) {
                // for outgoing connection, we need to reverse it, so increment the count
                if (isOutgoing) {
                    count++;
                }
                dfs(cities, j, visited);
            }
        }
     }
}
