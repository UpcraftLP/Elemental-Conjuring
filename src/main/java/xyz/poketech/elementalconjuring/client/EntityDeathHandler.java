package xyz.poketech.elementalconjuring.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.poketech.elementalconjuring.entities.ICustomDeathEntity;
import xyz.poketech.elementalconjuring.net.packet.PacketEntityDeath;

/**
 * @author UpcraftLP
 */
public class EntityDeathHandler implements IMessageHandler<PacketEntityDeath, IMessage> {

    @Override
    public IMessage onMessage(PacketEntityDeath message, MessageContext ctx) {
        Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.entityID);
        if(entity instanceof ICustomDeathEntity) ((ICustomDeathEntity) entity).setShouldDie();
        return null;
    }
}
