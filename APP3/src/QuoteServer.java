import java.io.*;
import java.net.InetAddress;

public class QuoteServer {
    public static void main(String[] args) throws IOException {
        QuoteServerThread a =  new QuoteServerThread();
        a.start();
    }
}