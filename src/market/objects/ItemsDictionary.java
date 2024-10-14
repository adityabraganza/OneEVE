package market.objects;

import java.util.Dictionary;
import java.util.Hashtable;

public class ItemsDictionary {
    private final Dictionary<String, Item> returnDictionary = new Hashtable<>();
    public ItemsDictionary(Item[] items){
        for (Item item:items){
            this.returnDictionary.put(String.valueOf(item.getName()), item);
        }
    }

    public Item getItemById(String id){
        return this.returnDictionary.get(id);
    }

    public boolean isEmpty(){
        return this.returnDictionary.isEmpty();
    }

    @Override
    public String toString() {
        return this.returnDictionary.toString();
    }
}
