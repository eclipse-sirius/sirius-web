/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.services.documents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.services.EObjectIDManager;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.document.UploadDocumentInput;
import org.eclipse.sirius.web.services.api.document.UploadDocumentSuccessPayload;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.documents.api.IUploadDocumentReportProvider;
import org.eclipse.sirius.web.services.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.messages.IServicesMessageService;
import org.eclipse.sirius.web.services.projects.NoOpServicesMessageService;
import org.eclipse.sirius.web.services.projects.api.EditingContextMetadata;
import org.eclipse.sirius.web.services.projects.api.IEditingContextMetadataProvider;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Unit tests of the create document from upload event handler.
 *
 * @author sbegaudeau
 */
public class UploadDocumentEventHandlerTests {

    private static final String FILE_NAME = "name";

    private static final String PATH_TO_XMI_DOCUMENT = "test_import/document.xmi";

    private static final String JSON_CONTENT = """
        {
          "json": {
            "version": "1.0",
            "encoding": "utf-8"
          },
          "ns": {
            "ecore": "http://www.eclipse.org/emf/2002/Ecore"
          },
          "content": [
            {
              "id": "c7fb6833-1452-49bb-aa54-5d148925c2cb",
              "eClass": "ecore:EPackage",
              "data": {
                "name": "ecore",
                "nsURI": "http://www.eclipse.org/emf/2002/Ecore",
                "nsPrefix": "ecore",
                "eClassifiers": [
                  {
                    "id": "031e998a-26b8-4eb0-9f62-d31ad2f96ca3",
                    "eClass": "ecore:EClass",
                    "data": {
                      "name": "AClass"
                    }
                  }
                ]
              }
            }
          ]
        }
        """;

    private static final String XMI_CONTENT = """
        <?xml version="1.0" encoding="UTF-8"?>
        <ecore:EPackage xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore" name="ecore" nsURI="http://www.eclipse.org/emf/2002/Ecore" nsPrefix="ecore">
          <eClassifiers xsi:type="ecore:EClass" name="AClass"/>
        </ecore:EPackage>
        """;

    @Test
    public void testUploadXMIDocument() {
        EditingDomain editingDomain = this.uploadDocument(new ByteArrayInputStream(XMI_CONTENT.getBytes()), ChangeKind.SEMANTIC_CHANGE, UploadDocumentSuccessPayload.class);
        this.testUploadXMIDocument(editingDomain);
    }

    @Test
    public void testUploadXMIDocumentFromFile() throws UnsupportedEncodingException {
        String filePath = UploadDocumentEventHandlerTests.class.getClassLoader().getResource(PATH_TO_XMI_DOCUMENT).getFile();
        String decodedfilePath = URLDecoder.decode(filePath, StandardCharsets.UTF_8.name());
        File file = new File(decodedfilePath);
        assertThat(file.exists()).isTrue();
        EditingDomain editingDomain = this.uploadDocument(file);
        this.testUploadXMIDocument(editingDomain);
    }

    @Test
    public void testUploadEmptyDocument() {
        EditingDomain editingDomain = this.uploadDocument(new ByteArrayInputStream(new byte[0]), ChangeKind.NOTHING, ErrorPayload.class);

        assertThat(editingDomain.getResourceSet().getResources().size()).isEqualTo(0);
    }

    @Test
    public void testUploadDocumentAndGetReport() {
        this.uploadDocumentAndCheckReport(new ByteArrayInputStream(XMI_CONTENT.getBytes()), ChangeKind.SEMANTIC_CHANGE, UploadDocumentSuccessPayload.class);
    }

    private void testUploadXMIDocument(EditingDomain editingDomain) {
        assertThat(editingDomain.getResourceSet().getResources().size()).isEqualTo(1);
        Resource res = editingDomain.getResourceSet().getResources().get(0);
        assertThat(res).isInstanceOf(JsonResourceImpl.class);
        assertThat(res.getContents()).hasSize(1);
        EObject root = res.getContents().get(0);
        assertThat(root).isInstanceOf(EPackage.class);
    }

