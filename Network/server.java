package Network;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class server {

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(8080)) {
            Socket s = ss.accept();
            System.out.println("client connected!");

            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String myInt = br.readLine();
            System.out.println("Client said: " + myInt);

            PrintWriter pr = new PrintWriter(s.getOutputStream());
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter a number to send to the server");
            int num = sc.nextInt();
            pr.println(num);
            pr.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
