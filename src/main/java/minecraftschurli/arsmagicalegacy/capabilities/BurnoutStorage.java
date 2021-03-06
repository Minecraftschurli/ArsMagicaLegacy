package minecraftschurli.arsmagicalegacy.capabilities;

import minecraftschurli.arsmagicalegacy.api.capability.IBurnoutStorage;
import minecraftschurli.arsmagicalegacy.api.config.Config;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public class BurnoutStorage implements IBurnoutStorage {
    private float maxBurnout = Config.SERVER.DEFAULT_MAX_BURNOUT.get().floatValue();
    private float burnout = 0;

    @Override
    public float getBurnout() {
        return burnout;
    }

    @Override
    public float getMaxBurnout() {
        return maxBurnout;
    }

    @Override
    public void setMaxBurnout(float amount) {
        if (amount >= 0) maxBurnout = amount;
    }

    @Override
    public boolean setBurnout(float amount) {
        if (amount > maxBurnout) return false;
        burnout = amount;
        return true;
    }

    @Override
    public void decrease(float amount) {
        if (amount > 0) burnout = Math.max(0, getBurnout() - amount);
    }

    @Override
    public boolean increase(float amount) {
        if (amount > 0) {
            if (getBurnout() + amount > getMaxBurnout()) return false;
            burnout = Math.min(getBurnout() + amount, getMaxBurnout());
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "BurnoutStorage{maxBurnout=" + maxBurnout + ", burnout=" + burnout + "}";
    }
}
