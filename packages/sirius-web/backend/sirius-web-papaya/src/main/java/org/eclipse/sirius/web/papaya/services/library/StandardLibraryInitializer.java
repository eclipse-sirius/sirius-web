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
package org.eclipse.sirius.web.papaya.services.library;

import java.util.Objects;

import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibraryCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to register the standard libraries once their semantic data have been created.
 *
 * @author sbegaudeau
 */
@Service
public class StandardLibraryInitializer {

    private final ILibraryCreationService libraryCreationService;

    public StandardLibraryInitializer(ILibraryCreationService libraryCreationService) {
        this.libraryCreationService = Objects.requireNonNull(libraryCreationService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataCreatedEvent(SemanticDataCreatedEvent event) {
        if (event.causedBy() instanceof PublishPapayaLibraryCommand publishPapayaLibraryCommand) {
            var library = Library.newLibrary()
                    .namespace(publishPapayaLibraryCommand.namespace())
                    .name(publishPapayaLibraryCommand.name())
                    .version(publishPapayaLibraryCommand.version())
                    .semanticData(AggregateReference.to(event.semanticData().getId()))
                    .description(publishPapayaLibraryCommand.description())
                    .build(event);
            this.libraryCreationService.createLibrary(library);
        }
    }
}
