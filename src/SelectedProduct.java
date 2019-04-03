public class SelectedProduct{
    public static final String PROFIT = "profit";
    static final String NAME = "name",PRICE = "price",NUM = "number",COST = "cost";

    private String name;
    private float price, number, cost,profit;
    Product product;

    public SelectedProduct(){

    }
    public SelectedProduct init(Product product,float number){
        name = product.getName();
        price = product.getPrice();
        this.number= number;
        cost = number * price;
        profit = number * product.getProfit();
        this.product = product;
        return this;
    }

    public float getNumber() {
        return number;
    }
    public String getName(){
        return name;
    }
    public float getPrice(){
        return price;
    }
    public float getCost(){
        return cost;
    }
    public float getProfit(){
        return profit;
    }
}