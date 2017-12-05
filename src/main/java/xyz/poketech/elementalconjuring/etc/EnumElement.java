package xyz.poketech.elementalconjuring.etc;

public enum EnumElement
{

    FIRE(255,0,0,1),
    WATER(0,0,255,1),
    EARTH(139,69,19,1),
    WIND(255,255,255,1);

    private int r = 0,
                g = 0,
                b = 0,
                a = 0;

    EnumElement(int r, int g, int b, int a)
    {
        this.r = r;
        this.a = a;
        this.b = b;
        this.g = g;
    }

    public int getA()
    {
        return a;
    }

    public int getB()
    {
        return b;
    }

    public int getG()
    {
        return g;
    }

    public int getR()
    {
        return r;
    }
}
