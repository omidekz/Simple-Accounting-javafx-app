import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShowFucktor {
    static void display(Factor factor){
        Stage stage = new Stage();
        Label label = new Label();
        for (int i = 0; i < factor.getGoods().size(); i++) {
            label.setText(label.getText()+factor.getGoods().get(i).getName()+"\n");
        }
        VBox v = new VBox();
        v.getChildren().addAll(label);
        Scene scene = new Scene(v);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
