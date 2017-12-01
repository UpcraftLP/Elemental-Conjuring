package xyz.poketech.elementalconjuring;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.poketech.elementalconjuring.items.ItemFireSword;

/**
 * Created by Antoine Gagnon (Poke1650) on 2017-11-30.
 */
public class ModItems
{

    @GameRegistry.ObjectHolder("elemental_conjuring:fire_sword")
    public static ItemFireSword itemFireSword;

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        itemFireSword.initModel();
    }
}
