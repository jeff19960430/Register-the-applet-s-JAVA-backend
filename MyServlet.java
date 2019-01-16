package com.Servlet;

import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mysql.jdbc.StringUtils;
import com.sun.security.jgss.InquireSecContextPermission;

import net.sf.json.JSONArray;

import net.sf.json.JSONObject;


/**
 * Servlet implementation class WxServlet
 */
@WebServlet("/WxServlet")

//Servlet�̳�BaseServlet������ʵ�ֶ��servlet�ķ�����ʵ�ֶ������
public class MyServlet extends BaseServlet {
  

    /**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	//�洢��������
	public void storage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        
        response.setContentType("text/html;charset=utf-8");          
        /* ������Ӧͷ����ajax������� */  
        response.setHeader("Access-Control-Allow-Origin", "*");  
        /* �Ǻű�ʾ���е��������󶼿��Խ��ܣ� */  
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");  
       
        //��ȡ΢��С����get�Ĳ���ֵ����ӡ
        String match_theme= request.getParameter("match_theme"); 
        System.out.println("match_theme="+match_theme);   
        String match_time = request.getParameter("match_time");  
        String match_week=request.getParameter("match_week");
        String match_address = request.getParameter("match_address");
        String match_rule=request.getParameter("match_rule");
        String match_color=request.getParameter("match_color");
        String match_people=request.getParameter("match_people");
        String match_remarks=request.getParameter("match_remarks");
        String sponsor=request.getParameter("user_name");
        String sponsor_openid=request.getParameter("openid");
        String openid=request.getParameter("openid");
        String longitude1=request.getParameter("longitude");
        String latitude1=request.getParameter("latitude");
        double longitude=Double.valueOf(longitude1);
        double latitude=Double.valueOf(latitude1);
        System.out.println("longitude="+longitude);  
        
