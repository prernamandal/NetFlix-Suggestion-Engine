import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;

public class Keywords {

    static HashMap<String, String> moviePlot = new HashMap<>();

    public static void main(String[] args) {
        try {
            HashSet<String> myGenres = new HashSet<>();
            myGenres.add("Short");
            myGenres.add("Drama");
            myGenres.add("Comedy");
            myGenres.add("Documentary");
            myGenres.add("Adult");
            myGenres.add("Action");
            myGenres.add("Thriller");
            myGenres.add("Romance");
            myGenres.add("Animation");
            myGenres.add("Family");
            myGenres.add("Horror");
            myGenres.add("Music");
            myGenres.add("History");
            myGenres.add("Sport");

            //Create movie_genre mapping
            HashMap<String, HashSet<String>> movie_genre = new HashMap<>();
            File genresFile = new File("genres_original.list");
            BufferedReader br2 = new BufferedReader(new FileReader(genresFile));
            String gg = "";
            while ((gg = br2.readLine()) != null) {
                String[] ss = gg.split(",");
                String title = ss[0];
                if (myGenres.contains(ss[1])) {
                    if (movie_genre.containsKey(title)) {
                        movie_genre.get(title).add(ss[1]);
                    } else {
                        HashSet<String> genreSet = new HashSet<>();
                        genreSet.add(ss[1]);
                        movie_genre.put(title, genreSet);
                    }
                }
            }
            System.out.println(myGenres);

            File f = new File("keywords.list");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = "";
            HashMap<String, String> movieKeywords = new HashMap<>();
            while ((line = br.readLine()) != null) {
                String[] ss = line.split("\t");
                if (movieKeywords.get(ss[0]) == null) {
                    movieKeywords.put(ss[0], ss[1]);
                } else {
                    movieKeywords.put(ss[0], movieKeywords.get(ss[0]) + " " + ss[1]);
                }
            }

            //Read Plots
            readPlot();

            //Read directors
            readDirectors();

            FileWriter fw = new FileWriter(new File("movieKeywordsPlotsDirs.list"));
            fw.write("title,keywords,Short,Drama,Comedy,Documentary,Adult,Action,Thriller,"
                    + "Romance,Animation,Family,Horror,Music,History,Sport\n");
            for (String title : movieKeywords.keySet()) {
                if (movie_genre.get(title) != null && moviePlot.get(title) != null) {
                    fw.write(title + "," + movieKeywords.get(title) + " " + moviePlot.get(title).replaceAll(",", " ") + " " + movie_director.get(title)
                            + (movie_genre.get(title).contains("Short") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("Drama") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("Comedy") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("Documentary") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("Adult") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("Action") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("Thriller") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("Romance") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("Animation") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("Family") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("Horror") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("Music") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("History") ? ",1" : ",0")
                            + (movie_genre.get(title).contains("Sport") ? ",1" : ",0")
                            + "\n");
                }
            }
            fw.flush();
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void readPlot() {
        try {
            File f = new File("plot.list");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = "";
            String lastTitle = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("MV:")) {
                    lastTitle = line.replace("MV: ", "");
                    moviePlot.put(lastTitle, "");
                }
                if (line.startsWith("PL:")) {
                    moviePlot.put(lastTitle, moviePlot.get(lastTitle) + " " + line.replace("PL: ", ""));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static HashMap<String, String> movie_director = new HashMap<>();

    public static void readDirectors() {
        try {
            File f = new File("directors - Copy.list");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String st = "";
            String director = "";
            while ((st = br.readLine()) != null) {
                if (st.isEmpty()) {
                    director = br.readLine().trim().replaceAll(",", " ");
                } else if (movie_director.containsKey(st)) {
                    movie_director.put(st, movie_director.get(st) + "::" + director);
                } else {
                    movie_director.put(st, director);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
