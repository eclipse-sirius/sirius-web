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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;
import org.eclipse.sirius.components.graphql.tests.api.IExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.library.dto.ImportLibrariesInput;
import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.application.library.services.api.IEditingContextLibraryLoader;
import org.eclipse.sirius.web.application.object.services.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.LibraryDependency;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.papaya.services.library.InitializeStandardLibraryEvent;
import org.eclipse.sirius.web.papaya.services.library.api.IStandardLibrarySemanticDataInitializer;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ImportLibrariesMutationRunner;
import org.eclipse.sirius.web.tests.graphql.LibrariesQueryRunner;
import org.eclipse.sirius.web.tests.graphql.PublishLibrariesMutationRunner;
import org.junit.jupiter.api.BeforeEach;
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
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private LibrariesQueryRunner librariesQueryRunner;

    @Autowired
    private PublishLibrariesMutationRunner publishLibrariesMutationRunner;

    @Autowired
    private ImportLibrariesMutationRunner importLibrariesMutationRunner;

    @Autowired
    private ILibrarySearchService librarySearchService;

    @Autowired
    private IStandardLibrarySemanticDataInitializer standardLibrarySemanticDataInitializer;

    @Autowired
    private ISemanticDataUpdateService semanticDataUpdateService;

    @Autowired
    private IExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @Autowired
    private IReadOnlyObjectPredicate readOnlyObjectPredicate;

    @BeforeEach
    public void beforeEach() {
        // Re-create the Papaya standard library before each test: it will be deleted by the cleanup script.
        var initializeJavaStandardLibraryEvent = new InitializeStandardLibraryEvent(UUID.randomUUID(), "java", "Java Standard Library", "17.0.0", "The standard library of the Java programming language");
        this.standardLibrarySemanticDataInitializer.initializeStandardLibrary(initializeJavaStandardLibraryEvent);
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }

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
                .anySatisfy(namespace -> assertThat(namespace).isEqualTo("java"));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when a dependency to a library is added, then the editing context contains the library and the library is read-only")
    public void givenProjectWhenLibraryDependencyIsAddedThenEditingContextContainsLibrary() {
        Optional<Library> library = this.librarySearchService.findByNamespaceAndNameAndVersion("java", "Java Standard Library", "17.0.0");
        assertThat(library).isPresent();
        var result = this.semanticDataUpdateService.addDependency(null, PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, library.get().getId());
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
        assertThat(result).isInstanceOf(Success.class);


        BiFunction<IEditingContext, IInput, IPayload> function = (editingContext, executeEditingContextFunctionInput) -> {
            if (editingContext instanceof IEMFEditingContext emfEditingContext) {
                assertThat(emfEditingContext.getDomain().getResourceSet().getResources())
                    .anyMatch(resource -> {
                        Optional<String> optName = resource.eAdapters().stream()
                                .filter(ResourceMetadataAdapter.class::isInstance)
                                .map(ResourceMetadataAdapter.class::cast)
                                .map(ResourceMetadataAdapter::getName)
                                .findFirst();
                        return optName.isPresent()
                                && optName.get().equals("Java Standard Library")
                                && Objects.equals(resource.getURI().scheme(), IEditingContextLibraryLoader.LIBRARY_SCHEME)
                                && this.readOnlyObjectPredicate.test(resource);
                    });
                return new SuccessPayload(executeEditingContextFunctionInput.id());
            }
            return new ErrorPayload(executeEditingContextFunctionInput.id(), "Invalid editing context");
        };

        var input = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), function);
        var payload = this.executeEditingContextFunctionRunner.execute(input).block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);
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

        String typename = JsonPath.read(result, "$.data.publishLibraries.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());

        long updatedLibraryCount = this.librarySearchService.findAll(PageRequest.of(1, 1)).getTotalElements();
        assertThat(updatedLibraryCount).isEqualTo(initialLibraryCount + 6);

        Optional<Library> sharedComponentsLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "shared_components", version);
        assertThat(sharedComponentsLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(sharedComponentsLibrary.get(), description, List.of());

        Optional<Library> buckLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "buck", version);
        assertThat(buckLibrary.isPresent());
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(buckLibrary.get(), description, List.of());

        Optional<Library> humanFormLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "Human Form", version);
        assertThat(humanFormLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(humanFormLibrary.get(), description, List.of(buckLibrary.get().getId()));

        Optional<Library> newTableDescriptionLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "New Table Description", version);
        assertThat(newTableDescriptionLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(newTableDescriptionLibrary.get(), description, List.of(buckLibrary.get().getId()));

        Optional<Library> rootDiagramDescriptionLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "Root Diagram0", version);
        assertThat(rootDiagramDescriptionLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(rootDiagramDescriptionLibrary.get(), description, List.of(buckLibrary.get().getId(), sharedComponentsLibrary.get().getId()));

        Optional<Library> rootDiagram1DescriptionLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "Root Diagram1", version);
        assertThat(rootDiagram1DescriptionLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(rootDiagram1DescriptionLibrary.get(), description, List.of(buckLibrary.get().getId(), sharedComponentsLibrary.get().getId()));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when a library is imported as a copy, then the editing context contains the copy of the library")
    public void givenProjectWhenLibraryIsImportedAsCopyThenEditingContextContainsTheLibraryCopy() {
        Optional<Library> library = this.librarySearchService.findByNamespaceAndNameAndVersion("java", "Java Standard Library", "17.0.0");
        assertThat(library).isPresent();
        var importLibrariesInput = new ImportLibrariesInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), "copy", List.of(library.get().getId().toString()));
        this.importLibrariesMutationRunner.run(importLibrariesInput);

        BiFunction<IEditingContext, IInput, IPayload> function = (editingContext, executeEditingContextFunctionInput) -> {
            if (editingContext instanceof IEMFEditingContext emfEditingContext) {
                assertThat(emfEditingContext.getDomain().getResourceSet().getResources())
                    .anyMatch(resource -> {
                        Optional<String> optName = resource.eAdapters().stream()
                                .filter(ResourceMetadataAdapter.class::isInstance)
                                .map(ResourceMetadataAdapter.class::cast)
                                .map(ResourceMetadataAdapter::getName)
                                .findFirst();
                        return optName.isPresent()
                                && optName.get().equals("Java Standard Library")
                                // The library should be in a RESOURCE_SCHEME resource: it has been copied and is a regular Sirius resource.
                                && Objects.equals(resource.getURI().scheme(), IEMFEditingContext.RESOURCE_SCHEME);
                    });
                return new SuccessPayload(executeEditingContextFunctionInput.id());
            }
            return new ErrorPayload(executeEditingContextFunctionInput.id(), "Invalid editing context");
        };

        var input = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), function);
        var payload = this.executeEditingContextFunctionRunner.execute(input).block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);
    }

    private void assertThatLibraryHasCorrectDescriptionAndDependencies(Library library, String description, List<UUID> dependencyIds) {
        assertThat(library)
            .returns(description, Library::getDescription)
            .extracting(Library::getDependencies)
            .asInstanceOf(InstanceOfAssertFactories.list(LibraryDependency.class))
            .hasSize(dependencyIds.size())
            .map(LibraryDependency::dependencyLibraryId)
            .map(AggregateReference::getId)
            .containsAll(dependencyIds);
    }

}
