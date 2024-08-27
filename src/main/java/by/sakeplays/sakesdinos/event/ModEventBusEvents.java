package by.sakeplays.sakesdinos.event;

import by.sakeplays.sakesdinos.SakesDinos;
import by.sakeplays.sakesdinos.entity.ModEntities;
import by.sakeplays.sakesdinos.entity.VelociraptorEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SakesDinos.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.VELOCIRAPTOR.get(), VelociraptorEntity.createAttributes().build());
    }
}
