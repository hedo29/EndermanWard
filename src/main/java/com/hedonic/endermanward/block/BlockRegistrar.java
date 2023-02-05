package com.hedonic.endermanward.block;

import com.hedonic.endermanward.EndermanWard;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockRegistrar {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EndermanWard.MOD_ID);

    public static final RegistryObject<Block> BLOCK_ENDERMAN_WARD = BLOCKS.register(BlockEndermanWard.REG_NAME, () -> new BlockEndermanWard());

    public static void register()
    {
        LOGGER.log(Level.ALL, "Adding block to registry....");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }
}
