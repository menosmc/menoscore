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
        Bukkit.getPluginManager().registerEvents(GuiListener.getInstance(), getInstance());
    }

    private void registerCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(getInstance());
        commandManager.registerCommand(new StaffCommand());
    }
}
