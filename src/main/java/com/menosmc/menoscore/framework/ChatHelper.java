package com.menosmc.menoscore.framework;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.logging.Level;

public class ChatHelper {


    public static void logError(String errorMessage, Object... objects) {
        Bukkit.getLogger().log(Level.INFO, ChatHelper.highlight(errorMessage, objects));
    }

    public static void logDebug(String errorMessage, Object... objects) {
        Bukkit.getLogger().log(Level.INFO, ChatHelper.standard(errorMessage, objects));
    }

    public static String successful(String string, Object... objects) {
        return ChatColor.DARK_GREEN + String.format(string.replaceAll("%s", ChatColor.GREEN + "%s" + ChatColor.DARK_GREEN), objects);
    }

    public static String highlight(String string, Object... objects) {
        return ChatColor.DARK_RED + String.format(string.replaceAll("%s", ChatColor.RED + "%s" + ChatColor.DARK_RED), objects);
    }

    public static String standard(String string, Object... objects) {
        return ChatColor.GOLD + String.format(string.replaceAll("%s", ChatColor.AQUA + "%s" + ChatColor.GOLD), objects);
    }

    public static String colorize(ChatColor color, String string, boolean bold) {
        return bold ? color + "" + ChatColor.BOLD + string : color + string;
    }

    public static String colorize(ChatColor color, ChatColor secondColor, String string, Object... objects) {
        return color + String.format(string.replaceAll("%s", secondColor + "%s" + color), objects);
    }

    public static void sendMessageBox(CommandSender sender, String title, Runnable runnable) {
        sender.sendMessage("");
        sender.sendMessage("§7§m==============§e§l " + title + " §7§m==============");
        sender.sendMessage("");

        runnable.run();

        int length = org.bukkit.ChatColor.stripColor(title).length();
        char[] array = new char[length];
        Arrays.fill(array, '=');
        String bottom = "==============================" + new String(array);
        sender.sendMessage("");
        sender.sendMessage("§7§m" + bottom);
    }

    public static void sendMessageToPlayersNearLocation(Location location, String message, double maxDistance) {
        for (Player player : location.getWorld().getNearbyEntitiesByType(Player.class, location, maxDistance)) {
            player.sendMessage(message);
        }
    }

    public static char getRoleUnicodeChar(String role) {
        switch (role) {
            case "owner":
                return '\uE50F';
            case "manager":
                return '\uE526';
            case "developer":
                return '\uE50E';
            case "admin":
                return '\uE50A';
            case "sr-mod":
                return '\uE51E';
            case "moderator":
                return '\uE50B';
            case "helperplus":
                return '\uE503';
            case "helper":
                return '\uE50C';
            case "builder":
                return '\uE530';
            default:
                return '\uE517';
        }

    }
}
