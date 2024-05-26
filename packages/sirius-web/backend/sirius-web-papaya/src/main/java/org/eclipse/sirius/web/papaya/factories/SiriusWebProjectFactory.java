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
import org.eclipse.sirius.web.papaya.factories.services.ComponentInitializer;
import org.eclipse.sirius.web.papaya.factories.services.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.services.api.IObjectFactory;

/**
 * Used to create the Sirius Web project.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class SiriusWebProjectFactory implements IObjectFactory {

    public void create(IEMFEditingContext editingContext) {
        this.createCharts(editingContext);
        this.createCompatibility(editingContext);
        this.createCore(editingContext);
        this.createDeck(editingContext);
        this.createDiagrams(editingContext);
        this.createDomain(editingContext);
        this.createEMF(editingContext);
        this.createFormDescriptionEditors(editingContext);
        this.createForms(editingContext);
        this.createGantt(editingContext);
        this.createPapaya(editingContext);
        this.createPortals(editingContext);
        this.createSelection(editingContext);
        this.createTrees(editingContext);
        this.createValidation(editingContext);
        this.createView(editingContext);
        this.createWeb(editingContext);

        this.createSiriusWeb(editingContext);
    }

    private Resource createResource(IEMFEditingContext editingContext, String name) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter(name);
        resource.eAdapters().add(resourceMetadataAdapter);
        editingContext.getDomain().getResourceSet().getResources().add(resource);
        return resource;
    }

    private void createCharts(IEMFEditingContext editingContext) {
        var siriusComponentsCharts = new ComponentInitializer().initialize("sirius-components-charts", "org.eclipse.sirius.components.charts", packageName -> packageName.startsWith("org.eclipse.sirius.components.charts"));
        var siriusComponentsCollaborativeCharts = new ComponentInitializer().initialize("sirius-components-collaborative-charts", "org.eclipse.sirius.components.collaborative.charts", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.charts"));

        var siriusComponentsChartsBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsChartsBackend.setName("backend");
        siriusComponentsChartsBackend.getComponents().addAll(List.of(siriusComponentsCharts, siriusComponentsCollaborativeCharts));

        var resource = this.createResource(editingContext, "Sirius Components - Charts");
        resource.getContents().add(siriusComponentsChartsBackend);
    }

    private void createCompatibility(IEMFEditingContext editingContext) {
        var siriusComponentsCompatibilityPackages = List.of(
                "org.eclipse.sirius.components.compatibility",
                "org.eclipse.sirius.components.compatibility.api",
                "org.eclipse.sirius.components.compatibility.configuration",
                "org.eclipse.sirius.components.compatibility.diagrams",
                "org.eclipse.sirius.components.compatibility.forms",
                "org.eclipse.sirius.components.compatibility.messages",
                "org.eclipse.sirius.components.compatibility.services"
        );
        var siriusComponentsCompatibility = new ComponentInitializer().initialize("sirius-components-compatibility", "org.eclipse.sirius.components.compatibility", siriusComponentsCompatibilityPackages::contains);
        var siriusComponentsCompatibilityEmf = new ComponentInitializer().initialize("sirius-components-compatibility-emf", "org.eclipse.sirius.components.compatibility.emf", packageName -> packageName.startsWith("org.eclipse.sirius.components.compatibility.emf"));

        var siriusComponentsCompatibilityBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsCompatibilityBackend.setName("backend");
        siriusComponentsCompatibilityBackend.getComponents().addAll(List.of(siriusComponentsCompatibility, siriusComponentsCompatibilityEmf));

        var resource = this.createResource(editingContext, "Sirius Components - Compatibility");
        resource.getContents().add(siriusComponentsCompatibilityBackend);
    }

    private void createCore(IEMFEditingContext editingContext) {
        var siriusComponentsAnnotations = new ComponentInitializer().initialize("sirius-components-annotations", "org.eclipse.sirius.components.annotations", packageName -> packageName.equals("org.eclipse.sirius.components.annotations"));
        var siriusComponentsAnnotationsSpring = new ComponentInitializer().initialize("sirius-components-annotations-spring", "org.eclipse.sirius.components.annotations.spring", packageName -> packageName.startsWith("org.eclipse.sirius.components.annotations.spring"));

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
        var siriusComponentsCollaborative = new ComponentInitializer().initialize("sirius-components-collaborative", "org.eclipse.sirius.components.collaborative", siriusComponentsCollaborativePackages::contains);

        var siriusComponentsCore = new ComponentInitializer().initialize("sirius-components-core", "org.eclipse.sirius.components.core", packageName -> packageName.startsWith("org.eclipse.sirius.components.core"));
        var siriusComponentsGraphQLApi = new ComponentInitializer().initialize("sirius-components-graphql-api", "org.eclipse.sirius.components.graphql.api", packageName -> packageName.startsWith("org.eclipse.sirius.components.graphql.api"));
        var siriusComponentsRepresentations = new ComponentInitializer().initialize("sirius-components-representations", "org.eclipse.sirius.components.representations", packageName -> packageName.startsWith("org.eclipse.sirius.components.representations"));

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
    }

    private void createDeck(IEMFEditingContext editingContext) {
        var siriusComponentsDeckPackages = List.of(
                "org.eclipse.sirius.components.deck",
                "org.eclipse.sirius.components.deck.description",
                "org.eclipse.sirius.components.deck.renderer",
                "org.eclipse.sirius.components.deck.renderer.component",
                "org.eclipse.sirius.components.deck.renderer.elements",
                "org.eclipse.sirius.components.deck.renderer.events"
        );
        var siriusComponentsDeck = new ComponentInitializer().initialize("sirius-components-deck", "org.eclipse.sirius.components.deck", siriusComponentsDeckPackages::contains);
        var siriusComponentsCollaborativeDeck = new ComponentInitializer().initialize("sirius-components-collaborative-deck", "org.eclipse.sirius.components.collaborative.deck", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.deck"));
        var siriusComponentsDeckGraphQL = new ComponentInitializer().initialize("sirius-components-deck-graphql", "org.eclipse.sirius.components.deck.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.deck.graphql"));

        var siriusComponentsDeckBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsDeckBackend.setName("backend");
        siriusComponentsDeckBackend.getComponents().addAll(List.of(siriusComponentsDeck, siriusComponentsCollaborativeDeck, siriusComponentsDeckGraphQL));

        var resource = this.createResource(editingContext, "Sirius Components - Deck");
        resource.getContents().add(siriusComponentsDeckBackend);

    }

    private void createDiagrams(IEMFEditingContext editingContext) {
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
        var siriusComponentsDiagrams = new ComponentInitializer().initialize("sirius-components-diagrams", "org.eclipse.sirius.components.diagrams", siriusComponentsDiagramsPackages::contains);
        var siriusComponentsCollaborativeDiagrams = new ComponentInitializer().initialize("sirius-components-collaborative-diagrams", "org.eclipse.sirius.components.collaborative.diagrams", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.diagrams"));
        var siriusComponentsDiagramsGraphQL = new ComponentInitializer().initialize("sirius-components-diagrams-graphql", "org.eclipse.sirius.components.diagrams.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.diagrams.graphql"));

        var siriusComponentsDiagramsBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsDiagramsBackend.setName("backend");
        siriusComponentsDiagramsBackend.getComponents().addAll(List.of(siriusComponentsDiagrams, siriusComponentsCollaborativeDiagrams, siriusComponentsDiagramsGraphQL));

        var resource = this.createResource(editingContext, "Sirius Components - Diagrams");
        resource.getContents().add(siriusComponentsDiagramsBackend);
    }

    private void createDomain(IEMFEditingContext editingContext) {
        var siriusComponentsDomainPackages = List.of(
                "org.eclipse.sirius.components.domain",
                "org.eclipse.sirius.components.domain.impl",
                "org.eclipse.sirius.components.domain.util"
        );
        var siriusComponentsDomain = new ComponentInitializer().initialize("sirius-components-domain", "org.eclipse.sirius.components.domain", siriusComponentsDomainPackages::contains);
        var siriusComponentsDomainEdit = new ComponentInitializer().initialize("sirius-components-domain-edit", "org.eclipse.sirius.components.domain.provider", packageName -> packageName.startsWith("org.eclipse.sirius.components.domain.provider"));
        var siriusComponentsDomainEMF = new ComponentInitializer().initialize("sirius-components-domain-emf", "org.eclipse.sirius.components.domain.emf", packageName -> packageName.startsWith("org.eclipse.sirius.components.domain.emf"));

        var siriusComponentsDomainBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsDomainBackend.setName("backend");
        siriusComponentsDomainBackend.getComponents().addAll(List.of(siriusComponentsDomain, siriusComponentsDomainEdit, siriusComponentsDomainEMF));

        var resource = this.createResource(editingContext, "Sirius Components - Domain");
        resource.getContents().add(siriusComponentsDomainBackend);
    }

    private void createEMF(IEMFEditingContext editingContext) {
        var siriusComponentsEMFPackages = List.of(
                "org.eclipse.sirius.components.emf",
                "org.eclipse.sirius.components.emf.configuration",
                "org.eclipse.sirius.components.emf.handlers",
                "org.eclipse.sirius.components.emf.migration",
                "org.eclipse.sirius.components.emf.migration.api",
                "org.eclipse.sirius.components.emf.query",
                "org.eclipse.sirius.components.emf.query.api",
                "org.eclipse.sirius.components.emf.services",
                "org.eclipse.sirius.components.emf.services.api",
                "org.eclipse.sirius.components.emf.services.messages",
                "org.eclipse.sirius.components.emf.utils"
        );
        var siriusComponentsEMF = new ComponentInitializer().initialize("sirius-components-emf", "org.eclipse.sirius.components.emf", siriusComponentsEMFPackages::contains);
        var siriusComponentsEMFForms = new ComponentInitializer().initialize("sirius-components-emf-forms", "org.eclipse.sirius.components.emf.forms", packageName -> packageName.startsWith("org.eclipse.sirius.components.emf.forms"));
        var siriusComponentsInterpreter = new ComponentInitializer().initialize("sirius-components-interpreter", "org.eclipse.sirius.components.interpreter", packageName -> packageName.startsWith("org.eclipse.sirius.components.interpreter"));

        var siriusComponentsEMFBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsEMFBackend.setName("backend");
        siriusComponentsEMFBackend.getComponents().addAll(List.of(siriusComponentsEMF, siriusComponentsEMFForms, siriusComponentsInterpreter));

        var resource = this.createResource(editingContext, "Sirius Components - EMF");
        resource.getContents().add(siriusComponentsEMFBackend);
    }

    private void createFormDescriptionEditors(IEMFEditingContext editingContext) {
        var siriusComponentsFormDescriptionEditorsPackages = List.of(
                "org.eclipse.sirius.components.formdescriptioneditors",
                "org.eclipse.sirius.components.formdescriptioneditors.components",
                "org.eclipse.sirius.components.formdescriptioneditors.configuration",
                "org.eclipse.sirius.components.formdescriptioneditors.description",
                "org.eclipse.sirius.components.formdescriptioneditors.elements",
                "org.eclipse.sirius.components.formdescriptioneditors.renderer"
        );
        var siriusComponentsFormDescriptionEditors = new ComponentInitializer().initialize("sirius-components-formdescriptioneditors", "org.eclipse.sirius.components.formdescriptioneditors", siriusComponentsFormDescriptionEditorsPackages::contains);

        var siriusComponentsCollaborativeFormDescriptionEditorsPackages = List.of(
                "org.eclipse.sirius.components.collaborative.formdescriptioneditors",
                "org.eclipse.sirius.components.collaborative.formdescriptioneditors.api",
                "org.eclipse.sirius.components.collaborative.formdescriptioneditors.configuration",
                "org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto",
                "org.eclipse.sirius.components.collaborative.formdescriptioneditors.handlers",
                "org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages",
                "org.eclipse.sirius.components.collaborative.formdescriptioneditors.services"
        );
        var siriusComponentsCollaborativeFormDescriptionEditors = new ComponentInitializer().initialize("sirius-components-collaborative-formdescriptioneditors", "org.eclipse.sirius.components.collaborative.formdescriptioneditors", siriusComponentsCollaborativeFormDescriptionEditorsPackages::contains);
        var siriusComponentsCollaborativeFormDescriptionEditorsWidgetReference = new ComponentInitializer().initialize("sirius-components-collaborative-formdescriptioneditors-widget-reference", "org.eclipse.sirius.components.emf.forms", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.formdescriptioneditors.widget.reference"));
        var siriusComponentsFormDescriptionEditorsGraphQL = new ComponentInitializer().initialize("sirius-components-formdescriptioneditors-graphql", "org.eclipse.sirius.components.formdescriptioneditors.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.formdescriptioneditors.graphql"));

        var siriusComponentsFormDescriptionEditorsBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsFormDescriptionEditorsBackend.setName("backend");
        siriusComponentsFormDescriptionEditorsBackend.getComponents().addAll(List.of(siriusComponentsFormDescriptionEditors, siriusComponentsCollaborativeFormDescriptionEditors, siriusComponentsCollaborativeFormDescriptionEditorsWidgetReference, siriusComponentsFormDescriptionEditorsGraphQL));

        var resource = this.createResource(editingContext, "Sirius Components - Form Description Editors");
        resource.getContents().add(siriusComponentsFormDescriptionEditorsBackend);
    }

    private void createForms(IEMFEditingContext editingContext) {
        var siriusComponentsFormsPackages = List.of(
                "org.eclipse.sirius.components.forms",
                "org.eclipse.sirius.components.forms.components",
                "org.eclipse.sirius.components.forms.description",
                "org.eclipse.sirius.components.forms.elements",
                "org.eclipse.sirius.components.forms.renderer",
                "org.eclipse.sirius.components.forms.validation"
        );
        var siriusComponentsForms = new ComponentInitializer().initialize("sirius-components-forms", "org.eclipse.sirius.components.forms", siriusComponentsFormsPackages::contains);

        var siriusComponentsCollaborativeFormsPackages = List.of(
                "org.eclipse.sirius.components.collaborative.forms",
                "org.eclipse.sirius.components.collaborative.forms.api",
                "org.eclipse.sirius.components.collaborative.forms.configuration",
                "org.eclipse.sirius.components.collaborative.forms.dto",
                "org.eclipse.sirius.components.collaborative.forms.handlers",
                "org.eclipse.sirius.components.collaborative.forms.messages",
                "org.eclipse.sirius.components.collaborative.forms.services",
                "org.eclipse.sirius.components.collaborative.forms.services.api",
                "org.eclipse.sirius.components.collaborative.forms.variables"

        );
        var siriusComponentsCollaborativeForms = new ComponentInitializer().initialize("sirius-components-collaborative-forms", "org.eclipse.sirius.components.collaborative.forms", siriusComponentsCollaborativeFormsPackages::contains);
        var siriusComponentsFormsGraphQL = new ComponentInitializer().initialize("sirius-components-forms-graphql", "org.eclipse.sirius.components.forms.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.forms.graphql"));

        var siriusComponentsWidgetReferencePackages = List.of(
                "org.eclipse.sirius.components.widget.reference",
                "org.eclipse.sirius.components.widget.reference.dto"
        );
        var siriusComponentsWidgetReference = new ComponentInitializer().initialize("sirius-components-widget-reference", "org.eclipse.sirius.components.widget.reference", siriusComponentsWidgetReferencePackages::contains);
        var siriusComponentsCollaborativeWidgetReference = new ComponentInitializer().initialize("sirius-components-collaborative-widget-reference", "org.eclipse.sirius.components.collaborative.widget.reference", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.widget.reference"));
        var siriusComponentsWidgetReferenceGraphQL = new ComponentInitializer().initialize("sirius-components-widget-reference-graphql", "org.eclipse.sirius.components.widget.reference.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.widget.reference.graphql"));

        var siriusComponentsFormsBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsFormsBackend.setName("backend");
        siriusComponentsFormsBackend.getComponents().addAll(List.of(siriusComponentsForms, siriusComponentsCollaborativeForms, siriusComponentsFormsGraphQL, siriusComponentsWidgetReference, siriusComponentsCollaborativeWidgetReference, siriusComponentsWidgetReferenceGraphQL));

        var resource = this.createResource(editingContext, "Sirius Components - Forms");
        resource.getContents().add(siriusComponentsFormsBackend);
    }

    private void createGantt(IEMFEditingContext editingContext) {
        var siriusComponentsGanttPackages = List.of(
                "org.eclipse.sirius.components.gantt",
                "org.eclipse.sirius.components.gantt.description",
                "org.eclipse.sirius.components.gantt.renderer",
                "org.eclipse.sirius.components.gantt.renderer.component",
                "org.eclipse.sirius.components.gantt.renderer.elements",
                "org.eclipse.sirius.components.gantt.renderer.events"
        );
        var siriusComponentsGantt = new ComponentInitializer().initialize("sirius-components-gantt", "org.eclipse.sirius.components.gantt", siriusComponentsGanttPackages::contains);
        var siriusComponentsCollaborativeGantt = new ComponentInitializer().initialize("sirius-components-collaborative-gantt", "org.eclipse.sirius.components.collaborative.gantt", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.gantt"));
        var siriusComponentsGanttGraphQL = new ComponentInitializer().initialize("sirius-components-gantt-graphql", "org.eclipse.sirius.components.gantt.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.gantt.graphql"));

        var siriusComponentsGanttBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsGanttBackend.setName("backend");
        siriusComponentsGanttBackend.getComponents().addAll(List.of(siriusComponentsGantt, siriusComponentsCollaborativeGantt, siriusComponentsGanttGraphQL));

        var resource = this.createResource(editingContext, "Sirius Components - Gantt");
        resource.getContents().add(siriusComponentsGanttBackend);
    }

    private void createPapaya(IEMFEditingContext editingContext) {
        var siriusComponentsPapayaPackages = List.of(
                "org.eclipse.sirius.components.papaya",
                "org.eclipse.sirius.components.papaya.impl",
                "org.eclipse.sirius.components.papaya.spec",
                "org.eclipse.sirius.components.papaya.spec.derived",
                "org.eclipse.sirius.components.papaya.util"
        );
        var siriusComponentsPapaya = new ComponentInitializer().initialize("sirius-components-papaya", "org.eclipse.sirius.components.papaya", siriusComponentsPapayaPackages::contains);
        var siriusComponentsPapayaEdit = new ComponentInitializer().initialize("sirius-components-papaya-edit", "org.eclipse.sirius.components.papaya.provider", packageName -> packageName.startsWith("org.eclipse.sirius.components.papaya.provider"));

        var siriusComponentsPapayaBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsPapayaBackend.setName("backend");
        siriusComponentsPapayaBackend.getComponents().addAll(List.of(siriusComponentsPapaya, siriusComponentsPapayaEdit));

        var resource = this.createResource(editingContext, "Sirius Components - Papaya");
        resource.getContents().add(siriusComponentsPapayaBackend);
    }

    private void createPortals(IEMFEditingContext editingContext) {
        var siriusComponentsPortalsPackages = List.of(
                "org.eclipse.sirius.components.portals",
                "org.eclipse.sirius.components.portals.description",
                "org.eclipse.sirius.components.portals.renderer"
        );
        var siriusComponentsPortals = new ComponentInitializer().initialize("sirius-components-portals", "org.eclipse.sirius.components.portals", siriusComponentsPortalsPackages::contains);
        var siriusComponentsCollaborativePortals = new ComponentInitializer().initialize("sirius-components-collaborative-portals", "org.eclipse.sirius.components.collaborative.portals", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.portals"));
        var siriusComponentsPortalsGraphQL = new ComponentInitializer().initialize("sirius-components-portals-graphql", "org.eclipse.sirius.components.portals.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.portals.graphql"));

        var siriusComponentsPortalsBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsPortalsBackend.setName("backend");
        siriusComponentsPortalsBackend.getComponents().addAll(List.of(siriusComponentsPortals, siriusComponentsCollaborativePortals, siriusComponentsPortalsGraphQL));

        var resource = this.createResource(editingContext, "Sirius Components - Portals");
        resource.getContents().add(siriusComponentsPortalsBackend);
    }

    private void createSelection(IEMFEditingContext editingContext) {
        var siriusComponentsSelectionPackages = List.of(
                "org.eclipse.sirius.components.selection",
                "org.eclipse.sirius.components.selection.description",
                "org.eclipse.sirius.components.selection.renderer"
        );
        var siriusComponentsSelection = new ComponentInitializer().initialize("sirius-components-selection", "org.eclipse.sirius.components.selection", siriusComponentsSelectionPackages::contains);
        var siriusComponentsCollaborativeSelection = new ComponentInitializer().initialize("sirius-components-collaborative-selection", "org.eclipse.sirius.components.collaborative.selection", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.selection"));
        var siriusComponentsSelectionGraphQL = new ComponentInitializer().initialize("sirius-components-selection-graphql", "org.eclipse.sirius.components.selection.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.selection.graphql"));

        var siriusComponentsSelectionBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsSelectionBackend.setName("backend");
        siriusComponentsSelectionBackend.getComponents().addAll(List.of(siriusComponentsSelection, siriusComponentsCollaborativeSelection, siriusComponentsSelectionGraphQL));

        var resource = this.createResource(editingContext, "Sirius Components - Selection");
        resource.getContents().add(siriusComponentsSelectionBackend);
    }

    private void createSiriusWeb(IEMFEditingContext editingContext) {
        var siriusWebDomain = new ComponentInitializer().initialize("sirius-web-domain", "org.eclipse.sirius.web.domain", packageName -> packageName.startsWith("org.eclipse.sirius.web.domain"));
        var siriusWebApplication = new ComponentInitializer().initialize("sirius-web-application", "org.eclipse.sirius.web.application", packageName -> packageName.startsWith("org.eclipse.sirius.web.application"));
        var siriusWebInfrastructure = new ComponentInitializer().initialize("sirius-web-infrastructure", "org.eclipse.sirius.web.infrastructure", packageName -> packageName.startsWith("org.eclipse.sirius.web.infrastructure"));
        var siriusWebStarter = new ComponentInitializer().initialize("sirius-web-starter", "org.eclipse.sirius.web.starter", packageName -> packageName.startsWith("org.eclipse.sirius.web.starter"));
        var siriusWeb = new ComponentInitializer().initialize("sirius-web", "org.eclipse.sirius.web", packageName -> packageName.equals("org.eclipse.sirius.web"));

        var siriusWebBackend = PapayaFactory.eINSTANCE.createProject();
        siriusWebBackend.setName("backend");
        siriusWebBackend.getComponents().addAll(List.of(siriusWebDomain, siriusWebApplication, siriusWebInfrastructure, siriusWebStarter, siriusWeb));

        var resource = this.createResource(editingContext, "Sirius Web");
        resource.getContents().add(siriusWebBackend);
    }

    private void createTrees(IEMFEditingContext editingContext) {
        var siriusComponentsTreesPackages = List.of(
                "org.eclipse.sirius.components.trees",
                "org.eclipse.sirius.components.trees.description",
                "org.eclipse.sirius.components.trees.renderer"
        );
        var siriusComponentsTrees = new ComponentInitializer().initialize("sirius-components-trees", "org.eclipse.sirius.components.trees", siriusComponentsTreesPackages::contains);
        var siriusComponentsCollaborativeTrees = new ComponentInitializer().initialize("sirius-components-collaborative-trees", "org.eclipse.sirius.components.collaborative.trees", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.trees"));
        var siriusComponentsTreesGraphQL = new ComponentInitializer().initialize("sirius-components-trees-graphql", "org.eclipse.sirius.components.trees.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.trees.graphql"));

        var siriusComponentsTreesBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsTreesBackend.setName("backend");
        siriusComponentsTreesBackend.getComponents().addAll(List.of(siriusComponentsTrees, siriusComponentsCollaborativeTrees, siriusComponentsTreesGraphQL));

        var resource = this.createResource(editingContext, "Sirius Components - Trees");
        resource.getContents().add(siriusComponentsTreesBackend);
    }

    private void createValidation(IEMFEditingContext editingContext) {
        var siriusComponentsValidationPackages = List.of(
                "org.eclipse.sirius.components.validation",
                "org.eclipse.sirius.components.validation.components",
                "org.eclipse.sirius.components.validation.description",
                "org.eclipse.sirius.components.validation.elements",
                "org.eclipse.sirius.components.validation.render"
        );
        var siriusComponentsValidation = new ComponentInitializer().initialize("sirius-components-validation", "org.eclipse.sirius.components.validation", siriusComponentsValidationPackages::contains);
        var siriusComponentsCollaborativeValidation = new ComponentInitializer().initialize("sirius-components-collaborative-validation", "org.eclipse.sirius.components.collaborative.validation", packageName -> packageName.startsWith("org.eclipse.sirius.components.collaborative.validation"));
        var siriusComponentsValidationGraphQL = new ComponentInitializer().initialize("sirius-components-validation-graphql", "org.eclipse.sirius.components.validation.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.validation.graphql"));

        var siriusComponentsValidationBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsValidationBackend.setName("backend");
        siriusComponentsValidationBackend.getComponents().addAll(List.of(siriusComponentsValidation, siriusComponentsCollaborativeValidation, siriusComponentsValidationGraphQL));

        var resource = this.createResource(editingContext, "Sirius Components - Validation");
        resource.getContents().add(siriusComponentsValidationBackend);
    }

    private void createView(IEMFEditingContext editingContext) {
        var siriusComponentsViewPackages = List.of(
                "org.eclipse.sirius.components.view",
                "org.eclipse.sirius.components.view.impl",
                "org.eclipse.sirius.components.view.util"
        );
        var siriusComponentsView = new ComponentInitializer().initialize("sirius-components-view", "org.eclipse.sirius.components.view", siriusComponentsViewPackages::contains);
        var siriusComponentsViewEdit = new ComponentInitializer().initialize("sirius-components-view-edit", "org.eclipse.sirius.components.view.provider", packageName -> packageName.startsWith("org.eclipse.sirius.components.view.provider"));
        var siriusComponentsViewBuilder = new ComponentInitializer().initialize("sirius-components-view-builder", "org.eclipse.sirius.components.view.builder", packageName -> packageName.startsWith("org.eclipse.sirius.components.view.builder"));

        var siriusComponentsViewDeckPackages = List.of(
                "org.eclipse.sirius.components.view.deck",
                "org.eclipse.sirius.components.view.deck.impl",
                "org.eclipse.sirius.components.view.deck.util"
        );
        var siriusComponentsViewDeck = new ComponentInitializer().initialize("sirius-components-view-deck", "org.eclipse.sirius.components.view.deck", siriusComponentsViewDeckPackages::contains);
        var siriusComponentsViewDeckEdit = new ComponentInitializer().initialize("sirius-components-view-deck-edit", "org.eclipse.sirius.components.view.deck.provider", packageName -> packageName.startsWith("org.eclipse.sirius.components.view.deck.provider"));

        var siriusComponentsViewDiagramPackages = List.of(
                "org.eclipse.sirius.components.view.diagram",
                "org.eclipse.sirius.components.view.diagram.adapters",
                "org.eclipse.sirius.components.view.diagram.impl",
                "org.eclipse.sirius.components.view.diagram.util"
        );
        var siriusComponentsViewDiagram = new ComponentInitializer().initialize("sirius-components-view-diagram", "org.eclipse.sirius.components.view.diagram", siriusComponentsViewDiagramPackages::contains);
        var siriusComponentsViewDiagramEdit = new ComponentInitializer().initialize("sirius-components-view-diagram-edit", "org.eclipse.sirius.components.view.diagram.provider", packageName -> packageName.startsWith("org.eclipse.sirius.components.view.diagram.provider"));

        var siriusComponentsViewFormPackages = List.of(
                "org.eclipse.sirius.components.view.form",
                "org.eclipse.sirius.components.view.form.adapters",
                "org.eclipse.sirius.components.view.form.impl",
                "org.eclipse.sirius.components.view.form.util"
        );
        var siriusComponentsViewForm = new ComponentInitializer().initialize("sirius-components-view-form", "org.eclipse.sirius.components.view.form", siriusComponentsViewFormPackages::contains);
        var siriusComponentsViewFormEdit = new ComponentInitializer().initialize("sirius-components-view-form-edit", "org.eclipse.sirius.components.view.form.provider", packageName -> packageName.startsWith("org.eclipse.sirius.components.view.form.provider"));

        var siriusComponentsViewGanttPackages = List.of(
                "org.eclipse.sirius.components.view.gantt",
                "org.eclipse.sirius.components.view.gantt.impl",
                "org.eclipse.sirius.components.view.gantt.util"
        );
        var siriusComponentsViewGantt = new ComponentInitializer().initialize("sirius-components-view-gantt", "org.eclipse.sirius.components.view.gantt", siriusComponentsViewGanttPackages::contains);
        var siriusComponentsViewGanttEdit = new ComponentInitializer().initialize("sirius-components-view-gantt-edit", "org.eclipse.sirius.components.view.gantt.provider", packageName -> packageName.startsWith("org.eclipse.sirius.components.view.gantt.provider"));

        var siriusComponentsViewEMFPackages = List.of(
                "org.eclipse.sirius.components.view.emf",
                "org.eclipse.sirius.components.view.emf",
                "org.eclipse.sirius.components.view.emf.api",
                "org.eclipse.sirius.components.view.emf.compatibility",
                "org.eclipse.sirius.components.view.emf.configuration",
                "org.eclipse.sirius.components.view.emf.deck",
                "org.eclipse.sirius.components.view.emf.diagram",
                "org.eclipse.sirius.components.view.emf.diagram.api",
                "org.eclipse.sirius.components.view.emf.diagram.providers",
                "org.eclipse.sirius.components.view.emf.diagram.providers.api",
                "org.eclipse.sirius.components.view.emf.form",
                "org.eclipse.sirius.components.view.emf.gantt"
        );
        var siriusComponentsViewEMF = new ComponentInitializer().initialize("sirius-components-view-emf", "org.eclipse.sirius.components.view.emf", siriusComponentsViewEMFPackages::contains);

        var siriusComponentsViewBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsViewBackend.setName("backend");
        siriusComponentsViewBackend.getComponents().addAll(List.of(
                siriusComponentsView,
                siriusComponentsViewEdit,
                siriusComponentsViewBuilder,
                siriusComponentsViewDeck,
                siriusComponentsViewDeckEdit,
                siriusComponentsViewDiagram,
                siriusComponentsViewDiagramEdit,
                siriusComponentsViewForm,
                siriusComponentsViewFormEdit,
                siriusComponentsViewGantt,
                siriusComponentsViewGanttEdit,
                siriusComponentsViewEMF
        ));

        var resource = this.createResource(editingContext, "Sirius Components - View");
        resource.getContents().add(siriusComponentsViewBackend);
    }

    private void createWeb(IEMFEditingContext editingContext) {
        var siriusComponentsGraphQL = new ComponentInitializer().initialize("sirius-components-graphql", "org.eclipse.sirius.components.graphql", packageName -> packageName.startsWith("org.eclipse.sirius.components.graphql"));
        var siriusComponentsWeb = new ComponentInitializer().initialize("sirius-components-web", "org.eclipse.sirius.components.web", packageName -> packageName.startsWith("org.eclipse.sirius.components.web"));

        var siriusComponentsWebBackend = PapayaFactory.eINSTANCE.createProject();
        siriusComponentsWebBackend.setName("backend");
        siriusComponentsWebBackend.getComponents().addAll(List.of(siriusComponentsGraphQL, siriusComponentsWeb));

        var resource = this.createResource(editingContext, "Sirius Components - Web");
        resource.getContents().add(siriusComponentsWebBackend);
    }

    @Override
    public void link(IEObjectIndexer eObjectIndexer) {
        this.linkCharts(eObjectIndexer);
        this.linkCompatibility(eObjectIndexer);
        this.linkCore(eObjectIndexer);
        this.linkDeck(eObjectIndexer);
        this.linkDiagrams(eObjectIndexer);
        this.linkDomain(eObjectIndexer);
        this.linkEMF(eObjectIndexer);
        this.linkFormDescriptionEditors(eObjectIndexer);
        this.linkForms(eObjectIndexer);
        this.linkGantt(eObjectIndexer);
        this.linkPapaya(eObjectIndexer);
        this.linkPortals(eObjectIndexer);
        this.linkSelection(eObjectIndexer);
        this.linkSiriusWeb(eObjectIndexer);
        this.linkTrees(eObjectIndexer);
        this.linkValidation(eObjectIndexer);
        this.linkView(eObjectIndexer);
        this.linkWeb(eObjectIndexer);
    }

    private void linkCharts(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsRepresentations = eObjectIndexer.getComponent("sirius-components-representations");
        var siriusComponentsCharts = eObjectIndexer.getComponent("sirius-components-charts");
        siriusComponentsCharts.getDependencies().add(siriusComponentsRepresentations);

        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsCollaborativeCharts = eObjectIndexer.getComponent("sirius-components-collaborative-charts");
        siriusComponentsCollaborativeCharts.getDependencies().addAll(List.of(siriusComponentsCharts, siriusComponentsCollaborative));
    }

    private void linkCompatibility(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsCore = eObjectIndexer.getComponent("sirius-components-core");
        var siriusComponentsForms = eObjectIndexer.getComponent("sirius-components-forms");
        var siriusComponentsSelection = eObjectIndexer.getComponent("sirius-components-selection");
        var siriusComponentsTrees = eObjectIndexer.getComponent("sirius-components-trees");
        var siriusComponentsCollaborativeDiagrams = eObjectIndexer.getComponent("sirius-components-collaborative-diagrams");
        var siriusComponentsCollaborativeForms = eObjectIndexer.getComponent("sirius-components-collaborative-forms");
        var siriusComponentsInterpreter = eObjectIndexer.getComponent("sirius-components-interpreter");

        var siriusComponentsCompatibility = eObjectIndexer.getComponent("sirius-components-compatibility");
        siriusComponentsCompatibility.getDependencies().addAll(List.of(
                siriusComponentsCore,
                siriusComponentsForms,
                siriusComponentsSelection,
                siriusComponentsTrees,
                siriusComponentsCollaborativeDiagrams,
                siriusComponentsCollaborativeForms,
                siriusComponentsInterpreter
        ));

        var siriusComponentsEMF = eObjectIndexer.getComponent("sirius-components-emf");
        var siriusComponentsEMFForms = eObjectIndexer.getComponent("sirius-components-emf-forms");

        var siriusComponentsCompatibilityEMF = eObjectIndexer.getComponent("sirius-components-compatibility-emf");
        siriusComponentsCompatibilityEMF.getDependencies().addAll(List.of(
                siriusComponentsCollaborativeDiagrams,
                siriusComponentsCollaborativeForms,
                siriusComponentsEMF,
                siriusComponentsEMFForms,
                siriusComponentsCompatibility
        ));
    }

    private void linkCore(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsAnnotations = eObjectIndexer.getComponent("sirius-components-annotations");
        var siriusComponentsAnnotationsSpring = eObjectIndexer.getComponent("sirius-components-annotations-spring");
        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsCore = eObjectIndexer.getComponent("sirius-components-core");
        var siriusComponentsGraphQLApi = eObjectIndexer.getComponent("sirius-components-graphql-api");
        var siriusComponentsRepresentations = eObjectIndexer.getComponent("sirius-components-representations");

        siriusComponentsRepresentations.getDependencies().add(siriusComponentsAnnotations);

        siriusComponentsCore.getDependencies().addAll(List.of(
                siriusComponentsAnnotations,
                siriusComponentsRepresentations
        ));

        siriusComponentsCollaborative.getDependencies().addAll(List.of(
                siriusComponentsCore,
                siriusComponentsRepresentations
        ));

        siriusComponentsGraphQLApi.getDependencies().addAll(List.of(
                siriusComponentsAnnotationsSpring,
                siriusComponentsCore,
                siriusComponentsCollaborative
        ));
    }

    private void linkDeck(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsRepresentations = eObjectIndexer.getComponent("sirius-components-representations");
        var siriusComponentsDeck = eObjectIndexer.getComponent("sirius-components-deck");
        siriusComponentsDeck.getDependencies().add(siriusComponentsRepresentations);

        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsCollaborativeDeck = eObjectIndexer.getComponent("sirius-components-collaborative-deck");
        siriusComponentsCollaborativeDeck.getDependencies().addAll(List.of(
                siriusComponentsDeck,
                siriusComponentsCollaborative
        ));

        var siriusComponentsGraphQLApi = eObjectIndexer.getComponent("sirius-components-graphql-api");
        var siriusComponentsAnnotationsSpring = eObjectIndexer.getComponent("sirius-components-annotations-spring");
        var siriusComponentsDeckGraphQL = eObjectIndexer.getComponent("sirius-components-deck-graphql");
        siriusComponentsDeckGraphQL.getDependencies().addAll(List.of(
                siriusComponentsGraphQLApi,
                siriusComponentsAnnotationsSpring,
                siriusComponentsCollaborativeDeck
        ));

    }

    private void linkDiagrams(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsRepresentations = eObjectIndexer.getComponent("sirius-components-representations");
        var siriusComponentsDiagrams = eObjectIndexer.getComponent("sirius-components-diagrams");
        siriusComponentsDiagrams.getDependencies().add(siriusComponentsRepresentations);

        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsCollaborativeDiagrams = eObjectIndexer.getComponent("sirius-components-collaborative-diagrams");
        siriusComponentsCollaborativeDiagrams.getDependencies().addAll(List.of(
                siriusComponentsDiagrams,
                siriusComponentsCollaborative
        ));

        var siriusComponentsGraphQLApi = eObjectIndexer.getComponent("sirius-components-graphql-api");
        var siriusComponentsAnnotationsSpring = eObjectIndexer.getComponent("sirius-components-annotations-spring");
        var siriusComponentsDiagramsGraphQL = eObjectIndexer.getComponent("sirius-components-diagrams-graphql");
        siriusComponentsDiagramsGraphQL.getDependencies().addAll(List.of(
                siriusComponentsGraphQLApi,
                siriusComponentsAnnotationsSpring,
                siriusComponentsCollaborativeDiagrams
        ));
    }

    private void linkDomain(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsDomain = eObjectIndexer.getComponent("sirius-components-domain");
        var siriusComponentsDomainEdit = eObjectIndexer.getComponent("sirius-components-domain-edit");
        siriusComponentsDomainEdit.getDependencies().add(siriusComponentsDomain);

        var siriusComponentsEMF = eObjectIndexer.getComponent("sirius-components-emf");
        var siriusComponentsDomainEMF = eObjectIndexer.getComponent("sirius-components-domain-emf");
        siriusComponentsDomainEMF.getDependencies().addAll(List.of(
                siriusComponentsEMF,
                siriusComponentsDomain
        ));
    }

    private void linkEMF(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsInterpreter = eObjectIndexer.getComponent("sirius-components-interpreter");
        var siriusComponentsEMF = eObjectIndexer.getComponent("sirius-components-emf");
        siriusComponentsEMF.getDependencies().addAll(List.of(
                siriusComponentsCollaborative,
                siriusComponentsInterpreter
        ));

        var siriusComponentsForms = eObjectIndexer.getComponent("sirius-components-forms");
        var siriusComponentsWidgetReference = eObjectIndexer.getComponent("sirius-components-widget-reference");
        var siriusComponentsEMFForms = eObjectIndexer.getComponent("sirius-components-emf-forms");
        siriusComponentsEMFForms.getDependencies().addAll(List.of(
                siriusComponentsForms,
                siriusComponentsWidgetReference,
                siriusComponentsEMF
        ));

    }

    private void linkFormDescriptionEditors(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsAnnotations = eObjectIndexer.getComponent("sirius-components-annotations");
        var siriusComponentsCore = eObjectIndexer.getComponent("sirius-components-core");
        var siriusComponentsRepresentations = eObjectIndexer.getComponent("sirius-components-representations");
        var siriusComponentsForms = eObjectIndexer.getComponent("sirius-components-forms");
        var siriusComponentsViewForm = eObjectIndexer.getComponent("sirius-components-view-form");
        var siriusComponentsFormDescriptionEditors = eObjectIndexer.getComponent("sirius-components-formdescriptioneditors");
        siriusComponentsFormDescriptionEditors.getDependencies().addAll(List.of(
                siriusComponentsAnnotations,
                siriusComponentsCore,
                siriusComponentsRepresentations,
                siriusComponentsForms,
                siriusComponentsViewForm
        ));

        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsCollaborativeFormDescriptionEditors = eObjectIndexer.getComponent("sirius-components-collaborative-formdescriptioneditors");
        siriusComponentsCollaborativeFormDescriptionEditors.getDependencies().addAll(List.of(
                siriusComponentsCollaborative,
                siriusComponentsFormDescriptionEditors
        ));

        var siriusComponentsGraphQLApi = eObjectIndexer.getComponent("sirius-components-graphql-api");
        var siriusComponentsAnnotationsSpring = eObjectIndexer.getComponent("sirius-components-annotations-spring");
        var siriusComponentsFormDescriptionEditorsGraphQL = eObjectIndexer.getComponent("sirius-components-formdescriptioneditors-graphql");
        siriusComponentsFormDescriptionEditorsGraphQL.getDependencies().addAll(List.of(
                siriusComponentsGraphQLApi,
                siriusComponentsAnnotationsSpring,
                siriusComponentsCollaborativeFormDescriptionEditors
        ));

        var siriusComponentsCollaborativeWidgetReference = eObjectIndexer.getComponent("sirius-components-collaborative-widget-reference");
        var siriusComponentsCollaborativeFormDescriptionEditorsWidgetReference = eObjectIndexer.getComponent("sirius-components-collaborative-formdescriptioneditors-widget-reference");
        siriusComponentsCollaborativeFormDescriptionEditorsWidgetReference.getDependencies().addAll(List.of(
                siriusComponentsCollaborativeFormDescriptionEditors,
                siriusComponentsCollaborativeWidgetReference
        ));
    }

    private void linkForms(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsAnnotations = eObjectIndexer.getComponent("sirius-components-annotations");
        var siriusComponentsRepresentations = eObjectIndexer.getComponent("sirius-components-representations");
        var siriusComponentsCharts = eObjectIndexer.getComponent("sirius-components-charts");
        var siriusComponentsForms = eObjectIndexer.getComponent("sirius-components-forms");
        siriusComponentsForms.getDependencies().addAll(List.of(
                siriusComponentsAnnotations,
                siriusComponentsRepresentations,
                siriusComponentsCharts
        ));

        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsCollaborativeCharts = eObjectIndexer.getComponent("sirius-components-collaborative-charts");
        var siriusComponentsCollaborativeForms = eObjectIndexer.getComponent("sirius-components-collaborative-forms");
        siriusComponentsCollaborativeForms.getDependencies().addAll(List.of(
                siriusComponentsForms,
                siriusComponentsCollaborative,
                siriusComponentsCollaborativeCharts
        ));

        var siriusComponentsGraphQLApi = eObjectIndexer.getComponent("sirius-components-graphql-api");
        var siriusComponentsAnnotationsSpring = eObjectIndexer.getComponent("sirius-components-annotations-spring");
        var siriusComponentsFormsGraphQL = eObjectIndexer.getComponent("sirius-components-forms-graphql");
        siriusComponentsFormsGraphQL.getDependencies().addAll(List.of(
                siriusComponentsGraphQLApi,
                siriusComponentsAnnotationsSpring,
                siriusComponentsForms,
                siriusComponentsCollaborativeForms
        ));


        var siriusComponentsWidgetReference = eObjectIndexer.getComponent("sirius-components-widget-reference");
        siriusComponentsWidgetReference.getDependencies().add(siriusComponentsForms);

        var siriusComponentsEMF = eObjectIndexer.getComponent("sirius-components-emf");
        var siriusComponentsCollaborativeTrees = eObjectIndexer.getComponent("sirius-components-collaborative-trees");
        var siriusComponentsCollaborativeWidgetReference = eObjectIndexer.getComponent("sirius-components-collaborative-widget-reference");
        siriusComponentsCollaborativeWidgetReference.getDependencies().addAll(List.of(
                siriusComponentsWidgetReference,
                siriusComponentsEMF,
                siriusComponentsCollaborativeForms,
                siriusComponentsCollaborativeTrees
        ));


        var siriusComponentsWidgetReferenceGraphQL = eObjectIndexer.getComponent("sirius-components-widget-reference-graphql");
        siriusComponentsWidgetReferenceGraphQL.getDependencies().addAll(List.of(
                siriusComponentsGraphQLApi,
                siriusComponentsAnnotationsSpring,
                siriusComponentsCollaborativeWidgetReference
        ));
    }

    private void linkGantt(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsRepresentations = eObjectIndexer.getComponent("sirius-components-representations");
        var siriusComponentsGantt = eObjectIndexer.getComponent("sirius-components-gantt");
        siriusComponentsGantt.getDependencies().add(siriusComponentsRepresentations);

        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsCollaborativeGantt = eObjectIndexer.getComponent("sirius-components-collaborative-gantt");
        siriusComponentsCollaborativeGantt.getDependencies().addAll(List.of(
                siriusComponentsGantt,
                siriusComponentsCollaborative
        ));

        var siriusComponentsGraphQLApi = eObjectIndexer.getComponent("sirius-components-graphql-api");
        var siriusComponentsAnnotationsSpring = eObjectIndexer.getComponent("sirius-components-annotations-spring");
        var siriusComponentsGanttGraphQL = eObjectIndexer.getComponent("sirius-components-gantt-graphql");
        siriusComponentsGanttGraphQL.getDependencies().addAll(List.of(
                siriusComponentsGraphQLApi,
                siriusComponentsAnnotationsSpring,
                siriusComponentsCollaborativeGantt
        ));
    }

    private void linkPapaya(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsPapaya = eObjectIndexer.getComponent("sirius-components-papaya");
        var siriusComponentsPapayaEdit = eObjectIndexer.getComponent("sirius-components-papaya-edit");
        siriusComponentsPapayaEdit.getDependencies().add(siriusComponentsPapaya);
    }

    private void linkPortals(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsAnnotations = eObjectIndexer.getComponent("sirius-components-annotations");
        var siriusComponentsRepresentations = eObjectIndexer.getComponent("sirius-components-representations");
        var siriusComponentsPortals = eObjectIndexer.getComponent("sirius-components-portals");
        siriusComponentsPortals.getDependencies().addAll(List.of(
                siriusComponentsAnnotations,
                siriusComponentsRepresentations
        ));

        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsCollaborativePortals = eObjectIndexer.getComponent("sirius-components-collaborative-portals");
        siriusComponentsCollaborativePortals.getDependencies().addAll(List.of(
                siriusComponentsPortals,
                siriusComponentsCollaborative
        ));

        var siriusComponentsGraphQLApi = eObjectIndexer.getComponent("sirius-components-graphql-api");
        var siriusComponentsAnnotationsSpring = eObjectIndexer.getComponent("sirius-components-annotations-spring");
        var siriusComponentsPortalsGraphQL = eObjectIndexer.getComponent("sirius-components-portals-graphql");
        siriusComponentsPortalsGraphQL.getDependencies().addAll(List.of(
                siriusComponentsGraphQLApi,
                siriusComponentsAnnotationsSpring,
                siriusComponentsCollaborativePortals
        ));
    }

    private void linkSelection(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsAnnotations = eObjectIndexer.getComponent("sirius-components-annotations");
        var siriusComponentsRepresentations = eObjectIndexer.getComponent("sirius-components-representations");
        var siriusComponentsSelection = eObjectIndexer.getComponent("sirius-components-selection");
        siriusComponentsSelection.getDependencies().addAll(List.of(
                siriusComponentsAnnotations,
                siriusComponentsRepresentations
        ));

        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsCollaborativeSelection = eObjectIndexer.getComponent("sirius-components-collaborative-selection");
        siriusComponentsCollaborativeSelection.getDependencies().addAll(List.of(
                siriusComponentsCollaborative,
                siriusComponentsSelection
        ));

        var siriusComponentsGraphQLApi = eObjectIndexer.getComponent("sirius-components-graphql-api");
        var siriusComponentsAnnotationsSpring = eObjectIndexer.getComponent("sirius-components-annotations-spring");
        var siriusComponentsSelectionGraphQL = eObjectIndexer.getComponent("sirius-components-selection-graphql");
        siriusComponentsSelectionGraphQL.getDependencies().addAll(List.of(
                siriusComponentsGraphQLApi,
                siriusComponentsAnnotationsSpring,
                siriusComponentsCollaborativeSelection
        ));
    }

    private void linkSiriusWeb(IEObjectIndexer eObjectIndexer) {
        var siriusWebDomain = eObjectIndexer.getComponent("sirius-web-domain");

        var siriusComponentsAnnotationsSpring = eObjectIndexer.getComponent("sirius-components-annotations-spring");
        var siriusComponentsGraphQLApi = eObjectIndexer.getComponent("sirius-components-graphql-api");
        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsCollaborativeForms = eObjectIndexer.getComponent("sirius-components-collaborative-forms");
        var siriusComponentsCollaborativePortals = eObjectIndexer.getComponent("sirius-components-collaborative-portals");
        var siriusComponentsCollaborativeTrees = eObjectIndexer.getComponent("sirius-components-collaborative-trees");
        var siriusComponentsCollaborativeValidation = eObjectIndexer.getComponent("sirius-components-collaborative-validation");
        var siriusComponentsEMF = eObjectIndexer.getComponent("sirius-components-emf");
        var siriusComponentsEMFForms = eObjectIndexer.getComponent("sirius-components-emf-forms");
        var siriusComponentsDomain = eObjectIndexer.getComponent("sirius-components-domain");
        var siriusComponentsDomainEdit = eObjectIndexer.getComponent("sirius-components-domain-edit");
        var siriusComponentsDomainEMF = eObjectIndexer.getComponent("sirius-components-domain-emf");
        var siriusComponentsTrees = eObjectIndexer.getComponent("sirius-components-trees");
        var siriusComponentsView = eObjectIndexer.getComponent("sirius-components-view");
        var siriusComponentsViewEdit = eObjectIndexer.getComponent("sirius-components-view-edit");
        var siriusComponentsViewBuilder = eObjectIndexer.getComponent("sirius-components-view-builder");
        var siriusComponentsViewDeck = eObjectIndexer.getComponent("sirius-components-view-deck");
        var siriusComponentsViewDeckEdit = eObjectIndexer.getComponent("sirius-components-view-deck-edit");
        var siriusComponentsViewDiagram = eObjectIndexer.getComponent("sirius-components-view-diagram");
        var siriusComponentsViewDiagramEdit = eObjectIndexer.getComponent("sirius-components-view-diagram-edit");
        var siriusComponentsViewForm = eObjectIndexer.getComponent("sirius-components-view-form");
        var siriusComponentsViewFormEdit = eObjectIndexer.getComponent("sirius-components-view-form-edit");
        var siriusComponentsViewGantt = eObjectIndexer.getComponent("sirius-components-view-gantt");
        var siriusComponentsViewGanttEdit = eObjectIndexer.getComponent("sirius-components-view-gantt-edit");
        var siriusComponentsViewEMF = eObjectIndexer.getComponent("sirius-components-view-emf");
        var siriusWebApplication = eObjectIndexer.getComponent("sirius-web-application");
        siriusWebApplication.getDependencies().addAll(List.of(
                siriusWebDomain,
                siriusComponentsAnnotationsSpring,
                siriusComponentsGraphQLApi,
                siriusComponentsCollaborative,
                siriusComponentsCollaborativeForms,
                siriusComponentsCollaborativePortals,
                siriusComponentsCollaborativeTrees,
                siriusComponentsCollaborativeValidation,
                siriusComponentsEMF,
                siriusComponentsEMFForms,
                siriusComponentsDomain,
                siriusComponentsDomainEdit,
                siriusComponentsDomainEMF,
                siriusComponentsTrees,
                siriusComponentsView,
                siriusComponentsViewEdit,
                siriusComponentsViewBuilder,
                siriusComponentsViewDeck,
                siriusComponentsViewDeckEdit,
                siriusComponentsViewDiagram,
                siriusComponentsViewDiagramEdit,
                siriusComponentsViewForm,
                siriusComponentsViewFormEdit,
                siriusComponentsViewGantt,
                siriusComponentsViewGanttEdit,
                siriusComponentsViewEMF
        ));

        var siriusWebInfrastructure = eObjectIndexer.getComponent("sirius-web-infrastructure");
        siriusWebInfrastructure.getDependencies().addAll(List.of(siriusWebApplication));

        var siriusComponentsGraphQL = eObjectIndexer.getComponent("sirius-components-graphql");
        var siriusComponentsDeckGraphQL = eObjectIndexer.getComponent("sirius-components-deck-graphql");
        var siriusComponentsDiagramsGraphQL = eObjectIndexer.getComponent("sirius-components-diagrams-graphql");
        var siriusComponentsFormsGraphQL = eObjectIndexer.getComponent("sirius-components-forms-graphql");
        var siriusComponentsWidgetReferenceGraphQL = eObjectIndexer.getComponent("sirius-components-widget-reference-graphql");
        var siriusComponentsFormDescriptionEditorsGraphQL = eObjectIndexer.getComponent("sirius-components-formdescriptioneditors-graphql");
        var siriusComponentsGanttGraphQL = eObjectIndexer.getComponent("sirius-components-gantt-graphql");
        var siriusComponentsPortalsGraphQL = eObjectIndexer.getComponent("sirius-components-portals-graphql");
        var siriusComponentsSelectionGraphQL = eObjectIndexer.getComponent("sirius-components-selection-graphql");
        var siriusComponentsTreesGraphQL = eObjectIndexer.getComponent("sirius-components-trees-graphql");
        var siriusComponentsValidationGraphQL = eObjectIndexer.getComponent("sirius-components-validation-graphql");
        var siriusWebStarter = eObjectIndexer.getComponent("sirius-web-starter");
        siriusWebStarter.getDependencies().addAll(List.of(
                siriusWebInfrastructure,
                siriusComponentsGraphQL,
                siriusComponentsDeckGraphQL,
                siriusComponentsDiagramsGraphQL,
                siriusComponentsFormsGraphQL,
                siriusComponentsWidgetReferenceGraphQL,
                siriusComponentsFormDescriptionEditorsGraphQL,
                siriusComponentsGanttGraphQL,
                siriusComponentsPortalsGraphQL,
                siriusComponentsSelectionGraphQL,
                siriusComponentsTreesGraphQL,
                siriusComponentsValidationGraphQL
        ));

        var siriusWeb = eObjectIndexer.getComponent("sirius-web");
        siriusWeb.getDependencies().addAll(List.of(siriusWebStarter));
    }

    private void linkTrees(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsAnnotations = eObjectIndexer.getComponent("sirius-components-annotations");
        var siriusComponentsRepresentations = eObjectIndexer.getComponent("sirius-components-representations");
        var siriusComponentsTrees = eObjectIndexer.getComponent("sirius-components-trees");
        siriusComponentsTrees.getDependencies().addAll(List.of(
                siriusComponentsAnnotations,
                siriusComponentsRepresentations
        ));

        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsCollaborativeTrees = eObjectIndexer.getComponent("sirius-components-collaborative-trees");
        siriusComponentsCollaborativeTrees.getDependencies().addAll(List.of(
                siriusComponentsTrees,
                siriusComponentsCollaborative
        ));


        var siriusComponentsGraphQLApi = eObjectIndexer.getComponent("sirius-components-graphql-api");
        var siriusComponentsAnnotationsSpring = eObjectIndexer.getComponent("sirius-components-annotations-spring");
        var siriusComponentsTreesGraphQL = eObjectIndexer.getComponent("sirius-components-trees-graphql");
        siriusComponentsTreesGraphQL.getDependencies().addAll(List.of(
                siriusComponentsGraphQLApi,
                siriusComponentsAnnotationsSpring,
                siriusComponentsCollaborativeTrees
        ));
    }

    private void linkValidation(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsRepresentations = eObjectIndexer.getComponent("sirius-components-representations");
        var siriusComponentsValidation = eObjectIndexer.getComponent("sirius-components-validation");
        siriusComponentsValidation.getDependencies().addAll(List.of(
                siriusComponentsRepresentations
        ));

        var siriusComponentsCollaborative = eObjectIndexer.getComponent("sirius-components-collaborative");
        var siriusComponentsCollaborativeValidation = eObjectIndexer.getComponent("sirius-components-collaborative-validation");
        siriusComponentsCollaborativeValidation.getDependencies().addAll(List.of(
                siriusComponentsValidation,
                siriusComponentsCollaborative
        ));

        var siriusComponentsGraphQLApi = eObjectIndexer.getComponent("sirius-components-graphql-api");
        var siriusComponentsAnnotationsSpring = eObjectIndexer.getComponent("sirius-components-annotations-spring");
        var siriusComponentsValidationGraphQL = eObjectIndexer.getComponent("sirius-components-validation-graphql");
        siriusComponentsValidationGraphQL.getDependencies().addAll(List.of(
                siriusComponentsGraphQLApi,
                siriusComponentsAnnotationsSpring,
                siriusComponentsCollaborativeValidation
        ));
    }

    private void linkView(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsView = eObjectIndexer.getComponent("sirius-components-view");
        var siriusComponentsViewEdit = eObjectIndexer.getComponent("sirius-components-view-edit");
        siriusComponentsViewEdit.getDependencies().add(siriusComponentsView);

        var siriusComponentsViewDeck = eObjectIndexer.getComponent("sirius-components-view-deck");
        siriusComponentsViewDeck.getDependencies().add(siriusComponentsView);
        var siriusComponentsViewDeckEdit = eObjectIndexer.getComponent("sirius-components-view-deck-edit");
        siriusComponentsViewDeckEdit.getDependencies().addAll(List.of(
                siriusComponentsViewEdit,
                siriusComponentsViewDeck
        ));

        var siriusComponentsViewDiagram = eObjectIndexer.getComponent("sirius-components-view-diagram");
        siriusComponentsViewDiagram.getDependencies().add(siriusComponentsView);
        var siriusComponentsViewDiagramEdit = eObjectIndexer.getComponent("sirius-components-view-diagram-edit");
        siriusComponentsViewDiagramEdit.getDependencies().addAll(List.of(
                siriusComponentsViewEdit,
                siriusComponentsViewDiagram
        ));

        var siriusComponentsViewForm = eObjectIndexer.getComponent("sirius-components-view-form");
        siriusComponentsViewForm.getDependencies().add(siriusComponentsView);
        var siriusComponentsViewFormEdit = eObjectIndexer.getComponent("sirius-components-view-form-edit");
        siriusComponentsViewFormEdit.getDependencies().addAll(List.of(
                siriusComponentsViewEdit,
                siriusComponentsViewForm
        ));

        var siriusComponentsViewGantt = eObjectIndexer.getComponent("sirius-components-view-gantt");
        siriusComponentsViewGantt.getDependencies().add(siriusComponentsView);
        var siriusComponentsViewGanttEdit = eObjectIndexer.getComponent("sirius-components-view-gantt-edit");
        siriusComponentsViewGanttEdit.getDependencies().addAll(List.of(
                siriusComponentsViewEdit,
                siriusComponentsViewGantt
        ));

        var siriusComponentsViewBuilder = eObjectIndexer.getComponent("sirius-components-view-builder");
        siriusComponentsViewBuilder.getDependencies().addAll(List.of(
                siriusComponentsViewDeck,
                siriusComponentsViewDiagram,
                siriusComponentsViewForm,
                siriusComponentsViewGantt
        ));

        var siriusComponentsViewEMF = eObjectIndexer.getComponent("sirius-components-view-emf");
        siriusComponentsViewEMF.getDependencies().addAll(List.of(
                siriusComponentsViewDeck,
                siriusComponentsViewDiagram,
                siriusComponentsViewForm,
                siriusComponentsViewGantt
        ));
    }

    private void linkWeb(IEObjectIndexer eObjectIndexer) {
        var siriusComponentsAnnotations = eObjectIndexer.getComponent("sirius-components-annotations");
        var siriusComponentsGraphQLApi = eObjectIndexer.getComponent("sirius-components-graphql-api");
        var siriusComponentsGraphQL = eObjectIndexer.getComponent("sirius-components-graphql");
        siriusComponentsGraphQL.getDependencies().addAll(List.of(
                siriusComponentsAnnotations,
                siriusComponentsGraphQLApi
        ));

        var siriusComponentsCore = eObjectIndexer.getComponent("sirius-components-core");
        var siriusComponentsWeb = eObjectIndexer.getComponent("sirius-components-web");
        siriusComponentsWeb.getDependencies().add(siriusComponentsCore);
    }
}
