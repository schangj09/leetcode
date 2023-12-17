package leetcode.read4;

public class Solution {

    int read4(char[] buf) {
        buf[0] = 'c';
        return 1;
    }

    /**
     * @param buf Destination buffer
     * @param n   Number of characters to read
     * @return    The number of actual characters read
     */
    public int read(char[] buf, int n) {
        char[] b4 = new char[4];
        int count = 0;
        boolean done = false;
        while (count < n && !done) {
            int c = read4(b4);
            if (c == 0) {
                done = true;
            }
            for (int i = 0; i < c && count < n; i++) {
                buf[count++] = b4[i];
            }
        }
        return count;
    }
}
