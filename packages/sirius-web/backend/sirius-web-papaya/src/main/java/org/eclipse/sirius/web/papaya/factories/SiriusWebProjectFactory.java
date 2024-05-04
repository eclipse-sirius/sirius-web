/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.factories;

import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.web.papaya.factories.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.api.IObjectFactory;
import org.eclipse.sirius.web.papaya.factories.siriusweb.SiriusWebComponentInitializer;

/**
 * Used to create the Sirius Web project.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class SiriusWebProjectFactory implements IObjectFactory {
    @Override
    public void create(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        this.createCharts(eObjectIndexer, editingContext);
        this.createCompatibility(eObjectIndexer, editingContext);
        this.createCore(eObjectIndexer, editingContext);
        this.createDeck(eObjectIndexer, editingContext);
        this.createDiagrams(eObjectIndexer, editingContext);
        this.createDomain(eObjectIndexer, editingContext);
        this.createEMF(eObjectIndexer, editingContext);
        this.createFormDescriptionEditors(eObjectIndexer, editingContext);
        this.createForms(eObjectIndexer, editingContext);
        this.createGantt(eObjectIndexer, editingContext);
        this.createPapaya(eObjectIndexer, editingContext);
        this.createPortals(eObjectIndexer, editingContext);
        this.createSelection(eObjectIndexer, editingContext);
        this.createStarters(eObjectIndexer, editingContext);
        this.createTask(eObjectIndexer, editingContext);
        this.createTrees(eObjectIndexer, editingContext);
        this.createValidation(eObjectIndexer, editingContext);
        this.createView(eObjectIndexer, editingContext);
        this.createWeb(eObjectIndexer, editingContext);

        this.createSiriusWeb(eObjectIndexer, editingContext);
    }

    private Resource createResource(IEMFEditingContext editingContext, String name) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter(name);
        resource.eAdapters().add(resourceMetadataAdapter);
        editingContext.getDomain().getResourceSet().getResources().add(resource);
        return resource;
    }

    private void createCharts(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var siriusComponentsCharts = new SiriusWebComponentInitializer().initialize("sirius-components-charts", "org.eclipse.sirius.components.charts", packageName -> packageName.startsWith("org.eclipse.sirius.components.charts"));
        var siriusComponentsCollaborativeCharts = new SiriusWebComponentInitializer().initialize("sirius-components-collaborative-charts", "org.eclipse.sirius.components.collaborative.charts", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.charts"));

        var siriusComponentsChartsBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsChartsBackend.setName("backend");
        siriusComponentsChartsBackend.getComponents().addAll(List.of(siriusComponentsCharts, siriusComponentsCollaborativeCharts));

        var resource = this.createResource(editingContext, "Sirius Components - Charts");
        resource.getContents().add(siriusComponentsChartsBackend);

        eObjectIndexer.index(siriusComponentsChartsBackend);
    }

    private void createCompatibility(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var siriusComponentsCompatibilityPackages = List.of(
                "org.eclipse.sirius.components.compatibility",
                "org.eclipse.sirius.components.compatibility.api",
                "org.eclipse.sirius.components.compatibility.configuration",
                "org.eclipse.sirius.components.compatibility.diagrams",
                "org.eclipse.sirius.components.compatibility.forms",
                "org.eclipse.sirius.components.compatibility.messages",
                "org.eclipse.sirius.components.compatibility.services"
        );
        var siriusComponentsCompatibility = new SiriusWebComponentInitializer().initialize("sirius-components-compatibility", "org.eclipse.sirius.components.compatibility", siriusComponentsCompatibilityPackages::contains);
        var siriusComponentsCompatibilityEmf = new SiriusWebComponentInitializer().initialize("sirius-components-compatibility-emf", "org.eclipse.sirius.components.compatibility.emf", packageName -> packageName.startsWith("org.eclipse.sirius.components.compatibility.emf"));

        var siriusComponentsCompatibilityBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsCompatibilityBackend.setName("backend");
        siriusComponentsCompatibilityBackend.getComponents().addAll(List.of(siriusComponentsCompatibility, siriusComponentsCompatibilityEmf));

        var resource = this.createResource(editingContext, "Sirius Components - Compatibility");
        resource.getContents().add(siriusComponentsCompatibilityBackend);

        eObjectIndexer.index(siriusComponentsCompatibilityBackend);
    }

    private void createCore(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var siriusComponentsAnnotations = new SiriusWebComponentInitializer().initialize("sirius-components-annotations", "org.eclipse.sirius.components.annotations", packageName -> packageName.equals("org.eclipse.sirius.components.annotations"));
        var siriusComponentsAnnotationsSpring = new SiriusWebComponentInitializer().initialize("sirius-components-annotations-spring", "org.eclipse.sirius.components.annotations.spring", packageName -> packageName.startsWith("org.eclipse.sirius.components.annotations.spring"));

        var siriusComponentsCollaborativePackages = List.of(
                "org.eclipse.sirius.components.collaborative",
                "org.eclipse.sirius.components.collaborative.api",
                "org.eclipse.sirius.components.collaborative.configuration",
                "org.eclipse.sirius.components.collaborative.dto",
                "org.eclipse.sirius.components.collaborative.editingcontext",
                "org.eclipse.sirius.components.collaborative.editingcontext.api",
                "org.eclipse.sirius.components.collaborative.handlers",
                "org.eclipse.sirius.components.collaborative.messages",
                "org.eclipse.sirius.components.collaborative.representations",
                "org.eclipse.sirius.components.collaborative.representations.migration"

        );
        var siriusComponentsCollaborative = new SiriusWebComponentInitializer().initialize("sirius-components-collaborative", "org.eclipse.sirius.components.collaborative", siriusComponentsCollaborativePackages::contains);

        var siriusComponentsCore = new SiriusWebComponentInitializer().initialize("sirius-components-core", "org.eclipse.sirius.components.core", packageName -> packageName.startsWith("org.eclipse.sirius.components.core"));
        var siriusComponentsGraphQLApi = new SiriusWebComponentInitializer().initialize("sirius-components-graphql-api", "org.eclipse.sirius.components.graphql.api", packageName -> packageName.startsWith("org.eclipse.sirius.components.graphql.api"));
        var siriusComponentsRepresentations = new SiriusWebComponentInitializer().initialize("sirius-components-representations", "org.eclipse.sirius.components.representations", packageName -> packageName.startsWith("org.eclipse.sirius.components.representations"));

        var siriusComponentsCoreBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsCoreBackend.setName("backend");
        siriusComponentsCoreBackend.getComponents().addAll(List.of(
                siriusComponentsAnnotations,
                siriusComponentsAnnotationsSpring,
                siriusComponentsCollaborative,
                siriusComponentsCore,
                siriusComponentsGraphQLApi,
                siriusComponentsRepresentations
        ));

        var resource = this.createResource(editingContext, "Sirius Components - Core");
        resource.getContents().add(siriusComponentsCoreBackend);

        eObjectIndexer.index(siriusComponentsCoreBackend);
    }

    private void createDeck(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var siriusComponentsDeckPackages = List.of(
                "org.eclipse.sirius.components.deck",
                "org.eclipse.sirius.components.deck.description",
                "org.eclipse.sirius.components.deck.renderer",
                "org.eclipse.sirius.components.deck.renderer.component",
                "org.eclipse.sirius.components.deck.renderer.elements",
                "org.eclipse.sirius.components.deck.renderer.events"
        );
        var siriusComponentsDeck = new SiriusWebComponentInitializer().initialize("sirius-components-deck", "org.eclipse.sirius.components.deck", siriusComponentsDeckPackages::contains);
        var siriusComponentsCollaborativeDeck = new SiriusWebComponentInitializer().initialize("sirius-components-collaborative-deck", "org.eclipse.sirius.components.collaborative.deck", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.deck"));
        var siriusComponentsDeckGraphQL = new SiriusWebComponentInitializer().initialize("sirius-components-deck-graphql", "org.eclipse.sirius.components.deck.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.deck.graphql"));

        var siriusComponentsDeckBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsDeckBackend.setName("backend");
        siriusComponentsDeckBackend.getComponents().addAll(List.of(siriusComponentsDeck, siriusComponentsCollaborativeDeck, siriusComponentsDeckGraphQL));

        var resource = this.createResource(editingContext, "Sirius Components - Deck");
        resource.getContents().add(siriusComponentsDeckBackend);

        eObjectIndexer.index(siriusComponentsDeckBackend);

    }

    private void createDiagrams(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var siriusComponentsDiagramsPackages = List.of(
                "org.eclipse.sirius.components.diagrams",
                "org.eclipse.sirius.components.diagrams.components",
                "org.eclipse.sirius.components.diagrams.description",
                "org.eclipse.sirius.components.diagrams.elements",
                "org.eclipse.sirius.components.diagrams.events",
                "org.eclipse.sirius.components.diagrams.layoutdata",
                "org.eclipse.sirius.components.diagrams.renderer",
                "org.eclipse.sirius.components.diagrams.tools"
        );
        var siriusComponentsDiagrams = new SiriusWebComponentInitializer().initialize("sirius-components-diagrams", "org.eclipse.sirius.components.diagrams", siriusComponentsDiagramsPackages::contains);
        var siriusComponentsCollaborativeDiagrams = new SiriusWebComponentInitializer().initialize("sirius-components-collaborative-diagrams", "org.eclipse.sirius.components.collaborative.diagrams", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.diagrams"));
        var siriusComponentsDiagramsGraphQL = new SiriusWebComponentInitializer().initialize("sirius-components-diagrams-graphql", "org.eclipse.sirius.components.diagrams.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.diagrams.graphql"));

        var siriusComponentsDiagramsBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsDiagramsBackend.setName("backend");
        siriusComponentsDiagramsBackend.getComponents().addAll(List.of(siriusComponentsDiagrams, siriusComponentsCollaborativeDiagrams, siriusComponentsDiagramsGraphQL));

        var resource = this.createResource(editingContext, "Sirius Components - Diagrams");
        resource.getContents().add(siriusComponentsDiagramsBackend);

        eObjectIndexer.index(siriusComponentsDiagramsBackend);
    }

    private void createDomain(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - Domain");
    }

    private void createEMF(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - EMF");
    }

    private void createFormDescriptionEditors(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - Form Description Editors");
    }

    private void createForms(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - Forms");
    }

    private void createGantt(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - Gantt");
    }

    private void createPapaya(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - Papaya");
    }

    private void createPortals(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - Portals");
    }

    private void createSelection(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - Selection");
    }

    private void createSiriusWeb(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Web");
    }

    private void createStarters(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - Starters");
    }

    private void createTask(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - Task");
    }

    private void createTrees(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - Trees");
    }

    private void createValidation(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - Validation");
    }

    private void createView(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - View");
    }

    private void createWeb(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {
        var resource = this.createResource(editingContext, "Sirius Components - Web");
    }



    @Override
    public void link(IEObjectIndexer eObjectIndexer, IEMFEditingContext editingContext) {

    }
}
