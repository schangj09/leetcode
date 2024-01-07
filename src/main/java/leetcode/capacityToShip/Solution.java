package leetcode.capacityToShip;

/*
A conveyor belt has packages that must be shipped from one port to another within days days.

The ith package on the conveyor belt has a weight of weights[i]. Each day, we load the ship with packages on 
the conveyor belt (in the order given by weights). We may not load more weight than the maximum weight capacity 
of the ship.

Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being 
shipped within days days.


 */
public class Solution {
    long [] prefix;

    public int shipWithinDays(int[] weights, int days) {
        // first idea:
        // take ceil(total weight divided by days) for a min target capacity
        // check if that capacity works, and increment until find a capacity that 
        // works.
        // How to check it works. sum of weights up to capacity, then next sum and 
        // continue d days - if there is no remaining packages, then we found it.
        // If we have prefix sums, then we can do a binary search for the 
        // package index starting the next day: current day i, then next j is 
        // first j for which (sum[j] - sum[i]) > ship capacity
        // cost is N for avg and prefix sums + KDlogN to check capacity for 
        // K times need to check - need to show bound of K is constant.

        // Note: almost solved it this way, but the checkCapacity function is tricky and 
        // didn't quite get all edge cases. It does work though and would improve the 
        // overall running time if combined with a binary search of capacities.

        // get min target capacity
        int n = weights.length;
        prefix = new long[n+1];
        // use 1 based offsets for the prefixSum
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i-1] + weights[i-1];
        }
        long totalWeight = prefix[n];
        int targetCapacity = (int)(totalWeight/days + 1);

        while (!checkCapacity(days, targetCapacity)) {
            System.out.println(String.format("checked: %d", targetCapacity));
            targetCapacity++;
        }
        return targetCapacity;
    }

    boolean checkCapacity(int days, int capacity) {
        int n = prefix.length;
        // for day d, find index of next day start
        int i = 0;
        for (int d = 1; d < days-1 && i < n; d++) {
            // binary search for next prefixSum that will start the next day
            long searchTarget = prefix[i] + capacity;
            int left = i;
            int right = n;
            while (left < right) {
                int mid = left + (right-left)/2;
                System.out.println(String.format("find: %d, left: %d %d, right: %d %d", searchTarget, left, prefix[left], right, prefix[right-1]));
                if (searchTarget >= prefix[mid]) { // perform right binary search for index after the target val when equal 
                    left = mid+1;
                } else {
                    right = mid;
                }
            }
            System.out.println(String.format("find: %d, index: %d", searchTarget, left));
            i = left-1;
        }
        // fits if the cost from the last searchTarget to the end is less than or equal to the capacity
        return prefix[n-1] - prefix[i] <= capacity;
    }
}
