/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import nl.imine.elytraboost.command.ElytraBoostCommand;
import nl.imine.elytraboost.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Dennis
 */
public class Help implements SubCommand {

    @Override
    public String getSubCommand() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Shows a list of all subcommands with a description.";
    }

    @Override
    public String getPermission() {
        return "Imine.elytraboost.help";
    }

    @Override
    public List<String> getUsage() {
        return null;
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "All elytraboost subcommands:");
        ElytraBoostCommand.getSubcommands().stream().forEach(subcommand -> {
            sender.sendMessage("   " + ChatColor.GOLD + subcommand.getSubCommand());
            sender.sendMessage("      " + ChatColor.WHITE + subcommand.getDescription());
        });
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>(); //sending an empty arraylist prevents tabcomplete instead of suggesting playernames
    }

}
