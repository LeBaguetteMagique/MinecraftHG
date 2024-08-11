package com.w7a2.hungergames.events;

import com.w7a2.hungergames.models.ClassManager;
import com.w7a2.hungergames.models.PlayerClass;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import static org.bukkit.Bukkit.getServer;

public class HungerGamesEvents implements Listener {

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Entferne die Join-Todesnachricht
        event.setJoinMessage(null);

        player.sendMessage(ChatColor.AQUA + "Willkommen zu " + ChatColor.GOLD + "hg-pvp.de" + ChatColor.AQUA + "!");
    }


    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event) {
        Player deceased = event.getEntity();
        Entity killer = deceased.getKiller();

        // Entferne die Standard-Todesnachricht
        event.setDeathMessage(null);

        if (killer instanceof Player killerPlayer) {
            // Der Spieler wurde von einem anderen Spieler getötet
            getServer().broadcastMessage( ChatColor.GOLD + deceased.getName() + " (" + ClassManager.getPlayerClass(deceased).getName() + ")" + ChatColor.AQUA + " wurde von " +
                                             ChatColor.GOLD + killerPlayer.getName() + " (" + ClassManager.getPlayerClass(killerPlayer).getName() + ")" + ChatColor.AQUA + " getötet!");
        } else {
            // Der Spieler ist durch etwas anderes gestorben
            getServer().broadcastMessage(ChatColor.GOLD + deceased.getName() + " (" + ClassManager.getPlayerClass(deceased).getName() + ")" + ChatColor.AQUA + " ist gestorben!");
        }
    }


    @EventHandler
    public static void onPlayerHit(EntityDamageByEntityEvent event) {
        // Überprüfen, ob der Angreifer ein Spieler ist
        if (event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            Entity victim = event.getEntity();

            // Klasse des Angreifers abrufen
            PlayerClass attackerClass = ClassManager.getPlayerClass(attacker);

            if (attackerClass != null) {
                // Überprüfen, ob das Opfer ein Spieler oder ein anderes Lebewesen ist
                if (victim instanceof Player) {
                    attackerClass.onHit(attacker, (Player) victim);
                } else if (victim instanceof LivingEntity) {
                    // Falls das Opfer ein Mob ist (z.B. Zombie, Skelett)
                    attackerClass.onHitMob(attacker, (LivingEntity) victim);
                }
            }
        }
    }

    @EventHandler
    public static void onPlayerPortal(PlayerPortalEvent event) {
        event.setCancelled(true);
        event.getPlayer().sendMessage(ChatColor.RED + "Portale sind in diesem Modus deaktiviert!");
    }

}
