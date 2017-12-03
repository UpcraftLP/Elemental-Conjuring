package xyz.poketech.elementalconjuring;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Poke on 2017-11-24.
 */

@Mod.EventBusSubscriber(modid = ElementalConjuring.MODID)
public class ModSounds
{
    public static SoundEvent circle_spawn = null;

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event)
    {
        circle_spawn = registerSound(event, "circle_spawn");
    }

    private static SoundEvent registerSound(RegistryEvent.Register<SoundEvent> event, @SuppressWarnings("SameParameterValue") String soundName)
    {
        SoundEvent sound = new SoundEvent(new ResourceLocation(ElementalConjuring.MODID, soundName)).setRegistryName(soundName);
        event.getRegistry().register(sound);
        return sound;
    }
}
