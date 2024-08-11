package com.w7a2.hungergames.models.classes;

import com.w7a2.hungergames.models.ClassManager;
import com.w7a2.hungergames.models.PlayerClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class SpringerClass extends PlayerClass implements Listener {

    // Speichert die Abklingzeiten der Fähigkeit für jeden Spieler
    private HashMap<UUID, Long> abilityCooldowns = new HashMap<>();

    public SpringerClass() {
        super(
                "Springer",
                new ItemStack[]{
                        new ItemStack(Material.COMPASS),
                        new ItemStack(Material.STICK)
                },
                "Hat Sprungkraft, nimmt keinen Fallschaden und kann mit der Fähigkeit 30 Blöcke hoch springen."
        );
    }

    // Gibt dem Spieler dauerhafte Sprungkraft 2, wenn er der Klasse beitritt
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (isClassSelected(player)) { // Überprüft, ob der Spieler diese Klasse gewählt hat
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1, true));
        }
    }

    // Verhindert Fallschaden
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (isClassSelected(player) && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true); // Verhindert den Fallschaden
            }
        }
    }

    // Aktiviert die Fähigkeit, wenn der Spieler mit dem Stick rechtsklickt
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (isClassSelected(player) && player.getItemInHand().getType() == Material.STICK) {
            UUID playerUUID = player.getUniqueId();
            long currentTime = System.currentTimeMillis();

            if (abilityCooldowns.containsKey(playerUUID)) {
                long lastUsed = abilityCooldowns.get(playerUUID);
                if (currentTime - lastUsed < 1000) { // 30 Sekunden Cooldown
                    player.sendMessage("Die Fähigkeit ist noch nicht bereit. Warte einen Moment.");
                    return;
                }
            }

            // Setze die Abklingzeit und führe die Fähigkeit aus
            abilityCooldowns.put(playerUUID, currentTime);

            // Der Spieler springt 30 Blöcke hoch
            Vector jump = player.getVelocity();
            jump.setY(2.0); // Y-Wert anpassen, um 30 Blöcke hoch zu springen
            player.setVelocity(jump);

            player.sendMessage("Du bist 30 Blöcke hoch gesprungen!");
        }
    }

    // Hilfsfunktion, um zu überprüfen, ob ein Spieler die Klasse Springer ausgewählt hat
    private boolean isClassSelected(Player player) {
        return ClassManager.getPlayerClass(player) instanceof SpringerClass;
    }
}

