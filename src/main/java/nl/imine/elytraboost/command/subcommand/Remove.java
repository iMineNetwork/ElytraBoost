package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import nl.imine.elytraboost.ElytraBooster;
import nl.imine.elytraboost.ElytraBoosterManager;
import nl.imine.elytraboost.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Remove implements SubCommand {

    @Override
    public String getSubCommand() {
        return "remove";
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
        Bukkit.getServer().getOnlinePlayers().forEach(p -> {
            p.sendMessage("BOOSTERS");
            ElytraBoosterManager.getInstance().getBoosters().forEach(b -> {
                p.sendMessage(b.getName());
            });

        });/*
        String name = "";
        int ID = 0;

        boolean usingID = false;

        try {
            ID = Integer.parseInt(args[1]);
            usingID = true;
        } catch (NumberFormatException ex) {

        }

        for (ElytraBooster booster : ElytraBoosterManager.getInstance().getBoosters()) {
            if (usingID) {
                if (booster.getID() == ID) {
                    ElytraBoosterManager.getInstance().getBoosters().remove(booster);
                    return true;
                }
            } else {
                if (booster.getName().equals(name)) {
                    ElytraBoosterManager.getInstance().getBoosters().remove(booster);
                    return true;
                }
            }

        }
*/
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
