
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mateusz
 */
public class LoadData {

    public static int edge_count;
    private static String[] loaded_data;
    public static String posible_genes;
    public static int[][] verticals;
    public static int[][] macierz;
    public static int chromaticIndex;

    public LoadData(String s, boolean iChromaIndex) {

        GetDataFile(s);

        verticals = new int[edge_count + 1][2];

        int iloscWierzcholkow = 0;

        for (int a = 0; a < loaded_data.length; a++) {
            String[] temp = loaded_data[a].split(" ");
            for (int b = 0; b < temp.length - 1; b++) {
                int VerticalNumber = Integer.valueOf(temp[0]);
                verticals[VerticalNumber][b] = Integer.valueOf(temp[b + 1]);
                if (iloscWierzcholkow < Integer.valueOf(temp[b + 1])) {
                    iloscWierzcholkow = Integer.valueOf(temp[b + 1]);
                }
            }
        }
        int krawedz;
        int wierzcholek1;
        int wierzcholek2;
        macierz = new int[iloscWierzcholkow + 1][edge_count + 1];
        for (int a = 0; a < loaded_data.length; a++) {
            String[] temp = loaded_data[a].split(" ");
            krawedz = Integer.valueOf(temp[0]);
            wierzcholek1 = Integer.valueOf(temp[1]);
            wierzcholek2 = Integer.valueOf(temp[2]);
            macierz[wierzcholek1][krawedz] = 1;
            macierz[wierzcholek2][krawedz] = 1;
        }

        int stopien = 0;
        int stopien_nowy = 0;
        for (int a = 0; a < macierz.length; a++) {
            if (stopien < stopien_nowy) {
                stopien = stopien_nowy;
            }
            stopien_nowy = 0;
            for (int b = 0; b < edge_count + 1; b++) {
                if (macierz[a][b] == 1) {
                    stopien_nowy++;
                }
            }
        }

        chromaticIndex = stopien + 1;
        if (iChromaIndex) {
            posible_genes = "";
            for (int a = 0; a < chromaticIndex; a++) {
                posible_genes += String.valueOf(a + 1);
                if (a != chromaticIndex) {
                    posible_genes += " ";
                }
            }
        } else {
            posible_genes = "";
            for (int a = 0; a < edge_count; a++) {
                posible_genes += String.valueOf(a + 1);
                if (a != edge_count) {
                    posible_genes += " ";
                }
            }
        }

    }

    public void GetDataFile(String s) {
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(s))) {

            String line = br.readLine();
            edge_count = Integer.parseInt(line);
            loaded_data = new String[edge_count];

            line = br.readLine();
            while (line != null) {
                loaded_data[i++] = line;
                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getFirstVerticalsEdge(int edge) {
        return verticals[edge][0];
    }

    public static int getSecondVerticalsEdge(int edge) {
        return verticals[edge][1];
    }

    public int getEdgeCount() {
        return edge_count;
    }

    public static String getSasiednieKrawedzie(int vertical1, int vertical2) {
        String wartosc = "";
        for (int b = 0; b < edge_count + 1; b++) {
            if (macierz[vertical1][b] == 1) {
                wartosc += String.valueOf(b) + " ";
            }
        }

        for (int b = 0; b < edge_count + 1; b++) {
            if (macierz[vertical2][b] == 1) {
                wartosc += String.valueOf(b) + " ";
            }
        }
        String[] temp = wartosc.split(" ");
        Set<String> set = new HashSet<String>();

        for (int i = 0; i < temp.length; i++) {
            if (!set.contains(temp[i])) {
                set.add(temp[i]);
            }
        }
        String result = "";

        for (String s : set) {
            result += s + " ";
        }

        return result;

    }

}
