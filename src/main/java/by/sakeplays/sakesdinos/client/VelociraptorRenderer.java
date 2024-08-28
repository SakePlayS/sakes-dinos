package by.sakeplays.sakesdinos.client;

import by.sakeplays.sakesdinos.SakesDinos;
import by.sakeplays.sakesdinos.client.layer.VelociraptorBackDecoLayer;
import by.sakeplays.sakesdinos.client.layer.VelociraptorEyesLayer;
import by.sakeplays.sakesdinos.client.variant.VelociraptorBackDecoVariant;
import by.sakeplays.sakesdinos.client.variant.VelociraptorBaseVariant;
import by.sakeplays.sakesdinos.client.variant.VelociraptorEyesVariant;
import by.sakeplays.sakesdinos.entity.VelociraptorEntity;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class VelociraptorRenderer extends GeoEntityRenderer<VelociraptorEntity> {

    public static final Map<VelociraptorBaseVariant, ResourceLocation> LOCATION_BASE_BY_VARIANT =
            Util.make(Maps.newEnumMap(VelociraptorBaseVariant.class), (baseTexture) -> {
                baseTexture.put(VelociraptorBaseVariant.GRAY,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/base_1.png"));
                baseTexture.put(VelociraptorBaseVariant.LIGHT_GRAY,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/base_2.png"));
                baseTexture.put(VelociraptorBaseVariant.BROWN,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/base_3.png"));
            });

    public static final Map<VelociraptorEyesVariant, ResourceLocation> LOCATION_EYES_BY_VARIANT =
            Util.make(Maps.newEnumMap(VelociraptorEyesVariant.class), (eyesTexture) -> {
                eyesTexture.put(VelociraptorEyesVariant.YELLOW,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/eyes_1.png"));
                eyesTexture.put(VelociraptorEyesVariant.RED,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/eyes_2.png"));
                eyesTexture.put(VelociraptorEyesVariant.BLUE,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/eyes_3.png"));
                eyesTexture.put(VelociraptorEyesVariant.GREEN,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/eyes_4.png"));
            });


    public static final Map<VelociraptorBackDecoVariant, ResourceLocation> LOCATION_BACK_DECO_BY_VARIANT =
            Util.make(Maps.newEnumMap(VelociraptorBackDecoVariant.class), (backDecoTexture) -> {
                backDecoTexture.put(VelociraptorBackDecoVariant.SPOTS_WHITE,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/back_deco_spots_1.png"));
                backDecoTexture.put(VelociraptorBackDecoVariant.SPOTS_BLACK,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/back_deco_spots_2.png"));
                backDecoTexture.put(VelociraptorBackDecoVariant.SPOTS_BLUE,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/back_deco_spots_3.png"));
                backDecoTexture.put(VelociraptorBackDecoVariant.SPOTS_GREEN,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/back_deco_spots_4.png"));
                backDecoTexture.put(VelociraptorBackDecoVariant.SPOTS_RED,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/back_deco_spots_5.png"));
                backDecoTexture.put(VelociraptorBackDecoVariant.SPOTS_BROWN,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/back_deco_spots_6.png"));



                backDecoTexture.put(VelociraptorBackDecoVariant.STRIPES_BLACK,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/back_deco_stripes_black.png"));
                backDecoTexture.put(VelociraptorBackDecoVariant.STRIPES_BLUE,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/back_deco_stripes_blue.png"));
                backDecoTexture.put(VelociraptorBackDecoVariant.STRIPES_BROWN,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/back_deco_stripes_brown.png"));
                backDecoTexture.put(VelociraptorBackDecoVariant.STRIPES_GREEN,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/back_deco_stripes_green.png"));
                backDecoTexture.put(VelociraptorBackDecoVariant.STRIPES_RED,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/back_deco_stripes_red.png"));
                backDecoTexture.put(VelociraptorBackDecoVariant.STRIPES_WHITE,
                        new ResourceLocation(SakesDinos.MODID, "textures/entity/velociraptor/back_deco_stripes_white.png"));
            });


    public VelociraptorRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new VelociraptorModel());

        addRenderLayer(new VelociraptorEyesLayer(this));
        addRenderLayer(new VelociraptorBackDecoLayer(this));

    }

    @Override
    public void preRender(PoseStack poseStack, VelociraptorEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        withScale(1.1f);
    }
}
