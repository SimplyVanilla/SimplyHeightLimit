package net.simplyvanilla.simplyheightlimit;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SimplyHeightLimit extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {

        initConfig();
        initLimiter();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void initLimiter() {
        HeightLimiter limiter;

        int maxHeight = config.getInt("limit");
        double damage = config.getDouble("damage");
        double intervalSeconds = config.getDouble("interval");

        int intervalTicks = (int) (20.0 * intervalSeconds); // Converting seconds to ticks

        limiter = new HeightLimiter(maxHeight, intervalTicks, damage, this);
        limiter.startAsyncChecking();
    }

    private void initConfig() {
        this.config = loadConfig();
    }

    private FileConfiguration loadConfig() {
        File dataFolder = getDataFolder();

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File configFile = new File(dataFolder, "config.yml");

        if (!configFile.exists()) {
            this.saveResource("config.yml", false);
        }

        return YamlConfiguration.loadConfiguration(configFile);
    }
}
