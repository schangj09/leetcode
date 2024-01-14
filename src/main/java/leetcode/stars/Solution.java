package leetcode.stars;

/*
https://leetcode.com/problems/removing-stars-from-a-string/?envType=study-plan-v2&envId=leetcode-75
Medium

You are given a string s, which contains stars *.

In one operation, you can:

    Choose a star in s.
    Remove the closest non-star character to its left, as well as remove the star itself.

Return the string after all stars have been removed.

Note:

    The input will be generated such that the operation is always possible.
    It can be shown that the resulting string will always be unique.

Posted my solution:
https://leetcode.com/problems/removing-stars-from-a-string/solutions/4564942/java-solution-beats-95-of-users-no-stack-instead-use-reverse-iteration/
 */
class Solution {
    public String removeStars(String s) {
        // use stack, or just StringBuffer
        StringBuffer r = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '*') {
                // throw exception if buffer length is 0
                r.deleteCharAt(r.length()-1);
            } else {
                r.append(c);
            }
        }
        return r.toString();
    }

    // Instead of going forward in the string and copying every non-star character
    // into the result (and then either popping/deleting/decrementing an index)
    // we can save time by only copying characters that are definitely in the
    // output.
    // The running time is still O(n), but the run time is 3 times faster since
    // fewer memory store operation.
    //
    // To acheive this, iterate in reverse and count the '*' characters, then
    // either skip a non-star, or include it based on the count.
    public String removeStars2(String s) {
        int j = s.length() - 1;
        int count = 0;
        StringBuffer sb = new StringBuffer(s.length());
        while (j >= 0) {
            char c = s.charAt(j);
            if (c == '*') {
                count++;
            } else if (count > 0) {
                count--;
            } else {
                sb.append(c);
            }
            j--;
        }
        return sb.reverse().toString();
    }
}