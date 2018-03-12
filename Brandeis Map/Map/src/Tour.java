
public class Tour {

    static int numofVertices = Map.numofVertices;

    public static Tree prim(Vertex[] vertices, Edge[] edges, ENode[] graph, Vertex start) {
        Tree result = new Tree(start);
        boolean marked[] = new boolean[numofVertices];
        marked[start.getIndex()] = true;
        int markedsize = 1;
        TNode tnodes[] = new TNode[numofVertices];
        tnodes[start.getIndex()] = result.root;

        Heap[] heaps = new Heap[numofVertices];
        int maxLen = 0;
        for(int i = 0; i < numofVertices; i++) {
            int len = 0;
            ENode tmp = graph[i];
            while(tmp != null) {
                len++;
                tmp = tmp.next;
            }
            if(len > maxLen) {
                maxLen = len;
            }
        }

        for(int i = 0; i < numofVertices; i++) {
            heaps[i] = new Heap(maxLen);
            ENode tmp = graph[i];
            while(tmp != null) {
                heaps[i].insert((double)tmp.val.getLength(), tmp.val.getIndex());
                tmp = tmp.next;
            }
        }

        Heap heap = new Heap(numofVertices);
        heap.insert(heaps[start.getIndex()].peak().dis, start.getIndex());
        while(markedsize < numofVertices - 1) {
            int hindex = heap.deleteMin();
            int eindex = heaps[hindex].deleteMin();
            if(heaps[hindex].peak() != null) {
                heap.insert(heaps[hindex].peak().dis, hindex);
            }


            Edge edge = edges[eindex];

            if(marked[edge.getVertex2()] == true){
                continue;
            }
            else {
                marked[edge.getVertex2()] = true;
                markedsize++;
                heap.insert(heaps[edge.getVertex2()].peak().dis, edge.getVertex2());
                tnodes[edge.getVertex1()].addSon(vertices[edge.getVertex2()]);
                tnodes[edge.getVertex2()] = tnodes[edge.getVertex1()].curSon;
                result.size++;
            }
        }

        return result;

    }
}
