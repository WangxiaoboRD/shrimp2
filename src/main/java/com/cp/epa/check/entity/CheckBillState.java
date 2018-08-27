package com.cp.epa.check.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 审核对象
 * 类名：Check  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 3:37:35 PM 
 * @version Dec 3, 2014
 */
@Entity
@Table(name="base_check_bill_state")
public class CheckBillState extends BaseEntity{
	
	private int code; //编码
	private String billId;//单据编码
	private String billType;//单据类
	//private String billLevel;//审核级别
	private String bussName;//业务名称
	
	private Character oneCheck;//一级审核状态
	private String oneCheckUser;//一级审核人
	private String oneCheckDate;//一级审核时间
	
	private Character twoCheck;//二级审核状态
	private String twoCheckUser;//二级审核人
	private String twoCheckDate;//二级审核时间
	
	private Character threeCheck;//三级审核状态
	private String threeCheckUser;//三级审核人
	private String threeCheckDate;//三级审核时间
	
	private Character fourCheck;//四级审核状态
	private String fourCheckUser;//四级审核人
	private String fourCheckDate;//四级审核时间
	
	private Character fiveCheck;//五级审核状态
	private String fiveCheckUser;//五级审核人
	private String fiveCheckDate;//五级审核时间
	
	private Character sixCheck;//六级审核状态
	private String sixCheckUser;//六级审核人
	private String sixCheckDate;//六级审核时间
	
	private Character sevenCheck;//七级审核状态
	private String sevenCheckUser;//七级审核人
	private String sevenCheckDate;//七级审核时间
	
	private Character eightCheck;//八级审核状态
	private String eightCheckUser;//八级审核人
	private String eightCheckDate;//八级审核时间
	
	@Id
	@GeneratedValue
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
//	public String getBillLevel() {
//		return billLevel;
//	}
//	public void setBillLevel(String billLevel) {
//		this.billLevel = billLevel;
//	}
	public Character getOneCheck() {
		return oneCheck;
	}
	public void setOneCheck(Character oneCheck) {
		this.oneCheck = oneCheck;
	}
	public String getOneCheckUser() {
		return oneCheckUser;
	}
	public void setOneCheckUser(String oneCheckUser) {
		this.oneCheckUser = oneCheckUser;
	}
	public String getOneCheckDate() {
		return oneCheckDate;
	}
	public void setOneCheckDate(String oneCheckDate) {
		this.oneCheckDate = oneCheckDate;
	}
	public Character getTwoCheck() {
		return twoCheck;
	}
	public void setTwoCheck(Character twoCheck) {
		this.twoCheck = twoCheck;
	}
	public String getTwoCheckUser() {
		return twoCheckUser;
	}
	public void setTwoCheckUser(String twoCheckUser) {
		this.twoCheckUser = twoCheckUser;
	}
	public String getTwoCheckDate() {
		return twoCheckDate;
	}
	public void setTwoCheckDate(String twoCheckDate) {
		this.twoCheckDate = twoCheckDate;
	}
	public Character getThreeCheck() {
		return threeCheck;
	}
	public void setThreeCheck(Character threeCheck) {
		this.threeCheck = threeCheck;
	}
	public String getThreeCheckUser() {
		return threeCheckUser;
	}
	public void setThreeCheckUser(String threeCheckUser) {
		this.threeCheckUser = threeCheckUser;
	}
	public String getThreeCheckDate() {
		return threeCheckDate;
	}
	public void setThreeCheckDate(String threeCheckDate) {
		this.threeCheckDate = threeCheckDate;
	}
	public Character getFourCheck() {
		return fourCheck;
	}
	public void setFourCheck(Character fourCheck) {
		this.fourCheck = fourCheck;
	}
	public String getFourCheckUser() {
		return fourCheckUser;
	}
	public void setFourCheckUser(String fourCheckUser) {
		this.fourCheckUser = fourCheckUser;
	}
	public String getFourCheckDate() {
		return fourCheckDate;
	}
	public void setFourCheckDate(String fourCheckDate) {
		this.fourCheckDate = fourCheckDate;
	}
	public Character getFiveCheck() {
		return fiveCheck;
	}
	public void setFiveCheck(Character fiveCheck) {
		this.fiveCheck = fiveCheck;
	}
	public String getFiveCheckUser() {
		return fiveCheckUser;
	}
	public void setFiveCheckUser(String fiveCheckUser) {
		this.fiveCheckUser = fiveCheckUser;
	}
	public String getFiveCheckDate() {
		return fiveCheckDate;
	}
	public void setFiveCheckDate(String fiveCheckDate) {
		this.fiveCheckDate = fiveCheckDate;
	}
	public Character getSixCheck() {
		return sixCheck;
	}
	public void setSixCheck(Character sixCheck) {
		this.sixCheck = sixCheck;
	}
	public String getSixCheckUser() {
		return sixCheckUser;
	}
	public void setSixCheckUser(String sixCheckUser) {
		this.sixCheckUser = sixCheckUser;
	}
	public String getSixCheckDate() {
		return sixCheckDate;
	}
	public void setSixCheckDate(String sixCheckDate) {
		this.sixCheckDate = sixCheckDate;
	}
	public Character getSevenCheck() {
		return sevenCheck;
	}
	public void setSevenCheck(Character sevenCheck) {
		this.sevenCheck = sevenCheck;
	}
	public String getSevenCheckUser() {
		return sevenCheckUser;
	}
	public void setSevenCheckUser(String sevenCheckUser) {
		this.sevenCheckUser = sevenCheckUser;
	}
	public String getSevenCheckDate() {
		return sevenCheckDate;
	}
	public void setSevenCheckDate(String sevenCheckDate) {
		this.sevenCheckDate = sevenCheckDate;
	}
	public Character getEightCheck() {
		return eightCheck;
	}
	public void setEightCheck(Character eightCheck) {
		this.eightCheck = eightCheck;
	}
	public String getEightCheckUser() {
		return eightCheckUser;
	}
	public void setEightCheckUser(String eightCheckUser) {
		this.eightCheckUser = eightCheckUser;
	}
	public String getEightCheckDate() {
		return eightCheckDate;
	}
	public void setEightCheckDate(String eightCheckDate) {
		this.eightCheckDate = eightCheckDate;
	}
	public String getBussName() {
		return bussName;
	}
	public void setBussName(String bussName) {
		this.bussName = bussName;
	}
}
