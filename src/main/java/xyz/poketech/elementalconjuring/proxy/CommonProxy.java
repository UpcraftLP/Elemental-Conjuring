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
import net.minecraftforge.fml.relauncher.Side;
import xyz.poketech.elementalconjuring.ElementalConjuring;
import xyz.poketech.elementalconjuring.entities.EntitySummonedItem;
import xyz.poketech.elementalconjuring.handler.JumpHandler;
import xyz.poketech.elementalconjuring.items.ItemFireSword;
import xyz.poketech.elementalconjuring.items.ItemWaterSword;
import xyz.poketech.elementalconjuring.net.NetworkHandler;
import xyz.poketech.elementalconjuring.net.packet.PacketJump;

/**
 * Created by Poke on 2017-11-22.
 */

@Mod.EventBusSubscriber
public class CommonProxy
{

    public void preInit(FMLPreInitializationEvent e)
    {
        NetworkHandler.registerMessage(JumpHandler.class, PacketJump.class, Side.SERVER);
    }

    public void init(FMLInitializationEvent e)
    {
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
                .id(new ResourceLocation(ElementalConjuring.MODID, "summoned_item"), ID++)
                .name("summoned_item")
                .tracker(64, 20, false)
                .build();
        event.getRegistry().register(magicCircleEntry);
    }

}
