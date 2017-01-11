package dao;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class JDBCConfiguration {

    private String driverName;
    private String URL;
    private String login;
    private String password;

    private static class SingletonJDBCConf{
        private static final JDBCConfiguration INSTANCE = new JDBCConfiguration();
    }

    public static JDBCConfiguration getInstance(){
        return SingletonJDBCConf.INSTANCE;
    }


    public String getDriverName() {
        return driverName;
    }

    public String getURL() {
        return URL;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }


    public  void applyConfiguration() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser =  factory.newSAXParser();
        SAXParserTest testParse = new SAXParserTest();
        saxParser.parse(new File("src/main/resources/JDBCconfig"),testParse);

    }

    private class SAXParserTest extends DefaultHandler {

        String thisElement;

        public SAXParserTest(){

        }
        @Override
        public void startDocument() throws SAXException {
            System.out.println("Start XML parse...");
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            thisElement = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (thisElement.equals("url")){
                URL  = new String(ch,start,length);
            }

            if (thisElement.equals("login")){
                login = new String(ch,start,length);
            }

            if (thisElement.equals("password")){
                password= new String(ch,start,length);
            }

            if (thisElement.equals("driver")){
                driverName = new String(ch,start,length);
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            thisElement = "";
        }

        @Override
        public void endDocument() throws SAXException {

            System.out.println("XML parse stopped!");

        }

    }
}
