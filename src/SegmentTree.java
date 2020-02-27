

class SegmentTree {

    private int[] tree;
    private int[] arr;
    private int n;
    //moving around in tree

    private int leftOf(int p) {
        return p << 1;
    }

    private int rightOf(int p) {
        return (p << 1) + 1;
    }
    
    public SegmentTree(int[] tempArr) {
        //init values
        arr = tempArr;
        n = arr.length;
        tree = new int[4 * n];

        for (int i = 0; i < 4 * n; i++) {
            tree[i] = 0;
        }

        //build segment tree
        build(1, 0, n - 1);
    }

    private void build(int idx, int left, int right) {
        if (left == right) {
            tree[idx] = left;
        } else {
            //build sides
            build(leftOf(idx), left, (left + right) / 2);
            build(rightOf(idx), (left + right) / 2 + 1, right);
            //connect
            int idxL = tree[leftOf(idx)];
            int idxR = tree[rightOf(idx)];
            if (arr[idxL] <= arr[idxR]) {
                tree[idx] = idxL;
            } else {
                tree[idx] = idxR;
            }
        }
    }

    //range MINIMUM query
    private int rmq(int idx, int left, int right, int i, int j) {
        if (i > right || j < left) {
            return -1; //not in range
        }
        if (left >= i && right <= j) {
            return tree[idx]; //in exact range
        }

        int idxL = rmq(leftOf(idx), left, (left + right) / 2, i, j);
        int idxR = rmq(rightOf(idx), (left + right) / 2 + 1, right, i, j);

        if (idxL == -1) {
            return idxR;   
        }
        if (idxR == -1) {
            return idxL;  
        }
        return (arr[idxL] <= arr[idxR]) ? idxL : idxR;
    }

    private int updatePoint(int p, int left, int right, int idx, int newVal) {
        int i = idx, j = idx;

        if (i > right || j < left) {
            return tree[p];
        }

        if (left == i && right == j) {
            arr[i] = newVal;//update array too!
            tree[p] = left;
            return tree[p];
        }

        int idxL = updatePoint(leftOf(p), left, (left + right) / 2, idx, newVal);
        int idxR = updatePoint(rightOf(p), (left + right) / 2 + 1, right, idx, newVal);

        tree[p] = arr[idxL] <= arr[idxR] ? idxL : idxR;
        return tree[p];
    }

    public int rmq(int left, int right) {
        return rmq(1, 0, n - 1, left, right);
    }

    public int updatePoint(int idx, int new_value) {
        return updatePoint(1, 0, n - 1, idx, new_value);
    }
}
