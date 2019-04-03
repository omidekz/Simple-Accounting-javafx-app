import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmBox {
    private static boolean isLive = false;
    private static boolean SURE = false;
    private static boolean alwaysTrue = false;

    public static boolean isIsLive(){
        return isLive;
    }

    public static boolean display(String tile,String mes){
        if(alwaysTrue)
            return true;
        Stage stage = new Stage();
        stage.getIcons().add(new Image("assets\\exit.png"));
        stage.setTitle(tile);
        stage.initModality(Modality.APPLICATION_MODAL);

        ImageView imageView = new ImageView(new Image("assets\\question.png"));
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        GridPane.setConstraints(imageView,0,0);

        //message
        Label message = new Label(mes);

        // checkBox
        CheckBox checkBox = new CheckBox();
        checkBox.setText("Don't show me again this message.");

        //ok btn
        Button sure = Factory.button("Sure");
        sure.setOnAction(e->{
            SURE=true;
            stage.close();
            if(checkBox.isSelected())
                alwaysTrue = true;
        });

        //cancel btn
        Button close = Factory.button("Close");
        close.setOnAction(e->{
            SURE=false;
            stage.close();
        });

        //holder of btns
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(sure,close);

        //holder of mes and holder of btn
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(message,hBox,checkBox);

        HBox layout = new HBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(20,20,20,10));

        layout.getChildren().addAll(imageView,vBox);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        return SURE;
    }

}
