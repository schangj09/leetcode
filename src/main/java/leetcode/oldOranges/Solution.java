package leetcode.oldOranges;
import java.util.*;
/*
 * https://leetcode.com/problems/rotting-oranges/description/
 * Medium
 * 
You are given an m x n grid where each cell can have one of three values:

    0 representing an empty cell,
    1 representing a fresh orange, or
    2 representing a rotten orange.

Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return

 */
public class Solution {
    public int orangesRotting(int[][] grid) {
        // proposal - do a BFS starting with all of the rotten oranges, where we change fresh to rotten on each
        // visited node. Track the maximum depth reached during the search.
        // Finally check if there are any fresh oranges left at the end.

        // the queue contains 3-tuple of row, col, depth and only contains rotten oranges.
        // (always change fresh to rotten before adding to the queue)
        Queue<int[]> q = new LinkedList<>();
        int maxDepth = 0;
        // add all initial rotten oranges at depth 0
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 2) {
                    q.offer(new int[] {r, c, 0});
                }
            }
        }

        int[][] dir = // right, left, up, down
        new int[][] { 
            {0,1}, {0,-1}, {-1,0}, {1,0}
        };

        while (!q.isEmpty()) {
            int[] curr = q.poll(); // current rotten orange
            int r = curr[0];
            int c = curr[1];
            int d = curr[2];

            // for each direction, check if the adjacent is fresh. If so, mark it rotten, add it to queue with depth d+1
            // update maxDepth
            for (int i = 0; i < 4; i++) {
                int adjr = r + dir[i][0];
                int adjc = c + dir[i][1];
                if (inBounds(grid, adjr, adjc) && grid[adjr][adjc] == 1) {
                    grid[adjr][adjc] = 2;
                    q.offer(new int[] {adjr, adjc, d + 1});
                    maxDepth = Math.max(maxDepth, d+1);
                }
            }
        }
        
        // scan for a remaining fresh orange, else return maxDepth
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1) {
                    return -1;
                }
            }
        }

        return maxDepth;
    }

    boolean inBounds(int[][] grid, int r, int c) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length;
    }
}
