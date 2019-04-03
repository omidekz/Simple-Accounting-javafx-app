import com.sun.javafx.binding.BindingHelperObserver;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.print.attribute.standard.MediaSize;
import java.util.*;

public class SHOW {
    static final int
            CO_WIDTH = 20,
            NAME_WIDTH = 100,
            PRICE_WIDTH = 70,
            NUM_WIDTH = 70,
            BUY_WIDTH = 100,
            EXPIRE_WIDTH = 100;
    private static TableView<Product> tableView;
    private static final int STAGE_WIDTH =
            CO_WIDTH+NAME_WIDTH+PRICE_WIDTH+NUM_WIDTH+BUY_WIDTH+EXPIRE_WIDTH+17;

    public static void display(List<Product> products){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().addAll(new Image("assets\\table.png"));
        stage.setTitle("Show Goods");

        VBox layout = getLayout(products);

        Scene scene = new Scene(layout);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    static VBox getLayout(List<Product> products){
        return getLayout(products,true);
    }
    static VBox getLayout(List<Product> products,boolean s) {
        tableView = getProductTableView(products);
        tableView.setRowFactory( tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Change.display(products,row.getIndex(),s);
                    tableView.getItems().remove(row.getIndex());
                    tableView.getItems().add(row.getIndex(),products.get(row.getIndex()));
                    tableView.refresh();
                }
            });
            return row ;
        });
        HBox quikAdd = quikAdd(products);
        VBox layout = new VBox();
        layout.getChildren().addAll(tableView,quikAdd);
        return layout;
    }

    private static HBox quikAdd(List<Product> products) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        TextField
                name = Factory.textField("name"),
                price = Factory.textField("price"),
                number = Factory.textField("number"),
                profit = Factory.textField("profit");

        Button add = Factory.button("Add");
        add.setOnAction(e->{
            if(name.getText().trim().length()==0)
                return;
            float p,n;
            try {
                p = Float.valueOf(price.getText());
                n = Float.valueOf(number.getText());
            }catch (Exception ee){
                return;
            }
            Product product = new Product(name.getText(),p,n,"","");
            product.setProfit(Float.valueOf(profit.getText()));
            product.setCounter(products.size()+1);
            products.add(product);
            tableView.getItems().add(product);
        });
        Button delete = Factory.button("Delete");
        delete.setOnAction(e->{
            ObservableList<Product> items;
            Product selected;
            selected = tableView.getSelectionModel().getSelectedItem();
            items = tableView.getItems();
            for (int i = 0; i < items.size(); i++) {
                if(items.get(i).equals(selected)){
                    items.remove(i);
                    break;
                }
            }
            products.remove(selected);
        });

        hBox.getChildren().addAll(name,price,profit,number,add,delete);
        return hBox;
    }

    static TableView<Product> getProductTableView(List<Product> products) {
        int[] c={1};
        products.forEach((f)->f.setCounter(c[0]++));

        ObservableList<Product> productObservableList = FXCollections.observableArrayList();
        productObservableList.addAll(products);

        TableColumn<Product,String>
                nameCol = makeColumn("Name",Product.NAME,NAME_WIDTH),
                priceCol = makeColumn("Price",Product.PRICE,PRICE_WIDTH),
                numCol = makeColumn("Number",Product.NUM,NUM_WIDTH),
                buyCol = makeColumn("Buy date",Product.BD,BUY_WIDTH),
                exCol = makeColumn("Expire date",Product.ED,EXPIRE_WIDTH),
                profit = makeColumn("Profit",Product.PROFIT,70),
                counterCol = makeColumn("Co",Product.COUNTER,20);

        TableView<Product> tableView = new TableView<>(productObservableList);
        tableView.setItems(productObservableList);

        tableView.getColumns().add(counterCol);
        tableView.getColumns().add(nameCol);
        tableView.getColumns().add(numCol);
        tableView.getColumns().add(profit);
        tableView.getColumns().add(priceCol);
        tableView.getColumns().add(buyCol);
        tableView.getColumns().add(exCol);

        return tableView;
    }
    static TableColumn<Product,String> makeColumn(String viewName,String propertyName,float minWidth){
        TableColumn<Product,String> tc = new TableColumn<>(viewName);
        tc.setMinWidth(minWidth);
        tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        return tc;
    }
}
