package xyz.poketech.elementalconjuring.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * Created by Poke on 2017-11-22.
 */
public class EntitySummonedItem extends Entity
{
    private static final DataParameter<ItemStack> SUMMONED_ITEM = EntityDataManager.<ItemStack>createKey(EntitySummonedItem.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Integer> ELEMENT = EntityDataManager.<Integer>createKey(EntitySummonedItem.class, DataSerializers.VARINT);

    public EntitySummonedItem(World world)
    {
        super(world);
    }

    public EntitySummonedItem(World world, int element, ItemStack itemStack)
    {
        super(world);
        this.setSummonedItem(itemStack);
        this.setElement(element);
    }

    @Override
    protected void entityInit()
    {
        this.dataManager.register(SUMMONED_ITEM, ItemStack.EMPTY);
        this.dataManager.register(ELEMENT,0);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        this.setSummonedItem(new ItemStack(compound.getCompoundTag("summoned_item")));
        this.setElement(compound.getInteger("element"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        NBTTagCompound itemTag = new NBTTagCompound();
        this.getSummonedItem().writeToNBT(itemTag);
        compound.setTag("summoned_item", itemTag);
        compound.setInteger("element", this.getElement());
    }

    public void setSummonedItem(ItemStack itemStack)
    {
        this.dataManager.set(SUMMONED_ITEM, itemStack);
    }

    public ItemStack getSummonedItem()
    {
        return this.dataManager.get(SUMMONED_ITEM);
    }

    public void setElement(int element)
    {
        this.dataManager.set(ELEMENT,element);
    }

    public int getElement()
    {
        return this.dataManager.get(ELEMENT);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return super.getRenderBoundingBox();
    }
}
