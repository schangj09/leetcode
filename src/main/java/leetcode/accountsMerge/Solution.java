package leetcode.accountsMerge;

import java.util.*;

/*
Given a list of accounts where each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.


 */
class Solution {

    // idea is to make a graph and traverse connected accounts and merge
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        
        // build the graph as a 2d array - 2 account ids are linked if there is an overlapping email
        Map<String, List<Integer>> emailsMap = new HashMap<>();
        boolean[][] graph = new boolean[n][n];
        for (int a = 0; a < n; a++) {
            List<String> acct = accounts.get(a);
            for (int i = 1; i < acct.size(); i++) {
                String email = acct.get(i);
                List<Integer> ids = emailsMap.computeIfAbsent(email, (x) -> new ArrayList<>());
                ids.add(a);
                // add bidirectional graph edge for any overlapping accounts found
                for (int b : ids) {
                    graph[a][b] = true;
                    graph[b][a] = true;
                }
            }
        }

        // traverse all account ids until all are visited
        List<List<String>> result = new ArrayList<>();
        boolean[] visited = new boolean[n];
        for (int a = 0; a < n; a++) {
            if (!visited[a]) {
                Set<String> emails = new HashSet<>();
                traverse(a, graph, visited, accounts, emails);
                List<String> acct = new ArrayList<>(emails);
                acct.sort(Comparator.naturalOrder());

                String accountName = accounts.get(a).get(0);
                acct.add(0, accountName);
                result.add(acct);
            }
        }
        return result;
    }

    void traverse(int a, boolean[][] graph, boolean[] visited, List<List<String>> accounts, Set<String> emails) {
        if (!visited[a]) {
            visited[a] = true;
            // add emails from account a and recurse on connected accounts
            for (int j = 1; j < accounts.get(a).size(); j++) {
                emails.add(accounts.get(a).get(j));
            }
            for (int b = 0; b < accounts.size(); b++) {
                // recurse on any connected account
                if (!visited[b] && graph[a][b]) {
                    traverse(b, graph, visited, accounts, emails);
                }
            }
        }
    }
}
