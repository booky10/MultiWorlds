package tk.t11e.multiworlds.commands;
// Created by booky10 in MultiWorlds (21:08 20.12.19)

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import tk.t11e.backup.BackupMain;
import tk.t11e.multiworlds.main.Main;
import tk.t11e.util.WorldUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("NullableProblems")
public class DeleteWorld implements CommandExecutor, TabCompleter {

    private final Main main = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("deleteWorld")) {
                if (args.length == 1)
                    if (WorldUtil.exitsWorld(args[0])) {
                        World world = Bukkit.getWorld(args[0]);
                        if (WorldUtil.getWorldCount() < 2) {
                            player.sendMessage(Main.PREFIX + "You can't delete all worlds!");
                            return true;
                        }
                        if (Objects.requireNonNull(world).getPlayerCount() > 0)

                            for (Player players : world.getPlayers())
                                players.kickPlayer("Your current world gets deleted, please stand by!");
                        Bukkit.unloadWorld(world, true);

                        if (main.getConfig().getBoolean("makeBackupOnDelete")) {
                            boolean finished = true;
                            try {
                                if (!BackupMain.makeBackup(args[0], args[0] + "-deleteBackup"))
                                    finished = false;
                            } catch (IOException exception) {
                                finished = false;
                                System.out.println(exception.getMessage());
                                player.sendMessage(exception.getMessage());
                            }
                            if (!finished) {
                                player.sendMessage(Main.PREFIX + "Something went wrong while making " +
                                        "final backup!");
                                return true;
                            }
                        }

                        File worldFile = new File(BackupMain.serverFolder
                                .getAbsolutePath() + "/" + world.getName());
                        System.out.println(worldFile.getAbsolutePath());
                        if (player.isOnline())
                            if (FileUtils.deleteQuietly(worldFile)) {
                                Main.getWorldNames().remove(world.getName());
                                player.sendMessage(Main.PREFIX + "§aSuccessfully deleted world!");
                            } else
                                player.sendMessage(Main.PREFIX + "Something went wrong while deleting!");
                    } else
                        player.sendMessage(Main.PREFIX + "World not found!");
                else
                    return false;
            } else
                player.sendMessage(Main.NO_PERMISSION);
        } else if (args.length == 1)
            if (WorldUtil.exitsWorld(args[0])) {
                World world = Bukkit.getWorld(args[0]);
                if (WorldUtil.getWorldCount() < 2) {
                    sender.sendMessage("You can't delete all worlds!");
                    return true;
                }
                if (Objects.requireNonNull(world).getPlayerCount() > 0)

                    for (Player players : world.getPlayers())
                        players.kickPlayer("Your current world gets deleted, please stand by!");
                Bukkit.unloadWorld(world, true);

                if (main.getConfig().getBoolean("makeBackupOnDelete")) {
                    boolean finished = true;
                    try {
                        if (!BackupMain.makeBackup(args[0], args[0] + "-deleteBackup"))
                            finished = false;
                    } catch (IOException exception) {
                        finished = false;
                        System.out.println(exception.getMessage());
                    }
                    if (!finished) {
                        sender.sendMessage("Something went wrong while making " +
                                "final backup!");
                        return true;
                    }
                }

                File worldFile = new File(BackupMain.serverFolder
                        .getAbsolutePath() + "/" + world.getName());
                if (FileUtils.deleteQuietly(worldFile)) {
                    Main.getWorldNames().remove(world.getName());
                    sender.sendMessage("Successfully deleted world!");
                } else
                    sender.sendMessage("Something went wrong while deleting!");
            } else
                sender.sendMessage("World not found!");
        else
            return false;

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1)
            for (World world : Bukkit.getWorlds())
                list.add(world.getName());
        return list;
    }
}