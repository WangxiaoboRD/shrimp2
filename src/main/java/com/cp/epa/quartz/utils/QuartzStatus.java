package com.cp.epa.quartz.utils;

public enum QuartzStatus {
	//LOAD("LOAD"),//装载
	//UNLOAD("UNLOAD"),//卸载
	ON("ON"),//启动
	PAUSE("PAUSE"),//暂停
	OFF("OFF");//停止
	private String value;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private QuartzStatus(String value) {
		this.value = value;
	}
}
