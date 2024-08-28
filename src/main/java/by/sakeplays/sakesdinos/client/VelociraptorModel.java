package by.sakeplays.sakesdinos.client;

import by.sakeplays.sakesdinos.SakesDinos;
import by.sakeplays.sakesdinos.entity.VelociraptorEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;

public class VelociraptorModel extends GeoModel<VelociraptorEntity> {

    @Override
    public ResourceLocation getModelResource(VelociraptorEntity animatable) {
        return new ResourceLocation(SakesDinos.MODID, "geo/entity/velociraptor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(VelociraptorEntity animatable) {
        return VelociraptorRenderer.LOCATION_BASE_BY_VARIANT.get(animatable.getBaseVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(VelociraptorEntity animatable) {
        return new ResourceLocation(SakesDinos.MODID, "animations/entity/velociraptor.animation.json");
    }

    @Override
    public void applyMolangQueries(VelociraptorEntity animatable, double animTime) {
        super.applyMolangQueries(animatable, animTime);

        MolangParser parser = MolangParser.INSTANCE;


    }
}
