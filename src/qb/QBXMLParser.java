package qb;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class QBXMLParser {
	Map<String,String> userMap = new HashMap<String,String>();
	Map<String,String>  groupMap = new HashMap<String,String>();
	Map<String,List<String>>  memberMap = new HashMap<String,List<String>>();
	
	private static final String FILENAME3 = "/Users/ssripathy/Documents/memberships.xml";
	private static final String FILENAME2 = "/Users/ssripathy/Documents/groups.xml";
	private static final String FILENAME1= "/Users/ssripathy/Documents/users.xml";
	//private static final String FILENAME = "/Users/ssripathy/Documents/test.xml";
	public static void main(String[] args) {
		QBXMLParser parserXML  = new QBXMLParser();
		//parserXML.execute();
		parserXML.executeUsers();
		parserXML.executeGroups();
		parserXML.executeMemberships();
		

		
	}
	  public void executeUsers() {

	      // Instantiate the Factory
	      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	      try {

	          // optional, but recommended
	          // process XML securely, avoid attacks like XML External Entities (XXE)
	          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

	          // parse XML file
	          DocumentBuilder db = dbf.newDocumentBuilder();

	          Document doc = db.parse(new File(FILENAME1));

	          // optional, but recommended
	          // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	          doc.getDocumentElement().normalize();

	          System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
	          System.out.println("------");
	          
	          if (doc.hasChildNodes()) {
	              printUserNode(doc.getChildNodes());
	          }
	      } catch (ParserConfigurationException | SAXException | IOException e) {
	          e.printStackTrace();
	      }

	  }

	  private void printUserNode(NodeList nodeList) {
		   System.out.println(" number of nodes :" + nodeList.getLength());
		   for (int temp = 0; temp < nodeList.getLength(); temp++) {
			   
	              Node node = nodeList.item(temp);
	              NodeList userNodeList =  node.getChildNodes();
	              for (int i = 0; i < userNodeList.getLength(); i++) {
	            	    node = userNodeList.item(i);
	            	  if (node.getNodeType() == Node.ELEMENT_NODE) {
	            	  
	            		  Element element = (Element) node;
	            		  // get text
	            		  String id = element.getElementsByTagName("id").item(0).getTextContent();
	            		  String name = element.getElementsByTagName("name").item(0).getTextContent();
	            		 // String fullName = element.getElementsByTagName("fullName").item(0).getTextContent();
	            		//  String email = element.getElementsByTagName("email").item(0).getTextContent();

	                  
	            		  System.out.println("Current Element :" + node.getNodeName());
	            		  System.out.println("Id : " + id);
	            		  System.out.println("Username  : " + name);
	            		  userMap.put(id, name);
	            		//  System.out.println("Full Name : " + fullName);
	            		 // System.out.println("Email : " + email);

	            	  }
	              }
	          }

	  }
	  
	  public void executeGroups() {

	      // Instantiate the Factory
	      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	      try {

	          // optional, but recommended
	          // process XML securely, avoid attacks like XML External Entities (XXE)
	          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

	          // parse XML file
	          DocumentBuilder db = dbf.newDocumentBuilder();

	          Document doc = db.parse(new File(FILENAME2));

	          // optional, but recommended
	          // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	          doc.getDocumentElement().normalize();

	          System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
	          System.out.println("------");
	          
	          if (doc.hasChildNodes()) {
	              printGroupNode(doc.getChildNodes());
	          }
	      } catch (ParserConfigurationException | SAXException | IOException e) {
	          e.printStackTrace();
	      }

	  }

	  private void printGroupNode(NodeList nodeList) {
		   System.out.println(" number of nodes :" + nodeList.getLength());
		   for (int temp = 0; temp < nodeList.getLength(); temp++) {
			   
	              Node node = nodeList.item(temp);
	              NodeList userNodeList =  node.getChildNodes();
	              for (int i = 0; i < userNodeList.getLength(); i++) {
	            	    node = userNodeList.item(i);
	            	  if (node.getNodeType() == Node.ELEMENT_NODE) {
	            	  
	            		  Element element = (Element) node;
	            		  // get text
	            		  String id = element.getElementsByTagName("id").item(0).getTextContent();
	            		  String name = element.getElementsByTagName("name").item(0).getTextContent();
	            		  groupMap.put(id, name);
	            		 // String fullName = element.getElementsByTagName("fullName").item(0).getTextContent();
	            		//  String email = element.getElementsByTagName("email").item(0).getTextContent();
	            		  System.out.println("Current Element :" + node.getNodeName());
	            		  System.out.println("Id : " + id);
	            		  System.out.println("Username  : " + name);
	            		//  System.out.println("Full Name : " + fullName);
	            		 // System.out.println("Email : " + email);

	            	  }
	              }
	          }

	  }

	
	  public void executeMemberships() {

	      // Instantiate the Factory
	      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	      try {

	          // optional, but recommended
	          // process XML securely, avoid attacks like XML External Entities (XXE)
	          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

	          // parse XML file
	          DocumentBuilder db = dbf.newDocumentBuilder();

	          Document doc = db.parse(new File(FILENAME3));
	          // optional, but recommended
	          // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	          doc.getDocumentElement().normalize();

	          System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
	          System.out.println("------");
	          
	          if (doc.hasChildNodes()) {
	              printMembershipNode(doc.getChildNodes());
	          }
	      } catch (ParserConfigurationException | SAXException | IOException e) {
	          e.printStackTrace();
	      }
	      System.out.println("-----------------------------------------------");
		    for (String i : memberMap.keySet()) {
		        System.out.println("User: " + i );
		        List<String> list = memberMap.get(i);
		        for(String item: list) {
		        	 System.out.println("\t\t Group: " + item );
		        }
		      }

	  }
	  
	  //Create a membership list where each user shows all the 

	  private void printMembershipNode(NodeList nodeList) {
		  
		   System.out.println(" number of nodes :" + nodeList.getLength());
		   for (int temp = 0; temp < nodeList.getLength(); temp++) {
			   
	              Node node = nodeList.item(temp);
	              NodeList userNodeList =  node.getChildNodes();
	              for (int i = 0; i < userNodeList.getLength(); i++) {
	            	    node = userNodeList.item(i);
	            	  if (node.getNodeType() == Node.ELEMENT_NODE) {
	            	  
	            		  Element element = (Element) node;
	            		  // get text
	            		  String id = element.getElementsByTagName("id").item(0).getTextContent();
	            		  
	            		  String userId = element.getElementsByTagName("user").item(0).getTextContent();
	            		  String userName = userMap.get(userId);
	            		  String groupId = element.getElementsByTagName("group").item(0).getTextContent();
	            		  String groupName = groupMap.get(groupId);
	            		  if(memberMap.containsKey(userName)) {
	            			  List<String> list = memberMap.get(userName);
	            			  list.add(groupName);
	            			  
	            		  }else {
	            			  List<String> list = new ArrayList<String>();
	            			  list.add(groupName);
	            			  memberMap.put(userName, list);
	            		  }
	
	            		 // String fullName = element.getElementsByTagName("fullName").item(0).getTextContent();
	            		//  String email = element.getElementsByTagName("email").item(0).getTextContent();
	            		  System.out.println("Current Element :" + node.getNodeName());
	            		  System.out.println("groupId : " + groupId);
	            		  System.out.println("userId  : " + userId);
	            		//  System.out.println("Full Name : " + fullName);
	            		 // System.out.println("Email : " + email);

	            	  }
	              }
	          }

	  }
	
}
