package com.wagner.dominik;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Dominik Wagner on 16.12.2016.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\Dominik Wagner\\IdeaProjects\\Dialog\\mt\\src\\main\\java\\com\\wagner\\dominik\\Scenarios.txt";

        //read file into stream, try-with-resources
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null){
            if(line.length() < 1){
                System.out.println("haha");
            }
            //System.out.println(line);
        }


    }
}
