package xyz.poketech.elementalconjuring.entities;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author UpcraftLP
 */
public interface ICustomDeathEntity {

    public int getID();

    @SideOnly(Side.CLIENT)
    public void setShouldDie();
}
