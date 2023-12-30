package leetcode.accountsMerge;

import java.util.*;

/*
Given a list of accounts where each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.


 */
class Solution {
    Map<Integer, String> accountNames = new HashMap<>();
    Map<Integer, Set<String>> accountsMap = new HashMap<>();
    Map<String, Integer> emails = new HashMap<>();
    int nextId = 1;

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // ida is to make a map of email to account so that we can check each email from the input to see 
        // if it is already seen. If so, then add all the emails to the existing account, else
        // create a new account and add all to that
        //
        // 4 accts, with one overlap in each account, so transitively 1 and 4 are linked, but there is no overlap
        // probably need a graph search


        for (List<String> a : accounts) {
            int id = findOrCreateAccount(a);
            addEmailsToAccount(id, a);
        }
        // convert the maps to a list
        List<List<String>> res = new ArrayList<>();
        for (int acctId : accountNames.keySet()) {
            List<String> acct = new ArrayList<>(accountsMap.get(acctId));
            acct.sort(Comparator.naturalOrder());
            acct.add(0, accountNames.get(acctId));
            res.add(acct);
        }
        return res;
    }

    int findOrCreateAccount(List<String> account) {
        String name = account.get(0);
        if (emails.size() > 0) {
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                if (emails.containsKey(email)) {
                    return emails.get(email);
                }
            }
        }
        int id = nextId++;
        accountsMap.put(id, new HashSet<>());
        accountNames.put(id, name);
        return id;
    }

    void addEmailsToAccount(int id, List<String> account) {
        for (int i = 1; i < account.size(); i++) {
            String email = account.get(i);
            emails.put(email, id);
            accountsMap.get(id).add(email);
        }
    }
}
