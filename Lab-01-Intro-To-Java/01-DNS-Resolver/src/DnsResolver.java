public class DnsResolver {
    public static String resolve(String[] from, String[] to, String start) {
        String slow = start;
        String fast = start;

        while (slow != null && fast != null) {
            slow = getNextHost(slow, from, to);
            fast = getNextHost(fast, from, to);

            if (fast != null) {
                fast = getNextHost(fast, from, to);
            }

            if (slow != null && fast != null && slow.equals(fast)) {
                return null;
            }
        }

        String result = start;
        String next = getNextHost(result, from, to);

        while (next != null) {
            result = next;
            next = getNextHost(result, from, to);
        }

        return result;
    }

    private static String getNextHost(String host, String[] from, String[] to) {
        for (int i = 0; i < from.length; i++) {
            if (host.equals(from[i])) {
                return to[i];
            }
        }

        return null;
    }
}