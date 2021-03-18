package tk.booky.multiworlds;
// Created by booky10 in MultiWorlds (21:32 18.03.21)

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("NullableProblems")
public class WorldCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("delete")) {
                World world = Bukkit.getWorld(args[1]);

                if (world == null) {
                    sender.sendMessage("Die Welt wurde nicht gefunden!");
                } else {
                    MultiWorldsMain.worlds.remove(world.getName());
                    MultiWorldsMain.main.saveConfig();

                    Bukkit.unloadWorld(world, true);
                    sender.sendMessage("Die Welt wurde entfernt!");
                }
            } else if (args[0].equalsIgnoreCase("goto")) {
                World world = Bukkit.getWorld(args[1]);

                if (world == null) {
                    sender.sendMessage("Die Welt wurde nicht gefunden!");
                } else if (sender instanceof Player) {
                    ((Player) sender).teleport(world.getSpawnLocation());
                    sender.sendMessage("Du wurdest teleportiert!");
                } else {
                    sender.sendMessage("Du musst diesen Befehl als Spieler ausführen!");
                }
            } else if (args[0].equalsIgnoreCase("create")) {
                World world = Bukkit.createWorld(new WorldCreator(args[1]));

                if (world != null) {
                    MultiWorldsMain.worlds.add(world.getName());
                    MultiWorldsMain.main.saveConfig();

                    sender.sendMessage("Die Welt wurde erstellt!");
                } else {
                    sender.sendMessage("Es gab einen Fehler bei der Erstellung der Welt!");
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("delete", "create", "goto"));
        } else if (args.length == 2 && (args[1].equalsIgnoreCase("delete") || args[1].equalsIgnoreCase("backup") || args[1].equalsIgnoreCase("goto"))) {
            completions.addAll(Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList()));
        }

        return completions.stream().filter(name -> StringUtil.startsWithIgnoreCase(name, args[args.length - 1])).collect(Collectors.toList());
    }
}