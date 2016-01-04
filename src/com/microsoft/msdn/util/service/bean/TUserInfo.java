package com.microsoft.msdn.util.service.bean;


import java.io.Serializable;

public class TUserInfo implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private String Usr_ID;

    private String Usr_RealName;

    private String Usr_Phone;

    private String Usr_Email;

	public String getUsr_ID() {
		return Usr_ID;
	}

	public void setUsr_ID(String usr_ID) {
		Usr_ID = usr_ID;
	}

	public String getUsr_RealName() {
		return Usr_RealName;
	}

	public void setUsr_RealName(String usr_RealName) {
		Usr_RealName = usr_RealName;
	}

	public String getUsr_Phone() {
		return Usr_Phone;
	}

	public void setUsr_Phone(String usr_Phone) {
		Usr_Phone = usr_Phone;
	}

	public String getUsr_Email() {
		return Usr_Email;
	}

	public void setUsr_Email(String usr_Email) {
		Usr_Email = usr_Email;
	}

}
