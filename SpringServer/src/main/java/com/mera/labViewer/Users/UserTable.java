package com.mera.labViewer.Users;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.mera.labViewer.Utils.Consts;

@Component
public class UserTable {
	private ArrayList<User> users;

	public UserTable(ArrayList<User> users) {
		super();
		this.users = users;
	}

	public UserTable() {
		super();
		this.users = new ArrayList<User>();
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "UserTable [Users=" + users + "]";
	}
	
	public String getUserByHost(String host) {
		String result = Consts.DEFAULT_USER_NAME;
		for (User user : getUsers()) {
			ArrayList<String> hosts = user.getHosts();
			for (String currentHost : hosts) {
				if (host.toLowerCase().equals(currentHost.toLowerCase())) {
					result = user.getName();
				}
			}
		}
		return result;
	}
	
	public void updateUserTable(UserTable newUserTable) {
		this.setUsers(newUserTable.getUsers());
	}
}
