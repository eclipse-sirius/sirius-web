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

package org.eclipse.sirius.web.papaya.projecttemplates;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.papaya.ContainingLink;
import org.eclipse.sirius.components.papaya.Folder;
import org.eclipse.sirius.components.papaya.ModelElement;
import org.eclipse.sirius.components.papaya.OperationalEntity;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.papaya.ReferencingLink;
import org.eclipse.sirius.web.papaya.factories.services.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.services.api.IObjectFactory;

/**
 * Used to create the objects of the operational analysis.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class OperationalAnalysisObjectFactory implements IObjectFactory {

    private final Project project;

    public OperationalAnalysisObjectFactory(Project project) {
        this.project = Objects.requireNonNull(project);
    }

    @Override
    public void create(IEMFEditingContext editingContext) {
        var operationalAnalysisFolder = PapayaFactory.eINSTANCE.createFolder();
        operationalAnalysisFolder.setName("Operational Analysis");

        this.createOperationalEntities(operationalAnalysisFolder);
        this.createOperationalCapabilities(operationalAnalysisFolder);

        project.getFolders().add(operationalAnalysisFolder);
    }

    private void createOperationalEntities(Folder operationalAnalysisFolder) {
        var operationalActorsFolder = PapayaFactory.eINSTANCE.createFolder();
        operationalActorsFolder.setName("Actors");
        operationalAnalysisFolder.getFolders().add(operationalActorsFolder);

        var eclipseFoundation = PapayaFactory.eINSTANCE.createOperationalEntity();
        eclipseFoundation.setName("Eclipse Foundation");

        var siriusWeb = this.siriusWeb();
        var sysON = this.sysON();
        var systemEngineeringCompany = this.systemEngineeringCompany();

        this.addReferencingLink(eclipseFoundation, "contains", siriusWeb);
        this.addReferencingLink(eclipseFoundation, "contains", sysON);
        this.addReferencingLink(eclipseFoundation, "contains", systemEngineeringCompany);

        operationalActorsFolder.getElements().addAll(List.of(eclipseFoundation, siriusWeb, sysON, systemEngineeringCompany));
    }

    private OperationalEntity siriusWeb() {
        var siriusWeb = PapayaFactory.eINSTANCE.createOperationalEntity();
        siriusWeb.setName("Sirius Web");

        var architect = PapayaFactory.eINSTANCE.createOperationalActor();
        architect.setName("Sirius Web Architect");
        architect.setType("human");
        this.addContainingLink(siriusWeb, "contains", architect);

        var developer = PapayaFactory.eINSTANCE.createOperationalActor();
        developer.setName("Sirius Web Developer");
        developer.setType("human");
        this.addContainingLink(siriusWeb, "contains", developer);

        var releaseEngineer = PapayaFactory.eINSTANCE.createOperationalActor();
        releaseEngineer.setName("Sirius Web Release Engineer");
        releaseEngineer.setType("human");
        this.addContainingLink(siriusWeb, "contains", releaseEngineer);

        var devopsEngineer = PapayaFactory.eINSTANCE.createOperationalActor();
        devopsEngineer.setName("Sirius Web Devops Engineer");
        devopsEngineer.setType("human");
        this.addContainingLink(siriusWeb, "contains", devopsEngineer);

        var qualityEngineer = PapayaFactory.eINSTANCE.createOperationalActor();
        qualityEngineer.setName("Sirius Web Quality Engineer");
        qualityEngineer.setType("human");
        this.addContainingLink(siriusWeb, "contains", qualityEngineer);

        return siriusWeb;
    }

    private OperationalEntity sysON() {
        var sysON = PapayaFactory.eINSTANCE.createOperationalEntity();
        sysON.setName("SysON");

        var sysONSpecifier = PapayaFactory.eINSTANCE.createOperationalActor();
        sysONSpecifier.setName("SysON Specifier");
        sysONSpecifier.setType("human");

        this.addContainingLink(sysON, "contains", sysONSpecifier);

        return sysON;
    }

    private OperationalEntity systemEngineeringCompany() {
        var systemEngineeringCompany = PapayaFactory.eINSTANCE.createOperationalEntity();
        systemEngineeringCompany.setName("System Engineering Company");

        var systemEngineer = PapayaFactory.eINSTANCE.createOperationalActor();
        systemEngineer.setName("System Engineer");
        systemEngineer.setType("human");

        this.addContainingLink(systemEngineeringCompany, "contains", systemEngineer);

        return systemEngineeringCompany;
    }

    private void addContainingLink(ModelElement source, String kind, ModelElement target) {
        ContainingLink containingLink = PapayaFactory.eINSTANCE.createContainingLink();
        containingLink.setKind(kind);
        containingLink.setTarget(target);
        source.getLinks().add(containingLink);
    }

    private void addReferencingLink(ModelElement source, String kind, ModelElement target) {
        ReferencingLink referencingLink = PapayaFactory.eINSTANCE.createReferencingLink();
        referencingLink.setKind(kind);
        referencingLink.setTarget(target);
        source.getLinks().add(referencingLink);
    }

    private void createOperationalCapabilities(Folder operationalAnalysisFolder) {
        var operationalCapabilitiesFolder = PapayaFactory.eINSTANCE.createFolder();
        operationalCapabilitiesFolder.setName("Capabilities");
        operationalAnalysisFolder.getFolders().add(operationalCapabilitiesFolder);

        Folder interactionCapabilities = this.createInteractionCapabilities();
        Folder configurationCapabilities = this.createConfigurabilityCapabilities();
        Folder extensibilityCapabilities = this.createExtensibilityCapabilities();
        Folder integrationCapabilities = this.createIntegrationCapabilities();
        Folder developmentLifecycleCapabilities = this.createDevelopmentLifecycleCapabilities();
        Folder maintainabilityCapability = this.createMaintainabilityCapability();
        Folder qualityCapabilities = this.qualityCapabilities();
        Folder operationCapabilities = this.operationCapabilities();

        operationalCapabilitiesFolder.getFolders().addAll(List.of(
                interactionCapabilities,
                configurationCapabilities,
                extensibilityCapabilities,
                integrationCapabilities,
                developmentLifecycleCapabilities,
                maintainabilityCapability,
                qualityCapabilities,
                operationCapabilities
        ));
    }

    private Folder createInteractionCapabilities() {
        var interactionCapabilities = PapayaFactory.eINSTANCE.createFolder();
        interactionCapabilities.setName("Interaction");

        var domainSpecificApplicationCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        domainSpecificApplicationCapability.setName("Domain specific tooling");
        domainSpecificApplicationCapability.setDescription("End users shall be able to have an application entirely dedicated to their business concerns");

        var createEntitiesCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        createEntitiesCapability.setName("Semantic data manipulation");
        createEntitiesCapability.setDescription("End users shall be able to instantiate the entities defined to manipulate semantic data");

        var createRepresentationsCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        createRepresentationsCapability.setName("Representation manipulation");
        createRepresentationsCapability.setDescription("End users shall be able to create and interact with representations");

        var createProjectsCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        createProjectsCapability.setName("Project support");
        createProjectsCapability.setDescription("End users shall be able to isolate their semantic data and representations in projects");

        var consistentSemanticDataCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        consistentSemanticDataCapability.setName("Semantic data consistency");
        consistentSemanticDataCapability.setDescription("The semantic data manipulated by Sirius Web shall be in a consistent state at all time");

        var representationSynchronizedWithSemanticDataCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        representationSynchronizedWithSemanticDataCapability.setName("Synchronization between data and representation");
        representationSynchronizedWithSemanticDataCapability.setDescription("Sirius Web shall ensure that representations manipulated by end users are synchronized with the state of the semantic data");

        var representationSynchronizedWithSubsetOfSemanticDataCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        representationSynchronizedWithSubsetOfSemanticDataCapability.setName("Partial synchronization");
        representationSynchronizedWithSubsetOfSemanticDataCapability.setDescription("End users shall be able to manipulate representations synchronized with only a subset of the semantic data");

        var representationSynchronizedCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        representationSynchronizedCapability.setName("Synchronization between representations");
        representationSynchronizedCapability.setDescription("Changes visible in one representation shall be visible in all the other impacted representations simultaneously");

        interactionCapabilities.getElements().addAll(List.of(
                domainSpecificApplicationCapability,
                createEntitiesCapability,
                createRepresentationsCapability,
                createProjectsCapability,
                consistentSemanticDataCapability,
                representationSynchronizedWithSemanticDataCapability,
                representationSynchronizedWithSubsetOfSemanticDataCapability,
                representationSynchronizedCapability
        ));

        return interactionCapabilities;
    }

    private Folder createConfigurabilityCapabilities() {
        var configurationCapabilities = PapayaFactory.eINSTANCE.createFolder();
        configurationCapabilities.setName("Configuration");

        var domainDefinitionCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        domainDefinitionCapability.setName("Domain definition");
        domainDefinitionCapability.setDescription("Specifiers shall be able to define business domains");

        var representationDescriptionDefinitionCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        representationDescriptionDefinitionCapability.setName("Representation description definition");
        representationDescriptionDefinitionCapability.setDescription("Specifiers shall be able to define representation descriptions");

        var programmaticRepresentationDescriptionDefinitionCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        programmaticRepresentationDescriptionDefinitionCapability.setName("Programmatic representation description definition");
        programmaticRepresentationDescriptionDefinitionCapability.setDescription("Specifiers shall be able to define representation descriptions using a programmatic API");

        var lowCodeRepresentationDescriptionDefinitionCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        lowCodeRepresentationDescriptionDefinitionCapability.setName("Low code representation description definition");
        lowCodeRepresentationDescriptionDefinitionCapability.setDescription("Specifiers shall be able to define representation descriptions using a declarative API and a lightweight expression language");

        var declarativeBehaviorDescriptionDefinitionCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        declarativeBehaviorDescriptionDefinitionCapability.setName("Declarative behavior description definition");
        declarativeBehaviorDescriptionDefinitionCapability.setDescription("Specifiers shall be able to define the behavior of their representation using a declarative API");

        var extremeConfigurabilityCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        extremeConfigurabilityCapability.setName("Extreme configurability");
        extremeConfigurabilityCapability.setDescription("Sirius Web should offer specifiers the ability to configure almost everything");

        configurationCapabilities.getElements().addAll(List.of(
                domainDefinitionCapability,
                representationDescriptionDefinitionCapability,
                programmaticRepresentationDescriptionDefinitionCapability,
                lowCodeRepresentationDescriptionDefinitionCapability,
                declarativeBehaviorDescriptionDefinitionCapability,
                extremeConfigurabilityCapability
        ));

        return configurationCapabilities;
    }

    private Folder createExtensibilityCapabilities() {
        var extensibilityCapabilities = PapayaFactory.eINSTANCE.createFolder();
        extensibilityCapabilities.setName("Extensibility");

        var backendServiceContributionCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        backendServiceContributionCapability.setName("Backend services contribution");
        backendServiceContributionCapability.setDescription("Specifiers shall be able to add backend services to change the behavior of the application");

        var graphqlAPIContributionCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        graphqlAPIContributionCapability.setName("GraphQL API contribution");
        graphqlAPIContributionCapability.setDescription("Specifiers shall be able to add new concepts and behavior to the GraphQL API");

        var frontendComponentsContributionCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        frontendComponentsContributionCapability.setName("Frontend components contribution");
        frontendComponentsContributionCapability.setDescription("Specifiers shall be able to add new components in the frontend");

        var backendLifecycleSynchronizationCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        backendLifecycleSynchronizationCapability.setName("Backend lifecycle synchronization");
        backendLifecycleSynchronizationCapability.setDescription("Specifiers shall be able to synchronize their code with the events of the lifecycle of the application");

        var hideRemoveFeaturesCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        hideRemoveFeaturesCapability.setName("Hide or remove existing features");
        hideRemoveFeaturesCapability.setDescription("Specifiers shall be able to hide or remove existing features to prevent end users from performing some operations");

        var facilitateExtensibilityCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        facilitateExtensibilityCapability.setName("Facilitate the extensibility");
        facilitateExtensibilityCapability.setDescription("Sirius Web should not try to prevent specifiers from extending or manipulating any part of the application");

        extensibilityCapabilities.getElements().addAll(List.of(
                backendServiceContributionCapability,
                graphqlAPIContributionCapability,
                frontendComponentsContributionCapability,
                backendLifecycleSynchronizationCapability,
                hideRemoveFeaturesCapability,
                facilitateExtensibilityCapability
        ));

        return extensibilityCapabilities;
    }

    private Folder createIntegrationCapabilities() {
        var integrationCapabilities = PapayaFactory.eINSTANCE.createFolder();
        integrationCapabilities.setName("Integration");

        var reuseDesktopCodeCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        reuseDesktopCodeCapability.setName("Reuse existing desktop code");
        reuseDesktopCodeCapability.setDescription("Specifiers shall be able to reuse existing pieces of code from Sirius Desktop based applications like existing metamodels based on Ecore");

        var reuseFrontendCodeCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        reuseFrontendCodeCapability.setName("Reuse existing frontend code");
        reuseFrontendCodeCapability.setDescription("Specifiers shall be able to reuse existing JavaScript frameworks in Sirius Web");

        var communicateWithWebServicesCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        communicateWithWebServicesCapability.setName("Communicate with existing web services");
        communicateWithWebServicesCapability.setDescription("Specifiers shall be able to send requests to existing APIs to send or retrieve data");

        var importAndExportCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        importAndExportCapability.setName("Import and export");
        importAndExportCapability.setDescription("End users shall be able to import and export their data");

        var embedInApplicationCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        embedInApplicationCapability.setName("Embed in another application");
        embedInApplicationCapability.setDescription("Specifiers shall be able to embed parts of the Sirius Web backend and/or frontend in other applications");

        var reuseInAnotherEnvironmentCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        reuseInAnotherEnvironmentCapability.setName("Reuse in another environment");
        reuseInAnotherEnvironmentCapability.setDescription("Sirius Web shall be reusable in other technical environments");

        var brandingCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        brandingCapability.setName("Branding");
        brandingCapability.setDescription("Specifiers shall be able to change the branding of Sirius Web to provide another identity");

        var licensingCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        licensingCapability.setName("Licensing");
        licensingCapability.setDescription("The Sirius Web source code shall be licensed under a license compatible with the requirements of the Eclipse Foundation");

        var dependencyLicensingCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        dependencyLicensingCapability.setName("Dependency licensing");
        dependencyLicensingCapability.setDescription("Sirius Web shall only use dependencies with a license compatible with the requirements of the Eclipse Foundation");

        var limitedDependenciesCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        limitedDependenciesCapability.setName("Limited dependencies");
        limitedDependenciesCapability.setDescription("Sirius Web shall use a limited amount of dependencies to minimize issues");

        integrationCapabilities.getElements().addAll(List.of(
                reuseDesktopCodeCapability,
                reuseFrontendCodeCapability,
                communicateWithWebServicesCapability,
                importAndExportCapability,
                embedInApplicationCapability,
                reuseInAnotherEnvironmentCapability,
                brandingCapability,
                licensingCapability,
                dependencyLicensingCapability,
                licensingCapability
        ));

        return integrationCapabilities;
    }

    private Folder createDevelopmentLifecycleCapabilities() {
        var developmentLifecycleCapabilities = PapayaFactory.eINSTANCE.createFolder();
        developmentLifecycleCapabilities.setName("Development Lifecycle");

        var foreseeableScheduleCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        foreseeableScheduleCapability.setName("Foreseeable schedule");
        foreseeableScheduleCapability.setDescription("Contributors and specifiers shall know easily when new releases will be available");

        var consumableByDownstreamProjectCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        consumableByDownstreamProjectCapability.setName("Consumable by downstream projects");
        consumableByDownstreamProjectCapability.setDescription("Specifiers maintaining downstream applications shall have everything needed to consume new versions of Sirius Web");

        var integrateUpstreamContributionCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        integrateUpstreamContributionCapability.setName("Integrate upstream contributions");
        integrateUpstreamContributionCapability.setDescription("Specifiers maintaining downstream applications shall be able to contribute back features and bug fixes in Sirius Web");

        var iterativeProcessCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        iterativeProcessCapability.setName("Iterative process");
        iterativeProcessCapability.setDescription("Contributors shall integrate and release improvements made to Sirius Web frequently");

        var abilityToPivotCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        abilityToPivotCapability.setName("Ability to pivot the product");
        abilityToPivotCapability.setDescription("Contributors shall have the ability to change their mind on the direction of the product to consider new requirements");

        var changeScopeCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        changeScopeCapability.setName("Change the scope of a feature");
        changeScopeCapability.setDescription("Contributors shall have the ability to change the scope of a feature while it is being developed");

        var communicationCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        communicationCapability.setName("Communication with downstream projects");
        communicationCapability.setDescription("Specifiers shall have the relevant information necessary to anticipate upgrades of Sirius Web based applications");

        var functionalFeedbackCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        functionalFeedbackCapability.setName("Functional feedback");
        functionalFeedbackCapability.setDescription("Contributors and specifiers shall have the ability to contribute feedback on the functional behavior of features to be developed");

        var technicalFeedbackCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        technicalFeedbackCapability.setName("Technical feedback");
        technicalFeedbackCapability.setDescription("Contributors and specifiers shall have the ability to contribute feedback on the technical implementation of features to be developed");

        developmentLifecycleCapabilities.getElements().addAll(List.of(
                foreseeableScheduleCapability,
                consumableByDownstreamProjectCapability,
                integrateUpstreamContributionCapability,
                iterativeProcessCapability,
                abilityToPivotCapability,
                changeScopeCapability,
                communicationCapability,
                functionalFeedbackCapability,
                technicalFeedbackCapability
        ));

        return developmentLifecycleCapabilities;
    }

    private Folder createMaintainabilityCapability() {
        var maintainabilityCapabilities = PapayaFactory.eINSTANCE.createFolder();
        maintainabilityCapabilities.setName("Maintainability");

        var ubiquitousLanguageCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        ubiquitousLanguageCapability.setName("Ubiquitous language");
        ubiquitousLanguageCapability.setDescription("All parts of Sirius Web shall share a common vocabulary and coherent concepts");

        var maintainabilityCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        maintainabilityCapability.setName("Maintainability");
        maintainabilityCapability.setDescription("Contributors shall be able to update the project to add new features and fix issues");

        var matureTechnologiesCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        matureTechnologiesCapability.setName("Mature technologies");
        matureTechnologiesCapability.setDescription("Sirius Web shall be created using mature pieces of technologies");

        var evolutiveApplicationCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        evolutiveApplicationCapability.setName("Evolutive application");
        evolutiveApplicationCapability.setDescription("Contributors shall be able to make the project evolve in new directions");

        var documentedCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        documentedCapability.setName("Documented");
        documentedCapability.setDescription("Some documentation shall be available to help contributors, specifiers and end users");

        var openDevelopmentCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        openDevelopmentCapability.setName("Open development");
        openDevelopmentCapability.setDescription("Contributors of Sirius Web shall be able to be build and maintain Sirius Web with open source tools");

        maintainabilityCapabilities.getElements().addAll(List.of(
                ubiquitousLanguageCapability,
                maintainabilityCapability,
                matureTechnologiesCapability,
                evolutiveApplicationCapability,
                documentedCapability,
                openDevelopmentCapability
        ));

        return maintainabilityCapabilities;
    }

    private Folder qualityCapabilities() {
        var qualityCapabilities = PapayaFactory.eINSTANCE.createFolder();
        qualityCapabilities.setName("Quality");

        var bestPracticesCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        bestPracticesCapability.setName("Best practices");
        bestPracticesCapability.setDescription("The Sirius Web source code shall follow software development best practices");

        var consistencyCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        consistencyCapability.setName("Consistency");
        consistencyCapability.setDescription("All parts of Sirius Web shall embrace a consistent philosophy for their user interface, behavior APIs, code conventions, etc.");

        var testQualityCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        testQualityCapability.setName("Test quality");
        testQualityCapability.setDescription("Tests shall be treated exactly as regular pieces of code and maintained as such");

        var historyQualityCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        historyQualityCapability.setName("History quality");
        historyQualityCapability.setDescription("The revision history of the project shall be easy to understand");

        qualityCapabilities.getElements().addAll(List.of(
                bestPracticesCapability,
                consistencyCapability,
                testQualityCapability,
                historyQualityCapability
        ));

        return qualityCapabilities;
    }

    private Folder operationCapabilities() {
        var operationCapabilities = PapayaFactory.eINSTANCE.createFolder();
        operationCapabilities.setName("Operation");

        var operatingSystemCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        operatingSystemCapability.setName("Operating system");
        operatingSystemCapability.setDescription("Sirius Web shall be independent of any operating system and run with the same quality of linux, macOS or windows.");

        var networkCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        networkCapability.setName("Network");
        networkCapability.setDescription("Sirius Web shall be usable with an internet connexion");

        var executionEnvironmentCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        executionEnvironmentCapability.setName("Execution environment");
        executionEnvironmentCapability.setDescription("Sirius Web shall require the simplest execution environment possible to run.");

        var deploymentCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        deploymentCapability.setName("Ease of deployment");
        deploymentCapability.setDescription("Sirius Web shall be easily deployable on any public cloud platform, private cloud platform or even on premise");

        var monitoringCapability = PapayaFactory.eINSTANCE.createOperationalCapability();
        monitoringCapability.setName("Monitoring");
        monitoringCapability.setDescription("DevOps team shall be able to retrieve from Sirius Web all the monitoring data expected from a modern web application");

        operationCapabilities.getElements().addAll(List.of(
                operatingSystemCapability,
                networkCapability,
                executionEnvironmentCapability,
                deploymentCapability,
                monitoringCapability
        ));

        return operationCapabilities;
    }

    @Override
    public void link(IEObjectIndexer eObjectIndexer) {

    }
}
