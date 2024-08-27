package by.sakeplays.sakesdinos.client;

import by.sakeplays.sakesdinos.entity.VelociraptorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class VelociraptorRenderer extends GeoEntityRenderer<VelociraptorEntity> {
    public VelociraptorRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new VelociraptorModel());

    }

    @Override
    public void preRender(PoseStack poseStack, VelociraptorEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        withScale(1.1f);
    }
}
