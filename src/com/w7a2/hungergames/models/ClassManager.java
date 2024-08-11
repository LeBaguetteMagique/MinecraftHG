package com.w7a2.hungergames.models;

import com.w7a2.hungergames.models.classes.BlutsaugerClass;
import com.w7a2.hungergames.models.classes.SpringerClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClassManager {
    // Map zur Speicherung der Spieler und ihrer zugewiesenen Klassen
    private static final Map<UUID, PlayerClass> playerClasses = new HashMap<>();

    // Andere bestehende Klassen und Methoden bleiben gleich
    private static final Map<String, PlayerClass> classes = new HashMap<>();

    // Klassen ohne Fähigkeit
    public static final PlayerClass BERSERKER = new PlayerClass(
            "Berserker",
            new ItemStack[]{
                    new ItemStack(Material.STONE_SWORD),
                    new ItemStack(Material.COMPASS)
            },
            "Keine spezielle Fähigkeit"
    );

    public static final PlayerClass SOLDAT = new PlayerClass(
            "Soldat",
            new ItemStack[]{
                    new ItemStack(Material.STONE_SWORD),
                    new ItemStack(Material.IRON_CHESTPLATE),
                    new ItemStack(Material.COMPASS)
            },
            "Keine spezielle Fähigkeit"
    );

    // Klassen mit Fähigkeit
    public static final PlayerClass BLUTSAUGER = new BlutsaugerClass();
    public static final PlayerClass SPRINGER = new SpringerClass();

    static {
        classes.put(BERSERKER.getName().toLowerCase(), BERSERKER);
        classes.put(SOLDAT.getName().toLowerCase(), SOLDAT);
        classes.put(BLUTSAUGER.getName().toLowerCase(), BLUTSAUGER);
        classes.put(SPRINGER.getName().toLowerCase(), SPRINGER);
        // TODO: Weitere Klassen hier hinzufügen
    }

    // Methode zur Zuordnung eines Spielers zu einer Klasse
    public static void assignClass(Player player, PlayerClass playerClass) {
        playerClasses.put(player.getUniqueId(), playerClass);
        player.sendMessage(ChatColor.RED + "Du hast die Klasse " + playerClass.getName() + " gewählt!");
        //playerClass.applyTo(player);
    }

    public static Map<String, PlayerClass> getClasses() {
        return classes;
    }

    // Methode zum Abrufen der Klasse eines Spielers
    public static PlayerClass getPlayerClass(Player player) {
        return playerClasses.get(player.getUniqueId());
    }

    // Methode zum Entfernen der Klasse eines Spielers (falls benötigt)
    public static void removePlayerClass(Player player) {
        playerClasses.remove(player.getUniqueId());
    }
}
