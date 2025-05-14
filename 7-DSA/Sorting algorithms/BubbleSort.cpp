#include <iostream>              // For input and output
#include <vector>                // For dynamic array (vector) use
#include <chrono>                // For time measurement
#include <omp.h>                 // For OpenMP parallelization
using namespace std;
using namespace std::chrono;     // For easier access to chrono types

// --------------------------------------
// FUNCTION: bubbleSortSequential
// PURPOSE: Implements standard bubble sort
// --------------------------------------
void bubbleSortSequential(vector<int>& arr) {
    int n = arr.size();
    // Outer loop runs (n-1) times
    for (int i = 0; i < n-1; ++i) {
        // Inner loop for each pair comparison
        for (int j = 0; j < n-i-1; ++j) {
            // Swap if left is greater than right
            if (arr[j] > arr[j+1])
                swap(arr[j], arr[j+1]);
        }
    }
}

// ---------------------------------------------------
// FUNCTION: bubbleSortParallel
// PURPOSE: Parallel Bubble Sort using Odd-Even phases
// ---------------------------------------------------
void bubbleSortParallel(vector<int>& arr) {
    int n = arr.size();
    // Repeat process n times (for full sorting)
    for (int i = 0; i < n; i++) {
        // EVEN INDEXED PHASE
        // Compare indices: (0,1), (2,3), ...
        #pragma omp parallel for
        for (int j = 0; j < n-1; j += 2) {
            if (arr[j] > arr[j+1])
                swap(arr[j], arr[j+1]);
        }

        // ODD INDEXED PHASE
        // Compare indices: (1,2), (3,4), ...
        #pragma omp parallel for
        for (int j = 1; j < n-1; j += 2) {
            if (arr[j] > arr[j+1])
                swap(arr[j], arr[j+1]);
        }
    }
}

// ---------------------------
// MAIN FUNCTION
// ---------------------------
int main() {
    int n;

    // Prompt user for size of array
    cout << "Enter number of elements: ";
    cin >> n;

    vector<int> arr(n);  // Original array
    cout << "Enter " << n << " elements:\n";
    for (int i = 0; i < n; ++i)
        cin >> arr[i];  // User input

    // Create copies of array for both sorting methods
    vector<int> arr_seq = arr;  // For sequential
    vector<int> arr_par = arr;  // For parallel

    // -------------------------------
    // TIME: Sequential Bubble Sort
    // -------------------------------
    auto start_seq = high_resolution_clock::now();   // Start time
    bubbleSortSequential(arr_seq);                   // Sort
    auto end_seq = high_resolution_clock::now();     // End time
    auto duration_seq = duration_cast<milliseconds>(end_seq - start_seq);  // Duration in ms

    // -------------------------------
    // TIME: Parallel Bubble Sort
    // -------------------------------
    auto start_par = high_resolution_clock::now();   // Start time
    bubbleSortParallel(arr_par);                     // Sort
    auto end_par = high_resolution_clock::now();     // End time
    auto duration_par = duration_cast<milliseconds>(end_par - start_par);  // Duration in ms

    // -------------------------------
    // OUTPUT: Sorted arrays
    // -------------------------------
    cout << "\nSorted array (Sequential): ";
    for (int x : arr_seq)
        cout << x << " ";

    cout << "\nSorted array (Parallel):   ";
    for (int x : arr_par)
        cout << x << " ";

    // -------------------------------
    // OUTPUT: Performance comparison
    // -------------------------------
    cout << "\n\nTime taken by Sequential Bubble Sort: " << duration_seq.count() << " ms";
    cout << "\nTime taken by Parallel Bubble Sort:   " << duration_par.count() << " ms\n";

    return 0;
}
