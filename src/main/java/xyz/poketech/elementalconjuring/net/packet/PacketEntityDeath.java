package xyz.poketech.elementalconjuring.net.packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import xyz.poketech.elementalconjuring.entities.ICustomDeathEntity;

/**
 * @author UpcraftLP
 */
public class PacketEntityDeath implements IMessage {

    public int entityID;

    @SuppressWarnings("unused")
    public PacketEntityDeath() {
        //NO-OP
    }

    public PacketEntityDeath(ICustomDeathEntity entity) {
        this.entityID = entity.getID();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityID);
    }
}
