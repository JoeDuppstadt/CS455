
public class Tag {

    private Tag lChild = null;
    private Tag rChild = null;
    private String name;

    public Tag(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String stuff) {
        name = stuff;
    }

    public boolean hasLChild() {
        if (lChild == null) {
            return false;
        }
        return true;
    }

    public boolean hasRChild() {
        if (rChild == null) {
            return false;
        }
        return true;
    }

    public Tag getLChild() {
        Tag tmp = lChild;
        return tmp;
    }

    public Tag getRChild() {
        Tag tmp = rChild;
        return tmp;
    }

    public void setLChild(Tag add) {
        lChild = add;
    }

    public void setRChild(Tag add) {
        rChild = add;
    }

    public void addChildTag(Tag c) {
        this.setLChild(c);
    }

    public void addSiblingTag(Tag c) {
        this.setRChild(c);
    }

}
