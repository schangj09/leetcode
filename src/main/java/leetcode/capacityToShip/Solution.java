package leetcode.capacityToShip;

import java.util.Arrays;
import java.util.function.BiFunction;

/*
A conveyor belt has packages that must be shipped from one port to another within days days.

The ith package on the conveyor belt has a weight of weights[i]. Each day, we load the ship with packages on 
the conveyor belt (in the order given by weights). We may not load more weight than the maximum weight capacity 
of the ship.

Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being 
shipped within days days.


 */
public class Solution {
    int[] prefix;
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
        // we can use use binary search on prefixSums to faster check each capacity.
        prefix = new int[weights.length];
        prefix[0] = weights[0];
        for (int k = 0; k < weights.length; k++) {
            prefix[k] = prefix[k-1] + weights[k];
        }

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
        int i = 0;
        int prevSum = 0;
        for (int d = 0; d < days && i < n; d++) {
            // find next prefixSum which is strictly over prevSum + capacity
            i = find(prefix, i, prefix.length, prevSum + capacity, (a, b) -> a < b);
            prevSum = prefix[i-1];
        }
        return i == n;
    }


    // returns the leftmost index for which condition is true
    // - to find leftmost instance of target (i.e. insertBefore), use "<=" and
    // to find value after target (i.e. insertAfter), use "<"
    int find(int[] nums, int start, int end, int target, BiFunction<Integer, Integer, Boolean> condition) {
        int l = start;
        int r = end;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (condition.apply(target, nums[mid])) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
}
