package util;

import java.util.ArrayList;
import java.util.Collections;
import Tabs.SorterSectionTab;
public class SortingAlg {

        public static class MergeSort<T extends Comparable<T>> {

            // Space complexity is O(n), time complexity is O(n log n)
            //Entry point for the algorithm
            public void mergeSort(ArrayList<T> arr, SorterSectionTab.SortDirection dir) {
                if (arr.size() < 2) {
                    return;
                }
                int mid = arr.size() / 2;
                ArrayList<T> left = new ArrayList<>(arr.subList(0, mid));
                ArrayList<T> right = new ArrayList<>(arr.subList(mid, arr.size()));
                mergeSort(left, dir);
                mergeSort(right, dir);
                merge(arr, left, right, dir);
            }

            public void merge(ArrayList<T> arr, ArrayList<T> left, ArrayList<T> right, SorterSectionTab.SortDirection dir) {
                int i = 0, j = 0, k = 0;
                while (i < left.size() && j < right.size()) {
                    if (compareObjects(left.get(i), right.get(j), dir) <= 0) {
                        arr.set(k++, left.get(i++));
                    } else {
                        arr.set(k++, right.get(j++));
                    }
                }
                while (i < left.size()) {
                    arr.set(k++, left.get(i++));
                }
                while (j < right.size()) {
                    arr.set(k++, right.get(j++));
                }
            }

            private int compareObjects(T obj1, T obj2, SorterSectionTab.SortDirection dir) {
                switch (dir) {
                    case ERROR_TYPE -> {
                        System.out.println("Error");
                        return -1;
                    }
                    case ASCENDING -> {
                        return obj1.compareTo(obj2);
                    }
                    case DESCENDING -> {
                        return obj2.compareTo(obj1);
                    }
                }
                return -1;
            }
        }

    //Space complexity is O(log n), time complexity is O(n log n) however worst case is O(n^2)
    public static class quickSort {

        public static void sort(ArrayList<String> arr, boolean asc) {
            quickSort(arr, 0, arr.size() - 1);
            if (!asc)
                Collections.reverse(arr); // Reverse the sorted ArrayList to get descending order
        }

        private static void quickSort(ArrayList<String> arr, int low, int high) {
            if (low < high) {
                int pivotIndex = partition(arr, low, high);
                quickSort(arr, low, pivotIndex - 1);
                quickSort(arr, pivotIndex + 1, high);
            }
        }

        private static int partition(ArrayList<String> arr, int low, int high) {
            String pivot = arr.get(high);
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (compareStrings(arr.get(j), pivot) > 0) { // If arr[j] > pivot
                    i++;
                    Collections.swap(arr, i, j);
                }
            }
            Collections.swap(arr, i + 1, high);
            return i + 1;
        }

        private static int compareStrings(String s1, String s2) {
            // Implement string comparison logic here, e.g. using the compareTo method
            // In this case, we compare the strings lexicographically in reverse order
            return s2.compareTo(s1);
        }

    }
}
