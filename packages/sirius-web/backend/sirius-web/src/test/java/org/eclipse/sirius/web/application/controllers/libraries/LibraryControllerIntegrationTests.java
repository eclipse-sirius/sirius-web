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

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.UnresolvedProxyCrossReferencer;
import org.eclipse.sirius.components.collaborative.dto.EditingContextEventInput;
import org.eclipse.sirius.components.collaborative.trees.dto.InvokeSingleClickTreeItemContextMenuEntryInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.graphql.tests.EditingContextEventSubscriptionRunner;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;
import org.eclipse.sirius.components.graphql.tests.api.IExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.application.library.dto.UpdateLibraryInput;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDependency;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.LibrariesQueryRunner;
import org.eclipse.sirius.web.tests.graphql.PublishLibrariesMutationRunner;
import org.eclipse.sirius.web.tests.graphql.SingleClickTreeItemContextMenuEntryMutationRunner;
import org.eclipse.sirius.web.tests.graphql.UpdateLibraryMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the library controllers.
 *
 * @author gdaniel
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private LibrariesQueryRunner librariesQueryRunner;

    @Autowired
    private PublishLibrariesMutationRunner publishLibrariesMutationRunner;

    @Autowired
    private UpdateLibraryMutationRunner updateLibraryMutationRunner;

    @Autowired
    private IExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @Autowired
    private SingleClickTreeItemContextMenuEntryMutationRunner singleClickTreeItemContextMenuEntryMutationRunner;

    @Autowired
    private ILibrarySearchService librarySearchService;

    @Autowired
    private ISemanticDataSearchService semanticDataSearchService;

    @Autowired
    private IObjectSearchService objectSearchService;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @Autowired
    private EditingContextEventSubscriptionRunner editingContextEventSubscriptionRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
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
        this.assertThatLibraryDocumentsAreReadOnly(sharedComponentsLibrary);

        Optional<Library> optionalBuckLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "buck", version);
        assertThat(optionalBuckLibrary).isPresent();

        var buckLibrary = optionalBuckLibrary.get();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(buckLibrary, description, List.of());
        this.assertThatLibraryDocumentsAreReadOnly(buckLibrary);

        Optional<Library> humanFormLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "Human Form", version);
        assertThat(humanFormLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(humanFormLibrary.get(), description, List.of(buckLibrary));
        this.assertThatLibraryDocumentsAreReadOnly(humanFormLibrary.get());

        Optional<Library> newTableDescriptionLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "New Table Description", version);
        assertThat(newTableDescriptionLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(newTableDescriptionLibrary.get(), description, List.of(buckLibrary));
        this.assertThatLibraryDocumentsAreReadOnly(newTableDescriptionLibrary.get());

        Optional<Library> rootDiagramDescriptionLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "Root Diagram0", version);
        assertThat(rootDiagramDescriptionLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(rootDiagramDescriptionLibrary.get(), description, List.of(buckLibrary, sharedComponentsLibrary));
        this.assertThatLibraryDocumentsAreReadOnly(rootDiagramDescriptionLibrary.get());

        Optional<Library> rootDiagram1DescriptionLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(StudioIdentifiers.SAMPLE_STUDIO_PROJECT, "Root Diagram1", version);
        assertThat(rootDiagram1DescriptionLibrary).isPresent();
        this.assertThatLibraryHasCorrectDescriptionAndDependencies(rootDiagram1DescriptionLibrary.get(), description, List.of(buckLibrary, sharedComponentsLibrary));
        this.assertThatLibraryDocumentsAreReadOnly(rootDiagram1DescriptionLibrary.get());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with dependencies, when a dependency is updated, then the editing context contains the new version of the dependency")
    public void givenProjectWithDependenciesWhenDependencyIsUpdatedThenEditingContextContainsTheNewVersionOfDependency() {
        Optional<Library> optionalLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "sirius-web-tests-data", "2.0.0");
        assertThat(optionalLibrary).isPresent();

        var editingContextEventInput = new EditingContextEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString());
        var flux = this.editingContextEventSubscriptionRunner.run(editingContextEventInput);

        BiFunction<IEditingContext, IInput, IPayload> checkInitialEditingContextFunction = (editingContext, executeEditingContextFunctionInput) -> {
            if (editingContext instanceof IEMFEditingContext emfEditingContext) {
                assertThat(emfEditingContext.getDomain().getResourceSet().getResources())
                    .anyMatch(resource -> this.hasResourceName(resource, "Sirius Web Tests Data")
                            && this.hasLibraryMetadata(resource, "papaya", "sirius-web-tests-data", "1.0.0"));
                Optional<Object> optAbstractTest = this.objectSearchService.getObject(editingContext, PapayaIdentifiers.PAPAYA_ABSTRACT_TEST_OBJECT.toString());
                assertThat(optAbstractTest)
                    .isPresent()
                    .get()
                    .isInstanceOf(Interface.class);
                Interface abstractTestInterface = (Interface) optAbstractTest.get();
                assertThat(abstractTestInterface.getAnnotations())
                    .hasSize(1)
                    .allMatch(annotation -> !annotation.eIsProxy()
                            && annotation.eResource() != null
                            && this.hasLibraryMetadata(annotation.eResource(), "papaya", "sirius-web-tests-data", "1.0.0"));
                return new SuccessPayload(executeEditingContextFunctionInput.id());
            }
            return new ErrorPayload(executeEditingContextFunctionInput.id(), "Invalid editing context");
        };

        Runnable checkInitialEditingContext = () -> {
            var checkInitialEditingContextInput = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), checkInitialEditingContextFunction);
            var checkInitialEditingContextPayload = this.executeEditingContextFunctionRunner.execute(checkInitialEditingContextInput).block();
            assertThat(checkInitialEditingContextPayload).isInstanceOf(SuccessPayload.class);
        };

        Runnable updateLibrary = () -> {
            var input = new UpdateLibraryInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), optionalLibrary.get().getId());
            var result = this.updateLibraryMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.updateLibrary.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        BiFunction<IEditingContext, IInput, IPayload> checkUpdatedEditingContextFunction = (editingContext, executeEditingContextFunctionInput) -> {
            if (editingContext instanceof IEMFEditingContext emfEditingContext) {
                assertThat(emfEditingContext.getDomain().getResourceSet().getResources())
                    .anyMatch(resource -> this.hasResourceName(resource, "Sirius Web Tests Data")
                            && this.hasLibraryMetadata(resource, "papaya", "sirius-web-tests-data", "2.0.0"))
                    .noneMatch(resource -> this.hasResourceName(resource, "Sirius Web Tests Data")
                            && this.hasLibraryMetadata(resource, "papaya", "sirius-web-tests-data", "1.0.0"));
                Optional<Object> optAbstractTest = this.objectSearchService.getObject(editingContext, PapayaIdentifiers.PAPAYA_ABSTRACT_TEST_OBJECT.toString());
                assertThat(optAbstractTest)
                    .isPresent()
                    .get()
                    .isInstanceOf(Interface.class);
                Interface abstractTestInterface = (Interface) optAbstractTest.get();
                assertThat(abstractTestInterface.getAnnotations())
                    .hasSize(1)
                    .allMatch(annotation -> !annotation.eIsProxy()
                            && annotation.eResource() != null
                            && this.hasLibraryMetadata(annotation.eResource(), "papaya", "sirius-web-tests-data", "2.0.0"));

                return new SuccessPayload(executeEditingContextFunctionInput.id());
            }
            return new ErrorPayload(executeEditingContextFunctionInput.id(), "Invalid editing context");
        };

        Runnable checkUpdatedEditingContext = () -> {
            var checkUpdatedEditingContextInput = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), checkUpdatedEditingContextFunction);
            var checkUpdatedEditingContextPayload = this.executeEditingContextFunctionRunner.execute(checkUpdatedEditingContextInput).block();
            assertThat(checkUpdatedEditingContextPayload).isInstanceOf(SuccessPayload.class);
        };

        StepVerifier.create(flux)
            .then(checkInitialEditingContext)
            .then(updateLibrary)
            .then(checkUpdatedEditingContext)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with dependencies, when a dependency is removed, then the editing context does not contain the dependency's resources")
    public void givenProjectWithDependenciesWhenDependencyIsRemovedThenTheEditingContextDoesNotContainTheDependencysResources() {
        Optional<Library> optionalLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "sirius-web-tests-data", "2.0.0");
        assertThat(optionalLibrary).isPresent();

        var editingContextEventInput = new EditingContextEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString());
        var flux = this.editingContextEventSubscriptionRunner.run(editingContextEventInput);

        AtomicReference<Integer> initialResourceCount = new AtomicReference<>();

        BiFunction<IEditingContext, IInput, IPayload> checkInitialEditingContextFunction = (editingContext, executeEditingContextFunctionInput) -> {
            if (editingContext instanceof IEMFEditingContext emfEditingContext) {
                assertThat(emfEditingContext.getDomain().getResourceSet().getResources())
                    .anyMatch(resource -> this.hasResourceName(resource, "Sirius Web Tests Data")
                            && this.hasLibraryMetadata(resource, "papaya", "sirius-web-tests-data", "1.0.0"));
                initialResourceCount.set(emfEditingContext.getDomain().getResourceSet().getResources().size());
                Optional<Object> optAbstractTest = this.objectSearchService.getObject(editingContext, PapayaIdentifiers.PAPAYA_ABSTRACT_TEST_OBJECT.toString());
                assertThat(optAbstractTest)
                    .isPresent()
                    .get()
                    .isInstanceOf(Interface.class);
                Interface abstractTestInterface = (Interface) optAbstractTest.get();
                assertThat(abstractTestInterface.getAnnotations())
                    .hasSize(1)
                    .allMatch(annotation -> !annotation.eIsProxy()
                            && annotation.eResource() != null
                            && this.hasLibraryMetadata(annotation.eResource(), "papaya", "sirius-web-tests-data", "1.0.0"));
                return new SuccessPayload(executeEditingContextFunctionInput.id());
            }
            return new ErrorPayload(executeEditingContextFunctionInput.id(), "Invalid editing context");
        };

        Runnable checkInitialEditingContext = () -> {
            var checkInitialEditingContextInput = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), checkInitialEditingContextFunction);
            var checkInitialEditingContextPayload = this.executeEditingContextFunctionRunner.execute(checkInitialEditingContextInput).block();
            assertThat(checkInitialEditingContextPayload).isInstanceOf(SuccessPayload.class);
        };

        var representationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, List.of(), List.of());

        Runnable removeLibrary = () -> {
            var input = new InvokeSingleClickTreeItemContextMenuEntryInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    representationId,
                    PapayaIdentifiers.PAPAYA_SIRIUS_WEB_TESTS_DATA_DOCUMENT.toString(),
                    "removeLibrary");
            this.singleClickTreeItemContextMenuEntryMutationRunner.run(input);
        };

        BiFunction<IEditingContext, IInput, IPayload> checkUpdatedEditingContextFunction = (editingContext, executeEditingContextFunctionInput) -> {
            if (editingContext instanceof IEMFEditingContext emfEditingContext) {
                assertThat(emfEditingContext.getDomain().getResourceSet().getResources())
                    .noneMatch(resource -> this.hasResourceName(resource, "Sirius Web Tests Data")
                            && this.hasLibraryMetadata(resource, "papaya", "sirius-web-tests-data", "1.0.0"));
                assertThat(emfEditingContext.getDomain().getResourceSet().getResources().size()).isEqualTo(initialResourceCount.get() - 1);
                Optional<Object> optAbstractTest = this.objectSearchService.getObject(editingContext, PapayaIdentifiers.PAPAYA_ABSTRACT_TEST_OBJECT.toString());
                assertThat(optAbstractTest)
                    .isPresent()
                    .get()
                    .isInstanceOf(Interface.class);
                Interface abstractTestInterface = (Interface) optAbstractTest.get();
                // The annotation should be removed during the removal, since the target object doesn't exist anymore in the resource set.
                assertThat(abstractTestInterface.getAnnotations())
                    .hasSize(0);

                return new SuccessPayload(executeEditingContextFunctionInput.id());
            }
            return new ErrorPayload(executeEditingContextFunctionInput.id(), "Invalid editing context");
        };

        Runnable checkUpdatedEditingContext = () -> {
            var checkUpdatedEditingContextInput = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), checkUpdatedEditingContextFunction);
            var checkUpdatedEditingContextPayload = this.executeEditingContextFunctionRunner.execute(checkUpdatedEditingContextInput).block();
            assertThat(checkUpdatedEditingContextPayload).isInstanceOf(SuccessPayload.class);
        };

        StepVerifier.create(flux)
            .then(checkInitialEditingContext)
            .then(removeLibrary)
            .then(checkUpdatedEditingContext)
            .thenCancel()
            .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with dependencies, when a dependency is updated, then the editing context does not contain proxies")
    public void givenProjectWithDependenciesWhenDependencyIsUpdatedThenEditingContextDoesNotContainProxies() {
        Optional<Library> optionalLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "sirius-web-tests-data", "3.0.0");
        assertThat(optionalLibrary).isPresent();

        var editingContextEventInput = new EditingContextEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString());
        var flux = this.editingContextEventSubscriptionRunner.run(editingContextEventInput);

        BiFunction<IEditingContext, IInput, IPayload> checkInitialEditingContextFunction = (editingContext, executeEditingContextFunctionInput) -> {
            if (editingContext instanceof IEMFEditingContext emfEditingContext) {
                var unresolvedProxies = UnresolvedProxyCrossReferencer.find(emfEditingContext.getDomain().getResourceSet());
                assertThat(unresolvedProxies).isEmpty();
                Optional<Object> optAbstractTest = this.objectSearchService.getObject(editingContext, PapayaIdentifiers.PAPAYA_ABSTRACT_TEST_OBJECT.toString());
                assertThat(optAbstractTest)
                    .isPresent()
                    .get()
                    .isInstanceOf(Interface.class);
                Interface abstractTestInterface = (Interface) optAbstractTest.get();
                assertThat(abstractTestInterface.getAnnotations())
                    .hasSize(1)
                    .allMatch(annotation -> !annotation.eIsProxy()
                            && annotation.eResource() != null
                            && this.hasLibraryMetadata(annotation.eResource(), "papaya", "sirius-web-tests-data", "1.0.0"));
                return new SuccessPayload(executeEditingContextFunctionInput.id());
            }
            return new ErrorPayload(executeEditingContextFunctionInput.id(), "Invalid editing context");
        };

        Runnable checkInitialEditingContext = () -> {
            var input = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), checkInitialEditingContextFunction);
            IPayload result = this.executeEditingContextFunctionRunner.execute(input).block();
            assertThat(result).isInstanceOf(SuccessPayload.class);
        };

        Runnable updateLibrary = () -> {
            var updateLibraryInput = new UpdateLibraryInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), optionalLibrary.get().getId());
            var result = this.updateLibraryMutationRunner.run(updateLibraryInput);
            String typename = JsonPath.read(result, "$.data.updateLibrary.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        BiFunction<IEditingContext, IInput, IPayload> checkUpdatedEditingContextFunction = (editingContext, executeEditingContextFunctionInput) -> {
            if (editingContext instanceof IEMFEditingContext emfEditingContext) {
                var unresolvedProxies = UnresolvedProxyCrossReferencer.find(emfEditingContext.getDomain().getResourceSet());
                assertThat(unresolvedProxies).isEmpty();
                Optional<Object> optAbstractTest = this.objectSearchService.getObject(editingContext, PapayaIdentifiers.PAPAYA_ABSTRACT_TEST_OBJECT.toString());
                assertThat(optAbstractTest)
                    .isPresent()
                    .get()
                    .isInstanceOf(Interface.class);
                Interface abstractTestInterface = (Interface) optAbstractTest.get();
                // The annotation should be removed during the update, since the target object doesn't exist anymore in the resource set.
                assertThat(abstractTestInterface.getAnnotations())
                    .isEmpty();
                return new SuccessPayload(executeEditingContextFunctionInput.id());
            }
            return new ErrorPayload(executeEditingContextFunctionInput.id(), "Invalid editing context");
        };

        Runnable checkUpdatedEditingContext = () -> {
            var input = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), checkUpdatedEditingContextFunction);
            IPayload result = this.executeEditingContextFunctionRunner.execute(input).block();
            assertThat(result).isInstanceOf(SuccessPayload.class);
        };

        StepVerifier.create(flux)
            .then(checkInitialEditingContext)
            .then(updateLibrary)
            .then(checkUpdatedEditingContext)
            .thenCancel()
            .verify(Duration.ofSeconds(10));

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

    private void assertThatLibraryDocumentsAreReadOnly(Library library) {
        var optionalLibrarySemanticData = this.semanticDataSearchService.findById(library.getSemanticData().getId());
        assertThat(optionalLibrarySemanticData.isPresent());
        var librarySemanticData = optionalLibrarySemanticData.get();
        assertThat(librarySemanticData.getDocuments()).allMatch(document -> document.isReadOnly());
    }

    private boolean hasResourceName(Resource resource, String name) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .anyMatch(resourceMetadata -> resourceMetadata.getName().equals(name));
    }

    private boolean hasLibraryMetadata(Resource resource, String namespace, String name, String version) {
        return resource.eAdapters().stream()
                .filter(LibraryMetadataAdapter.class::isInstance)
                .map(LibraryMetadataAdapter.class::cast)
                .anyMatch(libraryMetadata -> libraryMetadata.getNamespace().equals(namespace) && libraryMetadata.getName().equals(name) && libraryMetadata.getVersion().equals(version));
    }

}
