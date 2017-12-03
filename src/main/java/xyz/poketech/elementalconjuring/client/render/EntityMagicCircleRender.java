package xyz.poketech.elementalconjuring.client.render;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import scala.xml.Elem;
import xyz.poketech.elementalconjuring.ElementalConjuring;
import xyz.poketech.elementalconjuring.ModItems;
import xyz.poketech.elementalconjuring.entities.EntityMagicCircle;

import javax.annotation.Nullable;

/**
 * Created by Poke on 2017-11-22.
 */
public class EntityMagicCircleRender extends Render<EntityMagicCircle>
{
    private static final ResourceLocation ARRAY_TEXTURE = new ResourceLocation(ElementalConjuring.MODID, "textures/circles/circle1.png");
    float rotationspeed = 0.5f;
    final double SPAWN_ANIMATION_TIME = 20D;

    public EntityMagicCircleRender(RenderManager renderManager)
    {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMagicCircle entity)
    {
        return null;
    }

    @Override
    public void doRender(EntityMagicCircle entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if(entity.ticksExisted <= SPAWN_ANIMATION_TIME)
        {
            playSpawnAnimation(entity, x, y, z, entityYaw, partialTicks);
        }
        else
        {
            renderSpinning(entity, x, y, z, entityYaw, partialTicks);
        }
    }

    public void playSpawnAnimation(EntityMagicCircle entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();

        double ageRatio = (entity.ticksExisted + partialTicks) / SPAWN_ANIMATION_TIME;
        bindTexture(ARRAY_TEXTURE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();

        float rot = this.rotationspeed * (partialTicks + entity.ticksExisted);

        GlStateManager.disableBlend();
        GlStateManager.disableCull();
        GlStateManager.disableLighting();

        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(rot, 0, 1, 0);
        GlStateManager.scale(ageRatio, 1, ageRatio);

        GlStateManager.translate(-x, -y, -z);

        vertexbuffer.setTranslation(x, y, z);
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        vertexbuffer.pos(1, 0.05, -1).tex(0, 0).endVertex();
        vertexbuffer.pos(-1, 0.05, -1).tex(1, 0).endVertex();
        vertexbuffer.pos(-1, 0.05, 1).tex(1, 1).endVertex();
        vertexbuffer.pos(1, 0.05, 1).tex(0, 1).endVertex();

        tessellator.draw();

        vertexbuffer.setTranslation(0, 0, 0);
        GlStateManager.enableCull();

        GlStateManager.popMatrix();
    }

    public void renderSpinning(EntityMagicCircle entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        Item item;
        int[] color;

        switch(entity.getColor())
        {
            case 1:
                item = ModItems.itemFireSword;
                color = new int[]{255, 0, 0};
                break;
            case 2:
                item = ModItems.itemWaterSword;
                color = new int[]{0, 0, 255};
                break;
            default:
                item = Items.APPLE;
                color = new int[]{0, 0, 0};
                break;
        }


        GlStateManager.pushMatrix();

        bindTexture(ARRAY_TEXTURE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();

        float rot = this.rotationspeed * (partialTicks + entity.ticksExisted);

        GlStateManager.disableBlend();
        GlStateManager.disableCull();
        GlStateManager.disableLighting();

        GlStateManager.translate(x, y, z);

        GlStateManager.rotate(rot, 0, 1, 0);
        GlStateManager.translate(-x, -y, -z);

        vertexbuffer.setTranslation(x, y, z);
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        vertexbuffer.pos(1, 0.05, -1).tex(0, 0).endVertex();
        vertexbuffer.pos(-1, 0.05, -1).tex(1, 0).endVertex();
        vertexbuffer.pos(-1, 0.05, 1).tex(1, 1).endVertex();
        vertexbuffer.pos(1, 0.05, 1).tex(0, 1).endVertex();

        GlStateManager.color(color[0], color[1], color[2], 1);
        tessellator.draw();

        vertexbuffer.setTranslation(0, 0, 0);
        GlStateManager.enableCull();

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();

        double pos = 1;
        if(entity.ticksExisted <= (SPAWN_ANIMATION_TIME * 2))
        {
            pos = SPAWN_ANIMATION_TIME / (partialTicks + entity.ticksExisted - SPAWN_ANIMATION_TIME);
        }

        GlStateManager.translate(x, y + pos, z);

        GlStateManager.rotate(rot, 0, 1, 0);
        GlStateManager.rotate(135, 0, 0, 1);

        Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(item), ItemCameraTransforms.TransformType.FIXED);

        GlStateManager.translate(-x, -y - pos, -z);

        GlStateManager.popMatrix();
    }

}
