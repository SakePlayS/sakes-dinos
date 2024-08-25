package by.sakeplays.sakesdinos.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public abstract class Dinosaur extends Animal {


    protected Dinosaur(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


}

