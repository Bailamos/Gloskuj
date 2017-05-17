package com.sztokrotki.gloskuj.game.View;


public class MenuGameItem {
    public String gameName;
    public Class<?> activity;
    public MenuGameItem(){

    }
    public MenuGameItem(String gameName,  Class<?> activity){
        this.gameName = gameName;
        this.activity = activity;
    }
}
