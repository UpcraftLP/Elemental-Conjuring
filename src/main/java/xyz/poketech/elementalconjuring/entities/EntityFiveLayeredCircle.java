package xyz.poketech.elementalconjuring.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import xyz.poketech.elementalconjuring.ModSounds;

/**
 * Created by Poke on 2017-11-23.
 */
public class EntityFiveLayeredCircle extends Entity
{
    public static final int animationTime = 15;
    public static final int circleCount = 5;
    public static final int strikeTime = 50;
    public static final int activationTime = circleCount * animationTime;
    public static final int deactivatedTime = activationTime + strikeTime;

    public EntityFiveLayeredCircle(World worldIn)
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

//        if(this.ticksExisted >  activationTime)
//        {
//            world.spawnEntity(new EntityLightningBolt(world, posX + world.rand.nextInt(3) -1, posY, posZ + world.rand.nextInt(3) -1, false));
//
//            if(this.ticksExisted > deactivatedTime) //Activation is done, can now kill
//            {
//               this.setDead();
//            }
//        }
//        else
        if(this.ticksExisted <= activationTime && this.ticksExisted % animationTime == 0)
        {
            playSound(ModSounds.circle_spawn,1.0f,1.0F);
        }


    }

    @Override
    public int getBrightnessForRender()
    {
        return 0xF000F0;
    }
}
