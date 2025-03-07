/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.libraries;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDependency;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.LibrariesQueryRunner;
import org.eclipse.sirius.web.tests.graphql.PublishLibrariesMutationRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to get libraries from the GraphQL API.
 *
 * @author gdaniel
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private LibrariesQueryRunner librariesQueryRunner;

    @Autowired
    private PublishLibrariesMutationRunner publishLibrariesMutationRunner;

    @Autowired
    private ILibrarySearchService librarySearchService;

    @Autowired
    private ISemanticDataSearchService semanticDataSearchService;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of libraries, when a query is performed, then the libraries are returned")
    public void givenSetOfLibrariesWhenQueryIsPerformedThenTheLibrariesAreReturned() {
        Map<String, Object> variables = Map.of("page", 0, "limit", 10);
        var result = this.librariesQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.libraries.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.libraries.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.libraries.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.libraries.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.libraries.pageInfo.count");
        assertThat(count).isPositive();

        List<String> libraryNamespaces = JsonPath.read(result, "$.data.viewer.libraries.edges[*].node.namespace");
        assertThat(libraryNamespaces)
                .isNotEmpty()
                .anySatisfy(namespace -> assertThat(namespace).isEqualTo("papaya"));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a valid studio project ID, when the publication mutation is performed, then the libraries are published")
    public void givenValidStudioProjectIdWhenPublicationMutationIsPerformedThenLibrariesArePublished() {
        var page = this.librarySearchService.findAll(PageRequest.of(0, 10));
        long initialLibraryCount = page.getTotalElements();

        String version = "0.0.1";
        String description = "Initial version";

        var input = new PublishLibrariesInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "studio-all", version, description);
        var result = this.publishLibrariesMutationRunner.run(input);
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        String typename = JsonPath.read(result, "$.data.publishLibraries.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

        long updatedLibraryCount = this.librarySearchService.findAll(PageRequest.of(1, 1)).getTotalElements();
        assertThat(updatedLibraryCount).isEqualTo(initialLibraryCount + 6);

        Optional<Library> optionalSharedComponentsLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "shared_components", version);
        assertThat(optionalSharedComponentsLibrary).isPresent();

        var sharedComponentsLibrary = optionalSharedComponentsLibrary.get();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(sharedComponentsLibrary, description, List.of());

        Optional<Library> optionalBuckLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "buck", version);
        assertThat(optionalBuckLibrary.isPresent());

        var buckLibrary = optionalBuckLibrary.get();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(buckLibrary, description, List.of());

        Optional<Library> humanFormLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "Human Form", version);
        assertThat(humanFormLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(humanFormLibrary.get(), description, List.of(buckLibrary));

        Optional<Library> newTableDescriptionLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "New Table Description", version);
        assertThat(newTableDescriptionLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(newTableDescriptionLibrary.get(), description, List.of(buckLibrary));

        Optional<Library> rootDiagramDescriptionLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "Root Diagram0", version);
        assertThat(rootDiagramDescriptionLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(rootDiagramDescriptionLibrary.get(), description, List.of(buckLibrary, sharedComponentsLibrary));

        Optional<Library> rootDiagram1DescriptionLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "Root Diagram1", version);
        assertThat(rootDiagram1DescriptionLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(rootDiagram1DescriptionLibrary.get(), description, List.of(buckLibrary, sharedComponentsLibrary));
    }

    private void assertThatLibraryHasCorrectDescriptionAndDependencies(Library library, String description, List<Library> dependencyLibraries) {
        var optionalLibrarySemanticData = this.semanticDataSearchService.findById(library.getSemanticData().getId());

        assertThat(library.getDescription()).isEqualTo(description);
        assertThat(optionalLibrarySemanticData).isPresent();

        var librarySemanticData = optionalLibrarySemanticData.get();
        var dependencyIds = librarySemanticData.getDependencies().stream()
                .map(SemanticDataDependency::dependencySemanticDataId)
                .map(AggregateReference::getId)
                .toList();
        var expectedDependencyIds = dependencyLibraries.stream()
                .map(Library::getSemanticData)
                .map(AggregateReference::getId).toList();
        assertThat(expectedDependencyIds).containsExactlyInAnyOrderElementsOf(dependencyIds);
    }

}
