package com.menosmc.menoscore.menus;

import com.menosmc.menoscore.MenosCore;
import com.menosmc.menoscore.framework.ChatHelper;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.meta.SkullMeta;
import xyz.janboerman.guilib.api.ItemBuilder;
import xyz.janboerman.guilib.api.menu.ItemButton;
import xyz.janboerman.guilib.api.menu.MenuHolder;

import java.util.HashMap;
import java.util.List;


public class StaffMenu extends MenuHolder<MenosCore> {

    private final Player menuViewer;
    private final HashMap<String, List<User>> staffMembers = MenosCore.getLuckPermsManager().getStaffMembers();
    private final int page;

    public StaffMenu(Player player, int page) {
        super(MenosCore.getInstance(), 54, "Staff Overview");
        this.menuViewer = player;
        this.page = page;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        reload();
    }

    private void reload() {
        HashMap<User, String> shownUsers = manageShownUsers();

        int currentSlot = 9;
        int maxSlot = 46;
        for (User user : shownUsers.keySet()) {
            setButton(currentSlot, getStaffButton(user, shownUsers.get(user)));
        }
    }

    private ItemButton<StaffMenu> getStaffButton(User shownUser, String staffRole) {
        return new ItemButton<StaffMenu>(new ItemBuilder(Material.PLAYER_HEAD)
                .name(ChatHelper.getRoleUnicodeChar(staffRole) + " " + shownUser.getUsername())
                .changeItemMeta(itemMeta -> ((SkullMeta) itemMeta).setOwningPlayer(Bukkit.getOfflinePlayer(shownUser.getUniqueId())))
                .build()) {
            @Override
            public void onClick(StaffMenu holder, InventoryClickEvent event) {
                super.onClick(holder, event);
            }
        };
    }

    private HashMap<User, String> manageShownUsers() {
        HashMap<User, String> shownUsers = new HashMap<>();

        int currentIndex = (page - 1) * 36;
        int skippingIndex = 0;

        for (String staffRole : staffMembers.keySet()) {
            for (User user : staffMembers.get(staffRole)) {
                if (skippingIndex < currentIndex) {
                    skippingIndex++;
                    continue;
                }

                shownUsers.put(user, staffRole);
                currentIndex++;
                skippingIndex++;

                if (currentIndex == 44) break;
            }
        }

        return shownUsers;
    }
}
