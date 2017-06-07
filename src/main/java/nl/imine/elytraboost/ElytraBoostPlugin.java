package nl.imine.elytraboost;

import nl.imine.elytraboost.command.ElytraBoostCommand;
import nl.imine.elytraboost.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ElytraBoostPlugin extends JavaPlugin {

    private static Plugin plugin;
    private FileConfiguration Config;
    private static Settings settings;

    @Override
    public void onEnable() {
        plugin = this;
        settings = new Settings(this.getConfig());
        settings.createDefaults();

        Bukkit.getServer().getPluginManager().registerEvents(new ElytraBoostListener(), ElytraBoostPlugin.getPlugin());

        this.getCommand("elytraboost").setExecutor(new ElytraBoostCommand());
        this.getCommand("elytraboost").setTabCompleter(new ElytraBoostCommand());

        ElytraBoostCommand.init();

        ElytraBoosterManager.getInstance().loadBooster();
    }

    @Override
    public void onDisable() {
        ElytraBoosterManager.getInstance().saveBoosters();
        plugin = null;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static Settings getSettings() {
        return settings;
    }
}
