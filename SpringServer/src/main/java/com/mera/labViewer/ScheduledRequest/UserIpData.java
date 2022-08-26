package com.mera.labViewer.ScheduledRequest;

public class UserIpData {
	private String iP;

	public UserIpData(String iP) {
		super();
		this.iP = iP;
	}
	
	
	public UserIpData() {
		super();
		this.iP = "";
	}


	public String getIP() {
		return iP;
	}

	public void setIP(String iP) {
		this.iP = iP;
	}

	@Override
	public String toString() {
		return "UserIpData [iP=" + iP + "]";
	};
}
