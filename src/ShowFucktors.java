import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class ShowFucktors {
    private static TableView<Factor> factorTable;
    private static Button delete;
    public static void display(List<Factor> factors){
        factorTable = new TableView<>();
        TableColumn<Factor,String>
                cost = makeCol("Cost",Factor.COST,70),
                profit = makeCol("Profit",Factor.PROFIT,70),
                co = makeCol("Co",Factor.CO,20);
        int[] c={1};
        factors.forEach((f)->f.setCounter(c[0]++));
        factorTable.getItems().addAll(FXCollections.observableList(factors));
        factorTable.getColumns().addAll(co,profit,cost);

        factorTable.setRowFactory(tv ->{
            TableRow<Factor> row = new TableRow<>();

            row.setOnMouseClicked(ev->{
                if(ev.getClickCount() == 2 && !row.isEmpty())
                    ShowFucktor.display(row.getItem());
            });

            return row;
        });

        //delete button
        Button del = new Button("Delete");
        del.setOnAction(e-> {
            int i = factorTable.getSelectionModel().getSelectedIndex();
            if (i==-1)
                return;
            factors.remove(i);
            factorTable.getItems().remove(i);
        });

        Label allp = new Label();
        float sum = 0;
        for (int i = 0; i < factors.size(); i++) {
            sum+=factors.get(i).getSud();
        }

        allp.setText(sum+"");

        Stage stage = new Stage();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(factorTable,allp,del);
        stage.setScene(new Scene(vBox));
        stage.getIcons().addAll(new Image("assets\\sheets.png"));
        stage.setTitle("Show Factors");
        stage.show();
    }

    private static TableColumn<Factor,String> makeCol(String vn, String pn,float width){
        TableColumn<Factor,String> t = new TableColumn<>(vn);
        t.setCellValueFactory(new PropertyValueFactory<>(pn));
        t.setMinWidth(width);
        return t;
    }
}
