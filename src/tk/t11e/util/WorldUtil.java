package tk.t11e.util;
// Created by booky10 in MultiWorlds (21:10 20.12.19)

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldUtil {

    public static boolean exitsWorld(String worldName) {
        boolean exits = false;
        for (World world : Bukkit.getWorlds())
            if (world.getName().equalsIgnoreCase(worldName)) {
                exits = true;
                break;
            }
        return exits;
    }

    public static boolean exitsWorld(World world) {
        return Bukkit.getWorlds().contains(world);
    }

    public static int getWorldCount() {
        int counter = 0;
        for (World world : Bukkit.getWorlds())
            counter += 1;
        return counter;
    }
}