package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import nl.imine.api.gui.Container;
import nl.imine.api.gui.button.ButtonCommand;
import nl.imine.api.iMineAPI;
import nl.imine.elytraboost.BoosterPropertie;
import nl.imine.elytraboost.ElytraBooster;
import nl.imine.elytraboost.ElytraBoosterManager;
import nl.imine.elytraboost.Util;
import nl.imine.elytraboost.button.ButtonChatText;
import nl.imine.elytraboost.button.ButtonToggleOnOff;
import nl.imine.elytraboost.command.SubCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Edit implements SubCommand {

    @Override
    public String getSubCommand() {
        return "edit";
    }

    @Override
    public String getPermission() {
        return "Imine.elytraboost.edit";
    }

    @Override
    public List<String> getUsage() {
        List<String> ret = new ArrayList<>();

        ret.add("/elytraboost edit <name> <property> <value>");
        ret.add("/elytraboost edit <ID> <property> <value>");

        return ret;
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        String name = "";
        int id = 0;
        boolean usingID = false;

        try {
            id = Integer.parseInt(args[1]);
            usingID = true;
        } catch (NumberFormatException e) {
            name = args[1];
        }
        ElytraBooster booster = null;

        for (ElytraBooster elytraBooster : ElytraBoosterManager.getInstance().getBoosters()) {
            if (usingID ? elytraBooster.getID() == id : elytraBooster.getName().equals(name)) {
                booster = elytraBooster;
                break;
            }
        }

        if (booster == null) {
            player.sendMessage(ChatColor.RED + "Booster " + args[1] + " was not found");
            return false;
        }

        if (args.length == 2) {
//            showMenu(player, booster);
            player.sendMessage(ChatColor.RED + "please specify the property you want to change");
            return true;
        }

        if (args.length == 3) {
            player.sendMessage(ChatColor.RED + "Invalid argument length, no value found");
            return false;
        }

        switch (args[2].toLowerCase()) {
            case "name":
                for (ElytraBooster testBooster : ElytraBoosterManager.getInstance().getBoosters()) {
                    if (testBooster.getName().equalsIgnoreCase(args[2]) && booster.getID() != testBooster.getID()) {
                        return false;
                    }
                }
                booster.setName(args[3]);
                break;
            case "requiresunlock":
                booster.setRequiresUnlock(Boolean.parseBoolean(args[3]));
                break;
            case "cooldowntime":
                try {
                    Long l = Long.parseLong(args[3]);
                    booster.setCooldownTime(l);
                } catch (NumberFormatException nfe) {
                    return false;
                }
                break;
            case "effectradius":
                try {
                    double d = Double.parseDouble(args[3]);
                    booster.setEffectRadius(d);
                } catch (NumberFormatException nfe) {
                    return false;
                }
                break;
            case "power":
                try {
                    float d = Float.parseFloat(args[3]);
                    booster.setBoostPower(d);
                } catch (NumberFormatException nfe) {
                    return false;
                }
                break;
            case "location":
                try {
                    double x;
                    double y;
                    double z;

                    switch (args.length) {
                        case 3:
                            x = player.getLocation().getX();
                            y = player.getLocation().getY();
                            z = player.getLocation().getZ();
                            break;
                        case 6:
                            x = Double.parseDouble(args[3]);
                            y = Double.parseDouble(args[4]);
                            z = Double.parseDouble(args[5]);
                            break;
                        default:
                            return false;
                    }

                    Location loc = new Location(player.getWorld(), x, y, z);
                    booster.setLocation(loc);
                } catch (NumberFormatException nfe) {
                    return false;
                }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ret = new ArrayList<>();

        switch (args.length) {
            case 2:
                ElytraBoosterManager.getInstance().getBoosters()
                        .stream()
                        .filter(booster -> (args.length == 1 || booster.getName().contains(args[args.length - 1])))
                        .forEach(booster -> {
                            ret.add(booster.getName());
                        });
            case 3:
                ElytraBoosterManager.getInstance().getOptions()
                        .stream()
                        .filter(option -> (option.contains(args[args.length - 1])))
                        .forEach(option -> {
                            ret.add(option);
                        });
                break;
            default:
                return new ArrayList<>();
        }
        return ret;

    }

//    private void showMenu(Player player, ElytraBooster booster) {
//        Container inv = iMineAPI.createContainer("Edit booster: " + booster.getName(), 45, false, false);
//
//        ItemStack changeName = new ItemStack(Material.NAME_TAG);
//        ItemMeta changeNameMeta = changeName.getItemMeta();
//        changeNameMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Change name");
//        changeName.setItemMeta(changeNameMeta);
//        ItemStack location = new ItemStack(Material.MAP);
//        ItemMeta locationMeta = location.getItemMeta();
//        locationMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "location");
//        List<String> locationLore = new ArrayList<>();
//        locationLore.add("");
//        locationLore.add(ChatColor.RED + "Click to set the location of the booster to your current position.");
//        locationLore.add(ChatColor.RED + "To use coordinates use:");
//        locationLore.add(ChatColor.RED + "     /elytraboost edit " + booster.getID() + " name <NAME> ");
//        locationLore.add(ChatColor.RED + "     OR");
//        locationLore.add(ChatColor.RED + "     /elytraboost edit " + booster.getName() + " name <NAME> ");
//        locationMeta.setLore(locationLore);
//        location.setItemMeta(locationMeta);
//
//        ItemStack delete = new ItemStack(Material.TNT);
//        ItemMeta deleteMeta = changeName.getItemMeta();
//        deleteMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Delete");
//        delete.setItemMeta(deleteMeta);
//
//        inv.addButton(new ButtonChatText(changeName, 0, "Click here to edit booster " + booster.getID() + "'s name", "edit the name", "/elytraboost edit " + booster.getID() + " name "));
//        inv.addButton(new ButtonCommand(location, 4, "elytraboost edit " + booster.getID() + " location " + Util.round(player.getLocation().getX(), 2) + " " + Util.round(player.getLocation().getY(), 2) + " " + Util.round(player.getLocation().getZ(), 2)));
//        inv.addButton(new ButtonCommand(delete, 6, "elytraboost remove " + booster.getID()));
//
//        
//        
//        
//        
//        ItemStack showHideBooster = new ItemStack(Material.INK_SACK);
//        showHideBooster.setDurability((short) (booster.isShowing() ? 10 : 8));
//        ItemMeta showHideBoosterMeta = showHideBooster.getItemMeta();
//        showHideBoosterMeta.setDisplayName(
//                (booster.isShowing()
//                ? ChatColor.GREEN + "" + ChatColor.GREEN + "" + ChatColor.BOLD + "Booster visible" + ChatColor.GRAY + " (click to hide)"
//                : ChatColor.RED + "" + ChatColor.BOLD + "Booster hidden" + ChatColor.GRAY + " (click to show)")
//        );
//        
//        showHideBooster.setItemMeta(showHideBoosterMeta);
//
//        inv.addButton(new ButtonToggleOnOff(showHideBooster, 34,
//                ChatColor.GREEN + "" + ChatColor.BOLD + "Booster visible" + ChatColor.GRAY + " (click to hide)",
//                ChatColor.RED + "" + ChatColor.BOLD + "Booster hidden" + ChatColor.GRAY + " (click to show)",
//                "elytraboost hidelocation " + booster.getID(),
//                "elytraboost showlocation " + booster.getID())
//        );
//
//        inv.addButton(new ButtonToggleOnOff(showHideBooster, 34,
//                ChatColor.GREEN + "" + ChatColor.BOLD + "Booster visible" + ChatColor.GRAY + " (click to hide)",
//                ChatColor.RED + "" + ChatColor.BOLD + "Booster hidden" + ChatColor.GRAY + " (click to show)",
//                "elytraboost hidelocation " + booster.getID(),
//                "elytraboost showlocation " + booster.getID())
//        );
//
//        inv.addButton(getPropertyChangerButtonCommand(booster, 64, 18, BoosterPropertie.effectradius, -1));
//        inv.addButton(getPropertyChangerButtonCommand(booster, 10, 19, BoosterPropertie.effectradius, -0.1));
//        inv.addButton(getPropertyChangerButtonCommand(booster, 1, 20, BoosterPropertie.effectradius, -0.01));
//
//        inv.addButton(getPropertyChangerButtonCommand(booster, 1, 22, BoosterPropertie.effectradius, 0.01));
//        inv.addButton(getPropertyChangerButtonCommand(booster, 10, 23, BoosterPropertie.effectradius, 0.1));
//        inv.addButton(getPropertyChangerButtonCommand(booster, 64, 24, BoosterPropertie.effectradius, 1));
//
//        inv.addButton(getPropertyChangerButtonCommand(booster, 64, 27, BoosterPropertie.power, -1));
//        inv.addButton(getPropertyChangerButtonCommand(booster, 10, 28, BoosterPropertie.power, -0.1));
//        inv.addButton(getPropertyChangerButtonCommand(booster, 1, 29, BoosterPropertie.power, -0.01));
//
//        inv.addButton(getPropertyChangerButtonCommand(booster, 1, 31, BoosterPropertie.power, 0.01));
//        inv.addButton(getPropertyChangerButtonCommand(booster, 10, 32, BoosterPropertie.power, 0.1));
//        inv.addButton(getPropertyChangerButtonCommand(booster, 64, 33, BoosterPropertie.power, 1));
//
//        inv.addButton(getPropertyChangerButtonCommand(booster, 64, 36, BoosterPropertie.cooldowntime, -1));
//        inv.addButton(getPropertyChangerButtonCommand(booster, 10, 37, BoosterPropertie.cooldowntime, -0.1));
//        inv.addButton(getPropertyChangerButtonCommand(booster, 1, 38, BoosterPropertie.cooldowntime, -0.01));
//
//        inv.addButton(getPropertyChangerButtonCommand(booster, 1, 40, BoosterPropertie.cooldowntime, 0.01));
//        inv.addButton(getPropertyChangerButtonCommand(booster, 10, 41, BoosterPropertie.cooldowntime, 0.1));
//        inv.addButton(getPropertyChangerButtonCommand(booster, 64, 42, BoosterPropertie.cooldowntime, 1));
//
//        inv.open(player);
//    }

//    private ButtonCommand getPropertyChangerButtonCommand(ElytraBooster booster, int amountOfPanes, int slot, BoosterPropertie property, double valueChange) {
//
//        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
//        item.setDurability((short) (valueChange > 0 ? 5 : 14));
//        item.setAmount(amountOfPanes);
//        ItemMeta meta = item.getItemMeta();
//        meta.setDisplayName((valueChange > 0 ? ChatColor.GREEN + "+" : ChatColor.RED) + "" + valueChange);
//        List<String> lore = new ArrayList<>();
//        lore.add((valueChange > 0 ? ChatColor.GREEN + "add +" + valueChange + " to " + property.getProperty() : ChatColor.RED + "remove " + valueChange + " from " + property.getProperty()) + "");
//        meta.setLore(lore);
//        item.setItemMeta(meta);
//
//        return new ButtonCommand(item, slot, "elytraboost edit " + booster.getID() + " " + property.getProperty() + " " + booster.getEffectRadius());
//    }

}
