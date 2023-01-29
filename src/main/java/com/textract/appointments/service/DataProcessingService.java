package com.textract.appointments.service;

import com.textract.appointments.config.TextractProperties;
import com.textract.appointments.model.Appointment;
import com.textract.appointments.model.BlockGroup;
import com.textract.appointments.util.AppointmentUtil;
import com.textract.appointments.util.BlockGrouper;
import com.textract.appointments.util.MatcherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.textract.model.Block;
import software.amazon.awssdk.services.textract.model.BlockType;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataProcessingService {

    private TextractProperties textractProperties;

    public DataProcessingService(@Autowired final TextractProperties textractProperties) {
        this.textractProperties = textractProperties;
    }

    public List<Appointment> processDetectDocumentResponse(final DetectDocumentTextResponse detectDocumentTextResponse) {
        final List<Block> filteredBlocks = detectDocumentTextResponse.blocks().stream()
                .filter(block -> block.blockType().equals(BlockType.LINE))
                .collect(Collectors.toList());

        final List<BlockGroup> blockGroups = BlockGrouper.groupBlocks(filteredBlocks, textractProperties.getAppointmentLineProximity());

        final List<String> appointmentStringList = blockGroups.stream()
                .map(blockGroup -> blockGroup.getBlocks().stream().map(Block::text).collect(Collectors.joining(" ")))
                .filter(MatcherUtil::matchesWholeLine)
                .collect(Collectors.toList());

        return AppointmentUtil.parseAppointmentList(appointmentStringList);
    }

}
