package qb;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class QBClient {
	Map<String,String> userMap = new HashMap<String,String>();
	Map<String,String>  groupMap = new HashMap<String,String>();
	Map<String,List<String>>  memberMap = new HashMap<String,List<String>>();
	Map<String,List<String>>  groupMemberMap = new HashMap<String,List<String>>();
	
	private static final String FILENAME3 = "/Users/ssripathy/Documents/memberships.xml";
	private static final String FILENAME2 = "/Users/ssripathy/Documents/groups.xml";
	private static final String FILENAME1= "/Users/ssripathy/Documents/users.xml";
	//private static final String FILENAME = "/Users/ssripathy/Documents/test.xml";
	public static void main(String[] args) {
		QBClient parserXML  = new QBClient();
		//parserXML.execute();
		parserXML.executeUsers();
		parserXML.executeGroups();
		parserXML.executeMemberships();
		try {
		parserXML.getData();
		
		}catch(Exception e) {
			
		}
	}
	
	  /**
	   * This method returns the QBuild accounts
	   * @param None
	   * @return JSONArray
	   */
	    public JSONArray getAccounts() throws JSONException//, DaaSException, UnsupportedEncodingException
	    {
			System.out.println(">>>>>>>>>>>> getAccounts()");
	        //getData and return account array
	        JSONObject data = getData();
	         System.out.println("Accounts : "+  data.getJSONArray("accounts"));
	        System.out.println("<<<<<<<<<<<<  getAccounts()");
	        return data.getJSONArray("accounts");
	    }
	    
	    /**
	     * This method QB Permissions
	     * @param None
	     * @return JSONArray
	     */
	      public JSONArray getPermissions() throws JSONException//, DaaSException, UnsupportedEncodingException
	      {
	  		System.out.println(">>>>>>>>>>>> getPermissions()");
	          //getData and return perm array
	          JSONObject data = getData();
	           System.out.println("Permissions : {}" + data.getJSONArray("permissions"));
	          System.out.println("<<<<<<<<<<<<  getPermissions()");
	          return data.getJSONArray("permissions");
	      }
	      
		    /**
		     * 
		     * 
		     * @param xml
		     * @return
		     * @throws Exception
		     */
	      
	      public static Document loadXMLFromString(String xml) throws Exception
	      {
	          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	          DocumentBuilder builder = factory.newDocumentBuilder();
	          InputSource is = new InputSource(new StringReader(xml));
	          return builder.parse(is);
	      }
	      
	      
	      
	      
	      private String callQBRestEndpoint(String url) throws Exception {
	      	
	      	//String url = "https://openam-priceindustries-nane1-dev.id.forgerock.io/openidm/managed/alpha_user?";
	        	System.out.println("URL: " + url);
	      	   HttpClient httpClient = HttpClients.createDefault();
	      	
	      	  HttpGet request = new HttpGet(url);
	      	  request.addHeader("Authorization", "Bearer .....");
	    		// request.addHeader("Content-Type", "application/json");

	      	 HttpResponse response = null;
	      	 
	      	 try {
	      		 
	      	        response = httpClient.execute(request);
	      
	      	    } catch (IOException e) {
	      	        e.printStackTrace();
	      	    }

	      	  System.out.println("Step4: " + response.getStatusLine().getStatusCode() ) ; //+ " Response:" + EntityUtils.toString(response.getEntity()));
	      	  HttpEntity entity = response.getEntity();
	      	  String xml_string = EntityUtils.toString(entity);
	      	  

	      	  return xml_string;
	      
	      }
	      
	      /**
	       * This method collects the accounts, groups and permissions in a HashMap of JSONArray
	       * @param None
	       * @return JSONObject
	       */
	        public JSONObject getData() throws JSONException//, DaaSException, UnsupportedEncodingException
	        {
	    		System.out.println(">>>>>>>>>>>> getData()");
	    		// Commented this out as we would like to generate the PAM session token for each call
	    		//if(m_Connector.getSessionToken() != null &&  m_Connector.getSessionToken() != "")
	    		//	pamLogOff(); //Logoff the current session and force creation of a new session
	    		
	    		
	            JSONObject dataObject = new JSONObject();
	  
	            HashMap<String, JSONObject> users = new HashMap();
	    		HashMap<String, JSONObject> perms = new HashMap();

	 
	    		
	    		
	    		  Iterator it = memberMap.entrySet().iterator();
	    		    while (it.hasNext()) {
	    		        Map.Entry pair = (Map.Entry)it.next();
	    		        System.out.println(pair.getKey() + " = " + pair.getValue());
	    		        String userName = (String) pair.getKey();
	    		        JSONObject user = new JSONObject();
						user.put("id", userName);
						user.put("name", userName);
						user.put("type", "User");
	    		      
						//Get the list of group memberships and put it on the user
						List<String> groups = memberMap.get(userName);
						for(int i=0;i<groups.size();i++) {
							String groupName = groups.get(i);
							user.accumulate("groups",groupName);
						}
						System.out.println("User JSON: " + user.toString());
						users.put(userName, user);
						
	    		    }
	    		    
	    		   
		    		  it = groupMemberMap.entrySet().iterator();
		    		    while (it.hasNext()) {
		    		        Map.Entry pair = (Map.Entry)it.next();
		    		        System.out.println(pair.getKey() + " = " + pair.getValue());
		    		        String groupName = (String) pair.getKey();
		    		        JSONObject permission = new JSONObject();
		    		        permission.put("id", groupName);
		    		        permission.put("name", groupName);
		    		        permission.put("type", "Permission");
		    		      
							//Get the list of group memberships and put it on the user
							List<String> members = groupMemberMap.get(groupName);
							for(int i=0;i<members.size();i++) {
								String memberName = members.get(i);
								permission.accumulate("members",memberName);
							}
							System.out.println("Perms JSON: " + permission.toString());
							perms.put(groupName, permission);
							
		    		    }
	    		    
	            //Gather the users,safes, and safe permission
	            dataObject.put("accounts", users.values());
	            
	            
	
	            dataObject.put("permissions", perms.values());

	            // System.out.println("DataObject : {}", dataObject);
	           // System.out.println(">>>>>>>>>>>>");
	    		System.out.println("<<<<<<<<<<<< getData()");
	            return dataObject;
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

	          //String response = callQBRestEndpoint(String url);
	          //Document doc = loadXMLFromString(response);
	          
	          
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

	          //String response = callQBRestEndpoint(String url);
	          //Document doc = loadXMLFromString(response);
	          
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

	          //String response = callQBRestEndpoint(String url);
	          //Document doc = loadXMLFromString(response);
	          
	          
	          
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
	            		  
	            		  //user-->groups relationship
	            		  if(memberMap.containsKey(userName)) {
	            			  List<String> list = memberMap.get(userName);
	            			  list.add(groupName);
	            			  
	            		  }else {
	            			  List<String> list = new ArrayList<String>();
	            			  list.add(groupName);
	            			  memberMap.put(userName, list);
	            		  }
	
	            		  
	            		  //group --> members relationship
	            		  
	            		  if(groupMemberMap.containsKey(groupName)) {
	            			  List<String> list = groupMemberMap.get(groupName);
	            			  list.add(userName);
	            			  
	            		  }else {
	            			  List<String> list = new ArrayList<String>();
	            			  list.add(userName);
	            			  groupMemberMap.put(groupName, list);
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
