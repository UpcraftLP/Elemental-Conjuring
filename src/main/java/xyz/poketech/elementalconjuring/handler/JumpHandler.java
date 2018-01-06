package xyz.poketech.elementalconjuring.handler;

import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.poketech.elementalconjuring.ElementalConjuring;
import xyz.poketech.elementalconjuring.entities.EntitySummonedItem;
import xyz.poketech.elementalconjuring.net.packet.PacketJump;

import java.awt.*;
import java.util.Map;
import java.util.UUID;

/**
 * @author UpcraftLP
 */
@Mod.EventBusSubscriber(modid = ElementalConjuring.MODID)
public class JumpHandler implements IMessageHandler<PacketJump, IMessage> {

    private static final Map<UUID, Long> JUMP_PLAYERS = Maps.newHashMap();
    private static final Map<UUID, Boolean> SIDES = Maps.newHashMap();
    public static final long JUMP_DELAY = 300L;
    public static final int COLOR = new Color(0x29535D).getRGB();

    @SuppressWarnings("unused")
    public JumpHandler() {
        //NO-OP
    }

    @Override
    public IMessage onMessage(PacketJump message, MessageContext ctx) {
        synchronized (JUMP_PLAYERS) {
            EntityPlayer player = ctx.getServerHandler().player;
            if(!player.isCreative()) {
                UUID playerUUID = player.getUniqueID();
                long current = System.nanoTime() / 1000L;
                long lastJump = JUMP_PLAYERS.getOrDefault(playerUUID, 0L);
                if(current - lastJump > JUMP_DELAY) {  //TODO check if player has capability
                    if(!player.onGround) {
                        //TODO wall jumps should bounce off the wall instead of just this
                        Vec3d lookVec = player.getLookVec();
                        int toggle = SIDES.getOrDefault(playerUUID, false) ? 1 : -1;
                        player.motionX += lookVec.z * 0.3F * toggle;
                        player.motionZ += lookVec.x * 0.3F * toggle;
                        SIDES.put(playerUUID, toggle == -1);

                        EntitySummonedItem entity = new EntitySummonedItem(player.world, COLOR);
                        entity.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch); //FIXME orient circle properly!
                        entity.lifeSpan = 150;
                        entity.setNoPickup(true);
                        entity.ticksExisted = EntitySummonedItem.SPAWN_ANIMATION_TIME * 3;
                        player.world.spawnEntity(entity);

                        float f1 = player.rotationYaw * 0.017453292F;
                        player.motionY += 0.42F * 2;
                        player.motionX -= (double)(MathHelper.sin(f1) * 0.4F);
                        player.motionZ += (double)(MathHelper.cos(f1) * 0.4F);

                        player.fallDistance = 0;
                        player.velocityChanged = true;
                    }
                }
                JUMP_PLAYERS.put(playerUUID, current);
            }
        }
        return null; //we don't need to return a message to the client
    }
}
