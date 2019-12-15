package tk.t11e.backup;
// Created by booky10 in MultiWorlds (15:15 15.12.19)

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import tk.t11e.backup.commands.Backup;
import tk.t11e.multiworlds.main.Main;
import tk.t11e.util.TimeUtil;
import tk.t11e.util.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class BackupMain {

    public static final String PREFIX = "§7[§bBackup§7]§c ", NO_PERMISSION = PREFIX + "You don't have " +
            "the permissions for this!";
    private static final Main main = Main.getPlugin(Main.class);
    private static File serverFolder, backupsFolder;

    public static void onStart() {
        serverFolder = new File(main.getDataFolder().getAbsolutePath() + "/../..");
        backupsFolder = new File(serverFolder.getAbsolutePath() + "/backups");

        initScheduler(Bukkit.getScheduler());
        initCommands();
    }

    private static void initScheduler(BukkitScheduler scheduler) {
        scheduler.runTaskTimer(BackupMain.main, () -> {
            if (main.getConfig().getLong("lastBackup") + 36000000 < System.currentTimeMillis())
                try {
                    if (makeBackup())
                        System.out.println("[Backup] Successfully made backup!");
                    else
                        System.out.println("[Backup] Something went wrong while making backup!");
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
        }, 20 * 10, 20 * 60);
    }

    private static void initCommands() {
        Objects.requireNonNull(BackupMain.main.getCommand("backup")).setExecutor(new Backup());
    }

    public static boolean makeBackup() throws IOException {
        long time = System.currentTimeMillis();
        if (!backupsFolder.exists()) backupsFolder.mkdir();
        boolean finished = true;

        for (String worldName : Main.getWorldNames()) {
            File input = new File(serverFolder.getAbsolutePath() + "/" + worldName);
            String backupName = worldName + "+" + TimeUtil.getStringTime(time) + "+" + time;
            File output = new File(backupsFolder.getAbsolutePath() + "/" + backupName + ".zip");

            if (!ZipUtil.zipFolder(input, output)) finished = false;
        }
        main.getConfig().set("lastBackup", time);
        main.saveConfig();
        return finished;
    }
}