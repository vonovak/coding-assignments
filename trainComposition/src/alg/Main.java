package alg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static int[] weights;
    static ArrayList<Integer>[] predecessors; //array of arraylists
    static int maxWeight = 0;
    static ArrayList<Integer> maxTrain;
    static ArrayList<Integer> tempTrain;
    static int nOfWagons;

    public static void main(String[] args) throws IOException {
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(System.in));

        nOfWagons = Integer.valueOf(br.readLine());
        weights = new int[nOfWagons];
        predecessors = new ArrayList[nOfWagons];
        maxTrain = new ArrayList<Integer>();
        tempTrain = new ArrayList<Integer>(nOfWagons);
        StringTokenizer st;
        int index;

        for (int i = 0; i < nOfWagons; i++) {
            st = new StringTokenizer(br.readLine());
            index = Integer.valueOf(st.nextToken()) - 1;
            weights[index] = Integer.valueOf(st.nextToken());
            predecessors[index] = new ArrayList<Integer>(st.countTokens());
            while (st.hasMoreTokens()) {
                predecessors[index].add(Integer.valueOf(st.nextToken()));
            }
        }

        //input processed, now do the job of finding the optimal train and print it out
        findMaxTrain(0);
        System.out.print("0 ");
        for (Integer aMaxTrain : maxTrain) {
            System.out.print(aMaxTrain + " ");
        }
        System.out.println("\n" + maxWeight);
    }

    /*
    * findMaxTrain is a recursive function which goes through all possible train configurations
    * */
    static void findMaxTrain(int predecessorID) {
        //loop across all wagons
        for (int i = 0; i < predecessors.length; i++) {
            // and all their possible predecessors
            for (int j = 0; j < predecessors[i].size(); j++) {

                if (predecessors[i].get(j) == predecessorID) {
                     /*
                     * we found a possible successor wagon. Check if it already is in the train.
                     * iIf not, add it.
                     * while calling contains() on ArrayList is O(n),
                     * our n is guaranteed to be <=30 which is acceptable
                     * */
                    boolean notYetInTrain = !tempTrain.contains(i + 1);
                    if (notYetInTrain) {
                        tempTrain.add(i + 1);
                        findMaxTrain(i + 1);
                    }
                }
            }
        }

        /*
        * a train stored in tempTrain is now complete with all its wagons
        * */

        int sum = 0;
        for (Integer id : tempTrain) {
            sum += weights[id - 1];
        }
        /*
        * replace the maxTrain by current train if current train's weight is higher than max
        * the case when (sum == maxWeight AND sort order is lower) never happens thanks to the execution
        * order - we start from lower train ids
        * */
        if (sum > maxWeight) {
            maxWeight = sum;
            maxTrain.clear();
            maxTrain.addAll(tempTrain);
        }

        /*
        * remove the last wagon and check other possible configurations
        * given wagons in tempTrain by continuing higher up the call stack
        * */
        if (!tempTrain.isEmpty()) {
            tempTrain.remove(tempTrain.size() - 1);
        }
    }
}