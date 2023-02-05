package com.hedonic.endermanward.eventhandler;

import com.hedonic.endermanward.EndermanWardConfig;
import com.hedonic.endermanward.block.BlockEndermanWard;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EndermanEventHandler {

    @SubscribeEvent
    public void onEndermanGrief(EntityMobGriefingEvent event) {
        if(!EndermanWardConfig.allowGriefing) {
            if (event.getEntity() instanceof EnderMan) {
                if (!checkIfEndermanAllowed((EnderMan) event.getEntity())) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEndermanTeleport(EntityTeleportEvent.EnderEntity event) {
        if(!EndermanWardConfig.allowTeleporting) {
            if (event.getEntity() instanceof EnderMan) {
                if (!checkIfEndermanAllowed((EnderMan) event.getEntity())) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    private boolean checkIfEndermanAllowed(EnderMan enderMan) {
        Level level = enderMan.level;

        double cur_x = enderMan.getX();
        double cur_y = enderMan.getX();
        double cur_z = enderMan.getX();

        for (double checkX = cur_x - 63; checkX < cur_x + 63; checkX++) {
            for (double checkY = cur_y - 63; checkY < cur_y + 63; checkY++) {
                for (double checkZ = cur_z - 63; checkZ < cur_z + 63; checkZ++) {
                    BlockPos block = new BlockPos(checkX, checkY, checkZ);
                    Block checkBlock = level.getBlockState(block).getBlock();
                    if(checkBlock instanceof BlockEndermanWard) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
