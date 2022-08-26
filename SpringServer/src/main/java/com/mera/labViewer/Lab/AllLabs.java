package com.mera.labViewer.Lab;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class AllLabs {
	private ArrayList<Lab> labs;

	public AllLabs(ArrayList<Lab> labs) {
		super();
		this.labs = labs;
	}
	
	public AllLabs() {
		super();
		this.labs = new ArrayList<Lab>();
	}

	public ArrayList<Lab> getLabs() {
		return labs;
	}

	public void setLabs(ArrayList<Lab> labs) {
		this.labs = labs;
	}

	@Override
	public String toString() {
		return "AllLabs [labs=" + labs + "]";
	}
	
	private Lab getLabByName(String name) {
		for (Lab lab : labs) {
			if (lab.getName().equals(name)) {
				return lab;
			}
		}
		return null;
	}
	
	public void updateLabs(AllLabs loadedLabs) {
		for (Lab loadedLab : loadedLabs.getLabs()) {
			Lab lab = getLabByName(loadedLab.getName());
			if (null == lab) {
				labs.add(loadedLab);
			} else {
				lab.updateLab(loadedLab);
			}
		}
	}
	
	public void updateMessages(AllLabs loadedLabs) {
		for (Lab lab : labs) {
			Lab loadedLab = loadedLabs.getLabByName(lab.getName());
			if (null != loadedLab) {
				lab.updateMessage(loadedLab);
			} 
		}
	}
	
}
