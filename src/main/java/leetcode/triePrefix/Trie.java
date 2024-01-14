package leetcode.triePrefix;

/*
https://leetcode.com/problems/implement-trie-prefix-tree/
Medium

A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a 
dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.

Implement the Trie class:

    Trie() Initializes the trie object.
    void insert(String word) Inserts the string word into the trie.
    boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false 
    otherwise.
    boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix, 
    and false otherwise.

 */
class Trie {
    Node root = new Node();

    public Trie() {

    }

    public void insert(String word) {
        insert(root, word);
    }
    private void insert(Node node, String w) {
        if (w.length() > 0) {
            int idx = w.charAt(0)-'a';
            if (node.next[idx] == null) {
                node.next[idx] = new Node();
            }

            insert(node.next[idx], w.substring(1));
        } else {
            node.count++;
        }
    }

    public boolean search(String word) {
        return search(root, word);
    }
    private boolean search(Node node, String w) {
        if (node == null) {
            return false;
        }
        if (w.length() == 0) {
            return node.count > 0;
        }
        return search(node.next[w.charAt(0)-'a'], w.substring(1));
    }

    public boolean startsWith(String prefix) {
        return hasPrefix(root, prefix);
    }
    private boolean hasPrefix(Node node, String w) {
        if (w.length() == 0) {
            return true;
        } else if (node.next[w.charAt(0)-'a'] == null) {
            return false;
        }
        return hasPrefix(node.next[w.charAt(0)-'a'], w.substring(1));
    }

    static class Node {
        Node[] next = new Node[26];
        int count = 0;
    }
}