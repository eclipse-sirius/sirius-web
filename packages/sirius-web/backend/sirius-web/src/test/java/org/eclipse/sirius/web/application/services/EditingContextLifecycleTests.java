/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.papaya.Class;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to test the loading and saving of an editing context.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EditingContextLifecycleTests extends AbstractIntegrationTests {
    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IEditingContextPersistenceService editingContextPersistenceService;

    @Autowired
    private IObjectSearchService objectSearchService;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given semantic data using static metamodels, when the loading is performed, then the semantic data are available in the editing context")
    public void givenSemanticDataUsingStaticMetamodelsWhenLoadingIsPerformedThenSemanticDataAvailableInEditingContext() {
        assertThat(this.editingContextSearchService.existsById(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString())).isTrue();

        var optionalEditingContext = this.editingContextSearchService.findById(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString());
        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        assertThat(editingContext.getId()).isEqualTo(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString());
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var resourceSet = siriusWebEditingContext.getDomain().getResourceSet();
            assertThat(resourceSet.getResources()).hasSize(1);

            var resource = resourceSet.getResources().get(0);
            assertThat(resource.getURI().toString()).isEqualTo(IEMFEditingContext.RESOURCE_SCHEME + ":///" + TestIdentifiers.ECORE_SAMPLE_DOCUMENT);

            var rootEObject = resource.getContents().get(0);
            assertThat(rootEObject).isInstanceOf(EPackage.class);
        } else {
            fail("Invalid editing context");
        }
    }


    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context properly loaded, when it is modified and persisted, then the changes can be reloaded")
    public void givenEditingContextProperlyLoadedWhenItIsModifiedAndPersistedThenTheChangesCanBeReloaded() {
        var optionalEditingContext = this.editingContextSearchService.findById(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString());
        assertThat(optionalEditingContext).isPresent();

        TestTransaction.flagForCommit();
        TestTransaction.end();

        var editingContext = optionalEditingContext.get();
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var resourceSet = siriusWebEditingContext.getDomain().getResourceSet();
            var rootEObject = resourceSet.getResources().get(0).getContents().get(0);
            assertThat(rootEObject).isInstanceOf(EPackage.class);

            EPackage ePackage = (EPackage) rootEObject;
            assertThat(ePackage.getName()).isEqualTo("Sample");

            ePackage.setName("Sample Updated");

            TestTransaction.start();
            this.editingContextPersistenceService.persist(new ICause.NoOp(), editingContext);
            TestTransaction.flagForCommit();
            TestTransaction.end();
        }

        TestTransaction.start();
        optionalEditingContext = this.editingContextSearchService.findById(TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString());
        assertThat(optionalEditingContext).isPresent();

        TestTransaction.flagForCommit();
        TestTransaction.end();

        editingContext = optionalEditingContext.get();
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var resourceSet = siriusWebEditingContext.getDomain().getResourceSet();
            var rootEObject = resourceSet.getResources().get(0).getContents().get(0);
            assertThat(rootEObject).isInstanceOf(EPackage.class);

            EPackage ePackage = (EPackage) rootEObject;
            assertThat(ePackage.getName()).isEqualTo("Sample Updated");
        }
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an editing context properly loaded, when a read-only resource is modified and persisted, then the changes are not reloaded")
    public void givenEditingContextWithReadOnlyResourcesWhenAReadOnlyResourceIsModifiedAndPersistedThenTheChangesAreNotReloaded() {
        var optionalEditingContext = this.editingContextSearchService.findById(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString());
        assertThat(optionalEditingContext).isPresent();

        TestTransaction.flagForCommit();
        TestTransaction.end();

        var editingContext = optionalEditingContext.get();
        var optionalGraphQL = this.objectSearchService.getObject(editingContext, PapayaIdentifiers.PAPAYA_GRAPHQL_CLASS_OBJECT.toString());
        assertThat(optionalGraphQL).isPresent()
            .get()
            .isInstanceOf(Class.class);
        Class graphQL = (Class) optionalGraphQL.get();
        String initialGraphQLName = graphQL.getName();
        graphQL.setName("New name");

        TestTransaction.start();
        this.editingContextPersistenceService.persist(new ICause.NoOp(), editingContext);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        TestTransaction.start();
        optionalEditingContext = this.editingContextSearchService.findById(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString());
        assertThat(optionalEditingContext).isPresent();

        TestTransaction.flagForCommit();
        TestTransaction.end();

        editingContext = optionalEditingContext.get();

        optionalGraphQL = this.objectSearchService.getObject(editingContext, PapayaIdentifiers.PAPAYA_GRAPHQL_CLASS_OBJECT.toString());
        assertThat(optionalGraphQL).isPresent()
            .get()
            .isInstanceOf(Class.class);
        graphQL = (Class) optionalGraphQL.get();
        assertThat(graphQL.getName()).isEqualTo(initialGraphQLName);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given semantic data with a dependency, when the loading is performed, then the semantic data are available in the editing context")
    public void givenSemanticDataWithDependencyWhenLoadingIsPerformedThenSemanticDataAvailableInEditingContext() {
        var optionalEditingContext = this.editingContextSearchService.findById(PapayaIdentifiers.REACTIVE_STREAMS_LIBRARY_EDITING_CONTEXT_ID.toString());
        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        assertThat(editingContext.getId()).isEqualTo(PapayaIdentifiers.REACTIVE_STREAMS_LIBRARY_EDITING_CONTEXT_ID.toString());
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var resourceSet = siriusWebEditingContext.getDomain().getResourceSet();
            assertThat(resourceSet.getResources())
                .hasSize(2)
                .anyMatch(resource -> resource.eAdapters().stream().anyMatch(LibraryMetadataAdapter.class::isInstance));
        } else {
            fail("Invalid editing context");
        }
    }
}
