package it.polimi.ingsw.utility;

import it.polimi.ingsw.*;
import it.polimi.ingsw.gameboard.Color;
import it.polimi.ingsw.singleplayer.DiscardCardToken;
import it.polimi.ingsw.singleplayer.FaithToken;
import it.polimi.ingsw.singleplayer.ShuffleToken;
import it.polimi.ingsw.singleplayer.Token;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TokensParserXML {
    private static final String xmlPath = Objects.requireNonNull(TokensParserXML.class.getResource("/Tokens.xml")).toExternalForm();

    public ArrayList<Token> tokensParser(Game game) {
        ArrayList<Token> deck = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xmlPath);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        //get all <Token> nodes
        NodeList tokens = null;
        if (doc != null) {
            tokens = doc.getDocumentElement().getChildNodes();
        }

        if (tokens != null) {
            for (int i = 0; i < tokens.getLength(); i++) {
                Node tokenNode = tokens.item(i);
                if (tokenNode.getNodeType() == Node.ELEMENT_NODE) {
                    deck.add(tokenBuilder((Element) tokenNode, game));
                }
            }
        }
        return deck;
    }

    private Token tokenBuilder(Element token, Game game) {
        //reads all the attributes of the token
        Color color;
        String id = token.getElementsByTagName("id").item(0).getTextContent();
        int discardQuantity = Integer.parseInt(token.getElementsByTagName("discardQuantity").item(0).getTextContent());
        if(discardQuantity != 0) {
            color = Color.valueOf(token.getElementsByTagName("color").item(0).getTextContent());
        }
        else{
            color = null;
        }
        int faithGain = Integer.parseInt(token.getElementsByTagName("faithGain").item(0).getTextContent());
        int shuffle = Integer.parseInt(token.getElementsByTagName("shuffle").item(0).getTextContent());

        if (faithGain > 0 && shuffle == 0) {
            return new FaithToken(game, faithGain, id);
        }
        if (faithGain > 0 && shuffle > 0) {
            return new ShuffleToken(game, faithGain, shuffle, id);
        }
        return new DiscardCardToken(game, color, discardQuantity, id);
    }
}
