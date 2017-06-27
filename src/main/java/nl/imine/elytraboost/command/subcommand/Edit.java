package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import nl.imine.elytraboost.ElytraBooster;
import nl.imine.elytraboost.ElytraBoosterManager;
import nl.imine.elytraboost.Util;
import nl.imine.elytraboost.command.SubCommand;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Edit implements SubCommand {

    @Override
    public String getSubCommand() {
        return "edit";
    }

    @Override
    public String getDescription() {
        return "edit the properties of an existing elytrabooster.";
    }

    @Override
    public String getPermission() {
        return "Imine.elytraboost.edit";
    }

    @Override
    public List<String> getUsage() {
        List<String> ret = new ArrayList<>();

        ret.add("/elytraboost edit <name> <property> <value>");
        ret.add("/elytraboost edit <ID> <property> <value>");

        return ret;
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        ElytraBooster booster = Util.getBooster(args[1]);

        if (booster == null) {
            player.sendMessage(ChatColor.RED + "Booster " + args[1] + " was not found");
            return false;
        }

        if (args.length == 2) {
//            showMenu(player, booster);
            player.sendMessage(ChatColor.RED + "please specify the property you want to change (use tab)");
            return true;
        }

        if (args.length == 3) {
            player.sendMessage(ChatColor.RED + "Invalid argument length, no value found");
            return false;
        }

        switch (args[2].toLowerCase()) {
            case "name":
                if (!ElytraBoosterManager.getInstance().getBoosters().stream().noneMatch(testBooster //preventing duplicate names
                        -> testBooster.getName().equalsIgnoreCase(args[2])
                        && booster.getID() != testBooster.getID())) {
                    return false;
                }
                booster.setName(args[3]);
                break;
            case "requiresunlock":
                booster.setRequiresUnlock(Boolean.parseBoolean(args[3]));
                break;
            case "cooldowntime":
                try {
                    Long l = Long.parseLong(args[3]);
                    booster.setCooldownTime(l);
                } catch (NumberFormatException nfe) {
                    return false;
                }
                break;
            case "effectradius":
                try {
                    double d = Double.parseDouble(args[3]);
                    booster.setEffectRadius(d);
                } catch (NumberFormatException nfe) {
                    return false;
                }
                break;
            case "power":
                try {
                    float d = Float.parseFloat(args[3]);
                    booster.setBoostPower(d);
                } catch (NumberFormatException nfe) {
                    return false;
                }
                break;
            case "location":
                try {
                    double x;
                    double y;
                    double z;

                    switch (args.length) {
                        case 3:
                            x = player.getLocation().getX();
                            y = player.getLocation().getY();
                            z = player.getLocation().getZ();
                            break;
                        case 6:
                            x = Double.parseDouble(args[3]);
                            y = Double.parseDouble(args[4]);
                            z = Double.parseDouble(args[5]);
                            break;
                        default:
                            return false;
                    }

                    Location loc = new Location(player.getWorld(), x, y, z);
                    booster.setLocation(loc);
                } catch (NumberFormatException nfe) {
                    return false;
                }
        }
        player.sendMessage(ChatColor.GREEN + "The booster has been edited, use " + ChatColor.DARK_GREEN + "/elytraboost save " + ChatColor.GREEN + "to save the changes.\n");
        player.sendMessage(ChatColor.GREEN + "The changes will be saved automatically when you restart the server");
        player.sendMessage(ChatColor.RED + "You can undo the changes by loading the previous config with " + ChatColor.DARK_RED + "/elytraboost load.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ret = new ArrayList<>();

        switch (args.length) {
            case 2:
                ElytraBoosterManager.getInstance().getBoosters()
                        .stream()
                        .filter(booster -> (args.length == 1 || booster.getName().contains(args[args.length - 1])))
                        .forEach(booster -> {
                            ret.add(booster.getName());
                        });
            case 3:
                ElytraBoosterManager.getInstance().getOptions()
                        .stream()
                        .filter(option -> (option.contains(args[args.length - 1])))
                        .forEach(option -> {
                            ret.add(option);
                        });
                break;
            default:
                return new ArrayList<>();
        }
        return ret;

    }

}
