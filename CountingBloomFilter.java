import java.io.*;
import java.lang.Math;

public class CountingBloomFilter {
    int m; //no. of kmers
    int k; //no. of hash functions
    int[] CountBF;
    int n; //size of the hash table

    //Initialise a new empty BF
    //x and y are the length of the kmer and the no. of kmers respectively
    public void emptyBF(int m) {
        double falsePositiveRate = 0.01;
        n = -(int)Math.round((m*Math.log(falsePositiveRate))/(Math.pow(Math.log(2),2)));
        k= -(int)Math.round((n/m)*Math.log(2));
        CountBF = new int[n-1]; //initialise all initial values as 0
    }
    //x and y are the length of the kmer and the no. of kmers respectively
    public void insert(int w, int[] CountBF) {
        int hf0 = w%n;
        CountBF[hf0] += 1; //hash into CountBF table
        int hf1 = (int) (Math.pow(w,2)%n);
        CountBF[hf1] += 1; //hash into CountBF table
        for (int i=1; i<k-1; i++){
            int newHF = (hf0 + i*hf1)%n;
            CountBF[newHF] += 1;
        }
    }
    public int Query(int w, int[] CountBF){
        int hf0 = w%n;
        int min_count = CountBF[hf0];
        int hf1 = (int) (Math.pow(w,2)%n);
        if (min_count >0 && CountBF[hf1] <= min_count){
            min_count = CountBF[hf1];
        }
        int counter = 0; //in the case where min_count > 0 and there is a count, the loop should break
        while (min_count>0 && counter < k-2){
            for (int i=1; i<k-1; i++) {
                int newHF = (hf0 + i*hf1)%n;

                if (CountBF[newHF] > 0 && CountBF[newHF] <= min_count) {
                    min_count = CountBF[newHF];
                    counter+=1;
                } else if (CountBF[newHF]>0 && CountBF[newHF]>min_count){
                    counter+=1;
                    continue;
                }
                else {
                    min_count = 0;
                    break;
                }
            }
        }
        return min_count;
    }
}
