package nl.imine.elytraboost.settings;

import nl.imine.minigame.cluedo.settings.Setting;
import org.bukkit.configuration.file.FileConfiguration;

public class Settings {

    private final FileConfiguration CONFIGURATION;

    public Settings(FileConfiguration configuration) {
        this.CONFIGURATION = configuration;
    }

    public void createDefaults() {
        CONFIGURATION.addDefault(Setting.DEFAULT_COOLDOWN_TIME, 30);
        CONFIGURATION.addDefault(Setting.DEFAULT_EFFECT_RADIUS, 3);
        CONFIGURATION.addDefault(Setting.DEFAULT_BOOST_POWER, 2);
        
        CONFIGURATION.options().copyDefaults(true);
    }

    public String getString(String configPath) {
        return CONFIGURATION.getString(configPath);
    }

    public int getInt(String configPath) {
        return CONFIGURATION.getInt(configPath);
    }

    public double getDouble(String configPath) {
        return CONFIGURATION.getDouble(configPath);
    }

    public long getLong(String configPath) {
        return CONFIGURATION.getLong(configPath);
    }

    public float getFloat(String configPath) {
        return (float) CONFIGURATION.getDouble(configPath);
    }

}
