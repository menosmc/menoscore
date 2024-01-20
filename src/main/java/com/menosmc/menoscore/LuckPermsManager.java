package com.menosmc.menoscore;

import com.menosmc.menoscore.framework.ChatHelper;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.matcher.NodeMatcher;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LuckPermsManager {

    @Getter
    private final HashMap<String, List<User>> staffMembers = new HashMap<>();
    private final String[] staffRoles = new String[]{
            "owner",
            "manager",
            "developer",
            "admin",
            "sr-mod",
            "moderator",
            "helperplus",
            "helper",
            "builder"
    };
    private LuckPerms luckPerms;

    public LuckPermsManager() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            this.luckPerms = provider.getProvider();
        }

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(MenosCore.getInstance(), () -> {
            for (String staffRole : staffRoles) {
                getUsersInGroup(staffRole).whenComplete(((users, throwable) -> {
                    if (throwable != null) {
                        ChatHelper.logError("Something went wrong while getting the users belong to staff role %s.", staffRole);
                        return;
                    }

                    staffMembers.put(staffRole, users);
                }));
            }
        }, 0, 20 * 60 * 5);
    }


    private CompletableFuture<List<User>> getUsersInGroup(String groupName) {
        NodeMatcher<InheritanceNode> matcher = NodeMatcher.key(InheritanceNode.builder(groupName).build());
        return luckPerms.getUserManager().searchAll(matcher).thenComposeAsync(results -> {
            List<CompletableFuture<User>> users = new ArrayList<>();
            return CompletableFuture.allOf(
                    results.keySet().stream()
                            .map(uuid -> luckPerms.getUserManager().loadUser(uuid))
                            .peek(users::add)
                            .toArray(CompletableFuture[]::new)
            ).thenApply(x -> users.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList())
            );
        });
    }


}
