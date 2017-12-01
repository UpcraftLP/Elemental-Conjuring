package xyz.poketech.elementalconjuring.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import xyz.poketech.elementalconjuring.ElementalConjuring;
import xyz.poketech.elementalconjuring.entities.EntityFiveLayeredCircle;
import xyz.poketech.elementalconjuring.entities.EntityMagicCircle;
import xyz.poketech.elementalconjuring.items.ItemFireSword;

/**
 * Created by Poke on 2017-11-22.
 */

@Mod.EventBusSubscriber
public class CommonProxy
{

    public void preInit(FMLPreInitializationEvent e)
    {


    }

    public void init(FMLInitializationEvent e)
    {
        EntityRegistry.registerModEntity(
                new ResourceLocation(ElementalConjuring.MODID,"rune"),
                EntityMagicCircle.class,
                "Rune",0, ElementalConjuring.instance,80,1,false
        );

        EntityRegistry.registerModEntity(
                new ResourceLocation(ElementalConjuring.MODID,"eye"),
                EntityMagicCircle.class,
                "Eye",1, ElementalConjuring.instance,80,1,false
        );

        EntityRegistry.registerModEntity(
                new ResourceLocation(ElementalConjuring.MODID,"five_layer"),
                EntityFiveLayeredCircle.class,
                "FiveLayerMagicCircle",2, ElementalConjuring.instance,80,1,false
        );
    }

    public void postInit(FMLPostInitializationEvent e)
    {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemFireSword(Item.ToolMaterial.DIAMOND)); //TODO: set material in constructor directly
    }

}
