/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.imine.elytraboost.button;

import nl.imine.api.gui.Button;
import nl.imine.api.gui.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Dennis
 */
public class ButtonToggleOnOff extends Button {

    ItemStack itemOn;
    ItemStack itemOff;

    String enabledText;
    String disabledText;
    String enabledCommand;
    String disabledCommand;

    private ButtonToggleOnOff(ItemStack is, int slot) {
        super(is, slot);
    }

    public ButtonToggleOnOff(ItemStack itemOn, ItemStack itemOff, int slot, String enabledText, String disabledText, String enabledCommand, String disabledCommand) {
        super(itemOn, slot);

        this.itemOn = itemOn;
        this.itemOff = itemOff;

        this.enabledText = enabledText;
        this.disabledText = disabledText;
        this.enabledCommand = enabledCommand;
        this.disabledCommand = disabledCommand;
    }

    @Override
    public void doAction(Player player, Container container, ClickType clickType) {
        super.doAction(player, container, clickType);
        if (itemStack == itemOn) {
            itemStack = itemOn;
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(enabledText);
            player.performCommand(disabledCommand);
            itemStack.setItemMeta(meta);
        } else {
            itemStack = itemOff;
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(disabledText);
            player.performCommand(enabledCommand);
            itemStack.setItemMeta(meta);
        }
        container.refresh();
    }

    public ButtonToggleOnOff ButtonToggleOnOff(){
        
        return new ButtonToggleOnOff(itemOn, slot);
    }
}
