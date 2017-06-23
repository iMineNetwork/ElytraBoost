package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import nl.imine.elytraboost.ElytraBoostPlugin;
import nl.imine.elytraboost.ElytraBooster;
import nl.imine.elytraboost.ElytraBoosterManager;
import nl.imine.elytraboost.command.SubCommand;
import nl.imine.minigame.cluedo.settings.Setting;
import org.bukkit.ChatColor;
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
        player.sendMessage("args: " + args.length + "");
        String name = "";
        Location location = player.getLocation();

        float boostPower = ElytraBoostPlugin.getSettings().getLong(Setting.DEFAULT_BOOST_POWER);
        double effectRadius = ElytraBoostPlugin.getSettings().getLong(Setting.DEFAULT_EFFECT_RADIUS);
        long cooldownTime = ElytraBoostPlugin.getSettings().getLong(Setting.DEFAULT_COOLDOWN_TIME);

        boolean requiresUnlock = false;

        if (!(args.length == 2 || args.length == 5 || args.length == 9)) { //the only valid arg lengths are 2, 5 and 9 long
            return false;
        }

        if (args.length >= 2) {
            name = args[1];
            for (ElytraBooster booster : ElytraBoosterManager.getInstance().getBoosters()) {
                if(booster.getName().equals(name)){
                    player.sendMessage(ChatColor.RED + "A booster with the name " + name + " already exists.");
                    return false;
                }
            }

        }

        if (args.length >= 5) {
            try {
                location = new Location(player.getWorld(), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
            } catch (NumberFormatException nfe) {
                player.sendMessage(ChatColor.RED + "something went wrong while reading the location");
                return false;
            }
        }

        if (args.length == 9) {
            try {
                boostPower = Float.parseFloat(args[5]);
                effectRadius = Double.parseDouble(args[6]);
                cooldownTime = Long.parseLong(args[7]);

                requiresUnlock = args[8].equalsIgnoreCase("true") || args[8].equals("1");
                player.sendMessage(args[8] + ": " + requiresUnlock);
            } catch (NumberFormatException nfe) {
                player.sendMessage(ChatColor.RED + "something went wrong while reading the properties");
                return false;
            }
        }

        ElytraBoosterManager.getInstance()
                .createBooster(name, location, requiresUnlock, cooldownTime, effectRadius, boostPower);
        sender.sendMessage(
                "CREATED " + name);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }

}
