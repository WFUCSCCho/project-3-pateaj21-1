/**************************
 ∗ @file: Proj3.java
 ∗ @description: This program implements This program implements 5 different sorting
 algorithms (Merge, Quick, Heap, Bubble, & Odd-Even Transposition) to analyze and
 compare their performance on a Taylor Swift discography dataset under different
 conditions (sorted, shuffled, & reversed data).
 ∗ @author: Aashi Patel
 ∗ @date: November 14, 2024
 ************************/

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable<T>> void mergeSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(a, left, mid);
            mergeSort(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    public static <T extends Comparable<T>> void merge(ArrayList<T> a, int left, int mid, int right) {
        ArrayList<T> temp = new ArrayList<>(a);
        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            if (temp.get(i).compareTo(temp.get(j)) <= 0) {
                a.set(k++, temp.get(i++));
            } else {
                a.set(k++, temp.get(j++));
            }
        }
        while (i <= mid) {
            a.set(k++, temp.get(i++));
        }
    }

    // Quick Sort
    public static <T extends Comparable<T>> void quickSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(a, left, right);
            quickSort(a, left, pivotIndex - 1);
            quickSort(a, pivotIndex + 1, right);
        }
    }
    public static <T extends Comparable<T>> int partition(ArrayList<T> a, int left, int right) {
        T pivot = a.get(right);
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (a.get(j).compareTo(pivot) <= 0) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, right);
        return i + 1;
    }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable<T>> void heapSort(ArrayList<T> a, int left, int right) {
        for (int i = (right - left) / 2 - 1; i >= left; i--) {
            heapify(a, right + 1, i);
        }
        for (int i = right; i >= left; i--) {
            swap(a, left, i);
            heapify(a, i, left);
        }
    }

    public static <T extends Comparable<T>> void heapify(ArrayList<T> a, int size, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && a.get(left).compareTo(a.get(largest)) > 0) {
            largest = left;
        }
        if (right < size && a.get(right).compareTo(a.get(largest)) > 0) {
            largest = right;
        }
        if (largest != i) {
            swap(a, i, largest);
            heapify(a, size, largest);
        }
    }


    // Bubble Sort
    public static <T extends Comparable<T>> int bubbleSort(ArrayList<T> a, int size) {
        int comparisons = 0;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < size - 1; i++) {
                comparisons++;
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    swapped = true;
                }
            }
        } while (swapped);
        return comparisons;
    }


    // Odd-Even Transposition Sort
    public static <T extends Comparable<T>> int transpositionSort(ArrayList<T> a, int size) {
        int comparisons = 0;
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            // Odd phase
            for (int i = 1; i < size - 1; i += 2) {
                comparisons++;
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
            // Even phase
            for (int i = 0; i < size - 1; i += 2) {
                comparisons++;
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
        }
        return comparisons;
    }


    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 3) {
            System.err.println("Usage: java TestAvl <input file> <algorithm> <number of lines>");
            System.exit(1);
        }
        String inputFileName = args[0]; //filename of the dataset
        String algorithm = args[1]; // sorting algorithm type
        int numLines = Integer.parseInt(args[2]); // number of lines to read in the data set


        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line (header)
        inputFileNameScanner.nextLine();

        //Create an array list
        ArrayList<Taylor_Discography_Data> dataList = new ArrayList<Taylor_Discography_Data>();
        //Read file and store data
        for (int i = 0; i < numLines && inputFileNameScanner.hasNextLine(); i++) {
            String line = inputFileNameScanner.nextLine().trim();
            if (line.isEmpty()) continue;// skip  empty lines
            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Split CSV while respecting quotes

            try {
                // Ensure that each line has the expected 7 fields
                if (parts.length < 7) {
                    System.err.println("Incomplete data at line: " + line);
                    continue;
                }

                // Create a Taylor_Discography_Data object
                Taylor_Discography_Data data = new Taylor_Discography_Data(
                        parts[0].trim(),                     // album
                        parts[1].trim(),                     // track_title
                        Integer.parseInt(parts[2].trim()),   // track_number
                        parts[3].trim(),                     // released_year
                        parts[4].trim(),                     // lyrics
                        parts[5].trim(),                     // writers
                        parts[6].trim()                      // spotifyId
                );
                // Add the data object to the list
                dataList.add(data);

            } catch (NumberFormatException e) {
                System.err.println("Error parsing line: " + line);
            }
        }
        inputFileNameScanner.close();

        // print data before sorting
        System.out.println("Sorted dataset before operations: ");
        Collections.sort(dataList);
        try(FileWriter writer = new FileWriter("./sorted.txt")) {
            writer.write("Sorted dataset before operations: ");
            writer.write(".\n");
            for (Taylor_Discography_Data data : dataList) {
                writer.write(String.valueOf(data));
                writer.write(".\n");
            }
        }

        //Choose and execute the sorting algorithm
        int swapCount = 0;
        switch (algorithm) {
            case "bubble":
                //bubble sort running time for sorted, shuffled, reversed datasets
                Collections.sort(dataList);
                //time the sorting
                long startTime = System.nanoTime();
                swapCount = bubbleSort(dataList, dataList.size());
                long endTime = System.nanoTime();
                long Runtime = endTime - startTime;
                System.out.println("Sorted data bubble sort run time: " + Runtime);
                writeToFile( "Sorted data bubble sort run time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");
                System.out.println("# of swaps: " + swapCount);
                writeToFile( "# of swaps: " + String.valueOf(swapCount), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");

                // shuffled
                Collections.shuffle(dataList);
                startTime = System.nanoTime();
                swapCount = bubbleSort(dataList, dataList.size());
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Shuffled data bubble sort running time: " + Runtime);
                writeToFile( "Shuffled data bubble sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");
                System.out.println("# of swaps: " + swapCount);
                writeToFile( "# of swaps: " + String.valueOf(swapCount), "./analysis.txt");

                // reversed
                Collections.reverse(dataList);
                startTime = System.nanoTime();
                swapCount = bubbleSort(dataList, dataList.size());
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Reversed data bubble sort running time: " + Runtime);
                writeToFile("\n", "./analysis.txt");
                writeToFile( "Reversed data bubble sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");
                System.out.println("# of swaps: " + swapCount);
                writeToFile( "# of swaps: " + String.valueOf(swapCount), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");
                break;

            case "transposition":
                Collections.sort(dataList);
                // time sorting process
                startTime = System.nanoTime();
                swapCount = transpositionSort(dataList, dataList.size());
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Sorted data transposition sort running time: " + Runtime);
                writeToFile( "Sorted data transposition sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");
                System.out.println("# of swaps: " + swapCount);
                writeToFile( "# of swaps: " + String.valueOf(swapCount), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");

                // shuffled data set
                Collections.shuffle(dataList);
                startTime = System.nanoTime();
                swapCount = transpositionSort(dataList, dataList.size());
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Shuffled data transposition sort running time: " + Runtime);
                writeToFile( "Shuffled data transposition sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");
                System.out.println("# of comparisons: " + swapCount);
                writeToFile( "# of comparisons: " + String.valueOf(swapCount), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");

                // reversed data set
                Collections.reverse(dataList);
                startTime = System.nanoTime();
                swapCount = transpositionSort(dataList, dataList.size());
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Reversed data transposition sort running time: " + Runtime);
                writeToFile( "Reversed data transposition sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");
                System.out.println("# of comparisons: " + swapCount);
                writeToFile( "# of comparisons: " + String.valueOf(swapCount), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");
                break;
            case "merge":
                //merge sort running time for sorted, shuffled, reversed datasets
                Collections.sort(dataList);
                //Time  sorting process
                startTime = System.nanoTime();
                mergeSort(dataList,0,dataList.size()-1);
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Sorted data merge sort running time: " + Runtime);
                writeToFile( "Sorted data merge sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");


                //Shuffled data set
                Collections.shuffle(dataList);
                startTime = System.nanoTime();
                mergeSort(dataList,0,dataList.size()-1);
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Shuffled data merge sort running time: " + Runtime);
                writeToFile( "Shuffled data merge sort runnin time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");

                //Reversed data set
                Collections.reverse(dataList);
                startTime = System.nanoTime();
                mergeSort(dataList,0,dataList.size()-1);
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Reversed data merge sort running time: " + Runtime);
                writeToFile( "Reversed data merge sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");
                break;
            case "quick":
                //quick sort running time for sorted, shuffled, reversed datasets

                //sort the data
                Collections.sort(dataList);
                //time the sorting process
                startTime = System.nanoTime();
                quickSort(dataList,0,dataList.size()-1);
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Sorted data quick sort running time: " + Runtime);
                writeToFile( "Sorted data quick sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");

                //shuffled data set
                Collections.shuffle(dataList);
                startTime = System.nanoTime();
                quickSort(dataList,0,dataList.size()-1);
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Shuffled data quick sort running time: " + Runtime);
                writeToFile( "Shuffled data quick sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");
                //Reversed data set
                Collections.reverse(dataList);
                startTime = System.nanoTime();
                quickSort(dataList,0,dataList.size()-1);
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Reversed data quick sort running time: " + Runtime);
                writeToFile( "Reversed data quick sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");
                break;
            case "heap":
                //heap sort running time for sorted, shuffled, reversed datasets

                //sort the data
                Collections.sort(dataList);
                //time the sorting process
                startTime = System.nanoTime();
                heapSort(dataList,0,dataList.size()-1);
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Sorted data heap sort running time: " + Runtime);
                writeToFile( "Sorted data heap sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");

                //shuffled data set
                Collections.shuffle(dataList);
                startTime = System.nanoTime();
                heapSort(dataList,0,dataList.size()-1);
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Shuffled data heap sort running time: " + Runtime);
                writeToFile( "Shuffled data Heap sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");

                //Reversed data set
                Collections.reverse(dataList);
                startTime = System.nanoTime();
                heapSort(dataList,0,dataList.size()-1);
                endTime = System.nanoTime();
                Runtime = endTime - startTime;
                System.out.println("Reversed data heap sort running time: " + Runtime);
                writeToFile( "Reversed data Heap sort running time: " + String.valueOf(Runtime), "./analysis.txt");
                writeToFile("\n", "./analysis.txt");
                break;

            default:
                System.err.println("Error: Invalid sorting algorithm.");
                System.exit(1);
        }
    }

    //implement the writeToFile path.
    public static void writeToFile(String content, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            fileWriter.write(content);
            bufferedWriter.close();
            fileWriter.close();
        }
        catch (IOException ignored) {}
    }
}

