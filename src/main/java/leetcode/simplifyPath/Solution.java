package leetcode.simplifyPath;

import java.util.Stack;

/*
https://leetcode.com/problems/simplify-path/
Medium

Given a string path, which is an absolute path (starting with a slash '/') to a file or directory in a Unix-style file 
system, convert it to the simplified canonical path.

In a Unix-style file system, a period '.' refers to the current directory, a double period '..' refers to the directory up a 
level, and any multiple consecutive slashes (i.e. '//') are treated as a single slash '/'. For this problem, any other 
format of periods such as '...' are treated as file/directory names.

The canonical path should have the following format:

    The path starts with a single slash '/'.
    Any two directories are separated by a single slash '/'.
    The path does not end with a trailing '/'.
    The path only contains the directories on the path from the root directory to the target file or directory (i.e., no period '.' or double period '..')

Return the simplified canonical path.


 */
public class Solution {

    // The idea is to copy the characters one at a time in to a buffer and backtrack the 
    // buffer index for the different cases of directories
    // - a simpler approach (from the editorial) is to split the input string on the '/' char and 
    // then build the new path from the strings (using a Deque since we need both
    // LIFO and forward iteration).
    //
    // My implementation is much faster, so it would be useful for a low-level library implementation
    // but harder to get edges cases right in the interview.

    public String simplifyPath(String path) {
        int n = path.length();
        Stack<Integer> newPathDirIndex = new Stack<>();
        int i = 1;
        int j = 1;
        char[] buf = new char[n+1];
        buf[0] = '/';
        newPathDirIndex.push(0);
        while (i < n) {
            // get dir name - advance k to the index of the next '/'
            int k = i;
            while (k < n && path.charAt(k) != '/') {
                buf[j++] = path.charAt(k);
                k++;
            }
            // 3 cases:
            // 1) dir name empty or single dot
            // 2) dir name is ".."
            // 3) dir name is valid name
            if (k == i
                || (k == i+1 && path.charAt(i) == '.')) {
                // dir name empty or single dot
                // revert j back to last dir index, then i advances to k+1
                j = newPathDirIndex.peek()+1;
            } else if (k == i+2 && path.charAt(i) == '.' && path.charAt(i+1) == '.') {
                // dir name is ".."
                // reset j back by 1 directory indices, i advances
                if (newPathDirIndex.size() > 1) {
                    newPathDirIndex.pop();
                }
                j = newPathDirIndex.peek()+1;
            } else {
                // dir name is a valid name, so push the index, 
                // advance j and i
                newPathDirIndex.push(j);
                buf[j++] = '/';
            }
            // advance i past the '/' char
            i = k+1;
        }
        // remove the trailing '/' if necessary
        if (j > 1 && buf[j-1] == '/') {
            j--;
        }

        return new String(buf, 0, j);
    }
}
