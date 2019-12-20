package tk.t11e.backup.commands;
// Created by booky10 in MultiWorlds (15:15 15.12.19)

import org.bukkit.Bukkit;
import org.bukkit.World;
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
                if (args.length == 3)
                    if (args[0].equalsIgnoreCase("backup"))
                        try {
                            if (BackupMain.makeBackup(args[1], args[2]))
                                player.sendMessage(BackupMain.PREFIX + "§aSuccessfully made backup!");
                            else
                                player.sendMessage(BackupMain.PREFIX + "Something went wrong while " +
                                        "making backup!");
                        } catch (IOException exception) {
                            System.out.println(exception.getMessage());
                            player.sendMessage(BackupMain.PREFIX + exception.getMessage());
                            exception.printStackTrace();
                        }
                    else if (args[0].equalsIgnoreCase("restore"))
                        try {
                            if (BackupMain.restoreBackup(args[1], args[2]))
                                player.sendMessage(BackupMain.PREFIX + "§aSuccessfully restored " +
                                        "backup!");
                            else
                                player.sendMessage(BackupMain.PREFIX + "Something went wrong while " +
                                        "restoring backup! Does the file exits?");
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
        } else if (args.length == 3)
            if (args[0].equalsIgnoreCase("backup"))
                try {
                    if (BackupMain.makeBackup(args[1], args[2]))
                        sender.sendMessage("Successfully made backup!");
                    else
                        sender.sendMessage("Something went wrong while making backup!");
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                    exception.printStackTrace();
                }
            else if (args[0].equalsIgnoreCase("restore"))
                try {
                    if (BackupMain.restoreBackup(args[1], args[2]))
                        sender.sendMessage("Successfully restored backup!");
                    else
                        sender.sendMessage("Something went wrong while restoring backup! Does the file" +
                                " exits?");
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                    exception.printStackTrace();
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
        if (args.length == 1) {
            list.add("backup");
            list.add("restore");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("backup"))
            for (World world : Bukkit.getWorlds())
                list.add(world.getName());
        return list;
    }
}