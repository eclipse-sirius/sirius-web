/*******************************************************************************
 * Copyright (c) 2024, 2026 CEA LIST and others.
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

package org.eclipse.sirius.web.view.fork.services;

import static org.eclipse.sirius.web.application.studio.services.StudioProjectTemplateProvider.STUDIO_NATURE;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.project.dto.NatureDTO;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.eclipse.sirius.web.view.fork.dto.CreateForkedStudioInput;
import org.eclipse.sirius.web.view.fork.dto.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.view.fork.services.api.IForkedStudioCreationService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation ICreateForkedStudioService used to create a forked studio.
 *
 * @author mcharfadi
 */
@Service
public class ForkedStudioCreationService implements IForkedStudioCreationService {

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IURLParser urlParser;

    private final IProjectCreationService projectCreationService;

    private final IMessageService messageService;

    public ForkedStudioCreationService(IRepresentationMetadataSearchService representationMetadataSearchService, IURLParser urlParser, IProjectCreationService projectCreationService, IMessageService messageService) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.projectCreationService = Objects.requireNonNull(projectCreationService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Transactional
    @Override
    public IPayload create(IInput input, IEditingContext editingContext) {
        IPayload payload = new ErrorPayload(input.id(), this.messageService.invalidInput(CreateForkedStudioInput.class.getSimpleName(), input.getClass().getSimpleName()));

        var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
        if (optionalSemanticDataId.isPresent() && input instanceof CreateForkedStudioInput representationInput && this.getRepresentationId(representationInput.representationId()).isPresent()) {
            var semanticDataId = optionalSemanticDataId.get();
            var optionalRepresentationMetadataId = this.getRepresentationId(representationInput.representationId())
                    .flatMap(new UUIDParser()::parse);
            var optionalRepresentationMetadata = optionalRepresentationMetadataId
                    .flatMap(representationMetadataId -> this.representationMetadataSearchService.findMetadataById(AggregateReference.to(semanticDataId), representationMetadataId));

            if (optionalRepresentationMetadata.isPresent()) {
                var representationDescriptionId = optionalRepresentationMetadata.get().getDescriptionId();
                var sourceElementId = getSourceElementId(representationDescriptionId);
                var sourceId = getSourceId(representationDescriptionId);

                if (sourceElementId.isPresent() && sourceId.isPresent()) {
                    var currentName = optionalRepresentationMetadata.get().getLabel();
                    var newName = "Forked " + currentName;
                    var optionalForkedProject = this.createStudioProject(representationInput, newName);

                    if (optionalForkedProject.isPresent()) {
                        payload = new CreateProjectSuccessPayload(input.id(), this.projectToDTO(optionalForkedProject.get()));
                    }
                }
            }
        }
        return payload;
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }

    private Optional<String> getRepresentationId(String descriptionId) {
        var id = descriptionId.split("\\?cursor");
        return Optional.ofNullable(id[0]);
    }

    private Optional<String> getSourceId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ID)).orElse(List.of()).stream().findFirst();
    }

    private ProjectDTO projectToDTO(Project project) {
        var natures = project.getNatures().stream()
                .map(nature -> new NatureDTO(nature.name()))
                .toList();
        return new ProjectDTO(project.getId(), project.getName(), natures);
    }

    public Optional<Project> createStudioProject(CreateForkedStudioInput representationInput, String newName) {
        var studioNatures = List.of(STUDIO_NATURE);
        var result = this.projectCreationService.createProject(representationInput, newName, studioNatures);
        if (result instanceof Success<Project> success && success.data() != null) {
            return Optional.of(success.data());
        } else {
            return  Optional.empty();
        }
    }

}
