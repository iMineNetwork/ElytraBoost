/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import nl.imine.elytraboost.ElytraBoosterManager;
import nl.imine.elytraboost.Util;
import nl.imine.elytraboost.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Dennis
 */
public class BoosterList implements SubCommand {

    @Override
    public String getSubCommand() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Shows a list of all elytraboosters.";
    }

    @Override
    public String getPermission() {
        return "Imine.elytraboost.list";
    }

    @Override
    public List<String> getUsage() {
        return null;
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        ElytraBoosterManager.getInstance().getBoosters().forEach(booster -> {

            ChatColor color = (booster.isRequiresUnlock()) ? ChatColor.RED : ChatColor.GREEN;
            ChatColor darkColor = (booster.isRequiresUnlock()) ? ChatColor.DARK_RED : ChatColor.DARK_GREEN;
            
            //this is only 1 chat line
            player.spigot().sendMessage(
                    new ComponentBuilder("[" + booster.getID() + "]").color(darkColor).bold(true)
                            .append(" " + booster.getName()).color(color).event(
                            new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    new ComponentBuilder("Name: ")
                                            .color(ChatColor.GRAY).append(booster.getName() + "\n").color(color)
                                            .append("Id: ").color(ChatColor.GRAY).append(booster.getID() + "\n").color(color)
                                            .append("Always unlocked: ").color(ChatColor.GRAY).append(!booster.isRequiresUnlock() + "\n").color(color)
                                            .append("Power: ").color(ChatColor.GRAY).append(booster.getBoostPower() + "\n").color(color)
                                            .append("Location: ").color(ChatColor.GRAY).append("World: " + booster.getLocation().getWorld().getName() + ", X: "
                                            + Util.round(booster.getLocation().getX(), 2) + ", Y: "
                                            + Util.round(booster.getLocation().getY(), 2) + ", Z: "
                                            + Util.round(booster.getLocation().getZ(), 2) + "\n").color(color)
                                            .append("EffectRadius: ").color(ChatColor.GRAY).append(booster.getEffectRadius() + "").color(color)
                                            .append("\n\n" + "Click to edit this booster").color(ChatColor.DARK_GRAY)
                                            .create()
                            )
                    ).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/elytraboost edit " + booster.getID() + " ")).create()
            );
            
        });
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>(); //sending an empty arraylist prevents tabcomplete instead of suggesting playernames
    }

}
