#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <chrono>
#include <omp.h>

using namespace std;
using namespace std::chrono;

// Merge two sorted halves
void merge(vector<int>& arr, int left, int mid, int right) {
    vector<int> temp(right - left + 1);
    int i = left, j = mid + 1, k = 0;

    while (i <= mid && j <= right) {
        temp[k++] = (arr[i] < arr[j]) ? arr[i++] : arr[j++];
    }

    while (i <= mid) temp[k++] = arr[i++];
    while (j <= right) temp[k++] = arr[j++];

    for (int i = 0; i < temp.size(); ++i) {
        arr[left + i] = temp[i];
    }
}

// Sequential Merge Sort
void mergeSortSequential(vector<int>& arr, int left, int right) {
    if (left < right) {
        int mid = (left + right) / 2;
        mergeSortSequential(arr, left, mid);
        mergeSortSequential(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }
}

// Parallel Merge Sort with depth limit
void mergeSortParallel(vector<int>& arr, int left, int right, int depth = 0) {
    if (left < right) {
        int mid = (left + right) / 2;

        if (depth <= 3) {
            #pragma omp parallel sections
            {
                #pragma omp section
                mergeSortParallel(arr, left, mid, depth + 1);

                #pragma omp section
                mergeSortParallel(arr, mid + 1, right, depth + 1);
            }
        } else {
            mergeSortParallel(arr, left, mid, depth + 1);
            mergeSortParallel(arr, mid + 1, right, depth + 1);
        }

        merge(arr, left, mid, right);
    }
}

int main() {
    int n;
    cout << "Enter number of elements: ";
    cin >> n;

    vector<int> arr(n);
    srand(time(0));
    for (int i = 0; i < n; ++i)
        arr[i] = rand() % 100000;

    vector<int> arr_seq = arr;
    vector<int> arr_par = arr;

    // Sequential timing
    auto start_seq = high_resolution_clock::now();
    mergeSortSequential(arr_seq, 0, n - 1);
    auto end_seq = high_resolution_clock::now();
    auto duration_seq = duration_cast<milliseconds>(end_seq - start_seq);

    // Parallel timing
    auto start_par = high_resolution_clock::now();
    mergeSortParallel(arr_par, 0, n - 1);
    auto end_par = high_resolution_clock::now();
    auto duration_par = duration_cast<milliseconds>(end_par - start_par);

    cout << "\nTime taken by Sequential Merge Sort: " << duration_seq.count() << " ms";
    cout << "\nTime taken by Parallel Merge Sort:   " << duration_par.count() << " ms\n";

    return 0;
}
