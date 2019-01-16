package com.Servlet;

public class Match {
	protected String Uuid; //Ψһ����
    protected String Theme;     //��������
    protected String Address; //������ַ
    protected String Time;       //����ʱ��
    protected String Week;       //��������
    protected String Rule;       //��������
    protected String People;        //��������
    protected String Color;      //�·���ɫ
    protected String Remarks;      //��ע˵��
    protected String Sponsor;    //����������
    protected String Sponsor_openid; //������openid
    protected String User_join;      //�����μӵ���
    protected String User_leave;      //��ٵ���
    protected double Longitude;        //����
    protected double Latitude;        //γ��

    public Match(String Uuid,String Theme,String Time,String Week,String Address,String Rule,String Color,String People,String Remarks,String Sponsor,String Sponsor_openid,String User_join,String User_leave,Double Longitude,Double Latitude) {	
    	this.Uuid=Uuid;
    	this.Theme=Theme;
    	this.Address=Address;
    	this.Time=Time;
    	this.Week=Week;
    	this.Rule=Rule;
    	this.Color=Color;
    	this.People=People;
    	this.Remarks=Remarks;
    	this.Sponsor=Sponsor;
    	this.Sponsor_openid=Sponsor_openid;
    	this.User_join=User_join;
    	this.User_leave=User_leave;	
    	this.Longitude=Longitude;
    	this.Latitude=Latitude;
	}
   


	public String getSponsor_openid() {
		return Sponsor_openid;
	}



	public void setSponsor_openid(String sponsor_openid) {
		Sponsor_openid = sponsor_openid;
	}



	public String getWeek() {
		return Week;
	}



	public void setWeek(String week) {
		Week = week;
	}



	public double getLongitude() {
		return Longitude;
	}



	public void setLongitude(double longitude) {
		Longitude = longitude;
	}



	public double getLatitude() {
		return Latitude;
	}



	public void setLatitude(double latitude) {
		Latitude = latitude;
	}



	public String getSponsor() {
		return Sponsor;
	}

	public void setSponsor(String sponsor) {
		Sponsor = sponsor;
	}

	public String getUser_join() {
		return User_join;
	}

	public void setUser_join(String user_join) {
		User_join = user_join;
	}

	public String getUser_leave() {
		return User_leave;
	}

	public void setUser_leave(String user_leave) {
		User_leave = user_leave;
	}

	public String getUuid() {
		return Uuid;
	}


	public void setUuid(String uuid) {
		Uuid = uuid;
	}

	public String getRule() {
		return Rule;
	}

	public void setRule(String rule) {
		Rule = rule;
	}

	public String getPeople() {
		return People;
	}

	public void setPeople(String people) {
		People = people;
	}

	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		Color = color;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}


	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getTheme() {
		return Theme;
	}

	public void setTheme(String theme) {
		Theme = theme;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}
    
   
    

    
  
 }