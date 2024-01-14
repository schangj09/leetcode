package leetcode.nestedListWeightSum;

import java.util.List;

/*
https://leetcode.com/problems/nested-list-weight-sum/description/
Medium

You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements 
may also be integers or other lists.

The depth of an integer is the number of lists that it is inside of. For example, the nested 
list [1,[2,2],[[3],2],1] has each integer's value set to its depth.

Return the sum of each integer in nestedList multiplied by its depth.
 
*/
public class Solution {
    
/*
 // This is the interface that allows for creating nested lists.
 // You should not implement it, or speculate about its implementation
*/
 public interface NestedInteger {
     // @return true if this NestedInteger holds a single integer, rather than a nested list.
     public boolean isInteger();

     // @return the single integer that this NestedInteger holds, if it holds a single integer
     // Return null if this NestedInteger holds a nested list
     public Integer getInteger();

     // Set this NestedInteger to hold a single integer.
     public void setInteger(int value);

     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
     public void add(NestedInteger ni);

     // @return the nested list that this NestedInteger holds, if it holds a nested list
     // Return empty list if this NestedInteger holds a single integer
     public List<NestedInteger> getList();
 }
 

 
    public int depthSum(List<NestedInteger> nestedList) {
        return sum(1, nestedList);
    }
    int sum(int depth, List<NestedInteger> nestedList) {
        int sum = 0;
        for (NestedInteger i :nestedList) {
            if (i.isInteger()) {
                sum += i.getInteger() * depth;
            } else {
                sum += sum(depth+1, i.getList());
            }
        }
        return sum;
    }
}
