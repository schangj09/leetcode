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

        // both solutions depend on sorting the by time
        
        // 2 different ideas - one is the use a min-heap that contains all the occupied rooms, ordered by the end time
        // when a new meeting starts after the earliest end time, we can pop the heap and replace it with the new 
        // meetings end time. At the end the size of the heap is the max occupied rooms.
        //
        // don't forget to clarify if the end and start are equal, then does that count as overlap? (by leetcode yes)

        // other idea is to separate start and end times into separate arrays, sort each individually
        // Walk through the 2 arrays keepign track of how many meetings are overlapping.

        // option 1:
        Arrays.sort(intervals, (a, b) -> {return a[0] < b[0] ? -1 : (a[0] == b[0] ? 0 : 1);});

        PriorityQueue<Integer> occupiedRoomEndTime = new PriorityQueue<>((a, b) -> a - b);
        occupiedRoomEndTime.offer(intervals[0][1]);
        for (int i = 1; i < n; i++) {
            // if the next meeting starts after the earliest end time in the queue, then we can pop that from queue
            if (intervals[i][0] >= occupiedRoomEndTime.peek()) {
                occupiedRoomEndTime.poll();
            }
            // always add this meeting end time to the queue
            occupiedRoomEndTime.offer(intervals[i][1]);
        }

        return occupiedRoomEndTime.size();
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
