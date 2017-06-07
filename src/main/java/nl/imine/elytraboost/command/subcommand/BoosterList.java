/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import nl.imine.api.gui.Container;
import nl.imine.api.gui.button.ButtonCommand;
import nl.imine.api.iMineAPI;
import nl.imine.elytraboost.ElytraBooster;
import nl.imine.elytraboost.ElytraBoosterManager;
import nl.imine.elytraboost.Util;
import nl.imine.elytraboost.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
    public String getPermission() {
        return "";
    }

    @Override
    public List<String> getUsage() {
        return new ArrayList<>();
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        Container inv = iMineAPI.createContainer("Boosters", 54);

        List<ButtonCommand> defaultUnlockedBoosters = new ArrayList<>();
        List<ButtonCommand> defaultLockedBoosters = new ArrayList<>();

        for (ElytraBooster booster : ElytraBoosterManager.getInstance().getBoosters()) {

            ItemStack boosterStack = new ItemStack((booster.isRequiresUnlock()) ? Material.REDSTONE_BLOCK : Material.EMERALD_BLOCK);
            boosterStack.getItemMeta().addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

            boosterStack.setAmount(booster.getID());
            ItemMeta meta = boosterStack.getItemMeta();
            meta.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + booster.getName() + ChatColor.DARK_RED + ChatColor.BOLD + " [" + booster.getID() + "]");
            //

           /* List<String> lore = new ArrayList<>();

            lore.add(ChatColor.GRAY + "Name: " + ChatColor.RED + booster.getName());
            lore.add(ChatColor.GRAY + "Id: " + ChatColor.RED + booster.getID());

            lore.add(ChatColor.GRAY + "Required to unlock: " + ChatColor.RED + booster.isRequiresUnlock());
            lore.add(ChatColor.GRAY + "Power: " + ChatColor.RED + booster.getBoostPower());
            lore.add(ChatColor.GRAY + "Location: "
                    + ChatColor.RED + "World: " + booster.getLocation().getWorld().getName()
                    + ChatColor.RED + ", X: " + Util.round(booster.getLocation().getX(), 2)
                    + ChatColor.RED + ", Y: " + Util.round(booster.getLocation().getY(), 2)
                    + ChatColor.RED + ", Z: " + Util.round(booster.getLocation().getZ(), 2));
            lore.add(ChatColor.GRAY + "EffectRadius: " + ChatColor.RED + booster.getEffectRadius());

            meta.setLore(lore);*/
            boosterStack.setItemMeta(meta);
            if (booster.isRequiresUnlock()) {
                defaultLockedBoosters.add(new ButtonCommand(boosterStack, inv.getFreeSlot(), "say hi"));
            } else {
                defaultUnlockedBoosters.add(new ButtonCommand(boosterStack, inv.getFreeSlot(), "say hello"));
            }

        }
        defaultLockedBoosters.stream().forEach(booster -> {
            inv.addButton(booster);
        });

        defaultUnlockedBoosters.stream().forEach(booster -> {
            inv.addButton(booster);
        });
        
        inv.open(player);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }

}
