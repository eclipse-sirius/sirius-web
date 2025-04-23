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
package org.eclipse.sirius.web.papaya.factories;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.papaya.ApplicationConcern;
import org.eclipse.sirius.components.papaya.Channel;
import org.eclipse.sirius.components.papaya.Command;
import org.eclipse.sirius.components.papaya.Controller;
import org.eclipse.sirius.components.papaya.Domain;
import org.eclipse.sirius.components.papaya.Event;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.papaya.Publication;
import org.eclipse.sirius.components.papaya.Service;
import org.eclipse.sirius.components.papaya.Subscription;
import org.eclipse.sirius.web.papaya.factories.services.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.services.api.IObjectFactory;

/**
 * Used to document the creation of projects in Sirius Web.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class SiriusWebProjectCreationLifecycleFactory implements IObjectFactory {
    private ApplicationConcern projectCreationApplicationConcern;

    private Command createProjectInput;

    private Controller mutationCreateProjectDataFetcher;

    private Service iProjectApplicationService;

    private Service semanticDataInitializer;

    private Service projectSemanticDataInitializer;

    private Domain projectDomain;

    private Service iProjectCreationService;

    private Event projectCreatedEvent;

    private Domain semanticDataDomain;

    private Service iSemanticDataCreationService;

    private Event semanticDataCreatedEvent;

    private Domain projectSemanticDataDomain;

    private Service iProjectSemanticDataCreationService;

    private Event projectSemanticDataCreatedEvent;

    private Subscription createProjectSubscription;

    private Subscription projectCreatedEventSubscription;

    private Subscription semanticDataCreatedEventSubscription;

    private Publication projectCreatedEventPublication;

    private Publication semanticDataCreatedEventPublication;

    private Publication projectSemanticDataCreatedEventPublication;

    private Channel projectHttpChannel;

    @Override
    public void create(IEMFEditingContext editingContext) {
        Supplier<Resource> createResource = () -> {
            var documentId = UUID.randomUUID();
            var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
            var resourceMetadataAdapter = new ResourceMetadataAdapter("Sirius Web - Lifecycle");
            resource.eAdapters().add(resourceMetadataAdapter);
            editingContext.getDomain().getResourceSet().getResources().add(resource);
            return resource;
        };

        var resource = editingContext.getDomain().getResourceSet().getResources().stream()
                .filter(existingResource -> existingResource.eAdapters().stream()
                        .filter(ResourceMetadataAdapter.class::isInstance)
                        .map(ResourceMetadataAdapter.class::cast)
                        .anyMatch(resourceMetadataAdapter -> resourceMetadataAdapter.getName().equals("Sirius Web - Lifecycle")))
                .findFirst()
                .orElseGet(createResource);

        var project = resource.getContents().stream()
                .filter(Project.class::isInstance)
                .map(Project.class::cast)
                .filter(existingProject -> existingProject.getName().equals("Sirius Web"))
                .findFirst()
                .orElseGet(() -> {
                    var siriusWebProject = PapayaFactory.eINSTANCE.createProject();
                    siriusWebProject.setName("Sirius Web");
                    resource.getContents().add(siriusWebProject);
                    return siriusWebProject;
                });

        this.projectHttpChannel = PapayaFactory.eINSTANCE.createChannel();
        projectHttpChannel.setName("HTTP");
        project.getElements().add(projectHttpChannel);

        project.getElements().add(this.projectCreationApplicationConcern());
        project.getElements().add(this.projectDomain());
        project.getElements().add(this.semanticDataDomain());
        project.getElements().add(this.projectSemanticDataDomain());
    }

    private ApplicationConcern projectCreationApplicationConcern() {
        this.projectCreationApplicationConcern = PapayaFactory.eINSTANCE.createApplicationConcern();
        projectCreationApplicationConcern.setName("Project Creation");

        this.createProjectInput = PapayaFactory.eINSTANCE.createCommand();
        createProjectInput.setName("CreateProjectInput");
        projectCreationApplicationConcern.getCommands().add(createProjectInput);

        this.createProjectSubscription = PapayaFactory.eINSTANCE.createSubscription();
        createProjectSubscription.setMessage(this.createProjectInput);
        createProjectSubscription.setChannel(this.projectHttpChannel);

        this.mutationCreateProjectDataFetcher = PapayaFactory.eINSTANCE.createController();
        mutationCreateProjectDataFetcher.setName("MutationCreateProjectDataFetcher");
        projectCreationApplicationConcern.getControllers().add(mutationCreateProjectDataFetcher);

        this.iProjectApplicationService = PapayaFactory.eINSTANCE.createService();
        iProjectApplicationService.setName("IProjectApplicationService");

        this.semanticDataInitializer = PapayaFactory.eINSTANCE.createService();
        semanticDataInitializer.setName("SemanticDataInitializer");

        this.projectSemanticDataInitializer = PapayaFactory.eINSTANCE.createService();
        projectSemanticDataInitializer.setName("ProjectSemanticDataInitializer");

        this.projectCreationApplicationConcern.getServices().addAll(List.of(iProjectApplicationService, semanticDataInitializer, projectSemanticDataInitializer));

        return projectCreationApplicationConcern;
    }

    private Domain projectDomain() {
        this.projectDomain = PapayaFactory.eINSTANCE.createDomain();
        projectDomain.setName("Project");

        this.iProjectCreationService = PapayaFactory.eINSTANCE.createService();
        iProjectCreationService.setName("IProjectCreationService");
        projectDomain.getServices().add(iProjectCreationService);

        this.projectCreatedEvent = PapayaFactory.eINSTANCE.createEvent();
        projectCreatedEvent.setName("ProjectCreatedEvent");
        projectDomain.getEvents().add(projectCreatedEvent);

        this.projectCreatedEventPublication = PapayaFactory.eINSTANCE.createPublication();
        projectCreatedEventPublication.setMessage(this.projectCreatedEvent);

        this.projectCreatedEventSubscription = PapayaFactory.eINSTANCE.createSubscription();
        projectCreatedEventSubscription.setMessage(this.projectCreatedEvent);

        return projectDomain;
    }

    private Domain semanticDataDomain() {
        this.semanticDataDomain = PapayaFactory.eINSTANCE.createDomain();
        semanticDataDomain.setName("Semantic Data");

        this.iSemanticDataCreationService = PapayaFactory.eINSTANCE.createService();
        iSemanticDataCreationService.setName("ISemanticDataCreationService");
        semanticDataDomain.getServices().add(iSemanticDataCreationService);

        this.semanticDataCreatedEvent = PapayaFactory.eINSTANCE.createEvent();
        semanticDataCreatedEvent.setName("SemanticDataCreatedEvent");
        semanticDataDomain.getEvents().add(semanticDataCreatedEvent);

        this.semanticDataCreatedEventSubscription = PapayaFactory.eINSTANCE.createSubscription();
        semanticDataCreatedEventSubscription.setMessage(this.semanticDataCreatedEvent);

        this.semanticDataCreatedEventPublication = PapayaFactory.eINSTANCE.createPublication();
        semanticDataCreatedEventPublication.setMessage(this.semanticDataCreatedEvent);

        return semanticDataDomain;
    }

    private Domain projectSemanticDataDomain() {
        this.projectSemanticDataDomain = PapayaFactory.eINSTANCE.createDomain();
        projectSemanticDataDomain.setName("Project Semantic Data");

        this.iProjectSemanticDataCreationService = PapayaFactory.eINSTANCE.createService();
        iProjectSemanticDataCreationService.setName("IProjectSemanticDataCreationService");
        projectSemanticDataDomain.getServices().add(iProjectSemanticDataCreationService);

        this.projectSemanticDataCreatedEvent = PapayaFactory.eINSTANCE.createEvent();
        projectSemanticDataCreatedEvent.setName("ProjectSemanticDataCreatedEvent");
        projectSemanticDataDomain.getEvents().add(projectSemanticDataCreatedEvent);

        this.projectSemanticDataCreatedEventPublication = PapayaFactory.eINSTANCE.createPublication();
        projectSemanticDataCreatedEventPublication.setMessage(this.projectSemanticDataCreatedEvent);

        return projectSemanticDataDomain;
    }

    @Override
    public void link(IEObjectIndexer eObjectIndexer) {
        this.projectCreationApplicationConcern.getDomains().addAll(List.of(this.projectDomain, this.semanticDataDomain, this.projectSemanticDataDomain));
        this.mutationCreateProjectDataFetcher.getSubscriptions().add(this.createProjectSubscription);
        this.mutationCreateProjectDataFetcher.getCalls().add(this.iProjectApplicationService);
        this.iProjectApplicationService.getCalls().add(this.iProjectCreationService);
        this.iProjectCreationService.getPublications().add(this.projectCreatedEventPublication);
        this.projectCreatedEvent.getCausedBy().add(this.createProjectInput);

        this.semanticDataInitializer.getSubscriptions().add(this.projectCreatedEventSubscription);
        this.semanticDataInitializer.getCalls().add(this.iSemanticDataCreationService);
        this.iSemanticDataCreationService.getPublications().add(this.semanticDataCreatedEventPublication);
        this.semanticDataCreatedEvent.getCausedBy().add(this.projectCreatedEvent);

        this.projectSemanticDataInitializer.getSubscriptions().add(this.semanticDataCreatedEventSubscription);
        this.projectSemanticDataInitializer.getCalls().add(this.iProjectSemanticDataCreationService);
        this.iProjectSemanticDataCreationService.getPublications().add(this.projectSemanticDataCreatedEventPublication);
        this.projectSemanticDataCreatedEvent.getCausedBy().add(this.semanticDataCreatedEvent);
    }
}
