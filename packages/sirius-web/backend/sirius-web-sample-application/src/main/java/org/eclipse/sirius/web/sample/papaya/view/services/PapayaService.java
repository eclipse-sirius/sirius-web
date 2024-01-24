/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.web.sample.papaya.view.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * Java Service for the test view.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class PapayaService {

    private final IObjectService objectService;

    private EPackage.Registry ePackageRegistry;

    private EObject eclipseFoundation;

    private EObject githubOrganization;

    private EObject siriusWeb;

    private EObject contributor;

    private EObject specifier;

    private EObject endUser;

    private EObject hostTheSourceCode;

    private EObject hostTheIssues;

    private EObject buildTheProject;

    private EObject runTheTests;

    private EObject hostTheReleases;

    private EObject provideConfigurableRepresentationDescriptions;

    private EObject offerSupportForDiagrams;

    private EObject offerSupportForForms;

    private EObject offerSupportForTrees;

    private EObject provideCompatibilityLayerForSiriusDesktop;

    private EObject offerCollaborativeModelEdition;

    private EObject managesTheProject;

    private EObject contributeNewCode;

    private EObject raiseIssues;

    private EObject performNewReleases;

    private EObject configureRepresentationDescriptions;

    private EObject reuseExistingOdesignConfigurations;

    private EObject downloadReleases;

    private EObject customizeTheCode;

    private EObject buildCustomSiriusWebBasedApplications;

    private EObject useSiriusWeBasedApplications;

    private EObject raiseFeatureRequests;

    private EObject javaStandardLibrary;

    private EObject siriusWebApplication;

    private EObject siriusComponentsCore;

    private EObject siriusComponentsRepresentations;

    private EObject siriusComponentsCollaborative;

    private EObject siriusComponentsCollaborativeDiagrams;

    private EObject siriusComponentsDiagrams;

    private EObject siriusComponentsCollaborativeForms;

    private EObject siriusComponentsForms;

    private EObject siriusComponentsCollaborativeTrees;

    private EObject siriusComponentsTrees;

    private EObject webmaster;

    private EObject requireRepresentationEventProcessor;

    private EObject provideDiagramEventProcessor;

    private EObject provideFormEventProcessor;

    private EObject provideTreeEventProcessor;

    private EObject packageJavaLang;

    private EObject packageJavaUtil;

    private EObject packageSiriusComponentsCollaborativeEditingContext;

    private EObject packageSiriusComponentsCollaborativeApi;

    private EObject packageSiriusComponentsCollaborativeDiagramsApi;

    private EObject packageSiriusComponentsCollaborativeDiagrams;

    private EObject packageSiriusComponentsCollaborativeFormsApi;

    private EObject packageSiriusComponentsCollaborativeForms;

    private EObject packageSiriusComponentsCollaborativeTreesApi;

    private EObject packageSiriusComponentsCollaborativeTrees;

    private EObject packageSiriusComponentsRepresentations;

    private EObject packageSiriusComponentsDiagrams;

    private EObject packageSiriusComponentsDiagramsDescription;

    private EObject packageSiriusComponentsForms;

    private EObject packageSiriusComponentsFormsDescription;

    private EObject packageSiriusComponentsTrees;

    private EObject packageSiriusComponentsTreesDescription;

    private EObject packageSiriusComponentsCollaborativeRepresentations;

    private EObject dataTypeVoid;

    private EObject dataTypeObject;

    private EObject dataTypeString;

    private EObject dataTypeInt;

    private EObject dataTypeUUID;

    private EObject editingContextEventProcessor;

    private EObject iEditingContextEventProcessor;

    private EObject iEditingContextEventProcessorFactory;

    private EObject iEditingContextEventProcessorRegistry;

    private EObject iEditingContextEventHandler;

    private EObject changeDescription;

    private EObject iSubscriptionManager;

    private EObject iSubscriptionManagerFactory;

    private EObject iRepresentationSearchService;

    private EObject iRepresentationPersistenceService;

    private EObject iRepresentationEventProcessor;

    private EObject iRepresentation;

    private EObject iRepresentationDescription;

    private EObject iDiagramEventProcessor;

    private EObject iFormEventProcessor;

    private EObject iTreeEventProcessor;

    private EObject diagramEventProcessor;

    private EObject formEventProcessor;

    private EObject treeEventProcessor;

    private EObject subscriptionManager;

    private EObject subscriptionManagerSubscriptionCount;

    private EObject subscriptionManagerGetFlux;

    private EObject subscriptionManagerIsEmpty;

    private EObject subscriptionManagerCanBeDisposed;

    private EObject subscriptionManagerDispose;

    private EObject diagram;

    private EObject diagramId;

    private EObject node;

    private EObject nodeId;

    private EObject nodeType;

    private EObject nodeTargetObjectId;

    private EObject nodeTargetObjectKind;

    private EObject nodeTargetObjectLabel;

    private EObject label;

    private EObject labelId;

    private EObject labelType;

    private EObject labelText;

    private EObject edge;

    private EObject edgeId;

    private EObject edgeType;

    private EObject edgeTargetObjectId;

    private EObject edgeTargetObjectKind;

    private EObject edgeTargetObjectLabel;

    private EObject size;

    private EObject width;

    private EObject height;

    private EObject position;

    private EObject x;

    private EObject y;

    private EObject diagramDescription;

    private EObject nodeDescription;

    private EObject labelDescription;

    private EObject edgeDescription;

    private EObject form;

    private EObject page;

    private EObject group;

    private EObject abstractWidget;

    private EObject formDescription;

    private EObject pageDescription;

    private EObject groupDescription;

    private EObject tree;

    private EObject treeItem;

    private EObject treeDescription;

    private EObject operationIRepresentationEventProcessorGetRepresentation;

    private EObject operationIRepresentationEventProcessorRefresh;

    private EObject operationIRepresentationGetId;

    private EObject operationIRepresentationGetLabel;

    private EObject operationIRepresentationGetKind;

    private EObject operationIRepresentationGetDescriptionId;

    private EObject operationIRepresentationDescriptionGetId;

    private EObject operationIRepresentationDescriptionGetLabel;

    public PapayaService(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    public EObject initialize(EObject eObject, IDiagramContext diagramContext,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes) {
        this.ePackageRegistry = eObject.eResource().getResourceSet().getPackageRegistry();

        this.createStandardLibraries();
        this.createOperationalObjects(diagramContext, convertedNodes);
        this.createComponents();
        this.createCode();

        this.linkOperationalObjects();
        this.linkComponents();

        this.addMany(eObject, "operationalEntities", List.of(this.eclipseFoundation));
        this.addMany(eObject, "operationalActors", List.of(this.specifier, this.endUser, this.webmaster));
        this.addMany(eObject, "components",
                List.of(this.javaStandardLibrary, this.siriusWebApplication, this.siriusComponentsCore, this.siriusComponentsCollaborative, this.siriusComponentsRepresentations,
                        this.siriusComponentsCollaborativeDiagrams, this.siriusComponentsDiagrams, this.siriusComponentsCollaborativeForms, this.siriusComponentsForms,
                        this.siriusComponentsCollaborativeTrees, this.siriusComponentsTrees));

        this.drop(this.eclipseFoundation, null, diagramContext, convertedNodes);

        return eObject;
    }

    public EObject initDeploymentDiagram(EObject eObject) {
        this.ePackageRegistry = eObject.eResource().getResourceSet().getPackageRegistry();
        var componentWebBrowser = this.component("Web Browser");
        var componentAws = this.component("Amazon Web Services");
        this.addMany(eObject, "components", List.of(componentWebBrowser, componentAws));
        var componentAwsEb = this.component("AWS Elastic Beanstalk");
        var componentAwsRds = this.component("AWS RDS");

        this.addMany(componentAws, "components", List.of(componentAwsEb, componentAwsRds));
        var componentDocker = this.component("Docker");
        this.addMany(componentAwsEb, "components", List.of(componentDocker));
        var componentSiriusWeb = this.component("Amazon Web Services");
        this.addMany(componentDocker, "components", List.of(componentSiriusWeb));
        var componentPostgre = this.component("PostgreSQL");

        this.addMany(componentAwsRds, "components", List.of(componentPostgre));

        createComponentExchangeFromComponentList(eObject, "GraphQL queries and mutations", List.of(componentSiriusWeb, componentDocker, componentAwsEb, componentAws, componentWebBrowser));
        createComponentExchangeFromComponentList(eObject, "DB operations", List.of(componentSiriusWeb, componentDocker, componentAwsEb, componentAwsRds, componentPostgre));
        return eObject;
    }

    private void createComponentExchangeFromComponentList(EObject root, String name, List<EObject> listComponents) {
        var componentExchange = this.componentExchange(name, "Component Exchange", "component exchange");
        this.addMany(root, "componentExchanges", List.of(componentExchange));
        for (int i = 0; i < listComponents.size(); i++) {
            var port = this.componentPort("Port " + i, "");
            this.addMany(listComponents.get(i), "ports", List.of(port));
            this.componentsExchangePorts(componentExchange, port);
        }

    }
    public EObject createComponentExchange(EObject semanticEdgeSource, EObject semanticEdgeTarget) {
        this.ePackageRegistry = semanticEdgeSource.eResource().getResourceSet().getPackageRegistry();
        var listSourceComponents = new ArrayList<EObject>();
        var listTargetComponents = new ArrayList<EObject>();
        var semanticEdgeSourceContainer = semanticEdgeSource;
        var semanticEdgeTargetContainer = semanticEdgeTarget;

        while (semanticEdgeTargetContainer.eContainer() != null || semanticEdgeSourceContainer.eContainer() != null) {
            if (semanticEdgeTargetContainer.eContainer() != null) {
                listTargetComponents.add(semanticEdgeTargetContainer);
                semanticEdgeTargetContainer = semanticEdgeTargetContainer.eContainer();
            }
            if (semanticEdgeSourceContainer.eContainer() != null) {
                listSourceComponents.add(semanticEdgeSourceContainer);
                semanticEdgeSourceContainer = semanticEdgeSourceContainer.eContainer();
            }
        }
        var root = this.getRoot(semanticEdgeSource);
        this.initComponentExchangeFromComponents(listSourceComponents, listTargetComponents, root);
        return root;
    }

    private void componentsExchangePorts(EObject componentExchange, EObject port) {
        ((EList<EObject>) componentExchange.eGet(componentExchange.eClass().getEStructuralFeature("ports"))).add(port);
    }

    private void initComponentExchangeFromComponents(List<EObject> listSourceComponents, List<EObject> listTargetComponents, EObject root) {
        EObject sameContainer = this.getSameContainer(listSourceComponents, listTargetComponents);

        var componentExchange = this.componentExchange("Component Exchange", "Component Exchange", "component exchange");
        this.addMany(root, "componentExchanges", List.of(componentExchange));

        var index = 0;

        var sourceComponent = listSourceComponents.get(0);
        EObject lastAddedSourceComponent = null;
        while (sourceComponent != sameContainer) {
            var port = this.componentPort("Port " + index, "");
            this.addMany(sourceComponent, "ports", List.of(port));
            this.componentsExchangePorts(componentExchange, port);

            if (sourceComponent.eContainer().eClass().getName().equals("Root")) {
                lastAddedSourceComponent = sourceComponent;
            }
            sourceComponent = sourceComponent.eContainer();
            index++;
        }

        Collections.reverse(listTargetComponents);
        boolean shouldAdd = false;
        for (EObject listTargetComponent : listTargetComponents) {
            if (listTargetComponent.eContainer() == sameContainer) {
                shouldAdd = true;
            }
            if (shouldAdd && lastAddedSourceComponent != listTargetComponent) {
                var port = this.componentPort("Port " + index, "");
                this.addMany(listTargetComponent, "ports", List.of(port));
                this.componentsExchangePorts(componentExchange, port);
                index++;
            }
        }
    }

    private EObject getSameContainer(List<EObject> firstList, List<EObject> secondList) {
        EObject sameContainer = null;
        for (EObject sourceContainer : firstList) {
            for (EObject targetContainer : secondList) {
                if (sourceContainer.eContainer() == targetContainer.eContainer()) {
                    sameContainer = sourceContainer.eContainer();
                }
            }
        }
        return sameContainer;
    }

    private EObject getRoot(EObject eobject) {
        var root = eobject;
        while (!root.eClass().getName().equals("Root")) {
            root = root.eContainer();
        }
        return root;
    }

    private void createStandardLibraries() {
        this.javaStandardLibrary = this.component("Java Standard Library");
        this.packageJavaLang = this.packageEObject("java.lang");
        this.packageJavaUtil = this.packageEObject("java.util");

        this.dataTypeObject = this.dataType("Object");
        this.dataTypeString = this.dataType("String");
        this.dataTypeInt = this.dataType("int");
        this.dataTypeVoid = this.dataType("Void");
        this.dataTypeUUID = this.dataType("UUID");

        this.addMany(this.javaStandardLibrary, "packages", List.of(this.packageJavaLang, this.packageJavaUtil));
        this.addMany(this.packageJavaLang, "types", List.of(this.dataTypeObject, this.dataTypeVoid, this.dataTypeString, this.dataTypeInt));
        this.addMany(this.packageJavaUtil, "types", List.of(this.dataTypeUUID));
    }

    private void createOperationalObjects(IDiagramContext diagramContext, Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes) {
        this.eclipseFoundation = this.operationalEntity("Eclipse Foundation");
        this.githubOrganization = this.operationalPerimeter("Github");
        this.siriusWeb = this.operationalPerimeter("Sirius Web");
        this.contributor = this.operationalActor("Contributors");
        this.specifier = this.operationalActor("Specifier");
        this.endUser = this.operationalActor("End User");
        this.webmaster = this.operationalActor("Webmaster");

        this.hostTheSourceCode = this.operationalActivity("Host the source code");
        this.hostTheIssues = this.operationalActivity("Host the issues");
        this.buildTheProject = this.operationalActivity("Build the project");
        this.runTheTests = this.operationalActivity("Run the tests");
        this.hostTheReleases = this.operationalActivity("Host the releases");

        this.provideConfigurableRepresentationDescriptions = this.operationalActivity("Provide configurable representation descriptions");
        this.offerSupportForDiagrams = this.operationalActivity("Offer support for diagrams");
        this.offerSupportForForms = this.operationalActivity("Offer support for forms");
        this.offerSupportForTrees = this.operationalActivity("Offer support for trees");
        this.provideCompatibilityLayerForSiriusDesktop = this.operationalActivity("Provide a compatibility layer for Sirius Desktop");
        this.offerCollaborativeModelEdition = this.operationalActivity("Offer collaborative model edition");

        this.managesTheProject = this.operationalActivity("Manage the project");
        this.contributeNewCode = this.operationalActivity("Contribute new code");
        this.raiseIssues = this.operationalActivity("Raise issues");
        this.performNewReleases = this.operationalActivity("Perform new releases");

        this.configureRepresentationDescriptions = this.operationalActivity("Configure representation descriptions");
        this.reuseExistingOdesignConfigurations = this.operationalActivity("Reuse existing odesign configurations");
        this.downloadReleases = this.operationalActivity("Download releases");
        this.customizeTheCode = this.operationalActivity("Customize the code");
        this.buildCustomSiriusWebBasedApplications = this.operationalActivity("Build custom Sirius Web based applications");

        this.useSiriusWeBasedApplications = this.operationalActivity("Use Sirius Web based applications");
        this.raiseFeatureRequests = this.operationalActivity("Raise feature requests");
    }

    private void createComponents() {
        this.siriusWebApplication = this.component("sirius-web-sample-application");
        this.siriusComponentsCore = this.component("sirius-components-core");
        this.siriusComponentsCollaborative = this.component("sirius-components-collaborative");
        this.siriusComponentsRepresentations = this.component("sirius-components-representations");

        this.siriusComponentsCollaborativeDiagrams = this.component("sirius-components-collaborative-diagrams");
        this.siriusComponentsDiagrams = this.component("sirius-components-diagrams");
        this.siriusComponentsCollaborativeForms = this.component("sirius-components-collaborative-forms");
        this.siriusComponentsForms = this.component("sirius-components-forms");
        this.siriusComponentsCollaborativeTrees = this.component("sirius-components-collaborative-trees");
        this.siriusComponentsTrees = this.component("sirius-components-trees");

        this.requireRepresentationEventProcessor = this.requiredService();
        this.provideDiagramEventProcessor = this.providedService();
        this.provideFormEventProcessor = this.providedService();
        this.provideTreeEventProcessor = this.providedService();
    }

    private void createCode() {
        this.createSiriusComponentsCollaborative();
        this.createSiriusComponentsDiagrams();
        this.createSiriusComponentsForms();
        this.createSiriusComponentsTrees();
    }

    private void createSiriusComponentsCollaborative() {
        this.packageSiriusComponentsCollaborativeEditingContext = this.packageEObject("org.eclipse.sirius.components.collaborative.editingcontext");
        this.editingContextEventProcessor = this.classEObject("EditingContextEventProcessor");

        this.packageSiriusComponentsCollaborativeApi = this.packageEObject("org.eclipse.sirius.components.collaborative.api");
        this.iEditingContextEventProcessor = this.interfaceEObject("IEditingContextEventProcessor");
        this.iEditingContextEventProcessorFactory = this.interfaceEObject("IEditingContextEventProcessorFactory");
        this.iEditingContextEventProcessorRegistry = this.interfaceEObject("IEditingContextEventProcessorRegistry");
        this.iEditingContextEventHandler = this.interfaceEObject("IEditingContextEventHandler");
        this.changeDescription = this.classEObject("ChangeDescription");
        this.iSubscriptionManager = this.interfaceEObject("ISubscriptionManager");
        this.iSubscriptionManagerFactory = this.interfaceEObject("ISubscriptionManagerFactory");
        this.iRepresentationSearchService = this.interfaceEObject("IRepresentationSearchService");
        this.iRepresentationPersistenceService = this.interfaceEObject("IRepresentationPersistenceService");

        this.iRepresentationEventProcessor = this.interfaceEObject("IRepresentationEventProcessor");
        this.operationIRepresentationEventProcessorGetRepresentation = this.operation("getRepresentation");
        this.operationIRepresentationEventProcessorRefresh = this.operation("refresh");

        this.packageSiriusComponentsCollaborativeRepresentations = this.packageEObject("org.eclipse.sirius.components.collaborative.representations");
        this.subscriptionManager = this.classEObject("SubscriptionManager");
        this.subscriptionManagerSubscriptionCount = this.attributeEObject("subscriptionCount", this.dataTypeInt);
        this.subscriptionManagerGetFlux = this.operation("getFlux");
        this.subscriptionManagerIsEmpty = this.operation("isEmpty");
        this.subscriptionManagerCanBeDisposed = this.operation("canBeDisposed");
        this.subscriptionManagerDispose = this.operation("dispose");

        this.packageSiriusComponentsRepresentations = this.packageEObject("org.eclipse.sirius.components.representations");
        this.iRepresentation = this.interfaceEObject("IRepresentation");
        this.operationIRepresentationGetId = this.operation("getId");
        this.operationIRepresentationGetLabel = this.operation("getLabel");
        this.operationIRepresentationGetKind = this.operation("getKind");
        this.operationIRepresentationGetDescriptionId = this.operation("getDescriptionId");

        this.iRepresentationDescription = this.interfaceEObject("IRepresentationDescription");
        this.operationIRepresentationDescriptionGetId = this.operation("getId");
        this.operationIRepresentationDescriptionGetLabel = this.operation("getLabel");

        this.packageSiriusComponentsCollaborativeDiagramsApi = this.packageEObject("org.eclipse.sirius.components.collaborative.diagrams.api");
        this.iDiagramEventProcessor = this.interfaceEObject("IDiagramEventProcessor");

        this.packageSiriusComponentsCollaborativeDiagrams = this.packageEObject("org.eclipse.sirius.components.collaborative.diagrams");
        this.diagramEventProcessor = this.classEObject("DiagramEventProcessor");

        this.packageSiriusComponentsCollaborativeFormsApi = this.packageEObject("org.eclipse.sirius.components.collaborative.forms.api");
        this.iFormEventProcessor = this.interfaceEObject("IFormEventProcessor");

        this.packageSiriusComponentsCollaborativeForms = this.packageEObject("org.eclipse.sirius.components.collaborative.forms");
        this.formEventProcessor = this.classEObject("FormEventProcessor");

        this.packageSiriusComponentsCollaborativeTreesApi = this.packageEObject("org.eclipse.sirius.components.collaborative.trees.api");
        this.iTreeEventProcessor = this.interfaceEObject("ITreeEventProcessor");

        this.packageSiriusComponentsCollaborativeTrees = this.packageEObject("org.eclipse.sirius.components.collaborative.trees");
        this.treeEventProcessor = this.classEObject("TreeEventProcessor");
    }

    private void createSiriusComponentsDiagrams() {
        this.packageSiriusComponentsDiagrams = this.packageEObject("org.eclipse.sirius.components.diagrams");
        this.diagram = this.classEObject("Diagram");
        this.diagramId = this.attributeEObject("id", this.dataTypeString);

        this.node = this.classEObject("Node");
        this.nodeId = this.attributeEObject("id", this.dataTypeString);
        this.nodeType = this.attributeEObject("type", this.dataTypeString);
        this.nodeTargetObjectId = this.attributeEObject("targetObjectId", this.dataTypeString);
        this.nodeTargetObjectKind = this.attributeEObject("targetObjectKind", this.dataTypeString);
        this.nodeTargetObjectLabel = this.attributeEObject("targetObjectLabel", this.dataTypeString);

        this.label = this.classEObject("Label");
        this.labelId = this.attributeEObject("id", this.dataTypeString);
        this.labelType = this.attributeEObject("type", this.dataTypeString);
        this.labelText = this.attributeEObject("text", this.dataTypeString);

        this.edge = this.classEObject("Edge");
        this.edgeId = this.attributeEObject("id", this.dataTypeString);
        this.edgeType = this.attributeEObject("type", this.dataTypeString);
        this.edgeTargetObjectId = this.attributeEObject("targetObjectId", this.dataTypeString);
        this.edgeTargetObjectKind = this.attributeEObject("targetObjectKind", this.dataTypeString);
        this.edgeTargetObjectLabel = this.attributeEObject("targetObjectLabel", this.dataTypeString);

        this.size = this.classEObject("Size");
        this.width = this.attributeEObject("width", this.dataTypeInt);
        this.height = this.attributeEObject("height", this.dataTypeInt);

        this.position = this.classEObject("Position");
        this.x = this.attributeEObject("x", this.dataTypeInt);
        this.y = this.attributeEObject("y", this.dataTypeInt);

        this.packageSiriusComponentsDiagramsDescription = this.packageEObject("org.eclipse.sirius.components.diagrams.description");
        this.diagramDescription = this.classEObject("DiagramDescription");
        this.nodeDescription = this.classEObject("NodeDescription");
        this.labelDescription = this.classEObject("LabelDescription");
        this.edgeDescription = this.classEObject("EdgeDescription");

    }

    private void createSiriusComponentsForms() {
        this.packageSiriusComponentsForms = this.packageEObject("org.eclipse.sirius.components.forms");
        this.form = this.classEObject("Form");
        this.page = this.classEObject("Page");
        this.group = this.classEObject("Group");
        this.abstractWidget = this.classEObject("AbstractWidget");
        this.set(this.abstractWidget, "abstract", true);

        this.packageSiriusComponentsFormsDescription = this.packageEObject("org.eclipse.sirius.components.forms.description");
        this.formDescription = this.classEObject("FormDescription");
        this.pageDescription = this.classEObject("PageDescription");
        this.groupDescription = this.classEObject("GroupDescription");

    }

    private void createSiriusComponentsTrees() {
        this.packageSiriusComponentsTrees = this.packageEObject("org.eclipse.sirius.components.trees");
        this.tree = this.classEObject("Tree");
        this.treeItem = this.classEObject("TreeItem");

        this.packageSiriusComponentsTreesDescription = this.packageEObject("org.eclipse.sirius.components.trees.description");
        this.treeDescription = this.classEObject("TreeDescription");

    }

    private void linkOperationalObjects() {
        this.addMany(this.eclipseFoundation, "operationalPerimeters", List.of(this.githubOrganization, this.siriusWeb));
        this.addMany(this.eclipseFoundation, "operationalActors", List.of(this.contributor));
        this.addMany(this.githubOrganization, "operationalActivities", List.of(this.hostTheSourceCode, this.hostTheIssues, this.buildTheProject, this.runTheTests, this.hostTheReleases));
        this.addMany(this.siriusWeb, "operationalActivities", List.of(this.provideConfigurableRepresentationDescriptions, this.offerSupportForDiagrams, this.offerSupportForForms,
                this.offerSupportForTrees, this.provideCompatibilityLayerForSiriusDesktop, this.offerCollaborativeModelEdition));
        this.addMany(this.contributor, "operationalActivities", List.of(this.managesTheProject, this.contributeNewCode, this.raiseIssues, this.performNewReleases));
        this.addMany(this.specifier, "operationalActivities",
                List.of(this.configureRepresentationDescriptions, this.reuseExistingOdesignConfigurations, this.downloadReleases, this.customizeTheCode, this.buildCustomSiriusWebBasedApplications));
        this.addMany(this.endUser, "operationalActivities", List.of(this.useSiriusWeBasedApplications, this.raiseFeatureRequests));

        this.interactWith(this.managesTheProject, this.hostTheSourceCode);
        this.interactWith(this.hostTheSourceCode, this.buildTheProject);
        this.interactWith(this.buildTheProject, this.runTheTests);
        this.interactWith(this.buildTheProject, this.hostTheReleases);
        this.interactWith(this.contributeNewCode, this.hostTheSourceCode);
        this.interactWith(this.raiseIssues, this.hostTheIssues);
        this.interactWith(this.raiseFeatureRequests, this.hostTheIssues);
        this.interactWith(this.performNewReleases, this.hostTheReleases);
        this.interactWith(this.offerSupportForDiagrams, this.provideConfigurableRepresentationDescriptions);
        this.interactWith(this.offerSupportForForms, this.provideConfigurableRepresentationDescriptions);
        this.interactWith(this.offerSupportForTrees, this.provideConfigurableRepresentationDescriptions);
        this.interactWith(this.provideConfigurableRepresentationDescriptions, this.configureRepresentationDescriptions);
        this.interactWith(this.hostTheReleases, this.downloadReleases);
        this.interactWith(this.downloadReleases, this.customizeTheCode);
        this.interactWith(this.customizeTheCode, this.buildCustomSiriusWebBasedApplications);
        this.interactWith(this.provideCompatibilityLayerForSiriusDesktop, this.reuseExistingOdesignConfigurations);
        this.interactWith(this.buildCustomSiriusWebBasedApplications, this.useSiriusWeBasedApplications);
        this.interactWith(this.offerCollaborativeModelEdition, this.useSiriusWeBasedApplications);
        this.interactWith(this.useSiriusWeBasedApplications, this.raiseFeatureRequests);

        this.realizedBy(this.offerSupportForDiagrams, this.siriusComponentsCollaborativeDiagrams);
        this.realizedBy(this.offerSupportForForms, this.siriusComponentsCollaborativeForms);
        this.realizedBy(this.offerSupportForTrees, this.siriusComponentsCollaborativeTrees);
        this.realizedBy(this.offerCollaborativeModelEdition, this.siriusComponentsCollaborative);
        this.realizedBy(this.useSiriusWeBasedApplications, this.siriusWebApplication);
    }

    private void linkComponents() {
        this.linkSiriusComponentsCollaborativeCode();
        this.linkSiriusComponentsRepresentationsCode();
    }

    private void linkSiriusComponentsCollaborativeCode() {
        this.addMany(this.siriusComponentsCollaborative, "requiredServices", List.of(this.requireRepresentationEventProcessor));
        this.addMany(this.siriusComponentsCollaborativeDiagrams, "providedServices", List.of(this.provideDiagramEventProcessor));
        this.addMany(this.siriusComponentsCollaborativeForms, "providedServices", List.of(this.provideFormEventProcessor));
        this.addMany(this.siriusComponentsCollaborativeTrees, "providedServices", List.of(this.provideTreeEventProcessor));

        this.addMany(this.siriusComponentsCollaborative, "packages",
                List.of(this.packageSiriusComponentsCollaborativeEditingContext, this.packageSiriusComponentsCollaborativeApi, this.packageSiriusComponentsCollaborativeRepresentations));
        this.addMany(this.siriusComponentsCollaborativeDiagrams, "packages", List.of(this.packageSiriusComponentsCollaborativeDiagrams, this.packageSiriusComponentsCollaborativeDiagramsApi));
        this.addMany(this.siriusComponentsCollaborativeForms, "packages", List.of(this.packageSiriusComponentsCollaborativeForms, this.packageSiriusComponentsCollaborativeFormsApi));
        this.addMany(this.siriusComponentsCollaborativeTrees, "packages", List.of(this.packageSiriusComponentsCollaborativeTrees, this.packageSiriusComponentsCollaborativeTreesApi));

        this.contract(this.requireRepresentationEventProcessor, this.iRepresentationEventProcessor);
        this.contract(this.provideDiagramEventProcessor, this.iDiagramEventProcessor);
        this.contract(this.provideFormEventProcessor, this.iFormEventProcessor);
        this.contract(this.provideTreeEventProcessor, this.iTreeEventProcessor);

        this.addMany(this.packageSiriusComponentsCollaborativeEditingContext, "types", List.of(this.editingContextEventProcessor));
        this.addMany(this.packageSiriusComponentsCollaborativeApi, "types",
                List.of(this.iRepresentationEventProcessor, this.iEditingContextEventProcessor, this.iEditingContextEventProcessorFactory, this.iEditingContextEventProcessorRegistry,
                        this.iEditingContextEventHandler, this.changeDescription, this.iSubscriptionManager, this.iSubscriptionManagerFactory, this.iRepresentationSearchService,
                        this.iRepresentationPersistenceService));
        this.addMany(this.packageSiriusComponentsCollaborativeRepresentations, "types", List.of(this.subscriptionManager));
        this.addMany(this.packageSiriusComponentsCollaborativeDiagramsApi, "types", List.of(this.iDiagramEventProcessor));
        this.addMany(this.packageSiriusComponentsCollaborativeDiagrams, "types", List.of(this.diagramEventProcessor));
        this.addMany(this.packageSiriusComponentsCollaborativeFormsApi, "types", List.of(this.iFormEventProcessor));
        this.addMany(this.packageSiriusComponentsCollaborativeForms, "types", List.of(this.formEventProcessor));
        this.addMany(this.packageSiriusComponentsCollaborativeTreesApi, "types", List.of(this.iTreeEventProcessor));
        this.addMany(this.packageSiriusComponentsCollaborativeTrees, "types", List.of(this.treeEventProcessor));

        this.addMany(this.iRepresentationEventProcessor, "operations", List.of(this.operationIRepresentationEventProcessorGetRepresentation));
        this.addMany(this.iRepresentationEventProcessor, "operations", List.of(this.operationIRepresentationEventProcessorRefresh));

        this.addMany(this.iRepresentation, "operations", List.of(this.operationIRepresentationGetId));
        this.addMany(this.iRepresentation, "operations", List.of(this.operationIRepresentationGetKind));
        this.addMany(this.iRepresentation, "operations", List.of(this.operationIRepresentationGetLabel));
        this.addMany(this.iRepresentation, "operations", List.of(this.operationIRepresentationGetDescriptionId));

        this.addMany(this.iRepresentationDescription, "operations", List.of(this.operationIRepresentationDescriptionGetId));
        this.addMany(this.iRepresentationDescription, "operations", List.of(this.operationIRepresentationDescriptionGetLabel));

        this.addMany(this.iDiagramEventProcessor, "extends", List.of(this.iRepresentationEventProcessor));
        this.addMany(this.iFormEventProcessor, "extends", List.of(this.iRepresentationEventProcessor));
        this.addMany(this.iTreeEventProcessor, "extends", List.of(this.iRepresentationEventProcessor));

        this.addMany(this.diagramEventProcessor, "implements", List.of(this.iDiagramEventProcessor));
        this.reference(this.diagramEventProcessor, "currentDiagram", false, this.diagram);
        this.addMany(this.formEventProcessor, "implements", List.of(this.iFormEventProcessor));
        this.reference(this.formEventProcessor, "currentForm", false, this.form);
        this.addMany(this.treeEventProcessor, "implements", List.of(this.iTreeEventProcessor));
        this.reference(this.treeEventProcessor, "currentTree", false, this.tree);

        this.addMany(this.subscriptionManager, "implements", List.of(this.iSubscriptionManager));
        this.addMany(this.subscriptionManager, "attributes", List.of(this.subscriptionManagerSubscriptionCount));
        this.addMany(this.subscriptionManager, "operations",
                List.of(this.subscriptionManagerGetFlux, this.subscriptionManagerCanBeDisposed, this.subscriptionManagerIsEmpty, this.subscriptionManagerDispose));
    }

    private void linkSiriusComponentsRepresentationsCode() {
        this.addMany(this.siriusComponentsRepresentations, "packages", List.of(this.packageSiriusComponentsRepresentations));
        this.addMany(this.siriusComponentsDiagrams, "packages", List.of(this.packageSiriusComponentsDiagrams, this.packageSiriusComponentsDiagramsDescription));
        this.addMany(this.siriusComponentsForms, "packages", List.of(this.packageSiriusComponentsForms, this.packageSiriusComponentsFormsDescription));
        this.addMany(this.siriusComponentsTrees, "packages", List.of(this.packageSiriusComponentsTrees, this.packageSiriusComponentsTreesDescription));

        this.addMany(this.packageSiriusComponentsRepresentations, "types", List.of(this.iRepresentationDescription, this.iRepresentation));
        this.addMany(this.packageSiriusComponentsDiagrams, "types", List.of(this.diagram, this.node, this.label, this.edge, this.size, this.position));
        this.addMany(this.packageSiriusComponentsDiagramsDescription, "types", List.of(this.diagramDescription, this.nodeDescription, this.labelDescription, this.edgeDescription));
        this.addMany(this.packageSiriusComponentsForms, "types", List.of(this.form, this.page, this.group, this.abstractWidget));
        this.addMany(this.packageSiriusComponentsFormsDescription, "types", List.of(this.formDescription, this.pageDescription, this.groupDescription));
        this.addMany(this.packageSiriusComponentsTrees, "types", List.of(this.tree, this.treeItem));
        this.addMany(this.packageSiriusComponentsTreesDescription, "types", List.of(this.treeDescription));

        this.addMany(this.diagram, "implements", List.of(this.iRepresentation));
        this.addMany(this.diagram, "attributes", List.of(this.diagramId));
        this.addMany(this.node, "attributes", List.of(this.nodeId, this.nodeType, this.nodeTargetObjectId, this.nodeTargetObjectKind, this.nodeTargetObjectLabel));
        this.addMany(this.label, "attributes", List.of(this.labelId, this.labelType, this.labelText));
        this.addMany(this.edge, "attributes", List.of(this.edgeId, this.edgeType, this.edgeTargetObjectId, this.edgeTargetObjectKind, this.edgeTargetObjectLabel));
        this.addMany(this.size, "attributes", List.of(this.width, this.height));
        this.addMany(this.position, "attributes", List.of(this.x, this.y));
        this.reference(this.diagram, "nodes", true, this.node);
        this.reference(this.diagram, "edges", true, this.edge);
        this.reference(this.node, "label", false, this.label);
        this.reference(this.node, "size", false, this.size);
        this.reference(this.node, "position", false, this.position);
        this.reference(this.edge, "beginLabel", false, this.label);
        this.reference(this.edge, "centerLabel", false, this.label);
        this.reference(this.edge, "endLabel", false, this.label);

        this.addMany(this.form, "implements", List.of(this.iRepresentation));
        this.reference(this.form, "pages", true, this.page);
        this.reference(this.page, "groups", true, this.group);
        this.reference(this.group, "widgets", true, this.abstractWidget);

        this.addMany(this.tree, "implements", List.of(this.iRepresentation));
        this.reference(this.tree, "treeItems", true, this.treeItem);

        this.addMany(this.diagramDescription, "implements", List.of(this.iRepresentationDescription));
        this.reference(this.diagramDescription, "nodeDescriptions", true, this.nodeDescription);
        this.reference(this.nodeDescription, "labelDescription", true, this.labelDescription);
        this.reference(this.diagramDescription, "edgeDescriptions", true, this.edgeDescription);

        this.addMany(this.formDescription, "implements", List.of(this.iRepresentationDescription));
        this.addMany(this.treeDescription, "implements", List.of(this.iRepresentationDescription));
    }

    private EClass eClass(String domainType) {
        int separatorIndex = domainType.indexOf("::");
        String domain = domainType.substring(0, separatorIndex);
        String type = domainType.substring(separatorIndex + "::".length());

        // @formatter:off
        return this.ePackageRegistry.values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .filter(ePackage -> domain.equals(ePackage.getName()))
                .map(ePackage -> ePackage.getEClassifier(type))
                .filter(EClass.class::isInstance)
                .map(EClass.class::cast)
                .findFirst()
                .orElse(null);
        // @formatter:on
    }

    private void addMany(EObject parentEObject, String featureName, List<EObject> childEObjects) {
        var value = parentEObject.eGet(parentEObject.eClass().getEStructuralFeature(featureName));
        if (value instanceof Collection<?>) {
            var collection = (Collection<EObject>) value;
            collection.addAll(childEObjects);
        }
    }

    private void set(EObject eObject, String featureName, Object value) {
        eObject.eSet(eObject.eClass().getEStructuralFeature(featureName), value);
    }

    private EObject operationalEntity(String name) {
        var operationalEntityEClass = this.eClass("papaya_operational_analysis::OperationalEntity");
        var operationalEntity = operationalEntityEClass.getEPackage().getEFactoryInstance().create(operationalEntityEClass);
        operationalEntity.eSet(operationalEntityEClass.getEStructuralFeature("name"), name);
        return operationalEntity;
    }

    private EObject operationalPerimeter(String name) {
        var operationalPerimeterEClass = this.eClass("papaya_operational_analysis::OperationalPerimeter");
        var operationalPerimeter = operationalPerimeterEClass.getEPackage().getEFactoryInstance().create(operationalPerimeterEClass);
        operationalPerimeter.eSet(operationalPerimeterEClass.getEStructuralFeature("name"), name);
        return operationalPerimeter;
    }

    private EObject operationalActor(String name) {
        var operationalActorEClass = this.eClass("papaya_operational_analysis::OperationalActor");
        var operationalActor = operationalActorEClass.getEPackage().getEFactoryInstance().create(operationalActorEClass);
        operationalActor.eSet(operationalActorEClass.getEStructuralFeature("name"), name);
        return operationalActor;
    }

    private EObject operationalActivity(String name) {
        var operationalActivityEClass = this.eClass("papaya_operational_analysis::OperationalActivity");
        var operationalActivity = operationalActivityEClass.getEPackage().getEFactoryInstance().create(operationalActivityEClass);
        operationalActivity.eSet(operationalActivityEClass.getEStructuralFeature("name"), name);
        return operationalActivity;
    }

    private void interactWith(EObject sourceActivity, EObject targetActivity) {
        var interactionEClass = this.eClass("papaya_operational_analysis::Interaction");
        var interaction = interactionEClass.getEPackage().getEFactoryInstance().create(interactionEClass);
        interaction.eSet(interactionEClass.getEStructuralFeature("target"), targetActivity);
        this.addMany(sourceActivity, "interactions", List.of(interaction));
    }

    private EObject component(String name) {
        var componentEClass = this.eClass("papaya_logical_architecture::Component");
        var component = componentEClass.getEPackage().getEFactoryInstance().create(componentEClass);
        component.eSet(componentEClass.getEStructuralFeature("name"), name);
        return component;
    }

    private EObject componentPort(String name, String protocol) {
        var componentPortEClass = this.eClass("papaya_logical_architecture::ComponentPort");
        var component = componentPortEClass.getEPackage().getEFactoryInstance().create(componentPortEClass);
        component.eSet(componentPortEClass.getEStructuralFeature("name"), name);
        component.eSet(componentPortEClass.getEStructuralFeature("protocol"), name);
        return component;
    }

    private EObject componentExchange(String name, String role, String description) {
        var componentExchangeEClass = this.eClass("papaya_logical_architecture::ComponentExchange");
        var component = componentExchangeEClass.getEPackage().getEFactoryInstance().create(componentExchangeEClass);
        component.eSet(componentExchangeEClass.getEStructuralFeature("name"), name);
        component.eSet(componentExchangeEClass.getEStructuralFeature("role"), role);
        component.eSet(componentExchangeEClass.getEStructuralFeature("description"), description);
        return component;
    }

    private EObject providedService() {
        var providedServiceEClass = this.eClass("papaya_logical_architecture::ProvidedService");
        var providedService = providedServiceEClass.getEPackage().getEFactoryInstance().create(providedServiceEClass);
        return providedService;
    }

    private EObject requiredService() {
        var requiredServiceEClass = this.eClass("papaya_logical_architecture::RequiredService");
        var requiredService = requiredServiceEClass.getEPackage().getEFactoryInstance().create(requiredServiceEClass);
        return requiredService;
    }

    private void contract(EObject requiredOrProvidedService, EObject interfaceEObject) {
        requiredOrProvidedService.eSet(requiredOrProvidedService.eClass().getEStructuralFeature("contract"), interfaceEObject);
    }

    private EObject packageEObject(String name) {
        var packageEClass = this.eClass("papaya_logical_architecture::Package");
        var packageEObject = packageEClass.getEPackage().getEFactoryInstance().create(packageEClass);
        packageEObject.eSet(packageEClass.getEStructuralFeature("name"), name);
        return packageEObject;
    }

    private EObject interfaceEObject(String name) {
        var interfaceEClass = this.eClass("papaya_logical_architecture::Interface");
        var interfaceEObject = interfaceEClass.getEPackage().getEFactoryInstance().create(interfaceEClass);
        interfaceEObject.eSet(interfaceEClass.getEStructuralFeature("name"), name);
        return interfaceEObject;
    }

    private EObject classEObject(String name) {
        var classEClass = this.eClass("papaya_logical_architecture::Class");
        var classEObject = classEClass.getEPackage().getEFactoryInstance().create(classEClass);
        classEObject.eSet(classEClass.getEStructuralFeature("name"), name);
        return classEObject;
    }

    private EObject attributeEObject(String name, EObject type) {
        var attributeEClass = this.eClass("papaya_logical_architecture::Attribute");
        var attribute = attributeEClass.getEPackage().getEFactoryInstance().create(attributeEClass);
        attribute.eSet(attributeEClass.getEStructuralFeature("name"), name);
        attribute.eSet(attributeEClass.getEStructuralFeature("type"), type);
        return attribute;
    }

    private EObject operation(String name) {
        var operationEClass = this.eClass("papaya_logical_architecture::Operation");
        var operation = operationEClass.getEPackage().getEFactoryInstance().create(operationEClass);
        operation.eSet(operationEClass.getEStructuralFeature("name"), name);
        return operation;
    }

    private EObject dataType(String name) {
        var datatypeEClass = this.eClass("papaya_logical_architecture::DataType");
        var datatype = datatypeEClass.getEPackage().getEFactoryInstance().create(datatypeEClass);
        datatype.eSet(datatypeEClass.getEStructuralFeature("name"), name);
        return datatype;
    }

    private void realizedBy(EObject sourceActivity, EObject targetComponent) {
        sourceActivity.eSet(sourceActivity.eClass().getEStructuralFeature("realizedBy"), targetComponent);
    }

    private void reference(EObject parent, String name, boolean isMany, EObject type) {
        var referenceEClass = this.eClass("papaya_logical_architecture::Reference");
        var referenceEObject = referenceEClass.getEPackage().getEFactoryInstance().create(referenceEClass);
        referenceEObject.eSet(referenceEClass.getEStructuralFeature("name"), name);
        referenceEObject.eSet(referenceEClass.getEStructuralFeature("many"), isMany);
        referenceEObject.eSet(referenceEClass.getEStructuralFeature("type"), type);

        this.addMany(parent, "references", List.of(referenceEObject));
    }

    public EObject drop(EObject self, Node selectedNode, IDiagramContext diagramContext,
            Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes) {
        // @formatter:off
        var parentElementId = Optional.ofNullable(selectedNode)
                .map(Node::getTargetObjectId)
                .orElse(diagramContext.getDiagram().getId());

        var targetObjectId = this.objectService.getId(self);

        var domainType = self.eClass().getEPackage().getName() + "::" + self.eClass().getName();
        var nodeDescriptionName = "Node " + domainType;

        var descriptionId = convertedNodes.entrySet().stream()
                .filter(entry -> entry.getKey().getName().equals(nodeDescriptionName))
                .findFirst()
                .map(entry -> entry.getValue().getId())
                .orElse(null);

        if (parentElementId != null && targetObjectId != null && descriptionId != null) {
            var viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                    .parentElementId(parentElementId)
                    .targetObjectId(targetObjectId)
                    .descriptionId(descriptionId)
                    .containmentKind(NodeContainmentKind.CHILD_NODE)
                    .build();
            diagramContext.getViewCreationRequests().add(viewCreationRequest);
        }
        // @formatter:on

        return self;
    }

}
