package leetcode.meetingRooms2;

import java.util.*;


/*
 * Given an array of meeting time intervals intervals where intervals[i] = [starti, endi], return the minimum number of conference rooms required.
 */
public class Solution {
    public int minMeetingRooms(int[][] intervals) {
        int n = intervals.length;
        if (n == 0) {
            return 0;
        }
        // sort the meetings first. To find the min number of conference rooms, we need to 
        // count the maximum number of overlapping intervals.
        Arrays.sort(intervals, (a, b) -> {return a[0] < b[0] ? -1 : (a[0] == b[0] ? 0 : 1);});

        // keep a sliding window for the intervals that are currently overlapping with right indices not-inclusive
        // if the intervval
        int left = 0;
        int right = 1;
        int maxOverlap = 1;
        while (right < n) {
            System.out.println(Arrays.toString(intervals[left]));
            right++;
            // find the first meeting whose start time is after the left meeting end time
            while (right < n && intervals[right][0] <= intervals[left][1]) {
                right++;
            }
            int overlap = right - left;
            maxOverlap = Math.max(maxOverlap, overlap);
            // increment left meeting to find a next overlap group
            left++;
        }

        return maxOverlap;
    }

    public static void main(String[] args) {
        int[][] kg;
        int c;

        kg = new int[][] {
            {2, 3}, {5, 6}, {1, 4}, {7, 8}, 
        };
        c = new Solution().minMeetingRooms(kg);
        System.out.println(c);

        kg = new int[][] {
            {0, 30}, {5, 10}, {15, 20}, 
        };
        c = new Solution().minMeetingRooms(kg);
        System.out.println(c);
    }
}
