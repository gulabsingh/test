package wsc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sun.misc.BASE64Decoder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sforce.async.SObject;
import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.Error;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.UpsertResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Attachment;
import com.sforce.soap.enterprise.sobject.Case;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.soap.enterprise.sobject.RecordType;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/**
 * Servlet implementation class AddCase
 */
@WebServlet("/AddCase")
public class AddCase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//String USERNAME = "support@ubitechsolutions.com";
    //String PASSWORD = "ubipass@2016TCz697D2krvJrudnUjxwnU5Jh";
    
	static  String USERNAME ="support_sf@singpost.com.singpost" ;
	static  String PASSWORD ="Hellogulab20152YFXl78H4wIpONiWsBajkuG9";
	static EnterpriseConnection connection;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCase() {
        super();
        
        
    }
    
    public Calendar convertStringToDate(String dateStr){
    	
    	//String dateStr = "04/11/2010"; 

		SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yyyy"); 
		Date dateObj = null;
		try {
			dateObj = curFormater.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateObj);
		
		//result = "- Year:"+calendar.get(calendar.YEAR)+", Month:"+calendar.get(calendar.MONTH)+" -";
    	return calendar;
    }
    
    public Boolean convertStringToBoolean(String strval){
    	
    	if(strval.isEmpty()){
    		return false;
    	}else if(strval.equalsIgnoreCase("0")){
    		return false;
    	}
    	
    	return true;
    }
    
    public Double convertStringToDouble(String strval){
    	
    	if(strval.isEmpty()){
    		return null;
    	}
    	try{
    		return Double.parseDouble(strval);
    		
    	}catch(Exception e){
    		return null;
    	}
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ResultClass rc = new ResultClass();
		String result="";
	    String id ="";
	    
	    /*
	    try{
	    ServletContext cntxt = this.getServletContext();
        String fName = "/WEB-INF/configuration.xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream ins = cntxt.getResourceAsStream(fName);
        Document document = builder.parse(ins);
        //String id = document.getElementsByTagName("data").item(0).getAttributes().getNamedItem("id").getNodeValue();
        NodeList nodeList = document.getElementsByTagName("employee");
        Node node = nodeList.item(0);
        Element eElement = (Element) node;
        USERNAME =eElement.getElementsByTagName("username").item(0).getTextContent();
        PASSWORD =eElement.getElementsByTagName("password").item(0).getTextContent();
	    
	    }catch(Exception e){
	    	result +="Exception throws:"+e.getMessage();
	    }*/
        
		
	    
	    try {
	    	ConnectorConfig config = new ConnectorConfig();
		    config.setUsername(USERNAME);
		    config.setPassword(PASSWORD);
		    config.setTraceMessage(true);
	      connection = Connector.newConnection(config);
	      
	      // display some current settings
	      //System.out.println("Auth EndPoint: "+config.getAuthEndpoint());
	      //System.out.println("Service EndPoint: "+config.getServiceEndpoint());
	      System.out.println("Username: "+config.getUsername());
	      System.out.println("SessionId: "+config.getSessionId());
	     
	      
	    } catch (ConnectionException e1) {
	        //e1.printStackTrace();
	    	result = e1.getMessage();
	    } 
	    
	   
	    
	    
		//Gson gs = new Gson();  
		//CaseClass cc = gs.fromJson(request.getParameter("json"), CaseClass.class);
	    InputStreamReader isr = new InputStreamReader(request.getInputStream());
		BufferedReader in = new BufferedReader(isr);
		String line = in.readLine();
    	Gson gs = new Gson();  
		CaseClass cc = gs.fromJson(line, CaseClass.class);
		
		String CaseRecordTypeId= "";
		String accountid= "";
		String contactid= "";
		String mCode = cc.getEZY_Merchant_Code__c();
		String email = cc.getEmail();
		
		try{
			
			 //////////////  GETTING RECORDTYPE FOR CASE//////////////////// 
			String queryString1 ="Select id from RecordType where sObjectType = 'Case' and developerName ='ezyCommerce'";
	    	QueryResult queryResults1 = connection.query(queryString1);
	    	if(queryResults1.getSize() > 0){
	    		com.sforce.soap.enterprise.sobject.SObject[] records = queryResults1.getRecords();
	            for (int i = 0; i < records.length; ++i) {
	            	RecordType con = (RecordType) records[i];
	            	CaseRecordTypeId= con.getId();
	            	 //System.out.println("CaseRecordTypeId: "+CaseRecordTypeId);
	            }
	    	}
	    	
	    	////////////// GETTING ACCOUNT ID ///////////////////////////////////
	    	 String queryString11 ="Select id from Account where EZY_Merchant_Code__c = '"+mCode+"'";
		    	QueryResult queryResults11 = connection.query(queryString11);
		    	if(queryResults11.getSize() > 0){
		    		com.sforce.soap.enterprise.sobject.SObject[] record = queryResults11.getRecords();
		            for (int i = 0; i < record.length; ++i) {
		            	Account con = (Account) record[i];
		            	accountid= con.getId();
		            	//System.out.println("AccRecordTypeId: "+AccRecordTypeId);
		            }
		           
		    	}else{
		    		result+=" Provided wrong merchant code ";
		    	}
		    
		    /////////////  GETTING CONTACT ID /////////////////////////////////////
		    	String queryString12 ="Select id from Contact where Email = '"+email+"' and AccountId='"+accountid+"'";
		    	QueryResult queryResults12 = connection.query(queryString12);
		    	if(queryResults12.getSize() > 0){
		    		com.sforce.soap.enterprise.sobject.SObject[] records = queryResults12.getRecords();
		            for (int i = 0; i < records.length; ++i) {
		            	Contact con = (Contact) records[i];
		            	contactid= con.getId();
		            }
		    	}else{
		    		result+=" Provided wrong email id ";
		    	}
		    	
		
		    if((!contactid.isEmpty()) && (!accountid.isEmpty()))
		    {
			
			//////////////  CASE OBJECT CREATION FOR INSERTION  //////////////////////
			  Case c = new Case();
			  c.setRecordTypeId(CaseRecordTypeId);
			  
			  c.setSubject(cc.getSubject());
			  c.setAccountId(accountid);
			  c.setContactId(contactid);
			 
			c.setDescription(cc.getDescription());
			c.setStatus("Open");
			c.setOrigin("Web");
			c.setEZY_CATEGORY__c(cc.getEZY_CATEGORY__c());
			c.setEZY_DETAILS__c(cc.getEZY_DETAILS__c());
			c.setEZY_SUB_CATEGORY__c(cc.getEZY_SUB_CATEGORY__c());
			
		
			 Case[] records = {c};
			 
		  /////////////////  CREATING CASE  ////////////////////////////////
			 SaveResult[] saveResults = connection.create(records);
	      
	     
	      
	      // check the returned results for any errors
		      for (int i=0; i< saveResults.length; i++) {
		        if (saveResults[i].isSuccess()) {
		          //System.out.println(i+". Successfully created record - Id: " + saveResults[i].getId());
		        	result +="Successfully Created Case ";
		        	id=saveResults[i].getId();
		        	String caseid = saveResults[i].getId();
		        	
		        	rc.setIsSuccess(true);
		        	
		        	
		        	
		  //////////////  CREATING CASE FIRST ATTACHMENT 
		             if(!cc.getAttach_name().equalsIgnoreCase("")){
			            BASE64Decoder decoder = new BASE64Decoder();
			            byte[] imageByte=null;
			            imageByte = decoder.decodeBuffer(cc.getAttach_body());			             
			        	Attachment a =new Attachment();
			        	a.setContentType("Attachment");
			        	a.setBody(imageByte);
			        	a.setParentId(caseid);
			        	a.setName(cc.getAttach_name());
			        	
			        	Attachment[] record ={a};
			        	SaveResult[] saveResult = connection.create(record);
			        	result +=", Successfully uploaded first attachment" ;
			        	id+=","+saveResult[0].getId();
		             }
		             
		  //////////////  CREATING CASE SECOND ATTACHMENT    
		             if(!cc.getAttach_name1().equalsIgnoreCase("")){
				            BASE64Decoder decoder = new BASE64Decoder();
				            byte[] imageByte=null;
				            imageByte = decoder.decodeBuffer(cc.getAttach_body1());			             
				        	Attachment a =new Attachment();
				        	a.setContentType("Attachment");
				        	a.setBody(imageByte);
				        	a.setParentId(caseid);
				        	a.setName(cc.getAttach_name1());
				        	
				        	Attachment[] record ={a};
				        	SaveResult[] saveResult = connection.create(record);
				        	
				        	result +=", Successfully uploaded second attachment" ;
				        	id+=","+saveResult[0].getId();
			             }
		             
		 //////////////  CREATING CASE THIRD ATTACHMENT 
		             if(!cc.getAttach_name2().equalsIgnoreCase("")){
				            BASE64Decoder decoder = new BASE64Decoder();
				            byte[] imageByte=null;
				            imageByte = decoder.decodeBuffer(cc.getAttach_body2());			             
				        	Attachment a =new Attachment();
				        	a.setContentType("Attachment");
				        	a.setBody(imageByte);
				        	a.setParentId(caseid);
				        	a.setName(cc.getAttach_name2());
				        	
				        	Attachment[] record ={a};
				        	SaveResult[] saveResult = connection.create(record);
				        	
				        	result +=", Successfully uploaded third attachment" ;
				        	id+=","+saveResult[0].getId();
			             }
		             rc.setId(id);
		        } else {
		        	rc.setIsSuccess(false);
		          Error[] errors = saveResults[i].getErrors();
		          for (int j=0; j< errors.length; j++) {
		        	  result +="ERROR "+(j+1)+": " + errors[j].getMessage()+", ";
		        	 	
		          }
		        }    
		      
	  		} /// end of for loop
		      
		    }else{/// end of if condition
		    	rc.setIsSuccess(false);
		    }
		      
		}catch(Exception e){
			result+="Exception generate in code: "+e.getMessage();			
		}
    
		rc.setMessage(result);
		PrintWriter out= response.getWriter();  
        
		 Gson gson = new Gson();
		    String json =gson.toJson(rc);
		    try {
		        /* TODO output your response here.*/
		        out.println(json);
		    } finally {
		        out.close();
		    }
	}
	
	
	

}
