import java.util.Arrays;

public class DnsResolver {
    public static String resolve(String[] from, String[] to, String start) {
        int startIndex = 0;
        boolean containsStart = false;

        for (int i = 0; i < from.length; i++) {
            if (start.equals(from[i])) {
                startIndex = i;
                containsStart = true;
                break;
            }
        }

        if (!containsStart) {
            return start;
        }

        String result = to[startIndex];
        String[] sources = new String[]{start};

        for (int i = 0; i < from.length; i++) {
            for (int j = 0; j < from.length; j++) {
                if (from[j].equals(result)) {
                    if (from[j].equals(to[j])) {
                        return null;
                    }

                    for (int k = 0; k < j; k++) {
                        if (to[j].equals(from[k])) {
                            for (String source : sources) {
                                if (source.equals(from[k])) {
                                    return null;
                                }
                            }
                        }
                    }

                    result = to[j];
                    sources = Arrays.copyOf(sources, sources.length + 1);
                    sources[sources.length - 1] = from[j];
                    break;
                }
            }
        }

        return result;
    }
}