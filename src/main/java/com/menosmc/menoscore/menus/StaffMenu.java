package com.menosmc.menoscore.menus;

import com.menosmc.menoscore.MenosCore;
import com.menosmc.menoscore.framework.ChatHelper;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;
import xyz.janboerman.guilib.api.ItemBuilder;
import xyz.janboerman.guilib.api.menu.ItemButton;
import xyz.janboerman.guilib.api.menu.MenuHolder;

import java.util.HashMap;
import java.util.List;


public class StaffMenu extends MenuHolder<MenosCore> {

    private final Player menuViewer;
    private final Inventory lastInventory;
    private final HashMap<String, List<User>> staffMembers = MenosCore.getLuckPermsManager().getStaffMembers();
    private int page = 1;

    public StaffMenu(Player player, Inventory lastInventory) {
        super(MenosCore.getInstance(), 54, "Staff Overview");
        this.menuViewer = player;
        this.lastInventory = lastInventory;
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        reload();
    }

    private void reload() {
        HashMap<User, String> shownUsers = manageShownUsers();

        int currentSlot = 9;
        int maxSlot = 44;
        for (User user : shownUsers.keySet()) {
            if(currentSlot >= maxSlot) break;

            setButton(currentSlot, getStaffButton(user, shownUsers.get(user)));
            currentSlot++;
        }

        if (page > 1) {
            setButton(47, new ItemButton<StaffMenu>(new ItemBuilder(Material.ARROW)
                    .name("Previous Page")
                    .build()) {
                @Override
                public void onClick(StaffMenu holder, InventoryClickEvent event) {
                    page--;
                    reload();
                }
            });
        }

        if (page > 1) {
            setButton(51, new ItemButton<StaffMenu>(new ItemBuilder(Material.ARROW)
                    .name("Next Page")
                    .build()) {
                @Override
                public void onClick(StaffMenu holder, InventoryClickEvent event) {
                    page++;
                    reload();
                }
            });
        }


        setButton(49, new ItemButton<StaffMenu>(new ItemBuilder(Material.BARRIER)
                .name(lastInventory == null ? "Exit" : "Previous Menu")
                .build()) {
            @Override
            public void onClick(StaffMenu holder, InventoryClickEvent event) {
                event.getWhoClicked().closeInventory();
                menuViewer.openInventory(lastInventory);
            }
        });

        fillEmptySlots();
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

    private void fillEmptySlots() {
        for (int slot = 0; slot < getInventory().getSize(); slot++) {
            if (getInventory().getItem(slot) == null) {
                setButton(slot, new ItemButton<>(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).name(" ").build()));
            }
        }
    }
}
