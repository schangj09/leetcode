package leetcode.cycleLengthQueries;

/*
 You are given an integer n. There is a complete binary tree with 2n - 1 nodes. The root of that tree is the node with the value 1, and every node with a value val in the range [1, 2n - 1 - 1] has two children where:

    The left node has the value 2 * val, and
    The right node has the value 2 * val + 1.

You are also given a 2D integer array queries of length m, where queries[i] = [ai, bi]. For each query, solve the following problem:

    Add an edge between the nodes with values ai and bi.
    Find the length of the cycle in the graph.
    Remove the added edge between nodes with values ai and bi.

Note that:

    A cycle is a path that starts and ends at the same node, and each edge in the path is visited only once.
    The length of a cycle is the number of edges visited in the cycle.
    There could be multiple edges between two nodes in the tree after adding the edge of the query.

Return an array answer of length m where answer[i] is the answer to the ith query.

 
 */
public class Solution {
    // for the tree node i, the left child is 2i and right is 2i+1, so the parent of a node j
    // is j/2
    // to find the length of the cycle, we can find the lowest common ancestor of the 2
    // nodes, then count the levels
    //
    // take 2 nodes i, j where j > i, then they are on the same level if j/2 < i.
    // In this case we move both up a level and count+=2, else
    // we set j = parent(j) and count +=1, repeat until i == j
    public int[] cycleLengthQueries(int n, int[][] queries) {
        // for each query, get the cycleLength
        int[] result = new int[queries.length];
        for (int x = 0; x < result.length; x++) {
            result[x] = cycleLength(Math.min(queries[x][0], queries[x][1]), Math.max(queries[x][0], queries[x][1]));
        }
        return result;
    }

    // requires j >= i
    int cycleLength(int i, int j) {
        int count = 1;
        while (i != j) {
            if (j/2 < i) {
                count += 2;
                i = i/2;
                j = j/2;
            } else {
                count++;
                j = j/2;
            }
        }
        return count;
    }
}
