package atonkish.reputation.forge;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;

import snownee.jade.api.*;

import atonkish.reputation.forge.provider.IronGolemProvider;
import atonkish.reputation.forge.provider.VillagerReputationProvider;
import atonkish.reputation.forge.provider.VillagerSnitchProvider;

@WailaPlugin
public class ReputationPluginForge implements IWailaPlugin {
	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerEntityDataProvider(IronGolemProvider.INSTANCE, IronGolemEntity.class);
		registration.registerEntityDataProvider(VillagerReputationProvider.INSTANCE, VillagerEntity.class);
		registration.registerEntityDataProvider(VillagerSnitchProvider.INSTANCE, VillagerEntity.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerEntityComponent(IronGolemProvider.INSTANCE, IronGolemEntity.class);
		registration.registerEntityComponent(VillagerReputationProvider.INSTANCE, VillagerEntity.class);
		registration.registerEntityComponent(VillagerSnitchProvider.INSTANCE, VillagerEntity.class);

		registration.addTooltipCollectedCallback((tooltip, accessor) -> {
			if (accessor instanceof EntityAccessor entityAccessor) {
				Entity entity = entityAccessor.getEntity();
				if (entity instanceof VillagerEntity) {
					tooltip.remove(Identifiers.CORE_OBJECT_NAME);
				}
			}
		});
	}
}