package tk.t11e.multiworlds.commands;
// Created by booky10 in T11E (21:24 29.07.19)

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.t11e.multiworlds.main.Main;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class GotoWorld implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("gotoWorld"))
                if (args.length == 5)
                    if (Bukkit.getWorld(args[0]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null)
                            try {
                                double x = Integer.parseInt(args[2]);
                                double y = Integer.parseInt(args[3]);
                                double z = Integer.parseInt(args[4]);
                                World world = Bukkit.getWorld(args[0]);
                                Location targetLocation = new Location(world, x, y, z,
                                        target.getLocation().getYaw(), target.getLocation().getPitch());
                                targetLocation.add(0.5, 0.5, 0.5);

                                target.teleport(targetLocation);
                                if (target.getName().equals(player.getName()))
                                    player.sendMessage(Main.PREFIX + "§aSuccessfully teleported!");
                                else
                                    player.sendMessage(Main.PREFIX + "§a\"" + target.getName() + "\" " +
                                            "has been successfully teleported!");
                            } catch (NumberFormatException exception) {
                                return false;
                            }
                        else
                            player.sendMessage(Main.PREFIX + "Player not found!");
                    } else
                        player.sendMessage(Main.PREFIX + "World not found!");
                else
                    return false;
            else
                player.sendMessage(Main.NO_PERMISSION);
        } else if (args.length == 5)
            if (Bukkit.getWorld(args[0]) != null) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null)
                    try {
                        double x = Integer.parseInt(args[2]);
                        double y = Integer.parseInt(args[3]);
                        double z = Integer.parseInt(args[4]);
                        World world = Bukkit.getWorld(args[0]);
                        Location targetLocation = new Location(world, x, y, z,
                                target.getLocation().getYaw(), target.getLocation().getPitch());

                        target.teleport(targetLocation);
                        sender.sendMessage("\"" + target.getName() + "\" has been successfully " +
                                "teleported!");
                    } catch (NumberFormatException exception) {
                        return false;
                    }
                else
                    sender.sendMessage("Player not found!");
            } else
                sender.sendMessage("World not found!");
        else
            return false;
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location playerLocation = player.getLocation();
            if (args.length == 1)
                Bukkit.getWorlds().forEach(world -> list.add(world.getName()));
            else if (args.length == 2)
                Bukkit.getOnlinePlayers().forEach(players -> list.add(players.getName()));
            else if (args.length == 3)
                list.add(playerLocation.getBlockX() + "");
            else if (args.length == 4)
                list.add(playerLocation.getBlockY() + "");
            else if (args.length == 5)
                list.add(playerLocation.getBlockZ() + "");
        } else if (args.length == 1)
            Bukkit.getWorlds().forEach(world -> list.add(world.getName()));
        else if (args.length == 2)
            Bukkit.getOnlinePlayers().forEach(players -> list.add(players.getName()));
        else if (args.length == 3)
            list.add("0");
        else if (args.length == 4)
            list.add("100");
        else if (args.length == 5)
            list.add("0");
        return list;
    }
}