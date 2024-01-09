/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.web.sample.services;

import static org.eclipse.sirius.web.sample.services.EditingContextActionProvider.BIG_GUY_FLOW_ID;
import static org.eclipse.sirius.web.sample.services.EditingContextActionProvider.EMPTY_ACTION_ID;
import static org.eclipse.sirius.web.sample.services.EditingContextActionProvider.EMPTY_DOMAIN_ID;
import static org.eclipse.sirius.web.sample.services.EditingContextActionProvider.EMPTY_FLOW_ID;
import static org.eclipse.sirius.web.sample.services.EditingContextActionProvider.EMPTY_VIEW_ID;
import static org.eclipse.sirius.web.sample.services.EditingContextActionProvider.PAPAYA_DOMAIN_ID;
import static org.eclipse.sirius.web.sample.services.EditingContextActionProvider.PAPAYA_VIEW_ID;
import static org.eclipse.sirius.web.sample.services.EditingContextActionProvider.ROBOT_FLOW_ID;

import fr.obeo.dsl.designer.sample.flow.FlowFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLParserPool;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLParserPoolImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextActionHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.emf.utils.EMFResourceUtils;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.sample.configuration.SampleDomainNameProvider;
import org.eclipse.sirius.web.sample.papaya.domain.PapayaDomainProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


/**
 * Handler used to perform an action on the editingContext.
 *
 * @author frouene
 */
@Service
public class EditingContextActionHandler implements IEditingContextActionHandler {

    private static final XMLParserPool PARSER_POOL = new XMLParserPoolImpl();

    private static final List<String> HANDLED_ACTIONS = List.of(EMPTY_ACTION_ID, EMPTY_FLOW_ID, ROBOT_FLOW_ID, BIG_GUY_FLOW_ID,
            EMPTY_DOMAIN_ID, PAPAYA_DOMAIN_ID, EMPTY_VIEW_ID, PAPAYA_VIEW_ID);

    private final Logger logger = LoggerFactory.getLogger(EditingContextActionHandler.class);

    private final SampleDomainNameProvider domainNameProvider;

    public EditingContextActionHandler() {
        this.domainNameProvider = new SampleDomainNameProvider();
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String actionId) {
        return HANDLED_ACTIONS.contains(actionId);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, String actionId) {
        return Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(AdapterFactoryEditingDomain::getResourceSet)
                .map(resourceSet -> this.performActionOnResourceSet(resourceSet, actionId))
                .orElse(new Failure("Something went wrong while handling this action."));
    }

    private IStatus performActionOnResourceSet(ResourceSet resourceSet, String actionId) {
        return switch (actionId) {
            case EMPTY_ACTION_ID -> this.createResourceAndReturnSuccess(resourceSet, this::createEmptyResource);
            case EMPTY_FLOW_ID -> this.createResourceAndReturnSuccess(resourceSet, this::createEmptyFlowResource);
            case ROBOT_FLOW_ID -> this.createResourceAndReturnSuccess(resourceSet, this::createRobotFlowResource);
            case BIG_GUY_FLOW_ID -> this.createResourceAndReturnSuccess(resourceSet, this::createBigGuyFlowResource);
            case EMPTY_DOMAIN_ID -> this.createResourceAndReturnSuccess(resourceSet, this::createEmptyDomainResource);
            case PAPAYA_DOMAIN_ID -> this.createResourceAndReturnSuccess(resourceSet, this::createPapayaDomainResource);
            case EMPTY_VIEW_ID -> this.createResourceAndReturnSuccess(resourceSet, this::createEmptyViewResource);
            case PAPAYA_VIEW_ID -> this.createResourceAndReturnSuccess(resourceSet, this::createPapayaViewResource);
            default -> new Failure("Unknown action.");
        };

    }

    private IStatus createResourceAndReturnSuccess(ResourceSet resourceSet, Consumer<ResourceSet> createResource) {
        createResource.accept(resourceSet);
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
    }

