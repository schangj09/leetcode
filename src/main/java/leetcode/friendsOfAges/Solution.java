package leetcode.friendsOfAges;

import java.util.*;

/*
https://leetcode.com/problems/friends-of-appropriate-ages/description/
Medium

 There are n persons on a social media website. You are given an integer array ages where ages[i] is the age of the ith person.

A Person x will not send a friend request to a person y (x != y) if any of the following conditions is true:

    age[y] <= 0.5 * age[x] + 7
    age[y] > age[x]
    age[y] > 100 && age[x] < 100

Otherwise, x will send a friend request to y.

Note that if x sends a request to y, y will not necessarily send a request to x. Also, a person will not send a friend 
request to themself.

Return the total number of friend requests made.

 */
public class Solution {
    public int numFriendRequests(int[] ages) {
        // say the ages are sorted and we get a freq count for each age.
        // the number of requests sent by a person is the count of people less than or equal to their age 
        // and greater than the lowerbound (age/2+7)
        //
        // one way is to sort the entire ages array and binary search the lowerbound age. However, this is 
        // O(NlogN + NlogN) because first we have to sort N and then binary search on N.
        // Instead if we do freq count, we only need to sort on the number of unique ages K which 
        // should be bounded << N. Then, to get the count of requests for a given age, we need the sum of 
        // frequencies. To do this in O(1) can pre-compute the prefix sums and subtract.

        int n = ages.length;
        Map<Integer, Integer> f = new HashMap<>();
        int maxAge = 0;
        for (int i = 0; i < n; i++) {
            maxAge = Math.max(maxAge, ages[i]);
            f.put(ages[i], 1 + f.getOrDefault(ages[i], 0));
        }
        int k = maxAge;
        int[] countByAge = new int[k+1];
        for (int i = 0; i <= k; i++) {
            countByAge[i] = f.containsKey(i) ? f.get(i) : 0;
        }

        // formula for requests of age x, minFriendAge = (x/2 + 7) (subtract prefixSum[minFriendAge] so no included at that age)
        // total requests for a person of age x, is the number of people of ages > minFriendAge and <= x (minus 1 for the person),
        // then multiply tha tby the number of people at age x. or in case, minFriend >=x, it is just n(n-1) for n peopel of age x
        // prefixSum[x] - prefixSum[minFriendAge] - 1
        int[] prefixSum = new int[k+1];
        prefixSum[0] = countByAge[0];
        for (int i = 1; i <= k; i++) {
            prefixSum[i] = prefixSum[i-1] + countByAge[i];
        }

        int requests = 0;
        for (int x = 0; x <= k; x++) {
            int minFriendAge = x/2 + 7;
            if (minFriendAge < x) {
                requests += countByAge[x] * (prefixSum[x] - prefixSum[minFriendAge] - 1);
            }
        }
        return requests;
    }
}
