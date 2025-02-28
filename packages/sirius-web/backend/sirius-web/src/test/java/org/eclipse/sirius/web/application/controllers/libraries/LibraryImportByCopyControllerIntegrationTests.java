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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

import org.eclipse.emf.ecore.resource.Resource;
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
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ImportLibrariesMutationRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the library controllers when importing libraries as a copy.
 *
 * @author gdaniel
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryImportByCopyControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private ImportLibrariesMutationRunner importLibrariesMutationRunner;

    @Autowired
    private ILibrarySearchService librarySearchService;

    @Autowired
    private IExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when a library with dependencies is imported as a copy, then the editing context contains the copy of the libraries")
    public void givenProjectWhenLibraryWithDependenciesIsImportedAsCopyThenEditingContextContainsTheCopyOfLibraries() {
        Optional<Library> library = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "reactivestreams", "1.0.0");
        assertThat(library).isPresent();
        var importLibrariesInput = new ImportLibrariesInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), "copy", List.of(library.get().getId().toString()));
        this.importLibrariesMutationRunner.run(importLibrariesInput);

        BiFunction<IEditingContext, IInput, IPayload> function = (editingContext, executeEditingContextFunctionInput) -> {
            if (editingContext instanceof IEMFEditingContext emfEditingContext) {
                assertThat(emfEditingContext.getDomain().getResourceSet().getResources())
                    .anyMatch(resource -> this.hasResourceName(resource, "Reactive Streams Library")
                            && resource.eAdapters().stream().noneMatch(LibraryMetadataAdapter.class::isInstance))
                    .anyMatch(resource -> this.hasResourceName(resource, "Java Standard Library")
                            && resource.eAdapters().stream().noneMatch(LibraryMetadataAdapter.class::isInstance));
                return new SuccessPayload(executeEditingContextFunctionInput.id());
            }
            return new ErrorPayload(executeEditingContextFunctionInput.id(), "Invalid editing context");
        };

        var input = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), function);
        var payload = this.executeEditingContextFunctionRunner.execute(input).block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);
    }

    private boolean hasResourceName(Resource resource, String name) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .anyMatch(resourceMetadata -> resourceMetadata.getName().equals(name));
    }

}
