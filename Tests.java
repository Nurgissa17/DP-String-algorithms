import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Tests {
    static int passed = 0;
    static void check(boolean condition) {
        if (!condition) throw new AssertionError("Test " + (passed + 1) + " failed");
        passed++;
    }
    static void match(String text, String pattern, int... indices) {
        List<Integer> expected = new ArrayList<>();
        for (int i : indices) expected.add(i);
        check(Naive.naive(text, pattern).equals(expected));
        check(KMP.kmp(text, pattern).equals(expected));
        check(RabinKarp.rabinKarp(text, pattern).equals(expected));
    }
    public static void main(String[] args) {
        check(LCS.lcs("abc", "abc") == 3);
        check(LCS.lcs("abc", "xyz") == 0);
        check(LCS.lcs("abc", "") == 0);

        check(EditDistance.editDistance("abc", "abc") == 0);
        check(EditDistance.editDistance("", "abc") == 3);
        check(EditDistance.editDistance("cat", "cut") == 1);
        check(EditDistance.editDistance("cat", "cart") == 1);
        check(EditDistance.editDistance("cart", "cat") == 1);

        match("abcxxxx", "abc", 0);
        match("zzabczz", "abc", 2);
        match("zzabc", "abc", 2);
        match("ababa", "aba", 0, 2);
        match("xyz", "ab");
        match("ab", "abc");
        match("abc", "", 0, 1, 2, 3);

        check(Arrays.equals(Kasai.kasai("banana", new int[]{5, 3, 1, 0, 4, 2}),
                new int[]{0, 1, 3, 0, 0, 2}));
        System.out.println("All " + passed + " checks passed.");
    }
}
