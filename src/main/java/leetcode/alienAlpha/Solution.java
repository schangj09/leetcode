package leetcode.alienAlpha;
import java.util.*;

public class Solution {
    public String alienOrder(String[] words) {
        int n = words.length;
        if (n == 0) {
            return "";
        } else if (n == 1) {
            
        }

        // create a graph where an edge from a -> b indicates a comes before b in the lexicographical order
        Map<Character, Set<Character>> g = new HashMap<>();
        String prev = "";
        for (int i = 0; i < n; i++) {
            String w = words[i];
            int m = w.length();
            // add an edge for the first character between the current word and prev that is not equal
            boolean found = false;
            for (int j = 0; j < m; j++) {
                // make sure every character we see is in the graph, initially it has indegree 0
                g.putIfAbsent(w.charAt(j), new HashSet<>());

                // if it is the first different character, then we know prev[j] < w[j]
                if (!found && prev.length() > j && prev.charAt(j) != w.charAt(j)) {
                    g.computeIfAbsent(prev.charAt(j), (x) -> new HashSet<>())
                        .add(w.charAt(j));
                    found = true;
                }
            }
            // edge case - strings equal but prev is longer, so not in lexical order
            if (!found && prev.length() > w.length()) {
                return "";
            }
            prev = w;
        }
        // now we need a topological sort
        List<Character> sorted = toposort(g);
        // if no topological sort order then there is a cycle and then sorted list doesn't contain all the nodes
        if (sorted.size() < g.size()) {
            return "";
        }
        StringBuffer lex = new StringBuffer();
        for (Character v : sorted) {
            lex.append(v);
        }
        return lex.toString();
    }

    List<Character> toposort(Map<Character, Set<Character>> g) {
        Map<Character, Integer> indegree = new HashMap<>();
        for (Character u : g.keySet()) {
            for (Character v : g.get(u)) {
                indegree.put(v, 1 + indegree.getOrDefault(v, 0));
            }
        }

        List<Character> sorted = new ArrayList<>();
        Deque<Character> q = new LinkedList<>();
        for (Character u : g.keySet()) {
            if (indegree.getOrDefault(u, 0) == 0) {
                q.offerLast(u);
            }
        }
        while (!q.isEmpty()) {
            Character u = q.pollFirst();
            sorted.add(u);
            for (Character v : g.get(u)) {
                if (g.get(u).contains(v)) {
                    int vIn = indegree.getOrDefault(v, 0);
                    vIn = Math.max(0, vIn-1);
                    indegree.put(v, vIn);
                    if (vIn == 0) {
                        q.offerLast(v);
                    }
                }
            }
        }

        return sorted;
    }


    public static void main(String[] args) {
        String res = new Solution().alienOrder(new String[] {
            "wrt","wrf","er","ett","rftt"
        });
        System.out.println(res);
    }
}
