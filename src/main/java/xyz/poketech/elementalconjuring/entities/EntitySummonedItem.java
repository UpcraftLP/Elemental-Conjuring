package xyz.poketech.elementalconjuring.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.poketech.elementalconjuring.net.NetworkHandler;
import xyz.poketech.elementalconjuring.net.packet.PacketEntityDeath;

/**
 * Created by Poke on 2017-11-22.
 */
public class EntitySummonedItem extends Entity implements ICustomDeathEntity
{
    private static final DataParameter<ItemStack> SUMMONED_ITEM = EntityDataManager.createKey(EntitySummonedItem.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntitySummonedItem.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> NO_PICKUP = EntityDataManager.createKey(EntitySummonedItem.class, DataSerializers.BOOLEAN);
    public static final int SPAWN_ANIMATION_TIME = 20;

    private int pickupDelay;
    public int lifeSpan = 1200;
    public int deathTime = 0;
    public boolean shouldDie;

    public EntitySummonedItem(World world)
    {
        super(world);
        this.setDefaultPickupDelay();
        this.setRotation(0.0F, -90.0F);
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
        if(!this.shouldDie) {
            if(this.pickupDelay > 0 && this.ticksExisted > SPAWN_ANIMATION_TIME * 2) this.pickupDelay--;
            if(this.ticksExisted > this.lifeSpan) this.onKillCommand();
        }
        else {
            this.deathTime++;
            if(this.deathTime > SPAWN_ANIMATION_TIME / 2) this.setDead();
        }
    }

    public void setDefaultPickupDelay() {
        this.setPickupDelay(10);
    }

    public void setNoPickup(boolean noPickup) {
        this.dataManager.set(NO_PICKUP, noPickup);
    }

    public boolean canBePickedUp() {
        return !this.dataManager.get(NO_PICKUP);
    }

    public int getPickupDelay() {
        return pickupDelay;
    }

    public void setPickupDelay(int pickupDelay) {
        this.pickupDelay = pickupDelay;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {
        if(!this.world.isRemote && this.pickupDelay <= 0 && this.canBePickedUp()) {
            entityIn.addItemStackToInventory(this.getSummonedItem());
            this.setSummonedItem(ItemStack.EMPTY);
            this.onKillCommand();
        }
    }

    @Override
    public void onKillCommand() {
        this.shouldDie = true;
        if(!this.world.isRemote) {
            NetworkHandler.INSTANCE.sendToAllAround(new PacketEntityDeath(this), new NetworkRegistry.TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64));
            this.entityDropItem(this.getSummonedItem(), 0.0F);
        }
        this.setSummonedItem(ItemStack.EMPTY);
    }

    @Override
    protected void entityInit()
    {
        this.dataManager.register(SUMMONED_ITEM, ItemStack.EMPTY);
        this.dataManager.register(COLOR, 0);
        this.dataManager.register(NO_PICKUP, false);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        this.setSummonedItem(new ItemStack(compound.getCompoundTag("summoned_item")));
        this.setColor(compound.getInteger("element"));
        this.setNoPickup(compound.getBoolean("noPickup"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setTag("summoned_item", this.getSummonedItem().writeToNBT(new NBTTagCompound()));
        compound.setInteger("element", this.getColor());
        compound.setBoolean("noPickup", this.canBePickedUp());
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
        this.dataManager.set(COLOR, color);
    }

    public int getColor()
    {
        return this.dataManager.get(COLOR);
    }

    @Override
    public float getEyeHeight() {
        return 0.2F;
    }

    @Override
    public int getID() {
        return this.getEntityId();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void setShouldDie() {
        this.shouldDie = true;
    }
}
