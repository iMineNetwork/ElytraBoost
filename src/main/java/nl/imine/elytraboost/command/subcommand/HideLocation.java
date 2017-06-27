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
public class HideLocation implements SubCommand {

    @Override
    public String getSubCommand() {
        return "hidelocation";
    }

    @Override
    public String getDescription() {
        return "hide the location of one or mor elytraboosters.";
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
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        ElytraBooster booster = Util.getBooster(args[1]);

        if (booster == null && args[1].equals("all")) {
            return false;
        }

        if (args[1].equals("all")) {
            ElytraBoosterManager.getInstance().getBoosters().forEach(booster1 -> {
                booster1.stopShowingLocation();
            });
            return true;
        } else {
            booster.stopShowingLocation();
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>(); //sending an empty arraylist prevents tabcomplete instead of suggesting playernames
    }

}
