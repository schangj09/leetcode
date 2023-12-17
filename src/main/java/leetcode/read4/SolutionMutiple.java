package leetcode.read4;

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
