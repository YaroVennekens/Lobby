package io.yennekens.utils;

import io.yennekens.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerUtils {


    public static void givePlayerLobbySelector(Player player){

        player.getInventory().setItem(4, new LobbySelector(Lobby.getServerManager()).LobbySelector());
player.getInventory().setHeldItemSlot(4);
    }
    public static void teleportPlayerSpawn(Player player){
        World world = Bukkit.getServer().getWorld("world");

        if(world != null){
            player.teleport(world.getSpawnLocation());

        }
    }

    public static void resetPlayer(Player player){
        player.setGameMode(GameMode.SURVIVAL);
        player.setLevel(0);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setSaturation(20);
        player.setExp(0);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

    }
}
