package com.takoito.mek_slashblade;

import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
//import cpw.mods.fml.common.Optional.Method;
//import cofh.api.energy.IEnergyContainerItem;
import mekanism.api.EnumColor;
import mekanism.api.energy.IEnergizedItem;
//import ic2.api.item.ISpecialElectricItem;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

@InterfaceList({@Interface(
    iface = "ic2.api.item.ISpecialElectricItem",
    modid = "IC2"
)})

public class ItemEnergizedSlashBladeNamed extends ItemSlashBladeNamed implements IEnergizedItem/*, IEnergyContainerItem*/ {
    public double MAX_ELECTRICITY;
    public ItemEnergizedSlashBladeNamed(ToolMaterial par2EnumToolMaterial, float baseAttackModifiers, double maxElectricity) {
        super(par2EnumToolMaterial, baseAttackModifiers);
        this.MAX_ELECTRICITY=maxElectricity;
    }

    public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
        list.add(EnumColor.AQUA + LangUtils.localize("tooltip.storedEnergy") + ": " + EnumColor.GREY + MekanismUtils.getEnergyDisplay(this.getEnergy(itemstack)));
    }

//    public double getDurabilityForDisplay(ItemStack stack) {
//        return (double)1.0F - this.getEnergy(stack) / this.getMaxEnergy(stack);
//    }

//    @Override
//    public int receiveEnergy(ItemStack itemStack, int i, boolean b) {
//        return 0;
//    }
//
//    @Override
//    public int extractEnergy(ItemStack itemStack, int i, boolean b) {
//        return 0;
//    }
//
//    @Override
//    public int getEnergyStored(ItemStack itemStack) {
//        return 0;
//    }
//
//    @Override
//    public int getMaxEnergyStored(ItemStack itemStack) {
//        return 0;
//    }

    @Override
    public double getEnergy(ItemStack itemStack) {
        return itemStack.stackTagCompound == null ? (double)0.0F : itemStack.stackTagCompound.getDouble("electricity");
    }

    @Override
    public void setEnergy(ItemStack itemStack, double v) {
        if (itemStack.stackTagCompound == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        double electricityStored = Math.max(Math.min(v, this.getMaxEnergy(itemStack)), (double)0.0F);
        itemStack.stackTagCompound.setDouble("electricity", electricityStored);
    }

    @Override
    public double getMaxEnergy(ItemStack itemStack) {
        return this.MAX_ELECTRICITY;
    }

    @Override
    public double getMaxTransfer(ItemStack itemStack) {
        return this.getMaxEnergy(itemStack) * 0.005;
    }

    @Override
    public boolean canReceive(ItemStack itemStack) {
        return this.getMaxEnergy(itemStack) - this.getEnergy(itemStack) > (double)0.0F;
    }

    @Override
    public boolean canSend(ItemStack itemStack) {
        return this.getEnergy(itemStack) > (double)0.0F;
    }
}
