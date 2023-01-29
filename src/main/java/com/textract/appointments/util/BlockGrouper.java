package com.textract.appointments.util;


import com.textract.appointments.model.BlockGroup;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.textract.model.Block;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockGrouper {
    public static List<BlockGroup> groupBlocks(final List<Block> blocks, final double proximity) {
        final List<BlockGroup> groups = new ArrayList<>();

        for (final Block block : blocks) {
            boolean added = false;
            for (final BlockGroup group : groups) {
                added = group.add(block);
                if (added) {
                    break;
                }
            }
            if (!added) {
                final BlockGroup newGroup = new BlockGroup(proximity);
                newGroup.add(block);
                groups.add(newGroup);
            }
        }
        return groups;
    }
}
