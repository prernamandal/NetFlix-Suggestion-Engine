import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

public class Main {

    /**
     * @param args the command line arguments
     */
    static HashMap<String, String> movie_year = new HashMap<>();

    static int Limit = 1000000;

    public static void main(String[] args) {
        try {
            FileWriter fw = new FileWriter(new File("alaki_multi_big.csv"));

            File f = new File("movies.list");
            BufferedReader br = new BufferedReader(new FileReader(f));
            HashSet<String> seen = new HashSet<>();
            String st = "";
            int i = 0;
            while ((st = br.readLine()) != null && i < Limit) {
                if (st.isEmpty()) {
                    continue;
                }
                String[] ss = st.split(",");
                int year = -1;
                try {
                    year = Integer.parseInt(ss[1]);
                } catch (NumberFormatException nfe) {
                    continue;
                }
                String title = getCleanTitle(ss[0]);
                if (seen.contains(title)) {
                    movie_year.remove(title);
                    i--;
                } else {
                    movie_year.put(title, ss[1].trim());
                    i++;
                }
                seen.add(title);
            }

            //Create movie_director mapping
            readDirectors();
            System.out.println(movie_director.get("The Matrix"));
//            System.out.println(director_count.size());
//            int qwerty = 1;
//            qwerty = director_count.values().stream().sorted().skip(director_count.size() - 1000).findFirst().get();
//            System.out.println(qwerty);
            //Create movie_producer mapping
            readProducers();
            System.out.println(movie_producer.get("The Matrix"));
            //Create movie_runningTime mapping
            readRunningTimes();
            System.out.println(movie_runningTime.get("The Matrix"));

            readWriters();
            System.out.println(movie_writer.get("The Matrix"));

            readActors();
            System.out.println(movie_actor.get("The Matrix"));

            //Create movie_genre mapping
            HashMap<String, String> movie_genre = new HashMap<>();
            File genresFile = new File("genres.list");
            BufferedReader br2 = new BufferedReader(new FileReader(genresFile));
            String gg = "";
            while ((gg = br2.readLine()) != null) {
                String[] ss = gg.split(",");
                String title = getCleanTitle(ss[0]);
                if (movie_year.containsKey(title)) {
                    if (movie_genre.containsKey(title)) {
                        movie_genre.put(title, movie_genre.get(title) + "::" + ss[1]);
                    } else {
                        movie_genre.put(title, ss[1]);
                    }
                }
            }

            fw.write("title,genre,year,director1,director2,producer1,producer2,runningTime,"
                    + "writer1,writer2,actor1,actor2,actor3,actor4\n");
            for (String s : movie_year.keySet()) {
                if (movie_genre.get(s) == null
                        || movie_year.get(s) == null
                        || movie_director.get(s) == null
                        || director_count == null
                        || movie_producer.get(s) == null
                        || movie_runningTime.get(s) == null
                        || movie_writer.get(s) == null
                        || movie_actor.get(s) == null) {
                    continue;
                } else {
                    String[] genres = movie_genre.get(s).split("::");
                    String[] directors = movie_director.get(s).split("::");
                    String[] dirs = {"?", "?"};
                    for (int k = 0; k < Math.min(2, directors.length); k++) {
                        if (director_count.get(directors[k]) != null && director_count.get(directors[k]) > 1) {
                            dirs[k] = directors[k];
                        }
                    }
                    if (dirs[0].equalsIgnoreCase("?") && dirs[1].equalsIgnoreCase("?")) {
                        continue;
                    }
//                    System.arraycopy(directors, 0, dirs, 0, Math.min(2, directors.length));

                    String[] producers = movie_producer.get(s).split("::");
                    String[] pros = {"?", "?"};
                    System.arraycopy(producers, 0, pros, 0, Math.min(2, producers.length));

                    String[] writers = movie_writer.get(s).split("::");
                    String[] ws = {"?", "?"};
                    System.arraycopy(writers, 0, ws, 0, Math.min(2, writers.length));

                    String[] actors = movie_actor.get(s).split("::");
                    String[] acts = {"?", "?", "?", "?"};
                    System.arraycopy(actors, 0, acts, 0, Math.min(2, actors.length));

                    for (String genre : genres) {
                        fw.write(s
                                + "," + genre
                                + "," + movie_year.get(s)
                                + "," + dirs[0]
                                + "," + dirs[1]
                                + "," + pros[0]
                                + "," + pros[1]
                                + "," + movie_runningTime.get(s)
                                + "," + ws[0]
                                + "," + ws[1]
                                + "," + acts[0]
                                + "," + acts[1]
                                + "," + acts[2]
                                + "," + acts[3]
                                + "\n");
                    }
                }
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static HashMap<String, String> movie_director = new HashMap<>();
    static HashMap<String, Integer> director_count = new HashMap<>();

    public static void readDirectors() {
        try {
            File f = new File("directors.list");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String st = "";
            String director = "";
            while ((st = br.readLine()) != null) {
                if (st.isEmpty()) {
                    director = br.readLine().trim().replaceAll(",", " ");
                    director_count.put(director, 0);
//                    if (director.equalsIgnoreCase("Wachowski, Andy".trim().replaceAll(",", " "))) {
//                    System.out.println(director);
//                    }
                } else {
//                    if (director.equalsIgnoreCase("Wachowski Andy")) {
//                        System.out.println("got Andy");
//                        System.out.println(getCleanTitle(st));
//                    }
                    String title = getCleanTitle(st);
                    if (movie_year.containsKey(title)) {
                        if (movie_director.containsKey(title)) {
                            movie_director.put(title, movie_director.get(title) + "::" + director);
                        } else {
                            movie_director.put(title, director);
                        }
                        director_count.put(director, director_count.get(director) + 1);
//                    movie_director.put(getCleanTitle(st), director);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static HashMap<String, String> movie_producer = new HashMap<>();

    public static void readProducers() {
        try {
            File f = new File("producers.list");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String st = "";
            String producer = "";
            while ((st = br.readLine()) != null) {
                if (st.isEmpty()) {
                    producer = br.readLine().trim().replaceAll(",", " ");
                } else {
                    String title = getCleanTitle(st);
                    if (movie_year.containsKey(title)) {
                        if (movie_producer.containsKey(title)) {
                            movie_producer.put(title, movie_producer.get(title) + "::" + producer);
                        } else {
                            movie_producer.put(title, producer);
                        }
                    }
//                    movie_producer.put(getCleanTitle(st), producer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static HashMap<String, String> movie_runningTime = new HashMap<>();

    public static void readRunningTimes() {
        try {
            File f = new File("running-times.list");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String st = "";
            while ((st = br.readLine()) != null) {
                String[] ss = st.split(",");
                try {
                    int a = Integer.parseInt(ss[1]);
                } catch (Exception nfe) {
                    continue;
                }
                movie_runningTime.put(getCleanTitle(ss[0]), ss[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static HashMap<String, String> movie_writer = new HashMap<>();

    public static void readWriters() {
        try {
            File f = new File("writers.list");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String st = "";
            String writer = "";
            while ((st = br.readLine()) != null) {
                if (st.isEmpty()) {
                    writer = br.readLine().trim().replaceAll(",", " ");
                } //                else if (st.endsWith("(writer)")) {
                else {
//                    st = st.replace("(writer)", "");
                    String title = getCleanTitle(st);
                    if (movie_year.containsKey(title)) {
                        if (movie_writer.containsKey(title)) {
                            movie_writer.put(title, movie_writer.get(title) + "::" + writer);
                        } else {
                            movie_writer.put(title, writer);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static HashMap<String, String> movie_actor = new HashMap<>();

    public static void readActors() {
        try {
            File f = new File("actors.list");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String st = "";
            String actor = "";
            Pattern p = Pattern.compile("^.*?(<1>|<2>|<3>|<4>).*$");

            while ((st = br.readLine()) != null) {
                if (st.isEmpty()) {
                    actor = br.readLine().trim().replaceAll(",", " ");
                } else if (p.matcher(st).matches()) {
                    String title = getCleanTitle(st);
                    title = title.replaceAll("<\\d+>", "").trim();
                    if (movie_year.containsKey(title)) {
                        if (movie_actor.containsKey(title)) {
                            movie_actor.put(title, movie_actor.get(title) + "::" + actor);
                        } else {
                            movie_actor.put(title, actor);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getCleanTitle(String s) {
        String title = "";
        if (s.contains("{") || s.contains("}")) {
            return title;
        } else {
            title = s.trim();
        }
        return title;
    }

//    private static String getCleanTitle(String s) {
//        String title = "";
////        if (s.contains("{") && s.contains("}")) {
////            int tmp = s.indexOf("{");
////            title = s.substring(0, tmp).trim();
////        } else {
////            title = s.trim();
////        }
////        title = s.replaceAll("\\{+.*?\\}+", "").replaceAll("\\(.*?\\)", "").trim();
//        return title;
//    }
}
