package tk.t11e.backup.commands;
// Created by booky10 in MultiWorlds (15:15 15.12.19)

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.t11e.backup.BackupMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class Backup implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("backup"))
                if (args.length == 1)
                    if (args[0].equalsIgnoreCase("backup"))
                        try {
                            if (BackupMain.makeBackup())
                                player.sendMessage(BackupMain.PREFIX + "§aSuccessfully made backup!");
                            else
                                player.sendMessage(BackupMain.PREFIX + "Something went wrong while " +
                                        "making backup!");
                        } catch (IOException exception) {
                            System.out.println(exception.getMessage());
                            player.sendMessage(BackupMain.PREFIX + exception.getMessage());
                            exception.printStackTrace();
                        }
                    else
                        return false;
                else
                    return false;
            else
                player.sendMessage(BackupMain.NO_PERMISSION);
        } else if (args.length == 1)
            if (args[0].equalsIgnoreCase("backup"))
                try {
                    if (BackupMain.makeBackup())
                        sender.sendMessage("Successfully made backup!");
                    else
                        sender.sendMessage("Something went wrong while making backup!");
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                    sender.sendMessage(exception.getMessage());
                }
            else
                return false;
        else
            return false;
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        list.add("backup");
        return list;
    }
}