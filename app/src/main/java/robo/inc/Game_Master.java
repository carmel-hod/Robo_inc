package robo.inc;

public class Game_Master {
    public int money = 0;
    public int matrials = 0;
    public int scince_points = 0;
    public int robots = 0;
    public int advertise_points = 0;
    public int gathering_rate = 1;
    public int reserch_rate = 1;
    public int manufacture_amount = 1;
    public int manufacture_cost = 2;

    static private Game_Master singletone = null;

    private Game_Master() {
    }
    static public Game_Master getInstance() {
        if (singletone == null){
            singletone = new Game_Master();
        }
        return singletone;
    }
    public boolean gatherMatrials() {
        matrials+=gathering_rate;
        return true;
    }

    public boolean makeRobot() {
        if (matrials < manufacture_cost){
            return false;
        }
        matrials-=manufacture_cost;
        robots+=manufacture_amount;
        return true;
    }
    public boolean advertise() {
        advertise_points++;
        return true;
    }
    public boolean reserch() {
        scince_points+=reserch_rate;
        return true;
    }
}