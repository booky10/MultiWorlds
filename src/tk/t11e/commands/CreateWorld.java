package tk.t11e.commands;
// Created by booky10 in MultiWorlds (17:41 08.10.19)

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.t11e.main.Main;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class CreateWorld implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("createWorld")) {
                if (args.length == 1) {
                    if (!Main.getWorldNames().contains(args[0])) {
                        Main.worldList.add(args[0]);
                        sender.sendMessage(Main.PREFIX + "§eCreating World...");
                        Bukkit.createWorld(new WorldCreator(args[0]));
                        sender.sendMessage(Main.PREFIX + "§aSuccessfully created world!");
                    } else
                        sender.sendMessage(Main.PREFIX + "This world already exits!");
                } else
                    return false;
            } else
                player.sendMessage(Main.NO_PERMISSION);
        } else {
            if (args.length == 1) {
                if (!Main.getWorldNames().contains(args[0])) {
                    Main.worldList.add(args[0]);
                    sender.sendMessage("Creating World...");
                    Bukkit.createWorld(new WorldCreator(args[0]));
                    sender.sendMessage("Successfully created world!");
                } else
                    sender.sendMessage("This world already exits!");
            } else
                return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}