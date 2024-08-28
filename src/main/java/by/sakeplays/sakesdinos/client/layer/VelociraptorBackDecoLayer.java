package by.sakeplays.sakesdinos.client.layer;

import by.sakeplays.sakesdinos.client.VelociraptorRenderer;
import by.sakeplays.sakesdinos.entity.VelociraptorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class VelociraptorBackDecoLayer extends GeoRenderLayer<VelociraptorEntity> {
    public VelociraptorBackDecoLayer(GeoRenderer<VelociraptorEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, VelociraptorEntity animatable, BakedGeoModel bakedModel, RenderType renderType,
                       MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        ResourceLocation BACK_DECO = VelociraptorRenderer.LOCATION_BACK_DECO_BY_VARIANT.get(animatable.getBackDecoVariant());

        RenderType backDeco = RenderType.entityCutoutNoCull(BACK_DECO);

        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, backDeco,
                bufferSource.getBuffer(backDeco), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
