import java.util.*;

public class UnionFindDS {
    private ArrayList<Integer> parent;//parent of i
    private ArrayList<Integer> rank;//height of tree at i
    private ArrayList<Integer> setSize;//size of set at i
    
    private int numSets;//...
    
    public UnionFindDS(int nSets) {//nSets is also number of elements we are working with
        parent = new ArrayList<Integer>(nSets);
        rank = new ArrayList<Integer>(nSets);
        setSize = new ArrayList<Integer>(nSets);
        
        numSets = nSets;
        
        //initialize sets
        for (int i = 0; i < nSets; i++) {
            parent.add(i);//parent is own self
            rank.add(0);//no depth
            setSize.add(1);//each set is itself
        }
    }
    
    public int findSetOf(int elem)  {//given an element, what set does it belong to?
        if (parent.get(elem) == elem)//is own parent
            return elem;
        int ret = findSetOf(parent.get(elem));//find parent of parent
        parent.set(elem, ret);//compress - save time for multiple calls
        return ret;
    }
    
    public boolean inSameSet(int elem1, int elem2) {//are two elements "connected"?
        return findSetOf(elem1) == findSetOf(elem2);
    }
    
    public void unionSets(int elem1, int elem2) {//combine two sets
        //only if already not combined
        if (!inSameSet(elem1, elem2)) {
            numSets--;
            //find parents
            int p1 = findSetOf(elem1);
            int p2 = findSetOf(elem2);
            //keep the trees short (go off longer one)
            if (rank.get(p1) > rank.get(p2)) {
                parent.set(p2, p1);//parents point to one
                setSize.set(p1, setSize.get(p1) + setSize.get(p2));//combine sizes
            } else {
                parent.set(p1, p2);//parents point to one
                setSize.set(p2, setSize.get(p1) + setSize.get(p2));//combine sizes
                if (rank.get(p1) == rank.get(p2))//update rank
                    rank.set(p1, rank.get(p1) + 1);
            }
        }
    }
    
    public int getNumDisjointSets() {
        return numSets;
    }
    
    public int sizeOfSet(int elem) {
        return setSize.get(findSetOf(elem));
    }
    
}
