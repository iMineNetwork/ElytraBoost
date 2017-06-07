package nl.imine.elytraboost;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ElytraBooster {

    private static int highestID = 0;

    private final int ID;
    private String boosterName;

    private transient Location location;

    //saving the location a second time since Location cannot be saved easily (as far as I know)
    private String worldName;
    private double x;
    private double y;
    private double z;

    private boolean requiresUnlock;
    private List<UUID> playersUnlocked = new ArrayList<>();

    //saving players that have a cooldown would result in them never being removed upon reload/restart.
    //that's why I'm going to exclude them fromt saving
    private transient List<UUID> cooldownPlayers = new ArrayList<>();
    private long cooldownTime;

    private double effectRadius;
    private float boostPower;

    public boolean isInRange(Location location) {
        return getLocation().distance(location) <= effectRadius;
    }

    public boolean hasUnlocked(Player player) {
        return !requiresUnlock || playersUnlocked.contains(player.getUniqueId());
    }

    public boolean isOnCooldown(Player player) {
        return cooldownPlayers.contains(player.getUniqueId());
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return boosterName;
    }

    public void setName(String name) {
        this.boosterName = name;
    }

    public Location getLocation() {
        if (location == null) {
            location = new Location(Bukkit.getWorld(worldName), x, y, z);
        }
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;

        this.worldName = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public boolean isRequiresUnlock() {
        return requiresUnlock;
    }

    public void setRequiresUnlock(boolean requiresUnlock) {
        this.requiresUnlock = requiresUnlock;
    }

    public double getEffectRadius() {
        return effectRadius;
    }

    public void setEffectRadius(double effectRadius) {
        this.effectRadius = effectRadius;
    }

    public float getBoostPower() {
        return boostPower;
    }

    public void setBoostPower(float boostPower) {
        this.boostPower = boostPower;
    }

    public List<UUID> getPlayersUnlocked() {
        return playersUnlocked;
    }

    public List<UUID> getCooldownPlayers() {
        return cooldownPlayers;
    }

    public void applyBoost(Player player) {
        try{
        player.setVelocity(new Vector(player.getVelocity().getX() * boostPower, player.getVelocity().getY(), player.getVelocity().getZ() * boostPower));
        cooldownPlayers.add(player.getUniqueId());
        Bukkit.getScheduler().scheduleSyncDelayedTask(ElytraBoostPlugin.getPlugin(), () -> {
            cooldownPlayers.remove(player.getUniqueId());
        }, cooldownTime);
        }catch(Exception e){
            player.sendMessage("Error");
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    public ElytraBooster(String name, Location location, boolean requiresUnlock, long cooldownTime, double effectRadius, float boostPower) {
        highestID++;
        this.ID = highestID;

        this.boosterName = name;

        this.location = location;

        this.worldName = location.getWorld().getName();
        System.out.println(this.worldName + " is the name of the world");
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();

        requiresUnlock = false;

        this.effectRadius = effectRadius;
        this.boostPower = boostPower;
        this.cooldownTime = cooldownTime;

    }

    public ElytraBooster() {
        highestID++;
        this.ID = highestID;
        
    }

    public void showLocation() {

        //creating a task to show 5 redstone particles every tick, stopping that tast after 5 seconds
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(ElytraBoostPlugin.getPlugin(), () -> {
            location.getWorld().spawnParticle(Particle.REDSTONE, getLocation(), 5);
        }, 1l, 1L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(ElytraBoostPlugin.getPlugin(), () -> {
            Bukkit.getScheduler().cancelTask(taskId);
        }, 5 * 20l);
    }

}
