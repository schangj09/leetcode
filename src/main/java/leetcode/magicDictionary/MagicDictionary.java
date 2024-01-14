package leetcode.magicDictionary;

/*
https://leetcode.com/problems/implement-magic-dictionary/
Medium

Design a data structure that is initialized with a list of different words. Provided a string, you should determine if you 
can change exactly one character in this string to match any word in the data structure.

Implement the MagicDictionary class:

    MagicDictionary() Initializes the object.

    void buildDict(String[] dictionary) Sets the data structure with an array of distinct strings dictionary.

    bool search(String searchWord) Returns true if you can change exactly one character in searchWord to match any string in 
    the data structure, otherwise returns false.
 */
class MagicDictionary {

    // build a Trie for search. The search is modified to implement backtracking
    // approach with
    // exactly one character replaced.

    // examples: insert "hello"
    // search "hhllo"
    class Trie {
        Trie[] a = new Trie[26];
        boolean terminal = false;

        void insert(String s) {
            if (s.isEmpty()) {
                terminal = true;
            } else {
                char c = s.charAt(0);
                Trie sub = a[c - 'a'];
                if (sub == null) {
                    sub = new Trie();
                    a[c - 'a'] = sub;
                }
                sub.insert(s.substring(1));
            }
        }

        boolean search(String s, boolean hasCharReplaced) {
            if (s.isEmpty()) {
                return terminal && hasCharReplaced;
            }
            char c = s.charAt(0);
            int ci = c - 'a';
            for (int i = 0; i < 26; i++) {
                if (a[i] != null) {
                    // search without replacing this char
                    if (ci == i) {
                        if (a[i].search(s.substring(1), hasCharReplaced)) {
                            return true;
                        }
                    } else if (!hasCharReplaced) {
                        // search with replacing this char
                        if (a[i].search(s.substring(1), true)) {
                            return true;
                        }
                    }
                } else {
                    // a[i] == null
                    // break out if we are searching without replacement and this char is not in our trie
                    if (ci == i && hasCharReplaced) {
                        return false;
                    }
                }
            }
            return false;
        }

    }

    Trie dict = new Trie();

    public MagicDictionary() {

    }

    public void buildDict(String[] dictionary) {
        for (int i = 0; i < dictionary.length; i++) {
            dict.insert(dictionary[i]);
        }
    }

    public boolean search(String searchWord) {
        return dict.search(searchWord, false);
    }
}

/**
 * Your MagicDictionary object will be instantiated and called as such:
 * MagicDictionary obj = new MagicDictionary();
 * obj.buildDict(dictionary);
 * boolean param_2 = obj.search(searchWord);
 */