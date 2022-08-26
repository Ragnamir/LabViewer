package com.mera.labViewer.Lab;

import java.util.ArrayList;

import com.mera.labViewer.Utils.Consts;

public class Lab {

	@Override
	public String toString() {
		return "Lab [name=" + name + ", labPCs=" + labPCs + ", ]";
	}

	private String name;
	private String vmWareURI;
	private String vmWareUsername;
	private String vmWarePwd;
	private ArrayList<LabPC> labPCs;
	
	public Lab(String name, ArrayList<LabPC> labPCs) {
		super();
		this.name = name;
		this.labPCs = labPCs;
	}

	protected Lab() {
		super();
		this.name = Consts.DEFAULT_LAB_NAME;
		this.labPCs = new ArrayList<LabPC>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<LabPC> getLabPCs() {
		return labPCs;
	}

	public void setLabPCs(ArrayList<LabPC> labPCs) {
		this.labPCs = labPCs;
	}
	
	public String getVmWareURI() {
		return vmWareURI;
	}

	public void setVmWareURI(String vmWareURI) {
		this.vmWareURI = vmWareURI;
	}

	public String getVmWareUsername() {
		return vmWareUsername;
	}

	public void setVmWareUsername(String vmWareUsername) {
		this.vmWareUsername = vmWareUsername;
	}

	public String getVmWarePwd() {
		return vmWarePwd;
	}

	public void setVmWarePwd(String vmWarePwd) {
		this.vmWarePwd = vmWarePwd;
	}

	private LabPC getPCByIp(String Ip) {
		for (LabPC labPC : labPCs) {
			if (labPC.getIpAddress().equals(Ip)) {
				return labPC;
			}
		}
		return null;
	}
	
	public void updateLab(Lab loadedLab) {
		this.setName(loadedLab.getName());
		
		for (LabPC loadedPC : loadedLab.getLabPCs()) {
			LabPC labPC = getPCByIp(loadedPC.getIpAddress());
			if (null == labPC) {
				labPCs.add(loadedPC);
			} else {
				labPC.updatePC(loadedPC);
			}
		}
	}
	
	public void updateMessage(Lab loadedLab) {
		for (LabPC labPC : labPCs) {
			LabPC loadedLabPC = loadedLab.getPCByIp(labPC.getIpAddress());
			if (null != loadedLabPC) {
				labPC.setMessage(loadedLabPC.getMessage());
			}
		}
	}
	
}
