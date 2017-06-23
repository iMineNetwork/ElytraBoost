/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import nl.imine.api.gui.Button;
import nl.imine.api.gui.Container;
import nl.imine.api.gui.button.BrowseDirection;
import nl.imine.api.gui.button.ButtonChangePage;
import nl.imine.api.gui.button.ButtonCommand;
import nl.imine.api.iMineAPI;
import nl.imine.elytraboost.ElytraBooster;
import nl.imine.elytraboost.ElytraBoosterManager;
import nl.imine.elytraboost.Util;
import nl.imine.elytraboost.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
        Container inv = iMineAPI.createContainer("Boosters", 90);
        
        List<ItemStack> defaultUnlockedBoosters = new ArrayList<>();
        List<ItemStack> defaultLockedBoosters = new ArrayList<>();
        
        for (ElytraBooster booster : ElytraBoosterManager.getInstance().getBoosters()) {
            
            ItemStack boosterStack = new ItemStack((booster.isRequiresUnlock()) ? Material.REDSTONE_BLOCK : Material.EMERALD_BLOCK);
            
            ChatColor color = (booster.isRequiresUnlock()) ? ChatColor.RED : ChatColor.GREEN;
            ChatColor darkColor = (booster.isRequiresUnlock()) ? ChatColor.DARK_RED : ChatColor.DARK_GREEN;
            
            boosterStack.getItemMeta().addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
            
            boosterStack.setAmount(booster.getID() > boosterStack.getMaxStackSize() ? 1 : booster.getID());
            ItemMeta meta = boosterStack.getItemMeta();
            meta.setDisplayName("" + color + ChatColor.BOLD + booster.getName() + darkColor + ChatColor.BOLD + " [" + booster.getID() + "]");
            
            List<String> lore = new ArrayList<>();
            
            lore.add(ChatColor.GRAY + "Name: " + color + booster.getName());
            lore.add(ChatColor.GRAY + "Id: " + color + booster.getID());
            
            lore.add(ChatColor.GRAY + "Always unlocked: " + color + !booster.isRequiresUnlock());
            lore.add(ChatColor.GRAY + "Power: " + color + booster.getBoostPower());
            lore.add(ChatColor.GRAY + "Location: "
                    + color + "World: " + booster.getLocation().getWorld().getName()
                    + color + ", X: " + Util.round(booster.getLocation().getX(), 2)
                    + color + ", Y: " + Util.round(booster.getLocation().getY(), 2)
                    + color + ", Z: " + Util.round(booster.getLocation().getZ(), 2));
            lore.add(ChatColor.GRAY + "EffectRadius: " + color + booster.getEffectRadius());
            
            meta.setLore(lore);
            boosterStack.setItemMeta(meta);
            if (booster.isRequiresUnlock()) {
                defaultUnlockedBoosters.add(boosterStack);
            } else {
                defaultLockedBoosters.add(boosterStack);
            }
            
        }
        
        defaultUnlockedBoosters.stream().forEach(booster -> {
            int i = 0;
            boolean exit = false;
            while (!exit) {
                exit = true;
                for (Button b : inv.getButtons()) {
                    if (b.getSlot() == i) {
                        i++;
                        if (i % 45 >= 18) {
                            i = i + (45 - i % 45);
                        }
                        exit = false;
                        break;
                    }
                    
                }
            }
            inv.addButton(new ButtonCommand(booster, i, "elytraboost edit " + booster.getItemMeta().getLore().get(0).substring(10)));
        });
        
        defaultLockedBoosters.stream().forEach(booster -> {
            int i = 27;
            boolean exit = false;
            while (!exit) {
                exit = true;
                for (Button b : inv.getButtons()) {
                    if (b.getSlot() == i) {
                        i++;
                        if (i % 45 <= 18) {
                            i = i + (27 - i % 45);
                        }
                        exit = false;
                        break;
                    }
                    
                }
            }
                inv.addButton(new ButtonCommand(booster, i, "elytraboost edit " + booster.getItemMeta().getLore().get(0).substring(10)));
        });
        
        ButtonChangePage btnBack = new ButtonChangePage(inv, 48, BrowseDirection.PREVIOUS);
        ButtonChangePage btnNext = new ButtonChangePage(inv, 50, BrowseDirection.NEXT);
        
        inv.addStaticButton(btnBack);
        inv.addStaticButton(btnNext);
        
        inv.open(player);
        
        return true;
    }
    
    @Override
    
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
    
}
