package sample;

import Data.Account;
import Data.ComparableData;
import LinkedList.SortedLinkedList;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.math.BigDecimal;

import static java.lang.System.out;

public class Controller {

    private SortedLinkedList linkedList;

    private boolean animationRunning = false;

    ParallelTransition animationPlayer;

    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private Button b3;

    @FXML
    private AnchorPane searchPane;

    @FXML
    private ImageView searchImage;

    @FXML
    private GridPane searchPanePersonInformation;

    @FXML
    private TextField searchPaneFnameText;

    @FXML
    private TextField searchPaneLnaemText;

    @FXML
    private TextField searchPaneSSNumText;

    @FXML
    private TextField searchPaneGenderText;

    @FXML
    private TextField searchPaneAgeText;

    @FXML
    private TextField searchPaneIDText;

    @FXML
    private HBox searchBox;

    @FXML
    private TextField searchText;

    @FXML
    private Button searchButton;

    @FXML
    private AnchorPane listPane;

    @FXML
    private ListView<ComparableData> list;

    @FXML
    private Button listPaneDeleteButton;

    @FXML
    private AnchorPane insertPane;

    @FXML
    private TextField insertPaneFnameText;

    @FXML
    private TextField insertPaneLnameText;

    @FXML
    private TextField insertPaneSSNameText;

    @FXML
    private MenuButton insertPaneMenuButton;

    @FXML
    private RadioMenuItem Male;

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private RadioMenuItem Female;

    @FXML
    private TextField insertPaneAgeText;

    @FXML
    private TextField insertPaneIDText;

    @FXML
    private ImageView insertPaneImage;

    @FXML
    private Button insertSaveButton;

    @FXML
    private Button insertNewButton;

    @FXML
    private Button insertClearButton;

    @FXML
    private Text searchDisplay;

    @FXML
    void genderSelect(ActionEvent event) {
        insertPaneMenuButton.setText((String)genderGroup.getSelectedToggle().getUserData());
        insertPaneImage.setImage(new Image(insertPaneMenuButton.getText() + ".jpg"));
    }

    @FXML
    void insertClearButtonSelected(ActionEvent event) {
        genderGroup.selectToggle(null);
        insertPaneFnameText.setText("");
        insertPaneLnameText.setText("");
        insertPaneSSNameText.setText("");
        insertPaneMenuButton.setText("Chose");
        insertPaneAgeText.setText("");
        insertPaneIDText.setText("");
        insertPaneImage.setImage(new Image("person.jpg"));
    }

    @FXML
    void insertSaveButtonSelected(ActionEvent event) {
        String Fname = (!insertPaneFnameText.getText().replace(" ","").isEmpty())
                ?insertPaneFnameText.getText()
                :Alert("Pleas enter first name first!");
        String Lname = (!insertPaneLnameText.getText().replace(" ","").isEmpty())
                ?insertPaneLnameText.getText()
                :Alert("Pleas enter Last name first!");
        String SSNum = (!insertPaneSSNameText.getText().replace(" ","").replace("-","").isEmpty())
                ?insertPaneSSNameText.getText().replace("-","")
                :Alert("Pleas enter SocialSecurityNumber name first!");
        String AgeString = (!insertPaneAgeText.getText().isEmpty())
                ?insertPaneAgeText.getText()
                :Alert("Pleas enter Age first!");
        int Age = -1;
        try {
            assert AgeString != null;
            Age = Integer.parseInt(AgeString);
        }catch (Exception ex){
            Alert("Age must be a number");
        }
        int SSNumber = -1;
        try{
            assert SSNum != null;
            SSNumber = Integer.parseInt(SSNum);
        }catch (Exception ex){
            Alert("SSNumber is a number!");
        }
        if(genderGroup.getSelectedToggle() == null){
            Alert("Pleas select gender first!");
        }
        try{
            Account temp = new Account(Fname,Lname,(String) genderGroup.getSelectedToggle().getUserData(),Age,SSNumber);
            linkedList.sortedInsert(temp);
            insertPaneIDText.setText(temp.getID());
            insertNewButton.setVisible(true);
            insertSaveButton.setVisible(false);
            out.println(temp);
        }catch (Exception ex){
            Alert("Account save Failed");
            insertPaneIDText.setText("");
        }
    }

    @FXML
    void insertNewButtonSelected(ActionEvent event){
        insertClearButton.fire();
        insertNewButton.setVisible(false);
        insertSaveButton.setVisible(true);
    }

    @FXML
    void insert(ActionEvent event) {
        setVisible("insertPane");
    }

    @FXML
    void list(ActionEvent event) {
        setVisible("listPane");
        list.getItems().remove(0,list.getItems().size());
        for (int i = 0;i< linkedList.getLength();i++){
            list.getItems().add(linkedList.getNode(i).getData());
        }
    }

    @FXML
    void listPaneDeleteButtonSelected(ActionEvent event) {
        ComparableData[] temp = list.getSelectionModel().getSelectedItems().toArray(new ComparableData[0]);
        if(temp.length > 0 && confirmation("Are sure to delete this item :" + temp[0])) {
            list.getItems().remove(list.getSelectionModel().getSelectedIndex());
            linkedList.removeData(temp[0].getComparableValue());
        }
    }

