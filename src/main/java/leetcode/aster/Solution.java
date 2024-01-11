package leetcode.aster;

import java.util.*;

/**
 * Not solved - gave up and checked solution - a better algorithm is to use a
 * stack and and add to the stack if not collided then can easily leverage stack
 * peek() and pop() for removing collided ones
 */
class Solution {

    public int[] asteroidCollision(int[] asteroids) {

        // walk through the array of asteroids and push the result of a collision
        // test the collision with the next one and the top of the stack
        int n = asteroids.length;
        Deque<Integer> remaining = new LinkedList<>();
        remaining.push(asteroids[0]);

        for (int i = 1; i < n; i++) {
            int next = asteroids[i];
            boolean hasCollision = true;
            while (hasCollision && !remaining.isEmpty()) {
                hasCollision = false;
                // just look at the stack - don't need to pop unless there is a collision
                int prev = remaining.peek();
                if (prev < 0 && next < 0) {
                    remaining.push(next);
                } else if (prev > 0 && next > 0) {
                    remaining.push(next);
                } else {
                    // otherwise they are opposite signs, but if prev is moving
                    // left then they don't collide
                    if (prev < 0) {
                        remaining.push(next);
                    } else {
                        // pop the prev from the stack and put the winner of the collision into next
                        hasCollision = true;
                        remaining.pop();
                        int collision = Math.abs(prev) - Math.abs(next);
                        if (collision == 0) {
                            // equal, so both destroyed and break the loop
                            hasCollision = false;
                        } else if (collision > 0) {
                            next = prev;
                        }
                    }
                }
            }
            if (remaining.isEmpty() && hasCollision) {
                remaining.push(next);
            }

        }

        // copy the stack into an array
        int m = remaining.size();
        int[] res = new int[m];
        m--;
        while (m >= 0) {
            res[m--] = remaining.pop();
        }
        return res;
    }

    public int[] asteroidCollision1(int[] asteroids) {
        // walk forward and copy values from input into the result.
        // skip over adding the collided asteroids, and after a collision keep
        // walking backwards until no more collisions

        // could be done in place, but let's start with a copy
        int j = 0; // current last index into res
        int[] res = new int[asteroids.length];

        for (int i = 0; i < asteroids.length; i++) {
            // if nothing in the result (at the beginning or after collisions) then we always add the next one
            if (j == 0) {
                res[j++] = i;
            } else {
                // first case: same direction or moving away from each other, keep it
                if (sign(res[j - 1]) == sign(asteroids[i]) || sign(res[j - 1]) < 0) {
                    res[j++] = i;
                } else {
                    // otherwise collision
                    int next = Math.abs(asteroids[i]);
                    int prev = Math.abs(res[j - 1]);
                    // if next is smaller, then skip it
                    if (next < prev) {
                        continue;
                    } else if (next == prev) {
                        // if next is equal, then both are removed, so we just need to decrement j
                        j--;
                    } else {
                        // when prev is smaller, then we have to walk backwards until we find the beginning, the same direction asteroid or a bigger prev
                        while (j > 0 && prev < next) {
                            res[j - 1] = next;
                            j--;
                            if (j > 0) {
                                prev = Math.abs(res[j - 1]);
                            } else {
                                prev = next;
                            }
                        }
                        // the walk backwards ended with equal prev and next, then both are removed, so decrement j once more
                        if (j > 0 && prev == next) {
                            j--;
                        }
                    }
                }
            }
        }
        // at the end j is the index of the last one in result - it could be -1 if all asteroids collided

        return Arrays.copyOf(res, j + 1);
    }

    private int sign(int v) {
        if (v < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
