package cn.ksmcbrigade.cbr.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Monster implements PowerableMob {

    protected CreeperMixin(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Inject(method = "registerGoals",at = @At("TAIL"))
    public void init(CallbackInfo ci){
        this.goalSelector.addGoal(2,new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
    }

    @Inject(method = "tick",at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        if (this.isAlive()) {
            boolean bl = this.isSunBurnTick();
            if (bl) {
                this.igniteForSeconds(8);
            }
        }
    }
}
