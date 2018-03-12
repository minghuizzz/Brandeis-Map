import java.io.*;

import java.util.Scanner;


public class Map {
    public final static int numofVertices = 152;
    public final static int numofEdges = 596;
    public final static int MapWidthFeet = 5521; /*Width in feet of map.*/
    public final static int MapHeightFeet = 4369; /*Height in feet of map.*/
    public final static int MapWidthPixels = 2528; /*Width in pixels of map.*/
    public final static int MapHeightPixels = 2000; /*Height in pixels of map.*/
    public final static int CropLeft = 150; /*Pixels cropped from left of map.*/
    public final static int CropDown = 125; /*Pixels cropped from top of map.*/
    public final static String verticesPath = "file/MapDataVertices.txt";
    public final static String edgesPath = "file/MapDataEdges.txt";
    public final static String routePath = "file/Route.txt";
    public final static String routeCPath = "file/RouteCropped.txt";
    public final static String tourPPath = "file/Route.txt";
    public final static String tourPPPath = "file/RouteCropped.txt";

    public static double min = 0;


    public static void main(String[] args) {
        //read vertices, edges and construct graph
//        String verticesInfo[] = readLines("src/file/MapDataVertices.txt");
//        String edgesInfo[] = readLines("src/file/MapDataEdges.txt");
        String verticesInfo[] = readLines(verticesPath);
        String edgesInfo[] = readLines(edgesPath);
        Vertex vertices[] = toVertices(verticesInfo);
        Edge edges[] = toEdges(edgesInfo);
        ENode[] graph = constructGraph(vertices, edges);

        //input
        Scanner sc = new Scanner(System.in);
        System.out.println("************* WELCOME TO THE BRANDEIS MAP *************");
        Vertex startVer = null;
        while(startVer == null) {
            System.out.println("Enter start (return to quit):");
            String start = sc.nextLine();
            if(start.equals("")) {
                return;
            }
            startVer = findVertex(vertices, start);
            if(startVer == null) {
                System.out.println("Cannot find your start, please enter again.");
            }
        }

        Vertex endVer = null;
        Tree tour = new Tree(startVer );
        while(endVer == null) {
            System.out.println("Enter finish (or return to do a tour):");
            String finish = sc.nextLine();
            if(finish.equals("")){
                tour = Tour.prim(vertices, edges, graph, startVer);
                try {
//                    File tourP = new File("src/file/OutputTourP.txt");
//                    File tourPP = new File("src/file/OutputTourPP.txt");
                    File tourP = new File(tourPPath);
                    File tourPP = new File(tourPPPath);

//                    File route = new File("src/file/route.txt");
//                    File routeCropped = new File("src/file/routeCropped.txt");
                    File route = new File(routePath);
                    File routeCropped = new File(routeCPath);

                    tourP.createNewFile();
                    tourPP.createNewFile();
                    route.createNewFile();
                    routeCropped.createNewFile();
                    FileWriter fw0 = new FileWriter(tourP);
                    BufferedWriter out0 = new BufferedWriter(fw0);
                    FileWriter fw = new FileWriter(tourPP);
                    BufferedWriter out = new BufferedWriter(fw);
                    FileWriter fw1 = new FileWriter(route);
                    BufferedWriter out1 = new BufferedWriter(fw1);
                    FileWriter fw2 = new FileWriter(routeCropped);
                    BufferedWriter out2 = new BufferedWriter(fw2);
                    traverseTree(tour.root, out, out0, out1, out2, graph, vertices);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;

            }//tour
            endVer = findVertex(vertices, finish);
            if(endVer == null) {
                System.out.println("Cannot find your finish, please enter again.");
            }
        }



        System.out.println("Have a skateboard (y/n - default=n)?");
        String skate = sc.nextLine();
        boolean withSkate = false;
        if(skate.toLowerCase().equals("y")) {
            withSkate = true;
        }

        System.out.println("Minimize time (y/n - default=n)?");
        String mt = sc.nextLine();
        boolean minTime = false;
        if(mt.toLowerCase().equals("y")) {
            minTime = true;
        }



        VNode path = Dijkstra(vertices, edges, graph, startVer, endVer, withSkate, minTime);
        VNode tmp = path;
        int legs = 0;
        int totalDis = 0;
        double totalTime = 0;
        while(tmp.next != null) {
            legs++;
            Edge e = betweenEdge(tmp.val, tmp.next.val, graph);
            totalDis += e.getLength();
            double length = e.getLength();
            double factor = getFactor(e, withSkate);
            double speed = 272 * factor;
            double time = length / speed;
            totalTime += time;

            System.out.println("FROM: (" + vertices[e.getVertex1()].getLabel() + ") " + vertices[e.getVertex1()].getName());
            if(!e.getName().equals("")) {
                System.out.println("ON: " + e.getName());
            }
            if(glide(e, withSkate)) {
                System.out.println("Glide " + e.getLength() + " feet in direction " + e.getAngle() + " " + e.getDirection());
            }
            else {
                System.out.println("Walk " + e.getLength() + " feet in direction " + e.getAngle() + " " + e.getDirection());
            }
            System.out.println("TO: (" + vertices[e.getVertex2()].getLabel() + ") " + vertices[e.getVertex2()].getName());
            System.out.println("(" + timeToString(time) + ")");
            System.out.println("");
            tmp = tmp.next;
        }

        System.out.println("legs = " + legs + ", distance = " + totalDis + " feet, time = " + timeToString(totalTime));

        outputRouteFile(path, vertices, graph, "file");

    }

    public static String[] readLines(String filePath) {
        File file = new File(filePath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] lines = new String(filecontent).split("\n");

        return lines;
    }

    public static void outputRouteFile(VNode path, Vertex[] vertices, ENode[] graph, String filePath) {
        if(path.next == null) {
            return;
        }
        try {
//            File route = new File("src/file/Route.txt");
//            File routeCropped = new File("src/file/RouteCropped.txt");
            File route = new File(routePath);
            File routeCropped = new File(routeCPath);

            route.createNewFile();
            routeCropped.createNewFile();
            FileWriter fw1 = new FileWriter(route);
            FileWriter fw2 = new FileWriter(routeCropped);
            BufferedWriter out1 = new BufferedWriter(fw1);
            BufferedWriter out2 = new BufferedWriter(fw2);

            while(path.next != null) {
                Edge edge = betweenEdge(path.val, path.next.val, graph);
                double v = vertices[edge.getVertex1()].getX();
                double w = vertices[edge.getVertex1()].getY();
                double x = vertices[edge.getVertex2()].getX();
                double y = vertices[edge.getVertex2()].getY();
                double a = v * MapHeightPixels / MapHeightFeet;
                double b = w * MapWidthPixels / MapWidthFeet;
                double c = x * MapHeightPixels / MapHeightFeet;
                double d = y * MapWidthPixels / MapWidthFeet;

                out1.write((int)a + " " + (int)b + " " + (int)c + " " + (int)d + "\r\n");
                out1.flush();

                a = a - CropLeft;
                b = b - CropDown;
                c = c - CropLeft;
                d = d - CropDown;

                out2.write((int)a + " " + (int)b + " " + (int)c + " " + (int)d + "\r\n");
                out2.flush();
                path = path.next;

            }
            out1.close();
            out2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Vertex[] toVertices(String[] verticesInfo) {
        int i = 0;
        Vertex vertices[] = new Vertex[numofVertices];
        for(String s : verticesInfo) {
            if(!s.startsWith("//") && !s.equals("")) {
                Vertex vertex = new Vertex(s);
                vertices[i++] = vertex;
            }
        }
        return vertices;
    }

    public static Edge[] toEdges(String[] edgesInfo) {
        int i = 0;
        Edge edges[] = new Edge[numofEdges];
        for(String s : edgesInfo) {
            if(!s.startsWith("//") && !s.equals("")) {
                Edge edge = new Edge(s);
                edges[i++] = edge;
            }
        }
        return edges;
    }

    public static Vertex findVertex(Vertex[] vertices, String start) {
        for(Vertex v : vertices) {
            if(start.toLowerCase().equals(v.getLabel().toLowerCase())) {
                return v;
            }
        }
        for(Vertex v : vertices) {
            if(v.getName().toLowerCase().contains(start.toLowerCase())) {
                return v;
            }
        }
        return null;

    }

    public static ENode[] constructGraph(Vertex[] vertices, Edge[] edges) {
        ENode[] graph = new ENode[numofVertices];
        for(Edge edge : edges) {
            int index = edge.getVertex1();
            if(graph[index] == null) {
                graph[index] = new ENode(edge);
            }
            else {
                ENode tmp = graph[index];
                graph[index] = new ENode(edge);
                graph[index].next = tmp;
            }
        }
        return graph;
    }

    public static VNode Dijkstra(Vertex[] vertices, Edge[] edges, ENode[] graph,
                                Vertex start, Vertex end, boolean skate, boolean minT) {//Dijkstra's Algorithm
        double dis[] = new double[numofVertices];
        boolean marked[] = new boolean[numofVertices];
        int ref[] = new int[numofVertices];
        int markedsize = 0;
        Heap heap = new Heap(numofVertices);

        for(int i = 0; i < numofVertices; i++) {
            dis[i] = Integer.MAX_VALUE;
            heap.insert(dis[i], i);
        }
        heap.update(0, start.getIndex());
        dis[start.getIndex()] = 0;
        ref[start.getIndex()] = -1;
        while(markedsize < numofVertices - 1) {
            int index = heap.deleteMin();

            marked[index] = true;
            markedsize++;

            ENode es = graph[index];
            while(es != null) {
                int i = es.val.getVertex2();
                if(!marked[i]) {
                    Edge edge = es.val;
                    if(edge != null) {
                        double length = edge.getLength();
                        double factor = getFactor(edge, skate);
                        double speed = 272 * factor;
                        double time = length / speed;
                        if(minT) {
                            if(dis[i] > dis[index] + time) {
                                dis[i] = dis[index] + time;
                                ref[i] = index;
                            }
                        }
                        else {
                            if(dis[i] > dis[index] + length) {
                                dis[i] = dis[index] + length;
                                ref[i] = index;
                            }
                        }
                        heap.update(dis[i], i);
                    }
                }
                es = es.next;
            }
        }
        VNode result = new VNode();
        int index = end.getIndex();
        while(ref[index] != -1) {
            VNode tmp = result.next;
            result.next = new VNode(vertices[index]);
            result.next.next = tmp;
            index = ref[index];
        }
        VNode tmp = result.next;
        result.next = new VNode(vertices[start.getIndex()]);
        result.next.next = tmp;
        return result.next;
    }


    public static boolean glide(Edge edge, boolean skate) {
        String C = edge.getC();
        if(skate) {
            if(C.equals("U") || C.equals("D") || C.equals("F") || C.equals("x")) {
                return true;
            }
        }
        return false;
    }


    public static double getFactor(Edge edge, boolean skate) {
        String C = edge.getC();
        double factor = 1.0;
        if(skate) {
            switch (C) {
                case "U":
                    factor = 1.1;
                    break;
                case "D":
                    factor = 5.0;
                    break;
                case "F":
                    factor = 2.0;
                    break;
                case "x":
                    factor = 2.0;
                    break;
                case "u":
                    factor = 0.9;
                    break;
                case "d":
                    factor = 1.1;
                    break;
                case "s":
                    factor = 0.5;
                    break;
                case "t":
                    factor = 0.9;
                    break;
                case "f":
                    factor = 1.0;
                    break;
                case "b":
                    factor = 1.0;
                    break;
                default:
                    factor = 1.0;
            }
        }
        else {
            C = C.toLowerCase();
            switch (C) {
                case "u":
                    factor = 0.9;
                    break;
                case "d":
                    factor = 1.1;
                    break;
                case "s":
                    factor = 0.5;
                    break;
                case "t":
                    factor = 0.9;
                    break;
                default:
                    factor = 1.0;
            }
        }
        return factor;
    }


    public static Edge betweenEdge(Vertex v1, Vertex v2, ENode[] graph) {
        ENode en = graph[v1.getIndex()];

        while(en != null) {
            if(en.val.getVertex2() == v2.getIndex()) {
                return en.val;
            }
            else {
                en = en.next;
            }
        }

        return null;
    }

    public static String timeToString(double time) {
        String result = "";
        if(time >= 1.0) {
            result = String.format("%.1f", time);
            if(result.equals("1.0")) {
                result = result + " minute";
            }
            else {
                result = result + " minutes";
            }
            return result;
        }
        else {
            double sec = time * 60;
            result = String.format("%.0f",sec);
            if(result.equals("1")) {
                result = result + " second";
            }
            else {
                result = result + " seconds";
            }
            return result;
        }
    }

    public static void traverseTree(TNode tmp, BufferedWriter out,BufferedWriter out0, BufferedWriter out1, BufferedWriter out2, ENode[] graph, Vertex[] vertices) {
        if(tmp == null) {
            return;
        }

        try {
            out.write(tmp.val.getLabel() + " " + tmp.val.getName() + "\r\n");
            out.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }
        TNode sons = tmp.sons;
        while(sons != null) {
            Edge edge = betweenEdge(tmp.val, sons.val, graph);
            try {
                out0.write(edge.getLabel1() +" to " + edge.getLabel2() + "\r\n");
                out0.flush();

                double v = vertices[edge.getVertex1()].getX();
                double w = vertices[edge.getVertex1()].getY();
                double x = vertices[edge.getVertex2()].getX();
                double y = vertices[edge.getVertex2()].getY();
                double a = v * MapHeightPixels / MapHeightFeet;
                double b = w * MapWidthPixels / MapWidthFeet;
                double c = x * MapHeightPixels / MapHeightFeet;
                double d = y * MapWidthPixels / MapWidthFeet;

                out1.write((int)a + " " + (int)b + " " + (int)c + " " + (int)d + "\r\n");
                out1.flush();

                a = a - CropLeft;
                b = b - CropDown;
                c = c - CropLeft;
                d = d - CropDown;

                out2.write((int)a + " " + (int)b + " " + (int)c + " " + (int)d + "\r\n");
                out2.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }



            traverseTree(sons, out, out0, out1, out2, graph, vertices);
            try {
                out0.write(edge.getLabel2() +" back to " + edge.getLabel1() + "\r\n");
                out0.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
            sons = sons.next;
        }
    }
}
