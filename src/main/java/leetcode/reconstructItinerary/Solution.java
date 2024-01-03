package leetcode.reconstructItinerary;

import java.util.*;

/*
You are given a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival airports of one flight. Reconstruct the itinerary in order and return it.

All of the tickets belong to a man who departs from "JFK", thus, the itinerary must begin with "JFK". If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string.

    For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].

You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.

 */
public class Solution {
    List<String> ans = new ArrayList<>();
    Map<String, Integer> destinationIds = new HashMap<>();
     List<String> cityList = new ArrayList<>();
    int totalTickets;

    void getDestinations(List<List<String>> tickets) {
        Set<String> citySet = new HashSet<>();
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            citySet.add(from);
            citySet.add(to);
        }
        cityList.addAll(citySet);
        cityList.sort(Comparator.naturalOrder());
        for (int i = 0; i< cityList.size(); i++) {
            destinationIds.put(cityList.get(i), i);
        }
    }
    public List<String> findItinerary(List<List<String>> tickets) {
        
        // thinking of a directed graph traversal, with backtracking when hit a dead end
        // there is a solution if the total destinations in the answer matches the number of tickets
        // count the number of tickets from one node to the next and decrement

        // if at each traversal, we sort the descendants lexicographically and try them in order
        // then we know the first solution will be the lowest lexicographically

        // get a map of destination to node index - sort the destinations with JFK being node 0 and the rest in lex order
        getDestinations(tickets);
        totalTickets = tickets.size();
        
        int n = destinationIds.size();
        int[][] g = new int[n][n];
        // build the edge counts graph from the tickets
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            g[destinationIds.get(from)][destinationIds.get(to)] += 1;
        }

        // traverse the graph with backtracking until find the first path in which all tickets are used
        backtrack(g, destinationIds.get("JFK"));
        ans.add(0, "JFK");
        return ans;
    }
    boolean backtrack(int[][] g, int fromNode) {
        if (ans.size() == totalTickets) {
            return true;
        }
        
        // backtrack on each destination (in order) for which a ticket still exists from fromNode
        for (int to = 0; to < destinationIds.size(); to++) {
            if (to != fromNode && g[fromNode][to] > 0) {
                g[fromNode][to]--;
                ans.add(cityList.get(to));
                if (backtrack(g, to)) {
                    return true;
                }
                ans.remove(ans.size()-1);
                g[fromNode][to]++;
            }
        }
        // didn't find a solution here, so return false
        return false;
    }
}
