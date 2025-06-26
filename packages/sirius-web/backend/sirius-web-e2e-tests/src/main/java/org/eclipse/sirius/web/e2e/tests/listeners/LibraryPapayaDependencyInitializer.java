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
package org.eclipse.sirius.web.e2e.tests.listeners;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.eclipse.sirius.web.e2e.tests.templates.LibraryPapayaTemplatesProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Initializes the dependencies of projects created with the Papaya library template.
 *
 * @author gdaniel
 */
@Profile("test")
@Service
public class LibraryPapayaDependencyInitializer {

    private final ILibrarySearchService librarySearchService;

    private final ISemanticDataUpdateService semanticDataUpdateService;

    public LibraryPapayaDependencyInitializer(ILibrarySearchService librarySearchService, ISemanticDataUpdateService semanticDataUpdateService) {
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.semanticDataUpdateService = Objects.requireNonNull(semanticDataUpdateService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataCreatedEvent(SemanticDataCreatedEvent semanticDataCreatedEvent) {
        // We cannot react to the creation of a project from a template (see #4674), so we react to the creation of a papaya project with the name provided by the template.
        if (semanticDataCreatedEvent.causedBy() instanceof ProjectCreatedEvent projectCreatedEvent
                && projectCreatedEvent.project().getNatures().stream().anyMatch(nature -> Objects.equals(LibraryPapayaTemplatesProvider.PAPAYA_NATURE, nature.name()))
                && Objects.equals(LibraryPapayaTemplatesProvider.PAPAYA_PROJECT_NAME, projectCreatedEvent.project().getName())) {
            Optional<Library> optionalJavaLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "java", "0.0.3");
            optionalJavaLibrary.ifPresent(javaLibrary -> {
                this.semanticDataUpdateService.addDependencies(semanticDataCreatedEvent, AggregateReference.to(semanticDataCreatedEvent.semanticData().getId()), List.of(javaLibrary.getSemanticData()));
            });
        }
    }

}
