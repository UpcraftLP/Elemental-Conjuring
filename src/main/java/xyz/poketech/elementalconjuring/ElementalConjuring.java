package xyz.poketech.elementalconjuring;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import xyz.poketech.elementalconjuring.proxy.CommonProxy;

/**
 * Created by Poke on 2017-11-21.
 */
@Mod.EventBusSubscriber
@Mod(modid = ElementalConjuring.MODID, version = ElementalConjuring.VERSION)
public class ElementalConjuring
{
    public static final String MODID = "elemental_conjuring";
    public static final String VERSION = "1.0";

    @Mod.Instance
    public static ElementalConjuring instance;

    public static Logger logger;

    @SidedProxy(clientSide = "xyz.poketech.elementalconjuring.proxy.ClientProxy", serverSide = "xyz.poketech.elementalconjuring.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        proxy.postInit(e);
    }

}

