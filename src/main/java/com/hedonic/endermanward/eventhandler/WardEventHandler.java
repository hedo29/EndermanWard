package com.hedonic.endermanward.eventhandler;

import com.hedonic.endermanward.EndermanWard;
import com.hedonic.endermanward.WardData;
import com.hedonic.endermanward.block.BlockEndermanWard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WardEventHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public void onWardPlaced(BlockEvent.EntityPlaceEvent event) {
        if(event.getPlacedBlock().getBlock() instanceof BlockEndermanWard) {
            ServerLevel level = (ServerLevel) event.getLevel();
            WardData.get(level).addToList(event.getPos());
        }

    }

}
