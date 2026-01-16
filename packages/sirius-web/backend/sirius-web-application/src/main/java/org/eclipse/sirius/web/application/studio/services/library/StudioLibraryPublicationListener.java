/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.application.studio.services.library;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextService;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibraryCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to create a library once its semantic data are published.
 *
 * @author sbegaudeau
 */
@Service
public class StudioLibraryPublicationListener {

    private final ILibraryCreationService libraryCreationService;

    private final IProjectEditingContextService projectEditingContextService;

    private final Logger logger = LoggerFactory.getLogger(StudioLibraryPublicationListener.class);


    public StudioLibraryPublicationListener(ILibraryCreationService libraryCreationService, IProjectEditingContextService projectEditingContextService) {
        this.libraryCreationService = Objects.requireNonNull(libraryCreationService);
        this.projectEditingContextService = Objects.requireNonNull(projectEditingContextService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataCreatedEvent(SemanticDataCreatedEvent semanticDataCreatedEvent) {
        if (semanticDataCreatedEvent.causedBy() instanceof StudioLibrarySemanticDataCreationRequested request && request.causedBy() instanceof PublishLibrariesInput publishLibrariesInput) {
            var semanticData = semanticDataCreatedEvent.semanticData();

            Optional<String> optionalProjectId = this.projectEditingContextService.getProjectId(publishLibrariesInput.editingContextId());

            if (optionalProjectId.isPresent()) {
                Library library = Library.newLibrary()
                        .namespace(optionalProjectId.get())
                        .name(request.libraryName())
                        .semanticData(AggregateReference.to(semanticData.getId()))
                        .version(publishLibrariesInput.version())
                        .description(publishLibrariesInput.description())
                        .build(semanticDataCreatedEvent);
                this.libraryCreationService.createLibrary(library);
            } else {
                this.logger.warn("No project found for editing context {}", publishLibrariesInput.editingContextId());
            }
        }
    }
}
