package com.w7a2.hungergames.models;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerClass {

    private final String name;
    private final ItemStack[] items;
    private final String ability;


    public PlayerClass(String name, ItemStack[] items, String ability) {
        this.name = name;
        this.items = items;
        this.ability = ability;
    }

    public String getName() {
        return name;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public String getAbility() {
        return ability;
    }

    public void applyTo(Player player) {
        player.getInventory().clear();
        player.getInventory().addItem(items);
        player.sendMessage("Du hast die Klasse " + name + " gewählt!");
    }

    public void onHit(Player attacker, Player victim) {

    }
    public void onHitMob(Player attacker, LivingEntity victim) {

    }

    public void transformPlayer(Player attacker, Entity entity) {

    }
}


