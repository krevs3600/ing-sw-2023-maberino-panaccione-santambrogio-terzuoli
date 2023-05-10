package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.Drawable;
import it.polimi.ingsw.model.utils.TileType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    private List<PersonalGoalCard> deck;

    /**
     * Class constructor
     */

    public PersonalGoalCardDeck () {
        this.deck = new ArrayList<PersonalGoalCard>();
        try {
            String path = "C:\\Users\\franc\\IdeaProjects\\ing-sw-2023-maberino-panaccione-santambrogio-terzuoli\\MyShelfie\\src\\main\\java\\it\\polimi\\ingsw\\model\\configs\\PersonalGoalCards.json";
            Reader file = new FileReader(path);
            JSONParser parser = new JSONParser();
            Object jsonObj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) jsonObj;
            // read points from json
            ArrayList<Integer> points = (ArrayList<Integer>) jsonObject.get("points");
            // read configs from json
            ArrayList<HashMap> configurations = (ArrayList<HashMap>) jsonObject.get("configurations");
            // for each conf create the corresponding card
            for (HashMap conf : configurations) {
                JSONObject jsonConf = (JSONObject) conf;
                ArrayList<Long> position = (ArrayList<Long>) jsonConf.get("position");
                ArrayList<String> type = (ArrayList<String>) jsonConf.get("type");

                // check configs
                if (position.size() == type.size()) {
                    HashMap<Integer, TileType> map = new HashMap<>();
                    for (int i = 0; i < position.size(); i++) {
                        map.put(position.get(i).intValue(), TileType.valueOf(type.get(i)));
                    }
                    this.deck.add(new PersonalGoalCard(map));
                    size++;
                } else {
                    System.out.println("Please check configuration file.");
                }
            }

        } catch (ParseException | IOException e) {
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

    public int getSize () { return size;}
}