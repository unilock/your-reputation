package atonkish.reputation.fabric;

import atonkish.reputation.ReputationMod;
import net.fabricmc.api.ModInitializer;

public class ReputationModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ReputationMod.init();
    }
}
