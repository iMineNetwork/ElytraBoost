/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import nl.imine.elytraboost.ElytraBooster;
import nl.imine.elytraboost.ElytraBoosterManager;
import nl.imine.elytraboost.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Dennis
 */
public class HideLocation implements SubCommand {

    @Override
    public String getSubCommand() {
        return "hidelocation";
    }

    @Override
    public String getPermission() {
        return "Imine.elytraboost.hidelocation";
    }

    @Override
    public List<String> getUsage() {
        List<String> ret = new ArrayList<>();

        ret.add("/elytraboost hidelocation <name>");
        ret.add("/elytraboost hidelocation <id>");
        ret.add("/elytraboost hidelocation all");

        return ret;
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String name = "";
        int id = 0;
        boolean usingID = false;

        try {
            id = Integer.parseInt(args[1]);
            usingID = true;
        } catch (NumberFormatException e) {
            name = args[1];
        }

        ElytraBooster booster = null;

        for (ElytraBooster elytraBooster : ElytraBoosterManager.getInstance().getBoosters()) {
            if (usingID ? elytraBooster.getID() == id : elytraBooster.getName().equals(name)) {
                booster = elytraBooster;
                break;
            }
        }

        if (booster == null && !name.equals("all")) {
            return false;
        }

        if (name.equals("all")) {
            ElytraBoosterManager.getInstance().getBoosters().forEach(booster1 -> {
                booster1.stopShowingLocation();
            });
            return true;
        }

        booster.stopShowingLocation();

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }

}
