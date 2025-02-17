package atonkish.reputation.fabric;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;

import snownee.jade.api.*;

import atonkish.reputation.fabric.provider.IronGolemProvider;
import atonkish.reputation.fabric.provider.VillagerReputationProvider;
import atonkish.reputation.fabric.provider.VillagerSnitchProvider;

@WailaPlugin
public class ReputationPluginFabric implements IWailaPlugin {
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
