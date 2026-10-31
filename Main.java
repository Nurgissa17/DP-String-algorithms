import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
public class Main {
    static String randomString(int length, Random random) {
        StringBuilder s = new StringBuilder();
        String alphabet = "abcd";
        for (int i = 0; i < length; i++)
            s.append(alphabet.charAt(random.nextInt(alphabet.length())));
        return s.toString();
    }
    static int[] suffixArray(String text) {
        int n = text.length();
        String[] suffixes = new String[n];
        for (int i = 0; i < n; i++) suffixes[i] = text.substring(i);
        Arrays.sort(suffixes);
        int[] array = new int[n];
        for (int i = 0; i < n; i++) array[i] = n - suffixes[i].length();
        return array;
    }
    public static void main(String[] args) {
        Random random = new Random(42);
        System.out.println("DP");
        System.out.println("algorithm,size,avg_ms");
        for (int size : new int[]{100, 500, 1000}) {
            String a = randomString(size, random);
            String b = randomString(size, random);
            for (int algorithm = 0; algorithm < 2; algorithm++) {
                long total = 0;
                for (int repeat = 0; repeat < 5; repeat++) {
                    long start = System.nanoTime();
                    if (algorithm == 0) LCS.lcs(a, b);
                    else EditDistance.editDistance(a, b);
                    total += System.nanoTime() - start;
                }
                System.out.printf(Locale.US, "%s,%d,%.6f%n",
                        algorithm == 0 ? "LCS" : "Edit Distance", size, total / 5_000_000.0);
            }
        }
        System.out.println("MATCHING");
        System.out.println("algorithm,text_length,pattern,avg_ms,char_comparisons,hash_checks");
        String[] names = {"Naive", "KMP", "Rabin-Karp"};
        for (int size : new int[]{10000, 50000, 100000}) {
            String text = randomString(size, random);
            String present = text.substring(size / 2, size / 2 + 12);
            String absent = randomString(12, random);
            while (!Naive.naive(text, absent).isEmpty())
                absent = randomString(12, random);
            String[] patterns = {present, absent};
            for (int p = 0; p < 2; p++) {
                for (int algorithm = 0; algorithm < 3; algorithm++) {
                    long total = 0, characters = 0, hashes = 0;
                    List<Integer> result = null;
                    for (int repeat = 0; repeat < 5; repeat++) {
                        Naive.comparisons = 0;
                        KMP.comparisons = 0;
                        RabinKarp.comparisons = 0;
                        RabinKarp.hashChecks = 0;
                        long start = System.nanoTime();
                        if (algorithm == 0) result = Naive.naive(text, patterns[p]);
                        else if (algorithm == 1) result = KMP.kmp(text, patterns[p]);
                        else result = RabinKarp.rabinKarp(text, patterns[p]);
                        total += System.nanoTime() - start;
                        if (algorithm == 0) characters = Naive.comparisons;
                        else if (algorithm == 1) characters = KMP.comparisons;
                        else characters = RabinKarp.comparisons;
                        hashes = RabinKarp.hashChecks;
                    }
                    if (!result.equals(Naive.naive(text, patterns[p])))
                        throw new AssertionError("Matching results differ");
                    System.out.printf(Locale.US, "%s,%d,%s,%.6f,%d,%d%n", names[algorithm],
                            size, p == 0 ? "present" : "absent", total / 5_000_000.0,
                            characters, hashes);
                }
            }
        }
        System.out.println("KASAI");
        System.out.println("size,avg_ms");
        for (int size : new int[]{1000, 5000, 10000}) {
            String text = randomString(size, random);
            int[] suffixArray = suffixArray(text);
            long total = 0;
            for (int repeat = 0; repeat < 5; repeat++) {
                long start = System.nanoTime();
                Kasai.kasai(text, suffixArray);
                total += System.nanoTime() - start;
            }
            System.out.printf(Locale.US, "%d,%.6f%n", size, total / 5_000_000.0);
        }
    }
}