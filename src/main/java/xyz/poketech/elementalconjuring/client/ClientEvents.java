package xyz.poketech.elementalconjuring.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.poketech.elementalconjuring.ElementalConjuring;
import xyz.poketech.elementalconjuring.net.NetworkHandler;
import xyz.poketech.elementalconjuring.net.packet.PacketJump;

/**
 * @author UpcraftLP
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ElementalConjuring.MODID, value = {Side.CLIENT})
public class ClientEvents {

    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public static void onPlayerPressSpace(InputEvent.KeyInputEvent event) {
        if(mc.player == null) return;
        if(mc.gameSettings.keyBindJump.isPressed() && mc.player.isAirBorne) {
            NetworkHandler.INSTANCE.sendToServer(new PacketJump());
        }
    }

}
