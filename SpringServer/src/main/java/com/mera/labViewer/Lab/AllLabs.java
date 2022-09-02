package com.mera.labViewer.Lab;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Component;

@Component
public class AllLabs {
	private ArrayList<Lab> labs;
	private boolean updated = false;

	public AllLabs(ArrayList<Lab> labs) {
		super();
		this.labs = labs;
	}
	
	public AllLabs() {
		super();
		this.labs = new ArrayList<Lab>();
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public ArrayList<Lab> getLabs() {
		return labs;
	}

	public void setLabs(ArrayList<Lab> labs) {
		setUpdated(true);
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
		setUpdated(false);
		for (Lab loadedLab : loadedLabs.getLabs()) {
			Lab lab = getLabByName(loadedLab.getName());
			if (null == lab) {
				setUpdated(true);
				labs.add(loadedLab);
			} else {
				setUpdated(isUpdated() || lab.updateLab(loadedLab));
			}
		}

		Iterator<Lab> iter = getLabs().iterator();
		while (iter.hasNext()) {
			Lab lab = iter.next();
			if (loadedLabs.getLabByName(lab.getName()) == null) {
				setUpdated(true);
				iter.remove();
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
