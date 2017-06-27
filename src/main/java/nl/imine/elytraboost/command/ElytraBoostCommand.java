/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.elytraboost.command;

import java.util.ArrayList;
import java.util.List;
import nl.imine.elytraboost.command.subcommand.BoosterList;
import nl.imine.elytraboost.command.subcommand.Create;
import nl.imine.elytraboost.command.subcommand.Edit;
import nl.imine.elytraboost.command.subcommand.Help;
import nl.imine.elytraboost.command.subcommand.HideLocation;
import nl.imine.elytraboost.command.subcommand.Load;
import nl.imine.elytraboost.command.subcommand.Remove;
import nl.imine.elytraboost.command.subcommand.Save;
import nl.imine.elytraboost.command.subcommand.ShowLocation;
import nl.imine.elytraboost.command.subcommand.Teleport;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 *
 * @author Dennis
 */
public class ElytraBoostCommand implements CommandExecutor, TabCompleter {

    private static List<SubCommand> subcommands;

    public static List<SubCommand> getSubcommands() {
        return subcommands;
    }

    public static void init() {
        subcommands = new ArrayList<>();
        subcommands.add(new Create());
        subcommands.add(new Edit());
        subcommands.add(new BoosterList());
        subcommands.add(new Help());
        subcommands.add(new HideLocation());
        subcommands.add(new Load());
        subcommands.add(new Remove());
        subcommands.add(new Save());
        subcommands.add(new ShowLocation());
        subcommands.add(new Teleport());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean isPlayer = sender instanceof Player;

        if (args == null || args.length == 0) {
            subcommands.forEach(subcommand -> {
                sender.sendMessage(ChatColor.RED + "/elytraboost " + subcommand.getSubCommand() + (subcommand.getUsage().size() > 1 ? " ..." : ""));
            });
            return true;
        }

        for (SubCommand subcommand : subcommands) {
            if (subcommand.getSubCommand().equals(args[0])) {

                //send usages if the subcommand only is not enough to run the full command
                if (args.length == 1 && subcommand.getUsage() != null) {
                    subcommand.getUsage()
                            .forEach(usage -> {
                                sender.sendMessage(ChatColor.RED + "" + usage);
                            });
                    return true;

                }

                if (subcommand.isPlayerOnly() && !isPlayer) {
                    sender.sendMessage("You are not a player");
                    return true;
                }

                if (subcommand.onCommand(sender, command, label, args)) {
                    System.out.println(sender.getName() + " has was succesfull in running the elytrabooster command");
                } else {
                    System.out.println(sender.getName() + " has was unsuccesfull in running the elytrabooster command");
                }
                return true;
            }
        }

        sender.sendMessage("subcommand not found");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ret = new ArrayList<>();

        if (args.length == 1) {
            subcommands
                    .stream()
                    .filter(subcommand -> subcommand.getSubCommand().startsWith(args[0]))
                    .forEach(subcommand -> {
                        ret.add(subcommand.getSubCommand());
                    });
            return ret;
        }

        for (SubCommand subcommand : subcommands) {
            if (subcommand.getSubCommand().equals(args[0])) {
                return subcommand.onTabComplete(sender, command, alias, args);
            }
        }
        return ret;
    }

}
