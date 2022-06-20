package NetworkPack;

import Networking.GameClient;
import Networking.GameServer;

public class Packet02Move extends Packet {

	public Packet02Move(int packetId) {
		super(packetId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeData(GameClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeData(GameServer client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getData() {
		// TODO Auto-generated method stub
		return null;
	}


}