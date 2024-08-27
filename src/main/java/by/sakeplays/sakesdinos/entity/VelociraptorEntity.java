package by.sakeplays.sakesdinos.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VelociraptorEntity extends Animal implements GeoEntity {

    static final EntityDataAccessor<String> DATA_CURRENT_STATE = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.STRING);
    static final EntityDataAccessor<String> DATA_CURRENT_ACTIVITY = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.STRING);
    static final EntityDataAccessor<BlockPos> DATA_HOME_POS = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BLOCK_POS);
    static final EntityDataAccessor<Boolean> DATA_HAS_HOME = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    static final EntityDataAccessor<Integer> DATA_GROWTH_STAGE = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.INT);


    protected static final RawAnimation STANDING_IDLE = RawAnimation.begin().thenLoop("animation.velociraptor.standing_idle");
    protected static final RawAnimation AMBIENT_YAWN_STANDING = RawAnimation.begin().thenPlay("animation.velociraptor.ambient.yawn_standing");
    protected static final RawAnimation AMBIENT_LOOK_AROUND_STANDING = RawAnimation.begin().thenPlay("animation.velociraptor.ambient.look_around_standing");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);



    protected VelociraptorEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CURRENT_STATE, "calm");
        this.entityData.define(DATA_GROWTH_STAGE, 1);
        this.entityData.define(DATA_CURRENT_ACTIVITY, "idle");
        this.entityData.define(DATA_HAS_HOME, false);
        this.entityData.define(DATA_HOME_POS, this.getOnPos());

    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.getCompound("SakesDinos").putString("CurrentActivity", this.entityData.get(DATA_CURRENT_ACTIVITY));
        pCompound.getCompound("SakesDinos").putString("CurrentState", this.entityData.get(DATA_CURRENT_STATE));
        pCompound.getCompound("SakesDinos").putBoolean("HasHome", this.entityData.get(DATA_HAS_HOME));
        pCompound.getCompound("SakesDinos").putInt("GrowthStage", this.entityData.get(DATA_GROWTH_STAGE));


        pCompound.getCompound("SakesDinos").getCompound("HomePos").putInt("x", this.entityData.get(DATA_HOME_POS).getX());
        pCompound.getCompound("SakesDinos").getCompound("HomePos").putInt("y", this.entityData.get(DATA_HOME_POS).getY());
        pCompound.getCompound("SakesDinos").getCompound("HomePos").putInt("z", this.entityData.get(DATA_HOME_POS).getZ());

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.getCompound("SakesDinos").contains("CurrentActivity")) {
            this.entityData.set(DATA_CURRENT_ACTIVITY, pCompound.getCompound("SakesDinos").getString("CurrentActivity"));
        }

        if (pCompound.getCompound("SakesDinos").contains("CurrentState")) {
            this.entityData.set(DATA_CURRENT_STATE, pCompound.getCompound("SakesDinos").getString("CurrentState"));
        }

        if (pCompound.getCompound("SakesDinos").contains("HasHome")) {
            this.entityData.set(DATA_HAS_HOME, pCompound.getCompound("SakesDinos").getBoolean("HasHome"));
        }

        if (pCompound.getCompound("SakesDinos").contains("GrowthStage")) {
            this.entityData.set(DATA_GROWTH_STAGE, pCompound.getCompound("SakesDinos").getInt("GrowthStage"));
        }

        if (pCompound.getCompound("SakesDinos").getCompound("HomePos").contains("x")) {
            this.entityData.set(DATA_HOME_POS, new BlockPos(pCompound.getCompound("SakesDinos").getCompound("HomePos").getInt("x"),
                    pCompound.getCompound("SakesDinos").getCompound("HomePos").getInt("y"),
                    pCompound.getCompound("SakesDinos").getCompound("HomePos").getInt("z")));
        }

    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 4.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1f)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>(this, "movement", this::movementAnimController));

    }

    protected <E extends VelociraptorEntity> PlayState movementAnimController(final AnimationState<E> event) {
        if (!event.isMoving()) {
            return event.setAndContinue(STANDING_IDLE);
        }

        return PlayState.CONTINUE;

    }

    public void setHomePos(int x, int y, int z) {
        this.entityData.set(DATA_HOME_POS, new BlockPos(x, y ,z));

        CompoundTag pCompound = new CompoundTag();

        this.saveWithoutId(pCompound);

        pCompound.getCompound("SakesDinos").getCompound("HomePos").putInt("x", x);
        pCompound.getCompound("SakesDinos").getCompound("HomePos").putInt("y", y);
        pCompound.getCompound("SakesDinos").getCompound("HomePos").putInt("z", z);

        this.load(pCompound);
    }

    public void setCurrentActivity (String activity) {

        CompoundTag pCompound = new CompoundTag();

        this.saveWithoutId(pCompound);

        this.entityData.set(DATA_CURRENT_ACTIVITY, activity);
        pCompound.getCompound("SakesDinos").putString("CurrentActivity", activity);

        this.load(pCompound);

    }

    public void setGrowthStage (Integer value) {

        CompoundTag pCompound = new CompoundTag();

        this.saveWithoutId(pCompound);

        this.entityData.set(DATA_GROWTH_STAGE, value);
        pCompound.getCompound("SakesDinos").putInt("GrowthStage", value);

        this.load(pCompound);

    }

    public void setCurrentState (String state) {

        CompoundTag pCompound = new CompoundTag();

        this.saveWithoutId(pCompound);

        this.entityData.set(DATA_CURRENT_STATE, state);
        pCompound.getCompound("SakesDinos").putString("CurrentActivity", state);

        this.load(pCompound);

    }

    public void setHasHome (Boolean value) {

        CompoundTag pCompound = new CompoundTag();

        this.saveWithoutId(pCompound);

        this.entityData.set(DATA_HAS_HOME, value);
        pCompound.getCompound("SakesDinos").putBoolean("HasHome", value);

        this.load(pCompound);

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
