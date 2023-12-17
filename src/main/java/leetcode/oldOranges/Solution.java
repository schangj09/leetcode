package leetcode.oldOranges;
import java.util.*;
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
