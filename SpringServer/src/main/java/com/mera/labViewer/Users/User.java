package com.mera.labViewer.Users;

import java.util.ArrayList;

import com.mera.labViewer.Utils.Consts;

public class User {
	private String name;
	private ArrayList<String> hosts;
	
	public User(String name, ArrayList<String> hosts) {
		super();
		this.name = name;
		this.hosts = hosts;
	}

	public User() {
		super();
		this.name = Consts.DEFAULT_USER_NAME;
		this.hosts = new ArrayList<String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getHosts() {
		return hosts;
	}

	public void setHosts(ArrayList<String> hosts) {
		this.hosts = hosts;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", hosts=" + hosts + "]";
	}
	
	
}
