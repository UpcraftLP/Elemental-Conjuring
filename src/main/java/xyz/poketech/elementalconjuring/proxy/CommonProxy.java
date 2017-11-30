package xyz.poketech.elementalconjuring.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
import xyz.poketech.elementalconjuring.entities.EntityRune;

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
                EntityRune.class,
                "Rune",0, ElementalConjuring.instance,80,1,false
        );

        EntityRegistry.registerModEntity(
                new ResourceLocation(ElementalConjuring.MODID,"eye"),
                EntityRune.class,
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
    }

}
