package com.sztokrotki.gloskuj.game.decisions;

public class Word {

    String content;
    Boolean voiceless;
    Character voice;
    int voicePosition;
    int pairPosition;

    public Word(String content, Boolean voiceless, Character voice, int voicePosition, int pairPosition) {
        this.content = content;
        this.voiceless = voiceless;
        this.voicePosition = voicePosition;
        this.voice = voice;
        this.pairPosition = pairPosition;
    }

    public String toString() {
        return this.content;
    }
}
