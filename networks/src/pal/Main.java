package pal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by vojta on 11/15/16.
 */
public class Main {
    static StringTokenizer st;
    static int N, M;
    static Vertex[] allVertices;
    static int typeOneNetworks = 1, typeTwoNetworks = 0;

    public static void main(String args[]) throws IOException {
        loadGraph();
        buildCertificate();

        System.out.println(Math.min(typeOneNetworks, typeTwoNetworks) + " " + Math.max(typeOneNetworks, typeTwoNetworks));
    }

    static void buildCertificate() {
        Queue<Vertex> leafs = new LinkedList<>();
        for (Vertex v : allVertices) {
            if (v.numAdj == 1) {
                leafs.add(v);
            }
        }
        while (!leafs.isEmpty()) {
            Vertex leaf = leafs.poll();
            leaf.processed = true;
            leaf.numAdj--;

            Vertex parent = leaf.getParent();
            parent.numAdj--;
            if (parent.numAdj == 1) {
                //is a new leaf
                leafs.add(parent);
                setVertexCertificate(parent);
            }
        }
        LinkedList<Vertex> servers = new LinkedList<>();

        for (Vertex v : allVertices) {
            if (!v.processed) {
                servers.add(v);
                setVertexCertificate(v);
            }
        }

        for (int i = 1; i < servers.size(); i++) {
            Vertex server = servers.get(i);
            if (server.cert.equals(servers.get(0).cert)) {
                typeOneNetworks++;
            } else {
                typeTwoNetworks++;
            }
        }
    }

    static void setVertexCertificate(Vertex forVertex) {
        List<String> Y = new ArrayList<>();
        for (Vertex v : forVertex.adjList) {
            if (v.processed) {
                Y.add(v.cert);
            }
        }

        Collections.sort(Y);
        forVertex.cert = "0" + String.join("", Y) + "1";
    }

    static void loadGraph() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        allVertices = new Vertex[N];

        int fromID, toID;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            fromID = Integer.parseInt(st.nextToken());
            toID = Integer.parseInt(st.nextToken());
            ensureNotNull(fromID, toID);

            allVertices[fromID].adjList.add(allVertices[toID]);
            allVertices[fromID].numAdj++;
            allVertices[toID].numAdj++;
            allVertices[toID].adjList.add(allVertices[fromID]);
        }
    }

    static void ensureNotNull(int fromID, int toID) {
        if (allVertices[fromID] == null) {
            allVertices[fromID] = new Vertex(fromID);
        }
        if (allVertices[toID] == null) {
            allVertices[toID] = new Vertex(toID);
        }
    }

    static class Vertex implements Comparable<Vertex> {
        public int label;
        public String cert = "01";
        public ArrayList<Vertex> adjList;
        public int numAdj = 0;
        public boolean processed = false;

        public Vertex(int label) {
            this.label = label;
            adjList = new ArrayList<>();
        }

        public Vertex getParent() {
            for (Vertex v : adjList) {
                if (!v.processed) {
                    return v;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return "label: " + label + ", processed: " + processed;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vertex)) return false;
            Vertex vertex = (Vertex) o;

            return label == vertex.label;
        }

        @Override
        public int hashCode() {
            return label;
        }

        @Override
        public int compareTo(Vertex o) {
            if (numAdj < o.numAdj) {
                return -1;
            } else if (numAdj == o.numAdj) {
                return 0;
            }
            return 1;
        }
    }
}
