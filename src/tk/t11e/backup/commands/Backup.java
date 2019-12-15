package tk.t11e.backup.commands;
// Created by booky10 in MultiWorlds (15:15 15.12.19)

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.t11e.multiworlds.main.Main;

import java.util.ArrayList;
import java.util.List;

public class Backup implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("")) {

            } else
                player.sendMessage(Main.NO_PERMISSION);
        } else {

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        return list;
    }
}