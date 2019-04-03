
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ADD{
    private static Product p=null;
    private static final int width=500,height = 300;

    static Product display(){
        Stage stage = new Stage();
        stage.getIcons().addAll(new Image("assets\\addGoods.png"));
        stage.setTitle("Add Product");
        stage.setOnCloseRequest(e->{
            e.consume();
            if(ConfirmBox.display("Exit","Sure to close?"))
            stage.close();
        });
        stage.initModality(Modality.APPLICATION_MODAL);

        Label nameL= Factory.label("Name: ",0,0);
        TextField name = Factory.textField("name",0,1,100);

        Label priceL = Factory.label("Price: ",1,0);
        TextField price = Factory.textField("price",1,1,100);


        Label numL = label("Number: ",1,2);
        TextField num = textField("number",1,3,70);

        Label profL = label("Profit: ",2,0);
        TextField profit = textField("profit",2,1,50);

        Label buyDateL = label("BuyDate: ",3,0);
        TextField byear = textield("year",3,1,100),
                bmonth = textField("month",3,2,100),
                bday = textField("day",3,3,100);

        Label exDateL = label("ExpireDate: ",4,0);
        TextField eyear = textField("year",4,1,100),
                emonth = textField("month",4,2,100),
                eday = textField("day",4,3,100);

        Button done = button("DONE",5,2);
        done.setOnAction(e->{
            String number = num.getText();
            String na = name.getText();
            String pr = price.getText();
            String bd=  (byear.getText().length()==2?"13"+byear.getText():byear.getText())
                    +   Product.DATE_SEPRATOR
                    +   (bmonth.getText().length()==1?"0"+bmonth.getText():bmonth.getText())
                    +   Product.DATE_SEPRATOR
                    +   (bday.getText().length()==1?"0"+bday.getText():bday.getText());

            String ex = (eyear.getText().length()==2?"13"+eyear.getText():eyear.getText())
                    +   Product.DATE_SEPRATOR
                    +   (emonth.getText().length()==1?"0"+emonth.getText():emonth.getText())
                    +   Product.DATE_SEPRATOR
                    +   (eday.getText().length()==1?"0"+eday.getText():eday.getText());
            boolean retrn = false;

            retrn = isRetrn(numL, number, retrn);
            retrn = isRetrn(priceL, pr, retrn);

            if(na.length() == 0) {
                changeColor(nameL, Color.RED);
                retrn = true;
            }
            else changeColor(nameL,Color.BLACK);

            if(bd.length() == 0 || !Product.Checker.CHECKDATE(bd)) {
                changeColor(buyDateL, Color.RED);
                retrn = true;
            }
            else changeColor(buyDateL,Color.BLACK);

            if(ex.length() == 0 || !Product.Checker.CHECKDATE(ex)) {
                changeColor(exDateL, Color.RED);
                retrn = true;
            }
            else changeColor(exDateL,Color.BLACK);

            if(retrn)
                return;

            // todo
            p = new Product(na,Integer.valueOf(pr),Integer.valueOf(number),bd,ex);
            close(stage);
        });

        GridPane gridPane = gridPane(15,10,20,20,20,20);
        gridPane.getChildren().addAll(nameL,name,priceL,price,numL,num,profL,profit,buyDateL,byear,bmonth,bday,exDateL,eyear,emonth,eday,done);

        Scene scene = new Scene(gridPane,width,height);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        return p;
    }

    private static boolean isRetrn(Label priceL, String pr, boolean retrn) {
        try {
            int t = Integer.valueOf(pr);
            changeColor(priceL, Color.BLACK);
        }catch (Exception ee){
            changeColor(priceL,Color.RED);
            retrn = true;
        }
        return retrn;
    }

    private static TextField textield(String year, int i, int i1, int i2) {
        return Factory.textField(year,i,i1,i2);
    }

    private static Button button(String done, int i, int i1) {
        return Factory.button(done,i,i1);
    }

    private static GridPane gridPane(int i, int i1, int i2, int i3, int i4, int i5) {
        return Factory.gridPane(i,i1,i2,i3,i4,i5);
    }

    private static void changeColor(Label l,Color c){
        Factory.changeColor(l,c);
    }
    private static TextField textField(String month, int i, int i1, int i2) {
        return Factory.textField(month,i,i1,i2);
    }

    private static Label label(String s, int i, int i1) {
        return Factory.label(s,i,i1);
    }

    private static void close(Stage stage){
        boolean is = ConfirmBox.display("Exit","Are sure to want to save?");
        if(is)
            stage.close();
    }

}
