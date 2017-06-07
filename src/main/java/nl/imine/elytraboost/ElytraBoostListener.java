package nl.imine.elytraboost;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ElytraBoostListener implements Listener {

    List<Player> elytraFlyingPlayers = new ArrayList<>();
    List<Player> elytraBoostCooldown = new ArrayList<>();

    @EventHandler
    public void onElytraToggle(EntityToggleGlideEvent etge) {

        if (!(etge.getEntity() instanceof Player)) {
            System.out.println(etge.getEntity());
            return;
        }

        Player player = (Player) etge.getEntity();

        if (etge.isGliding()) {
            if (!elytraFlyingPlayers.contains(player)) {
                elytraFlyingPlayers.add(player);
            }
        } else {
            elytraFlyingPlayers.remove(player);
        }

    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent pme) {
        Player player = pme.getPlayer();

        if (!elytraFlyingPlayers.contains(player)) {
            return;
        }

        ElytraBoosterManager.getInstance().getBoosters().forEach(booster -> {
            if (booster.isInRange(player.getLocation()) && booster.hasUnlocked(player) && !booster.isOnCooldown(player)) {
                booster.applyBoost(player);
            }
        });
    }
    
    @EventHandler
    private void onInventoryMove(InventoryInteractEvent imie){
        imie.setCancelled(true);
        imie.getWhoClicked().sendMessage("HALLO");
        System.out.println("HALLO WERELD!");
        System.out.println("InventoryInteractEvent: " + imie.getInventory().getName());
        if(imie.getInventory().getName().contains("Boosters")){
            
            
        }
    }
}
