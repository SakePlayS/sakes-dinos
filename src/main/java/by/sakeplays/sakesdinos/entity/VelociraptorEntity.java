package by.sakeplays.sakesdinos.entity;

import by.sakeplays.sakesdinos.client.variant.VelociraptorBackDecoVariant;
import by.sakeplays.sakesdinos.client.variant.VelociraptorBaseVariant;
import by.sakeplays.sakesdinos.client.variant.VelociraptorEyesVariant;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
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
    static final EntityDataAccessor<String> DATA_CURRENT_AMBIENT_ACTIVITY = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.STRING);

    static final EntityDataAccessor<BlockPos> DATA_HOME_POS = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BLOCK_POS);
    static final EntityDataAccessor<Boolean> DATA_HAS_HOME = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.BOOLEAN);
    static final EntityDataAccessor<Integer> DATA_GROWTH_STAGE = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.INT);
    static final EntityDataAccessor<Integer> DATA_BASE_VARIANT = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.INT);
    static final EntityDataAccessor<Integer> DATA_EYES_VARIANT = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.INT);
    static final EntityDataAccessor<Integer> DATA_BACK_DECO_VARIANT = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.INT);


    static final EntityDataAccessor<Integer> DATA_AMBIENT_ANIM_TIMER = SynchedEntityData.defineId(VelociraptorEntity.class, EntityDataSerializers.INT);



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
        this.entityData.define(DATA_AMBIENT_ANIM_TIMER, 1);
        this.entityData.define(DATA_BASE_VARIANT, 0);
        this.entityData.define(DATA_EYES_VARIANT, 0);
        this.entityData.define(DATA_BACK_DECO_VARIANT, 0);
        this.entityData.define(DATA_CURRENT_ACTIVITY, "idle");
        this.entityData.define(DATA_CURRENT_AMBIENT_ACTIVITY, "none");
        this.entityData.define(DATA_HAS_HOME, false);
        this.entityData.define(DATA_HOME_POS, this.getOnPos());

    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        pCompound.putString("sdCurrentActivity", this.entityData.get(DATA_CURRENT_ACTIVITY));
        pCompound.putString("sdCurrentAmbientActivity", this.entityData.get(DATA_CURRENT_AMBIENT_ACTIVITY));
        pCompound.putString("sdCurrentState", this.entityData.get(DATA_CURRENT_STATE));
        pCompound.putBoolean("sdHasHome", this.entityData.get(DATA_HAS_HOME));
        pCompound.putInt("sdGrowthStage", this.entityData.get(DATA_GROWTH_STAGE));

        // VARIANTS
        pCompound.putInt("sdBaseVariant", this.entityData.get(DATA_BASE_VARIANT));
        pCompound.putInt("sdEyesVariant", this.entityData.get(DATA_EYES_VARIANT));
        pCompound.putInt("sdBackDecoVariant", this.entityData.get(DATA_BACK_DECO_VARIANT));

        // AMBIENT ANIMATIONS
        pCompound.putInt("sdAmbientAnimTimer", this.entityData.get(DATA_AMBIENT_ANIM_TIMER));


        // HOME POS
        pCompound.putInt("sdHomePosX", this.entityData.get(DATA_HOME_POS).getX());
        pCompound.putInt("sdHomePosY", this.entityData.get(DATA_HOME_POS).getY());
        pCompound.putInt("sdHomePosZ", this.entityData.get(DATA_HOME_POS).getZ());



    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("sdCurrentActivity")) {
            this.entityData.set(DATA_CURRENT_ACTIVITY, pCompound.getString("sdCurrentActivity"));
        }

        if (pCompound.contains("sdCurrentAmbientActivity")) {
            this.entityData.set(DATA_CURRENT_AMBIENT_ACTIVITY, pCompound.getString("sdCurrentAmbientActivity"));
        }

        if (pCompound.contains("sdCurrentState")) {
            this.entityData.set(DATA_CURRENT_STATE, pCompound.getString("sdCurrentState"));
        }

        if (pCompound.contains("sdHasHome")) {
            this.entityData.set(DATA_HAS_HOME, pCompound.getBoolean("sdHasHome"));
        }

        if (pCompound.contains("sdGrowthStage")) {
            this.entityData.set(DATA_GROWTH_STAGE, pCompound.getInt("sdGrowthStage"));
        }

        if (pCompound.contains("sdBaseVariant")) {
            this.entityData.set(DATA_BASE_VARIANT, pCompound.getInt("sdBaseVariant"));
        }

        if (pCompound.contains("sdEyesVariant")) {
            this.entityData.set(DATA_EYES_VARIANT, pCompound.getInt("sdEyesVariant"));
        }
        if (pCompound.contains("sdBackDecoVariant")) {
            this.entityData.set(DATA_BACK_DECO_VARIANT, pCompound.getInt("sdBackDecoVariant"));
        }

        if (pCompound.contains("sdAmbientAnimTimer")) {
            this.entityData.set(DATA_AMBIENT_ANIM_TIMER, pCompound.getInt("sdAmbientAnimTimer"));
        }

        if ((pCompound.contains("sdHomePosX")) && (pCompound.contains("sdHomePosY")) && (pCompound.contains("sdHomePosZ"))) {
            this.entityData.set(DATA_HOME_POS, new BlockPos(pCompound.getInt("sdHomePosX"),
                    pCompound.getInt("sdHomePosY"),
                    pCompound.getInt("sdHomePosZ")));
        }


    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 45d)
                .add(Attributes.MOVEMENT_SPEED, 0.3d)
                .add(Attributes.ATTACK_DAMAGE, 4.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1f)
                .add(Attributes.FOLLOW_RANGE, 16d)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8d);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>(this, "movement", 5, this::movementAnimController));
        controllers.add(new AnimationController<>(this, "ambient", 5, this::ambientAnimController)
                .triggerableAnim("yawn_standing", AMBIENT_YAWN_STANDING).
                triggerableAnim("look_around_standing", AMBIENT_LOOK_AROUND_STANDING));


    }

    protected <E extends VelociraptorEntity> PlayState movementAnimController(final AnimationState<E> event) {
        if (!event.isMoving()) {
            return event.setAndContinue(STANDING_IDLE);

        }

        return PlayState.CONTINUE;

    }

    protected <E extends VelociraptorEntity> PlayState ambientAnimController(final AnimationState<E> event) {

        return PlayState.CONTINUE;

    }

    public void setHomePos(int x, int y, int z) {
        this.entityData.set(DATA_HOME_POS, new BlockPos(x, y ,z));

        CompoundTag pCompound = new CompoundTag();

        this.saveWithoutId(pCompound);

        pCompound.putInt("sdHomePosX", x);
        pCompound.putInt("sdHomePosY", y);
        pCompound.putInt("sdHomePosZ", z);

        this.load(pCompound);
    }

    public void setCurrentActivity (String activity) {

        CompoundTag pCompound = new CompoundTag();

        this.saveWithoutId(pCompound);

        this.entityData.set(DATA_CURRENT_ACTIVITY, activity);
        pCompound.putString("sdCurrentActivity", activity);

        this.load(pCompound);

    }

    public void setGrowthStage (Integer value) {

        CompoundTag pCompound = new CompoundTag();

        this.saveWithoutId(pCompound);

        this.entityData.set(DATA_GROWTH_STAGE, value);
        pCompound.putInt("sdGrowthStage", value);

        this.load(pCompound);

    }

    public void setAmbientAnimTimer (Integer value) {
        this.entityData.set(DATA_AMBIENT_ANIM_TIMER, value);
    }

    public int getAmbientAnimTimer() {
        return this.entityData.get(DATA_AMBIENT_ANIM_TIMER);
    }

    public void setCurrentState (String state) {

        CompoundTag pCompound = new CompoundTag();

        this.saveWithoutId(pCompound);

        this.entityData.set(DATA_CURRENT_STATE, state);
        pCompound.putString("sdCurrentActivity", state);

        this.load(pCompound);

    }

    public void setHasHome (Boolean value) {

        CompoundTag pCompound = new CompoundTag();

        this.saveWithoutId(pCompound);

        this.entityData.set(DATA_HAS_HOME, value);
        pCompound.putBoolean("sdHasHome", value);

        this.load(pCompound);

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {

            this.setAmbientAnimTimer(this.getAmbientAnimTimer() + 1);

            if (this.getAmbientAnimTimer() > 250) {

                int animRandomizer = (int)Math.ceil(Math.random() * 6);

                if (animRandomizer >= 1 && animRandomizer <= 5) {
                    this.triggerAnim("ambient", "look_around_standing");
                } else if (animRandomizer == 6){
                    this.triggerAnim("ambient", "yawn_standing");
                }

                this.setAmbientAnimTimer(0);
            }
        }


    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_,
                                        MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_,
                                        @Nullable CompoundTag p_146750_) {
        VelociraptorBaseVariant base_variant = Util.getRandom(VelociraptorBaseVariant.values(), this.random);
        VelociraptorEyesVariant eyes_variant = Util.getRandom(VelociraptorEyesVariant.values(), this.random);
        VelociraptorBackDecoVariant back_deco_variant = Util.getRandom(VelociraptorBackDecoVariant.values(), this.random);

        setDataBaseVariant(base_variant);
        setDataEyesVariant(eyes_variant);
        setDataBackDecoVariant(back_deco_variant);



        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    public VelociraptorBaseVariant getBaseVariant() {
        return VelociraptorBaseVariant.byId(this.getDataBaseVariant() & 255);
    }

    public VelociraptorBackDecoVariant getBackDecoVariant() {
        return VelociraptorBackDecoVariant.byId(this.getDataBackDecoVariant() & 255);
    }

    public VelociraptorEyesVariant getEyesVariant() {
        return VelociraptorEyesVariant.byId(this.getDataEyesVariant() & 255);
    }

    private int getDataBaseVariant() {
        return this.entityData.get(DATA_BASE_VARIANT);
    }
    private int getDataBackDecoVariant() {
        return this.entityData.get(DATA_BACK_DECO_VARIANT);
    }

    private void setDataBaseVariant(VelociraptorBaseVariant variant) {
        this.entityData.set(DATA_BASE_VARIANT, variant.getId() & 255);
    }

    private void setDataBackDecoVariant(VelociraptorBackDecoVariant variant) {
        this.entityData.set(DATA_BACK_DECO_VARIANT, variant.getId() & 255);
    }

    private int getDataEyesVariant() {
        return this.entityData.get(DATA_EYES_VARIANT);
    }

    private void setDataEyesVariant(VelociraptorEyesVariant variant) {
        this.entityData.set(DATA_EYES_VARIANT, variant.getId() & 255);
    }
}
