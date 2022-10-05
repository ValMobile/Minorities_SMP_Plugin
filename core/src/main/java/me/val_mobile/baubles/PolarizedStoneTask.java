/*
    Copyright (C) 2022  Val_Mobile

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.val_mobile.baubles;

import me.val_mobile.data.RSVPlayer;
import me.val_mobile.realisticsurvival.RealisticSurvivalPlugin;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PolarizedStoneTask extends BukkitRunnable {

    private static final Map<UUID, PolarizedStoneTask> tasks = new HashMap<>();
    private final RSVPlayer rsvPlayer;
    private final RealisticSurvivalPlugin plugin;
    private final UUID id;
    private final Collection<String> allowedWorlds;
    private final FileConfiguration config;
    private final double maxRadius;
    private final double pullForce;

    public PolarizedStoneTask(BaubleModule module, RSVPlayer rsvPlayer, RealisticSurvivalPlugin plugin) {
        this.rsvPlayer = rsvPlayer;
        this.id = rsvPlayer.getPlayer().getUniqueId();
        this.allowedWorlds = module.getAllowedWorlds();
        this.config = module.getUserConfig().getConfig();
        this.plugin = plugin;
        this.maxRadius = config.getDouble("Items.polarized_stone.MaxRadius");
        this.pullForce = config.getDouble("Items.polarized_stone.PullForce");
        tasks.put(id, this);
    }

    @Override
    public void run() {
        Player player = rsvPlayer.getPlayer();

        if (player == null) {
            tasks.remove(id);
            cancel();
        }
        else {
            if (player.isOnline() && allowedWorlds.contains(player.getWorld().getName())) {
                if (rsvPlayer.getBaubleDataModule().hasBauble("polarized_stone")) {
                    // effect the player with resistance
                    Location pLoc = player.getLocation();
                    Vector pVector = pLoc.toVector();
                    for (Entity entity : player.getNearbyEntities(maxRadius, maxRadius, maxRadius)) {
                        if (entity.getType() == EntityType.EXPERIENCE_ORB || entity.getType() == EntityType.DROPPED_ITEM) {
                            Location eLoc = entity.getLocation();
                            Vector eVector = eLoc.toVector();

                            entity.teleport(entity.getLocation().subtract(eVector.subtract(pVector).normalize().multiply(pullForce)).setDirection(pLoc.getDirection()));
                        }
                    }
                }
                // if the player doesn't have rings of res in his/her inventory
                else {
                    // update static hashmap values and cancel the runnable
                    tasks.remove(id);
                    cancel();
                }
            }
            else {
                tasks.remove(id);
                cancel();
            }
        }
    }

    public void start() {
        int tickSpeed = config.getInt("Items.polarized_stone.TickTime"); // get the tick speed
        this.runTaskTimer(plugin, 0L, tickSpeed);
    }

    public static boolean hasTask(UUID id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id) != null;
        }
        return false;
    }

    public static Map<UUID, PolarizedStoneTask> getTasks() {
        return tasks;
    }
}
