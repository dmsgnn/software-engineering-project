package it.polimi.ingsw.utility;

import it.polimi.ingsw.Resource;
import it.polimi.ingsw.gameboard.Color;
import it.polimi.ingsw.leadercard.LeaderCard;
import it.polimi.ingsw.leadercard.Requirements.ColorRequirements;
import it.polimi.ingsw.leadercard.Requirements.Requirements;
import it.polimi.ingsw.leadercard.Requirements.ResourceRequirements;
import it.polimi.ingsw.leadercard.ability.*;
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


public class LeaderCardsParserXML {
    private static final String xpath = LeaderCardsParserXML.class.getResource("/LeaderCards.xml").toExternalForm();

    public ArrayList<LeaderCard> leaderCardsParser(){
        ArrayList<LeaderCard> cards = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xpath);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        //get all <card> nodes
        NodeList cardList  =null;
        if (doc != null){
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
        return cards;
    }

    private LeaderCard cardBuilder(Element card){
        Map<Resource, Integer> resourceRequirements;
        Map<Color, Integer> colorRequirements;
        Map<Color, Integer> levelColorRequirements;
        Resource cardResource = Resource.valueOf(card.getAttribute("cardResource"));
        String realAbility = String.valueOf(card.getAttribute("ability"));
        String id = card.getElementsByTagName("id").item(0).getTextContent();
        int victoryPoints = Integer.parseInt(card.getElementsByTagName("victoryPoints").item(0).getTextContent());


        //color req
        Node ColorRequirementsNode = card.getElementsByTagName("colorRequirements").item(0);
        NodeList colorReqList = ColorRequirementsNode.getChildNodes();
        colorRequirements = colorMapBuilder(colorReqList);

        //levelColor req
        Node levelRequirementsNode = card.getElementsByTagName("levelRequirements").item(0);
        NodeList levelReqList = levelRequirementsNode.getChildNodes();
        levelColorRequirements = levelMapBuilder(levelReqList);


        //resource req
        Node ResourceRequirementsNode = card.getElementsByTagName("resourceRequirements").item(0);
        NodeList resourceReqList = ResourceRequirementsNode.getChildNodes();
        resourceRequirements = resourceMapBuilder(resourceReqList);




        Ability ability;
        Requirements requirements;
        Requirements extraRequirements;
        switch (realAbility) {
            case "exchange":
                ability = new ExchangeAbility();
                break;
            case "store":
                ability = new StoreAbility();
                break;
            case "production":
                ability = new ProductionAbility();
                break;
            default:
                ability = new DiscountAbility();
                break;
        }
        boolean value=false;
        for (Color color: colorRequirements.keySet()){
          if (colorRequirements.get(color)!=0) value=true;
        }
        if (value){
            ColorRequirements colored = new ColorRequirements();
            colored.setLevelCardRequirements(levelColorRequirements);
            colored.setColorCardRequirements(colorRequirements);
            requirements = colored;
        }
        else {
            ResourceRequirements reserve = new ResourceRequirements();
            reserve.setCardRequirements(resourceRequirements);
            requirements = reserve;
        }

        return new LeaderCard(id, victoryPoints,ability,requirements,cardResource);

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

    private Map<Color, Integer> colorMapBuilder(NodeList colors){

        Map<Color, Integer> colorMap = new HashMap<Color, Integer>(){{
            for(Color color: Color.values())
                put(color, 0);
        }};

        for(int i=0; i<colors.getLength(); i++){
            Node n = colors.item(i);
            if(n.getNodeType() == Node.ELEMENT_NODE){
                Element color = (Element) n;

                Color key = Color.valueOf(color.getTagName());
                int value = Integer.parseInt(color.getTextContent());
                colorMap.replace(key, value);
            }
        }
        return colorMap;
    }

    private Map<Color, Integer> levelMapBuilder(NodeList colors){

        Map<Color, Integer> colorMap = new HashMap<Color, Integer>(){{
            for(Color color: Color.values())
                put(color, 0);
        }};

        for(int i=0; i<colors.getLength(); i++){
            Node n = colors.item(i);
            if(n.getNodeType() == Node.ELEMENT_NODE){
                Element color = (Element) n;

                Color key = Color.valueOf(color.getTagName());
                int value = Integer.parseInt(color.getTextContent());

                colorMap.replace(key, value);
            }
        }
        return colorMap;
    }





}
