import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class kmer {
    //Method to convert kmers to its respective integer value
    public static int convertRead(String kmer) {
        int length = kmer.length();
        String C_kmer = canonicalForm(kmer);
        int score_a = 0;
        int score_c = 1;
        int score_g = 2;
        int score_t = 3;
        int int_value_kmer = 0;
        int length_Ckmer = C_kmer.length();
        for (int j = 0; j < length; j++) {
            if (C_kmer.substring(j, j + 1).equals("a")) {
                int_value_kmer += score_a * Math.pow(4,length_Ckmer - j - 1);
            } else if (C_kmer.substring(j, j + 1).equals("c")) {
                int_value_kmer += score_c * Math.pow(4,length_Ckmer - j - 1);
            } else if (C_kmer.substring(j, j + 1).equals("g")) {
                int_value_kmer += score_g * Math.pow(4,length_Ckmer - j - 1);
            } else {
                int_value_kmer += score_t * Math.pow(4,length_Ckmer - j - 1);
            }
        }
        return int_value_kmer;
    }

    //Method to find canonical form of each kmer
    public static String canonicalForm(String str){
        int length = str.length();
        String canonical_form = "";
        if (str.contains("n")||str.contains("N")){
            canonical_form = "None";
        }
        else {
            //to make sure that kmers are all in a,t,c,g form
            String kmer = "";
            for (int i=0; i<length; i++){
                String nucleotide;
                if (str.substring(i,i+1).equals("a")||str.substring(i,i+1).equals("A")){
                    nucleotide = "a";
                }
                else if (str.substring(i,i+1).equals("t")||str.substring(i,i+1).equals("T")){
                    nucleotide = "t";
                }
                else if (str.substring(i,i+1).equals("c")||str.substring(i,i+1).equals("C")){
                    nucleotide = "c";
                }
                else {
                    nucleotide = "g";
                }
                kmer = kmer + nucleotide;
            }
            //get the reverse complement
            String reverse = "";
            for (int i=0; i<length; i++){
                String nucleotide;
                if (kmer.substring(i,i+1).equals("a")){
                    nucleotide = "t";
                }
                else if (kmer.substring(i,i+1).equals("t")){
                    nucleotide = "a";
                }
                else if (kmer.substring(i,i+1).equals("c")){
                    nucleotide = "g";
                }
                else{
                    nucleotide = "c";
                }
                reverse = nucleotide + reverse;
            }
            //calculation to find canonical form
            int score_kmer = 0;
            int score_reverse = 0;
            //Case 1: reverse complement is exactly the kmer
            if (kmer.equals(reverse)) {
                canonical_form = kmer;
            }
            //Case 2: reverse complement and kmer are different, hence find canonical form
            else {
                for (int i=0; i<length;i++){
                    int kmer_ACSII = kmer.charAt(i);
                    int reverse_ACSII = reverse.charAt(i);
                    score_kmer += kmer_ACSII;
                    score_reverse += reverse_ACSII;
                    if (score_kmer < score_reverse){
                        canonical_form = kmer;
                        break;
                    }
                    else if (score_kmer > score_reverse){
                        canonical_form = reverse;
                        break;
                    }
                    else{
                        continue;
                    }
                }
            }
        }
        return canonical_form;
    }
    public static void main(String[] args) throws IOException {
        //NOTE: navigate to wherever the testcase files are 
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long start = System.currentTimeMillis();
        String filename = args[0];
        int k = Integer.parseInt(args[1]); //kmer length
        int q = Integer.parseInt(args[2]); //minimum count threshold
        //create result file and buffered writer to write results into a text file
        File results = new File("/Users/huiyitay/Downloads/CS4330/Assignment/A2/ass2_dataset/"+filename.substring(0,filename.length()-3)+"_out.txt");
        FileOutputStream fos_results = new FileOutputStream(results);
        BufferedWriter bw_results = new BufferedWriter(new OutputStreamWriter(fos_results));

        File file = new File ("/Users/huiyitay/Downloads/CS4330/Assignment/A2/ass2_dataset/"+filename);
        BufferedReader br = new BufferedReader(new FileReader(file)); //read file containing dna sequence
        File fout = new File("/Users/huiyitay/Downloads/CS4330/Assignment/A2/ass2_dataset/"+filename.substring(0,filename.length()-2)+"txt"); //create new file with all the kmers
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        int count = 0; //keep track of the number of kmers in the textfile

        while (br.ready()==true){
            br.mark(10); //mark initial position
            //if we are at the line which describes the name of the sequence
            if (br.readLine().substring(0,1).equals(">")){
                br.reset(); //reset br to its initial position
                br.readLine(); //skip the line
            }
            //mark initial position of the sequence
            br.mark(30000000);
            int length = br.readLine().length(); //get the length of the sequence
            br.reset(); //reset to initial
            //some of prof's testcases has nicely formatted kmers
            if (length==k){
                bw.write(br.readLine()); //directly write kmers into a new file
                bw.newLine();
                bw.flush(); //flush out of memory
                count+=1;
            }
            //for testcases in which the whole DNA seq is given and we need to do kmer splitting
            else{
                //for i in range 0 to (no. of kmers)
                for (int i=0; i<length-k+1;i++){
                    br.mark(30000000);
                    bw.write(br.readLine().substring(i,i+k));
                    bw.newLine();
                    bw.flush();
                    br.reset();
                    count+=1;
                }
                if (br.readLine()==null){
                    break;
                }
            }
        }
        String folderName = filename.substring(0,filename.length()-3);
        new File("/Users/huiyitay/Downloads/CS4330/Assignment/A2/ass2_dataset/"+folderName).mkdir();
        String MainPath = "/Users/huiyitay/Downloads/CS4330/Assignment/A2/ass2_dataset/"+folderName+"/";
        int M = (int) Math.pow(10,6); //memory limit of 1Mb
        int D = 2*M;
        int nlist = (2*k*count)/D;
        int nsublist = (int) ((D*(2*k+32))/(0.7*2*k*M));
        if (nlist==0){
            nlist = 1; //one txt file is big enough to store all kmers
        }
        for (int i=0; i<nlist; i++) {
            new File (MainPath+"list"+i).mkdir();
            String lstPath = MainPath+"list"+i+"/";
            //initialise a set of empty sublist in disk
            for (int j=0; j<nsublist; j++){
                File f = new File(lstPath+"sublist"+j);
                f.createNewFile();
            }
            //new buffered reader to read the txtfile with the set of kmers to sort
            BufferedReader brSet = new BufferedReader(new FileReader(fout));
            String line;
            while ((line=brSet.readLine())!=null){
                if(line.contains("N")||line.contains("n")){
                    continue;
                }
                else {
                    String cF = canonicalForm(line);
                    int z = convertRead(cF);
                    int hashList = z%nlist; //gets the folder to enter
                    if (hashList==i){
                        String hashListPath = MainPath + "list" + hashList +"/"; //path to folder
                        int hashSublist = (hashList)%nsublist; //sublist (.txt) to write kmer into
                        String sublistName = "sublist" + hashSublist; //txtfile name to write kmer into
                        FileOutputStream fos1 = new FileOutputStream(hashListPath+sublistName, true);
                        BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(fos1));
                        bw1.write(cF);
                        bw1.newLine();
                        bw1.flush();
                    }
                }
            }
            for (int j=0; j<nsublist; j++) {
                //Hashtable to store all the kmers that exceed the min count (q)
                Hashtable<String,Integer> hs = new Hashtable<String,Integer>();
                File f = new File(lstPath+"sublist"+j);
                BufferedReader br1 = new BufferedReader((new FileReader(f)));
                br1.mark(30000000);
                int m = 0; //find the no. of kmers in the sublist
                String read;
                while ((read=br1.readLine())!=null){
                    m+=1;
                }
                br1.reset();
                if (m==0){
                    continue;
                }
                else {
                    CountingBloomFilter cBF = new CountingBloomFilter();
                    cBF.emptyBF(m);
                    while (br1.ready()&&(read=br1.readLine())!=null){
                        //if hashtable already contains the kmer, increment the count
                        if (hs.containsKey(read)==true) {
                            hs.put(read, hs.get(read)+1);
                        }
                        //hashtable does not contain the kmer
                        else {
                            int value = convertRead(read);
                            cBF.insert(value, cBF.CountBF); //insert read into counting bloom filter
                            int count_cBF = cBF.Query(value,cBF.CountBF); //query the count of the kmer in the counting bloom filter
                            //if kmer is not present in hashtable and the count of the kmer >= min count (q)
                            //Note: this condition only occurs once because once the count is =2, the kmer will be inserted into the hashtable
                            if (count_cBF>=q && (hs.containsKey(read)==false)){
                                hs.put(read,q); //put the kmer in the hashtable
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    for (String key: hs.keySet()) {
                        int value = hs.get(key);
                        System.out.println(String.valueOf(value) + ' ' + key);
                        bw_results.write(String.valueOf(value) + ' ' + key);
                        bw_results.newLine();
                        bw_results.flush();
                    }
                }
            }
        }
        bw_results.close();
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        //to find out the memory used for the program
        System.out.println(beforeUsedMem);
        System.out.println(afterUsedMem);
        System.out.println(actualMemUsed);

        //to find out the time used for each testcase 
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
    }
}
