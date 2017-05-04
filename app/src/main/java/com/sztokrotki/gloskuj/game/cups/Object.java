package com.sztokrotki.gloskuj.game.cups;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Klasa abstrakcyjna.
 * Wykoszystywana w klasach Sciana, Poziom i Harold
 * @author Krystian Szutowicz
 */
public abstract class Object {

    /** Wspolrzedna polozenia X. */
    protected int x;
    /** Wspolrzedna polozenia Y. */
    protected int y;
    /** Zmiana polozenia wzgledem osi Y. */
    protected int dy;
    /** Szerokosc. */
    protected int width;
    /** Wysokosc. */
    protected int height;

    protected Bitmap image;


    public int getX(){return x;  }

    public int getY(){return y;  }

    public int getDy(){return dy;  }

    public int getWidth(){return width;  }

    public int getHeight(){return height;  }

    public Rect getRect(){return new Rect(x, y, x+width, y+height);  }
}