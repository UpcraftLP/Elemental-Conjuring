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
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import xyz.poketech.elementalconjuring.ElementalConjuring;
import xyz.poketech.elementalconjuring.entities.EntitySummonedItem;
import xyz.poketech.elementalconjuring.items.ItemFireSword;
import xyz.poketech.elementalconjuring.items.ItemWaterSword;

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
                new ResourceLocation(ElementalConjuring.MODID, "summoned_item"),
                EntitySummonedItem.class,
                "Rune", 0, ElementalConjuring.instance, 80, 1, true
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
        event.getRegistry().register(new ItemWaterSword(Item.ToolMaterial.DIAMOND)); //TODO: set material in constructor directly
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
    {
        int ID = 0;
        EntityEntry magicCircleEntry = EntityEntryBuilder.create()
                .entity(EntitySummonedItem.class)
                .id(new ResourceLocation(ElementalConjuring.MODID, "rune"), ID++)
                .name("rune")
                .tracker(64, 20, false)
                .build();
        event.getRegistry().register(magicCircleEntry);
    }

}
