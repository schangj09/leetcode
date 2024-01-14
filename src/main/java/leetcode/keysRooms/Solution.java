package leetcode.keysRooms;

import java.util.*;

/*
 * https://leetcode.com/problems/keys-and-rooms/
 * Medium
 * 
There are n rooms labeled from 0 to n - 1 and all the rooms are locked except for room 0. Your goal is to visit all the 
rooms. However, you cannot enter a locked room without having its key.

When you visit a room, you may find a set of distinct keys in it. Each key has a number on it, denoting which room it 
unlocks, and you can take all of them with you to unlock the other rooms.

Given an array rooms where rooms[i] is the set of keys that you can obtain if you visited room i, return true if you can 
visit all the rooms, or false otherwise.
 */
public class Solution {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        // consider it is a directed graph where room a and b are connected if a has a
        // key to b
        // then DFS of graph and return true if the count == number of rooms
        Map<Integer, List<Integer>> roomMap = new HashMap<>();
        for (int i = 0; i < rooms.size(); i++) {
            roomMap.put(i, rooms.get(i));
        }
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> toVisit = new Stack<>();
        toVisit.push(0);
        while (!toVisit.isEmpty()) {
            int i = toVisit.pop();
            if (!visited.contains(i)) {
                visited.add(i);
                List<Integer> keys = roomMap.get(i);
                keys.stream().forEach(k -> toVisit.push(k));
            }
        }
        return visited.size() == rooms.size();
    }

}
