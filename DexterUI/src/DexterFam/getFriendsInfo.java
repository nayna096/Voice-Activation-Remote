package DexterFam;

import java.util.Scanner;

import java.io.File;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class getFriendsInfo {
	public static void main(String[]args){
//
//	String firstname;
//	String lastname;
//	String gender;
//	String birthday;
//	String phonenumber;
//	String phonenumbertype;
//	public String getFirstName(String firstname) {
//		return firstname;
//	}
//
//	public String getLastName(String lastname) {
//		return lastname;
//	}
//
//	public String getGender(String gender) {
//		return gender;
//	}
//
//	public String getBirth(String birthday) {
//		return birthday;
//	}
//
//	public String getPhone(String phonenumber) {
//		return phonenumber;
//	}
//
//	public String getType(String phonenumbertype) {
//		return phonenumbertype;
//	}
//	public void setFirstName(String firstname) {
//		this.firstname = firstname;
//	}
//	public void setLastName(String lastname) {
//		this.lastname = lastname;
//	}
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//	public static void main(String[]args) {
//		String name;
//		Scanner input = new Scanner(System.in);
//		System.out.println("Please enter your name: ");
//		name = input.nextLine();
//		input.close();
//		System.out.println("Your name is: "+name);
//	}
//}
//		try {
//			File inputFile = new File("FriendsList.xml");
//			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//			org.w3c.dom.Document doc = docBuilder.parse(inputFile);
//			org.w3c.dom.Node cars = ((org.w3c.dom.Node) doc).getFirstChild();
//			org.w3c.dom.Node supercar = doc.getElementsByTagName("Friend").item(3);
//			// update supercar attribute
//			NamedNodeMap attr = ((org.w3c.dom.Node) supercar).getAttributes();
//			Node nodeAttr = (Node) attr.getNamedItem("name");
//			((org.w3c.dom.Node) nodeAttr).setTextContent("nickname");
//
//			// loop the supercar child node
//			NodeList list = ((org.w3c.dom.Node) supercar).getChildNodes();
//
//			for (int temp = 0; temp < list.getLength(); temp++) {
//				Node node = (Node) list.item(temp);
//				if (((org.w3c.dom.Node) node).getNodeType() == 1) {
//					Element eElement = (Element) node;
//					if ("FirstName".equals(eElement.getNodeName())) {
//						if ("Adam".equals(eElement.getTextContent())) {
//							eElement.setTextContent("Lamborigini 001");
//						}
//						if ("Emma".equals(eElement.getTextContent()))
//							eElement.setTextContent("Lamborigini 002");
//					}
//				}
//			}
		try {
			File inputFile = new File("inputfile.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.parse(inputFile);
			org.w3c.dom.Node cars = doc.getFirstChild();
			org.w3c.dom.Node supercar = doc.getElementsByTagName("supercars").item(0);
			org.w3c.dom.Node carname = doc.getElementsByTagName("carname").item(0);
			// update supercar attribute
			NamedNodeMap attr = supercar.getAttributes();
			org.w3c.dom.Node nodeAttr = attr.getNamedItem("company");
			nodeAttr.setTextContent("Noah do you got this?");
			NamedNodeMap attribu = carname.getAttributes();
			org.w3c.dom.Node nodeattribu = attribu.getNamedItem("type");
			nodeattribu.setTextContent("Noah you got this!");
			// loop the supercar child node
			NodeList list = supercar.getChildNodes();

			for (int temp = 0; temp < list.getLength(); temp++) {
				org.w3c.dom.Node node = list.item(temp);
				if (node.getNodeType() == 1) {
					Element eElement = (Element) node;
					if ("carname".equals(eElement.getNodeName())) {
						if ("Lamborigini 001".equals(eElement.getTextContent())) {
							eElement.setTextContent("Adam");
						}
						if ("Lamborigini 002".equals(eElement.getTextContent()))
							eElement.setTextContent("Matthew");
					}
				}
			}
			NodeList childNodes = cars.getChildNodes();

			for (int count = 0; count < childNodes.getLength(); count++) {
				org.w3c.dom.Node node = childNodes.item(count);

				if ("luxurycars".equals(node.getNodeName()))
					cars.removeChild(node);
				
			}

			// write the content on console
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource((org.w3c.dom.Node) doc);
			StreamResult modfile = new StreamResult("inputfile.xml");
			transformer.transform(source, modfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
