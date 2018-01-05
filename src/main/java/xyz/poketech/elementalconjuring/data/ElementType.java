package xyz.poketech.elementalconjuring.data;

import java.awt.*;

public enum ElementType
{

    FIRE(new Color(255,0,0).getRGB()),
    WATER(new Color(0,0,255).getRGB()),
    EARTH(new Color(139,69,19).getRGB()),
    WIND(new Color(255,255,255).getRGB());

    private int color;

    ElementType(int color)
    {
        this.color = color;
    }

    public int getColor()
    {
        return color;
    }
}
