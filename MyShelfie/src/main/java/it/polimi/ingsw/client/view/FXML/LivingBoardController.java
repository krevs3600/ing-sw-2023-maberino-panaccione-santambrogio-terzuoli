//ingsw.client.view.FXML;
//ngsw.controller.GameController;
//ngsw.model.CommonGoalCard.*;
//ngsw.model.Game;
//ngsw.model.LivingRoomBoard;
//ngsw.model.ModelView.LivingRoomBoardView;
//ngsw.model.Player;
//ngsw.model.Space;
//ngsw.model.utils.NumberOfPlayers;
//ngsw.model.utils.Position;
//ngsw.model.utils.SpaceType;
//ngsw.model.utils.TileType;
//t.EventType;
//.FXML;
//e.Group;
//e.Node;
//e.control.Button;
//e.control.RadioButton;
//e.image.Image;
//e.image.ImageView;
//e.input.MouseEvent;
//e.layout.AnchorPane;
//e.layout.GridPane;
//e.Stage;
//
//eInputStream;
//eNotFoundException;
//;
//
//gBoardController {
//
//;
//
//
//;
//
//
//tions;
//
//
//helf;
//
//
//t_2;
//
//
//t_4;
//
//
//t_6;
//
//
//t_8;
//
//
//nd_2;
//
//nd_4;
//
//
//nd_6;
//
//
//nd_8;
//
//
//yerBookshelves;
//
//
//onalCard;
//
//
//onGoalCardView1;
//
//
//onGoalCardView2;
//
//
//
//
//
//
//n columnSelected = false;
//lumn;
// nickname = "carlo";
//
//String getCommonGoalCardPic(Object o){
//h = "src/main/resources/it/polimi/ingsw/client/view/common goal cards/";
//anceof TwoSquaresCommonGoalCard){
// path + "1.jpg";
//
// instanceof TwoColumnsCommonGoalCard){
// path + "2.jpg";
//
// instanceof FourGroupsCommonGoalCard){
// path + "3.jpg";
//
// instanceof SixGroupsCommonGoalCard){
// path + "4.jpg";
//
// instanceof ThreeColumnsCommonGoalCard){
// path + "5.jpg";
//
// instanceof TwoLinesCommonGoalCard){
// path + "6.jpg";
//
// instanceof FourLinesCommonGoalCard){
// path + "7.jpg";
//
// instanceof CornersCommonGoalCard){
// path + "8.jpg";
//
// instanceof EightTilesCommonGoalCard){
// path + "9.jpg";
//
// instanceof CrossCommonGoalCard){
// path + "10.jpg";
//
// instanceof DiagonalCommonGoalCard){
// path + "11.jpg";
//
// instanceof IncreasingColumnsCommonGoalCard){
// path + "12.jpg";
//
//
// "back.jpg";
//
//
//
//itialize() throws FileNotFoundException {
// itemTiles paths and creating support structures
//h = "src/main/resources/it/polimi/ingsw/client/view/itemtiles/";
//pe, String[]> images = new HashMap<>();
//ats = {"Gatti1.1.png", "Gatti1.2.png", "Gatti1.3.png"};
//rames = {"Cornici1.1.png", "Cornici1.2.png", "Cornici1.3.png"};
//ames = {"Giochi1.1.png", "Giochi1.2.png", "Giochi1.3.png"};
//ooks = {"Libri1.1.png", "Libri1.2.png", "Libri1.3.png"};
//lants = {"Piante1.1.png", "Piante1.2.png", "Piante1.3.png"};
//rophey = {"Trofei1.1.png", "Trofei1.2.png", "Trofei1.3.png"};
//(TileType.CAT, cats);
//(TileType.FRAME, frames);
//(TileType.GAME, games);
//(TileType.BOOK, books);
//(TileType.PLANT, plants);
//(TileType.TROPHY, trophey);
//
//
//ME
//orrect the game creation
//= new Game(NumberOfPlayers.FOUR_PLAYERS, "game"); // default 2 players
//tLivingRoomBoard(NumberOfPlayers.FOUR_PLAYERS);
//Board livingBoard = game.getLivingRoomBoard();
//lo = new Player("carlo");
// = new Player("fraaaa");
//= new Player("piiii111");
//e = new Player("mab3");
//ribe(carlo);
//ribe(fra);
//ribe(pi);
//ribe(mabe);
//
//
//
//
//VING_ROOM_BOARD
//ut.println(new LivingRoomBoardView(livingBoard).toString());
// r=0; r<9; r++){
//(int c=0; c<9; c++){
//Space space = livingBoard.getSpace(new Position(r,c));
//if (space.getType() == SpaceType.PLAYABLE){
//    String randImage = images.get(space.getTile().getType())[new Random().nextInt(0,2)];
//    ImageView imageView = new ImageView(new Image(new FileInputStream(path + randImage)));
//    imageView.setFitWidth((board.getPrefWidth() - ((board.getColumnCount()-1)*board.getHgap()))/board.getColumnCount()-10);
//    imageView.setFitHeight((board.getPrefHeight() - ((board.getRowCount()-1)*board.getVgap()))/board.getRowCount()-10);
//    imageView.setOnMouseClicked(this::boardTileClicked);
//    board.add(imageView, c, r);
//}
//
//
//
//MMON_GOAL_CARD
//Card commonGoalCard1 = game.getLivingRoomBoard().getCommonGoalCards().get(0);
//Card commonGoalCard2 = game.getLivingRoomBoard().getCommonGoalCards().get(1);
//CardView1.setImage(new Image(new FileInputStream(getCommonGoalCardPic(commonGoalCard1))));
//CardView2.setImage(new Image(new FileInputStream(getCommonGoalCardPic(commonGoalCard2))));
//
//LEPACK
//node : tilePack.getChildren()){
//iew imageView = (ImageView) node;
//eView) node).setImage(null);
//iew.setOnMouseClicked(this::packTileClicked);
//
//
//
//LUMN BUTTONS
//node : columnOptions.getChildren()){
//utton radioButton = (RadioButton) node;
//utton.setOnMouseClicked(this::radioButtonPressed);
//
//
//OKSHELF
//=0; r<6;r++){
//nt c=0; c<5; c++){
//ageView imageView = new ImageView();
//ageView.setFitWidth((bookshelf.getPrefWidth() - (int)bookshelf.getPadding().getLeft() - (int)bookshelf.getPadding().getRight() - ((bookshelf.getColumnCount()-1)*bookshelf.getHgap()))/bookshelf.getColumnCount());
//ageView.setFitHeight((bookshelf.getPrefHeight() - (int)bookshelf.getPadding().getTop() - (int)bookshelf.getPadding().getBottom() - ((bookshelf.getRowCount()-1)*bookshelf.getVgap()))/bookshelf.getRowCount());
//ageView.setImage(null);
//okshelf.add(imageView, c,r);
//
//
//
//TTONS
//ake buttons invisible, then enable the right ones
//ode : playerBookshelves.getChildren()){
// button = (Button) node;
//.setVisible(false);
//
//
//.println(game.getNumberOfPlayers().getValue());
//
//=0; i<game.getNumberOfPlayers().getValue(); i++){
//jects.equals(game.getSubscribers().get(i).getName(), nickname)){
//tton button = (Button) playerBookshelves.getChildren().get(j);
//tton.setVisible(true);
//tton.setText(game.getSubscribers().get(i).getName() + "'s bookshelf");
//tton.setOnMouseClicked(this::bookshelfButtonPressed);
//+;
//
//
//
//rsonal Card
//carlo.getPersonalGoalCard().getPath();
//rd.setImage(new Image(new FileInputStream("src/main/resources/it/polimi/ingsw/client/view/personal goal cards/Personal_Goals" + String.valueOf(num) + ".png")));
//
//
//
//
//
//adioButtonPressed(MouseEvent event) {
//.println("Button pressed");
//n buttonEvent = (RadioButton) event.getSource();
//n = Integer.parseInt(buttonEvent.getId().replaceAll("Column", ""))-1;
//.println(column);
//cted = true;
//
//
//ackTileClicked(MouseEvent event) {
//Selected){
//iew sourceImageView = (ImageView) event.getSource();
//t r=bookshelf.getRowCount()-1; r>0; r--){
//ageView bookshelfImage = (ImageView) bookshelf.getChildren().get(r*5 + column);
// (bookshelfImage.getImage() == null) {
//  bookshelfImage.setImage(sourceImageView.getImage());
//  sourceImageView.setImage(null);
//
//
//
//
//
//ardTileClicked(MouseEvent event){
//.println("carta cliccata");
//k needs to be updated
//sourceImageView = (ImageView) event.getSource();
//node : tilePack.getChildren()){
//iew imageView = (ImageView) node;
//ageView.getImage() == null){
//ImageView) node).setImage(sourceImageView.getImage());
//urceImageView.setImage(null);
//
//
//
//
//okshelfButtonPressed(MouseEvent event) {
//e = new Stage();
//();
//
//
//
//oringTokenClicked(MouseEvent event){
//tVisible(false);
//
//
//


