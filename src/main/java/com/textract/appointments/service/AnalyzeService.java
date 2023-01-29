package com.textract.appointments.service;

import com.textract.appointments.model.Appointment;
import com.textract.appointments.model.BlockGroup;
import com.textract.appointments.util.AppointmentUtil;
import com.textract.appointments.util.BlockGrouper;
import com.textract.appointments.util.MatcherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.textract.model.Block;
import software.amazon.awssdk.services.textract.model.BlockType;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyzeService {

    private final IcsService icsService;

    private final TextractService textractService;

    public AnalyzeService(@Autowired final IcsService icsService, @Autowired final TextractService textractService) {
        this.icsService = icsService;
        this.textractService = textractService;
    }

    public Resource analyzeDocument(final byte[] documentBytes) {

        final DetectDocumentTextResponse detectDocumentTextResponse = textractService.detectDocumentText(documentBytes);

        final List<Block> filteredBlocks = detectDocumentTextResponse.blocks().stream()
                .filter(block -> block.blockType().equals(BlockType.LINE))
                .collect(Collectors.toList());

        final List<BlockGroup> blockGroups = BlockGrouper.groupBlocks(filteredBlocks, 0.01);

        final List<String> appointmentStringList = blockGroups.stream()
                .map(blockGroup -> blockGroup.getBlocks().stream().map(Block::text).collect(Collectors.joining(" ")))
                .filter(MatcherUtil::matchesWholeLine)
                .collect(Collectors.toList());

        final List<Appointment> appointmentList = AppointmentUtil.parseAppointmentList(appointmentStringList);

        return icsService.convertToIcsFile(appointmentList);
    }

}
