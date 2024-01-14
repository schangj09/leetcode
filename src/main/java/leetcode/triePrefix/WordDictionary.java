package leetcode.triePrefix;

/*
https://leetcode.com/problems/design-add-and-search-words-data-structure/description/
Medium

Design a data structure that supports adding new words and finding if a string matches any previously added string
with wildcard matches.

Implement the WordDictionary class:

    WordDictionary() Initializes the object.
    void addWord(word) Adds word to the data structure, it can be matched later.
    bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise. word may contain dots '.' where dots can be matched with any letter.
*/

// examples:
// add "be"
// search ".e" -> true
// search ".f" -> false
class WordDictionary {
    static class Trie {
        Trie[] p = new Trie[26];
        boolean terminal = false;

        // create the index in the Trie or mark it as a terminal for empty string
        void add(String s) {
            if (s.length() == 0) {
                terminal = true;
                return;
            }
            int c = s.charAt(0) - 'a';
            if (p[c] == null) {
                p[c] = new Trie();
            }
            p[c].add(s.substring(1));
        }

        boolean find(String s) {
            if (s.length() == 0) {
                // it is found only if this is a terminal Trie node
                return terminal;
            }
            // case when fist char is a '.', then iterate
            if (s.charAt(0) == '.') {
                String sub = s.substring(1);
                boolean found = false;
                for (int i = 0; i < 26 && !found; i++) {
                    if (p[i] != null) {
                        found = p[i].find(sub);
                    }
                }
                return found;
            } else {
                int c = s.charAt(0) - 'a';
                return p[c] == null ? false : p[c].find(s.substring(1));
            }
        }
    }

    Trie t = new Trie();
    public WordDictionary() {
    }
    
    public void addWord(String word) {
        t.add(word);
    }

    public boolean search(String word) {

        return t.find(word);
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */