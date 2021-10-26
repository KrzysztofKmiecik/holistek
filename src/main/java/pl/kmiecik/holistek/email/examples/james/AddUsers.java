package pl.kmiecik.holistek.email.examples.james;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class AddUsers {


    public static void main(String[] args) throws IOException {

        Socket s=new Socket("localhost",4555);
        PrintStream ps=new PrintStream(s.getOutputStream());
        ps.println("root");
        ps.println("root");
        ps.println("adduser john hello");
        ps.println("adduser jack hello");
        ps.println("listusers");
        ps.println("quit");
        BufferedReader br=new BufferedReader(
                new InputStreamReader(s.getInputStream()));

        String line =null;
        while((line= br.readLine())!=null)
            System.out.println(line);
        ps.close();br.close();s.close();



    }

}
