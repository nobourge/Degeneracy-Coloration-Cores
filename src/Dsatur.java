/*
Adapted from maeroso's version
 */


import java.io.*;
import java.util.*;

public class Dsatur {
    static int[] degreesArray;
    static int mapSize;

    public static void main(String[] args) {
        LinkedHashMap<Integer, LinkedList<Integer>> map = Dsatur.read("graphtest");
        int colorNbr = 0;
        degreesArray = Dsatur.calculateVerticesDegrees(map);
        Map<Integer, Integer> resultingColor = new LinkedHashMap<>();
        List<Integer> coloredVertices = new ArrayList<>();

        int[] coloring = new int[mapSize];
        int notColored = mapSize;
        for (int i = 0; i < mapSize; i++) { coloring[i] = -1; }

        int highestDegreeVertex = Dsatur.getHighestDegreeVertex(degreesArray);
        coloring[highestDegreeVertex] = 0;
        coloredVertices.add(highestDegreeVertex);
        resultingColor.put(highestDegreeVertex, 0);
        notColored -= 1;

        int lastColor = 0;
        boolean[] availableColors = new boolean[mapSize];
        int vertice = Dsatur.getHighestSaturation(map, coloring);
        while (notColored > 0) {
            while (resultingColor.containsKey(vertice)) {
                vertice = Dsatur.getHighestSaturation(map, coloring);
            }
            for (int j = 0; j < mapSize; j++) {
                availableColors[j] = true;
            }
            for (int currentVertex : coloredVertices) {
                if (Dsatur.areAdjacent(map, vertice, currentVertex)) {
                    int color = resultingColor.get(currentVertex);
                    availableColors[color] = false;
                }
            }
            for (int j = 0; j < availableColors.length; j++) {
                if (availableColors[j]) {
                    lastColor = j;
                    break;
                }
            }
            resultingColor.put(vertice, lastColor);
            notColored -= 1;
            coloredVertices.add(vertice);
            coloring[vertice] = lastColor;
            System.out.println(notColored);
            if (lastColor > colorNbr) { colorNbr = lastColor; }
        }
        System.out.println(resultingColor);
        System.out.println(colorNbr+1);
    }

    public static int getHighestSaturation(LinkedHashMap<Integer, LinkedList<Integer>> graph, int[] coloring) {
        int maxSaturation = 0;
        int vertexWithMaxSaturation = 0;
        Set<Integer> colors;
        int tempSaturation;

        for (int i = 0; i < mapSize; i++) {
            if (coloring[i] == -1) {
                colors = new TreeSet<>();
                tempSaturation = 0;
                for (int j = 0; j < mapSize; j++) {
                    if (Dsatur.areAdjacent(graph, i, j) && coloring[j] != -1) {
                        colors.add(coloring[j]);
                        tempSaturation += 1;
                    }
                }
                if (tempSaturation > maxSaturation) {
                    maxSaturation = tempSaturation;
                    vertexWithMaxSaturation = i;
                } else if (tempSaturation == maxSaturation && degreesArray[i] >= degreesArray[vertexWithMaxSaturation]) {
                    vertexWithMaxSaturation = i;
                }
            }
        }
        return vertexWithMaxSaturation;
    }

    private static boolean areAdjacent(LinkedHashMap<Integer, LinkedList<Integer>> graph, int i, int j) {
        return graph.get(i).contains(j);
    }

    public static LinkedHashMap<Integer, LinkedList<Integer>> read(String csvFile) {
        String row;
        LinkedHashMap<Integer, LinkedList<Integer>> vertexMap = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((row = br.readLine()) != null) {
                String[] columns = row.split(" ");
                int key = Integer.parseInt(columns[0]);
                int val = Integer.parseInt(columns[1]);
                if (mapSize == 0 && key == -2) { mapSize = val; }
                else {
                    if (!vertexMap.containsKey(key)) { vertexMap.put(key, new LinkedList<>()); }
                    if (!vertexMap.containsKey(val)) { vertexMap.put(val, new LinkedList<>()); }
                    vertexMap.get(key).add(val);
                    vertexMap.get(val).add(key);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        System.out.println("file parsing done");
        return vertexMap;
    }

    public static int[] calculateVerticesDegrees(LinkedHashMap<Integer, LinkedList<Integer>> m) {
        int[] degreeArray = new int[mapSize];
        for (Map.Entry<Integer, LinkedList<Integer>> listEntry : m.entrySet()) {
            int k = listEntry.getKey();
            int s = listEntry.getValue().size();
            degreeArray[k] = s;
        }
        return degreeArray;
    }

    public static int getHighestDegreeVertex(int[] degreeArray) {
        int highestDegVertexIndex = 0;
        for (int i = 0; i < degreeArray.length; i++) {
            if (degreeArray[i] > degreeArray[highestDegVertexIndex]) { highestDegVertexIndex = i; }
        }
        return highestDegVertexIndex;
    }
}
