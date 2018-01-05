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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Poke on 2017-11-22.
 */
public class EntitySummonedItem extends Entity
{
    private static final DataParameter<ItemStack> SUMMONED_ITEM = EntityDataManager.<ItemStack>createKey(EntitySummonedItem.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntitySummonedItem.class, DataSerializers.VARINT);

    private int pickupDelay;
    public int lifeSpan = 1200;

    public EntitySummonedItem(World world)
    {
        super(world);
        this.setDefaultPickupDelay();
    }

    public EntitySummonedItem(World world, int color)
    {
        this(world);
        this.setColor(color);
    }

    public EntitySummonedItem(World world, int color, ItemStack itemStack)
    {
        this(world, color);
        this.setSummonedItem(itemStack);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(this.pickupDelay > 0) this.pickupDelay--;
        if(this.ticksExisted > this.lifeSpan) this.onKillCommand();
    }

    public void setDefaultPickupDelay() {
        this.pickupDelay = 50; //40 ticks spawn animation + default item pickup delay
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {
        if(!this.world.isRemote && this.pickupDelay == 0) {
            entityIn.addItemStackToInventory(this.getSummonedItem());
            this.setSummonedItem(ItemStack.EMPTY);
            this.setDead(); //TODO fancy pickup animation?
        }
    }

    @Override
    public void onKillCommand() {
        super.onKillCommand();
        this.entityDropItem(this.getSummonedItem(), 0.0F);
        this.setSummonedItem(ItemStack.EMPTY);
    }

    @Override
    protected void entityInit()
    {
        this.dataManager.register(SUMMONED_ITEM, ItemStack.EMPTY);
        this.dataManager.register(COLOR,0);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        this.setSummonedItem(new ItemStack(compound.getCompoundTag("summoned_item")));
        this.setColor(compound.getInteger("element"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        NBTTagCompound itemTag = new NBTTagCompound();
        this.getSummonedItem().writeToNBT(itemTag);
        compound.setTag("summoned_item", itemTag);
        compound.setInteger("element", this.getColor());
    }

    public void setSummonedItem(ItemStack itemStack)
    {
        this.dataManager.set(SUMMONED_ITEM, itemStack);
    }

    public ItemStack getSummonedItem()
    {
        return this.dataManager.get(SUMMONED_ITEM);
    }

    public void setColor(int color)
    {
        this.dataManager.set(COLOR,color);
    }

    public int getColor()
    {
        return this.dataManager.get(COLOR);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return super.getRenderBoundingBox();
    }
}
