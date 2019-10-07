import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UDPServer {
	DatagramSocket socket;
	SocketAddress addr;
	public UDPServer () throws IOException {
//		pingpong();
//		joke();
		startServer();
	}
	
	public static void main(String [] args) throws IOException{
      new UDPServer();
	}
	
	private void pingpong () throws SocketException, IOException {
		  	DatagramSocket socket = new DatagramSocket(new InetSocketAddress(61363));
	        System.out.println(InetAddress.getLocalHost());
	        System.out.println(socket.getLocalPort());
	        
	        int i = 0;
	        double start = System.nanoTime();
	        while(i<=30) {
	        byte[] message = new byte[512];
	        DatagramPacket packet = new DatagramPacket(message, message.length);
	        socket.receive(packet);
	        String receive = new String(packet.getData(),packet.getOffset(), packet.getLength());
	        String num = receive.substring((receive.indexOf(' ')+1));
	        i = Integer.parseInt(num) +1;
	        System.out.println(receive);
	        SocketAddress addr = packet.getSocketAddress();
	        
	        packet = null;
	        
	        byte[] send = ("ServerHello " + i).getBytes();
	        packet = new DatagramPacket(send, send.length);
	        socket.connect(addr);
	        socket.send(packet);
	        i++;
	        }
	        
	        System.out.println("time: " + (System.nanoTime()-start));
	        socket.close();
	}
	
	private void startServer() throws IOException {
		socket = new DatagramSocket(new InetSocketAddress(61363));
		while(true) {
		byte[] receiveQ = new byte[512];
        DatagramPacket rp = new DatagramPacket(receiveQ, receiveQ.length);
        socket.receive(rp);
        addr = rp.getSocketAddress();
        String receive = new String(rp.getData(),rp.getOffset(), rp.getLength());
        if(receive.equals("joke")) {
        	joke();
        }
		}
		
	}
	private void joke() throws IOException {
//		DatagramSocket socket = new DatagramSocket(new InetSocketAddress(61363));
//		System.out.println("Server socket opened");
		
//		byte[] receiveQ = new byte[512];
//        DatagramPacket rp = new DatagramPacket(receiveQ, receiveQ.length);
//        socket.receive(rp);
//        SocketAddress addr = rp.getSocketAddress();
//        String receive = new String(rp.getData(),rp.getOffset(), rp.getLength());
        String fullJoke;
        String question;
        String answer = "Oops, I've lost the answer!";
//		if(receive.equals("joke")) {
		int i = (int)(1+Math.random()*countJokes());
			fullJoke = getJokeLine(i);
			question = fullJoke.substring(1, fullJoke.indexOf("-"));
			answer = fullJoke.substring(fullJoke.indexOf("-")+1,fullJoke.length());
			byte[] send = (question).getBytes();
	        DatagramPacket sp = new DatagramPacket(send, send.length);
	        socket.connect(addr);
	        socket.send(sp);
//		}
		
		byte[] receiveA = new byte[512];
        DatagramPacket rp = new DatagramPacket(receiveA, receiveA.length);
        socket.receive(rp);
        String receive = new String(rp.getData(),rp.getOffset(), rp.getLength());
		if(receive.equals("Tell me")) {
			byte[] sendA = (answer).getBytes();
	        DatagramPacket spA = new DatagramPacket(sendA, sendA.length);
	        socket.connect(addr);
	        socket.send(spA);
		}
//		System.out.println("Server socket closed");
		
	}
	
	private int countJokes() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("jokes.txt"));
		int count = 0;
		while(sc.hasNext()) {
			if(sc.hasNextInt()) {
			count++;
			}
			sc.nextLine();
		}
		return count;
	}
	
	private String getJokeLine(int i) throws IOException {
		Scanner sc = new Scanner(new File("jokes.txt"));
		while (sc.hasNext()) {
			int x = sc.nextInt();
			if(x == i) {
				String joke = sc.nextLine();
				return joke;
			}
			else{
				sc.nextLine();
			}
		}
		return "No joke, I couldn't find that joke";
	}
}
