package leetcode.minTravelDist;

import java.util.*;

/*
https://leetcode.com/problems/shortest-distance-from-all-buildings/
Hard

 You are given an m x n grid grid of values 0, 1, or 2, where:

    each 0 marks an empty land that you can pass by freely,
    each 1 marks a building that you cannot pass through, and
    each 2 marks an obstacle that you cannot pass through.

You want to build a house on an empty land that reaches all buildings in the shortest total travel distance. 
You can only move up, down, left, and right.

Return the shortest travel distance for such a house. If it is not possible to build such a house according 
to the above rules, return -1.

The total travel distance is the sum of the distances between the houses of the friends and the meeting point.
 */
public class Solution {

    // The idea is to perform a BFS simultaneously starting from each building.
    // For an empty cell, record the current dist to reach that cell from building i.
    // Option: for k buildings, maintain an array of k values per grid cell
    // After BFS, we find the cell with the minimum sum of k distances.
    // Optimize - instead of storing each of k distances, we only need to
    public int shortestDistance(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        List<Integer[]> buildings = findBuildings(grid);

        int numBuildings = buildings.size();
        // compute the travel distance from each building to each node i,j - it will be
        // 0 for non-reachable
        int[][] adj = new int[][] {
                // left, up, right, down
                { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 }
        };
        int[][][] travelDist = new int[m][n][numBuildings];
        for (int k = 0; k < numBuildings; k++) {

            // BFS search to fill in the minimumm travelDist[][][k] value for every empty
            // node in the grid
            Deque<Integer[]> q = new LinkedList<>();
            q.offerLast(buildings.get(k));
            int dist = 1;
            while (!q.isEmpty()) {
                // process all the nodes at this level
                for (int l = q.size(); l > 0; l--) {
                    Integer[] node = q.pollFirst();

                    // for each adjacent node in the bounds, set the travel distance
                    // from building k to that node if it hasn't already been set
                    for (int e = 0; e < 4; e++) {
                        int u = node[0] + adj[e][0];
                        int v = node[1] + adj[e][1];
                        if (u >= 0 && u < m && v >= 0 && v < n) {
                            // if adjacent node is not yet set, then mark the distance and it to the queue
                            if (grid[u][v] == 0 && travelDist[u][v][k] == 0) {
                                travelDist[u][v][k] = dist;
                                q.offerLast(new Integer[] { u, v });
                            }
                        }
                    }
                }
                dist++;
            }
        }

        // compute the travel distance to buildings by summing the distance for all buildings
        // exclude nodes that are not empty or from which all buildings are not reachable
        int minsum = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    // System.out.println(String.format("%d %d %s", i, j,
                    // Arrays.toString(travelDist[i][j])));
                    minsum = Math.min(minsum, sumdist(travelDist[i][j]));
                }
            }
        }
        return minsum < Integer.MAX_VALUE ? minsum : -1;
    }

    List<Integer[]> findBuildings(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        List<Integer[]> buildings = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    buildings.add(new Integer[] { i, j });
                }
            }
        }
        return buildings;
    }

    int sumdist(int[] distances) {
        int sum = 0;
        for (int k = 0; k < distances.length; k++) {
            if (distances[k] == 0) {
                return Integer.MAX_VALUE;
            }
            sum += distances[k];
        }
        return sum;
    }

    public static void main(String[] args) {
        int[][] g = TestCases.G1;
        System.out.println(new Solution().shortestDistance(g));

        // grid with unreachable buildings
        g = TestCases.G2;
        System.out.println(new Solution().shortestDistance(g));

        // large grid with unreachable buildings
        g = TestCases.G3;
        System.out.println(new Solution().shortestDistance(g));
    }
}