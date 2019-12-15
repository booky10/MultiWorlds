package tk.t11e.backup;
// Created by booky10 in MultiWorlds (15:15 15.12.19)

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import tk.t11e.backup.commands.Backup;
import tk.t11e.multiworlds.main.Main;
import tk.t11e.util.TimeUtil;
import tk.t11e.util.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class BackupMain {

    private static Main main = Main.getPlugin(Main.class);
    private static File serverFolder, backupsFolder;

    public static void onStart() {
        serverFolder = new File(main.getDataFolder().getAbsolutePath() + "/../..");
        backupsFolder = new File(serverFolder.getAbsolutePath() + "/backups");

        //initScheduler(Bukkit.getScheduler(), main);
        //initCommands(main);
    }

    private static void initScheduler(BukkitScheduler scheduler, JavaPlugin plugin) {
        scheduler.runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {

            }
        }, 20, 20 * 30);
    }

    private static void initCommands(JavaPlugin plugin) {
        Objects.requireNonNull(plugin.getCommand("backup")).setExecutor(new Backup());
    }

    public static long makeBackup() throws IOException {
        long time = System.currentTimeMillis();
        if (!backupsFolder.exists()) backupsFolder.mkdir();

        for (String worldName : Main.getWorldNames())
            makeBackup(worldName);
        return time;
    }

    public static long makeBackup(String worldName) throws IOException {
        long time = System.currentTimeMillis();
        if (!backupsFolder.exists()) backupsFolder.mkdir();

        File input = new File(serverFolder.getAbsolutePath() + "/" + worldName);
        String backupName = worldName + "+" + TimeUtil.getStringTime(time) + "+" + time;
        File output = new File(backupsFolder.getAbsolutePath() + "/" + backupName + ".zip");

        ZipUtil.zipFolder(input, output);
        return time;
    }
}