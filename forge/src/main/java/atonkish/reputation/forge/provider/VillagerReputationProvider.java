package atonkish.reputation.forge.provider;

import atonkish.reputation.ReputationMod;
import atonkish.reputation.util.ReputationStatus;
import atonkish.reputation.util.cache.VillagerCache;
import com.google.common.cache.Cache;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

import java.util.Optional;

public enum VillagerReputationProvider implements IEntityComponentProvider, IServerDataProvider<Entity> {

    INSTANCE;

    public static final Identifier VILLAGER_REPUTATION_IDENTIFIER = new Identifier(ReputationMod.MOD_ID,
            "villager_reputation");
    public static final String REPUTATION_KEY = "ReputationModReputation";

    @Override
    public Identifier getUid() {
        return VillagerReputationProvider.VILLAGER_REPUTATION_IDENTIFIER;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.BODY + 100;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        NbtCompound data = accessor.getServerData();
        PlayerEntity player = accessor.getPlayer();
        VillagerEntity villager = (VillagerEntity) accessor.getEntity();

        VillagerCache.Data villagerData = VillagerReputationProvider.getVillagerData(data, player, villager);

        @Nullable
        Integer reputation = villagerData.getReputation();
        ReputationStatus status = ReputationStatus.getStatus(reputation);

        MutableText text = Text.translatable(status.getTranslateKey());

        if (reputation != null) {
            text = text.append(String.format(" (%d)", reputation));
        }

        text = text.formatted(status.getFormatting());

        tooltip.add(text);
    }

    @Override
    public final void appendServerData(NbtCompound data, ServerPlayerEntity player, World world, Entity entity,
            boolean showDetails) {
        VillagerEntity villager = (VillagerEntity) entity;

        int reputation = villager.getReputation(player);
        data.putInt(VillagerReputationProvider.REPUTATION_KEY, reputation);
    }

    private static VillagerCache.Data getVillagerData(NbtCompound data, PlayerEntity player, VillagerEntity villager) {
        Cache<VillagerEntity, VillagerCache.Data> villagerCache = VillagerCache.getOrCreate(player);
        VillagerCache.Data villagerData = Optional
                .ofNullable(villagerCache.getIfPresent(villager))
                .orElse(new VillagerCache.Data());

        @Nullable
        Integer reputation = data.contains(VillagerReputationProvider.REPUTATION_KEY)
                ? data.getInt(VillagerReputationProvider.REPUTATION_KEY)
                : null;
        if (reputation != null) {
            villagerData.setReputation(reputation);
        }

        villagerCache.put(villager, villagerData);

        return villagerData;
    }
}
