/*
Created by maeroso at 01/12/2017.
 */

//package Dsatur;

import java.io.*;
import java.util.*;

public class Dsatur {
    static int[] degreesArray;
    static int mapSize;

    public static void main(String[] args) {
        LinkedHashMap<Integer, LinkedList<Integer>> map = Dsatur.read("graphtest");
        //mapSize = map.get(-1).get(0);

        degreesArray = Dsatur.calculateVerticesDegrees(map);
        Map<Integer, Integer> resultingColor = new LinkedHashMap<>();
        List<Integer> coloredVertices = new ArrayList<>();

        int[] coloring = new int[mapSize];
        for (int i = 0; i < mapSize; i++) {
            coloring[i] = -1;
        }
        List<Integer> notColored = new ArrayList<>();
        for (int i = 0; i < mapSize; i++) {
            notColored.add(i);
        }

        int highestDegreeVertex = Dsatur.getHighestDegreeVertex(degreesArray);
        coloring[highestDegreeVertex] = 0;
        coloredVertices.add(highestDegreeVertex);
        resultingColor.put(highestDegreeVertex, 0);
        notColored.remove(highestDegreeVertex);

        while (notColored.size() > 0) {
            int vertice = Dsatur.getHighestSaturation(map, coloring);
            while (resultingColor.containsKey(vertice)) {
                vertice = Dsatur.getHighestSaturation(map, coloring);
            }
            boolean[] availableColors = new boolean[mapSize];
            for (int j = 0; j < mapSize; j++) {
                availableColors[j] = true;
            }

            int lastColor = 0;
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
            notColored.remove((Object) vertice);
            coloredVertices.add(vertice);
            coloring[vertice] = lastColor;
        }
        System.out.println(resultingColor);
        writeOutputCSV(resultingColor);
    }


    public static int getHighestSaturation(LinkedHashMap<Integer, LinkedList<Integer>> graph, int[] coloring) {
        int maxSaturation = 0;
        int vertexWithMaxSaturation = 0;

        for (int i = 0; i < mapSize; i++) {
            if (coloring[i] == -1) {
                Set<Integer> colors = new TreeSet<>();
                for (int j = 0; j < mapSize; j++) {
                    if (Dsatur.areAdjacent(graph, i, j) && coloring[j] != -1) {
                        colors.add(coloring[j]);
                    }
                }
                int tempSaturation = colors.size();
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
        String csvSeparator = " ";
        LinkedHashMap<Integer, LinkedList<Integer>> vertexMap = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((row = br.readLine()) != null) {
                String[] columns = row.split(csvSeparator);
                int key = Integer.parseInt(columns[0]);
                int val = Integer.parseInt(columns[1]);
                if (mapSize == 0 && key == -2) {
                    mapSize = val;
                }
                else {
                    if (!vertexMap.containsKey(key)) {
                        vertexMap.put(key, new LinkedList<>());
                    }
                    if (!vertexMap.containsKey(val)) {
                        vertexMap.put(val, new LinkedList<>());
                    }
                    System.out.println(vertexMap);
                    System.out.println(key);
                    System.out.println(val);
                    vertexMap.get(key).add(val);
                    vertexMap.get(val).add(key);
                }
/*
                for (int i = 0, columnsLength = columns.length; i < columnsLength; i++) {
                    String column = columns[i].trim();
                    if (i == 0) {
                        continue;
                    }
                    int value = Integer.valueOf(column) - 1;
                    vertexMap.get(vertexCount).add(value);
                }
                vertexCount++;*/
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(vertexMap);
        return vertexMap;
    }

    public static void writeOutputCSV(Map<Integer, Integer> coloracaoResultante) {
        BufferedWriter bufferedWriter;

        try {
            FileWriter fileWriter = new FileWriter("output.csv");
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.append("Nó,Cor\n");

            for (Map.Entry<Integer, Integer> integerEntry : coloracaoResultante.entrySet()) {
                bufferedWriter.append(String.valueOf(integerEntry.getKey()))
                        .append(",")
                        .append(String.valueOf(integerEntry.getValue()))
                        .append("\n");
            }

            bufferedWriter.close();

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            if (degreeArray[i] > degreeArray[highestDegVertexIndex])
                highestDegVertexIndex = i;
        }

        return highestDegVertexIndex;
    }
}
