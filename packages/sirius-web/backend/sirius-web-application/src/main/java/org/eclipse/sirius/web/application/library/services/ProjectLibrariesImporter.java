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
package org.eclipse.sirius.web.application.library.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.project.api.ICreateProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplate;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.eclipse.sirius.web.library.domain.Library;
import org.eclipse.sirius.web.library.domain.services.api.ILibrarySearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to import libraries once the project semantic data has been created.
 *
 * @author gcoutable
 */
@Service
public class ProjectLibrariesImporter {

    private final ISemanticDataUpdateService semanticDataUpdateService;

    private final List<IProjectTemplateProvider> projectTemplateProviders;

    private final ILibrarySearchService librarySearchService;

    public ProjectLibrariesImporter(ISemanticDataUpdateService semanticDataUpdateService, List<IProjectTemplateProvider> projectTemplateProviders, ILibrarySearchService librarySearchService) {
        this.semanticDataUpdateService = Objects.requireNonNull(semanticDataUpdateService);
        this.projectTemplateProviders = Objects.requireNonNull(projectTemplateProviders);
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onSemanticDataCreatedEvent(SemanticDataCreatedEvent event) {
        if (event.causedBy() instanceof ProjectCreatedEvent projectCreatedEvent && projectCreatedEvent.causedBy() instanceof ICreateProjectInput createProjectInput) {
            var projectSemanticData = event.semanticData();
            // Retrieving the libraries looks more complex than it needs to be because we work with (namespace, name, version) as well as UUID identifiers. This should be unified once #6743 is fixed.
            Set<Library> libraries = new TreeSet<>(Comparator.comparing(Library::getId));
            // Always add the libraries required by the template: we cannot trust the input to always contain them.
            Optional<ProjectTemplate> optionalProjectTemplate = this.projectTemplateProviders.stream()
                    .map(IProjectTemplateProvider::getProjectTemplates)
                    .flatMap(Collection::stream)
                    .filter(projectTemplate -> projectTemplate.id().equals(createProjectInput.templateId()))
                    .findFirst();

            optionalProjectTemplate.ifPresent(projectTemplate -> projectTemplate.requiredLibraries()
                    .stream()
                    .map(projectTemplateLibrary -> this.librarySearchService.findByNamespaceAndNameAndVersion(projectTemplateLibrary.namespace(), projectTemplateLibrary.name(),
                            projectTemplateLibrary.version()))
                    .flatMap(Optional::stream)
                    .forEach(libraries::add));

            // Then add the libraries provided in the input that haven't been added yet.
            libraries.addAll(this.librarySearchService.findAllById(createProjectInput.libraryIds().stream()
                    .map(new UUIDParser()::parse)
                    .flatMap(Optional::stream)
                    .toList()));
            List<AggregateReference<SemanticData, UUID>> newLibraries = new ArrayList<>();
            for (Library library : libraries) {
                var isAlreadyUsed = projectSemanticData.getDependencies().stream()
                        .anyMatch(dependency -> dependency.dependencySemanticDataId().getId().equals(library.getSemanticData().getId()));
                if (!isAlreadyUsed) {
                    newLibraries.add(library.getSemanticData());
                }
            }
            this.semanticDataUpdateService.addDependencies(event, AggregateReference.to(projectSemanticData.getId()), newLibraries);
        }
    }

}
