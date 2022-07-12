package dev.booky.multiworlds;
// Created by booky10 in MultiWorlds (21:32 18.03.21)

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class WorldCommand implements TabExecutor {

    private final Set<String> worlds;
    private final MultiWorldsMain plugin;

    public WorldCommand(Set<String> worlds, MultiWorldsMain plugin) {
        this.worlds = worlds;
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation") // non-adventure support
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }

        return switch (args[0].toLowerCase(Locale.ROOT)) {
            case "load" -> {
                if (args.length < 2) {
                    sender.sendMessage("Please specify a world name to load");
                    yield true;
                }

                String worldName;
                if (args.length == 2) {
                    worldName = args[1];
                } else {
                    String[] nameArr = new String[args.length - 1];
                    System.arraycopy(args, 1, nameArr, 0, args.length - 1);
                    worldName = String.join(" ", nameArr); // Something like "  " can be lost here, but idc
                }

                World worldObj = Bukkit.getWorld(worldName);
                if (worldObj != null) {
                    sender.sendMessage("World \"" + worldName + "\" is already loaded");
                    yield true;
                }

                sender.sendMessage("Loading world \"" + worldName + "\"...");
                try {
                    worldObj = plugin.newWorldCreator(worldName).createWorld();
                    if (worldObj == null) {
                        throw new IllegalStateException("Couldn't load world");
                    }

                    worlds.add(worldObj.getName());
                    plugin.saveConfig();

                    sender.sendMessage("Loaded world \"" + worldName + "\"");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    sender.sendMessage("Error while loading world named \"" + worldName + "\": " + throwable);
                }

                yield true;
            }
            case "unload" -> {
                if (args.length < 2) {
                    sender.sendMessage("Please specify a world name to unload");
                    yield true;
                }

                String worldName;
                if (args.length == 2) {
                    worldName = args[1];
                } else {
                    String[] nameArr = new String[args.length - 1];
                    System.arraycopy(args, 1, nameArr, 0, args.length - 1);
                    worldName = String.join(" ", nameArr); // Something like "  " can be lost here, but idc
                }

                World worldObj = Bukkit.getWorld(worldName);
                if (worldObj == null) {
                    sender.sendMessage("World \"" + worldName + "\" is not loaded");
                    yield true;
                }

                sender.sendMessage("Unloading world \"" + worldName + "\"...");
                try {
                    for (Player player : worldObj.getPlayers()) {
                        player.kickPlayer("World \"" + worldName + "\" is getting unloaded");
                    }

                    if (!Bukkit.unloadWorld(worldObj, true)) {
                        throw new IllegalStateException("Couldn't unload world");
                    }

                    worlds.remove(worldObj.getName());
                    plugin.saveConfig();

                    sender.sendMessage("Unloaded world \"" + worldName + "\"");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    sender.sendMessage("Error while unloading world named \"" + worldName + "\": " + throwable);
                }
                yield true;
            }
            case "goto" -> {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("This command can only be used as a player");
                    yield true;
                }
                if (args.length < 2) {
                    sender.sendMessage("Please specify a world name to go to");
                    yield true;
                }

                String worldName;
                if (args.length == 2) {
                    worldName = args[1];
                } else {
                    String[] nameArr = new String[args.length - 1];
                    System.arraycopy(args, 1, nameArr, 0, args.length - 1);
                    worldName = String.join(" ", nameArr); // Something like "  " can be lost here, but idc
                }

                World worldObj = Bukkit.getWorld(worldName);
                if (worldObj == null) {
                    sender.sendMessage("World \"" + worldName + "\" is not loaded");
                    yield true;
                }

                sender.sendMessage("Teleporting to world \"" + worldName + "\"...");
                player.teleport(worldObj.getSpawnLocation().toCenterLocation());
                yield true;
            }
            case "list" -> {
                StringBuilder builder = new StringBuilder();
                for (World worldObj : Bukkit.getWorlds()) {
                    if (builder.length() > 0) builder.append(", ");
                    builder.append('"').append(worldObj.getName()).append('"');

                    if (worlds.contains(worldObj.getName())) {
                        builder.append(" (MW)");
                    }
                }

                if (builder.length() == 0) {
                    sender.sendMessage("No worlds loaded yet");
                    yield true;
                }

                sender.sendMessage("Loaded worlds: " + builder);
                yield true;
            }
            default -> false;
        };
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("load", "unload", "goto", "list"));
        } else if (args.length == 2) {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "unload", "goto" -> {
                    for (World worldObj : Bukkit.getWorlds()) {
                        completions.add(worldObj.getName());
                    }
                }
            }
        }

        return completions.stream()
                .filter(name -> StringUtil.startsWithIgnoreCase(name, args[args.length - 1]))
                .collect(Collectors.toList());
    }
}
