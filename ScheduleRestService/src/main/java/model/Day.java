package model;


public class Day {

    private String[] classes = null;

    public Day(){}

    public Day(String[] classes){
        setClasses(classes);
    }

    public String[] getClasses() {
        return classes;
    }

    public void setClasses(String[] classes) {
        //this.classes = classes;
        for (int i = 0; i < classes.length; ++i)
            this.getClasses()[i] = classes[i];
    }
}
