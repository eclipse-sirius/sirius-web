/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.web.services.representations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.collaborative.editingcontext.EditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.forms.api.IRepresentationsDescriptionProvider;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.compatibility.services.ImageConstants;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.components.ListComponent;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.ListDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.springframework.stereotype.Service;

/**
 * Used to provide a default form description for the representations representation.
 *
 * @author gcoutable
 */
@Service
public class RepresentationsDescriptionProvider implements IRepresentationsDescriptionProvider {

    public static final String REPRESENTATIONS_DEFAULT_FORM_DESCRIPTION_ID = "representations_default_form_description";

    private final IObjectService objectService;

    private final IRepresentationService representationService;

    private final List<IRepresentationImageProvider> representationImageProviders;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    public RepresentationsDescriptionProvider(IObjectService objectService, IRepresentationService representationService, List<IRepresentationImageProvider> representationImageProviders) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationService = Objects.requireNonNull(representationService);
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
        this.semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null);
    }

    @Override
    public FormDescription getRepresentationsDescription() {
        List<GroupDescription> groupDescriptions = new ArrayList<>();
        GroupDescription groupDescription = this.getGroupDescription();

        groupDescriptions.add(groupDescription);

        List<PageDescription> pageDescriptions = new ArrayList<>();
        PageDescription firstPageDescription = this.getPageDescription(groupDescriptions);
        pageDescriptions.add(firstPageDescription);

        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getFullLabel)
                .orElse("Properties");

        return FormDescription.newFormDescription(UUID.nameUUIDFromBytes(REPRESENTATIONS_DEFAULT_FORM_DESCRIPTION_ID.getBytes()).toString())
                .label("Representations default form description")
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(labelProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .canCreatePredicate(variableManager -> false)
                .pageDescriptions(pageDescriptions)
                .build();
    }

    private PageDescription getPageDescription(List<GroupDescription> groupDescriptions) {
        return PageDescription.newPageDescription("representationPageId")
                .idProvider(variableManager -> "Representations Page")
                .labelProvider(variableManager -> "Representations Page")
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .groupDescriptions(groupDescriptions)
                .canCreatePredicate(variableManager -> true)
                .build();
    }

    private GroupDescription getGroupDescription() {
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();
        Function<VariableManager, IStatus> itemClickHandlerProvider = variableManager -> new Success();

        ListDescription listDescription = ListDescription.newListDescription("RepresentationsList")
            .idProvider(new WidgetIdProvider())
            .labelProvider((variableManager) -> "Representations")
            .itemsProvider(this.getItemsProvider())
            .itemIdProvider(this.getItemIdProvider())
            .itemLabelProvider(this.getItemLabelProvider())
            .itemIconURLProvider(this.getItemIconURLProvider())
            .itemDeletableProvider(this.getItemDeletableProvider())
            .itemClickHandlerProvider(itemClickHandlerProvider)
            .itemDeleteHandlerProvider(this.getItemDeleteHandlerProvider())
            .itemKindProvider(this.getItemKindProvider())
            .diagnosticsProvider((variableManager) -> List.of())
            .kindProvider((object) -> "")
            .messageProvider((object) -> "")
            .styleProvider(variableManager -> null)
            .targetObjectIdProvider(this.semanticTargetIdProvider)
            .build();

        controlDescriptions.add(listDescription);

        return GroupDescription.newGroupDescription("representationsGroupId")
                .idProvider(variableManager -> "Representations Group")
                .labelProvider(variableManager -> "Representations Group")
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(controlDescriptions)
                .build();
    }

    private Function<VariableManager, IStatus> getItemDeleteHandlerProvider() {
        return variableManager -> {
            return variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class)
                    .map(RepresentationMetadata::getId)
                    .map(this::getSuccessStatus)
                    .orElse(new Failure(""));
        };
    }

    private IStatus getSuccessStatus(String representationId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(EditingContextEventProcessor.REPRESENTATION_ID, representationId);
        return new Success(ChangeKind.REPRESENTATION_TO_DELETE, parameters);
    }

    private Function<VariableManager, Boolean> getItemDeletableProvider() {
        return variableManager -> {
            return true;
        };
    }

    private Function<VariableManager, List<String>> getItemIconURLProvider() {
        return variableManager -> {
            var optionalRepresentationMetadata = variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class);
            if (optionalRepresentationMetadata.isPresent()) {
                RepresentationMetadata representationMetadata = optionalRepresentationMetadata.get();

                return this.representationImageProviders.stream()
                        .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.getKind()))
                        .flatMap(Optional::stream)
                        .toList();
            }
            return List.of(ImageConstants.DEFAULT_SVG);
        };
    }

    private Function<VariableManager, String> getItemLabelProvider() {
        return variableManager -> {
            return variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class)
                    .map(RepresentationMetadata::getLabel)
                    .orElse(null);
        };
    }

    private Function<VariableManager, String> getItemKindProvider() {
        return variableManager -> {
            return variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class)
                    .map(RepresentationMetadata::getKind)
                    .orElse(null);
        };
    }

    private Function<VariableManager, String> getItemIdProvider() {
        return variableManager -> {
            return variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class)
                    .map(RepresentationMetadata::getId)
                    .orElse(null);
        };
    }

    private Function<VariableManager, List<?>> getItemsProvider() {
        return variableManager -> {
            Object object = variableManager.getVariables().get(VariableManager.SELF);
            String id = this.objectService.getId(object);
            var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
            if (optionalEditingContext.isPresent() && id != null) {
                IEditingContext editingContext = optionalEditingContext.get();
                List<RepresentationMetadata> items = this.representationService.findAllByTargetObjectId(editingContext, id);
                return items;
            }
            return List.of();
        };
    }
}
