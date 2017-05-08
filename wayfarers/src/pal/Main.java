package pal;

import java.io.*;
import java.util.*;

public class Main {
    static StringTokenizer st;
    static int N, M, W, D;
    //list of neighbours for each node
    static LinkedList<Integer>[] nodeNeighbors;
    //index and lowlink used in Tarjan's algorithm
    static boolean[] isHome;
    static int[] indices;
    static int[] lowlinks;
    // for each node, have a 'superNode' which is the representant of its strongly connected component (SCC)
    static int[] superNode;
    // distances used in searching for the longest path
    static int[] maxNumberOfCitiesFromNodeToHome;

    static int index = 0;
    static Stack<Integer> stack = new Stack<>();
    static Set<Integer>[] homesReachingToNode;


    public static void main(String args[]) throws IOException {
        loadGraph();
        //run tarjan from destination, the graph is reversed. we do not need to run tarjan from any other nodes!
        findAndWalkThroughSCCs(D);
        System.out.println(maxNumberOfCitiesFromNodeToHome[D]);
    }

    /*
    * Modified Tarjan's algorithm for finding strongly connected components.
    * It also gives us the topological ordering which we take advantage of.
    * */
    private static void findAndWalkThroughSCCs(int vertexID) {
        ++index;
        indices[vertexID] = index;
        lowlinks[vertexID] = index;

        stack.push(vertexID);

        int nbId;
        for (Integer neighbor : nodeNeighbors[vertexID]) {
            nbId = Math.abs(neighbor);
            if (indices[nbId] == 0) { //neighbor not yet visited
                findAndWalkThroughSCCs(nbId);
                lowlinks[vertexID] = Math.min(lowlinks[vertexID], lowlinks[nbId]);
            } else if (stack.lastIndexOf(nbId) != -1) {
                lowlinks[vertexID] = Math.min(lowlinks[vertexID], indices[nbId]);
            }
        }

        if (lowlinks[vertexID] == indices[vertexID]) {
            walkThroughScc(vertexID);
        }
    }

    /*
    * An SCC was found. Let's walk though it and find the largest amount of cities (C) on a way.
    * We go from homes to destination and gradually find C.
    * */
    private static void walkThroughScc(int vertexID){
        int nodeInSCCid;
        int maxNeighboringSuperNodeScore = 0;
        int sccSize = 0;

        do {
            nodeInSCCid = stack.pop();
            superNode[nodeInSCCid] = vertexID;
            sccSize++;
            if (isHome[nodeInSCCid]) {
                homesReachingToNode[vertexID].add(nodeInSCCid);
            }

            int toRepre;
            for (Integer neighbor : nodeNeighbors[nodeInSCCid]) {
                toRepre = superNode[neighbor];
                if (vertexID != toRepre && toRepre != 0) {
                    if (homesReachingToNode[vertexID].size() != W) {
                        // only add when necessary, since this is expensive
                        homesReachingToNode[vertexID].addAll(homesReachingToNode[toRepre]);
                    }
                    if (maxNeighboringSuperNodeScore < maxNumberOfCitiesFromNodeToHome[toRepre]) {
                        maxNeighboringSuperNodeScore = maxNumberOfCitiesFromNodeToHome[toRepre];
                    }
                }
            }
        } while (nodeInSCCid != vertexID);
        if (homesReachingToNode[vertexID].size() == W) {
            maxNumberOfCitiesFromNodeToHome[vertexID] += (sccSize + maxNeighboringSuperNodeScore);
        }
    }

    private static void loadGraph() throws IOException {
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        N = Integer.valueOf(st.nextToken());

        N++;
        M = Integer.valueOf(st.nextToken());
        W = Integer.valueOf(st.nextToken());
        D = Integer.valueOf(st.nextToken());
        st = new StringTokenizer(br.readLine());

        isHome = new boolean[N];

        int homeId;
        while (st.hasMoreTokens()) {
            homeId = Integer.valueOf(st.nextToken());
            isHome[homeId] = true;
        }

        nodeNeighbors = new LinkedList[N];

        homesReachingToNode = new HashSet[N];
        for (int i = 0; i < homesReachingToNode.length; i++) {
            homesReachingToNode[i] = new HashSet<>();
        }
        indices = new int[N];
        lowlinks = new int[N];
        superNode = new int[N];
        maxNumberOfCitiesFromNodeToHome = new int[N];
        for (int i = 0; i < N; i++) {
            nodeNeighbors[i] = new LinkedList<>();
        }

        int fromID, toID;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            fromID = Integer.valueOf(st.nextToken());
            toID = Integer.valueOf(st.nextToken());
            nodeNeighbors[toID].add(fromID);
        }
    }
}