/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.web.sample.configuration;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * Java Service for the test view.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class TestService {
    private EPackage ePackage;

    private EFactory eFactory;

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

    private EObject siriusWebApplication;

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

    private EObject packageSiriusComponentsCollaborativeApi;

    private EObject packageSiriusComponentsRepresentations;

    private EObject iRepresentationEventProcessor;

    private EObject iRepresentation;

    private EObject iRepresentationDescription;

    private EObject operationIRepresentationEventProcessorGetRepresentation;

    private EObject operationIRepresentationEventProcessorRefresh;

    private EObject operationIRepresentationGetId;

    private EObject operationIRepresentationGetLabel;

    private EObject operationIRepresentationGetKind;

    private EObject operationIRepresentationGetDescriptionId;

    private EObject operationIRepresentationDescriptionGetId;

    private EObject operationIRepresentationDescriptionGetLabel;

    public EObject initialize(EObject eObject) {
        this.ePackage = eObject.eClass().getEPackage();
        this.eFactory = this.ePackage.getEFactoryInstance();

        this.createOperationalObjects();
        this.createComponents();
        this.createCode();

        this.linkOperationalObjects();
        this.linkComponents();

        this.addChildren(eObject, "operationalEntities", List.of(this.eclipseFoundation));
        this.addChildren(eObject, "operationalActors", List.of(this.specifier, this.endUser, this.webmaster));
        this.addChildren(eObject, "components", List.of(this.siriusWebApplication, this.siriusComponentsCollaborative, this.siriusComponentsCollaborativeDiagrams, this.siriusComponentsDiagrams,
                this.siriusComponentsCollaborativeForms, this.siriusComponentsForms, this.siriusComponentsCollaborativeTrees, this.siriusComponentsTrees));

        return eObject;
    }

    private void createOperationalObjects() {
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
        this.packageSiriusComponentsCollaborativeApi = this.packageEObject("org.eclipse.sirius.components.collaborative.api");
        this.packageSiriusComponentsRepresentations = this.packageEObject("org.eclipse.sirius.components.representations");

        this.iRepresentationEventProcessor = this.interfaceEObject("IRepresentationEventProcessor");
        this.iRepresentation = this.interfaceEObject("IRepresentation");
        this.iRepresentationDescription = this.interfaceEObject("IRepresentationDescription");

        this.operationIRepresentationEventProcessorGetRepresentation = this.operation("getRepresentation");
        this.operationIRepresentationEventProcessorRefresh = this.operation("refresh");

        this.operationIRepresentationGetId = this.operation("getId");
        this.operationIRepresentationGetLabel = this.operation("getLabel");
        this.operationIRepresentationGetKind = this.operation("getKind");
        this.operationIRepresentationGetDescriptionId = this.operation("getDescriptionId");

        this.operationIRepresentationDescriptionGetId = this.operation("getId");
        this.operationIRepresentationDescriptionGetLabel = this.operation("getLabel");
    }

    private void linkOperationalObjects() {
        this.addChildren(this.eclipseFoundation, "operationalPerimeters", List.of(this.githubOrganization, this.siriusWeb));
        this.addChildren(this.eclipseFoundation, "operationalActors", List.of(this.contributor));
        this.addChildren(this.githubOrganization, "operationalActivities", List.of(this.hostTheSourceCode, this.hostTheIssues, this.buildTheProject, this.runTheTests, this.hostTheReleases));
        this.addChildren(this.siriusWeb, "operationalActivities", List.of(this.provideConfigurableRepresentationDescriptions, this.offerSupportForDiagrams, this.offerSupportForForms,
                this.offerSupportForTrees, this.provideCompatibilityLayerForSiriusDesktop, this.offerCollaborativeModelEdition));
        this.addChildren(this.contributor, "operationalActivities", List.of(this.managesTheProject, this.contributeNewCode, this.raiseIssues, this.performNewReleases));
        this.addChildren(this.specifier, "operationalActivities",
                List.of(this.configureRepresentationDescriptions, this.reuseExistingOdesignConfigurations, this.downloadReleases, this.customizeTheCode, this.buildCustomSiriusWebBasedApplications));
        this.addChildren(this.endUser, "operationalActivities", List.of(this.useSiriusWeBasedApplications, this.raiseFeatureRequests));

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
        this.addChildren(this.siriusComponentsCollaborative, "requiredServices", List.of(this.requireRepresentationEventProcessor));
        this.addChildren(this.siriusComponentsCollaborativeDiagrams, "providedServices", List.of(this.provideDiagramEventProcessor));
        this.addChildren(this.siriusComponentsCollaborativeForms, "providedServices", List.of(this.provideFormEventProcessor));
        this.addChildren(this.siriusComponentsCollaborativeTrees, "providedServices", List.of(this.provideTreeEventProcessor));

        this.addChildren(this.siriusComponentsRepresentations, "packages", List.of(this.packageSiriusComponentsRepresentations));
        this.addChildren(this.siriusComponentsCollaborative, "packages", List.of(this.packageSiriusComponentsCollaborativeApi));

        this.contract(this.requireRepresentationEventProcessor, this.iRepresentationEventProcessor);

        this.addChildren(this.packageSiriusComponentsCollaborativeApi, "types", List.of(this.iRepresentationEventProcessor));
        this.addChildren(this.packageSiriusComponentsRepresentations, "types", List.of(this.iRepresentationDescription, this.iRepresentation));

        this.addChildren(this.iRepresentationEventProcessor, "operations", List.of(this.operationIRepresentationEventProcessorGetRepresentation));
        this.addChildren(this.iRepresentationEventProcessor, "operations", List.of(this.operationIRepresentationEventProcessorRefresh));

        this.addChildren(this.iRepresentation, "operations", List.of(this.operationIRepresentationGetId));
        this.addChildren(this.iRepresentation, "operations", List.of(this.operationIRepresentationGetKind));
        this.addChildren(this.iRepresentation, "operations", List.of(this.operationIRepresentationGetLabel));
        this.addChildren(this.iRepresentation, "operations", List.of(this.operationIRepresentationGetDescriptionId));

        this.addChildren(this.iRepresentationDescription, "operations", List.of(this.operationIRepresentationDescriptionGetId));
        this.addChildren(this.iRepresentationDescription, "operations", List.of(this.operationIRepresentationDescriptionGetLabel));
    }

    private EClass eClass(String name) {
        return Optional.ofNullable(this.ePackage.getEClassifier(name)).filter(EClass.class::isInstance).map(EClass.class::cast).orElse(null);
    }

    private void addChildren(EObject parentEObject, String featureName, List<EObject> childEObjects) {
        var value = parentEObject.eGet(parentEObject.eClass().getEStructuralFeature(featureName));
        if (value instanceof Collection<?>) {
            var collection = (Collection<EObject>) value;
            collection.addAll(childEObjects);
        }
    }

    private EObject operationalEntity(String name) {
        var operationalEntityEClass = this.eClass("OperationalEntity");
        var operationalEntity = this.eFactory.create(operationalEntityEClass);
        operationalEntity.eSet(operationalEntityEClass.getEStructuralFeature("name"), name);
        return operationalEntity;
    }

    private EObject operationalPerimeter(String name) {
        var operationalPerimeterEClass = this.eClass("OperationalPerimeter");
        var operationalPerimeter = this.eFactory.create(operationalPerimeterEClass);
        operationalPerimeter.eSet(operationalPerimeterEClass.getEStructuralFeature("name"), name);
        return operationalPerimeter;
    }

    private EObject operationalActor(String name) {
        var operationalActorEClass = this.eClass("OperationalActor");
        var operationalActor = this.eFactory.create(operationalActorEClass);
        operationalActor.eSet(operationalActorEClass.getEStructuralFeature("name"), name);
        return operationalActor;
    }

    private EObject operationalActivity(String name) {
        var operationalActivityEClass = this.eClass("OperationalActivity");
        var operationalActivity = this.eFactory.create(operationalActivityEClass);
        operationalActivity.eSet(operationalActivityEClass.getEStructuralFeature("name"), name);
        return operationalActivity;
    }

    private void interactWith(EObject sourceActivity, EObject targetActivity) {
        var interactionEClass = this.eClass("Interaction");
        var interaction = this.eFactory.create(interactionEClass);
        interaction.eSet(interactionEClass.getEStructuralFeature("target"), targetActivity);
        this.addChildren(sourceActivity, "interactions", List.of(interaction));
    }

    private EObject component(String name) {
        var componentEClass = this.eClass("Component");
        var component = this.eFactory.create(componentEClass);
        component.eSet(componentEClass.getEStructuralFeature("name"), name);
        return component;
    }

    private EObject providedService() {
        var providedServiceEClass = this.eClass("ProvidedService");
        var providedService = this.eFactory.create(providedServiceEClass);
        return providedService;
    }

    private EObject requiredService() {
        var requiredServiceEClass = this.eClass("RequiredService");
        var requiredService = this.eFactory.create(requiredServiceEClass);
        return requiredService;
    }

    private void contract(EObject requiredOrProvidedService, EObject interfaceEObject) {
        requiredOrProvidedService.eSet(requiredOrProvidedService.eClass().getEStructuralFeature("contract"), interfaceEObject);
    }

    private EObject packageEObject(String name) {
        var packageEClass = this.eClass("Package");
        var packageEObject = this.eFactory.create(packageEClass);
        packageEObject.eSet(packageEClass.getEStructuralFeature("name"), name);
        return packageEObject;
    }

    private EObject interfaceEObject(String name) {
        var interfaceEClass = this.eClass("Interface");
        var interfaceEObject = this.eFactory.create(interfaceEClass);
        interfaceEObject.eSet(interfaceEClass.getEStructuralFeature("name"), name);
        return interfaceEObject;
    }

    private EObject operation(String name) {
        var operationEClass = this.eClass("Operation");
        var operation = this.eFactory.create(operationEClass);
        operation.eSet(operationEClass.getEStructuralFeature("name"), name);
        return operation;
    }

    private void realizedBy(EObject sourceActivity, EObject targetComponent) {
        sourceActivity.eSet(sourceActivity.eClass().getEStructuralFeature("realizedBy"), targetComponent);
    }

}
