package model;


public class Schedule {

    private Day[] days = null; //new Day[6];

    public Schedule(){}

    public Schedule(Day[] days){
        setDays(days);
    }

    public Day[] getDays() {
        return days;
    }

    public void setDays(Day[] days) {
        //this.days = days;
        for (int i = 0; i < days.length; ++i)
            this.getDays()[i] = days[i];
    }
}
