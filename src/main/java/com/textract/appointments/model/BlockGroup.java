package com.textract.appointments.model;

import lombok.Getter;
import software.amazon.awssdk.services.textract.model.Block;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BlockGroup {

    private final List<Block> blocks;

    private final double proximity;

    public BlockGroup(final double proximity) {
        this.blocks = new ArrayList<>();
        this.proximity = proximity;
    }

    public boolean add(final Block newBlock) {
        if (blocks.size() == 0) {
            blocks.add(newBlock);
            return true;
        }
        for (final Block block : blocks) {
            if (Math.abs(block.geometry().boundingBox().top() - newBlock.geometry().boundingBox().top()) < proximity) {
                blocks.add(newBlock);
                return true;
            }
        }
        return false;
    }
}

