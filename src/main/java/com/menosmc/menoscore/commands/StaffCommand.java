package com.menosmc.menoscore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.entity.Player;

@CommandAlias("staff|staffgui")
@CommandPermission("menoscore.commands.staffgui")
public class StaffCommand extends BaseCommand {

    @Default
    @Description("Opens a menu that shows all staff members.")
    public void openGui(Player player) {

    }
}
