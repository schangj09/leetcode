# code_snippet
## Summary
The code snippet is a method called `findDifference` in the `Solution` class. It takes two integer arrays as inputs and returns a list of lists of integers. The method finds the difference between the two input arrays by comparing the elements in each array and adding the elements that are present in one array but not in the other to separate lists.

## Example Usage
```java
int[] nums1 = {1, 2, 3};
int[] nums2 = {2, 3, 4};
Solution solution = new Solution();
List<List<Integer>> result = solution.findDifference(nums1, nums2);
System.out.println(result);
```
Expected Output:
```
[[1], [4]]
```

## Code Analysis
### Inputs
- `nums1`: An integer array representing the first set of numbers.
- `nums2`: An integer array representing the second set of numbers.
___
### Flow
1. Create two empty sets, `set1` and `set2`, to store unique elements from `nums1` and `nums2` respectively.
2. Create two empty lists, `a1` and `a2`, to store the elements that are present in one array but not in the other.
3. Iterate over each element in `nums1` and add it to `set1`.
4. Iterate over each element in `nums2` and add it to `set2`.
5. Iterate over each element `n` in `set1`.
   - If `n` is not present in `set2`, add it to `a1`.
6. Iterate over each element `n` in `set2`.
   - If `n` is not present in `set1`, add it to `a2`.
7. Return a list containing `a1` and `a2` using `Arrays.asList()`.
___
### Outputs
- A list of lists of integers, where the first list (`a1`) contains the elements that are present in `nums1` but not in `nums2`, and the second list (`a2`) contains the elements that are present in `nums2` but not in `nums1`.
___
