public class Vertex {//151 vertices
    private int index;
    private String label;
    private int x;
    private int y;
    private String name;

    public Vertex(String line) {
        String info[] = line.split(" ");
        this.index = Integer.parseInt(info[0]);
        this.label = info[1];
        this.x = Integer.parseInt(info[2]);
        this.y = Integer.parseInt(info[3]);
        this.name = line.split("\"")[1];
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
