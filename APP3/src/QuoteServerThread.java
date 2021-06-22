import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;


public class QuoteServerThread extends Thread{
    DatagramSocket socket = null;
    BufferedReader in = null;
    BufferedWriter allo = null;
    boolean moreQuotes = true;
    String addresse = null;
    byte[] buf = new byte[200];

    public QuoteServerThread() throws IOException {
        this("QuoteServer");
    }
    ArrayList<PacketReseau> listPktR = new ArrayList<PacketReseau>();

    @Override
    public void run() {

        while (moreQuotes) {
         recevoir();


        }
        socket.close();
    }
    private void loadNewFile(String name) {
        if (in != null){
            try {
                in.close();
                in = new BufferedReader(new FileReader(name));
            }catch (FileNotFoundException e){
                System.out.println("File not found");
                System.out.println(e.getMessage());
            }catch (IOException e){
                System.out.println(e.getMessage());
            }catch (Exception e){
                System.out.println("comment");
            }

        }

    }
    private String getText(){

        String value = null;
        String returnValue = null;
        try {
            while ((value = in.readLine()) != null) {
                returnValue += value;
            }
            in.close();
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }

        return returnValue;
    }
    private boolean recevoir() {
        try {

            DatagramSocket socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        DatagramPacket  packet = new DatagramPacket(buf,buf.length);
        try {
            socket.receive(packet);
            System.out.println(new String(packet.getData()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            allo = new  BufferedWriter(new FileWriter("fichier.txt",true));
            String stringu = new String (packet.getData());
            allo.write(stringu.substring(0));
            allo.close();
            packet = new DatagramPacket(stringu.getBytes(),200,InetAddress.getLoopbackAddress(),25420);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private String getNextQuote() {
        String returnValue = null;

        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
                moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }


    public QuoteServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(25420);

        try {
            in = new BufferedReader(new FileReader("one-liners.txt"));
        }
        catch (FileNotFoundException e){
            System.err.println("Couldn't open quote file.  Serving time instead.");
        }
    }
}
