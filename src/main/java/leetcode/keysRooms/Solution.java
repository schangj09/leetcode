package leetcode.keysRooms;

import java.util.*;

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
