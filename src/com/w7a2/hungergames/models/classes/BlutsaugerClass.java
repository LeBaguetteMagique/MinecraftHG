package com.w7a2.hungergames.models.classes;

import com.w7a2.hungergames.models.PlayerClass;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlutsaugerClass extends PlayerClass {

    public BlutsaugerClass() {
        super(
                "Blutsauger",
                new ItemStack[] {
                        new ItemStack(Material.COMPASS)
                },
                "Regeneriert bei jedem Schlag 0.5 Herzen."
        );
    }

    @Override
    public void onHit(Player attacker, Player victim) {

        PotionEffect healEffect = new PotionEffect(PotionEffectType.HEAL, 1, 0);
        attacker.addPotionEffect(healEffect);

        attacker.sendMessage("Du hast 0.5 Herzen regeneriert!");
    }
    public void onHitMoB(Player attacker, LivingEntity victim) {
        PotionEffect healEffect = new PotionEffect(PotionEffectType.HEAL, 1, 0);
        attacker.addPotionEffect(healEffect);

        attacker.sendMessage("Du hast 0.5 Herzen regeneriert!");
    }
}
