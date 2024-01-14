package leetcode.read4;

/*
 * https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/
 * Hard
 * 
Given a file and assume that you can only read the file using a given method read4, implement a method read to read n 
characters. Your method read may be called multiple times.

Method read4:

The API read4 reads four consecutive characters from file, then writes those characters into the buffer array buf4.

The return value is the number of actual characters read.

Note that read4() has its own file pointer, much like FILE *fp in C.
 */
public class SolutionMutiple {
    
    int read4(char[] buf) {
        buf[0] = 'c';
        return 1;
    }

    // buffer from the last call to read4 and indexes of what is remaining to copy from it
    char[] b4 = new char[4];
    int b4Start = 0;
    int b4Count = 0;

    /**
     * @param buf Destination buffer
     * @param n   Number of characters to read
     * @return    The number of actual characters read
     */
    public int read(char[] buf, int n) {
        int count = 0;
        boolean done = false;
        while (b4Start < b4Count && count < n && !done) {
            buf[count++] = b4[b4Start++];
        }

        while (count < n && !done) {
            b4Start = 0;
            b4Count = read4(b4);
            if (b4Count == 0) {
                done = true;
            }
            while (b4Start < b4Count && count < n) {
                buf[count++] = b4[b4Start++];
            }
        }
        return count;
    }
}
