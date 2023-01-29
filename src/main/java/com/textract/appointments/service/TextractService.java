package com.textract.appointments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextRequest;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextResponse;
import software.amazon.awssdk.services.textract.model.Document;

@Service
public class TextractService {

    private final TextractClient textractClient;

    public TextractService(@Autowired final TextractClient textractClient) {
        this.textractClient = textractClient;
    }

    public DetectDocumentTextResponse detectDocumentText(final byte[] documentBytes) {
        final Document document = Document.builder()
                .bytes(SdkBytes.fromByteArray(documentBytes))
                .build();

        return textractClient.detectDocumentText(DetectDocumentTextRequest.builder().document(document).build());
    }
}
