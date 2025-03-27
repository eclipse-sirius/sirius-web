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
package org.eclipse.sirius.web.application.document.services;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.application.document.services.api.IDocumentSanitizedJsonContentProvider;
import org.eclipse.sirius.web.application.document.services.api.IUploadFileLoader;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextMigrationParticipantPredicate;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Use to load a file receive in an upload document event.
 *
 * @author frouene
 */
@Service
public class UploadFileLoader implements IUploadFileLoader {

    private final Logger logger = LoggerFactory.getLogger(UploadFileLoader.class);

    private final IMessageService messageService;

    private final IResourceLoader resourceLoader;

    private final IDocumentSanitizedJsonContentProvider documentSanitizedJsonContentProvider;

    private final List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates;

    public UploadFileLoader(IMessageService messageService, IResourceLoader resourceLoader, IDocumentSanitizedJsonContentProvider documentSanitizedJsonContentProvider, List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates) {
        this.messageService = Objects.requireNonNull(messageService);
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
        this.documentSanitizedJsonContentProvider = Objects.requireNonNull(documentSanitizedJsonContentProvider);
        this.migrationParticipantPredicates = Objects.requireNonNull(migrationParticipantPredicates);
    }

    @Override
    public IResult<Resource> load(ResourceSet resourceSet, IEMFEditingContext emfEditingContext, UploadFile file) {
        var fileName = file.getName();
        var applyMigrationParticipants = this.migrationParticipantPredicates.stream().anyMatch(predicate -> predicate.test(emfEditingContext.getId()));
        var optionalContent = this.getContent(resourceSet, file, applyMigrationParticipants);
        if (optionalContent.isPresent()) {
            String id = UUID.randomUUID().toString();
            String content = optionalContent.get();
            ResourceSet targetResourceSet = emfEditingContext.getDomain().getResourceSet();
            var optionalRessource = this.resourceLoader.toResource(targetResourceSet, id, fileName, content, applyMigrationParticipants);
            if (optionalRessource.isPresent()) {
                return new Success<>(optionalRessource.get());
            }
        }
        return new Failure<>(this.messageService.unexpectedError());
    }

    private Optional<String> getContent(ResourceSet resourceSet, UploadFile file, boolean applyMigrationParticipants) {
        Optional<String> optionalContent = Optional.empty();

        try (var inputStream = file.getInputStream()) {
            optionalContent = this.documentSanitizedJsonContentProvider.getContent(resourceSet, file.getName(), inputStream, applyMigrationParticipants);
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return optionalContent;
    }
}
