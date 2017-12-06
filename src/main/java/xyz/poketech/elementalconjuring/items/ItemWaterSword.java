package xyz.poketech.elementalconjuring.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.poketech.elementalconjuring.ElementalConjuring;

/**
 * Created by Antoine Gagnon (Poke1650) on 2017-11-30.
 */
public class ItemWaterSword extends ItemSword
{
    public ItemWaterSword(ToolMaterial material)
    {
        super(material);
        setRegistryName("water_sword");
        setUnlocalizedName(ElementalConjuring.MODID + ".water_sword");
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        //target.setInWeb();
        return super.hitEntity(stack, target, attacker);
    }

    @SideOnly(Side.CLIENT)
    public void initModel()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
