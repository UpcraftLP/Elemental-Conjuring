package xyz.poketech.elementalconjuring.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * Created by Poke on 2017-11-22.
 */
public class EntityMagicCircle extends Entity
{
    private static final DataParameter<Integer> CIRCLE_COLOR = EntityDataManager.<Integer>createKey(EntityMagicCircle.class, DataSerializers.VARINT);

    public EntityMagicCircle(World world)
    {
        super(world);
        this.setColor(Integer.valueOf(1));
    }

    public EntityMagicCircle(World world, int color)
    {
        super(world);
        this.setColor(Integer.valueOf(color));
    }

    @Override
    protected void entityInit()
    {
        this.dataManager.register(CIRCLE_COLOR, Integer.valueOf(1));
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        this.setColor(compound.getInteger("color"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setInteger("color", this.getColor());
    }

    public void setColor(int color)
    {
        this.dataManager.set(CIRCLE_COLOR, Integer.valueOf(color));
    }

    public int getColor()
    {
        return this.dataManager.get(CIRCLE_COLOR).intValue();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        return super.getRenderBoundingBox();
    }
}
