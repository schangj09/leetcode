package leetcode.mergeIntervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.
 */
public class Solution {
     public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }
        Arrays.sort(intervals, (a, b) -> { return a[0] - b[0]; });
        
        List<Integer[]> a = new ArrayList<>();
        int start = intervals[0][0];
        int end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int[] cur = intervals[i];
            if (cur[0] > end) {
                a.add(new Integer[] {start, end});
                start = cur[0];
                end = cur[1];
            } else {
                end = Math.max(end, cur[1]);
            }
        }
        a.add(new Integer[] {start, end});
        
        int[][] r = new int[a.size()][2];
        for (int i = 0; i < a.size(); i++) {
            r[i][0] = a.get(i)[0];
            r[i][1] = a.get(i)[1];
        }
        return r;
    }
}
