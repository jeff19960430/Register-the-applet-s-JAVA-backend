package com.Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.metadata.IIOInvalidTreeException;

import com.mysql.jdbc.Statement;




public class Operate {
  
   //��ӱ�������
   public boolean add_match(Match ee) throws IOException{
       String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//���ݿ��˻���һ��Ϊroot
       String password = "123456";//���ݿ�����
       Connection conn=null;
       PreparedStatement pstmt=null;
       try{
           //������������
           Class.forName("com.mysql.jdbc.Driver");
           //������ݿ�����
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="insert into match_information (uuid,theme,time,week,address,rule,color,people,remarks,sponsor,sponsor_openid,user_join,user_leave,longitude,latitude)"
           		+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
           pstmt=conn.prepareStatement(sql);
           pstmt.setString(1, ee.Uuid);
           pstmt.setString(2, ee.Theme);
           pstmt.setString(3, ee.Time);
           pstmt.setString(4, ee.Week);
           pstmt.setString(5, ee.Address);
           pstmt.setString(6, ee.Rule);
           pstmt.setString(7, ee.Color);
           pstmt.setString(8, ee.People);
           pstmt.setString(9, ee.Remarks);
           pstmt.setString(10, ee.Sponsor);
           pstmt.setString(11,ee.Sponsor_openid);
           pstmt.setString(12, ee.User_join);
           pstmt.setString(13, ee.User_leave);
           pstmt.setDouble(14,ee.Longitude);
           pstmt.setDouble(15, ee.Latitude);
           pstmt.execute();
             }catch(ClassNotFoundException e){
                 e.printStackTrace();
             }catch(SQLException e){
                 e.printStackTrace();
             }     
     
       return true;

  }
   
   //�����û����еı����ֶ�����ѯ��ӦID�ı���
   public  ResultSet inquire_match(String user_match,String openid) throws ClassNotFoundException, SQLException {
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//���ݿ��˻���һ��Ϊroot
       String password = "123456";//���ݿ�����
       Connection conn=null;
       Statement stmt=null;
       Statement stmt1=null;
           //������������
           Class.forName("com.mysql.jdbc.Driver");
           //������ݿ�����
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql1="UPDATE  match_status  SET match_status='3' WHERE STR_TO_DATE(match_time,'%Y-%m-%d %H:%i:%s') < NOW()";
           String sql="SELECT * FROM (SELECT * FROM  match_information WHERE FIND_IN_SET(id,'"+user_match+"')) AS a LEFT JOIN (SELECT * FROM match_status WHERE openid='"+openid+"') AS b ON a.uuid=b.status_uuid order by a.id desc";    
           stmt=(Statement) conn.createStatement();
           stmt.executeUpdate(sql1);
           ResultSet rs=stmt.executeQuery(sql);
           return  rs;
           
           
   }
   
   //�����û���Ϣ����
   public boolean add_user(User aa) throws IOException{
       String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//���ݿ��˻���һ��Ϊroot
       String password = "123456";//���ݿ�����
       Connection conn=null;
       PreparedStatement pstmt=null;
       try{
           //������������
           Class.forName("com.mysql.jdbc.Driver");
           //������ݿ�����
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="insert into user(openid,user_name,user_url)values(?,?,?)";
           pstmt=conn.prepareStatement(sql);
           pstmt.setString(1, aa.Openid);
           pstmt.setString(2, aa.User_name);
           pstmt.setString(3, aa.User_url);
           pstmt.execute();
             }catch(ClassNotFoundException e){
                 e.printStackTrace();
             }catch(SQLException e){
                 e.printStackTrace();
             }  
 
      
       return true;
  }
   
