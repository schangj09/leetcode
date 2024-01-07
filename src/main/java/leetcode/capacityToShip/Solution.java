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

        // Normal solution is to do a simple linear scan to check a given capacity, but
        // use binary search to narrow down to a correct capacity.

        // get min target capacity
        int n = weights.length;
        int totalWeight = 0;
        int maxWeight = 0;
        for (int i = 0; i < n; i++) {
            totalWeight = totalWeight + weights[i];
            maxWeight = Math.max(maxWeight, weights[i]);
        }
        int minCapacity = Math.max((int)(totalWeight/days), maxWeight);

        int l = minCapacity;
        int r = totalWeight;
        while (l < r) {
            int mid = l + (r-l)/2;
            if (checkCapacity(days, mid, weights)) {
                r = mid;
            } else {
                l = mid+1;
            }
        }
        return l;
    }

    boolean checkCapacity(int days, int capacity, int[] weights) {
        int n = weights.length;
        // increment d each time the container is full - success if d < days
        int d = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (sum + weights[i] > capacity) {
                d++;
                sum = 0;
            } else {
                sum += weights[i];
            }
        }
        return d < days;
    }
}
