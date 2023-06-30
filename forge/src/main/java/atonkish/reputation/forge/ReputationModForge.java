package atonkish.reputation.forge;

import atonkish.reputation.ReputationMod;
import net.minecraftforge.fml.common.Mod;

@Mod(ReputationMod.MOD_ID)
public class ReputationModForge {
    public ReputationModForge() {
        ReputationMod.init();
    }
}
