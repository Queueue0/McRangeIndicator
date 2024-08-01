package queueue.RangeIndicator.mixin.client;

import com.mojang.logging.LogUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import org.slf4j.Logger;

@Mixin(MinecraftClient.class)
public class HasOutlineMixin {
	private static final Logger LOGGER = LogUtils.getLogger();

	private void returnTrue(CallbackInfoReturnable<Boolean> info) {
		info.setReturnValue(true);
		info.cancel();
	}
	@Inject(at = @At("HEAD"), method = "hasOutline(Lnet/minecraft/entity/Entity;)Z", cancellable = true)
	private void hasOutline(Entity e, CallbackInfoReturnable<Boolean> info) {
		MinecraftClient c = MinecraftClient.getInstance();
		Entity te = c.targetedEntity;
		if (e.equals(te)) {
			returnTrue(info);
		}

		if (e instanceof MobEntity) {
			LivingEntity t = ((MobEntity) e).getTarget();
			if (t != null && t instanceof PlayerEntity) {
				returnTrue(info);
			} else {
				if (t != null) {
					LOGGER.info(t.toString());
				}
			}
		}
	}
}
