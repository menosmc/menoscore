package com.menosmc.menoscore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.menosmc.menoscore.menus.StaffMenu;
import org.bukkit.entity.Player;

@CommandAlias("staff|staffgui")
@CommandPermission("menosCore.commands.staffgui")
public class StaffCommand extends BaseCommand {

    @Default
    @Description("Opens a menu that shows all staff members.")
    public void openGui(Player player) {
        player.openInventory(new StaffMenu(player, null).getInventory());
    }
}
