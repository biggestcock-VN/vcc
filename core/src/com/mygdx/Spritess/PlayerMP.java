package com.mygdx.Spritess;

import java.net.InetAddress;

import com.badlogic.gdx.physics.box2d.World;

public class PlayerMP extends testMainPlayer{
    
	public String username;
	public InetAddress ipAdress;
	public int port;
	public PlayerMP(World world,String username,InetAddress ipAddress, int port) {
		super(world);
		super.defineBody();
		this.username = username;
		this.ipAdress = ipAddress;
		this.port = port;
	}
	
	public String getUsername() {
        return this.username;
    }


}
