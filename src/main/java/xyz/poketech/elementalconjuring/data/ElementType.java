package xyz.poketech.elementalconjuring.data;

import xyz.poketech.elementalconjuring.util.ColorUtil;

public enum ElementType
{

    FIRE(ColorUtil.getRGB(255,0,0)),
    WATER(ColorUtil.getRGB(0,0,255)),
    EARTH(ColorUtil.getRGB(139,69,19)),
    WIND(ColorUtil.getRGB(255,255,255));

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
