package xyz.poketech.elementalconjuring;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.poketech.elementalconjuring.entities.EntityFiveLayeredCircle;
import xyz.poketech.elementalconjuring.entities.EntityMagicCircle;

/**
 * Created by Poke on 2017-11-23.
 */
@Mod.EventBusSubscriber
public class DebugEvents
{
    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.RightClickBlock e)
    {
        if(!e.getWorld().isRemote)
        {
            Entity entity = null;
            if(e.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() ==  Items.STICK)
            {
                entity = new EntityMagicCircle(e.getWorld(), 2);
                entity.setPosition(e.getHitVec().x,e.getHitVec().y,e.getHitVec().z);
                e.getWorld().spawnEntity(entity);
            }
            else if(e.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() ==  Items.BONE)
            {
                entity = new EntityMagicCircle(e.getWorld(), 1);
                entity.setPosition(e.getHitVec().x,e.getHitVec().y,e.getHitVec().z);
                e.getWorld().spawnEntity(entity);
            }
        }
    }
}
