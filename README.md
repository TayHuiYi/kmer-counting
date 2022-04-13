Codes in this repo has been written by Tay Hui Yi for CS4330 - Computational Methods in Bioinformatics, under Prof. Sung Wing Kin.

# Description of the program
The aim of the Java program is to allows users to count the number of occurrences of each kmer in DNA sequences and output the counts of all k-mers in the fasta file that occur at least q times. A memory-based approach (Counting BloomFilter) is implemented to carry out kmer counting. A new output txtfile containing the kmers that occur at least q (defined by user) times and its respective count is generated.

**Brief Instructions**
1. Ensure that the files containing the DNA sequence(s) are formatted in the following manner.
     ```java
      >s1 
      ATCGCTG...
      >s2
      ATCGCTG...
      ```
2. Manually change all paths in the main method to the location containing the necessary files (as in step 1). 
3. To run the program, first ensure that the working directory contains all the Java programs. Second, run the following commands. 
      ```java
      javac *.java 
      java kmer f k q
      
      /*
      Description of parameters 
        f: Name of the input file containing the DNA sequence(s)
        k: length of kmers to be counted 
        q: minimum number of occurences for kmer to be counted 
        
      Example 
        java kmer 15mer.fa 15 3
      */
      ```
4. Open the output txtfile, "f_out.txt", for the generated kmer counts. 

**Note**
- A disk-based approach (DSK algorithm) is currently undergoing implementation. The program will be updated in the near future. 
- A summary of the methods used in the program can be found under the "Summary.pdf".
