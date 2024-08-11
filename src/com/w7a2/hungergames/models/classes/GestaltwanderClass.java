package com.w7a2.hungergames.models.classes;

import com.w7a2.hungergames.models.ClassManager;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import com.w7a2.hungergames.models.PlayerClass;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class GestaltwanderClass extends PlayerClass {

    public GestaltwanderClass() {
        super(
                "Gestaltwanlder",
                new ItemStack[] {
                        new ItemStack(Material.COMPASS)
                },
                "Verwandelt sich in das Wesen das er tötet."
        );
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // Überprüfen, ob der Killer ein Spieler ist
        if (event.getEntity().getKiller() instanceof Player) {
            Player player = event.getEntity().getKiller();
            Entity entity = event.getEntity();
            PlayerClass attackerClass = ClassManager.getPlayerClass(player);
            attackerClass.transformPlayer(player, entity);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        // Check if the damaged entity is a player
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            // Check if the player has a disguise
            if (DisguiseAPI.isDisguised(player)) {
                player.sendMessage("Your disguise has been removed because you were hit!");
                DisguiseAPI.undisguiseToAll(player);
            }
        }
    }

    @Override
    public void transformPlayer(Player attacker, Entity entity) {
        Disguise dis;
        switch (entity.getType()) {
            case COW:
                dis = new MobDisguise(DisguiseType.COW);
                DisguiseAPI.disguiseToAll(attacker, dis);
                break;
            case ZOMBIE:
                Disguise zombieDisguise = new MobDisguise(DisguiseType.ZOMBIE);
                DisguiseAPI.disguiseToAll(attacker, zombieDisguise);
                break;
            // Weitere EntityTypen kannst du hier hinzufügen
            default:
                attacker.sendMessage("Du hast ein " + entity.getType().name() + " getötet!");
                break;
        }
    }
}

