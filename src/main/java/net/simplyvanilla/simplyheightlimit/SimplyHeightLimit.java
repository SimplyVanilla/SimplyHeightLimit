package net.simplyvanilla.simplyheightlimit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

public final class SimplyHeightLimit extends JavaPlugin {

    private HeightLimiter limiter;
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

        int maxHeight = config.getInt("limit");
        double damage = config.getDouble("damage");
        double intervalSeconds = config.getDouble("interval");

        int intervalTicks = (int) (20.0 * intervalSeconds); //Converting seconds to ticks

        this.limiter = new HeightLimiter(maxHeight, intervalTicks, damage, this);
        this.limiter.startAsyncChecking();
    }

    private void initConfig() {
        try {
            this.config = loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "Failed to load config.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private FileConfiguration loadConfig() throws IOException {
        File dataFolder = getDataFolder();

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File configFile = new File(dataFolder, "config.yml");

        if (!configFile.exists()) {
            Files.copy(getClassLoader().getResourceAsStream("config.yml"), configFile.toPath());
        }

        return YamlConfiguration.loadConfiguration(configFile);
    }
}
