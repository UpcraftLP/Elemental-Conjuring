package xyz.poketech.elementalconjuring.client.render;


import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xyz.poketech.elementalconjuring.ElementalConjuring;
import xyz.poketech.elementalconjuring.entities.EntityFiveLayeredCircle;

import javax.annotation.Nullable;

/**
 * Created by Poke on 2017-11-22.
 *
 * To anyone reading this, if you're going to yell at me because of how ugly that is, just know that I do not care.
 *
 */
public class EntityFiveLayeredCircleRender extends Render
{
    private static final ResourceLocation ARRAY_TEXTURE = new ResourceLocation(ElementalConjuring.MODID, "textures/circles/earth.png");
    static float rotationspeed = 3;
    public static final double SPAWN_ANIMATION_TIME = 15D;
    static final double offset = 1D;
    public static final int timeOffset = 15;

    public EntityFiveLayeredCircleRender(RenderManager renderManager)
    {
        super(renderManager);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        bindTexture(ARRAY_TEXTURE);

        int age = entity.ticksExisted;

        //If in first stage, only spawn the first layer
        if(age < timeOffset)
        {
            spawnLayer(0,entity,x,y,z,entityYaw,partialTicks);
            return;
        }

        //If we're pass the time it takes to spawn the 5 layers
        //Render them spinning
        if(age > EntityFiveLayeredCircle.activationTime)
        {
            renderRotating(5,entity,x,y,z,entityYaw,partialTicks);
            return;
        }

        //Spawn layer and render the others under rotating
        for(int i = 2; i <= 5; i++)
        {
            if(age <= timeOffset * i)
            {
                spawnLayer(i - 1,entity,x,y,z,entityYaw,partialTicks);
                renderRotating(i - 1,entity,x,y,z,entityYaw,partialTicks);
                break;
            }
        }
    }

    /*
        Renders layerCount layers on top of each other
        Every next layer is 2 times bigger than the previous
        Every layer is separated by 1 block on the Y axis
     */
    private void renderRotating(int layerCount, Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if(layerCount == 0) return;

        GlStateManager.disableBlend();
        GlStateManager.disableCull();
        GlStateManager.disableLighting();

        for(int i = 0; i < layerCount; i++)
        {
            GlStateManager.pushMatrix();

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBuffer();

            float rot = rotationspeed * (partialTicks + entity.ticksExisted);
            rot = (i % 2 == 0 ? rot * - 1: rot);


            GlStateManager.translate(x ,y + i ,z);
            GlStateManager.rotate(rot,0,1,0);
            GlStateManager.translate(-x,-y,-z);

            vertexbuffer.setTranslation(x,y,z);
            vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

            vertexbuffer.pos(offset + i, 0.05, -offset - i ).tex(0, 0).endVertex();
            vertexbuffer.pos(-offset - i , 0.05, -offset - i ).tex(1, 0).endVertex();
            vertexbuffer.pos(-offset - i , 0.05, offset + i ).tex(1, 1).endVertex();
            vertexbuffer.pos(offset + i , 0.05, offset+ i ).tex(0, 1).endVertex();

            tessellator.draw();
            vertexbuffer.setTranslation(0,0,0);

            GlStateManager.popMatrix();
        }
    }


    /*
        Plays the spawn animation for the layer at pos i
        Slowly scales up from nothing to the full size based on the SPAWN_ANIMATION_TIME constant
     */
    private void spawnLayer(int i, Entity entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();

        bindTexture(ARRAY_TEXTURE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();

        float rot = rotationspeed * (partialTicks + entity.ticksExisted);
        rot = (i % 2 == 0 ? rot * - 1: rot);    //So every layer spin in opposite direction

        GlStateManager.disableBlend();
        GlStateManager.disableCull();
        GlStateManager.disableLighting();

        //Ratio of where we are in the stage, 0 being the start and 1 the end
        double ageRatio;
        if(i == 0)
            ageRatio = (((entity.ticksExisted + partialTicks) / SPAWN_ANIMATION_TIME));
        else
            ageRatio = ((entity.ticksExisted + partialTicks) - (timeOffset * i)) / SPAWN_ANIMATION_TIME;

        bindTexture(ARRAY_TEXTURE);

        GlStateManager.translate(x ,y + i,z);

        GlStateManager.rotate(rot,0,1,0);

        //Scales according to the ageRatio, so the later we are the closer to 1 we get.
        GlStateManager.scale(ageRatio,1, ageRatio);

        GlStateManager.translate(-x,-y,-z);

        vertexbuffer.setTranslation(x,y,z);
        vertexbuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        vertexbuffer.pos(offset + i, 0.05, -offset - i ).tex(0, 0).endVertex();
        vertexbuffer.pos(-offset - i , 0.05, -offset - i ).tex(1, 0).endVertex();
        vertexbuffer.pos(-offset - i , 0.05, offset + i ).tex(1, 1).endVertex();
        vertexbuffer.pos(offset + i , 0.05, offset+ i ).tex(0, 1).endVertex();

        tessellator.draw();

        vertexbuffer.setTranslation(0,0,0);
        GlStateManager.enableCull();

        GlStateManager.popMatrix();
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return null;
    }
    
}
