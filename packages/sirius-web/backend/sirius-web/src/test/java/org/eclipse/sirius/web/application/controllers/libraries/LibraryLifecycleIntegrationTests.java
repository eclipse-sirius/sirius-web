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
import static org.assertj.core.api.Assertions.fail;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.object.services.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to get libraries from the GraphQL API.
 *
 * @author gdaniel
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryLifecycleIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private ILibrarySearchService librarySearchService;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IReadOnlyObjectPredicate readOnlyObjectPredicate;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a library, when its editing context is loaded, then all its data are loaded properly")
    public void givenLibraryWhenItsEditingContextIsLoadedThenAllItsDataAreLoadedProperly() {
        var optionalLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "java", "1.0.0");
        assertThat(optionalLibrary).isPresent();

        var library = optionalLibrary.get();
        assertThat(library.getSemanticData().getId()).isNotNull();
        var optionalEditingContext = this.editingContextSearchService.findById(library.getSemanticData().getId().toString());
        assertThat(optionalEditingContext).isPresent();
        assertThat(optionalEditingContext.get()).isInstanceOf(EditingContext.class);

        var resources = ((EditingContext) optionalEditingContext.get()).getDomain().getResourceSet().getResources();
        assertThat(resources).hasSize(1);
        assertThat(resources.get(0).getContents())
                .hasSize(1)
                .anyMatch(Package.class::isInstance);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a library with a dependency, when the loading is performed, then all the content is read only")
    public void givenLibraryWithDependencyWhenTheLoadingIsPerformedThenAllTheContentIsReadOnly() {
        var optionalEditingContext = this.editingContextSearchService.findById(PapayaIdentifiers.REACTIVE_STREAMS_LIBRARY_EDITING_CONTEXT_ID.toString());
        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        assertThat(editingContext.getId()).isEqualTo(PapayaIdentifiers.REACTIVE_STREAMS_LIBRARY_EDITING_CONTEXT_ID.toString());
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var resourceSet = siriusWebEditingContext.getDomain().getResourceSet();

            var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(resourceSet.getAllContents(), Spliterator.ORDERED), false);
            assertThat(stream).isNotEmpty().allMatch(this.readOnlyObjectPredicate);
        } else {
            fail("Invalid editing context");
        }
    }

}