public class Heap {
    HNode array[];
    int size = 0;
    int nextRB = 0;
    public Heap(int n) {
        array = new HNode[n];
        size = n;
    }

    public int parent(int i) {
        if(i > 0) {
            return (i - 1) / 2;
        }
        else {
            return -1;
        }
    }

    public int lChild(int i) {
        if(2 * i + 1 < size) {
            return 2 * i + 1;
        }
        else {
            return -1;
        }
    }

    public int rChild(int i) {
        if(2 * i + 2 < size) {
            return 2 * i + 2;
        }
        else {
            return -1;
        }
    }

    public void percup(int i) {
        while(i > 0 && array[i].dis < array[parent(i)].dis) {
            HNode tmp = array[parent(i)];
            array[parent(i)] = array[i];
            array[i] = tmp;
            i = parent(i);
        }
    }

    public void insert(double dis, int index) {
        if(nextRB >= size) {
            //error
        }
        else {
            array[nextRB] = new HNode(dis, index);
            percup(nextRB);
            nextRB++;
        }
    }

    public void percdown(int i) {
        while(lChild(i) != -1 && nextRB > lChild(i)) {
            HNode left = array[lChild(i)];
            HNode right = array[rChild(i)];
            if(right == null) {
                if(left.dis < array[i].dis) {
                    HNode tmp = array[lChild(i)];
                    array[lChild(i)] = array[i];
                    array[i] = tmp;
                    i = lChild(i);
                }
                else {
                    break;
                }
            }
            else {
                if(left.dis < array[i].dis && right.dis < array[i].dis) {
                    if(left.dis < right.dis) {
                        HNode tmp = array[lChild(i)];
                        array[lChild(i)] = array[i];
                        array[i] = tmp;
                        i = lChild(i);
                    }
                    else {
                        HNode tmp = array[rChild(i)];
                        array[rChild(i)] = array[i];
                        array[i] = tmp;
                        i = rChild(i);
                    }
                }
                else if(left.dis < array[i].dis) {
                    HNode tmp = array[lChild(i)];
                    array[lChild(i)] = array[i];
                    array[i] = tmp;
                    i = lChild(i);
                }
                else if(right.dis < array[i].dis) {
                    HNode tmp = array[rChild(i)];
                    array[rChild(i)] = array[i];
                    array[i] = tmp;
                    i = rChild(i);
                }
                else {
                    break;
                }
            }

        }
    }

    public int deleteMin() {
        if(nextRB <= 0) {
            //error
            return -1;
        }
        else {
            nextRB--;
            int result = array[0].index;
            array[0] = array[nextRB];
            array[nextRB] = null;
            percdown(0);
            return result;
        }
    }

    public HNode peak() {
        return array[0];
    }

    public void update(double dis, int index) {
        for(int i = 0; i < nextRB; i++) {
            if(array[i].index == index) {
                array[i].dis = dis;
                percup(i);
                return;
            }
        }
        insert(dis, index);
    }

}

class HNode {
    double dis;
    int index;

    public HNode(double dis, int index) {
        this.dis = dis;
        this.index = index;
    }
}
