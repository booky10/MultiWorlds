package tk.t11e.multiworlds.main;
// Created by booky10 in MultiWorlds (17:30 08.10.19)

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import tk.t11e.backup.BackupMain;
import tk.t11e.multiworlds.commands.CreateWorld;
import tk.t11e.multiworlds.commands.GotoWorld;
import tk.t11e.multiworlds.commands.WorldList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends JavaPlugin {

    public static final String PREFIX = "§7[§bT11E§7]§c ", NO_PERMISSION = PREFIX + "You have no " +
            "permissions for this!";
    public static List<String> worldList;

    @Override
    public void onEnable() {
        long milliseconds = System.currentTimeMillis();

        saveDefaultConfig();

        worldList = getConfig().getStringList("worlds");
        for (World world : Bukkit.getWorlds())
            worldList.add(world.getName());

        for (String worldName : worldList)
            Bukkit.createWorld(new WorldCreator(worldName));
        initCommands();
        BackupMain.onStart();

        System.out.println("[MultiWorlds] It took " + (System.currentTimeMillis() - milliseconds) +
                "ms to initialize this plugin!");
    }

    @Override
    public void onDisable() {
        List<String> bukkitWorldList = new ArrayList<>();
        for (World world : Bukkit.getWorlds())
            bukkitWorldList.add(world.getName());
        getConfig().set("worlds", bukkitWorldList);
        saveConfig();
    }

    private void initCommands() {
        Objects.requireNonNull(getCommand("createWorld")).setExecutor(new CreateWorld());
        Objects.requireNonNull(getCommand("gotoWorld")).setExecutor(new GotoWorld());
        Objects.requireNonNull(getCommand("initWorld")).setExecutor(new CreateWorld());
        Objects.requireNonNull(getCommand("worldList")).setExecutor(new WorldList());
    }

    public static List<String> getWorldNames() {
        List<String> worldNames = new ArrayList<>();
        for (World world : Bukkit.getWorlds())
            worldNames.add(world.getName());
        return worldNames;
    }
}