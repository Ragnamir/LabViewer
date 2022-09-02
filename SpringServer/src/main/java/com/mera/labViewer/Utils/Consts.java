package com.mera.labViewer.Utils;

public class Consts {
	//Default data
	public static final String UNREACHABLE = "Unreachable";
	public static final String DEFAULT_USER_NAME = "Unknown user";
	public static final String DEFAULT_LAB_NAME = "Default lab name";
	public static final String DEFAULT_PC_NAME = "Default PC name";
	
	//Scheduled task strings
	public static final String PROTOCOL = "http://";
	public static final String PORT = ":18080";
	public static final String DETAILED_URL = "/detailed";
	public static final String NAMES_URL = "/names";
	
	//File strings
	public static final String LAB_CONFIG_FILE = "LabsConfig.json";
	public static final String USER_CONFIG_FILE = "UsersConfig.json"; 
	
	//Logging strings
	public static final String LAB = "Lab ";
	public static final String USER = "User "; 
	public static final String CONFIG_LOADED = "config loaded.";
	public static final String ERROR_LOADING_CONFIG = "config loading ERROR!";
	public static final String SCHEDULED_TASK_INPROGRESS = "Scheduled task in progress";
	public static final String PROCESSING_LAB = "Processing lab ";
	public static final String REQUEST_URL = "Request for ";
	public static final String REPLY_URL = "Got reply for ";
	public static final String UPDATING_FRONT = "Got updates. Sending to front-end.";
}
