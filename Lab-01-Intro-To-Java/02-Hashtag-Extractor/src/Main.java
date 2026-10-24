void main() {
    String[] hashtags = HashtagExtractor.extractHashtags("#MJT #mjt");

    for (String hashtag : hashtags) {
        System.out.println(hashtag);
    }
}