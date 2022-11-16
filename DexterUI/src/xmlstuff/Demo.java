package xmlstuff;
import javax.xml.bind.*;
import javax.xml.namespace.*;
public class Demo {

    public static void main(String[] args) throws Exception {
    	MyFriend friend = new MyFriend();
    	
//    	JAXBContext jc = JAXBContext.newInstance(MyFriend.class);

        friend.setFirstName("Jane");

        friend.setLastName("Doe");

        PhoneNumber workPhone = new PhoneNumber();

        workPhone.setType("work");

        workPhone.setNumber("555-1111");
    //     friend.getPhoneNumbers().add(workPhone.getNumber());
         System.out.println("My friend's name is "+ friend.getFirstName()+" and their last name is "+friend.getLastName());

    //     System.out.println("My friend's phone number is 613-"+ friend.getPhoneNumbers());
         
//
//        Marshaller marshaller = jc.createMarshaller();
//
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//        JAXBElement<MyFriend> rootElement = new JAXBElement<MyFriend>(new QName("friend"), MyFriend.class, friend);
//
//        marshaller.marshal(rootElement, System.out);

    }

}