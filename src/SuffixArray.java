
import java.util.Arrays;


public class SuffixArray {
    char[] string;
    int n;//length of string
    
    int[] rankArr;
    int[] tempRA;
    int[] suffixArr;
    int[] tempSA;
    
    int[] c;//for counting sort
    
    int[] phi;//for lcp - previous (phi[sa[3]] = sa[2])
    int[] pLCP;//permuted
    int[] LCP;
    
    private static final int MAX_LEN = 100000;
    
    public SuffixArray(String str) {
        str += "$";
        this.string = str.toCharArray();
        this.n = this.string.length;
        System.out.println(str);
        
        this.rankArr = new int[MAX_LEN];
        this.tempRA = new int[MAX_LEN];
        this.suffixArr = new int[MAX_LEN];
        this.tempSA = new int[MAX_LEN];
        
        this.c = new int[MAX_LEN];
        
        phi = new int[MAX_LEN];
        pLCP = new int[MAX_LEN];
        LCP = new int[MAX_LEN];
        
        constructSA();
    }
    
    private void countingSort(int k) {
        int maxi = Math.max(300, n);//max length/buckets needed
        Arrays.fill(c, 0);//clear freq table

        for (int i = 0; i < n; i++) {
            if (i + k < n)
                c[rankArr[i + k]]++;
            else
                c[0]++;
        }

        int sum = 0;
        for (int i = 0; i < maxi; i++) {
            int t = c[i];
            c[i] = sum;
            sum += t;
        }

        
        for (int i = 0; i < n; i++) {//shuffle?
            tempSA[c[suffixArr[i] + k < n ? rankArr[suffixArr[i] + k] : 0]++] = suffixArr[i];
        }
        
        for (int i = 0; i < n; i++)//update SA
            suffixArr[i] = tempSA[i];
    }
    
    private void constructSA() {
        for (int i = 0; i < n; i++)//initial rank
            rankArr[i] = string[i];
        for (int i = 0; i < n; i++)//initial suffix arr
            suffixArr[i] = i;

        for (int k = 1; k < n; k <<= 1) {//sort log n times, shifting bit
            countingSort(k);
            countingSort(0);
            
            int r = 0;
            tempRA[suffixArr[0]] = 0;//start Reranking
            for (int i = 1; i < n; i++) {
                if (rankArr[suffixArr[i]] == rankArr[suffixArr[i - 1]] //same pair gets same rank, otherwise increase rank
                 && rankArr[suffixArr[i] + k] == rankArr[suffixArr[i - 1] + k]) {
                    tempRA[suffixArr[i]] = r;
                } else {
                    tempRA[suffixArr[i]] = ++r;
                }
            }
            
            for (int i = 0; i < n; i++) {//update ranks
                rankArr[i] = tempRA[i];
            }
            
        }
            
    }
    
    private int compWithSuffix(char[] a, int i, char[] b, int j, int n){
        for (int k=0; i+k < a.length && j+k < b.length; k++){
          if (a[i+k] != b[j+k])
              return a[i+k] - b[j+k];
        }
        return 0;
    }
    
    public int[] stringMatching(String toM) {
        return stringMatching(toM.toCharArray());
    }
    
    public int[] stringMatching(char[] P) {//P - string to be matched/found
        int low = 0;
        int high = n - 1;
        int mid = 0;
        //binary search for starts and ends
        while (low < high) {
            mid = (low + high) / 2;
            int cmp = compWithSuffix(string, suffixArr[mid], P, 0, P.length);
            if (cmp >= 0)
                high = mid;
            else
                low = mid + 1;
        }
        if (compWithSuffix(string, suffixArr[low], P, 0, P.length) != 0)//if not found
            return new int[]{-1, -1};
        
        int[] ans = new int[2];
        ans[0] = low;//otherwise, we found one bound, now the other...
        
        low = 0;
        high = n - 1;
        mid = 0;
        //find upper
        while (low < high) {
            mid = (low + high) / 2;
            int cmp = compWithSuffix(string, suffixArr[mid], P, 0, P.length);
            if (cmp > 0)
                high = mid;
            else
                low = mid + 1;
        }
        
        if (compWithSuffix(string, suffixArr[low], P, 0, P.length) != 0)//if only one
            high--;
        
        ans[1] = high;
        return ans;//inclusive - ex. [1,1] means one match at index 1
    }
    
    private void computeLCP() {
        int L = 0;
        phi[suffixArr[0]] = -1;
        for (int i = 1; i < n; i++)
            phi[suffixArr[i]] = suffixArr[i - 1];//compute phi
        
        for (int i = 0; i < n; i++) {
            if (phi[i] == -1) {
                pLCP[i] = 0;
                continue;
            }
            
            while (string[i + L] == string[phi[i] + L])
                L++;
            pLCP[i] = L;//calculate L (amortized n)
            L = Math.max(L - 1, 0);
        }
        
        for (int i = 0; i < n; i++) {
            LCP[i] = pLCP[suffixArr[i]];//unpermute
        }
    }
    
    
    public String LRS() {//longest repeated substring
        int max = 0;
        int idx = 0;
        for (int i = 1; i < n; i++) {
            if (LCP[i] > max) {
                max = LCP[i];
                idx = i;
            }
        }
        
        return new String(string).substring(suffixArr[idx], suffixArr[idx] + max);
    }
}
