package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import nl.imine.elytraboost.ElytraBooster;
import nl.imine.elytraboost.ElytraBoosterManager;
import nl.imine.elytraboost.Util;
import nl.imine.elytraboost.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Remove implements SubCommand {

    @Override
    public String getSubCommand() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "removes an existing elytrabooster.";
    }

    @Override
    public String getPermission() {
        return "Imine.elytraboost.remove";
    }

    @Override
    public List<String> getUsage() {

        List<String> ret = new ArrayList<>();

        ret.add("/elytraboost remove <name>");
        ret.add("/elytraboost remove <id>");

        return ret;

    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        ElytraBooster booster = Util.getBooster(args[1]);
        if (booster != null) {
            booster.stopShowingLocation();
            ElytraBoosterManager.getInstance().getBoosters().remove(booster);
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> names = new ArrayList<>();

        ElytraBoosterManager.getInstance().getBoosters()
                .stream()
                .filter(booster -> (args.length == 1 || booster.getName().contains(args[args.length - 1])))
                .forEach(booster -> {
                    names.add(booster.getName());
                });

        return names;
    }
}