    @FXML
    void search(ActionEvent event) {
        searchImage.setImage(new Image("person.jpg"));
        setVisible("searchPane");
        TranslateTransition searchBoxAnimation = new TranslateTransition(Duration.millis(1000));
        searchBoxAnimation.setToY(0);
        searchBoxAnimation.setNode(searchBox);
        FadeTransition infoBoxAnimation = new FadeTransition(Duration.millis(1200));
        infoBoxAnimation.setToValue(0.0);
        infoBoxAnimation.setNode(searchPanePersonInformation);
        animationPlayer = new ParallelTransition(searchBoxAnimation,infoBoxAnimation);
        animationPlayer.setOnFinished(event1 -> {
            searchDisplay.setText("Enter Person ID");
            FadeTransition animation = new FadeTransition(Duration.millis(700));
            animation.setToValue(1.0);
            animation.setNode(searchDisplay);
            animation.play();
        });
        animationRunning = true;
        animationPlayer.play();
    }

    @FXML
    void searchButtonSelected(ActionEvent event){
        String id = searchText.getText().replace("-","").replace(" ","");
        System.out.println(id);
        if(!id.isEmpty()) {
            int account = linkedList.searchNode(new BigDecimal(id));
            if(account != -1) {
                Account temp = (Account) linkedList.getNode(account).getData();
                searchPaneFnameText.setText(temp.getFirstName());
                searchPaneLnaemText.setText(temp.getLastName());
                searchPaneAgeText.setText(String.valueOf(temp.getAge()));
                searchPaneGenderText.setText(temp.getGender());
                searchPaneSSNumText.setText(String.valueOf(temp.getSocialSecurityNumber()));
                searchPaneIDText.setText(temp.getID());
                searchImage.setImage(new Image(temp.getPicture().toString()));
                TranslateTransition searchBoxAnimation = new TranslateTransition(Duration.millis(1000));
                searchBoxAnimation.setToY(-175);
                searchBoxAnimation.setNode(searchBox);
                FadeTransition infoBoxAnimation = new FadeTransition(Duration.millis(1200));
                searchPanePersonInformation.setVisible(true);
                infoBoxAnimation.setFromValue(0.0);
                infoBoxAnimation.setToValue(1.0);
                infoBoxAnimation.setNode(searchPanePersonInformation);
                animationPlayer = new ParallelTransition(searchBoxAnimation, infoBoxAnimation);
                FadeTransition displayAnimation = new FadeTransition(Duration.millis(500));
                displayAnimation.setToValue(0.0);
                displayAnimation.setNode(searchDisplay);
                displayAnimation.play();
                animationPlayer.play();
            }
            else{
                b1.fire();
                searchDisplay.setText("Not founded");
            }
        }else{
            searchImage.setImage(new Image("person.jpg"));
            TranslateTransition searchBoxAnimation = new TranslateTransition(Duration.millis(1000));
            searchBoxAnimation.setToY(0);
            searchBoxAnimation.setNode(searchBox);
            FadeTransition infoBoxAnimation = new FadeTransition(Duration.millis(1200));
            infoBoxAnimation.setToValue(0.0);
            infoBoxAnimation.setNode(searchPanePersonInformation);
            animationPlayer = new ParallelTransition(searchBoxAnimation,infoBoxAnimation);
            animationPlayer.setOnFinished(event1 -> {
                FadeTransition animation = new FadeTransition(Duration.millis(500));
                animation.setToValue(1.0);
                animation.setNode(searchDisplay);
                animation.play();
            });
            animationPlayer.play();
            searchDisplay.setText("Enter Person ID");
        }
    }

    private void setVisible(String pane){
        searchPane.setVisible(false);
        listPane.setVisible(false);
        insertPane.setVisible(false);
        switch (pane){
            case "searchPane" :
                searchPane.setVisible(true);
                break;
            case "listPane":
                listPane.setVisible(true);
                break;
            case "insertPane":
                insertPane.setVisible(true);
                break;
        }
    }

    private boolean confirmation(String massage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                massage, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }

    private String Alert(String massage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,massage,ButtonType.OK);
        alert.showAndWait();
        return null;
    }

    public void initialize(){
        Account test = new Account("M0", "Negad-asgary", "female", 20, 1362523658);
        Account test1 = new Account("M1", "Nassiri", "male", 20, 1362523658);
        Account test2 = new Account("M2", "Nassiri", "male", 20, 1362523658);
        Account test3 = new Account("M3", "Nassiri", "male", 20, 1362523658);
        Account test4 = new Account("M4", "Nassiri", "male", 20, 1362523658);
        linkedList = new SortedLinkedList();
        linkedList.sortedInsert(test,test4,test2,test3,test1);
        linkedList.display();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                searchImage.setImage(new Image("person.jpg"));
                insertPaneImage.setImage(new Image("person.jpg"));
                ImageView searchIcon = new ImageView(new Image("search icon .jpg"));
                searchIcon.setFitHeight(30);
                searchIcon.setFitWidth(30);
                b1.setGraphic(searchIcon);
                ImageView addPerson = new ImageView(new Image("addPerson.png"));
                addPerson.setFitHeight(40);
                addPerson.setFitWidth(40);
                b2.setGraphic(addPerson);
                ImageView list = new ImageView(new Image("list.png"));
                list.setFitHeight(40);
                list.setFitWidth(40);
                b3.setGraphic(list);
            }
        });
        Male.setUserData("Male");
        Female.setUserData("Female");
    }

}
