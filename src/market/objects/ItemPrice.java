package market.objects;

public class ItemPrice {
    private final double adjusted_price;
    private final double average_price;
    private final int type_id;
    public ItemPrice(int type_id, double adjusted_price, double average_price) {
        this.type_id = type_id;
        this.adjusted_price = adjusted_price;
        this.average_price = average_price;
    }

    public double getAdjustedPrice(){
        return this.adjusted_price;
    }

    public double getAveragePrice(){
        return this.average_price;
    }

    public int getId(){
        return this.type_id;
    }

    @Override
    public String toString() {
        return "ItemPrice{" +
                "adjusted_price=" + adjusted_price +
                ", average_price=" + average_price +
                ", type_id=" + type_id +
                '}';
    }
}
