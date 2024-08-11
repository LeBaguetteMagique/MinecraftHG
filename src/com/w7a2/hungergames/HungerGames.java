package com.w7a2.hungergames;

import com.w7a2.hungergames.commands.HungerGamesCommands;
import com.w7a2.hungergames.events.HungerGamesEvents;
import com.w7a2.hungergames.models.classes.SpringerClass;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class HungerGames extends JavaPlugin {

    @Override
    public void onEnable() {
        HungerGamesCommands commands = new HungerGamesCommands();
        getServer().getPluginManager().registerEvents(new HungerGamesEvents(), this);
        getServer().getPluginManager().registerEvents(new SpringerClass(), this);
        getCommand("klasse").setExecutor(commands);
        getCommand("klassen").setExecutor(commands);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[HungerGames]: Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[HungerGames]: Plugin is disabled!");
    }


}
