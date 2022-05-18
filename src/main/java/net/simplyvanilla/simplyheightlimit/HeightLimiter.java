package net.simplyvanilla.simplyheightlimit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HeightLimiter {

    private final int maxHeight;
    private final JavaPlugin plugin;
    private final int period;
    private final double damage;

    public HeightLimiter(int maxHeight, int period, double damage, JavaPlugin plugin) {
        this.maxHeight = maxHeight;
        this.plugin = plugin;
        this.period = period;
        this.damage = damage;
    }

    public void checkHeightOnAll() {
        var players = Bukkit.getOnlinePlayers();

        for (Player p : players) {
            checkAndAdjustPlayer(p);
        }
    }

    private void checkAndAdjustPlayer(Player p) {
        var loc = p.getLocation();
        int height = loc.getBlockY();

        if (height > maxHeight) {
            var newLoc = new Location( loc.getWorld(), loc.getBlockX(), maxHeight, loc.getBlockZ());
            newLoc.setYaw(loc.getYaw());
            newLoc.setPitch(loc.getPitch());

            //Going back to sync to access bukkit APIs
            Bukkit.getScheduler().runTask(this.plugin, () -> {
                p.sendMessage(ChatColor.RED + "You are too high!");
                p.teleport(newLoc);
                p.setFlying(false);
                p.damage(this.damage);
                p.setGliding(false);
            });
        }
    }


    public void startAsyncChecking() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, () -> {
            checkHeightOnAll();
        }, 0, this.period);
    }

}
