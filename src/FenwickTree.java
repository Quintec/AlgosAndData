//1-index based structure
public class FenwickTree {
    private int[] values;
    
    public FenwickTree(int size) {
        this.values = new int[size];
    }
    
    public void add(int idx, int k) {//adds k to element at idx
        while (idx < values.length) {
            values[idx] += k;
            idx += _lsb(idx);
        }
    } 
    
    public int sumTo(int idx) {//sum from 1 to idx inclusive
        int sum = 0;
        while (idx > 0) {
            sum += values[idx];
            idx -= _lsb(idx);
        }
        return sum;
    }
    
    private int _lsb(int i) {//least significant bit
        return i & -i;
    }
}
