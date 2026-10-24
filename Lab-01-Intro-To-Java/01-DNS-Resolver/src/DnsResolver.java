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

        for (int i = 0; i < from.length; i++) {
            for (int j = 0; j < from.length; j++) {
                if (from[j].equals(result) && from[j].equals(to[j])) {
                    return null;
                }

                if (from[j].equals(result)) {
                    result = to[j];
                }

                for (int k = startIndex; k < from.length; j++) {
                    if (to[j].equals(from[k])) {
                        if (j != startIndex && !from[j].equals(to[j - 1])) {
                            continue;
                        }
                        return null;
                    }
                }
            }
        }

//        String result = "";
//
//        for (int i = startIndex; i < from.length; i++) {
//            if (i != startIndex && !from[i].equals(result)) {
//                continue;
//            }
//
//            if (from[i].equals(to[i])) {
//                return null;
//            }
//
//            for (int j = startIndex; j < i; j++) {
//                if (to[i].equals(from[j])) {
//                    if(j != startIndex && !from[j].equals(to[j-1])){
//                        continue;
//                    }
//                    return null;
//                }
//            }
//
//            result = to[i];
//        }

        return result;
    }
}