    private EditingDomain uploadDocument(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream inputstream = new BufferedInputStream(fis);
            inputstream.mark(Integer.MAX_VALUE);
            return this.uploadDocument(inputstream, ChangeKind.SEMANTIC_CHANGE, UploadDocumentSuccessPayload.class);
        } catch (FileNotFoundException exception) {
            fail(exception.getMessage());
        }
        return null;
    }

    private EditingDomain uploadDocument(InputStream inputstream, String expectedChangeKind, Class<?> expectedPayload) {
        IDocumentService documentService = new IDocumentService.NoOp() {
            @Override
            public Optional<Document> createDocument(String projectId, String name, String content) {
                return Optional.of(new Document(UUID.randomUUID(), new Project(UUID.fromString(projectId), "", List.of()), name, content));
            }
        };
        IServicesMessageService messageService = new NoOpServicesMessageService();
        var editingContextMetadata = new EditingContextMetadata(List.of());
        IEditingContextMetadataProvider editingContextMetadataProvider = editingContextId -> editingContextMetadata;

        UploadDocumentEventHandler handler = new UploadDocumentEventHandler(documentService, messageService, editingContextMetadataProvider, List.of(new XMIExternalResourceLoaderService(), new JSONExternalResourceLoaderService()), List.of(), new SimpleMeterRegistry());

        UploadFile file = new UploadFile(FILE_NAME, inputstream);
        var input = new UploadDocumentInput(UUID.randomUUID(), UUID.randomUUID().toString(), file, false);

        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();

        IEditingContext editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of(), List.of());

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        assertThat(handler.canHandle(editingContext, input)).isTrue();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(expectedChangeKind);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(expectedPayload);

        return editingDomain;
    }

    private EditingDomain uploadDocumentAndCheckReport(InputStream inputstream, String expectedChangeKind, Class<?> expectedPayload) {
        IDocumentService documentService = new IDocumentService.NoOp() {
            @Override
            public Optional<Document> createDocument(String projectId, String name, String content) {
                return Optional.of(new Document(UUID.randomUUID(), new Project(UUID.fromString(projectId), "", List.of()), name, content));
            }
        };
        IServicesMessageService messageService = new NoOpServicesMessageService();
        var editingContextMetadata = new EditingContextMetadata(List.of());
        IEditingContextMetadataProvider editingContextMetadataProvider = editingContextId -> editingContextMetadata;
        List<IUploadDocumentReportProvider> uploadDocumentReportProvider = List.of(new IUploadDocumentReportProvider() {

            @Override
            public String createReport(Resource resource) {
                return "This is a report";
            }

            @Override
            public boolean canHandle(Resource resource) {
                return true;
            }
        });

        UploadDocumentEventHandler handler = new UploadDocumentEventHandler(documentService, messageService, editingContextMetadataProvider, List.of(new XMIExternalResourceLoaderService(), new JSONExternalResourceLoaderService()), uploadDocumentReportProvider, new SimpleMeterRegistry());

        UploadFile file = new UploadFile(FILE_NAME, inputstream);
        var input = new UploadDocumentInput(UUID.randomUUID(), UUID.randomUUID().toString(), file, false);

        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();

        IEditingContext editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of(), List.of());

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        assertThat(handler.canHandle(editingContext, input)).isTrue();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(expectedChangeKind);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(expectedPayload);

        assertEquals("This is a report", ((UploadDocumentSuccessPayload) payload).report());

        return editingDomain;
    }

    /**
     * This method test the {@link UploadDocumentEventHandler} generates new IDs for objects loaded from
     * {@link UploadFile} to ensure there will be no ID conflict with existing elements.
     */
    @Test
    public void testEObjectIDGenerationForUpload() {
        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();
        byte[] resourceBytes = JSON_CONTENT.getBytes();

        Map<String, String> eObjectUriToId = this.getEObjectUriToId(editingDomain, resourceBytes);

        final UUID documentId = UUID.randomUUID();
        this.simulatesDocumentUpload(editingDomain, documentId, resourceBytes);

        Resource resource = editingDomain.getResourceSet().getResource(new JSONResourceFactory().createResourceURI(documentId.toString()), false);
        // Use an output stream to prevent the resource to be written on file system.
        try (var outputStream = new ByteArrayOutputStream()) {
            resource.save(outputStream, Collections.singletonMap(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager() {
                @Override
                public String getOrCreateId(EObject eObject) {
                    // Should get the Id produced by the EObjectRandomIDSupplier during upload
                    String id = super.getOrCreateId(eObject);
                    String uriFragment = resource.getURIFragment(eObject);
                    // Use the map to get and check if, the id generate from the first load and the id generated during
                    // upload are different
                    String idBeforeUpload = eObjectUriToId.get(uriFragment);
                    assertThat(idBeforeUpload).isNotEqualTo(id);
                    return id;
                }
            }));
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
    }

    /**
     * Simulates a document upload with fake services. The uploaded resource will be put in the given editing domain
     * with the documentId as URI.
     *
     * @param editingDomain
     *            The editing domain where the uploaded resource will be put
     * @param documentId
     *            The id of the document to upload
     * @param resourceBytes
     *            The content of the document to upload
     */
    private void simulatesDocumentUpload(AdapterFactoryEditingDomain editingDomain, UUID documentId, byte[] resourceBytes) {
        IDocumentService documentService = new IDocumentService.NoOp() {

            @Override
            public Optional<Document> createDocument(String projectId, String name, String content) {
                return Optional.of(new Document(documentId, new Project(UUID.fromString(projectId), "", List.of()), name, content));
            }
        };
        IServicesMessageService messageService = new NoOpServicesMessageService();
        var editingContextMetadata = new EditingContextMetadata(List.of());
        IEditingContextMetadataProvider editingContextMetadataProvider = editingContextId -> editingContextMetadata;

        UploadDocumentEventHandler handler = new UploadDocumentEventHandler(documentService, messageService, editingContextMetadataProvider, List.of(new XMIExternalResourceLoaderService(), new JSONExternalResourceLoaderService()), List.of(), new SimpleMeterRegistry());
        UploadFile file = new UploadFile(FILE_NAME, new ByteArrayInputStream(resourceBytes));

        var input = new UploadDocumentInput(UUID.randomUUID(), UUID.randomUUID().toString(), file, false);

        IEditingContext editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of(), List.of());

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        assertThat(handler.canHandle(editingContext, input)).isTrue();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(UploadDocumentSuccessPayload.class);
    }

    /**
     * Loads a json resource with the {@link #JSON_CONTENT} to map the uriFragment of eObjects to the id supplied by
     * {@link EObjectIDManager}, then returns the map.
     */
    private Map<String, String> getEObjectUriToId(EditingDomain editingDomain, byte[] resourceBytes) {
        Map<String, String> eObjectUriToId = new HashMap<>();
        JsonResource jsonResource = new JSONResourceFactory().createResourceFromPath("json.flow");
        editingDomain.getResourceSet().getResources().add(jsonResource);

        Map<String, Object> options = new HashMap<>();
        options.put(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager());
        try {
            // Load the resource to attach the contained element to the resource.
            ByteArrayInputStream inputStream = new ByteArrayInputStream(resourceBytes);
            jsonResource.load(inputStream, options);
            /*
             * Once the resource is loaded we cannot load it anymore, so we use the save to associate, in a map, the uri
             * fragment of eObject to theirs IDs that have been computed by the IDSupplier during the previous load.
             */
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            jsonResource.save(outputStream, Collections.singletonMap(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager() {
                @Override
                public String getOrCreateId(EObject eObject) {
                    String id = super.getOrCreateId(eObject);
                    String uriFragment = jsonResource.getURIFragment(eObject);
                    eObjectUriToId.put(uriFragment, id);
                    return id;
                }
            }));
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
        return eObjectUriToId;
    }
}
