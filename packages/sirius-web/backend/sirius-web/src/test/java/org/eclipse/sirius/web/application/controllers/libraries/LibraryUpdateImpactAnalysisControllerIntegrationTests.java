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
import java.util.function.BiFunction;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.dto.EditingContextEventInput;
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
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.EditingContextUpdateLibraryImpactAnalysisReportQueryRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the library controllers when computing the impact analysis of a library update.
 *
 * @author gdaniel
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryUpdateImpactAnalysisControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private ILibrarySearchService librarySearchService;

    @Autowired
    private EditingContextUpdateLibraryImpactAnalysisReportQueryRunner editingContextUpdateLibraryImpactAnalysisReportQueryRunner;

    @Autowired
    private EditingContextEventSubscriptionRunner editingContextEventSubscriptionRunner;

    @Autowired
    private IObjectSearchService objectSearchService;

    @Autowired
    private ExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;


    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with dependencies, when an impact analysis report is requested for a the update of a dependency, then the report is returned")
    public void givenProjectWithDependenciesWhenImpactAnalysisReportIsRequestedForTheUpdateOfADependencyThenTheReportIsReturned() {
        Optional<Library> optionalLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "sirius-web-tests-data", "3.0.0");
        assertThat(optionalLibrary).isPresent();

        Map<String, Object> input = Map.of("editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "libraryId", optionalLibrary.get().getId());
        var result = this.editingContextUpdateLibraryImpactAnalysisReportQueryRunner.run(input);
        int nbElementDeleted = JsonPath.read(result, "$.data.viewer.editingContext.updateLibraryImpactAnalysisReport.nbElementDeleted");
        assertThat(nbElementDeleted).isEqualTo(0);
        int nbElementModified = JsonPath.read(result, "$.data.viewer.editingContext.updateLibraryImpactAnalysisReport.nbElementModified");
        assertThat(nbElementModified).isEqualTo(0);
        int nbElementCreated = JsonPath.read(result, "$.data.viewer.editingContext.updateLibraryImpactAnalysisReport.nbElementCreated");
        assertThat(nbElementCreated).isEqualTo(0);
        List<String> additionalReports = JsonPath.read(result, "$.data.viewer.editingContext.updateLibraryImpactAnalysisReport.additionalReports[*]");
        assertThat(additionalReports).hasSize(1);
        assertThat(additionalReports.get(0)).isEqualTo("[BROKEN] Sirius Web Architecture - AbstractTest.annotations (previously set to GivenSiriusWebServer)");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with dependencies, when an impact analysis report is requested, then the project's content is not changed")
    public void givenProjectWithDependenciesWhenImpactAnalysisReportIsRequestedThenTheProjectsContentIsNotChanged() {
        Optional<Library> optionalLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "sirius-web-tests-data", "3.0.0");
        assertThat(optionalLibrary).isPresent();

        var editingContextEventInput = new EditingContextEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString());
        var flux = this.editingContextEventSubscriptionRunner.run(editingContextEventInput);


        BiFunction<IEditingContext, IInput, IPayload> checkEditingContextFunction = (editingContext, executeEditingContextFunctionInput) -> {
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

        Runnable checkEditingContext = () -> {
            var checkEditingContextInput = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), checkEditingContextFunction);
            var payload = this.executeEditingContextFunctionRunner.execute(checkEditingContextInput).block();
            assertThat(payload).isInstanceOf(SuccessPayload.class);
        };

        Runnable getUpdateLibraryImpactAnalysisReport = () -> {
            Map<String, Object> input = Map.of("editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID, "libraryId", optionalLibrary.get().getId());
            this.editingContextUpdateLibraryImpactAnalysisReportQueryRunner.run(input);
        };

        StepVerifier.create(flux)
            .then(checkEditingContext)
            .then(getUpdateLibraryImpactAnalysisReport)
            .then(checkEditingContext)
            .thenCancel()
            .verify(Duration.ofSeconds(5));
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
