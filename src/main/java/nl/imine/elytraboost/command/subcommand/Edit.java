package nl.imine.elytraboost.command.subcommand;

import java.util.ArrayList;
import java.util.List;
import nl.imine.elytraboost.ElytraBoosterManager;
import nl.imine.elytraboost.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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

}
