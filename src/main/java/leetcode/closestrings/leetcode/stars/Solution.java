package leetcode.closestrings.leetcode.stars;

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
}