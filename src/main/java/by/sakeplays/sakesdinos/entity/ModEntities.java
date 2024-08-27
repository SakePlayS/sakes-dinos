package by.sakeplays.sakesdinos.entity;

import by.sakeplays.sakesdinos.SakesDinos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SakesDinos.MODID);

    public static final RegistryObject<EntityType<VelociraptorEntity>> VELOCIRAPTOR = ENTITIES.register("velociraptor",
            () -> EntityType.Builder.of(VelociraptorEntity::new, MobCategory.MISC)
                    .sized(0.66f, 1.8f)
                    .build(new ResourceLocation(SakesDinos.MODID,"velociraptor").toString()));
}
