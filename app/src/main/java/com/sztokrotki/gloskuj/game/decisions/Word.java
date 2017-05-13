package com.sztokrotki.gloskuj.game.decisions;

public class Word {

    String content;
    Boolean voiceless;
    int voicePosition;

    public Word(String content, Boolean voiceless, int voicePosition) {
        this.content = content;
        this.voiceless = voiceless;
        this.voicePosition = voicePosition;
    }

    public String toString() {
        return this.content;
    }
}
