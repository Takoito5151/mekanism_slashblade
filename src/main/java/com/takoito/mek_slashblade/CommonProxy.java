package com.takoito.mek_slashblade;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.SlashBlade;

public class CommonProxy {

    public static Item spa;
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)

    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        spa = new Item().setCreativeTab(SlashBlade.tab)
            .setUnlocalizedName("SPA")
            .setTextureName("mek_slashblade:spa");

        GameRegistry.registerItem(spa, "SPA");


        MekSlashblade.LOG.info(Config.greeting);
        MekSlashblade.LOG.info("I am Mekanism:Slashblade at version " + Tags.VERSION);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        if (Loader.isModLoaded("MekanismTools")) {
            GameRegistry.addRecipe(new RecipeWrapMekBlade());

        }
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        List<ItemStack> required_spa = new ArrayList<>();
        required_spa.add(SlashBlade.getCustomBlade("flammpfeil.slashblade.named.yuzukitukumo"));
        required_spa.add(SlashBlade.getCustomBlade("flammpfeil.slashblade.named.muramasa"));
        required_spa.add(SlashBlade.getCustomBlade("flammpfeil.slashblade.named.tagayasan"));
        required_spa.add(SlashBlade.getCustomBlade("flammpfeil.slashblade.named.doutanuki"));
        required_spa.add(SlashBlade.getCustomBlade("flammpfeil.slashblade.named.sange"));
        required_spa.add(SlashBlade.getCustomBlade("flammpfeil.slashblade.named.yamato"));
        required_spa.add(SlashBlade.getCustomBlade("flammpfeil.slashblade.named.koseki"));
        List<NBTTagCompound> tag_required_spa = new ArrayList<>();
        for (int i =0;i<7;i++){
            tag_required_spa.add(ItemSlashBlade.getItemTagCompound(required_spa.get(i)));
            if (i!=5){
                ItemSlashBlade.KillCount.set(tag_required_spa.get(i), 1500);
            }else{
                ItemSlashBlade.KillCount.set(tag_required_spa.get(i), 3000);
            }
        }
        ItemStack proudSoul = GameRegistry.findItemStack(SlashBlade.modid,SlashBlade.ProudSoulStr,1);
        ItemStack sphere = GameRegistry.findItemStack("flammpfeil.slashblade", "sphere_bladesoul", 1);
        ItemStack atomic_alloy = GameRegistry.findItemStack("Mekanism","AtomicAlloy",1);
        IRecipe recipe_spa = new ShapedOreRecipe(
            CommonProxy.spa,
            "NXM",
            "WAD",
            "SYK",
            'N',required_spa.get(0),
            'X',sphere,
            'M',required_spa.get(1),
            'W',required_spa.get(2),
            'A',atomic_alloy,
            'D',required_spa.get(3),
            'S',required_spa.get(4),
            'Y',required_spa.get(5),
            'K',required_spa.get(6)
        );
        SlashBlade.addRecipe("spa",recipe_spa);

    }

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}
}
