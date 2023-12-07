/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.task.starter.configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.task.starter.helper.StereotypeBuilder;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Provides a initializer for Task example model.
 *
 * @author lfasani
 */
@Configuration
public class TaskStudioTemplatesInitializer implements IProjectTemplateInitializer {

    static final String TASK_MODEL_DOCUMENT_NAME = "Task Model";

    private final Logger logger = LoggerFactory.getLogger(TaskStudioTemplatesInitializer.class);

    private final IProjectRepository projectRepository;

    private final IDocumentRepository documentRepository;

    private final StereotypeBuilder stereotypeBuilder;

    public TaskStudioTemplatesInitializer(IProjectRepository projectRepository, IDocumentRepository documentRepository, MeterRegistry meterRegistry) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.stereotypeBuilder = new StereotypeBuilder("task-template-initializer", meterRegistry);
    }

    @Override
    public boolean canHandle(String templateId) {
        return TaskProjectTemplatesProvider.TASK_EXAMPLE_TEMPLATE_ID.equals(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(String templateId, IEditingContext editingContext) {
        Optional<RepresentationMetadata> representationMetadata = Optional.empty();
        if (TaskProjectTemplatesProvider.TASK_EXAMPLE_TEMPLATE_ID.equals(templateId)) {
            representationMetadata = this.initializeTaskProject(editingContext, TASK_MODEL_DOCUMENT_NAME, this.createTaskModel(), null);
        }
        return representationMetadata;
    }

    private String createTaskModel() {
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource("model/task_example.task"));
    }

    private Optional<RepresentationMetadata> initializeTaskProject(IEditingContext editingContext, String documentName, String documentContent,
            Function<Resource, Optional<RepresentationMetadata>> createRepresentation) {
        Optional<RepresentationMetadata> result = Optional.empty();
        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext).filter(EditingContext.class::isInstance).map(EditingContext.class::cast)
                .map(EditingContext::getDomain);
        Optional<UUID> editingContextUUID = new IDParser().parse(editingContext.getId());
        if (optionalEditingDomain.isPresent() && editingContextUUID.isPresent()) {
            AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
            ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();

            var optionalDomainDocumentEntity = this.projectRepository.findById(editingContextUUID.get()).map(projectEntity -> {
                DocumentEntity documentEntity = new DocumentEntity();
                documentEntity.setProject(projectEntity);
                documentEntity.setName(documentName);
                documentEntity.setContent(documentContent);

                documentEntity = this.documentRepository.save(documentEntity);
                return documentEntity;
            });

            if (optionalDomainDocumentEntity.isPresent()) {
                DocumentEntity documentEntity = optionalDomainDocumentEntity.get();

                JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
                resourceSet.getResources().add(resource);
                try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                    resource.load(inputStream, null);

                    if (createRepresentation != null) {
                        result = createRepresentation.apply(resource);
                    }

                } catch (IOException exception) {
                    this.logger.warn(exception.getMessage(), exception);
                }

                resource.eAdapters().add(new ResourceMetadataAdapter(documentName));

            }
        }
        return result;
    }
}
