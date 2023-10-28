package com.m2at.constants;

public enum ConfigPath {

	CONFIG_FILE_PATH("config/");
	
	private String value;
	
	private ConfigPath(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
