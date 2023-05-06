package it.polimi.ingsw.model.ModelView;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.Space;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class LivingRoomBoardView extends Observable implements Observer {

    private final LivingRoomBoard livingRoom;

    public LivingRoomBoardView (LivingRoomBoard livingRoom) {
        this.livingRoom = livingRoom;
    }

    public Space[][] getSpaces () {
        return livingRoom.getSpaces();
    }

    public List<CommonGoalCard> getCommonGoalCards () {
        return livingRoom.getCommonGoalCards();
    }

    public Bag getBag () {
        return livingRoom.getBag();
    }
}
