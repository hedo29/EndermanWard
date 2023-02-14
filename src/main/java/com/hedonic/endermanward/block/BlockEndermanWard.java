package com.hedonic.endermanward.block;

import com.hedonic.endermanward.EndermanWardConfig;
import com.hedonic.endermanward.WardData;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.ItemStack;

public class BlockEndermanWard extends Block {

    public static String REG_NAME = "enderman_ward";

    public BlockEndermanWard ()
    {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(2).sound(SoundType.STONE));
    }


    @Override
    public RenderShape getRenderShape(BlockState iBlockState) {
        return RenderShape.MODEL;
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, BlockEntity entity, ItemStack stack) {
        WardData.get(level).removeFromList(pos);
        if(EndermanWardConfig.dropOnDestroy) {
            super.playerDestroy(level, player, pos, state, entity, stack);
        } else {
            player.awardStat(Stats.BLOCK_MINED.get(this));
            player.causeFoodExhaustion(0.005F);
        }


    }

}
