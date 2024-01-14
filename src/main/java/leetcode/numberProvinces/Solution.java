package leetcode.numberProvinces;
import java.util.*;

/*
 * https://leetcode.com/problems/number-of-provinces/description/?envType=study-plan-v2&envId=leetcode-75
 * Medium
 * 
There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b, and city b 
is connected directly with city c, then city a is connected indirectly with city c.

A province is a group of directly or indirectly connected cities and no other cities outside of the group.

You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly 
connected, and isConnected[i][j] = 0 otherwise.

Return the total number of provinces.
 */
public class Solution {
    static Set<Integer> EMPTY = new HashSet<>();
    public int findCircleNum(int[][] isConnected) {
        // We can do a depth-first search starting from each of the n cities and delete the connection
        // as we go and track the visited cities. Count each time we start a new search on an unvisited city
        // and that is the number of provinces.
        // Note, we need map with each city list of connections.
        // Note: we only need to traverse half (triangle) of the input array.
        int n = isConnected.length;
        // build a HashMap for the connections
        Map<Integer, Set<Integer>> connect = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    Set<Integer> c = connect.getOrDefault(i, new HashSet<>());
                    c.add(j);
                    connect.put(i, c);
                    c = connect.getOrDefault(j, new HashSet<>());
                    c.add(i);
                    connect.put(j, c);
                }
            }
        }

        
        int[] visited = new int[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i] != 1) {
                count++;
                visited[i] = 1;
                dfs_visit(connect, i, visited);
            }
        }

        return count;
    }
    void dfs_visit(Map<Integer, Set<Integer>> connect, int i, int[] visited) {
        Set<Integer> iCities = connect.getOrDefault(i, EMPTY);
        while (!iCities.isEmpty()) {
            int j = iCities.iterator().next();
            visited[j] = 1;
            iCities.remove(j);
            connect.get(j).remove(i);
            dfs_visit(connect, j, visited);
        }
    }
}