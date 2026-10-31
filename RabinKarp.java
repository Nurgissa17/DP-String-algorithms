import java.util.ArrayList;
import java.util.List;

public class RabinKarp {
    static long comparisons;
    static long hashChecks;

    static List<Integer> rabinKarp(String text, String pattern) {
        int n = text.length(), m = pattern.length();
        List<Integer> result = new ArrayList<>();
        if (m == 0) {
            for (int i = 0; i <= n; i++) result.add(i);
            return result;
        }
        if (m > n) return result;
        long mod = 1_000_000_009L, base = 31, power = 1;
        long patternHash = 0, windowHash = 0;
        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * base + pattern.charAt(i)) % mod;
            windowHash = (windowHash * base + text.charAt(i)) % mod;
            if (i < m - 1) power = power * base % mod;
        }
        for (int i = 0; i <= n - m; i++) {
            hashChecks++;
            if (patternHash == windowHash) {
                int j = 0;
                while (j < m) {
                    comparisons++;
                    if (text.charAt(i + j) != pattern.charAt(j)) break;
                    j++;
                }
                if (j == m) result.add(i);
            }
            if (i < n - m)
                windowHash = ((windowHash - text.charAt(i) * power % mod + mod)
                        * base + text.charAt(i + m)) % mod;
        }
        return result;
    }
}
