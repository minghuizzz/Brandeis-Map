public class TNode {
    Vertex val;
    TNode sons;
    TNode next;
    TNode curSon;

    public TNode() {};
    public TNode(Vertex val) {
        this.val = val;
    }

    public void addSon(Vertex val) {
        if(sons == null) {
            sons = new TNode(val);
            curSon = sons;
        }
        else {
            curSon.next = new TNode(val);
            curSon = curSon.next;
        }
    }
}