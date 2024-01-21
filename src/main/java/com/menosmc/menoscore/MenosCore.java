package com.menosmc.menoscore;

import co.aikar.commands.PaperCommandManager;
import com.menosmc.menoscore.commands.StaffCommand;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.janboerman.guilib.api.GuiListener;

public final class MenosCore extends JavaPlugin {

    @Getter
    private static MenosCore instance;

    @Getter
    private static LuckPermsManager luckPermsManager;

    @Override
    public void onEnable() {
        instance = this;
        luckPermsManager = new LuckPermsManager();

        registerCommands();
        registerListeners();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerListeners() {
        Bukkit.getLogger().info("Registering Listeners...");
        Bukkit.getPluginManager().registerEvents(GuiListener.getInstance(), getInstance());
        Bukkit.getLogger().info("Finished Registering Listeners.");
    }

    private void registerCommands() {
        Bukkit.getLogger().info("Registering Commands...");
        PaperCommandManager commandManager = new PaperCommandManager(getInstance());
        commandManager.registerCommand(new StaffCommand());
        Bukkit.getLogger().info("Finished registering commands.");
    }
}