         //����ΨһID��������ÿ������
        DataProcessing key=new DataProcessing();
        String uuid=key.getId();
        String id=null;
        String match=null;
        Match em=new Match(uuid,match_theme, match_time,match_week, match_address,match_rule,match_color,match_people,match_remarks,sponsor,sponsor_openid,"","",longitude,latitude);
        Operate add=new Operate();
        add.add_match(em);   //���������ݴ������ݿ�
        //����UUid�ҳ���������Ϣ
        ResultSet resultSet=new Operate().find_matchInformation(uuid);
        while(resultSet.next()) {
        	id=resultSet.getString("id");  //ȡ������ID
        }
        ResultSet user_information=new Operate().find_userId(openid);  //����openid�ҳ��û�����Ϣ
        while(user_information.next()) {
        	match=user_information.getString("match");  //ȡ���û��ı����ֶ�
        }
       //�����ֶ��м������ID��Ȼ�������openid���û���
        String new_userMatch=new DataProcessing().increase_string(match, id);   //�����ַ����Ĵ�����ԭ�����ַ���������µ��ַ�����re_leave��ס
        System.out.println("new_usermatch:"+new_userMatch);
        //��user���У���������id���뵽��Ӧ�û��ı����ֶ��С�
        new Operate().update_userMatch(new_userMatch, openid);
        new Operate().add_matchStatus(uuid, openid,"0", match_time);
        //ʹ��Gson����Ҫ����gson-2.8.0.jar
        String json = new Gson().toJson(em);
        //����ֵ��΢��С����
        Writer out = response.getWriter(); 
        out.write(json);
        out.flush();  
        
        
    }
	
	//ȡ����������
	public void take(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		 response.setContentType("text/html;charset=utf-8");          
	        /* ������Ӧͷ����ajax������� */  
	        response.setHeader("Access-Control-Allow-Origin", "*");  
	        /* �Ǻű�ʾ���е��������󶼿��Խ��ܣ� */  
	        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
	        String openid=request.getParameter("openid");
            String mark_sponsor=null;
	        String user_match=null;
	        ResultSet user_information=new Operate().find_userId(openid);
	        while(user_information.next()) {
	        	user_match=user_information.getString("match");
	        }
             //ȡ����ѯ�������ݼ�
	        ResultSet rs=new Operate().inquire_match(user_match,openid);
	        //����ת������������ת����json����
	        String json=new DataProcessing().resultSetToJson(rs);
	        System.out.println("��ӡ�������ݣ�"+json);
	        Writer out = response.getWriter();
            out.write(json);
	        out.flush();        
	}
	
	//����͸����û�����
	public   void user_info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		 response.setContentType("text/html;charset=utf-8");          
	        /* ������Ӧͷ����ajax������� */  
	        response.setHeader("Access-Control-Allow-Origin", "*");  
	        /* �Ǻű�ʾ���е��������󶼿��Խ��ܣ� */  
	        response.setHeader("Access-Control-Allow-Methods", "GET,POST");

            String appsercet=request.getParameter("appsercet");
            String appid=request.getParameter("appid");
            String Code=request.getParameter("code");
            String grant_type=request.getParameter("grant_type");
            String user_name=request.getParameter("user_name");
            String user_url=request.getParameter("user_url");
            System.out.println("Code:"+Code);
            System.out.println("appsercet:"+appsercet);
            System.out.println("appid:"+appid);
            System.out.println("grant_type:"+grant_type);
            System.out.println("user_name:"+user_name);
            System.out.println("user_url:"+user_url);
            new WeiXinOpenid();
           //����վ���󣬵�ȡ��������ȡopenid
			String openID=WeiXinOpenid.GetOpenID(appid, appsercet, Code);
			System.out.println("openid"+openID);
            //��json��ʽ�����ݣ�ȡ����Ӧ����ֵ
            JSONObject jsonObject = JSONObject.fromObject(openID);
              String openid= (String) jsonObject.get("openid");
             System.out.println("openid:"+openid);  
             //�жϸ�openid�Ƿ��Ѿ�����,������������������ͷ�����������������¼�¼
             ResultSet user_imformation=new Operate().inquire_openid(openid);
             if(user_imformation.next()) {                   //�ж����ݼ��Ƿ���ֵ,���������£�û�о����
            	 User user=new User(openid,user_name,user_url);
            	 new Operate().update_user(user);
             }else {
            	    User user=new User(openid,user_name,user_url);
                    System.out.println("user:"+user);
                     new Operate().add_user(user);
             }
            //��ֵ����Ϊjson���ݣ�����ֵ��΢��С����
            String json = new Gson().toJson(openID);
            Writer out = response.getWriter();
            out.write(openid);
            out.flush();
	        
	}
	
     //�û����������˼·Ϊ��������ı����ֶ��м�һ��ID������ֶ��м�ȥ��ӦID.ȡ���ⳡ�����е����б����˵�������ͷ�񣬴��ظ�΢��С����
	public void sign_up(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		 response.setContentType("text/html;charset=utf-8");          
	        /* ������Ӧͷ����ajax������� */  
	        response.setHeader("Access-Control-Allow-Origin", "*");  
	        /* �Ǻű�ʾ���е��������󶼿��Խ��ܣ� */  
	        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
	        String id_match=null;     //�洢��Ҫ����ı���ID
	        String user_match=null; //�洢�����ݿ���ȡ���Ķ�Ӧ�û��ı����ֶ�
	        String id=null;
	        String status="1";
	        String join=null;   //�ӱ����ֶ���ȡ�����ַ�����������
	        String leave=null;  //������ֶ���ȡ�����ַ�����������
	        String openid=request.getParameter("openid");
	        String uuid=request.getParameter("uuid");
	        String time=request.getParameter("time");
	        System.out.println("openid:"+openid);
	        System.out.println("uuid:"+uuid);	        
	        ResultSet userId=new Operate().find_userId(openid);  //����openid�ҳ��û���id
	        while(userId.next()) {
              id=userId.getString("id");       //��ȡ�µı�����
              user_match=userId.getString("match");  //��ȡ�Ѵ��ڵı����ֶ�
	        }    
	        ResultSet matchId=new Operate().find_matchInformation(uuid);
	        while(matchId.next()) {
	             id_match=matchId.getString("id");       //��ȡ����id
		        } 
	        //����Ҫ����ı���ID���뵽ȡ�����ֶ���
	        String new_userMatch=new DataProcessing().increase_string(user_match, id_match);   //�����ַ����Ĵ�����ԭ�����ַ���������µ��ַ�����re_leave��ס
	       //��user���У���������id���뵽��Ӧ�û��ı����ֶ��С�
	        new Operate().update_userMatch(new_userMatch, openid);
	         //�ҳ���Ӧuuid�ı����ı����ֶ��µ��ַ��������õ����û�id���뵽match��ı����У���������ֶ��и��û�ȥ��
	        ResultSet user_join=new Operate().find_matchInformation(uuid);
	        while(user_join.next()) {
	        	join=user_join.getString("user_join");       //ȡ�����ݼ��У���������
	        	leave=user_join.getString("user_leave");    //ȡ�����ݼ��У���ٵ���
	        }	    
	        String rs_join=new DataProcessing().increase_string(join, id);   //�����ַ����Ĵ�����ԭ�����ַ���������µ��ַ�����re_join��ס
	        String rs_leave=new DataProcessing().reduce_string(leave, id) ; //�����ַ����Ĵ�����ԭ�����ַ����м�ȥĳ���ַ�����re_leave��ס
	        //��match���У����ⳡ�����µı����ֶ��ַ���������ֶ��ַ������滻ԭ���ı�������١�
	        new Operate().update_join(uuid,rs_join,rs_leave);
	        //ȡ���ⳡ�����е����б����˺�����˵�������ͷ�񣬽��д���󣬴��ظ�΢��С����
	        ResultSet register_information=new Operate().register_information(rs_join);
	        ResultSet leave_information=new Operate().leave_information(rs_leave);
	        String json_register=new DataProcessing().resultSetToJson(register_information);
	        String json_leave=new DataProcessing().resultSetToJson(leave_information);
	         //��ȡ����״̬�����û���ⳡ����벢�����䱨��״̬�����������ת̬��Ϊ����״̬
	        ResultSet match_status=new Operate().inquire_matchStatus(uuid, openid);
            if(match_status.next()) {                   //�ж����ݼ��Ƿ���ֵ,���������£�û�о����
           	 new Operate().update_matchStatus(uuid, openid,status);
            }else {
                    new Operate().add_matchStatus(uuid, openid,status,time);
            }
	       //��������json����ĺϲ�Ƕ�ף���ҪJSONObject������кϲ�Ƕ��
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("json_register", json_register);
	        jsonObject.put("json_leave", json_leave);
	        String json = new Gson().toJson(jsonObject);  //ת����json����
	        //�����ݴ���С����
            Writer out=response.getWriter();
            out.write(json);
            out.flush();
	        
	}
	
    //�û������٣�˼·Ϊ��������ı����ֶ��м�һ��ID������ֶ��м�����ӦID��ȡ���ⳡ�����е����б����˵�������ͷ�񣬴��ظ�΢��С����
	public void leave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		 response.setContentType("text/html;charset=utf-8");          
	        /* ������Ӧͷ����ajax������� */  
	        response.setHeader("Access-Control-Allow-Origin", "*");  
	        /* �Ǻű�ʾ���е��������󶼿��Խ��ܣ� */  
	        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
	        String status="2";
	        String id=null;            //�洢�û���id
	        String id_match=null;     //�洢��Ҫ����ı���ID
	        String user_match=null; //�洢�����ݿ���ȡ���Ķ�Ӧ�û��ı����ֶ�
	        String join=null;   //�ӱ����ֶ���ȡ�����ַ�����������
	        String leave=null;  //������ֶ���ȡ�����ַ�����������
	        String openid=request.getParameter("openid");
	        String uuid=request.getParameter("uuid");
	        String time=request.getParameter("time");
	        System.out.println("openid:"+openid);
	        System.out.println("uuid:"+uuid);	        
	        ResultSet userId=new Operate().find_userId(openid);  //����openid�ҳ��û�����Ϣ
	        while(userId.next()) {
             id=userId.getString("id");       //��ȡ�µ������id
             user_match=userId.getString("match");  //��ȡ�Ѵ��ڵı����ֶ�
	        }    
	        ResultSet matchId=new Operate().find_matchInformation(uuid);
	        while(matchId.next()) {
	             id_match=matchId.getString("id");       //��ȡ����id
		        } 
	         //����Ҫ����ı���ID���뵽ȡ�����ֶ���
	        String new_userMatch=new DataProcessing().increase_string(user_match, id_match);   //�����ַ����Ĵ�����ԭ�����ַ���������µ��ַ�����re_leave��ס
	       //��user���У���������id���뵽��Ӧ�û��ı����ֶ��С�
	        new Operate().update_userMatch(new_userMatch, openid);
	        //�ҳ���Ӧuuid�ı����ı����ֶ��µ��ַ��������õ����û�id���뵽match��ı����У���������ֶ��и��û�ȥ��
	        ResultSet user_join=new Operate().find_matchInformation(uuid);
	        while(user_join.next()) {
	        	join=user_join.getString("user_join");       //ȡ�����ݼ��У���������
	        	leave=user_join.getString("user_leave");    //ȡ�����ݼ��У���ٵ���
	        }	    
	        System.out.println("user_join:"+join);
	        System.out.println("user_leave:"+leave);
	        String rs_leave=new DataProcessing().increase_string(leave, id);   //�����ַ����Ĵ�����ԭ�����ַ���������µ��ַ�����re_leave��ס
	        String rs_join=new DataProcessing().reduce_string(join, id); //�����ַ����Ĵ�����ԭ�����ַ����м�ȥĳ���ַ�����re_join��ס
	        //��match���У����ⳡ�����µı����ֶ��ַ���������ֶ��ַ������滻ԭ���ı�������١�
	        new Operate().update_join(uuid,rs_join,rs_leave);
	        //ȡ���ⳡ�����е����б����˺�����˵�������ͷ�񣬽��д���󣬴��ظ�΢��С����
	        ResultSet register_information=new Operate().register_information(rs_join);
	        ResultSet leave_information=new Operate().leave_information(rs_leave);
	        String json_register=new DataProcessing().resultSetToJson(register_information);
	        String json_leave=new DataProcessing().resultSetToJson(leave_information);
	         //��ȡ����״̬�����û���ⳡ����벢�����䱨��״̬�����������ת̬��Ϊ����״̬
	        ResultSet match_status=new Operate().inquire_matchStatus(uuid, openid);
           if(match_status.next()) {                   //�ж����ݼ��Ƿ���ֵ,���������£�û�о����
          	 new Operate().update_matchStatus(uuid, openid,status);
           }else {
                   new Operate().add_matchStatus(uuid, openid, status,time);
           }
	        //��������json����ĺϲ�Ƕ�ף���ҪJSONObject������кϲ�Ƕ��
	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("json_register", json_register);
	        jsonObject.put("json_leave", json_leave);
	        String json = new Gson().toJson(jsonObject);//ת����json����
	        //�����ݴ���С����
            Writer out=response.getWriter();
            out.write(json);
            out.flush();
	        
	}
	
	//�������µ�uuidȡ����Ӧ��������Ϣ
	public void take_basisUuid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		 response.setContentType("text/html;charset=utf-8");          
	        /* ������Ӧͷ����ajax������� */  
	        response.setHeader("Access-Control-Allow-Origin", "*");  
	        /* �Ǻű�ʾ���е��������󶼿��Խ��ܣ� */  
	        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
	        String uuid=request.getParameter("uuid");
	        String openid=request.getParameter("openid");
	        String join=null;   //�ӱ����ֶ���ȡ�����ַ�����������
	        String leave=null;  //������ֶ���ȡ�����ַ�����������
	        String id=null;
	        double longitude=0;
	        double latitude=0;
	        System.out.println("uuid:"+uuid);
	        System.out.println("openid:"+openid);
             //ȡ����ѯ�������ݼ�
	        ResultSet user_id=new Operate().find_userId(openid);//����openid�û���id
	        while(user_id.next()) {
	        	id=user_id.getString("id");
	        }
	        ResultSet result=new Operate().find_matchInformation(uuid);  //����uuid�����Ӧ������Ϣ      
	        //ȡ�����ݼ��У���������ٵ���
	        while(result.next()) {
	        	join=result.getString("user_join");  
	        	leave=result.getString("user_leave"); 
	        }
	         //�жϸ��û�ID�Ƿ��Ѿ���������٣������򷵻�true������Ϊfalse
	        Boolean disable_join=new DataProcessing().judge_string(join, id);
	        Boolean disable_leave=new DataProcessing().judge_string(leave, id);
	        //ȡ���ⳡ�����е����б����˺�����˵�������ͷ�񣬽��д���󣬴��ظ�΢��С����
	        ResultSet register_information=new Operate().register_information(join);
	        ResultSet leave_information=new Operate().leave_information(leave);
	        String json_register=new DataProcessing().resultSetToJson(register_information);
	        String json_leave=new DataProcessing().resultSetToJson(leave_information);
	        //��Ϊ����.next���ˣ����ܵ���������ݼ��е�������ʧ�ˣ��������δ���λ�õ���Ҳ�ᵼ��һ�������û�С�����ֻ������ȥ���ݿ��ѯ��һ�顣
	        ResultSet rs=new Operate().find_matchStatus(uuid, openid); //����uuid��openidƴ�ӳ����б���״̬���±�    
	        while(rs.next()) {
	        	longitude=rs.getDouble("longitude");  //��Ϊ��γ����double���� ������Ҫ����ȡ���� ��ƴ�ӽ�ȥ
	        	latitude=rs.getDouble("latitude");
	        }
	        ResultSet re=new Operate().find_matchStatus(uuid, openid); 
	        String match_information=new DataProcessing().resultSetToJson(re);
	       //��������json����ĺϲ�Ƕ�ף���ҪJSONObject������кϲ�Ƕ��
	        JSONObject jsonObject = new JSONObject();	  
	        jsonObject.put("json_register", json_register);
	        jsonObject.put("json_leave", json_leave);
	        jsonObject.put("match_information", match_information);
	        jsonObject.put("disable_join", disable_join);
	        jsonObject.put("disable_leave", disable_leave);
	        jsonObject.put("longitude", longitude);
	        jsonObject.put("latitude", latitude);
	        String json = new Gson().toJson(jsonObject);   //��JSON����ת����json����
            //������õ����ݴ��ظ�С����
	        Writer out = response.getWriter();
            out.write(json);
	        out.flush();        
	}

	//���ı�������
	public void edit_match(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        
        response.setContentType("text/html;charset=utf-8");          
        /* ������Ӧͷ����ajax������� */  
        response.setHeader("Access-Control-Allow-Origin", "*");  
        /* �Ǻű�ʾ���е��������󶼿��Խ��ܣ� */  
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");  
       
        //��ȡ΢��С����get�Ĳ���ֵ����ӡ
        String theme=request.getParameter("match_theme");  
        String time=request.getParameter("match_time");
        String week=request.getParameter("match_week");
        System.out.println("���ı�������match_theme="+theme);
        System.out.println("���ı�������match_time="+time);
        System.out.println("���ı�������match_week="+week);
        String address = request.getParameter("match_address");
        String rule=request.getParameter("match_rule");
        String color=request.getParameter("match_color");
        String remarks=request.getParameter("match_remarks");
        String openid=request.getParameter("openid");
        String uuid=request.getParameter("uuid");
        String longitude1=request.getParameter("longitude");
        String latitude1=request.getParameter("latitude");
        double longitude=Double.valueOf(longitude1);
        double latitude=Double.valueOf(latitude1);
         int result=new Operate().edit_match(theme, time, week, address, longitude, latitude, rule, color, remarks, uuid);
         int result2=new Operate().edit_matchStatus(uuid, openid, time);
        //����ֵ��΢��С����
        Writer out = response.getWriter(); 
        out.write("���ĳɹ�");
        out.flush();  
        
        
    }

	//ɾ����������
	public void delete_match(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
        // TODO Auto-generated method stub
        
        response.setContentType("text/html;charset=utf-8");          
        /* ������Ӧͷ����ajax������� */  
        response.setHeader("Access-Control-Allow-Origin", "*");  
        /* �Ǻű�ʾ���е��������󶼿��Խ��ܣ� */  
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");  
       
        //��ȡ΢��С����get�Ĳ���ֵ
        String openid=request.getParameter("openid");
        String uuid=request.getParameter("uuid");
        System.out.println("ɾ����������uuid="+uuid);
        System.out.println("ɾ����������openid="+openid);
         Boolean delete=new Operate().delete_match(uuid,openid);
        
        //����ֵ��΢��С����
        Writer out = response.getWriter(); 
        out.write("���ĳɹ�");
        out.flush();  
        
        
    }
}
	
  
    
	

