package market.objects;

public class NameData {
    private final String category;
    private final int id;
    private final String name;
    public NameData(String category, int id, String name){
        this.category = category;
        this.id = id;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getID(){
        return this.id;
    }

    public String getCategory(){
        return this.category;
    }

    @Override
    public String toString() {
        return "NameData{" +
                "category='" + category + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
