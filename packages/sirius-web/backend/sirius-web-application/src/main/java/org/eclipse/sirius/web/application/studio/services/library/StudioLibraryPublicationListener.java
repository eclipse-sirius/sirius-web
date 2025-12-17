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
package org.eclipse.sirius.web.application.studio.services.library;

import org.eclipse.sirius.web.application.library.api.IPublishLibraryInput;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibraryCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
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

    public StudioLibraryPublicationListener(ILibraryCreationService libraryCreationService) {
        this.libraryCreationService = libraryCreationService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataCreatedEvent(SemanticDataCreatedEvent semanticDataCreatedEvent) {
        if (semanticDataCreatedEvent.causedBy() instanceof StudioLibrarySemanticDataCreationRequested request && request.causedBy() instanceof IPublishLibraryInput publishLibrariesInput) {
            var semanticData = semanticDataCreatedEvent.semanticData();

            Library library = Library.newLibrary()
                    .namespace(publishLibrariesInput.projectId())
                    .name(request.libraryName())
                    .semanticData(AggregateReference.to(semanticData.getId()))
                    .version(publishLibrariesInput.version())
                    .description(publishLibrariesInput.description())
                    .build(semanticDataCreatedEvent);
            this.libraryCreationService.createLibrary(library);
        }
    }
}
