package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import nl.imine.elytraboost.ElytraBoostPlugin;
import nl.imine.elytraboost.ElytraBoosterManager;
import nl.imine.elytraboost.command.SubCommand;
import nl.imine.minigame.cluedo.settings.Setting;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Create implements SubCommand {
    
    @Override
    public String getSubCommand() {
        return "create";
    }
    
    @Override
    public String getPermission() {
        return "Imine.elytraboost.create";
    }
    
    @Override
    public List<String> getUsage() {
        List<String> ret = new ArrayList<>();
        
        ret.add("/elytraboost create <name> "); //length 2
        ret.add("/elytraboost create <name> <x> <y> <z> "); //length 5
        ret.add("/elytraboost create <name> <x> <y> <z> <power> <effectradius> <cooldowntime> <requiresUnlock> "); //length 9

        return ret;
    }
    
    @Override
    public boolean isPlayerOnly() {
        return true;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        Player player = (Player) sender;
        
        String name = "";
        Location location = player.getLocation();
        
        boolean requiresUnlock = false;
        
        long cooldownTime = ElytraBoostPlugin.getSettings().getLong(Setting.DEFAULT_COOLDOWN_TIME);
        double effectRadius = ElytraBoostPlugin.getSettings().getLong(Setting.DEFAULT_EFFECT_RADIUS);
        float boostPower = ElytraBoostPlugin.getSettings().getLong(Setting.DEFAULT_BOOST_POWER);
        
        
        if (!(args.length == 2 || args.length == 5 || args.length == 9)) { //the only valid arg lengths are 2, 5 and 9 long
            return true;
        }
        
        if (args.length >= 2) {
            name = args[1];
        }
        
        if (args.length >= 5) {
            try {
                location = new Location(player.getWorld(), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
        
        if (args.length == 9) {
            try {
                requiresUnlock = args[6].equalsIgnoreCase("true") || args[6].equals("1");
                cooldownTime = Long.parseLong(args[7]);
                effectRadius = Double.parseDouble(args[8]);
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
        
        ElytraBoosterManager.getInstance().createBooster(name, location, requiresUnlock, cooldownTime, effectRadius, boostPower);
        sender.sendMessage("CREATED " + name);
        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
    
}
