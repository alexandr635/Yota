package api;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class XmlWorker {
    public String changeStatus(String baseUrl, String xml) throws IOException, ParserConfigurationException, SAXException {
        URL url = new URL(baseUrl + "customer/findByPhoneNumber");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/xml");

        OutputStream os = connection.getOutputStream();
        os.write(xml.getBytes(StandardCharsets.UTF_8));
        String body = getResponse(connection);

        String id = getId(body);
        connection.disconnect();
        return id;
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        BufferedReader bf;
        if (100 <= connection.getResponseCode() && connection.getResponseCode() >= 399) {
            bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }else{
            bf = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String resp;
        while ((resp = bf.readLine()) != null){
            sb.append(resp);
        }

        return sb.toString();
    }

    private String getId(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource inputSource = new InputSource();
        inputSource.setCharacterStream(new StringReader(xml));

        Document doc = documentBuilder.parse(inputSource);
        String id = doc.getElementsByTagName("customerId").item(0).getTextContent();

        return id;
    }
}
