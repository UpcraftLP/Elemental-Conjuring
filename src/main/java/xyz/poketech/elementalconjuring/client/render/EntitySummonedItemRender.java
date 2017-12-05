package xyz.poketech.elementalconjuring.client.render;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xyz.poketech.elementalconjuring.ElementalConjuring;
import xyz.poketech.elementalconjuring.entities.EntitySummonedItem;
import xyz.poketech.elementalconjuring.etc.EnumElement;

import javax.annotation.Nullable;

/**
 * Created by Poke on 2017-11-22.
 */
public class EntitySummonedItemRender extends Render<EntitySummonedItem>
{
    private static final ResourceLocation ARRAY_TEXTURE = new ResourceLocation(ElementalConjuring.MODID, "textures/circles/circle1.png");
    float rotationspeed = 0.5f;
    final double SPAWN_ANIMATION_TIME = 20D;

    public EntitySummonedItemRender(RenderManager renderManager)
    {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySummonedItem entity)
    {
        return null;
    }

    @Override
    public void doRender(EntitySummonedItem entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        int r = EnumElement.values()[entity.getElement()].getR();
        int b = EnumElement.values()[entity.getElement()].getB();
        int g = EnumElement.values()[entity.getElement()].getG();
        int a = EnumElement.values()[entity.getElement()].getA();

        if(entity.ticksExisted <= SPAWN_ANIMATION_TIME)
        {
            playSpawnAnimation(entity, x, y, z, entityYaw, partialTicks,r,b,g,a);
        }
        else
        {
            renderSpinning(entity, x, y, z, entityYaw, partialTicks,r,b,g,a);
        }
    }

    public void playSpawnAnimation(EntitySummonedItem entity, double x, double y, double z, float entityYaw, float partialTicks, int r,int b, int g, int a)
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

        GlStateManager.color(r,g,b,a);

        tessellator.draw();

        vertexbuffer.setTranslation(0, 0, 0);
        GlStateManager.enableCull();

        GlStateManager.popMatrix();
    }

    public void renderSpinning(EntitySummonedItem entity, double x, double y, double z, float entityYaw, float partialTicks, int r,int b, int g, int a)
    {
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

        GlStateManager.color(r,g,b,a);
        tessellator.draw();

        vertexbuffer.setTranslation(0, 0, 0);
        GlStateManager.enableCull();

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();

        double pos = 1;
        double offset = Math.cos((entity.ticksExisted + partialTicks) / 10) / 4.0;

        if(entity.ticksExisted <= (SPAWN_ANIMATION_TIME * 2))
        {
            pos = -1 + ((entity.ticksExisted + partialTicks - SPAWN_ANIMATION_TIME) / 10); //Don't even ask
            offset = 0;

        }
        GlStateManager.translate(x, y + pos + offset, z);

        GlStateManager.rotate(-rot * 2, 0, 1, 0);
        GlStateManager.rotate(135, 0, 0, 1);

        Minecraft.getMinecraft().getRenderItem().renderItem(entity.getSummonedItem(), ItemCameraTransforms.TransformType.FIXED);

        GlStateManager.translate(-x, -y - pos - offset, -z);

        GlStateManager.popMatrix();
    }

}
