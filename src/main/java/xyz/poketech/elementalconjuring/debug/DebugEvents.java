package xyz.poketech.elementalconjuring.debug;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import xyz.poketech.elementalconjuring.ElementalConjuring;
import xyz.poketech.elementalconjuring.ModItems;
import xyz.poketech.elementalconjuring.data.ElementType;
import xyz.poketech.elementalconjuring.entities.EntitySummonedItem;

/**
 * Created by Poke on 2017-11-23.
 */
@Mod.EventBusSubscriber(modid = ElementalConjuring.MODID)
public class DebugEvents
{
    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent.RightClickBlock event)
    {
        if(event.getSide() == Side.SERVER)
        {
            Item item = event.getItemStack().getItem();
            ItemStack stack = null;
            int color = 0;
            if(item == Items.STICK)
            {
                stack = new ItemStack(ModItems.itemFireSword);
                color = ElementType.FIRE.getColor();
            }
            else if(item == Items.BONE)
            {
                stack = new ItemStack(ModItems.itemWaterSword);
                color = ElementType.WATER.getColor();
            }

            if(stack != null) {
                EntitySummonedItem entity = new EntitySummonedItem(event.getWorld(), color, stack);
                entity.setPosition(event.getHitVec().x, event.getHitVec().y, event.getHitVec().z);
                event.getWorld().spawnEntity(entity);
            }
        }
    }
}
