package leetcode.buildingsWithOceanView;

import java.util.*;

/*
There are n buildings in a line. You are given an integer array heights of size n that represents the heights of the buildings in the line.

The ocean is to the right of the buildings. A building has an ocean view if the building can see the ocean without obstructions. Formally, a building has an ocean view if all the buildings to its right have a smaller height.

Return a list of indices (0-indexed) of buildings that have an ocean view, sorted in increasing order.


 */
public class Solution {
    public int[] findBuildings(int[] heights) {
        // iterate from the right and keep track of the maxHeight so far

        int maxHeight = 0;
        List<Integer> result = new ArrayList<>();
        for (int j = heights.length-1; j >= 0; j--) {
            if (heights[j] > maxHeight) {
                result.add(j);
                maxHeight = heights[j];
            }
        }
        Collections.reverse(result);
        int[] r = new int[result.size()];
        int i = 0;
        for (int j : result) {
            r[i++] = j;
        }
        return r;
    }
}
