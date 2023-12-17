/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package leetcode.triePrefix;

/**
 *
 * @author Jeffrey Schang
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
        }
    }

    public boolean search(String word) {
        return search(root, word);
    }
    private boolean search(Node node, String w) {
        if (w.length() == 0 || node.next[w.charAt(0)-'a'] == null) {
            return false;
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
    }
}