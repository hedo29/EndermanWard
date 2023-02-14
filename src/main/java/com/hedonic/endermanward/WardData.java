package com.hedonic.endermanward;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nonnull;
import java.util.ArrayList;
public class WardData extends SavedData {

    private ArrayList<BlockPos> wardList = new ArrayList<>();

    public ArrayList<BlockPos> getWardList() {
        return this.wardList;
    }

    public void removeFromList(BlockPos block) {
        BlockPos pos = null;
        for(BlockPos ward: this.wardList) {
            if(ward.getX() == block.getX() && ward.getY() == block.getY() && ward.getZ() == block.getZ()) {
                pos = ward;
            }
        }
        if(null != pos) {
            this.wardList.remove(pos);
        } else {
            EndermanWard.logMessageError("Attempted to remove a ward that wasn't listed in stored data. Please make sure your world is not write-protected or locked. This may cause wards to become ineffective when placed.");
        }
        this.setDirty();

    }

    public void addToList(BlockPos block) {
        EndermanWard.logMessageDebug("Adding block with pos: " + block.getX() + ", " + block.getY() + ", " + block.getZ());
        this.wardList.add(block);
        this.setDirty();
    }

    @Nonnull
    public static WardData get(Level level) {

        if(level.isClientSide) {
            throw new RuntimeException("No need to process data client-side.");
        }

        DimensionDataStorage dimensionData = ((ServerLevel) level).getDataStorage();

        return dimensionData.computeIfAbsent(WardData::load, WardData::create, "wards");
    }

    //Create new list if data doesn't exist.
    public static WardData create() {
        return new WardData();
    }

    //Recreate ward list from data.
    public static WardData load(CompoundTag tag) {

        ListTag wards = tag.getList("wards", tag.getId());

        WardData data = create();
        data.wardList = new ArrayList<>();

        //Don't NPE on no wards being placed
        if(wards.isEmpty()) {
            EndermanWard.logMessageWarn("No stored wards found for this dimension!");
            data.wardList = new ArrayList<>();
        }

        wards.forEach(rawTag -> {

            CompoundTag wardTag = (CompoundTag)rawTag;
            EndermanWard.logMessageDebug("Found saved ward location at: " +
                    wardTag.getInt("x") + ", " +
                    wardTag.getInt("y") + ", " +
                    wardTag.getInt("z"));
            data.wardList.add(new BlockPos(wardTag.getInt("x"), wardTag.getInt("y"), wardTag.getInt("z")));
        });
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag wards = new ListTag();
        this.wardList.forEach(ward -> {
            CompoundTag wardTag = new CompoundTag();
            EndermanWard.logMessageDebug("Saving ward location at: " +
                    ward.getX() + ", " +
                    ward.getY() + ", " +
                    ward.getZ());
            wardTag.putInt("x", ward.getX());
            wardTag.putInt("y", ward.getY());
            wardTag.putInt("z", ward.getZ());
            wards.add(wardTag);
        });

        tag.put("wards", wards);
        return tag;
    }
}
