package xyz.poketech.elementalconjuring.client.render;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xyz.poketech.elementalconjuring.ElementalConjuring;
import xyz.poketech.elementalconjuring.entities.EntitySummonedItem;
import xyz.poketech.elementalconjuring.util.ColorUtil;

import javax.annotation.Nullable;

/**
 * Created by Poke on 2017-11-22.
 */
public class RenderSummonedItem extends Render<EntitySummonedItem>
{
    private static final ResourceLocation ARRAY_TEXTURE = new ResourceLocation(ElementalConjuring.MODID, "textures/circles/circle.png");
    private static final float ROTATION_SPEED = 0.5F;

    public RenderSummonedItem(RenderManager renderManager)
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
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(90 - entity.rotationYaw, 0, 1, 0);
        GlStateManager.rotate(90 + entity.rotationPitch, 0, 0, 1);
        GlStateManager.rotate(ROTATION_SPEED * (partialTicks + entity.ticksExisted) % 360.0F, 0, 1, 0);

        GlStateManager.disableBlend();
        GlStateManager.disableCull();
        GlStateManager.disableLighting();

        bindTexture(ARRAY_TEXTURE);
        ColorUtil.setGLColorFromInt(entity.getColor());

        if(entity.shouldDie) {
            playDeathAnimation(entity, x, y, z, entityYaw, partialTicks);
        }
        else if(entity.ticksExisted <= EntitySummonedItem.SPAWN_ANIMATION_TIME)
        {
            playSpawnAnimation(entity, x, y, z, entityYaw, partialTicks);
        }
        else
        {
            renderSpinning(entity, x, y, z, entityYaw, partialTicks);
        }
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }

    private void playDeathAnimation(EntitySummonedItem entity, double x, double y, double z, float entityYaw, float partialTicks) {

        double ageRatio = ((entity.deathTime + partialTicks) / ((double) EntitySummonedItem.SPAWN_ANIMATION_TIME)) * 2;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();

        GlStateManager.scale(1.0D - ageRatio, 1, 1.0D - ageRatio);

        GlStateManager.translate(-x, -y, -z);

        vertexbuffer.setTranslation(x, y, z);
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        vertexbuffer.pos(1, 0.05, -1).tex(0, 0).endVertex();
        vertexbuffer.pos(-1, 0.05, -1).tex(1, 0).endVertex();
        vertexbuffer.pos(-1, 0.05, 1).tex(1, 1).endVertex();
        vertexbuffer.pos(1, 0.05, 1).tex(0, 1).endVertex();

        tessellator.draw();

        vertexbuffer.setTranslation(0, 0, 0);
    }

    public void playSpawnAnimation(EntitySummonedItem entity, double x, double y, double z, float entityYaw, float partialTicks)
    {

        double ageRatio = (entity.ticksExisted + partialTicks) / ((double) EntitySummonedItem.SPAWN_ANIMATION_TIME);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();

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
    }

    public void renderSpinning(EntitySummonedItem entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();

        float rot = ROTATION_SPEED * (partialTicks + entity.ticksExisted);

        GlStateManager.translate(-x, -y, -z);

        vertexbuffer.setTranslation(x, y, z);
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        vertexbuffer.pos(1, 0.05, -1).tex(0, 0).endVertex();
        vertexbuffer.pos(-1, 0.05, -1).tex(1, 0).endVertex();
        vertexbuffer.pos(-1, 0.05, 1).tex(1, 1).endVertex();
        vertexbuffer.pos(1, 0.05, 1).tex(0, 1).endVertex();

        tessellator.draw();

        vertexbuffer.setTranslation(0, 0, 0);

        ItemStack itemStack = entity.getSummonedItem();
        if(!itemStack.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, z);
            double pos = 1;
            double offset = Math.cos((entity.ticksExisted + partialTicks) / 10.0D) / 4.0D;

            if(entity.ticksExisted <= (EntitySummonedItem.SPAWN_ANIMATION_TIME * 2))
            {
                pos = ((entity.ticksExisted + partialTicks - EntitySummonedItem.SPAWN_ANIMATION_TIME) / 10.0D) - 1; //Don't even ask
                offset = 0;
            }
            GlStateManager.translate(0, pos + offset, 0);

            GlStateManager.rotate(-rot * 2, 0, 1, 0);
            int angle = 0;
            if(itemStack.getItem() instanceof ItemSword || itemStack.getItem().getToolClasses(itemStack).contains("sowrd")) angle = 135;
            else if(itemStack.getItem().isFull3D()) angle =  -45;
            GlStateManager.rotate(angle, 0, 0, 1);

            Minecraft.getMinecraft().getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);

            GlStateManager.translate(0, -(pos + offset), 0);

            GlStateManager.popMatrix();
        }
    }

}