   //����openid��ȡ�����û������ƣ��Դ��жϻ�ȡ����Openid�Ƿ������ݿ��Ѿ�����
   public  ResultSet inquire_openid(String openid) throws ClassNotFoundException, SQLException {
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//���ݿ��˻���һ��Ϊroot
       String password = "123456";//���ݿ�����
       Connection conn=null;
       Statement stmt=null;
       
           //������������
           Class.forName("com.mysql.jdbc.Driver");
           //������ݿ�����
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="select user_name from user where openid = '"+openid+"'";
           stmt=(Statement) conn.createStatement();
           ResultSet rs=stmt.executeQuery(sql);
           return  rs;    

           
   }
   
   //���¸�openid���û����ֺ�ͷ��
   public  int update_user(User bb) throws ClassNotFoundException, SQLException {
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//���ݿ��˻���һ��Ϊroot
       String password = "123456";//���ݿ�����
       Connection conn=null;
       Statement stmt=null;    
           //������������
           Class.forName("com.mysql.jdbc.Driver");
           //������ݿ�����
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="update user set user_name='"+bb.User_name+"',user_url='"+bb.User_url+"'  where openid='"+bb.Openid+"'";
           stmt=(Statement) conn.createStatement();
           int result=stmt.executeUpdate(sql);
           return  result;
           
   }
   
   //����openid�ҵ��û�����Ϣ
   public  ResultSet find_userId(String openid) throws ClassNotFoundException, SQLException {
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//���ݿ��˻���һ��Ϊroot
       String password = "123456";//���ݿ�����
       Connection conn=null;
       Statement stmt=null;
       
           //������������
           Class.forName("com.mysql.jdbc.Driver");
           //������ݿ�����
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="select * from user where openid='"+openid+"'";
           stmt=(Statement) conn.createStatement();
           ResultSet userId=stmt.executeQuery(sql);
           return  userId;
      
   }
   
    //����uuidȡ���������е�������Ϣ��
   public  ResultSet find_matchInformation(String uuid) throws ClassNotFoundException, SQLException {
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//���ݿ��˻���һ��Ϊroot
       String password = "123456";//���ݿ�����
       Connection conn=null;
       Statement stmt=null;
           //������������
           Class.forName("com.mysql.jdbc.Driver");
           //������ݿ�����
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="select * from match_information where uuid='"+uuid+"'";
           stmt=(Statement) conn.createStatement();
           ResultSet allUser=stmt.executeQuery(sql);
           return  allUser;
      
   }
   //����uuid��openid��������ͱ���ת̬��ƴ��������
  public  ResultSet find_matchStatus(String uuid,String openid) throws ClassNotFoundException, SQLException {
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
      String username = "root";//���ݿ��˻���һ��Ϊroot
      String password = "123456";//���ݿ�����
      Connection conn=null;
      Statement stmt=null;
          //������������
          Class.forName("com.mysql.jdbc.Driver");
          //������ݿ�����
          conn=(Connection)DriverManager.getConnection(url,username,password);
          String sql="SELECT * FROM (select * from match_information where uuid='"+uuid+"') AS a LEFT JOIN (SELECT * FROM match_status WHERE openid='"+openid+"') AS b ON a.uuid=b.status_uuid";
          stmt=(Statement) conn.createStatement();
          ResultSet allUser=stmt.executeQuery(sql);
          return  allUser;
     
  }
   
      //���ⳡ���������ֶκ�����ֶ����У��µõ����ַ����滻ԭ�����ַ���
    public  int update_join(String uuid,String rs_join,String rs_leave) throws ClassNotFoundException, SQLException { //��������������һ���Ǳ�����openid,�ҵ��ⳡ������һ�����µı����ֶ��ַ���
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//���ݿ��˻���һ��Ϊroot
       String password = "123456";//���ݿ�����
       Connection conn=null;
       Statement stmt=null;    
           //������������
           Class.forName("com.mysql.jdbc.Driver");
           //������ݿ�����
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="update match_information set user_join='"+rs_join+"',user_leave='"+rs_leave+"' where uuid='"+uuid+"'";
           stmt=(Statement) conn.createStatement();
           int result=stmt.executeUpdate(sql);
           return  result;
           
   }
    
