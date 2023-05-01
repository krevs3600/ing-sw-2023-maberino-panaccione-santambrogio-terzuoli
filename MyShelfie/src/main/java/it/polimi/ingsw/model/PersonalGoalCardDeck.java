package it.polimi.ingsw.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <h1>Class PersonalGoalCardDeck</h1>
 * The class PersonalGoalCardDeck contains a list of personal goal cards
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 3/28/2023
 */

public class PersonalGoalCardDeck implements Drawable {

    private int size = 0;
    private final List<PersonalGoalCard> deck;

    /**
     * Class constructor
     */

    public PersonalGoalCardDeck () {
        this.deck = new ArrayList<PersonalGoalCard>();
        try {
            Reader file = new FileReader("src/main/java/it/polimi/ingsw/model/PersonalGoalCards.json");
            JSONParser parser = new JSONParser();
            Object jsonObj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) jsonObj;
            // read points from json
            ArrayList<Integer> points = (ArrayList<Integer>) jsonObject.get("points");
            System.out.println("points: " + points);
            // read configs from json
            ArrayList<HashMap> configurations = (ArrayList<HashMap>) jsonObject.get("configurations");
            // for each conf create the corresponding card
            for (HashMap conf : configurations) {
                JSONObject jsonConf = (JSONObject) conf;
                ArrayList<Integer> position = (ArrayList<Integer>) jsonConf.get("position");
                ArrayList<String> type = (ArrayList<String>) jsonConf.get("type");

                // check configs
                if (position.size() == type.size()) {
                    HashMap map = new HashMap<Integer, TileType>();
                    for (int i = 0; i < position.size(); i++) {
                        map.put(position.get(i), TileType.valueOf(type.get(i)));
                    }
                    this.deck.add(new PersonalGoalCard(map));
                    size++;
                } else {
                    System.out.println("Please check config file.");
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to draw a random personal goal card from the deck
     * @return GoalCard the randomly drawn goal card
     */
    @Override
    public GoalCard draw() {
        int randNumber = ThreadLocalRandom.current().nextInt(0, getDeck().size());
        GoalCard toBeDrawn = getDeck().get(randNumber);
        deck.remove(randNumber);
        size--;
        return toBeDrawn;
    }

    /**
     * This getter method gets the deck of personal goal cards
     * @return List<PersonalGoalCard> It returns the deck
     */
    public List<PersonalGoalCard> getDeck() {
        return deck;
    }
}