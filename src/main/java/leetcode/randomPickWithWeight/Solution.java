package leetcode.randomPickWithWeight;

/*
You are given a 0-indexed array of positive integers w where w[i] describes the weight of the ith index.

You need to implement the function pickIndex(), which randomly picks an index in the range [0, w.length - 1] (inclusive) and returns it.
The probability of picking an index i is w[i] / sum(w).

For example, if w = [1, 3], the probability of picking index 0 is 1 / (1 + 3) = 0.25 (i.e., 25%), and the probability of picking 
index 1 is 3 / (1 + 3) = 0.75 (i.e., 75%).

 */
class Solution {

    // imagine a number line for 0 to sum(w), the range for w[i] is prefix sum(0:i-1) to sum(0:i)
    
    // then pick a random number, n in 0,sum(w) and find the range that corresponds to the value.
    // the corresponding index is the max i such that sum(0:i) <= n
    //
    // if we store all sums, then do a binary search to find the index corresponding to a 
    // random number
    // (no overflow since 10^4 count * 10^5 max weight = 10^9 which fits in an int)
    int[] sums;
    public Solution(int[] w) {
        sums = new int[w.length];
        sums[0] = w[0];
        for (int i = 1; i < w.length; i++) {
            sums[i] = sums[i-1] + w[i];
        }
    }

    public int pickIndex() {
        int r = (int)(Math.random()*sums[sums.length-1]);
        // binary search for sums[k] such that sums[k] <= r and sums[k+1] > r
        int start = 0;
        int end = sums.length-1;
        while (start < end) {
            int mid = start + (end - start)/2; // avoid overflow
            if (r < sums[mid]) {
                end = mid;
            } else {
                start = mid+1;
            }
        }
        return start;
    }
}