    private void createEmptyResource(ResourceSet resourceSet) {
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
        resource.eAdapters().add(new ResourceMetadataAdapter("Others..."));
        resourceSet.getResources().add(resource);
    }

    private void createEmptyFlowResource(ResourceSet resourceSet) {
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
        resource.getContents().add(FlowFactory.eINSTANCE.createSystem());
        resource.eAdapters().add(new ResourceMetadataAdapter("Flow"));
        resourceSet.getResources().add(resource);
    }

    private void createEmptyDomainResource(ResourceSet resourceSet) {
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName(this.domainNameProvider.getSampleDomainName());
        resource.getContents().add(domain);
        resource.eAdapters().add(new ResourceMetadataAdapter("Domain"));
        resourceSet.getResources().add(resource);
    }

    private void createPapayaDomainResource(ResourceSet resourceSet) {
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
        new PapayaDomainProvider().getDomains().forEach(resource.getContents()::add);
        resource.eAdapters().add(new ResourceMetadataAdapter("Papaya Domain"));
        resourceSet.getResources().add(resource);
    }

    private void createEmptyViewResource(ResourceSet resourceSet) {
        View newView = ViewFactory.eINSTANCE.createView();
        DiagramDescription diagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName("New Diagram Description");
        newView.getDescriptions().add(diagramDescription);

        JsonResource resource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
        resource.getContents().add(newView);
        resource.eAdapters().add(new ResourceMetadataAdapter("View"));
        resourceSet.getResources().add(resource);
    }

    private void createPapayaViewResource(ResourceSet resourceSet) {

        JsonResource resource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
        resource.getContents().add(new PapayaViewProvider().getView());
        resource.eAdapters().add(new ResourceMetadataAdapter("Papaya View"));
        resourceSet.getResources().add(resource);
    }


    private void createRobotFlowResource(ResourceSet resourceSet) {
        this.getResourceFromClassPathResource(new ClassPathResource("robot.flow")).ifPresent(resource -> {
            resource.eAdapters().add(new ResourceMetadataAdapter("Robot Flow"));
            resourceSet.getResources().add(resource);
        });
    }

    private void createBigGuyFlowResource(ResourceSet resourceSet) {
        this.getResourceFromClassPathResource(new ClassPathResource("Big_Guy.flow")).ifPresent(resource -> {
            resource.eAdapters().add(new ResourceMetadataAdapter("Big Guy Flow (17k elements)"));
            resourceSet.getResources().add(resource);
        });
    }

    public Optional<Resource> getResourceFromClassPathResource(ClassPathResource classPathResource) {

        try (var inputStream = classPathResource.getInputStream()) {
            URI uri = new JSONResourceFactory().createResourceURI(UUID.randomUUID().toString());
            return Optional.of(this.loadFromXMIAndTransformToJSONResource(uri, inputStream));
        } catch (IOException exception) {
            this.logger.error(exception.getMessage(), exception);
            return Optional.empty();
        }
    }

    private Resource loadFromXMIAndTransformToJSONResource(URI uri, InputStream inputStream) throws IOException {
        Resource inputResource = new XMIResourceImpl(uri);
        Map<String, Object> xmiLoadOptions = new EMFResourceUtils().getXMILoadOptions(PARSER_POOL);
        inputResource.load(inputStream, xmiLoadOptions);
        return this.transformToJSON(uri, inputResource);
    }

    private JsonResource transformToJSON(URI uri, Resource inputResource) throws IOException {
        JsonResource outputResource = new JSONResourceFactory().createResource(uri);
        outputResource.getContents().addAll(inputResource.getContents());
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Map<String, Object> jsonSaveOptions = new EMFResourceUtils().getFastJSONSaveOptions();
            jsonSaveOptions.put(JsonResource.OPTION_ENCODING, JsonResource.ENCODING_UTF_8);
            jsonSaveOptions.put(JsonResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
            outputResource.save(outputStream, jsonSaveOptions);
        }
        return outputResource;
    }

}
