/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.spring.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zeroturnaround.zip.ZipUtil;
import java.text.MessageFormat;
import vanth2polymer.plugin.files.*;
/**
 * The entry point of the HTTP API to download documents. vanth
 * <p>
 * This endpoint will be available on the API base path prefix with download segment and followed by the document Id
 * used as a suffix. As such, users will be able to send document download request to the following URL:
 * </p>
 *
 * <pre>
 * PROTOCOL://DOMAIN.TLD(:PORT)/API_BASE_PATH/projects/PROJECT_ID/documents/DOCUMENT_ID
 * </pre>
 *
 * <p>
 * In a development environment, the URL will most likely be:
 * </p>
 *
 * <pre>
 * http://localhost:8080/api/projects/PROJECT_ID/documents/DOCUMENT_ID
 * </pre>
 *
 * <p>
 * Only documents of type xmi are supported.
 * </p>
 *
 * @author smonnier
 */
@Controller
@RequestMapping(URLConstants.DOCUMENT_BASE_PATH)
public class DocumentController {

    private final IDocumentService documentService;

    private final AmazonClient amazonClient;

    private MessageDigest sha;

    private final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    public DocumentController(IDocumentService documentService) {
        this.documentService = Objects.requireNonNull(documentService);
        this.amazonClient = new AmazonClient();
        try {
            this.sha = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/{documentId}")
    @ResponseBody
    public ResponseEntity<Resource> getDocument(@PathVariable String projectId, @PathVariable String documentId) {
        var optionalProjectId = this.convertToUUID(projectId);
        var optionalDocumentId = this.convertToUUID(documentId);
        Optional<Document> optionalDocument = optionalProjectId.flatMap(pId -> {
            return optionalDocumentId.flatMap(dId -> this.documentService.getDocument(pId, dId));
        });

        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            Optional<byte[]> optionalBytes = this.documentService.getBytes(document, IDocumentService.RESOURCE_KIND_XMI);
            if (optionalBytes.isPresent()) {
                byte[] bytes = optionalBytes.get();

                // @formatter:off
                ContentDisposition contentDisposition = ContentDisposition.builder("attachment")  //$NON-NLS-1$
                        .filename(document.getName())
                        .build();
                // @formatter:on
                this.logger.debug("HOLA MUNDO");
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(contentDisposition);
                headers.setContentType(MediaType.APPLICATION_XML);
                headers.setContentLength(bytes.length);
                InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/{documentId}/generate/{languageBack}/{languageFront}")
    @ResponseBody
    public String getCode(@PathVariable String projectId, @PathVariable String documentId, @PathVariable String languageBack, @PathVariable String languageFront) {
        try {
            System.out.println("hola mundo");
            var optionalProjectId = this.convertToUUID(projectId);
            var optionalDocumentId = this.convertToUUID(documentId);
            Optional<Document> optionalDocument = optionalProjectId.flatMap(pId -> {
                return optionalDocumentId.flatMap(dId -> this.documentService.getDocument(pId, dId));
            });
            
            if (optionalDocument.isPresent()) {
                Document document = optionalDocument.get();
                Optional<byte[]> optionalBytes = this.documentService.getBytes(document, IDocumentService.RESOURCE_KIND_XMI);
                if (optionalBytes.isPresent()) {
                    byte[] bytes = optionalBytes.get();

                // @formatter:off
                ContentDisposition contentDisposition = ContentDisposition.builder("attachment")  //$NON-NLS-1$
                        .filename(document.getName())
                        .build();
                // @formatter:on
                   
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentDisposition(contentDisposition);
                    headers.setContentType(MediaType.APPLICATION_XML);
                    headers.setContentLength(bytes.length);
                    InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
                    String documentName = document.getName().replace(" ","");
                    File file = new File(documentName + ".vanth");
                    InputStream resource2 = new ByteArrayInputStream(bytes);
                    this.copyInputStreamToFile(resource2, file);

                    if(languageBack.contains("php")){
                    vanth2api.main.Generate trans = new vanth2api.main.Generate();
                    trans.runGenerate(file.getPath(), "project/back");
                    }
                   
                    if(languageFront.contains("polymer")){
                    Vanth2polymer transPolymer = new Vanth2polymer();
                    transPolymer.runGenerate(file.getPath(),documentName+".tags");
                    
                    tags2src.main.Generate trans = new tags2src.main.Generate();
                    trans.runGenerate(documentName+".tags","project/front");
                    }

                    if(languageBack.contains("django")){
                    vanth2django.main.Generate transPHP = new vanth2django.main.Generate();
                    transPHP.runGenerate(file.getPath(),"project/back");
                    }
                    
                    File output = new File("project/demo.zip");
                    ZipUtil.pack(new File("project"), output);

                    String hash = this.getFileChecksum(output);
                    

                    String url = this.amazonClient.uploadFile(output, hash + "_code.zip");
                    this.deleteDirectory(new File("project"));
                    this.logger.info(url);
                    file.delete();
                    output.delete();
                    //return new ResponseEntity<>(resource, headers, HttpStatus.OK);
                    return url;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return "";
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    private String getFileChecksum(File file) throws IOException {
        // Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        // Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        // Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            this.sha.update(byteArray, 0, bytesCount);
        } ;

        // close the stream; We don't need it now.
        fis.close();

        // Get the hash's bytes
        byte[] bytes = this.sha.digest();

        // This bytes[] has bytes in decimal format;
        // Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        // return complete hash
        return sb.toString();
    }

    private Optional<UUID> convertToUUID(String id) {
        Optional<UUID> optionalId = Optional.empty();
        try {
            optionalId = Optional.of(UUID.fromString(id));
        } catch (IllegalArgumentException exception) {
            this.logger.error(exception.getMessage(), exception);
        }
        return optionalId;
    }

    private void copyInputStreamToFile(InputStream in, File file) {
        try {

            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}