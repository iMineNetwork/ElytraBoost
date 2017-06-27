/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import nl.imine.elytraboost.ElytraBooster;
import nl.imine.elytraboost.Util;
import nl.imine.elytraboost.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 *
 * @author Dennis
 */
public class Teleport implements SubCommand {

    @Override
    public String getSubCommand() {
        return "teleport";
    }

    @Override
    public String getDescription() {
        return "teleport to the location of the booster";
    }

    @Override
    public String getPermission() {
        return "Imine.elytraboost.teleport";
    }

    @Override
    public List<String> getUsage() {
        List<String> ret = new ArrayList<>();
        ret.add("/elytraboost teleport <name>");
        ret.add("/elytraboost teleport <ID>");
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
            player.sendMessage("Booster " + args[1] + " was not found");
            return false;
        }

        player.teleport(booster.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>(); //sending an empty arraylist prevents tabcomplete instead of suggesting playernames
    }

}
