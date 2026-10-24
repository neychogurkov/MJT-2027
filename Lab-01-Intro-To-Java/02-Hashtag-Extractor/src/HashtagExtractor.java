import java.util.Arrays;

public class HashtagExtractor {
    public static String[] extractHashtags(String post) {
        if (post == null) {
            return new String[]{};
        }

        String[] hashtags = new String[]{};
        String currHashtag = "";
        char[] charPost = post.toCharArray();
        boolean inHashtag = false;

        for (char ch : charPost) {
            if (inHashtag) {
                if (Character.isLetterOrDigit(ch)) {
                    currHashtag += ch;
                } else {
                    inHashtag = false;

                    if (currHashtag.isEmpty()) {
                        continue;
                    }

                    hashtags = addHashtag(hashtags, currHashtag);
                }
            }

            if (ch == '#') {
                inHashtag = true;
                currHashtag = "";
            }
        }

        if (!currHashtag.isEmpty() && inHashtag) {
            hashtags = addHashtag(hashtags, currHashtag);
        }

        return hashtags;
    }

    private static String[] addHashtag(String[] hashtags, String currHashtag) {
        for (String hashtag : hashtags) {
            if (hashtag.equals(currHashtag.toLowerCase())) {
                return hashtags;
            }
        }

        hashtags = Arrays.copyOf(hashtags, hashtags.length + 1);
        hashtags[hashtags.length - 1] = currHashtag.toLowerCase();

        return hashtags;
    }
}