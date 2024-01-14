package leetcode.movingAverage;

import java.util.*;

/*
https://leetcode.com/problems/moving-average-from-data-stream/
Easy

Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.

Implement the MovingAverage class:

    MovingAverage(int size) Initializes the object with the size of the window size.
    double next(int val) Returns the moving average of the last size values of the stream.


 */

class MovingAverage {

    // the idea is keep the window of values in linked list. we can remove the head and 
    // add to the tail on each new value.
    // maintain the sum of the values for O(1) average calculation
    //
    // Note: the linkedlist has some overhead that can be lessened by using an array
    final int size;
    long sum = 0;
    final Deque<Integer> vals = new LinkedList<>();

    public MovingAverage(int size) {
        this.size = size;
        sum = 0;
    }
    
    public double next(int val) {
        if (vals.size() == size) {
            int first = vals.pollFirst();
            sum -= first;
        }
        vals.offerLast(val);
        sum += val;

        return ((double)sum)/vals.size();
    }
}
