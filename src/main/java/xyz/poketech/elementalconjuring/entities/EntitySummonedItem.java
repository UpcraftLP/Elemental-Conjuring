package xyz.poketech.elementalconjuring.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * Created by Poke on 2017-11-22.
 */
public class EntitySummonedItem extends Entity
{
    private static final DataParameter<ItemStack> SUMMONED_ITEM = EntityDataManager.<ItemStack>createKey(EntitySummonedItem.class, DataSerializers.ITEM_STACK);

    public EntitySummonedItem(World world)
    {
        super(world);
    }

    public EntitySummonedItem(World world, ItemStack itemStack)
    {
        super(world);
        this.setSummonedItem(itemStack);
    }

    @Override
    protected void entityInit()
    {
        this.dataManager.register(SUMMONED_ITEM, ItemStack.EMPTY);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        this.setSummonedItem(new ItemStack(compound));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        this.getSummonedItem().writeToNBT(compound);
    }

    public void setSummonedItem(ItemStack itemStack)
    {
        this.dataManager.set(SUMMONED_ITEM, itemStack);
    }

    public ItemStack getSummonedItem()
    {
        return this.dataManager.get(SUMMONED_ITEM);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return super.getRenderBoundingBox();
    }

    @Override
    public AxisAlignedBB getEntityBoundingBox()
    {
        return super.getEntityBoundingBox();
    }
}
