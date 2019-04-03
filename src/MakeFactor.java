import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.LabeledSkinBase;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Type;
import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.activation.ActivationException;
import java.rmi.activation.ActivationID;
import java.rmi.activation.Activator;
import java.rmi.activation.UnknownObjectException;
import java.util.List;

public class MakeFactor {
    private static TableView<Product> productTable;
    private static TableView<SelectedProduct> selectTable;
    private static Factor factor;
    private static Button add,delete,done;
    private static TextField number;

    static Factor display(List<Product> products){
        Stage stage = new Stage();
        stage.getIcons().addAll(new Image("assets\\table.png"));
        stage.setTitle("Make Factor");
        VBox layout = getLayout(products, stage);

        Scene scene = new Scene(layout,400,600);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.showAndWait();
        return factor;
    }

    private static VBox getLayout(List<Product> products, Stage stage) {
        factor = new Factor();
        makeProTable();
        productTable.setItems(FXCollections.observableList(products));
        makeSelectedTable();

        number = Factory.textField("number");
        add = Factory.button("Add");
        add.setOnAction(e->{
            if(number.getText().trim().length() == 0)
                return;
            float n;
            try {
                n = Float.valueOf(number.getText());
            }catch (Exception ee){
                return;
            }
            Product p= products.get(productTable.getSelectionModel().getSelectedIndex());
            factor.add(p,n);
            SelectedProduct s = new SelectedProduct();
            s.init(p,n);
            selectTable.getItems().add(s);
            productTable.refresh();

        });

        HBox bottomOfProTable = new HBox();
        bottomOfProTable.setSpacing(10);
        bottomOfProTable.setAlignment(Pos.CENTER);
        bottomOfProTable.setPadding(new Insets(10,10,10,10));
        bottomOfProTable.getChildren().addAll(number,add);

        VBox proLayout = new VBox();
        proLayout.getChildren().addAll(productTable,bottomOfProTable);

        delete = Factory.button("Delete");
        delete.setOnAction(e->{
            SelectedProduct s = selectTable.getSelectionModel().getSelectedItem();
            if(s == null)
                return;
            factor.remove(s.product,s.getNumber());
            selectTable.getItems().remove(s);
        });
        done = new Button("Done");
        done.setOnAction(e->{
            stage.close();
        });
        stage.setOnCloseRequest(e->stage.close());

        HBox bottomOfSelected = new HBox();
        bottomOfProTable.getChildren().addAll(delete,done);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(selectTable,bottomOfSelected);


        VBox layout = new VBox();
        layout.getChildren().addAll(vBox,proLayout);
        return layout;
    }

    private static void makeSelectedTable() {
        selectTable = new TableView<>();
        TableColumn<SelectedProduct,String>
                name = makeCol("Name",SelectedProduct.NAME,SHOW.NAME_WIDTH),
                price = makeCol("Price",SelectedProduct.PRICE,SHOW.PRICE_WIDTH),
                number = makeCol("Number",SelectedProduct.NUM,SHOW.NUM_WIDTH),
                cost = makeCol("Cost",SelectedProduct.COST,SHOW.PRICE_WIDTH),
                profit = makeCol("Profit",SelectedProduct.PROFIT,40);
        selectTable.getColumns().addAll(name,price,number,cost,profit);
    }

    private static TableColumn<SelectedProduct,String> makeCol(String vn,String pn,float width){
        TableColumn<SelectedProduct,String> t = new TableColumn<>(vn);
        t.setCellValueFactory(new PropertyValueFactory<>(pn));
        t.setMinWidth(width);
        return t;
    }

    private static void makeProTable(){
        productTable = new TableView<>();
        TableColumn<Product,String>
                name = SHOW.makeColumn("Name",Product.NAME,SHOW.NAME_WIDTH),
                price= SHOW.makeColumn("Price",Product.PRICE,SHOW.PRICE_WIDTH),
                number = SHOW.makeColumn("Number",Product.NUM,SHOW.NUM_WIDTH);
        productTable.getColumns().addAll(name,price,number);
    }




}
