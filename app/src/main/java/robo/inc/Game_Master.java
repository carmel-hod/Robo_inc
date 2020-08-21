package robo.inc;

public class Game_Master {
    public int money = 0;
    public int matrials = 0;
    public int scince_points = 0;
    public int robots = 0;
    public int advertise_points = 0;

    static private Game_Master singletone = null;

    private Game_Master() {
    }
    static public Game_Master getInstance() {
        if (singletone == null){
            singletone = new Game_Master();
        }
        return singletone;
    }
}
