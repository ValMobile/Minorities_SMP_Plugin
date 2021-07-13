package me.val_mobile.rlcraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public class RLCraft extends JavaPlugin {

    private final Commands commands = new Commands(this);

    private final CustomConfig customConfig = new CustomConfig(this);
    private final Recipes recipes = new Recipes(this);

    private final NoTreePunching noTreePunching = new NoTreePunching();
    private final DragonFightEvents dragonFight = new DragonFightEvents(this);
    private final DragonGearEvents dragonGear = new DragonGearEvents(this);
    private final SeaSerpentGearEvents seaSerpentGear = new SeaSerpentGearEvents(this);
    private final SeaSerpentDrops seaSerpentDrops = new SeaSerpentDrops(this);
    private final WitherDrops witherDrops = new WitherDrops(this);
    private final LycanitesMobsEvents lycanitesMobs = new LycanitesMobsEvents();
    private final BaubleEvents bauble = new BaubleEvents(this);
    private final WaystoneEvents waystones = new WaystoneEvents();
    
    private final Tab tab = new Tab();

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        customConfig.createSpartanWeaponryConfig();
        customConfig.createBaubleConfig();
        customConfig.createIceFireGearConfig();
        customConfig.createMobConfig();
        customConfig.createNoTreePunchingConfig();
        customConfig.createLycanitesMobsConfig();
        customConfig.createItemsConfig();

        recipes.populateSpartanWeaponryRecipes();
        recipes.populateBaubleRecipes();
        recipes.populateDragonRecipes();
        recipes.populateNtrRecipes();
        recipes.populateSeaSerpentRecipes();
        recipes.populateWaystoneRecipes();

        Logger logger = this.getLogger();
        int resourceID = 93795;
        new UpdateChecker(this, resourceID).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info(ChatColor.translateAlternateColorCodes('&', "&fYou are running the latest version"));
            } else {
                logger.info(ChatColor.translateAlternateColorCodes('&', "&aThere is a new update available: https://www.spigotmc.org/resources/rlcraft-1-17-baubles-dragons-sea-serpents.93795/history"));
            }
        });

        PluginManager pm = this.getServer().getPluginManager();

        if (this.getConfig().getBoolean("NoTreePunching")) {
            for (Recipe r : Recipes.getNtrRecipes()) {
                Bukkit.addRecipe(r);
            }

            pm.registerEvents(noTreePunching, this);
        }

        if (this.getConfig().getBoolean("Dragons")) {
            for (Recipe r : Recipes.getDragonRecipes()) {
                Bukkit.addRecipe(r);
            }


            pm.registerEvents(dragonFight, this);
            pm.registerEvents(dragonGear, this);
        }
        if (this.getConfig().getBoolean("SeaSerpents")) {
            for (Recipe r : Recipes.getSeaSerpentRecipes()) {
                Bukkit.addRecipe(r);
            }


            pm.registerEvents(seaSerpentDrops, this);
            pm.registerEvents(seaSerpentGear, this);
        }

        if (this.getConfig().getBoolean("Witherbones")) {
            pm.registerEvents(witherDrops, this);
        }

        if (this.getConfig().getBoolean("Baubles")) {
            for (Recipe r : Recipes.getBaubleRecipes()) {
                Bukkit.addRecipe(r);
            }

            pm.registerEvents(bauble, this);
        }

        if (this.getConfig().getBoolean("LycanitesMobs")) {
            pm.registerEvents(lycanitesMobs, this);
        }

        if (this.getConfig().getBoolean("Waystones")) {
            pm.registerEvents(waystones, this);
        }

        Objects.requireNonNull(this.getCommand("RLCraft")).setExecutor(commands);
        Objects.requireNonNull(this.getCommand("RLCraft")).setTabCompleter(tab);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
