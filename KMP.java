import java.util.ArrayList;
import java.util.List;
public class KMP {
    static long comparisons;
    static List<Integer> kmp(String text, String pattern) {
        int n = text.length(), m = pattern.length();
        List<Integer> result = new ArrayList<>();
        if (m == 0) {
            for (int i = 0; i <= n; i++) result.add(i);
            return result;
        }
        int[] lps = new int[m];
        for (int i = 1, len = 0; i < m;) {
            comparisons++;
            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i++] = ++len;
            } else if (len != 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        for (int i = 0, j = 0; i < n;) {
            comparisons++;
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    result.add(i - j);
                    j = lps[j - 1];
                }
            } else if (j != 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return result;
    }
}