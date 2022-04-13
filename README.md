Codes in this repo has been written by Tay Hui Yi for CS4330 - Computational Methods in Bioinformatics, under Prof. Sung Wing Kin.

# Introduction to kmers 
kmers are substrings of length *k* contained within biological sequences and are generally composed of nucleotides (A,T,C,G). Generally, the term kmer refers to all of the substrings of length *k* in a particular DNA sequence. Hence, a sequence of length *L* will have *(L-k+1)* kmers. In the context of DNA sequences, the total number of possible kmers is 4<sup>k</sup>. In bioinformatics, kmers are mainly used within the context of computational genomics and sequence analysis. 

# Description of the Program
The aim of the kmer program is to generate the counts of all kmers in a fasta file that occur at least *q* (user defined) times. A memory-based approach (Counting BloomFilter) is implemented to carry out kmer counting. A new output txtfile containing the kmers that occur at least q  times and its respective count is generated.

## Note 
- Only the canonical form of kmers are counted, where the canonical form is the alphabetically smaller kmer among the kmer itself and its reverse complement.
- kmers containing *n* or *N* indicate a missing nucleotide and are disregarded during kmer counting. 


# Brief Instructions
1. Ensure that the input fasta files containing the DNA sequence(s) are formatted in the following manner.
     ```java
      >s1 
      ATCGCTG...
      >s2
      ATCGCTG...
      ```
2. Download and unzip the kmer_counting.zip in the location with the input fasta files. 
3. Manually change all paths in the main method of **kmer.java** to the location containing the input fasta files. 
4. To run the program, run the following commands. 
      ```java
      /*
      Description of parameters 
        f: Name of the input file containing the DNA sequence(s)
        k: length of kmers to be counted 
        q: minimum number of occurences for kmer to be counted 
        
      Example 
        java kmer 15mer.fa 15 3
      */
      
      javac *.java 
      java kmer f k q
      ```
4. Open the output txtfile, "f_out.txt", for the generated kmer counts. 

# Additional Notes
- A disk-based approach (DSK algorithm) and accuracy improvements are currently undergoing implementation. The program will be updated in the near future. 
- A summary of the methods used in the program can be found under the "Summary.pdf".
- Terminal will output the total RAM and wall-clock running time of each input fasta file.
