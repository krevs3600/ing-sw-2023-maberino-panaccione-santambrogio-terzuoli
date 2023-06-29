package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.Drawable;
import it.polimi.ingsw.model.utils.TileType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <h1>Class PersonalGoalCardDeck</h1>
 * The class PersonalGoalCardDeck consists of a list of {@link PersonalGoalCard}s
 *
 * @author Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 3/28/2023
 */

public class PersonalGoalCardDeck implements Drawable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private int size = 0;
    private final List<PersonalGoalCard> deck;

    /**
     * Class constructor
     * This method gets all the {@link PersonalGoalCard}s from the JSON file, parses them and puts in the list {@link PersonalGoalCardDeck#deck}
     */
    public PersonalGoalCardDeck () {
        this.deck = new ArrayList<>();
        try {
            String path = "src/main/java/it/polimi/ingsw/model/configs/PersonalGoalCards.json";
            //String path = "C:\\Users\\franc\\IdeaProjects\\ing-sw-2023-maberino-panaccione-santambrogio-terzuoli\\MyShelfie\\src\\main\\java\\it\\polimi\\ingsw\\model\\configs\\PersonalGoalCards.json";
            Reader file = new FileReader(path);
            JSONParser parser = new JSONParser();
            Object jsonObj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) jsonObj;
            // read points from json
            ArrayList<Integer> points = (ArrayList<Integer>) jsonObject.get("points");
            // read configs from json
            ArrayList<HashMap> configurations = (ArrayList<HashMap>) jsonObject.get("configurations");
            // for each conf create the corresponding card
            int num = 1;
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
                    this.deck.add(new PersonalGoalCard(map, num));
                    size++;
                } else {
                    System.out.println("Please check configuration file.");
                }
                num++;
            }

        } catch (ParseException | IOException e) {
            // should not be thrown normally
            throw new RuntimeException(e);
        }
    }

    /**
     * This method can be used in order to draw a random {@link PersonalGoalCard}  from the {@link PersonalGoalCardDeck#deck}
     * @return the randomly drawn {@link PersonalGoalCard}
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
     * Getter method
     * @return {@link PersonalGoalCardDeck#deck}
     */
    public List<PersonalGoalCard> getDeck() {
        return deck;
    }

    /**
     * Getter method
     * @return the {@code int} value of the {@link PersonalGoalCardDeck#size} of the {@link PersonalGoalCardDeck#deck}
     */
    public int getSize () { return size;}
}