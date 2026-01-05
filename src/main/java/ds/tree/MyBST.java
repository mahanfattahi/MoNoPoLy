package ds.tree;

public class MyBST {
    private TreeNode root;

    public void insert(int key, Object data) {
        root = insertRec(root, key, data);
    }

    private TreeNode insertRec(TreeNode root, int key, Object data) {
        if (root == null) {
            root = new TreeNode(key, data);
            return root;
        }
        if (key < root.key) {
            root.left = insertRec(root.left, key, data);
        } else if (key > root.key) {
            root.right = insertRec(root.right, key, data);
        }
        return root;
    }

    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(TreeNode root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.print(root.data + " ");
            inOrderRec(root.right);
        }
    }
}