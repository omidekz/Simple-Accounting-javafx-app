import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main_2 extends Application {
    private VBox mainMenu;
    private HBox container;
    private List<Product> products = new ArrayList<>();
    private List<Factor> factors   = new ArrayList<>();
    private MyLabel[] list;
    private int menuWidth = 140;
    private int numberOfItem = 5;
    private Stage window;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        DataBase db = new DataBase(products,factors);
        db.fread();
        db.read();

        stage.setOnCloseRequest(e->{
            try {
                db.save();
                db.fsave();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        window = stage;
        window.getIcons().addAll(new Image("assets\\icon.png"));
        initLeftlayout();
        list[0].requestFocus();


        window.setScene(new Scene(container,1000,400));
        window.show();
    }

    private void initLeftlayout() {
        int cc = -1;

        list = new MyLabel[numberOfItem];
        list[++cc] = new MyLabel("Home");
        list[cc].setGraphic(new ImageView("assets\\Home.png"));
        list[++cc] = new MyLabel("Add");
        list[cc].setGraphic(new ImageView("assets\\add2.png"));
        list[++cc] = new MyLabel("Goods");
        list[cc].setGraphic(new ImageView("assets\\store2.png"));
        list[++cc] = new MyLabel("New Factor");
        list[cc].setGraphic(new ImageView("assets\\addFactor2.png"));
        list[++cc] = new MyLabel("Factors");
        list[cc].setGraphic(new ImageView(new Image("assets\\docs.png")));

        final int minWidth = 22;
        final int height = 25;

        for (MyLabel aLabel : list) {
            aLabel.setPrefSize(menuWidth, height);
            aLabel.setMinSize(minWidth, height);

        }

        HomeClick();
        AddClick();
        GoodsClick();
        NewFactorClick();
        FactorsClick();

        mainMenu = new VBox();
        mainMenu.setMinWidth(minWidth);
        mainMenu.setMaxWidth(menuWidth);
        mainMenu.setMinHeight(list.length * height);
        mainMenu.getChildren().addAll(list);
        mainMenu.getStylesheets().addAll("mainMenu.css");
        mainMenu.getStyleClass().add("root");

        container = new HBox();
        container.getChildren().addAll(mainMenu);
        container.getStylesheets().addAll("container.css");
    }

    private void setFloat(TextField tf) {
        tf.textProperty().addListener((observableValue, old, nv) -> {
            if(!nv.matches("[0-9]*(.[0-9]*)?"))
                tf.setText(old);
        });
    }

    private void HomeClick(){
        list[0].setOnMouseClicked(e->{
            fixContainer();
            focusing(0);
        });
    }

    private void AddClick() {
        list[1].setOnMouseClicked(e->{
            fixContainer();
            focusing(1);

            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(20,20,20,20));
            gridPane.setVgap(10);
            gridPane.setHgap(5);

            //name label
            Label nameL = new Label("Name:");
            GridPane.setConstraints(nameL,0,0);
            //textfield
            TextField name = new TextField();
            name.setPromptText("name");
            GridPane.setConstraints(name,1,0);

            //price label
            Label priceL = new Label("Price: ");
            GridPane.setConstraints(priceL,2,0);
            //price textfield
            TextField price = new TextField();
            price.setPromptText("price");
            GridPane.setConstraints(price,3,0);
            setFloat(price);

            //number label
            Label numberL = new Label("Number: ");
            GridPane.setConstraints(numberL,0,1);
            //number text field
            TextField number = new TextField();
            number.setPromptText("number");
            GridPane.setConstraints(number,1,1);
            setFloat(number);

            //profit label
            Label profitL = new Label("Profit: ");
            GridPane.setConstraints(profitL,2,1);
            //profit textfield
            TextField profit = new TextField();
            profit.setPromptText("profit");
            GridPane.setConstraints(profit,3,1);
            setFloat(profit);


            //buy date label
            Label bdL = new Label("BuyDate: ");
            GridPane.setConstraints(bdL,0,5);
            //buy date text field
            TextField bd = new TextField();
            bd.setPromptText("buy date");
            GridPane.setConstraints(bd,1,5);

            //ex date label
            Label exL = new Label("ExpireDate: ");
            GridPane.setConstraints(exL,0,6);
            //
            TextField ex = new TextField();
            ex.setPromptText("expire date");
            GridPane.setConstraints(ex,1,6);

            //add btn
            Button add = new Button("ADD");
            GridPane.setConstraints(add,4,4);
            add.setMinWidth(100);

            add.setOnAction(action->{
                String na = name.getText();
                float pr = Float.valueOf(price.getText());
                float num = Float.valueOf(number.getText());
                float pro = Float.valueOf(profit.getText());
                String bud = bd.getText();
                String ed = ex.getText();
                products.add(new Product(na,pr,num,pro,bud,ed));
                name.clear();
                price.clear();
                number.clear();
                profit.clear();
                bd.clear();
                ex.clear();
            });

            //clr btn
            Button clr = new Button("CLEAR");
            GridPane.setConstraints(clr,4,5);
            clr.setMinWidth(100);

            gridPane.getChildren().addAll(nameL,name,priceL,price,numberL,number,profitL,profit,add,bdL,bd,clr,exL,ex);
            container.getChildren().addAll(gridPane);

        });
    }

    private void GoodsClick() {
        list[2].setOnMouseClicked(e->{
            fixContainer();
            focusing(2);

            VBox pro = SHOW.getLayout(products,false);
            container.getChildren().addAll(pro);

        });
    }

    private void NewFactorClick() {
        list[3].setOnMouseClicked(e->{
            fixContainer();
            focusing(3);


            Label cost = new Label();
            Label profit = new Label();

            Factor factor = new Factor();
            //selection table
            TableView<Product> productTable = new TableView<>();
            String nameH = "Name",numberH = "Number",priceH = "Price";
            TableColumn<Product,String>
                    nameCol  = Utility.productColumn(nameH,Product.NAME,70),
                    numberCol= Utility.productColumn(numberH,Product.NUM,30),
                    priceCol = Utility.productColumn(priceH,Product.PRICE,50);
            productTable.getColumns().addAll(nameCol,numberCol,priceCol);
            productTable.setItems(FXCollections.observableList(products));
            productTable.setRowFactory(productTableView -> {
                TableRow<Product> tv = new TableRow<>();
                tv.setOnMouseClicked(event -> {
                    if(event.getClickCount()==2 && !tv.isEmpty()){
                        System.out.println("Clicked");
                    }
                });
                return tv;
            });

            //selected table view
            TableView<SelectedProduct> selectedTV = new TableView<>();
            TableColumn<SelectedProduct,String>
                    nameSelCol = Utility.selectedProductColumn(nameH,Product.NAME,70),
                    numSCol    = Utility.selectedProductColumn(numberH,Product.NUM,30);
            selectedTV.getColumns().addAll(nameSelCol,numSCol);

            final int propertyWidth = 50;
            //check box
            CheckBox ignoreNumber = new CheckBox();
            ignoreNumber.setText("Ignore Number");
            //selectoin property
            TextField numberTf = new TextField();
            numberTf.setPromptText("number");
            numberTf.setPadding(new Insets(3,3,3,0));
            numberTf.setMaxWidth(propertyWidth);
            //add btn
            Button addBtn = new Button(">>");
            addBtn.setMaxWidth(propertyWidth);
            addBtn.setOnMouseClicked(ee->{
                if(numberTf.getText().trim().equals(""))
                    return;
                if(productTable.getSelectionModel().getSelectedIndex()==-1)
                    return;
                int index = productTable.getSelectionModel().getSelectedIndex();
                float number = Float.valueOf(numberTf.getText());
                boolean isAdd = ignoreNumber.isSelected();
                if(factor.add(products.get(index),number,isAdd))
                    selectedTV.getItems().add(new SelectedProduct().init(products.get(index),number));
                selectedTV.refresh();
                numberTf.clear();
                productTable.getSelectionModel().clearSelection();
                cost.setText(factor.getCost()+"");
                profit.setText(factor.getSud()+"");
            });
            //del btn
            Button del = new Button("<<");
            del.setOnMouseClicked(ee->{
                SelectedProduct tmp = selectedTV.getSelectionModel().getSelectedItem();
                if(tmp==null)
                    return;
                factor.remove(tmp.product,tmp.getNumber());
                tmp.product.setNumber(tmp.product.getNumber()+tmp.getNumber());
                cost.setText(factor.getCost()+"");
                profit.setText(factor.getSud()+"");
                selectedTV.getItems().remove(tmp);
                selectedTV.refresh();
                productTable.refresh();
            });
            del.setMaxWidth(propertyWidth);
            //selection propert vbox
            VBox selectionPropertyVbox = new VBox();
            selectionPropertyVbox.setAlignment(Pos.CENTER);
            selectionPropertyVbox.setPadding(new Insets(10,10,10,10));
            selectionPropertyVbox.setSpacing(15);
            selectionPropertyVbox.getChildren().addAll(numberTf,ignoreNumber,addBtn,del);

            VBox costVbox = new VBox(cost,profit);
            costVbox.setAlignment(Pos.CENTER);
            costVbox.setPadding(new Insets(20));

            HBox selectionHbox = new HBox();
            selectionHbox.setPadding(new Insets(10,10,10,10));
            selectionHbox.getChildren().addAll(
                    productTable,
                    selectionPropertyVbox,
                    selectedTV,
                    costVbox);

            Button done = new Button("Done");
            done.setOnMouseClicked(ee->{
                factors.add(factor);
                fixContainer();
                focusing(0);
            });

            selectionPropertyVbox.getChildren().add(done);

            selectionHbox.setAlignment(Pos.CENTER);

            container.getChildren().addAll(selectionHbox);
        });
    }

    private void FactorsClick() {
        list[4].setOnMouseClicked(e->{
            fixContainer();
            focusing(4);
            float costs = 0,profits = 0;
            for (int i = 0; i < factors.size(); i++) {
                costs+=factors.get(i).getCost();
                profits+=factors.get(i).getSud();
            }

            Label cost = new Label("Cost : "+ costs);
            Label profit = new Label("Profit "+ profits);

            cost.setMinSize(100,100);
            profit.setMinSize(100,100);

            HBox l = new HBox();
            l.setAlignment(Pos.CENTER);
            l.setPadding(new Insets(0,container.getWidth()/4,0,container.getWidth()/4));
            l.setSpacing(100);
            l.getChildren().addAll(cost,profit);

            container.getChildren().addAll(l);

        });
    }

    private void focusing(int j) {
        for (int i = 0; i < list.length; i++) {
            list[i].deRequestFocus();
        }
        list[j].requestFocus();
    }

    private void fixContainer(){
        if(container == null)
            return;
        int len = container.getChildren().size();
        while (len>1)
            container.getChildren().remove(--len);
    }
}
