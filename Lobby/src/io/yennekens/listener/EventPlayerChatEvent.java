package io.yennekens.listener;

import io.yennekens.utils.ChatUtils;
import io.yennekens.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EventPlayerChatEvent implements Listener {


    public String LOBBY_CHAT_PERMISSION = "yennekens.lobby.allowchat";
    public String LOBBY_CHATTING_NOT_ALLOWED = "De chat is momenteel uitgeschakeld in de Lobby";

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

    Player player = event.getPlayer();

    if(!(player.hasPermission(LOBBY_CHAT_PERMISSION))) {

        ChatUtils.sendPlayerMessage(player, LOBBY_CHATTING_NOT_ALLOWED);
        event.setCancelled(true);
    }
    }

}
