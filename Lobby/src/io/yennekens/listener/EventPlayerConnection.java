package io.yennekens.listener;

import io.yennekens.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static io.yennekens.utils.ChatUtils.resetPlayerChat;
import static io.yennekens.utils.PlayerUtils.*;

public class EventPlayerConnection implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        givePlayerLobbySelector(player);
        resetPlayerChat(player);

        resetPlayer(player);
        teleportPlayerSpawn(player);
    }

}
