package leetcode.dota2;

import java.util.*;

/*
 * https://leetcode.com/problems/dota2-senate
 * Medium
 * 
In the world of Dota2, there are two parties: the Radiant and the Dire.

The Dota2 senate consists of senators coming from two parties. Now the Senate wants to decide on a change in the Dota2 game. 
The voting for this change is a round-based procedure. In each round, each senator can exercise one of the two rights:

    Ban one senator's right: A senator can make another senator lose all his rights in this and all the following rounds.
    Announce the victory: If this senator found the senators who still have rights to vote are all from the same party, he 
    can announce the victory and decide on the change in the game.

Given a string senate representing each senator's party belonging. The character 'R' and 'D' represent the Radiant party and 
the Dire party. Then if there are n senators, the size of the given string will be n.

The round-based procedure starts from the first senator to the last senator in the given order. This procedure will last 
until the end of voting. All the senators who have lost their rights will be skipped during the procedure.

Suppose every senator is smart enough and will play the best strategy for his own party. Predict which party will finally 
announce the victory and change the Dota2 game. The output should be "Radiant" or "Dire".


 */

class Solution {
    public String predictPartyVictory(String senate) {
        // so, there are 2 sets of senators (1 set R, 1 set D)
        // if R bans any D, then remove that D and vice versa when a D bans a R
        // iterate rounds until one set is empty
        // first create the parties
        Set<Integer> r = new HashSet<>();
        Set<Integer> d = new HashSet<>();
        for (int i = 0; i < senate.length(); i++) {
            if (senate.charAt(i) == 'R') {
                r.add(i);
            } else {
                d.add(i);
            }
        }
        while (r.size() > 0 && d.size() > 0) {
            for (int i = 0; i < senate.length(); i++) {
                if (r.contains(i) && d.size() > 0) { // next senator is a R and hasn't been banned
                    // which senator to remove? yes, it does matter - we want to prevent the next possible opponent
                    // find the next senator d at index (j+i)%n
                    int j = 0;
                    while (j < senate.length() && !d.contains((j+i)%senate.length())) {
                        j++;
                    }
                    d.remove((j+i)%senate.length());
                } else if (d.contains(i) && r.size() > 0) {
                    // find the next senator r at index (j+i)%n
                    int j = 0;
                    while (j < senate.length() && !r.contains((j+i)%senate.length())) {
                        j++;
                    }
                    r.remove((j+i)%senate.length());
                }
            }
        }
        return r.size() > 0 ? "Radiant" : "Dire";
    }
}