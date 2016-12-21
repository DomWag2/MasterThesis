package com.wagner.dominik;

import okhttp3.*;
import org.javatuples.Triplet;
import sun.nio.cs.StandardCharsets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static double getAnswer(String w1, String w2) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String content = "{\"corpus\": \"wiki-2014\", \"model\": \"GLOVE\", \"language\": \"EN\","
                + "\"scoreFunction\": \"COSINE\", \"pairs\": [{\"t1\":\""+w1+"\", \"t2\": \""+w2+"\"}]}";

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("http://alphard.fim.uni-passau.de:8916/indra/v1/relatedness")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        String cd = response.body().string();
        int start = cd.indexOf("score\":")+"score\":".length();
        int end = cd.indexOf("}]");
        double scoreV = Double.parseDouble(cd.substring(start, end));
        return scoreV;
    }
    public static void main( String[] args ) throws IOException {
        String fileName = "C:\\Users\\Dominik Wagner\\IdeaProjects\\Dialog\\mt\\src\\main\\java\\com\\wagner\\dominik\\Scenarios.txt";
        String outFile = "resultsGLOVE.txt";
        Writer out = Files.newBufferedWriter(Paths.get(outFile));


        //read file into stream, try-with-resources
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        ArrayList<Triplet<String, String, Double>> result = new ArrayList();
        ArrayList<Double> calcA = new ArrayList<Double>();


        String w1 = "";
        String w2 = "";
        ArrayList<Double> calc = new ArrayList<Double>();
        int i = 0;

        while ((line = br.readLine()) != null){
            if (line.length() > 1) {

                for (String w : line.split(" ")) {
                    w.replace(",", "");
                    w.replace(".", "");
                    w.replace(";", "");
                    w.replace("!", "");
                    w.replace("?", "");
                    if (w1 == "") {
                        w1 = w;
                    } else if (w2 == "") {
                        w2 = w;
                        System.out.println(w1);
                        System.out.println(w2);
                        Double answer = getAnswer(w1, w2);
                        Triplet<String, String, Double> trp = Triplet.with(w1, w2, answer);
                        result.add(trp);
                        calc.add(answer);
                        calcA.add(answer);
                    } else {
                        w1 = w2;
                        w2 = w;
                        System.out.println(w1);
                        System.out.println(w2);
                        Double answer = getAnswer(w1, w2);
                        Triplet<String, String, Double> trp = Triplet.with(w1, w2, answer);
                        result.add(trp);
                        calc.add(answer);
                        calcA.add(answer);
                    }
                }

            } else {
                double sum = 0;
                for (Double d : calc) {
                    sum += d;
                }
                System.out.println("for line"+"\t"+i+"\t" + sum);
                out.write("for line"+"\t"+i+"\t" + sum);
                out.write("\n");
                i += 1;
                w1 = "";
                w2 = "";
                calc = new ArrayList<Double>();
            }
        }
        double sums = 0;
        for (Double d : calcA) {
            sums += d;
        }
        System.out.println("all in all"+"\t"+sums);
        out.write("all in all"+"\t"+sums);
        out.close();





    }

}

