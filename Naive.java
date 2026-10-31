import java.util.ArrayList;
import java.util.List;
public class Naive {
    static long comparisons;
    static List<Integer> naive(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        int n = text.length(), m = pattern.length();
        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m) {
                comparisons++;
                if (text.charAt(i + j) != pattern.charAt(j)) break;
                j++;
            }
            if (j == m) result.add(i);
        }
        return result;
    }
}