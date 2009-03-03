/**
 * (c) 2009 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.upnpcontrol;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Communication {

	private static String SSDP = "M-SEARCH * HTTP/1.1\r\n" + "HOST: 239.255.255.250:1900\r\n"
			+ "MAN: \"ssdp:discover\"\r\n" + "MX: 5\r\n"
			+ "ST: urn:schemas-upnp-org:device:MediaServer:1\r\n\r\n";

	private static class SsdpBroadcast implements Runnable {
		public void run() {
			try {
				MulticastSocket udpSocket = new MulticastSocket(4711);
				InetAddress group = InetAddress.getByName("239.255.255.250");
				byte[] data = SSDP.getBytes("utf-8");
				udpSocket.joinGroup(group);
				DatagramPacket packet = new DatagramPacket(data, data.length, group, 1900);
				for (int i = 0; i < 10; i++) {
					System.out.println("sending broadcast from port " + udpSocket.getLocalPort()
							+ " ...");
					udpSocket.send(packet);
					Thread.sleep(1000);
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void receiveBroadCast() {
		try {
			InetAddress group = InetAddress.getByName("239.255.255.250");
			MulticastSocket udpSocket = new MulticastSocket(1900);
			udpSocket.joinGroup(group);
			//DatagramSocket udpSocket = new DatagramSocket(4711);
			new Thread(new SsdpBroadcast()).start();
			Thread.sleep(1000);
			while (true) {
				DatagramPacket reply = new DatagramPacket(new byte[1024], 1024);
				System.out.println("waiting for reply to " + udpSocket.getLocalSocketAddress()
						+ " ...");
				udpSocket.receive(reply);
				String msg = new String(reply.getData());
				System.out.println("Received message from: " + reply.getAddress() + ":"
						+ reply.getPort() + "\n" + msg);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		receiveBroadCast();
	}
}
