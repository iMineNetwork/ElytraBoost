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
import nl.imine.elytraboost.Util;
import nl.imine.elytraboost.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Dennis
 */
public class ShowLocation implements SubCommand {

    @Override
    public String getSubCommand() {
        return "showlocation";
    }

    @Override
    public String getDescription() {
        return "Show the location of one or more elytraboosters.";
    }

    @Override
    public String getPermission() {
        return "Imine.elytraboost.showlocation";
    }

    @Override
    public List<String> getUsage() {
        List<String> ret = new ArrayList<>();

        ret.add("/elytraboost showlocation <name>");
        ret.add("/elytraboost showlocation <id>");
        ret.add("/elytraboost showlocation all");

        return ret;
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        ElytraBooster booster = Util.getBooster(args[1]);

        if (booster == null && !args[1].equals("all")) {
            return false;
        }

        if (args[1].equals("all")) {
            ElytraBoosterManager.getInstance().getBoosters().forEach(booster1 -> {
                booster1.showLocation();
            });
            return true;
        } else {
            booster.showLocation();
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>(); //sending an empty arraylist prevents tabcomplete instead of suggesting playernames
    }

}
