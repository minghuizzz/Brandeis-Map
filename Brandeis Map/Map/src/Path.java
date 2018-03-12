import java.util.List;

public class Path {


    public List<Edge> getPath() {
        return path;
    }

    public void setPath(List<Edge> path) {
        this.path = path;
    }

    public List<Double> getTimes() {
        return times;
    }

    public void setTimes(List<Double> times) {
        this.times = times;
    }

    public int getNumofEdges() {
        return numofEdges;
    }

    public void setNumofEdges(int numofEdges) {
        this.numofEdges = numofEdges;
    }

    public double getTotalDis() {
        return totalDis;
    }

    public void setTotalDis(double totalDis) {
        this.totalDis = totalDis;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    private List<Edge> path;
    private List<Double> times;
    private int numofEdges;
    private double totalDis;
    private double totalTime;

    public Path() {//cannot reach condition
        this.numofEdges = -1;
    }

    public Path(List<Edge> path, List<Double> times, int numofEdges, double totalDis, double totalTime) {
        this.path = path;
        this.times = times;
        this.numofEdges = numofEdges;
        this.totalDis = totalDis;
        this.totalTime = totalTime;
    }




}
