package io.yennekens;

import io.yennekens.listener.EventPlayerChatEvent;
import io.yennekens.listener.EventPlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin
{

    public PluginManager pluginManager;

    public static String prefix = "&c&l[LOBBY]&e ";

    public void onEnable(){
        pluginManager = Bukkit.getPluginManager();

        registerCommands();
        registerEvents(pluginManager);
    }

    public void onDisable(){


    }

    public void registerCommands(){

    }

    public void registerEvents(PluginManager pluginManager){
        pluginManager.registerEvents(new EventPlayerConnection(), this);
        pluginManager.registerEvents(new EventPlayerChatEvent(), this);

    }

}
