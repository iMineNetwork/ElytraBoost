/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.elytraboost.button;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import nl.imine.api.gui.Button;
import nl.imine.api.gui.Container;
import nl.imine.api.iMineAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Dennis
 */
public class ButtonChatText extends Button {

    String text, hoverText, command;

    public ButtonChatText(ItemStack is, int slot) {
        this(is, slot, "ERROR", "ERROR", "ERROR");
    }

    public ButtonChatText(ItemStack is, int slot, String text, String hoverText, String command) {
        super(is, slot);
        this.text = text;
        this.hoverText = hoverText;
        this.command = command;
    }

    @Override
    public void doAction(Player player, Container container, ClickType clickType) {
        super.doAction(player, container, clickType);
        container.close();

        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "tellraw 1II1 [\"\",{\"text\":\" " + text + "\",\"color\":\"red\",\"bold\":true,\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + (command.startsWith("/") ? command : "/" + command) + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + hoverText + "\",\"color\":\"red\"}]}}}]");
//        Bukkit.dispatchCommand(, text)
    }

}
