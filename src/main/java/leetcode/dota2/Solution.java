package leetcode.dota2;

import java.util.*;

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