package net.simplyvanilla.simplyheightlimit;

import org.bukkit.plugin.java.JavaPlugin;

public final class SimplyHeightLimit extends JavaPlugin {

    private HeightLimiter limiter;

    @Override
    public void onEnable() {
        // Plugin startup logic

        initLimiter();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void initLimiter() {
        this.limiter = new HeightLimiter(256, 60, 0.5, this);
        this.limiter.startAsyncChecking();
    }
}
