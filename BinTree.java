
public class BinTree {

    Tag root;
    Tag current;
    Tag found;
    int size;
    boolean firstrun = true;

    BinTree(Tag t) {
        root = t;
        current = t;
        size = 1;
    }

    // deletes the cspecified tag
    //public void deleteTag(Tag n) {}
    public void search(Tag n, Tag start) {
        if (start != null) {
            current = start;
        }

        if (current.getName().equals(n.getName())) {
            current = n;
        }
        if (current.hasLChild()) {
            search(n, current.getLChild());
        }
        if (current.hasRChild()) {
            search(n, current.getRChild());
        }
    }

    public Tag getRoot() {
        return root;
    }

    public void print(Tag n) {
        Tag lChild = new Tag("null");
        Tag rChild = new Tag("null");

        if (firstrun) {
            firstrun = false;
            search(n, getRoot());
        }
        if (n.hasLChild()) {
            lChild = n.getLChild();
        }
        if (n.hasRChild()) {
            rChild = n.getRChild();
        }

        System.out.println("Node: '" + n.getName() + "' has left child: '" + lChild.getName() + "' and has right child: '" + rChild.getName() + "'");
        if (n.hasLChild()) {
            current = n;
            print(n.getLChild());

        }

        if (n.hasRChild()) {
            current = n;
            print(n.getRChild());
        }
        firstrun = true;
        if (!n.hasLChild() && !n.hasRChild()) {
            current = n;
        }

    }

}
