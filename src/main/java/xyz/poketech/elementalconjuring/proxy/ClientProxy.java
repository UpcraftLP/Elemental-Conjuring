package xyz.poketech.elementalconjuring.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import xyz.poketech.elementalconjuring.ModItems;
import xyz.poketech.elementalconjuring.client.render.EntityFiveLayeredCircleRender;
import xyz.poketech.elementalconjuring.client.render.EntityMagicCircleRender;
import xyz.poketech.elementalconjuring.entities.EntityFiveLayeredCircle;
import xyz.poketech.elementalconjuring.entities.EntityMagicCircle;

/**
 * Created by Poke on 2017-11-22.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy
{

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicCircle.class, EntityMagicCircleRender::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFiveLayeredCircle.class, EntityFiveLayeredCircleRender::new);
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        super.init(e);
        
    }

    @Override
    public void postInit(FMLPostInitializationEvent e)
    {
        super.postInit(e);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        ModItems.initModels();
    }

}
