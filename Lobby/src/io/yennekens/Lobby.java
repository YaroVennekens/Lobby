package io.yennekens;

import io.yennekens.listener.EventPlayerChatEvent;
import io.yennekens.listener.EventPlayerConnection;
import io.yennekens.listener.lobbySelector.LobbySelectorListener;
import io.yennekens.manager.ServerManager;
import io.yennekens.utils.BungeeCordUtils;
import io.yennekens.utils.MySQLConnection;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin implements Listener {
    private static Lobby instance;
    public static String prefix = "&c&l[LOBBY]&e ";
    public PluginManager pluginManager;
    private static ServerManager serverManager;

    public String db_name = "Lobby";
    public String db_host = "localhost";
    public String db_port = "3306";

    @Override
    public void onEnable() {
        instance = this;
        pluginManager = getServer().getPluginManager();


        boolean isDatabaseConnected = MySQLConnection.connect(
                db_host,
                db_port,
                db_name,
                "root",
                "my-secret-pw"
        );

        if (!isDatabaseConnected) {
            getLogger().severe("Could not establish a connection to the database! Players will not be able to join.");
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            registerEvents(Bukkit.getPluginManager());
            serverManager = new ServerManager();
            BungeeCordUtils.initialize();
            registerEvents(pluginManager);
        }
    }

    public void registerEvents(PluginManager pluginManager) {
        pluginManager.registerEvents(new EventPlayerConnection(), this);
        pluginManager.registerEvents(new EventPlayerChatEvent(), this);
        pluginManager.registerEvents(new LobbySelectorListener(), this);
    }

    public static Lobby getInstance() {
        return instance;
    }

    public static ServerManager getServerManager() {
        return serverManager;
    }
}