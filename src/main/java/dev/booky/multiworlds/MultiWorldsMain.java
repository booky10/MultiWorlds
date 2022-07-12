package dev.booky.multiworlds;
// Created by booky10 in MultiWorlds (17:30 08.10.19)

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MultiWorldsMain extends JavaPlugin {

    private final Set<String> worlds = new HashSet<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        Objects.requireNonNull(getCommand("world")).setExecutor(new WorldCommand(worlds, this));

        getLogger().info("Loading configured worlds...");
        for (String world : worlds) {
            World worldObj = Bukkit.getWorld(world);
            if (worldObj != null) {
                getLogger().info("\"" + world + "\" is already loaded, skipping");
                continue;
            }

            newWorldCreator(world).createWorld();
            getLogger().info("Loaded world named \"" + world + "\"");
        }
    }

    public WorldCreator newWorldCreator(String name) {
        World.Environment env = World.Environment.NORMAL;
        if (name.endsWith("_nether")) {
            env = World.Environment.NETHER;
        } else if (name.endsWith("_the_end")) {
            env = World.Environment.THE_END;
        }

        return WorldCreator.name(name).environment(env);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();

        worlds.clear();
        worlds.addAll(getConfig().getStringList("worlds"));
    }

    @Override
    public void saveConfig() {
        getConfig().set("worlds", ImmutableList.copyOf(worlds));
        super.saveConfig();
    }
}
