package io.yennekens;

import io.yennekens.listener.EventPlayerBuild;
import io.yennekens.listener.EventPlayerChatEvent;
import io.yennekens.listener.EventPlayerJoin;
import io.yennekens.listener.lobbySelector.LobbySelectorListener;
import io.yennekens.utils.BungeeCordUtils;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin implements Listener {
    private static Lobby instance;
    public static String prefix = "&c&l[LOBBY]&e ";
    public PluginManager pluginManager;

    public String db_name = "Lobby";
    public String db_host = "localhost";
    public String db_port = "3306";

    @Override
    public void onEnable() {

        instance = this;
        pluginManager = getServer().getPluginManager();

        BungeeCordUtils.initialize();
        registerEvents(pluginManager);
    }

    public void registerEvents(PluginManager pluginManager) {

        pluginManager.registerEvents(new EventPlayerChatEvent(), this);
        pluginManager.registerEvents(new LobbySelectorListener(), this);
        pluginManager.registerEvents(new EventPlayerBuild(), this);
        pluginManager.registerEvents(new EventPlayerJoin(), this);
    }

    public static Lobby getInstance() {
        return instance;
    }


}