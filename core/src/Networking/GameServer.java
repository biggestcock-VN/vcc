package Networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.mygdx.Spritess.PlayerMP;
import com.mygdx.gamestuff.GameScreen;

import NetworkPack.Packet;
import NetworkPack.Packet.PacketTypes;
import NetworkPack.Packet00Login;
import NetworkPack.Packet01Disconnect;

public class GameServer extends Thread {

		private DatagramSocket socket;
		private GameScreen game;
		private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
		private int count = 0;
		
		public GameServer(GameScreen gameScreen) 
		{
			this.game = gameScreen;
			try {
				this.socket = new DatagramSocket(1384);
			}
			catch (SocketException e) {
				// TODO Auto-generated catch block
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
				  parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
//				  String message = new String(packet.getData());
//				   System.out.println("Client>" + message);
//				  if(message.trim().equalsIgnoreCase("ping")) {
//				 System.out.println("returning");
//				  sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
//			  }
		      }
			  
		}
		
		private void parsePacket(byte[] data, InetAddress address, int port) {
			// TODO Auto-generated method stub
			String message = new String(data).trim();
			PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
			 Packet packet = null;
			switch(type) {
			default:
			case INVALID:
			   break;
			case LOGIN:
				count++;
			     packet = new Packet00Login(data);
				System.out.println("["+address.getHostAddress()+":" +port +"]" + ((Packet00Login) packet).getUsername() + " has connected");
				PlayerMP player = null;
				if(address.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
                player = new PlayerMP(game.world,((Packet00Login) packet).getUsername(),address, port); 
				}
				if(player != null && count > 0) {
					this.connectedPlayers.add(player);
					game.mpPlayer = player;
				}
				break;
			case MOVE:
			    break;
			case DISCONNECT:
				packet = new Packet01Disconnect(data);
	            System.out.println("[" + address.getHostAddress() + ":" + port + "] "
	                    + ((Packet01Disconnect) packet).getUsername() + " has left...");
	            this.removeConnection((Packet01Disconnect) packet);
	            break;
					
		}
		}


		public void sendData(byte[] data, InetAddress ipAddress, int port) {
			DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
			try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void sendDataToAllClient(byte[] data) {
			// TODO Auto-generated method stub
			for(PlayerMP p : connectedPlayers) {
				sendData(data, p.ipAdress, p.port);
			}
			
		}
		
		 public void removeConnection(Packet01Disconnect packet) {
		      
		        this.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
		        packet.writeData(this);
		    }

		    public PlayerMP getPlayerMP(String username) {
		        for (PlayerMP player : this.connectedPlayers) {
		            if (player.getUsername().equals(username)) {
		                return player;
		            }
		        }
		        return null;
		    }
		    
		    public int getPlayerMPIndex(String username) {
		        int index = 0;
		        for (PlayerMP player : this.connectedPlayers) {
		            if (player.getUsername().equals(username)) {
		                break;
		            }
		            index++;
		        }
		        return index;
		    }
	

	}
