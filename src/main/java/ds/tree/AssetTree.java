package ds.tree;

public class AssetTree {
    private TreeNode root;

    public AssetTree(Object rootData) {
        this.root = new TreeNode(0, rootData);
    }

    public TreeNode getRoot() {
        return root;
    }

    public void addChild(TreeNode parent, Object childData) {
        TreeNode childNode = new TreeNode(0, childData);
        parent.children.add(childNode);
    }
}
