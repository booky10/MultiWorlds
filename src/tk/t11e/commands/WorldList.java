package tk.t11e.commands;
// Created by booky10 in T11E (21:21 29.07.19)

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.t11e.main.Main;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class WorldList implements TabCompleter, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("worldList"))
                if (args.length == 0) {
                    player.sendMessage("§e---§6[Worlds]§e---");
                    Bukkit.getWorlds().forEach(world -> player.sendMessage("§e  -§6 " + world.getName()));
                } else
                    return false;
            else
                player.sendMessage(Main.NO_PERMISSION);
        } else if (args.length == 0) {
            sender.sendMessage("---[Worlds]---");
            Bukkit.getWorlds().forEach(world -> sender.sendMessage("  - " + world.getName()));
        } else
            return false;
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}