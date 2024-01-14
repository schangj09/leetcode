package leetcode.read4;

/*
 * https://leetcode.com/problems/read-n-characters-given-read4/
 * Easy
 * 
Given a file and assume that you can only read the file using a given method read4, implement a method to read n characters.

Method read4:

The API read4 reads four consecutive characters from file, then writes those characters into the buffer array buf4.

The return value is the number of actual characters read.

Note that read4() has its own file pointer, much like FILE *fp in C.
 */
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
