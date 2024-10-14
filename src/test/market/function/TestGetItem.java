package test.market.function;

import market.objects.Item;

import static market.Functions.getPrices;

public class TestGetItem {
    public static void main(String[] args) throws Exception {
        try{
            for (Item i: getPrices()){
                System.out.println(i);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
