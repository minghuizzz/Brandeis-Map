public class Edge {//595 edges
    private int index;
    private String label1;
    private String label2;
    private int vertex1;
    private int vertex2;
    private int length;
    private int angle;
    private String direction;
    private String C;
    private String name;

    public Edge(String line) {
        String info[] = line.split(" ");
        this.index = Integer.parseInt(info[0]);
        this.label1 = info[1];
        this.label2 = info[2];
        this.vertex1 = Integer.parseInt(info[3]);
        this.vertex2 = Integer.parseInt(info[4]);
        this.length = Integer.parseInt(info[5]);
        this.angle = Integer.parseInt(info[6]);
        this.direction = info[7];
        this.C = info[8].substring(1,2);
        if(line.split("\"").length == 1) {
            this.name = "";
        }
        else {
            this.name = line.split("\"")[1];
        }

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public int getVertex1() {
        return vertex1;
    }

    public void setVertex1(int vertex1) {
        this.vertex1 = vertex1;
    }

    public int getVertex2() {
        return vertex2;
    }

    public void setVertex2(int vertex2) {
        this.vertex2 = vertex2;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