    //��match�����У�ȡ�������˵�������ͷ��
    public ResultSet register_information (String user_join) throws ClassNotFoundException, SQLException { //��������������һ���Ǳ�����openid,�ҵ��ⳡ������һ�����µı����ֶ��ַ���
		   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
	       String username = "root";//���ݿ��˻���һ��Ϊroot
	       String password = "123456";//���ݿ�����
	       Connection conn=null;
	       Statement stmt=null;
	           //������������
	           Class.forName("com.mysql.jdbc.Driver");
	           //������ݿ�����
	           conn=(Connection)DriverManager.getConnection(url,username,password);
	           String sql="select user_name,user_url from user where FIND_IN_SET (id,'"+user_join+"')";
	           stmt=(Statement) conn.createStatement();
	           ResultSet register_imformation=stmt.executeQuery(sql);      
	           return register_imformation;
	           
	   }

    
    //��match�����У�ȡ������˵�������ͷ��
    public ResultSet leave_information (String user_leave) throws ClassNotFoundException, SQLException { //��������������һ���Ǳ�����openid,�ҵ��ⳡ������һ�����µı����ֶ��ַ���
		   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
	       String username = "root";//���ݿ��˻���һ��Ϊroot
	       String password = "123456";//���ݿ�����
	       Connection conn=null;
	       Statement stmt=null;
	           //������������
	           Class.forName("com.mysql.jdbc.Driver");
	           //������ݿ�����
	           conn=(Connection)DriverManager.getConnection(url,username,password);
	           String sql="select user_name,user_url from user where FIND_IN_SET (id,'"+user_leave+"')";
	           stmt=(Statement) conn.createStatement();
	           ResultSet leave_imformation=stmt.executeQuery(sql);      
	           return leave_imformation;
	           
	   }

    //���û����еı����ֶν��и���
    public  int update_userMatch(String new_userMatch,String openid) throws ClassNotFoundException, SQLException { //��������������һ�����µı����ֶ�,һ�����û���id
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
     String username = "root";//���ݿ��˻���һ��Ϊroot
     String password = "123456";//���ݿ�����
     Connection conn=null;
     Statement stmt=null;    
         //������������
         Class.forName("com.mysql.jdbc.Driver");
         //������ݿ�����
         conn=(Connection)DriverManager.getConnection(url,username,password);
         String sql="update user set `match`='"+new_userMatch+"' where openid ='"+openid+"' ";
         stmt=(Statement) conn.createStatement();
         int result=stmt.executeUpdate(sql);
         return  result;
         
 }

    //��match_status�в������״̬�ͱ���ʱ��
    public boolean add_matchStatus(String uuid,String openid,String status,String time) throws IOException{
        String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
        String username = "root";//���ݿ��˻���һ��Ϊroot
        String password = "123456";//���ݿ�����
        Connection conn=null;
        PreparedStatement pstmt=null;
        try{
            //������������
            Class.forName("com.mysql.jdbc.Driver");
            //������ݿ�����
            conn=(Connection)DriverManager.getConnection(url,username,password);
            String sql="insert into match_status(status_uuid,openid,match_status,match_time)values(?,?,?,?)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, uuid);
            pstmt.setString(2, openid);
            pstmt.setString(3, status);
            pstmt.setString(4, time);
            pstmt.execute();
              }catch(ClassNotFoundException e){
                  e.printStackTrace();
              }catch(SQLException e){
                  e.printStackTrace();
              }  
  
       
        return true;
   }

    //��match_status�и��±���״̬�ͱ���ʱ��
    public int edit_matchStatus(String uuid,String openid,String time) throws ClassNotFoundException, SQLException{
        String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
        String username = "root";//���ݿ��˻���һ��Ϊroot
        String password = "123456";//���ݿ�����
        Connection conn=null;
        Statement stmt=null;
     
            //������������
            Class.forName("com.mysql.jdbc.Driver");
            //������ݿ�����
            conn=(Connection)DriverManager.getConnection(url,username,password);
            String sql="update match_status set match_time='"+time+"' where status_uuid='"+uuid+"' and openid='"+openid+"'";
            stmt=(Statement) conn.createStatement();
            int result=stmt.executeUpdate(sql);
            return  result;
   }
  
