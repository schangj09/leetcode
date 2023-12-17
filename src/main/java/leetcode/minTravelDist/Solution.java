package leetcode.minTravelDist;

import java.util.*;

/*
 You are given an m x n grid grid of values 0, 1, or 2, where:

    each 0 marks an empty land that you can pass by freely,
    each 1 marks a building that you cannot pass through, and
    each 2 marks an obstacle that you cannot pass through.

You want to build a house on an empty land that reaches all buildings in the shortest total travel distance. You can only move up, down, left, and right.

Return the shortest travel distance for such a house. If it is not possible to build such a house according to the above rules, return -1.

The total travel distance is the sum of the distances between the houses of the friends and the meeting point.
 */
public class Solution {
    public int shortestDistance(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        List<Integer[]> buildings = findBuildings(grid);

        int numBuildings = buildings.size();
        // compute the travel distance from each building to each node i,j - it will be
        // 0 for non-reachable
        int[][][] travelDist = new int[m][n][numBuildings];
        for (int k = 0; k < numBuildings; k++) {
            Integer[] building = buildings.get(k);
            boolean[][] visited = new boolean[m][n];

            // filling in the minimumm travelDist[][][k] value for every empty node in the
            // grid
            // computeDist(building, grid, visited, travelDist);

            int[][] adj = new int[][] {
                    // left, up, right, down
                    { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 }
            };
            Deque<Integer[]> q = new LinkedList<>();
            q.offerLast(building);
            while (!q.isEmpty()) {
                Integer[] node = q.pollFirst();
                // mark as visited so we don't come back to it
                visited[node[0]][node[1]] = true;
                // only proceed to adjacents for empty nodes - skip this node if it is a
                // building or an obstacle
                if (grid[node[0]][node[1]] == 0 || (node == building)) {
                    // for each adjacent node in the bounds, compute the minimum travel distance
                    // from building k to that node
                    for (int e = 0; e < 4; e++) {
                        int u = node[0] + adj[e][0];
                        int v = node[1] + adj[e][1];
                        if (u >= 0 && u < m && v >= 0 && v < n) {
                            // ok we have an adjacent node, if it is an empty lot, then the distance is
                            // this node min dist + 1, unless it already has a lower minimum travel dist
                            // System.out.println(String.format("%d, %d, %d, %d, %s", node[0], node[1], u,
                            // v, travelDist[u][v][k]));
                            if (grid[u][v] == 0) {
                                int minDist = travelDist[node[0]][node[1]][k] + 1;
                                if (travelDist[u][v][k] == 0) {
                                    travelDist[u][v][k] = minDist;
                                } else {
                                    travelDist[u][v][k] = Math.min(travelDist[u][v][k], minDist);
                                }
                            }
                            // System.out.println(String.format("%d, %d, %d, %d, %s", node[0], node[1], u,
                            // v, travelDist[u][v][k]));
                            // if adjacent node is not yet visited, then add it to the queue
                            if (!visited[u][v]) {
                                q.offerLast(new Integer[] { u, v });
                            }
                        }
                    }
                }
            }
        }

        // compute the travel distance to buildings by summing the distance for all
        // buildings
        // exclude nodes that are not empty or from which all buildings are not
        // reachable
        int minsum = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    minsum = Math.min(minsum, sumdist(travelDist[i][j]));
                }
            }
        }
        return minsum < Integer.MAX_VALUE ? minsum : 0;
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
        int[][] g = new int[][] { { 1, 0, 2, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0 } };
        System.out.println(new Solution().shortestDistance(g));

        // grid with unreachable buildings
        g = new int[][] { { 1, 0, 2, 0, 1 }, { 0, 2, 0, 0, 0 }, { 2, 0, 1, 0, 0 } };
        System.out.println(new Solution().shortestDistance(g));

        g = new int[][]

        { { 2, 2, 2, 2, 0, 2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 1, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0,
                0, 2, 2, 2, 0, 2, 0, 0 },
                { 0, 0, 2, 2, 0, 0, 0, 2, 0, 0, 2, 2, 0, 0, 0, 0, 2, 0, 2, 1, 0, 2, 0, 1, 2, 2, 2, 0, 0, 2, 0, 2, 2, 0,
                        0, 0, 0, 2, 2, 0, 2, 1, 2, 0 },
                { 1, 0, 0, 0, 0, 2, 2, 0, 0, 2, 0, 0, 1, 0, 0, 2, 0, 0, 2, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 2,
                        0, 2, 2, 2, 0, 2, 0, 2, 2, 0 },
                { 1, 0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0,
                        0, 0, 2, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 1, 0, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0,
                        2, 0, 2, 0, 1, 0, 2, 2, 0, 2 },
                { 2, 0, 0, 0, 0, 2, 0, 0, 2, 2, 2, 2, 0, 0, 2, 2, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 2, 0, 2, 0, 1, 2,
                        0, 2, 2, 0, 0, 0, 2, 0, 0, 2 },
                { 0, 0, 0, 2, 2, 2, 0, 2, 0, 0, 0, 2, 0, 0, 2, 0, 2, 0, 2, 0, 0, 2, 0, 2, 0, 1, 0, 2, 2, 0, 0, 2, 0, 0,
                        0, 2, 2, 2, 2, 0, 0, 0, 2, 0 },
                { 2, 0, 0, 2, 0, 2, 2, 2, 2, 0, 0, 2, 2, 0, 2, 0, 0, 0, 1, 2, 0, 2, 2, 0, 2, 0, 2, 1, 2, 0, 1, 0, 2, 1,
                        2, 0, 0, 0, 0, 0, 0, 0, 2, 2 },
                { 0, 0, 2, 2, 0, 2, 0, 0, 0, 2, 0, 2, 0, 2, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 1, 2, 0, 0, 0, 2,
                        1, 0, 2, 2, 2, 0, 0, 0, 2, 0 },
                { 2, 0, 0, 0, 0, 2, 0, 0, 2, 2, 2, 2, 0, 2, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 1, 0, 0, 2, 2, 2,
                        1, 2, 2, 0, 0, 0, 0, 0, 2, 2 },
                { 0, 1, 0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 2, 0, 2, 2, 2, 2, 0, 0, 2, 2, 2, 0, 2, 0, 2, 2, 2,
                        0, 0, 2, 0, 0, 0, 0, 0, 2, 2 },
                { 0, 2, 0, 0, 0, 2, 0, 2, 2, 2, 0, 0, 1, 2, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0,
                        0, 0, 0, 0, 0, 0, 2, 0, 0, 2 },
                { 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 2, 0, 2, 2, 2, 0, 2, 0, 2, 0, 2, 2, 0, 2, 0, 0, 0, 2, 0, 0, 2, 2, 2,
                        2, 2, 0, 0, 0, 2, 0, 2, 2, 0 },
                { 0, 0, 2, 0, 2, 2, 0, 0, 0, 0, 0, 0, 2, 2, 0, 2, 0, 2, 2, 0, 0, 0, 0, 0, 2, 2, 1, 0, 0, 1, 2, 0, 2, 0,
                        0, 0, 0, 2, 0, 2, 0, 2, 0, 0 },
                { 0, 2, 2, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 2, 2, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0,
                        2, 0, 0, 0, 0, 2, 0, 2, 0, 0 },
                { 0, 0, 0, 0, 0, 2, 2, 0, 2, 2, 0, 0, 0, 2, 2, 2, 0, 2, 2, 0, 2, 0, 2, 0, 2, 2, 0, 1, 0, 2, 0, 2, 0, 0,
                        2, 0, 0, 0, 0, 0, 0, 2, 0, 2 },
                { 2, 2, 0, 2, 0, 0, 1, 2, 0, 1, 0, 2, 0, 0, 2, 0, 1, 2, 1, 2, 2, 0, 2, 0, 0, 0, 0, 2, 2, 2, 0, 2, 0, 2,
                        0, 2, 0, 0, 0, 0, 0, 0, 0, 2 },
                { 0, 2, 2, 0, 2, 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 2, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 2,
                        0, 0, 0, 0, 0, 2, 2, 0, 2, 2 },
                { 0, 2, 2, 0, 0, 2, 2, 0, 2, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 2, 2, 2, 1, 0, 2, 2, 0, 2, 0, 0, 2, 0, 0, 0,
                        1, 2, 0, 2, 2, 0, 0, 0, 0, 0 },
                { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 2, 2, 0, 0, 0, 2, 2, 1, 0, 0, 2, 2, 0, 2, 0, 2, 0,
                        1, 0, 2, 0, 0, 0, 0, 2, 0, 2 },
                { 0, 0, 2, 2, 0, 0, 2, 0, 2, 0, 2, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0,
                        2, 0, 2, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 2, 2, 2, 2, 0, 0, 2, 0, 0, 2, 2, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 2, 0, 1, 2 },
                { 2, 2, 2, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 2, 2, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 2, 2, 0, 0, 0, 2, 2, 0,
                        0, 2, 0, 0, 2, 0, 2, 2, 0, 2 },
                { 2, 0, 0, 2, 2, 2, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 1, 0, 1, 0, 0, 2, 0,
                        2, 0, 2, 0, 2, 1, 0, 2, 0, 0 },
                { 2, 0, 0, 2, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 2, 2, 0, 0, 2, 0, 0, 2, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 0,
                        2, 0, 0, 2, 2, 1, 0, 0, 0, 2 },
                { 1, 0, 2, 0, 2, 0, 0, 2, 0, 0, 0, 2, 0, 1, 0, 0, 2, 2, 0, 0, 2, 0, 2, 0, 2, 2, 0, 0, 0, 2, 0, 0, 0, 0,
                        2, 2, 2, 2, 0, 2, 2, 2, 0, 2 },
                { 0, 0, 0, 0, 2, 2, 2, 0, 0, 1, 0, 2, 2, 2, 0, 0, 0, 1, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0,
                        0, 0, 2, 0, 0, 1, 0, 0, 0, 2 },
                { 0, 2, 2, 0, 2, 0, 2, 1, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 2, 2, 2, 0, 0, 2, 0, 2,
                        0, 2, 0, 0, 0, 2, 0, 0, 0, 0 },
                { 0, 2, 2, 0, 2, 2, 0, 2, 2, 0, 0, 0, 1, 2, 2, 0, 0, 2, 0, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 0, 0, 0, 2, 2,
                        0, 0, 0, 0, 2, 2, 0, 2, 2, 2 },
                { 0, 0, 2, 0, 0, 0, 1, 2, 0, 2, 2, 2, 0, 0, 2, 2, 0, 0, 0, 2, 0, 0, 2, 0, 0, 2, 2, 2, 0, 2, 2, 0, 0, 0,
                        0, 2, 0, 0, 0, 0, 0, 0, 2, 2 } };
        System.out.println(new Solution().shortestDistance(g));
    }
}