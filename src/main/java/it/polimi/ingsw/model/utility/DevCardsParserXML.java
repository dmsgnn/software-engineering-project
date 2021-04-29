package it.polimi.ingsw.model.utility;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.gameboard.Color;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCard;
import it.polimi.ingsw.model.gameboard.development.DevelopmentCardDeck;
import it.polimi.ingsw.model.gameboard.development.ProductionPower;
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
import java.util.HashMap;
import java.util.Map;


public class DevCardsParserXML {
    private static final String xmlpath = DevCardsParserXML.class.getResource("/DevelopmentCards.xml").toExternalForm();

    public ArrayList<DevelopmentCardDeck> devCardsParser() {
        ArrayList<DevelopmentCard> cards = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xmlpath);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        //get all <card> nodes
        NodeList cardList = null;
        if (doc != null) {
            cardList = doc.getDocumentElement().getChildNodes();
        }

        if (cardList != null) {
            for(int i=0; i<cardList.getLength(); i++){
                Node cardNode = cardList.item(i);
                if(cardNode.getNodeType() == Node.ELEMENT_NODE){
                    cards.add(cardBuilder((Element) cardNode));
                }
            }
        }
        //builds a list containing the decks for gameboard
        ArrayList<DevelopmentCardDeck> devCardDeckList = new ArrayList<>();
        for(int level = 1; level <= 3; level++){
            for(Color color: Color.values()){
                int finalLevel = level;
                ArrayList<DevelopmentCard> tempDeck = cards.stream().filter(x -> x.getColor() == color).filter(x -> x.getLevel() == finalLevel).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
                devCardDeckList.add(new DevelopmentCardDeck(tempDeck, level, color));
            }
        }
        return devCardDeckList;
    }

    private DevelopmentCard cardBuilder(Element card){
        //reads all the attributes of the card
        Color color = Color.valueOf(card.getAttribute("color"));
        String id = card.getElementsByTagName("id").item(0).getTextContent();
        int level = Integer.parseInt(card.getElementsByTagName("level").item(0).getTextContent());
        int victoryPoints = Integer.parseInt(card.getElementsByTagName("victoryPoints").item(0).getTextContent());
        ProductionPower production = null;
        Map<Resource, Integer> requirements;

        Node requirementsNode = card.getElementsByTagName("requirements").item(0);
        NodeList reqNodeList = requirementsNode.getChildNodes();

        requirements = resourceMapBuilder(reqNodeList);

        Node productionNode = card.getElementsByTagName("cardProduction").item(0);
        NodeList prodNodeList = productionNode.getChildNodes();

        for(int i=0; i<prodNodeList.getLength(); i++){
            Node prodNode = prodNodeList.item(i);
            if(prodNode.getNodeType() == Node.ELEMENT_NODE){
                production = productionPowerBuilder((Element) prodNode);
            }
        }

        return new DevelopmentCard(requirements, color, id, level, victoryPoints, production);
    }

    private ProductionPower productionPowerBuilder(Element production){
        //reads all the attribute of the production power
        Map<Resource, Integer> cost;
        Map<Resource, Integer> gain;
        int faithGain = Integer.parseInt(production.getElementsByTagName("faithGain").item(0).getTextContent());
        Node costsNode = production.getElementsByTagName("resourceCost").item(0);
        NodeList costNodeList = costsNode.getChildNodes();

        cost = resourceMapBuilder(costNodeList);

        Node gainsNode = production.getElementsByTagName("resourceGain").item(0);
        NodeList gainsNodeList = gainsNode.getChildNodes();

        gain = resourceMapBuilder(gainsNodeList);

        return new ProductionPower(cost, gain, faithGain);
    }

    private Map<Resource, Integer> resourceMapBuilder(NodeList resources){

        Map<Resource, Integer> resourceMap = new HashMap<Resource, Integer>(){{
            for(Resource resource: Resource.values())
            put(resource, 0);
        }};

        for(int i=0; i<resources.getLength(); i++){
            Node n = resources.item(i);
            if(n.getNodeType() == Node.ELEMENT_NODE){
                Element resource = (Element) n;

                Resource key = Resource.valueOf(resource.getTagName());
                int value = Integer.parseInt(resource.getTextContent());

                resourceMap.replace(key, value);
            }
        }
        return resourceMap;
    }
}

