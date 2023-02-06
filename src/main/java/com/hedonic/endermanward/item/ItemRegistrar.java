package com.hedonic.endermanward.item;

import com.hedonic.endermanward.EndermanWard;
import com.hedonic.endermanward.block.BlockEndermanWard;
import com.hedonic.endermanward.block.BlockRegistrar;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistrar {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EndermanWard.MOD_ID);

    public static final RegistryObject<Item> ITEM_ENDERMAN_WARD = ItemRegistrar.ITEMS.register(BlockEndermanWard.REG_NAME, () -> new BlockItem(BlockRegistrar.BLOCK_ENDERMAN_WARD.get(), new Item.Properties()));

    public static void register()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
