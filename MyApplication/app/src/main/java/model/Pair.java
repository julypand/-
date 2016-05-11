package model;

public class Pair {
    private String first;
    private String second;

    public Pair(){}

    public Pair(String first, String second){
        this.setFirst(first);
        this.setSecond(second);
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}
