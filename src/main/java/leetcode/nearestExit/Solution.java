package leetcode.nearestExit;

import java.util.*;
/*
 * https://leetcode.com/problems/nearest-exit-from-entrance-in-maze/
 * Medium
 * 
You are given an m x n matrix maze (0-indexed) with empty cells (represented as '.') and walls (represented as '+'). You are 
also given the entrance of the maze, where entrance = [entrancerow, entrancecol] denotes the row and column of the cell you 
are initially standing at.

In one step, you can move one cell up, down, left, or right. You cannot step into a cell with a wall, and you cannot step 
outside the maze. Your goal is to find the nearest exit from the entrance. An exit is defined as an empty cell that is at 
the border of the maze. The entrance does not count as an exit.

Return the number of steps in the shortest path from the entrance to the nearest exit, or -1 if no such path exists.

 */
public class Solution {
    public int nearestExit(char[][] maze, int[] entrance) {
        // think of maze as graph where two cells are connected they are adjacent and
        // both empty
        // we can find the minium path to the exit by BFS from the entrance - must keep
        // track of visited
        // cells
        // To return the length of shortest path, we need to know the level of the BFS,
        // so we will iterate
        // by levels in the BFS keeping a distance counter.

        int[][] adj = new int[][] {
            {-1, 0}, //up
            {0, -1}, //left
            {1, 0}, //down
            {0, 1} // right
        };

        int dist = 0;
        Deque<int[]> q = new LinkedList<>();
        maze[entrance[0]][entrance[1]] = 'o'; // mark next one as visited
        q.offerLast(entrance);
        while (!q.isEmpty()) {
            // iterate all the nodes at this level and check for exit and add nodes of next distance
            int k = q.size();
            for (int i = 0; i < k; i++) {
                int[] c = q.pollFirst();
                //System.out.println(String.format("dist: %d, i: %d, %s", dist, i, Arrays.toString(c)));

                if (dist > 0 && isExit(c, maze)) {
                    return dist;
                }
                for (int x = 0; x < 4; x++) {
                    int a = c[0] + adj[x][0];
                    int b = c[1] + adj[x][1];
                    if (a >= 0 && a < maze.length 
                        && b >= 0 && b < maze[0].length 
                        && maze[a][b] == '.'
                    ) {
                        maze[a][b] = 'o'; // mark next one as visited
                        q.offerLast(new int[] {a, b});
                    }
                }
            }
            dist++;
        }
        // didn't find an exit, so return -1
        return -1;
    }
    boolean isExit(int[] c, char[][] maze) {
        return (c[0] == 0 || c[0] == maze.length - 1 || c[1] == 0 || c[1] == maze[0].length - 1);
    }

    public static void main(String[] argv) {
        String[][] maze = TestCases.LARGE_MAZE;

         char[][] m = new char[maze.length][maze[0].length];
         for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                m[i][j] = maze[i][j].charAt(0);
            }
         }

         int[] start = new int[] {42, 1};
         System.out.println(String.format("running with: start: %s", Arrays.toString(start)));
         System.out.println(String.format("maze rows: %d, cols: %d", m.length, m[0].length));
         System.out.println(new Solution().nearestExit(m, start));
    }
}
