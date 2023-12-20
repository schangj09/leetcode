package leetcode.dotProductSparse;

import java.util.*;

/*
Given two sparse vectors, compute their dot product.

Implement class SparseVector:

    SparseVector(nums) Initializes the object with the vector nums
    dotProduct(vec) Compute the dot product between the instance of SparseVector and vec

A sparse vector is a vector that has mostly zero values, you should store the sparse vector efficiently and compute the dot product between two SparseVector.

Follow up: What if only one of the vectors is sparse?


 */
class SparseVector {
    // For follow up question:
    // If only 1 vector is sparse, we will still have an optimal run time for
    // dotProduct, but the
    // memory usage is higher than necessary due to the overhead of the HashMap.
    //
    // We can optimize mem usage by first checking the number of non-empty values,
    // and if ratio non-empty is over
    // a threshold, keep the input array instead of a map.
    // Rewrite dotProduct to use a size() method and iterator instead of map keySet

    static double THRESHOLD = 0.6;
    Map<Integer, Integer> values = new HashMap<>();
    int[] nums;
    int keyCount;

    SparseVector(int[] nums) {
        keyCount = getNonZeroCount(nums);
        if ((double) keyCount / nums.length > THRESHOLD) {
            this.nums = nums;
        } else {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    values.put(i, nums[i]);
                }
            }
        }
    }

    int getNonZeroCount(int[] nums) {
        // for testing pretend the vector is not sparse enough
        return nums.length;
    }

    public int size() {
        return keyCount;
    }

    public int get(int i) {
        if (nums != null) {
            return nums[i];
        }
        return values.getOrDefault(i, 0);
    }

    Iterator<Integer> iter() {
        if (nums != null) {
            return new Iter();
        } else {
            return values.keySet().iterator();
        }
    }

    // inner instance class
    class Iter implements Iterator<Integer> {
        int i = 0;

        Iter() {
            while (i < nums.length && nums[i] == 0) {
                i++;
            }
        }

        @Override
        public boolean hasNext() {
            return i < nums.length;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new RuntimeException();
            }
            int next = i++;
            while (i < nums.length && nums[i] == 0) {
                i++;
            }
            return next;
        }

    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        int val = 0;
        Iterator<Integer> keys = size() < vec.size() ? iter() : vec.iter();
        while (keys.hasNext()) {
            int i = keys.next();
            val += get(i) * vec.get(i);
        }
        return val;
    }
}
