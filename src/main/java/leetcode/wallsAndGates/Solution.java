package leetcode.wallsAndGates;

import java.util.*;

/*
https://leetcode.com/problems/walls-and-gates/
Medium

You are given an m x n grid rooms initialized with these three possible values.

    -1 A wall or an obstacle.
    0 A gate.
    INF Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF as you may assume that the 
    distance to a gate is less than 2147483647.

Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.
*/

public class Solution {
    public void wallsAndGates(int[][] rooms) {
        // the idea is to do breadth first search from each gate simultaeously and 
        // make a queue of rooms to visit next. Initially it is the gates
        int INF = (int)Math.pow(2, 31) - 1;
        Deque<Integer[]> q = new LinkedList<>();
        int m = rooms.length;
        int n = rooms[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    q.offerLast(new Integer[] { i, j });
                }
            }
        }

        while (!q.isEmpty()) {
            Integer[] r = q.pollFirst();
            // visit each neighbor room, if it is not a wall or gate and the distance is not yet entered,
            // then set the distance to this room+1
            int i = r[0];
            int j = r[1];
            int dist = 1 + rooms[i][j];
            int[][] adj = new int[][] {
                {0,-1}, //left
                {-1,0}, //up
                {0,1}, //right
                {1,0} //down
            };
            for (int k = 0; k < 4; k++) {
                int u = i+adj[k][0];
                int v = j+adj[k][1];
                if (u >= 0 && u < m && v >= 0 && v < n && rooms[u][v] == INF) {
                    rooms[u][v] = dist; 
                    q.offerLast(new Integer[] {u,v});
                }
            }
        }
    }

}
