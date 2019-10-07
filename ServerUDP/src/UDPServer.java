import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class UDPServer {
	
	public static void main(String [] args)throws IOException {
        DatagramSocket socket = new DatagramSocket(new InetSocketAddress(61363));
        System.out.println(InetAddress.getLocalHost());
        System.out.println(socket.getLocalPort());
        
        int i = 0;
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
        }
	}
}
