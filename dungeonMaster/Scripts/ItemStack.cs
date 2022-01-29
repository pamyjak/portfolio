using System;
using System.Collections.Generic;

// ItemStack //
public class ItemStack {
    public byte amount;
    public short durability;
    public Material material;
    public ItemData item_data;
    //  public string sprite;

    // Constructors
    public ItemStack() {
        this.amount = 1;
        this.durability = -1;
        this.material = Material.NULL;
        this.item_data = new ItemData();
    }
    public ItemStack(Material type) {
        this.amount = 1;
        this.durability = -1;
        this.material = type;
        this.item_data = new ItemData();
    }
    public ItemStack(Material type, byte count) {
        this.amount = count;
        this.durability = -1;
        this.material = type;
        this.item_data = new ItemData();
    }

    // Methods
    public byte getAmout() {
        return this.amount;
    }
    public void setAmount(byte value) {
        this.amount = value;
    }

    public short getDurability() {
        return this.amount;
    }
    public void setDurability(short value) {
        this.durability = value;
    }

    public Material getMaterial() {
        return this.material;
    }
    public void setMaterial(Material type) {
        this.material = type;
    }

    public ItemData getItemData() {
        return this.item_data;
    }
    public void setItemData(ItemData data) {
        this.item_data = data;
    }

}

// ItemData //
public class ItemData {
    public String displayName;
    public List<String> lore;
    public List<Enchantment> enchantments;
}

// Enchantment //
public class EnchantmentData {
    public Enchantment type;
    public byte level;
}