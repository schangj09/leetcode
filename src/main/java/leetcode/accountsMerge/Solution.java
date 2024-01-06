package leetcode.accountsMerge;

import java.util.*;

/*
Given a list of accounts where each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.


 */
class Solution {
    List<List<String>> result = new ArrayList<>();

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
                dfs(g, k, visited, accounts, acctEmails);

                List<String> emlList = new ArrayList<>(acctEmails);
                emlList.sort(Comparator.naturalOrder());
                emlList.add(0, accounts.get(k).get(0));
                result.add(emlList);
            }
        }
        return result;
    }

    void dfs(Map<Integer, Set<Integer>> g, int k, Set<Integer> visited, List<List<String>> accounts, Set<String> acctEmails) {
        visited.add(k);
        List<String> kEml = accounts.get(k);
        for (int e = 1; e < kEml.size(); e++) {
            acctEmails.add(kEml.get(e));
        }
        for (int l : g.get(k)) {
            if (!visited.contains(l)) {
                dfs(g, l, visited, accounts, acctEmails);
            }
        }
    }
}
