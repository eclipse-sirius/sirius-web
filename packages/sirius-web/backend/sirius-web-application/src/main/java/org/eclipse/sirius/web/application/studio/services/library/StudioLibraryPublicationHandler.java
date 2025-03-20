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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.application.library.services.api.ILibraryPublicationHandler;
import org.eclipse.sirius.web.application.studio.services.library.api.DependencyGraph;
import org.eclipse.sirius.web.application.studio.services.library.api.IStudioLibraryDependencyCollector;
import org.eclipse.sirius.web.application.studio.services.library.api.IStudioLibrarySemanticDataCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Handles the publication of libraries from studios.
 *
 * @author gdaniel
 */
@Service
public class StudioLibraryPublicationHandler implements ILibraryPublicationHandler {

    private final IEditingContextSearchService editingContextSearchService;

    private final IStudioLibraryDependencyCollector studioLibraryDependencyCollector;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final IStudioLibrarySemanticDataCreationService studioLibrarySemanticDataCreationService;

    private final IMessageService messageService;

    public StudioLibraryPublicationHandler(IEditingContextSearchService editingContextSearchService, IStudioLibraryDependencyCollector studioLibraryDependencyCollector, IProjectSemanticDataSearchService projectSemanticDataSearchService, IStudioLibrarySemanticDataCreationService studioLibrarySemanticDataCreationService, IMessageService messageService) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.studioLibraryDependencyCollector = Objects.requireNonNull(studioLibraryDependencyCollector);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.studioLibrarySemanticDataCreationService = Objects.requireNonNull(studioLibrarySemanticDataCreationService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(PublishLibrariesInput input) {
        return Objects.equals(input.publicationKind(), "studio-all");
    }

    @Override
    public IPayload handle(PublishLibrariesInput input) {
        IPayload result = new ErrorPayload(input.id(), this.messageService.unexpectedError());

        Optional<IEMFEditingContext> optionalEditingContext = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(input.projectId()))
                .flatMap(projectSemanticData -> this.editingContextSearchService.findById(projectSemanticData.getSemanticData().getId().toString()))
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast);

        if (optionalEditingContext.isPresent()) {
            var editingContext = optionalEditingContext.get();
            ResourceSet resourceSet = editingContext.getDomain().getResourceSet();
            resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put(IEMFEditingContext.RESOURCE_SCHEME, new JSONResourceFactory());

            DependencyGraph<EObject> dependencyGraph = this.studioLibraryDependencyCollector.collectDependencies(resourceSet);

            Collection<SemanticData> createdSemanticData = this.studioLibrarySemanticDataCreationService.createSemanticData(input, dependencyGraph, resourceSet);

            result = new SuccessPayload(input.id(), List.of(new Message(createdSemanticData.size() + " libraries published", MessageLevel.SUCCESS)));
        }
        return result;
    }


}
