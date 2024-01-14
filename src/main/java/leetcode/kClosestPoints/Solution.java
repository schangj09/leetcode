package leetcode.kClosestPoints;

import java.util.PriorityQueue;

/*
 * https://leetcode.com/problems/k-closest-points-to-origin/
 * Medium
 * 
Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, return the k closest points to the origin (0, 0).

The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).

You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).
 */
public class Solution {
    public int[][] kClosest(int[][] points, int k) {
        // one way to solve is with a Heap
        // time is O(nlogn) since we have to first add all the points to the 
        // heap and then remove the top k
        //
        // since we only need k elements, we could do a max heap of k elements
        // and if the new point is greater than heap, then skip it. Otherwise,
        // pop the heap and add it. This results in O(nlogk)

        PriorityQueue<Point> maxheap = new PriorityQueue<>((a, b) -> a.dist > b.dist ? -1 : 1);
        int n = points.length;
        for (int i = 0; i < n; i++) {
            Point p = new Point(points[i][0], points[i][1]);
            if (maxheap.size() < k) {
                maxheap.offer(p);
            } else if (p.dist < maxheap.peek().dist) {
                // take out the largest element from the heap and put in p
                maxheap.poll();
                maxheap.offer(p);
            }
        }
        
        int[][] result = new int[maxheap.size()][2];
        int i = 0;
        for (Point p : maxheap) {
            p.get(result[i++]);
        }
        return result;
    }

    static class Point {
        int x;
        int y;
        double dist;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
            dist = Math.sqrt(x*x + y*y);
        }
        void get(int[] p) {
            p[0] = x;
            p[1] = y;
        }
    }
}
