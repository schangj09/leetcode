package leetcode.findCelebrity;

import java.util.*;
/*
 * https://leetcode.com/problems/find-the-celebrity/
 * Medium
 * 
Suppose you are at a party with n people labeled from 0 to n - 1 and among them, there may exist one celebrity. The 
definition of a celebrity is that all the other n - 1 people know the celebrity, but the celebrity does not know any of them.

Now you want to find out who the celebrity is or verify that there is not one. You are only allowed to ask questions like: 
"Hi, A. Do you know B?" to get information about whether A knows B. You need to find out the celebrity (or verify there is 
not one) by asking as few questions as possible (in the asymptotic sense).

You are given a helper function bool knows(a, b) that tells you whether a knows b. Implement a function int findCelebrity
(n). There will be exactly one celebrity if they are at the party.

Return the celebrity's label if there is a celebrity at the party. If there is no celebrity, return -1.
 */
public class Solution {
    int[][] knowsGrid;

    Solution(int[][] knows) {
        this.knowsGrid = knows;
    }

    public boolean knows(int a, int b) {
        return knowsGrid[a][b] == 1;
    }

    public int findCelebrity(int n) {
        // sounds like a reverse topo sort (a knows b is edge b->a), so the celebrity
        // has no incoming edges
        // that would require first building all the edges,
        // and then traverse the graph starting with no incoming edges
        // O(n^2) to build the edges and counts at the same time
        // O(E) to perform topo sort

        // instead we can focus on the candidates for celebrity.
        // only one node can have no incoming edges, so we just need to find for each i,
        // if i knows j, then i is not a celebrity, j might be. Next search for j knows
        // any of the remaining candidates
        // start with all nodes are candidates. for each candidate a, check if they know
        // any other candidate. If so, then
        // remove a from candidates. for next candidate b, check if they know any of the
        // remaining candidates

        Set<Integer> exclude = new HashSet<>();
        Set<Integer> candidates = new HashSet<>();
        for (int i = 0; i < n; i++) {
            // once we find i knows someone, we break the loop and proceed to the next
            // candidate
            for (int j = 0; j < n && !exclude.contains(i); j++) {
                if (i != j) {
                    // if i knows j, then we can remove i from candidates and add j to candidates
                    // otherwise, i might still be a candidate
                    if (knows(i, j)) {
                        exclude.add(i);
                        if (candidates.contains(i)) {
                            candidates.remove(i);
                        }
                        if (!exclude.contains(j)) {
                            candidates.add(j);
                        }
                    } else {
                        candidates.add(i);
                    }
                }
            }

        }
        System.out.println(exclude);
        System.out.println(candidates);
        // now we've checked every i to determine if it could be a candidate and
        // excluded every node that
        // definitely isn't
        // if there is more than one candidate, then there can't be a solution since
        // that would mean that
        // 2 people are there who don't know anyone and the celebrity is known by all
        if (candidates.size() != 1) {
            return -1;
        } else {
            int c = candidates.iterator().next();
            // verify that everyone knows c
            for (int i = 0; i < n; i++) {
                if (i != c && !knows(i, c)) {
                    return -1;
                }
            }
            return c;
        }
    }

    public static void main(String[] args) {
        int[][] kg;
        int c;

        kg = new int[][] {
                { 1, 1 },
                { 1, 1 }
        };
        c = new Solution(kg).findCelebrity(2);
        System.out.println(c);

        kg = new int[][] {
                { 1, 1, 0 },
                { 0, 1, 0 },
                { 1, 1, 1 }
        };
        c = new Solution(kg).findCelebrity(3);
        System.out.println(c);
    }
}