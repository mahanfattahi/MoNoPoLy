package ds.tree;

import ds.list.LinkedList;

public class TreeNode {
    public Object data;
    public int key;
    public TreeNode left;
    public TreeNode right;
    public LinkedList children;

    public TreeNode(int key, Object data) {
        this.key = key;
        this.data = data;
        this.left = null;
        this.right = null;
        this.children = new LinkedList();
    }
}