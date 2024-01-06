package leetcode.accountsMerge;

import java.util.*;

/*
Given a list of accounts where each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.


 */
class Solution {
        List<List<String>> result = new ArrayList<>();

    // Let N is number of accounts in input, K is max length of an account email
    // Need at least O(NK) running time to go once through the input and O(NKlogNK) to build the result (beacuse of sorting).
    // One idea is to make a graph of connected accounts - where node is the account and an edge exists if 2 accounts share an email
    // Size of graph is N accounts, 2 edges for each account that has an overlap = O(N^2) in the worst case.
    // Run time is iterate once on emails of every account in the input to build adjacency and once more to build output. 
    // Plus sorting of merged account lists.
    //
    // Another idea is to make a graph of connected emails, where each node is an email and an edge exists.
    // For 2 emails that belong to the same account. Not necessary to build all edges, just x-1 per account with x distinct emails.
    // This guarantees that if 2 accounts overlap, then any shared node is connected to the others.
    // Space is O(NK) for the adjacency graph (worst case every unique email is connected to every account)
    // Time is O(NKlogNK) - once to build graph

    // which algorithm is better depends on the nature of the input. 
    // For a large number of accounts with few accounts to merge, it is more efficient to represent the 

    // idea is to make a graph and traverse connected accounts and merge
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();

// the graph of connected account edges
        Map<Integer, Set<Integer>> g = new HashMap<>();

        // map of email to account id
        Map<String, Set<Integer>> emails = new HashMap<>();
        
        // build the graph
        int a = 0;
        for (List<String> acct : accounts) {
            Set<Integer> edges = g.computeIfAbsent(a, (x) -> new HashSet<>());
            for (int j = 1; j < acct.size(); j++) {
                String email = acct.get(a);
                Set<Integer> emailAccts = emails.computeIfAbsent(email, (x) -> new HashSet<>());
                emailAccts.add(a);
                for (int b : emails.get(email)) {
                    edges.add(b);
                    g.computeIfAbsent(b, (x) -> new HashSet<>()).add(a);
                }
            }

            a++;
        }

        // traverse the graph with DFS search
        Set<Integer> visited = new HashSet<>();
        for (int k : g.keySet()) {
            if (!visited.contains(k)) {
                Set<String> acctEmails = new HashSet<>();
                dfs(k, visited, g, accounts, acctEmails);

                List<String> emlList = new ArrayList<>(acctEmails);
                emlList.sort(Comparator.naturalOrder());
                emlList.add(0, accounts.get(k).get(0));
                result.add(emlList);
            }
        }
        return result;
    }

    void dfs(int k, Set<Integer> visited, Map<Integer, Set<Integer>> g, List<List<String>> accounts, Set<String> acctEmails) {
        visited.add(k);
        List<String> kEml = accounts.get(k);
        for (int e = 1; e < kEml.size(); e++) {
            acctEmails.add(kEml.get(e));
        }
        for (int l : g.get(k)) {
            if (!visited.contains(l)) {
                dfs(l, visited, g, accounts, acctEmails);
            }
        }

    }
}
