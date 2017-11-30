package xyz.poketech.elementalconjuring.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

/**
 * Created by Poke on 2017-11-22.
 */
public class EntityRune extends Entity
{
    public EntityRune(World worldIn)
    {
        super(worldIn);
    }

    @Override
    protected void entityInit()
    {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {

    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if(world.getWorldTime() % 20 == 0)
            this.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, this.posX + world.rand.nextFloat() * 2 - 1, this.posY, this.posZ + world.rand.nextFloat() * 2 - 1, 0.0D, 0.05D, 0.0D);
    }
}
