package leetcode.buySellStock;

/*
https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
Easy

You are given an array prices where prices[i] is the price of a given stock on the ith day.

You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to 
sell that stock.

Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.

 */
public class Solution {
    public int maxProfit(int[] prices) {
        // not dynamic programming - just need 2 variables - the min price seen before index i and the max profit
        // found so far
        int minPrice = prices[0];
        int maxProfit = Integer.MIN_VALUE;
        for (int i = 0; i < prices.length; i++) {
            int p = prices[i];
            if (p < minPrice) {
                minPrice = p;
            } else {
                maxProfit = Math.max(maxProfit, p-minPrice);
            }
        }
        return Math.max(maxProfit, 0);
    }

}
