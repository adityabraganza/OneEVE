package market.objects;

import java.io.IOException;

public class Item {
    private final String category;
    private final int id;
    private final String name;
    private double adjusted_price = 0;
    private double average_price = 0;

    public Item(ItemPrice itemPrice, NameData nameData) throws IOException {
        if (itemPrice.getId() == nameData.getID()){
            this.id = itemPrice.getId();
        } else{
            throw new IOException("The ID in the ItemPrice object does not match the ID in the NameData object");
        }
        this.category = nameData.getCategory();
        this.name = nameData.getName();
        this.adjusted_price = itemPrice.getAdjustedPrice();
        this.average_price = itemPrice.getAveragePrice();
    }

    public int getId(){
        return this.id;
    }

    public String getCategory(){
        return this.category;
    }

    public String getName(){
        return this.name;
    }

    public double getAdjustedPrice(){
        return this.adjusted_price;
    }

    public double getAveragePrice(){
        return this.average_price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "category='" + category + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", adjusted_price=" + adjusted_price +
                ", average_price=" + average_price +
                '}';
    }
}
