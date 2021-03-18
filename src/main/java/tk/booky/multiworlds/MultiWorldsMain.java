package tk.booky.multiworlds;
// Created by booky10 in MultiWorlds (17:30 08.10.19)

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MultiWorldsMain extends JavaPlugin {

    public static final List<String> worlds = new ArrayList<>();
    public static MultiWorldsMain main;

    @Override
    public void onEnable() {
        main = this;
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("world")).setExecutor(new WorldCommand());

        worlds.addAll(getConfig().getStringList("worlds"));
        worlds.forEach(world -> Bukkit.createWorld(new WorldCreator(world)));
    }

    @Override
    public void saveConfig() {
        getConfig().set("worlds", worlds);
        super.saveConfig();
    }
}