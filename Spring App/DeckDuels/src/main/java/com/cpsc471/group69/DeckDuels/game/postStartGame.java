package com.cpsc471.group69.DeckDuels.game;

public class postStartGame {

    private String Usernamep1;
    private String p1h;
    private String p1b;
    private String Usernamep2;
    private String p2h;
    private String p2b;

    public postStartGame(String usernamep1, String p1h, String p1b, String usernamep2, String p2h, String p2b) {
        Usernamep1 = usernamep1;
        this.p1h = p1h;
        this.p1b = p1b;
        Usernamep2 = usernamep2;
        this.p2h = p2h;
        this.p2b = p2b;
    }

    public String getUsernamep1() {
        return Usernamep1;
    }

    public String getP1h() {
        return p1h;
    }

    public String getP1b() {
        return p1b;
    }

    public String getUsernamep2() {
        return Usernamep2;
    }

    public String getP2h() {
        return p2h;
    }

    public String getP2b() {
        return p2b;
    }
}
