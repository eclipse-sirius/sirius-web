/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.services.editingcontext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.EObjectIDManager;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.document.Document;
import org.eclipse.sirius.web.services.api.events.DocumentsModifiedEvent;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.documents.DocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to persist the editing context when a change has been performed.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextPersistenceService implements IEditingContextPersistenceService {

    private static final String TIMER_NAME = "siriusweb_editingcontext_save";

    private final Logger logger = LoggerFactory.getLogger(EditingContextPersistenceService.class);

    private final IDocumentRepository documentRepository;

    private final IProjectRepository projectRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Timer timer;

    public EditingContextPersistenceService(IDocumentRepository documentRepository, IProjectRepository projectRepository, ApplicationEventPublisher applicationEventPublisher, MeterRegistry meterRegistry) {
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.applicationEventPublisher = Objects.requireNonNull(applicationEventPublisher);

        this.timer = Timer.builder(TIMER_NAME).register(meterRegistry);
    }

    @Override
    public void persist(ICause cause, IEditingContext editingContext) {
        long start = System.currentTimeMillis();

        if (editingContext instanceof IEMFEditingContext) {
            List<DocumentEntity> documentEntities = this.persistResources((IEMFEditingContext) editingContext);
            List<Document> documents = documentEntities.stream().map(new DocumentMapper()::toDTO).toList();
            new IDParser().parse(editingContext.getId())
                .map(editingContextId -> new DocumentsModifiedEvent(editingContextId, documents))
                .ifPresent(this.applicationEventPublisher::publishEvent);
        }

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
    }

    private List<DocumentEntity> persistResources(IEMFEditingContext editingContext) {
        List<DocumentEntity> result = new ArrayList<>();

        List<Resource> resources = editingContext.getDomain().getResourceSet().getResources().stream()
            .filter(res -> {
                URI uri = res.getURI();
                if (uri != null && !ColorPaletteService.SIRIUS_STUDIO_COLOR_PALETTES_URI.equals(uri.toString())) {
                    return IEMFEditingContext.RESOURCE_SCHEME.equals(uri.scheme());
                }
                return false;
            })
            .toList();

        for (Resource resource : resources) {
            this.save(resource, editingContext.getId()).ifPresent(result::add);
        }
        return result;
    }

    private Optional<DocumentEntity> save(Resource resource, String editingContextId) {
        Optional<DocumentEntity> result = Optional.empty();
        HashMap<Object, Object> options = new HashMap<>();
        options.put(JsonResource.OPTION_ID_MANAGER, new EObjectIDManager());
        options.put(JsonResource.OPTION_SCHEMA_LOCATION, true);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            resource.save(outputStream, options);

            for (Resource.Diagnostic warning : resource.getWarnings()) {
                this.logger.warn(warning.getMessage());
            }
            for (Resource.Diagnostic error : resource.getErrors()) {
                this.logger.warn(error.getMessage());
            }

            String content = outputStream.toString();

            var optionalResourceUUID = new IDParser().parse(resource.getURI().path().substring(1));

            result = optionalResourceUUID
                    .flatMap(this.documentRepository::findById)
                    .or(() -> this.createNewDocumentEntityWithoutContent(resource, editingContextId, optionalResourceUUID))
                    .map(entity -> {
                        entity.setContent(content);
                        return this.documentRepository.save(entity);
                    });

        } catch (IllegalArgumentException | IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return result;
    }

    private Optional<DocumentEntity> createNewDocumentEntityWithoutContent(Resource resource, String editingContextId, Optional<UUID> optionalResourceUUID) {
        return new IDParser().parse(editingContextId)
                             .flatMap(this.projectRepository::findById)
                             .map(projectEntity -> {
                                 var name = resource.eAdapters().stream()
                                         .filter(ResourceMetadataAdapter.class::isInstance)
                                         .map(ResourceMetadataAdapter.class::cast)
                                         .findFirst().map(ResourceMetadataAdapter::getName)
                                         .orElse("");

                                 DocumentEntity documentEntity = new DocumentEntity();
                                 documentEntity.setId(optionalResourceUUID.orElseGet(UUID::randomUUID));
                                 documentEntity.setProject(projectEntity);
                                 documentEntity.setName(name);
                                 return documentEntity;
                             });


    }
}
