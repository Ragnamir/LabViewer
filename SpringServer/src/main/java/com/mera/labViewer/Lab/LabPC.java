package com.mera.labViewer.Lab;

import com.mera.labViewer.Utils.Consts;

public class LabPC {
		
	@Override
	public String toString() {
		return "LabPC [ipAddress=" + ipAddress + ", name=" + name + ", state=" + state + ", message=" + message + "]";
	}

	private String ipAddress;
	private String name;
	private String state;
	private String message;
	
	protected LabPC(String ipAddress, String name, String state, String message) {
		super();
		this.ipAddress = ipAddress;
		this.name = name;
		this.state = state;
		this.message = message;
	}
	
	protected LabPC() {
		super();
		this.ipAddress = "";
		this.name = Consts.DEFAULT_PC_NAME;
		this.state = "";
		this.message = "";
	}

	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void updatePC(LabPC loadedPC) {
		setIpAddress(loadedPC.getIpAddress());
		setName(loadedPC.getName());
		if (getMessage().equals("")) {
			setMessage(loadedPC.getMessage());
		}
	}
	
}
