package com.mera.labViewer.Lab;

import com.mera.labViewer.ScheduledRequest.UserNameData;
import com.mera.labViewer.Utils.Consts;

import java.util.ArrayList;
import java.util.List;

public class LabPC {
		
	@Override
	public String toString() {
		return "LabPC [ipAddress=" + ipAddress + ", name=" + name + ", state=" + state + ", message=" + message + "]";
	}

	private String ipAddress;
	private String name;
	private List<String> state;
	private String message;
	
	protected LabPC(String ipAddress, String name, List<String> state, String message) {
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
		this.state = new ArrayList<>();
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
	public List<String> getState() {
		return state;
	}
	public void setState(List<String> state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean updatePC(LabPC loadedPC) {
		boolean flag = false;
		if (!getIpAddress().equals(loadedPC.getIpAddress())) {
			flag = true;
			setIpAddress(loadedPC.getIpAddress());
		}

		if (!getName().equals(loadedPC.getName())) {
			flag = true;
			setName(loadedPC.getName());
		}

		if (getMessage().equals("")) {
			flag = true;
			setMessage(loadedPC.getMessage());
		}
		return flag;
	}
	
}
