package com.hedonic.endermanward.block;

import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class BlockEndermanWard extends Block {

    public static String REG_NAME = "enderman_ward";

    public BlockEndermanWard ()
    {
        super(BlockBehaviour.Properties.of(Material.DECORATION).strength(2).sound(SoundType.WOOD));
    }

    @Override
    public RenderShape getRenderShape(BlockState iBlockState) {
        return RenderShape.MODEL;
    }
}
