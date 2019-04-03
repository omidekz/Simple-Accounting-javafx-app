import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class Change {
    private static boolean ttt;
    static void display(List<Product> products,int index){
        display(products,index,true);
    }
    public static void display(List<Product> products,int index,boolean state){
        ttt = state;
        Product product = products.get(index);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Label namL = Factory.label("Name",0,0);
        TextField name = Factory.textField("name",0,1,100);
        name.setText(product.getName());

        Label priceL = Factory.label("Price",1,0);
        TextField price = Factory.textField("price",1,1,100);
        price.setText(product.getPrice()+"");

        Label numL = Factory.label("Number",1,2);
        TextField number = Factory.textField("number",1,3,100);
        number.setText(product.getNumber()+"");

        Label proL = Factory.label("Profit",2,0);
        TextField profit = Factory.textField("profit",2,1);
        profit.setText(product.getProfit()+"");

        Label bdL = Factory.label("BuyDate",3,0);
        TextField bd = Factory.textField("buy date",3,1,100);
        bd.setText(product.getBuyDate());

        Label edL = Factory.label("ExpireDate",4,0);
        TextField ed = Factory.textField("expire date",4,1);
        ed.setText(product.getExpirationDate());

        Button done = Factory.button("Done",5,1);

        done.setOnAction(e->close(stage,name,price,number,profit,bd,ed,product,products));
        stage.setOnCloseRequest(e->close(stage,name,price,number,profit,bd,ed,product,products));

        GridPane gridPane = Factory.gridPane(15,10,20,20,20,20);
        gridPane.getChildren().addAll(namL,name,priceL,price,numL,number,proL,profit,bdL,bd,edL,ed,done);

        Scene scene = new Scene(gridPane,500,300);
        stage.setScene(scene);
        stage.show();
    }

    private static void close(Stage stage,
                              TextField name,
                              TextField price,
                              TextField number,
                              TextField profit,
                              TextField bd,
                              TextField ed,
                              Product product,
                              List<Product> products) {

        product.setName(name.getText());
        product.setPrice(Float.valueOf(price.getText()));
        product.setNumber(Float.valueOf(number.getText()));
        product.setBuyDate(bd.getText());
        product.setProfit(Float.valueOf(profit.getText()));
        product.setExpirationDate(ed.getText());
        stage.close();
        if(ttt)
            SHOW.display(products);
    }
}
