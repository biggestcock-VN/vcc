package Networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.mygdx.game.MyGdxGame;
import com.mygdx.gamestuff.GameScreen;

public class GameClient extends Thread{

	private InetAddress ipAddress;
	private DatagramSocket socket;
	private GameScreen game;
	
	public GameClient(GameScreen gameScreen, String ipAdress) 
	{
		this.game = gameScreen;
		try {
			this.socket = new DatagramSocket();
		
		this.ipAddress = InetAddress.getByName(ipAdress);
		}
		catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		  while(true) {
			  byte[] data = new byte[1024];
			  DatagramPacket packet = new DatagramPacket(data, data.length);
			  try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  System.out.println("Server>" + new String(packet.getData()));
		  }
	}
	
	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1384);
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
