void main() {
    String[] from = {"e", "www.fmi.bg", "fmi.uni-sofia.bg", "a", "b", "c", "uni-sofia.bg"};
    String[] to = {"g", "fmi.uni-sofia.bg", "uni-sofia.bg", "b", "c", "d", "a"};
    String start = "www.fmi.bg";

//    String[] from = {"moodle.bg", "login.moodle.bg"};
//    String[] to = {"login.moodle.bg", "moodle.bg"};
//    String start = "moodle.bg";
//
//    String[] from = {"localhost"};
//    String[] to = {"localhost"};
//    String start = "localhost";
//
//    String[] from = {"blog.me", "cdn.me", "cache.me"};
//    String[] to = {"cdn.me", "cache.me", "cdn.me"};
//    String start = "blog.me";
//
//    String[] from = {"www.fmi.bg", "fmi.uni-sofia.bg"};
//    String[] to = {"fmi.uni-sofia.bg", "uni-sofia.bg"};
//    String start = "example.com";

    System.out.println(DnsResolver.resolve(from, to, start));
}