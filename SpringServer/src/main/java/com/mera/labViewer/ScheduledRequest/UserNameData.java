package com.mera.labViewer.ScheduledRequest;

import com.mera.labViewer.Utils.Consts;

public class UserNameData {
	private String name;

	protected UserNameData(String name) {
		super();
		this.name = name;
	}

	protected UserNameData() {
		super();
		this.name = Consts.DEFAULT_USER_NAME;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserNameData [name=" + name + "]";
	}
}
