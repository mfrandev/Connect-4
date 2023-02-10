package Network.Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", 5050)) {

            PrintWriter pr = new PrintWriter(s.getOutputStream());
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter a number to send to the server");
            int num = sc.nextInt();
            pr.println(num);
            pr.flush();

            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String myInt = br.readLine();
            System.out.println("Server said: " + myInt);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
