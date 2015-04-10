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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.DeleteResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.Error;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.soap.enterprise.sobject.RecordType;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/**
 * Servlet implementation class AddUpdateMerchant
 */
@WebServlet("/AddUpdateMerchant")
public class AddUpdateMerchant extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//String USERNAME = "support@ubitechsolutions.com";
    //String PASSWORD = "ubipass@2016TCz697D2krvJrudnUjxwnU5Jh";
    
	static  String USERNAME ="support_sf@singpost.com.singpost" ;
	static  String PASSWORD ="Hellogulab20152YFXl78H4wIpONiWsBajkuG9";
	static EnterpriseConnection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUpdateMerchant() {
        super();
        // TODO Auto-generated constructor stub
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
	      //System.out.println("Username: "+config.getUsername());
	     // System.out.println("SessionId: "+config.getSessionId());
	     
	      
	    } catch (ConnectionException e1) {
	        result +="Throws Exception: "+e1.getMessage();
	    } 
	   
	    
	    try {
	    	InputStreamReader isr = new InputStreamReader(request.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			String line = in.readLine();
	    	Gson gs = new Gson();  
			CaseClass cc = gs.fromJson(line, CaseClass.class);
			
			
		    String accountid="";
		    String contactid="";
	    	String AccRecordTypeId= "";
	    	
	    	
	    	String ConRecordTypeId="";
	    	
	    	//////////////// FETCHING CONTACT RECORD TYPE //////////////////////////
	    	
    		String queryString ="Select id from RecordType where sObjectType = 'Contact' and developerName ='ezyCommerce'";
	    	QueryResult queryResults = connection.query(queryString);
	    	if(queryResults.getSize() > 0){
	    		com.sforce.soap.enterprise.sobject.SObject[] records = queryResults.getRecords();
	            for (int i = 0; i < records.length; ++i) {
	            	RecordType con = (RecordType) records[i];
	            	ConRecordTypeId= con.getId();
	            	//System.out.println("ConRecordTypeId: "+ConRecordTypeId);
	            }
	    	}
	    	
	    	
	    	String mCode= cc.getEZY_Merchant_Code__c();
	    	String type= cc.getActionType();
	    	String email = cc.getEmail();
	    	//System.out.println("type: "+type);
	    	if(type.equalsIgnoreCase("SIGNUP"))
	    	{
	    		///////////// FETCHING ACCOUNT RECORD TYPE /////////////////////
	    		String queryString1 ="Select id from RecordType where sObjectType = 'Account' and developerName ='ezyCommerce'";
		    	QueryResult queryResults1 = connection.query(queryString1);
		    	if(queryResults1.getSize() > 0){
		    		com.sforce.soap.enterprise.sobject.SObject[] records = queryResults1.getRecords();
		            for (int i = 0; i < records.length; ++i) {
		            	RecordType con = (RecordType) records[i];
		            	AccRecordTypeId= con.getId();
		            	//System.out.println("AccRecordTypeId: "+AccRecordTypeId);
		            }
		    	}
		    	
		    	Account at = new Account();
		    	
		    	at.setRecordTypeId(AccRecordTypeId);
		    	at.setEZY_Merchant_Code__c(mCode);
		    	at.setEZY_Existing_Customer__c(convertStringToBoolean(cc.getEZY_Existing_Customer__c())); //boolean
		    	at.setEZY_Merchant_Category__c(cc.getEZY_Merchant_Category__c());
		    	at.setNumberOfEmployees(convertStringToInteger(cc.getNumberOfEmployees()));
		    	at.setEZY_Account_Number__c(cc.getEZY_Account_Number__c());
		    	at.setEZY_Temporary_Employee__c(convertStringToDouble(cc.getEZY_Temporary_Employee__c()));
		    	at.setName(cc.getName());
		    	at.setEZY_Is_Active__c(convertStringToBoolean(cc.getEZY_Is_Active__c())); //boolean
		    	at.setEZY_Company_Registration_Type__c(cc.getEZY_Company_Registration_Type__c());
		    	
		    	if(!cc.getEZY_Active_Date__c().equalsIgnoreCase("")){
		    		at.setEZY_Active_Date__c(convertStringToDate(cc.getEZY_Active_Date__c())); //caleandar
		    	}
		    	at.setEZY_Company_Registration_Number__c(cc.getEZY_Company_Registration_Number__c());
		    	if(!cc.getEZY_Inactive_Date__c().equalsIgnoreCase("")){
		    		at.setEZY_Inactive_Date__c(convertStringToDate(cc.getEZY_Inactive_Date__c())); //calendar
		    	}
		    	at.setEZY_Company_Founded_Year__c(convertStringToDouble(cc.getEZY_Company_Founded_Year__c())); //double
		    	at.setEZY_Membership_Plan__c(cc.getEZY_Membership_Plan__c());
		    	at.setEZY_Credit_Terms_in_Days__c(convertStringToDouble(cc.getEZY_Credit_Terms_in_Days__c()));// double
		    	at.setEZY_Security_Question__c(cc.getEZY_Security_Question__c());
		    	at.setEZY_Security_Answer__c(cc.getEZY_Security_Answer__c());
		    	at.setEZY_Locale__c(cc.getEZY_Locale__c());
		    	at.setEZY_Domestic_Orders_Percentage__c(cc.getEZY_Domestic_Orders_Percentage__c());
		    	at.setEZY_Average_Units_Order__c(cc.getEZY_Average_Units_Order__c());
		    	at.setEZY_No_of_Unique_SKU_s__c(cc.getEZY_No_of_Unique_SKU_s__c());
		    	at.setEZY_No_Of_Online_Channels__c(cc.getEZY_No_Of_Online_Channels__c());
		    	at.setEZY_Sales_Chanel_Link__c(cc.getEZY_Sales_Chanel_Link__c());
		    	at.setEZY_No_Of_Overseas_Selling__c(cc.getEZY_No_Of_Overseas_Selling__c());
		    	at.setEZY_Orders_Month__c(cc.getEZY_Orders_Month__c()); //double
		    	at.setEZY_Promotional_Code__c(cc.getEZY_Promotional_Code__c());
		    	at.setEZY_Plan_Code__c(cc.getEZY_Plan_Code__c());
		    	at.setEZY_Subsidy_Granted__c(convertStringToBoolean(cc.getEZY_Subsidy_Granted__c())); //boolean
		    	if(!cc.getEZY_Created_Date__c().equalsIgnoreCase("")){
		    	at.setEZY_Created_Date__c(convertStringToDate(cc.getEZY_Created_Date__c())); //calendar
		    	}
		    	if(!cc.getEZY_Subsidy_End_Date__c().equalsIgnoreCase("")){
		    	at.setEZY_Subsidy_End_Date__c(convertStringToDate(cc.getEZY_Subsidy_End_Date__c())); //calendar
		    	}
		    	if(!cc.getEZY_Modified_Date__c().equalsIgnoreCase("")){
		    	at.setEZY_Modified_Date__c(convertStringToDate(cc.getEZY_Modified_Date__c())); //calendar
		    	}
		    	if(!cc.getEZY_Subsidy_Start_Date__c().equalsIgnoreCase("")){
		    	at.setEZY_Subsidy_Start_Date__c(convertStringToDate(cc.getEZY_Subsidy_Start_Date__c()));//calendar
		    	}
		    	at.setEZY_Country_Code__c(cc.getEZY_Country_Code__c());
		    	
		    	at.setBillingStreet(cc.getAddress_1__c());
		    	at.setBillingCity(cc.getCity_Name__c());
		    	at.setBillingState(cc.getState__c());
		    	at.setBillingCountry(cc.getCountry__c());
		    	at.setBillingPostalCode(cc.getPostal_Code__c());
		    	at.setEZY_Revenue__c(cc.getEZY_Revenue__c());
		    	
		    	at.setAccount_Phone__c(cc.getAccount_Phone__c());
		    	at.setWebsite(cc.getWebsite());
		    	at.setEZY_Apply_SME__c(convertStringToBoolean(cc.getEZY_Apply_SME__c())); //boolean
		    	if(!cc.getAccount_Creation_Date__c().equalsIgnoreCase("")){
		    	at.setAccount_Creation_Date__c(convertStringToDate(cc.getAccount_Creation_Date__c())); //calendar
		    	}
		    	at.setReclassification_Indicator__c(cc.getReclassification_Indicator__c());
		    	
		    	
		    	SaveResult[] saveResult1=null;
		    	if(!mCode.equalsIgnoreCase("")){
			    	String queryString11 ="Select id from Account where EZY_Merchant_Code__c = '"+mCode+"'";
			    	QueryResult queryResults11 = connection.query(queryString11);
			    	if(queryResults11.getSize() > 0){
			    		com.sforce.soap.enterprise.sobject.SObject[] records = queryResults11.getRecords();
			            for (int i = 0; i < records.length; ++i) {
			            	Account con = (Account) records[i];
			            	at.setId(con.getId());
			            }
			            Account[] recordat ={at};
			    		saveResult1 = connection.update(recordat);
			    	}else{
			    		Account[] recordat ={at};
			    		saveResult1 = connection.create(recordat);
			    	}
		    	}else{
		    		Account[] recordat ={at};
		    		saveResult1 = connection.create(recordat);
		    	}
		    	
		    	//System.out.println("Account saveResult: "+saveResult1[0].toString());
	        	if(saveResult1[0].isSuccess()){
	        		rc.setIsSuccess(true);
	        		accountid = saveResult1[0].getId();
	        		result +="Successfully saved account record";
	        		id=saveResult1[0].getId();
	        		rc.setId(id);
	        		
	        		//System.out.println(result);
	        		if(type.equalsIgnoreCase("SIGNUP"))
	        		{
		        		
		        		Contact ct = new Contact();
		        		ct.setEmail(email);
		        		ct.setAccountId(accountid);
		        		//ct.setFirstName(cc.getFirstName());
		        		ct.setLastName(cc.getFirstName()+" "+cc.getLastName());
		        		ct.setPhone(cc.getPhone());
		        		
		        		ct.setEZY_Role_1__c(true);
		        		ct.setEZY_Role_2__c(true);
		        		ct.setEZY_Role_3__c(true);
		        		ct.setMailingStreet(cc.getAddress_1__c());
		        		ct.setMailingCity(cc.getCity_Name__c());
		        		ct.setMailingState(cc.getState__c());
		        		ct.setMailingCountry(cc.getCountry__c());
		        		ct.setMailingPostalCode(cc.getPostal_Code__c());
		        		
		        		ct.setMobilePhone(cc.getMobilePhone());
		        		ct.setRecordTypeId(ConRecordTypeId);
		        		ct.setEzyCommerce_Contact_Role__c("Primary User");
		        		
		        		SaveResult[] saveResult2=null;
		        			String queryString12 ="Select id from Contact where Email = '"+email+"' and AccountId='"+accountid+"'";
		    		    	QueryResult queryResults12 = connection.query(queryString12);
		    		    	if(queryResults12.getSize() > 0){
		    		    		com.sforce.soap.enterprise.sobject.SObject[] records = queryResults12.getRecords();
		    		            for (int i = 0; i < records.length; ++i) {
		    		            	Contact con = (Contact) records[i];
		    		            	ct.setId(con.getId());
		    		            }
		    		            Contact[] recordct ={ct};
		    		    		saveResult2 = connection.update(recordct);
		    		    	}else{		    		    		
		    		    		Contact[] recordct ={ct};
		    		    		saveResult2 = connection.create(recordct);
		    		    	}
		    		  
		    		    	//System.out.println("Contact saveResult: "+saveResult2[0].toString());
		    		    	
		        		if(saveResult2[0].isSuccess()){
		            		contactid = saveResult2[0].getId();
		            		result +="Successfully saved contact record";
		            		//System.out.println(result);
		            		id +=", "+contactid;
		            		rc.setId(id);
		            	}else{		            		
		            		Error[] sr =saveResult2[0].getErrors();
		            		for (int s = 0; s < sr.length; ++s) {
				            	result+=sr[0].getMessage();
				            }
		            	}
	        		}
	        	}else{
	        		rc.setIsSuccess(false);
	        		Error[] sr =saveResult1[0].getErrors();
	        		result+="Account errors:";
            		for (int s = 0; s < sr.length; ++s) {
		            	result+=sr[0].getMessage();
		            }
	        	}
	    	}else{
	    		
	    		if(type.equalsIgnoreCase("CONTACT"))
	    		{
		    		
	        		Contact ct = new Contact();
	        		ct.setEmail(email);
	        		ct.setAccountId(accountid);
	        		//ct.setFirstName(cc.getFirstName());
	        		ct.setLastName(cc.getLastName());
	        		ct.setPhone(cc.getPhone());
	        		
	        		ct.setEZY_Role_1__c(convertStringToBoolean(cc.getEZY_Role_1__c()));
	        		ct.setEZY_Role_2__c(convertStringToBoolean(cc.getEZY_Role_2__c()));
	        		ct.setEZY_Role_3__c(convertStringToBoolean(cc.getEZY_Role_3__c()));
	        		ct.setMailingStreet(cc.getAddress_1__c());
	        		ct.setMailingCity(cc.getCity_Name__c());
	        		ct.setMailingState(cc.getState__c());
	        		ct.setMailingCountry(cc.getCountry__c());
	        		ct.setMailingPostalCode(cc.getPostal_Code__c());
	        		
	        		ct.setMobilePhone(cc.getMobilePhone());
	        		ct.setRecordTypeId(ConRecordTypeId);
	        		ct.setEzyCommerce_Contact_Role__c("Sub Users");
	        		//UpsertResult[] saveResult2 = connection.upsert("Id", recordct);
	        		
	        		SaveResult[] saveResult2=null;
	        		//if(!email.equalsIgnoreCase("")){
	        		String queryString11 ="Select id from Account where EZY_Merchant_Code__c = '"+mCode+"'";
			    	QueryResult queryResults11 = connection.query(queryString11);
			    	if(queryResults11.getSize() > 0){
			    		com.sforce.soap.enterprise.sobject.SObject[] records = queryResults11.getRecords();
			            for (int i = 0; i < records.length; ++i) {
			            	Account con = (Account) records[i];
			            	accountid= con.getId();
			            	ct.setAccountId(accountid);
			            	String queryString12 ="Select id from Contact where Email = '"+email+"' and AccountId='"+accountid+"'";
		    		    	QueryResult queryResults12 = connection.query(queryString12);
		    		    	if(queryResults12.getSize() > 0){
		    		    		com.sforce.soap.enterprise.sobject.SObject[] records1 = queryResults12.getRecords();
		    		            for (int s = 0; s < records1.length; ++s) {
		    		            	Contact con1 = (Contact) records1[s];
		    		            	ct.setId(con1.getId());
		    		            }
		    		            Contact[] recordct ={ct};
		    		    		saveResult2 = connection.update(recordct);
		    		    	}else{
		    		    		Contact[] recordct ={ct};
		    		    		saveResult2 = connection.create(recordct);
		    		    	}
			            }
			    	  
		    		    	//System.out.println("Contact saveResult: "+saveResult2[0].toString());
		        		if(saveResult2[0].isSuccess()){
		        			rc.setIsSuccess(true);
		            		contactid = saveResult2[0].getId();
		            		result +="Successfully saved contact record";
		            		id+=", "+contactid;
		            		rc.setId(id);
		            		//System.out.println(result);
		            		
		            	}else{
		            		rc.setIsSuccess(false);
		            		Error[] sr =saveResult2[0].getErrors();
		            		for (int s = 0; s < sr.length; ++s) {
				            	result+=sr[s].getMessage();
				            }
		            	}
			    	}else{
			    		rc.setIsSuccess(false);
			    		//System.out.println("Provided Wrong merchant code "+queryResults11.toString());
			    		result+="Provided Wrong merchant code";
			    	}
	    		}else if(type.equalsIgnoreCase("CONTACT_DELETE")){
	    			
	    			String queryString11 ="Select id from Account where EZY_Merchant_Code__c = '"+mCode+"'";
			    	QueryResult queryResults11 = connection.query(queryString11);
			    	if(queryResults11.getSize() > 0){
			    		com.sforce.soap.enterprise.sobject.SObject[] records = queryResults11.getRecords();
			            for (int i = 0; i < records.length; ++i) {
			            	Account con = (Account) records[i];
			            	accountid= con.getId();
			            	
			            	String queryString12 ="Select id from Contact where EzyCommerce_Contact_Role__c='Sub Users' and Email = '"+email+"' and AccountId='"+accountid+"' ";
		    		    	QueryResult queryResults12 = connection.query(queryString12);
		    		    	if(queryResults12.getSize() > 0){
		    		    		com.sforce.soap.enterprise.sobject.SObject[] records1 = queryResults12.getRecords();
		    		    		String[] ids = new String[records1.length];
		    		            for (int s = 0; s < records1.length; ++s) {
		    		            	Contact con1 = (Contact) records1[s];
		    		            	ids[s] =(con1.getId());
		    		            	if(id.isEmpty())
		    		            		id=con1.getId();
		    		            	else
		    		            		id+=", "+con1.getId();
		    		            }
		    		            DeleteResult[] deleteResults = connection.delete(ids);
		    		            if(deleteResults[0].isSuccess()){
		    		            	rc.setIsSuccess(true);
		    		            	rc.setId(id);		    		            	
		    		            	result +=deleteResults.length+" Successfully deleted contact ";
		    		            	
		    		            }else{
		    		            	rc.setIsSuccess(false);
		    		            	Error[] sr =deleteResults[0].getErrors();
				            		for (int s = 0; s < sr.length; ++s) {
						            	result+=sr[s].getMessage();
						            }
		    		            }
		    		            
		    		       }else{
		    		    	   rc.setIsSuccess(false);
		    		    	   result +="No contact found to delete ";
		    		       }
			            }
			    	  
			    	}else{
			    		rc.setIsSuccess(false);
			    		//System.out.println("Provided Wrong merchant code "+queryResults11.toString());
			    		result +="Provided Wrong merchant code ";
			    	}
	    		}
	    	}
		      
		    } catch (Exception e) {
		     	result +="Exception throws:"+ e.toString();
		    }
	   
	    PrintWriter out= response.getWriter();      
	   rc.setMessage(result);
	    Gson gson = new Gson();
	    String json =gson.toJson(rc);
	    //System.out.println(json);
	    try {
	        out.println(json);
	    } finally {
	        out.close();
	    }
	}
	
	 public Calendar convertStringToDate(String dateStr){
	    	
	    	//String dateStr = "04/11/2010"; 
		 	Calendar calendar = Calendar.getInstance();
			SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yyyy"); 
			Date dateObj = null;
			try {
				dateObj = curFormater.parse(dateStr);
				
				calendar.setTime(dateObj);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			
			
			//result = "- Year:"+calendar.get(calendar.YEAR)+", Month:"+calendar.get(calendar.MONTH)+" -";
	    	return calendar;
	    }
	    
	    public Boolean convertStringToBoolean(String strval){
	    	
	    	try{
	    		if(strval.isEmpty()){
		    		return false;
		    	}
	    		
	    		return Boolean.parseBoolean(strval);
	    	}catch(Exception e){//e.printStackTrace();
	    		
	    	}
	    	
	    	
	    	return true;
	    }
	    
	    public Double convertStringToDouble(String strval){
	    	
	    	
	    	try{
	    		if(strval.isEmpty()){
		    		return null;
		    	}
	    		return Double.parseDouble(strval);
	    		
	    	}catch(Exception e){
	    		return null;
	    	}
	    	
	    }
	    public Integer convertStringToInteger(String strval){
	    	
	    	
	    	try{
	    		if(strval.isEmpty()){
		    		return null;
		    	}
	    		return Integer.parseInt(strval);
	    		
	    	}catch(Exception e){
	    		return null;
	    	}
	    	
	    }

}
