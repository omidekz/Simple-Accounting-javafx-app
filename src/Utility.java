import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class Utility {
    public static TableColumn<Product,String> productColumn(String headerName,String PROPERT_NAME,int minWIDTH){
        TableColumn<Product,String> t = new TableColumn<>(headerName);
        t.setCellValueFactory(new PropertyValueFactory<>(PROPERT_NAME));
        t.setMinWidth(minWIDTH);
        return t;
    }

    public static TableColumn<SelectedProduct,String> selectedProductColumn(String h,String pn,int wid){
        TableColumn<SelectedProduct,String> t = new TableColumn<>(h);
        t.setMinWidth(wid);
        t.setCellValueFactory(new PropertyValueFactory<>(pn));
        return t;
    }
}
