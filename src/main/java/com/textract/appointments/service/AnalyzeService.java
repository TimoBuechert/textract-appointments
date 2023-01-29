package com.textract.appointments.service;

import com.textract.appointments.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextResponse;

import java.util.List;

@Service
public class AnalyzeService {

    private final IcsService icsService;

    private final TextractService textractService;

    private final DataProcessingService dataProcessingService;

    public AnalyzeService(@Autowired final IcsService icsService, @Autowired final TextractService textractService,
                          @Autowired final DataProcessingService dataProcessingService) {
        this.icsService = icsService;
        this.textractService = textractService;
        this.dataProcessingService = dataProcessingService;
    }

    public Resource analyzeDocument(final byte[] documentBytes) {

        final DetectDocumentTextResponse detectDocumentTextResponse = textractService.detectDocumentText(documentBytes);

        final List<Appointment> appointmentList = dataProcessingService.processDetectDocumentResponse(detectDocumentTextResponse);

        return icsService.convertToIcsFile(appointmentList);
    }

}
