package wsc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
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
		// TODO Auto-generated method stub
		String result="";
		//String[] myJsonData = request.getParameterValues("json");
//		Gson gs = new Gson();  
//		CaseClass cc = gs.fromJson(request.getParameter("json"), CaseClass.class);
//		
//		String result=cc.getSubject();
//		Enumeration enParams = request.getParameterNames(); 
//		while(enParams.hasMoreElements()){
//		 String paramName = (String)enParams.nextElement();
//		 result+=("\n<br>Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
//		}
//		result +="hi "+request.getParameter("email");
		
		PrintWriter out = response.getWriter();
		 try {
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
        String username =eElement.getElementsByTagName("username").item(0).getTextContent();
        String password =eElement.getElementsByTagName("password").item(0).getTextContent();
        out.write(username);
        }catch(Exception e){            	
        	e.printStackTrace();   
        }finally {
            out.close();
        }
	}

}