   //����match_status�б�����Ӧ�û���״̬
    public  int update_matchStatus(String uuid,String openid,String status) throws ClassNotFoundException, SQLException {
 	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
        String username = "root";//���ݿ��˻���һ��Ϊroot
        String password = "123456";//���ݿ�����
        Connection conn=null;
        Statement stmt=null;    
            //������������
            Class.forName("com.mysql.jdbc.Driver");
            //������ݿ�����
            conn=(Connection)DriverManager.getConnection(url,username,password);
            String sql="update match_status set match_status='"+status+"' where openid='"+openid+"'and status_uuid='"+uuid+"'";
            stmt=(Statement) conn.createStatement();
            int result=stmt.executeUpdate(sql);
            return  result;
            
    }

    //��match_status�� ȡ����Ӧ״̬
    public ResultSet inquire_matchStatus (String uuid,String openid) throws ClassNotFoundException, SQLException { //��������������һ���Ǳ�����openid,�ҵ��ⳡ������һ�����µı����ֶ��ַ���
		   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
	       String username = "root";//���ݿ��˻���һ��Ϊroot
	       String password = "123456";//���ݿ�����
	       Connection conn=null;
	       Statement stmt=null;
	           //������������
	           Class.forName("com.mysql.jdbc.Driver");
	           //������ݿ�����
	           conn=(Connection)DriverManager.getConnection(url,username,password);
	           String sql="select match_status from match_status where status_uuid='"+uuid+"' and openid='"+openid+"'";
	           stmt=(Statement) conn.createStatement();
	           ResultSet leave_imformation=stmt.executeQuery(sql);      
	           return leave_imformation;
	           
	   }
     
  //�޸�match�ж�Ӧuuid�ı�����Ϣ
    public  int edit_match(String theme,String time,String week,String address,Double longitude,Double latitude,String rule,String color,String remarks,String uuid) throws ClassNotFoundException, SQLException {
 	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
        String username = "root";//���ݿ��˻���һ��Ϊroot
        String password = "123456";//���ݿ�����
        Connection conn=null;
        Statement stmt=null;    
            //������������
            Class.forName("com.mysql.jdbc.Driver");
            //������ݿ�����
            conn=(Connection)DriverManager.getConnection(url,username,password);
            String sql="update match_information set theme='"+theme+"',time='"+time+"',week='"+week+"',address='"+address+"',"
            		+ "longitude='"+longitude+"',latitude='"+latitude+"',rule='"+rule+"',color='"+color+"',"
            				+ "remarks='"+remarks+"' where uuid='"+uuid+"'";
            stmt=(Statement) conn.createStatement();
            int result=stmt.executeUpdate(sql);
            return  result;
            
    }
 
    //��match_information���match_status����ɾ����Ӧ����
    public Boolean delete_match(String uuid,String openid) throws ClassNotFoundException, SQLException { //��������������һ���Ǳ�����openid,�ҵ��ⳡ������һ�����µı����ֶ��ַ���
		   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
	       String username = "root";//���ݿ��˻���һ��Ϊroot
	       String password = "123456";//���ݿ�����
	       Connection conn=null;
	       Statement stmt=null;
	           //������������
	           Class.forName("com.mysql.jdbc.Driver");
	           //������ݿ�����
	           conn=(Connection)DriverManager.getConnection(url,username,password);
	           String sql_information="delete from match_information where uuid='"+uuid+"'";
	           String sql_status="delete from match_status where status_uuid='"+uuid+"' and openid='"+openid+"'";
	           stmt=(Statement) conn.createStatement();
	           int update1=stmt.executeUpdate(sql_information);
	           int update2=stmt.executeUpdate(sql_status);
	           return true;
	           
	   }
    
}


