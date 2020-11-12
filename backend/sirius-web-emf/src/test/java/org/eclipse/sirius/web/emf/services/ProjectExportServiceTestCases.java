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
package org.eclipse.sirius.web.emf.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import fr.obeo.dsl.designer.sample.flow.FlowPackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.persistence.repositories.IIdMappingRepository;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.accounts.Profile;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.document.IDocumentService;
import org.eclipse.sirius.web.services.api.projects.IProjectExportService;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.Visibility;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.spring.collaborative.diagrams.INodeStyleDeserializer;
import org.eclipse.sirius.web.spring.collaborative.representations.IRepresentationDeserializer;
import org.junit.Test;
import org.springframework.boot.info.BuildProperties;

/**
 * Unit tests of the project export service.
 *
 * @author gcoutable
 *
 */
public class ProjectExportServiceTestCases {

    /*
     * If the test need to be updated, you may need to change this version and in manifest.json as well.
     */
    private static final String SIRIUS_WEB_VERSION = "0.1.0-SNAPSHOT"; //$NON-NLS-1$

    private static final UUID FLOW_DOCUMENT_ID = UUID.fromString("41f1f42f-2b3c-4226-b3f5-3befe064f38c"); //$NON-NLS-1$

    private static final String FLOW_DOCUMENT_NAME = "flow"; //$NON-NLS-1$

    private static final String PROJECT_NAME = "Test Project"; //$NON-NLS-1$

    private static final UUID PROJECT_ID = UUID.randomUUID();

    private static final String EXPECTED_PROJECT_EXPORT = "test_export/expected/"; //$NON-NLS-1$

    private static final String SOURCE_FLOW_DOCUMENT = "test_export/source/flow"; //$NON-NLS-1$

    private static final String SOURCE_TOPOGRAPHY_REPRESENTATION = "test_export/source/topography"; //$NON-NLS-1$

    private static final Project PROJECT = new Project(PROJECT_ID, PROJECT_NAME, new Profile(UUID.randomUUID(), "username"), Visibility.PUBLIC); //$NON-NLS-1$

    private final ClassLoader classLoader = ProjectExportService.class.getClassLoader();

    private final IProjectService projectService = new NoOpProjectService() {
        @Override
        public Optional<Project> getProject(UUID projectId) {
            return Optional.of(PROJECT);
        }
    };

    private final IDocumentService documentService = new NoOpDocumentService() {
        @Override
        public List<Document> getDocuments(UUID projectId) {
            // We put null in the document because we provide its content through NoOpDocumentService#getBytes.
            return List.of(new Document(FLOW_DOCUMENT_ID, PROJECT, FLOW_DOCUMENT_NAME, null));
        }

        @Override
        public Optional<byte[]> getBytes(Document document, String resourceKind) {
            byte[] flowByte = ProjectExportServiceTestCases.readFile(ProjectExportServiceTestCases.this.classLoader, SOURCE_FLOW_DOCUMENT);
            return Optional.of(flowByte);
        }
    };

    private final BuildProperties buildProperties = new BuildProperties(new Properties()) {
        @Override
        public String getVersion() {
            return SIRIUS_WEB_VERSION;
        }
    };

    private static byte[] readFile(ClassLoader classLoader, String path) {
        URL sourcedocumentFlowUrl = classLoader.getResource(path);
        byte[] flowReadFromFile = new byte[0];
        try {
            flowReadFromFile = Files.readAllBytes(Paths.get(sourcedocumentFlowUrl.toURI()));
        } catch (IOException | URISyntaxException e) {
            fail(e.getMessage());
        }
        return flowReadFromFile;
    }

    /**
     * This test will try to export a project as zip, try to test produced zip content with expected content.
     */
    @Test
    public void testExportProject() throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(INodeStyle.class, new INodeStyleDeserializer());
        module.addDeserializer(IRepresentation.class, new IRepresentationDeserializer());
        objectMapper.registerModule(module);

        byte[] topographyByte = ProjectExportServiceTestCases.readFile(this.classLoader, SOURCE_TOPOGRAPHY_REPRESENTATION);
        RepresentationDescriptor representationDescriptor = objectMapper.readValue(topographyByte, RepresentationDescriptor.class);

        IRepresentationService representationService = new NoOpRepresentationService() {
            @Override
            public List<RepresentationDescriptor> getRepresentationDescriptorsForProjectId(UUID projectId) {
                return List.of(representationDescriptor);
            }
        };

        Registry ePackageRegistry = new EPackageRegistryImpl();
        ePackageRegistry.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
        ePackageRegistry.put(FlowPackage.eINSTANCE.getNsURI(), FlowPackage.eINSTANCE);

        IIdMappingRepository idMappingRepository = new NoOpIdMappingRepository();
        IProjectExportService projectExportService = new ProjectExportService(this.projectService, this.documentService, representationService, ePackageRegistry, idMappingRepository, objectMapper,
                this.buildProperties);
        byte[] zippedProject = projectExportService.exportProjectAsZip(PROJECT_ID);
        assertThat(zippedProject).isNotEmpty();

        boolean readAtLeastOnZipEntry = false;
        try (var zippedProjectInputStream = new ZipInputStream(new ByteArrayInputStream(zippedProject))) {
            ZipEntry entryIn = zippedProjectInputStream.getNextEntry();
            while (entryIn != null) {
                String entryName = entryIn.getName();
                // Read and compare entry with the content found in expected folder
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                zippedProjectInputStream.transferTo(outputStream);

                URL resource = this.classLoader.getResource(EXPECTED_PROJECT_EXPORT + entryName);
                Path path = Paths.get(resource.toURI());
                File file = path.toFile();
                if (!file.isDirectory()) {
                    String expected = new String(Files.readAllBytes(path));
                    String current = outputStream.toString();
                    assertThat(current).isEqualTo(expected);
                    readAtLeastOnZipEntry = true;
                }
                entryIn = zippedProjectInputStream.getNextEntry();
            }
        } catch (URISyntaxException e) {
            fail(e.getMessage());
        }
        assertThat(readAtLeastOnZipEntry).isTrue();
    }
}
