package it.polimi.ingsw.utility;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.gameboard.Market;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MarketParserXML {
    private static String xmlpath = MarketParserXML.class.getResource("/MarketConf.xml").toExternalForm();

    public Market marketParser(Game game){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xmlpath);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        NodeList market = null;
        if (doc != null) {
            market = doc.getElementsByTagName("market");
        }

        int rows, columns, red, white, blue, yellow, purple, grey;

        Node marketNode = null;
        if (market != null) {
            marketNode = market.item(0);
        }
        Element elem = (Element) marketNode;

        Node node = elem.getElementsByTagName("rows").item(0);
        rows = Integer.parseInt(node.getTextContent());

        node = elem.getElementsByTagName("columns").item(0);
        columns = Integer.parseInt(node.getTextContent());

        node = elem.getElementsByTagName("redMarbles").item(0);
        red = Integer.parseInt(node.getTextContent());

        node = elem.getElementsByTagName("whiteMarbles").item(0);
        white = Integer.parseInt(node.getTextContent());

        node = elem.getElementsByTagName("yellowMarbles").item(0);
        yellow = Integer.parseInt(node.getTextContent());

        node = elem.getElementsByTagName("blueMarbles").item(0);
        blue = Integer.parseInt(node.getTextContent());

        node = elem.getElementsByTagName("purpleMarbles").item(0);
        purple = Integer.parseInt(node.getTextContent());

        node = elem.getElementsByTagName("greyMarbles").item(0);
        grey = Integer.parseInt(node.getTextContent());

        return new Market(game, rows, columns, red, white, blue, yellow, purple, grey);
    }
}

