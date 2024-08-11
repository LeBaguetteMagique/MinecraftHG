package com.w7a2.hungergames.commands;

import com.w7a2.hungergames.models.ClassManager;
import com.w7a2.hungergames.models.PlayerClass;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class HungerGamesCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Überprüfen, ob der Sender ein Spieler ist
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;

        // Überprüfen, welcher Befehl ausgeführt wurde
        switch (command.getName().toLowerCase()) {
            case "klasse":
                handleClassCommand(player, args);
                break;

            case "klassen":
                handleListClassesCommand(player);
                break;

            // Weitere Commands können hier hinzugefügt werden

            default:
                player.sendMessage("Unbekannter Befehl.");
                break;
        }

        return true;
    }

    private void handleListClassesCommand(Player player) {
        // Alle Klassennamen dynamisch abrufen
        String classesList = ClassManager.getClasses().values().stream()
                .map(PlayerClass::getName)
                .collect(Collectors.joining(", "));

        // Nachricht an den Spieler senden
        player.sendMessage(ChatColor.AQUA + "Verfügbare Klassen: " + ChatColor.GOLD + classesList);
    }

    private void handleClassCommand(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("Bitte wähle eine Klasse! Beispiel: /klasse berserker");
            return;
        }

        String className = args[0].toLowerCase();
        PlayerClass playerClass = ClassManager.getClasses().get(className);

        if (playerClass != null) {
            // Wählt existierende Klasse
            ClassManager.assignClass(player, playerClass);
        } else {
            player.sendMessage("Diese Klasse existiert nicht.");
        }
    }
}
