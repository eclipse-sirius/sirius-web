/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

    public static final String REPRESENTATIONS_DEFAULT_FORM_DESCRIPTION_ID = "representations_default_form_description"; //$NON-NLS-1$

    private final IObjectService objectService;

    private final IRepresentationService representationService;

    private final List<IRepresentationImageProvider> representationImageProviders;

    public RepresentationsDescriptionProvider(IObjectService objectService, IRepresentationService representationService, List<IRepresentationImageProvider> representationImageProviders) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationService = Objects.requireNonNull(representationService);
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
    }

    @Override
    public FormDescription getRepresentationsDescription() {
        List<GroupDescription> groupDescriptions = new ArrayList<>();
        GroupDescription groupDescription = this.getGroupDescription();

        groupDescriptions.add(groupDescription);

        List<PageDescription> pageDescriptions = new ArrayList<>();
        PageDescription firstPageDescription = this.getPageDescription(groupDescriptions);
        pageDescriptions.add(firstPageDescription);

        // @formatter:off
        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(self -> self instanceof List<?>)
                .map(self -> (List<?>) self)
                .flatMap(self -> self.stream().findFirst())
                .map(this.objectService::getFullLabel)
                .orElse("Properties"); //$NON-NLS-1$
        // @formatter:on

        // @formatter:off
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(self -> self instanceof List<?>)
                .map(self -> (List<?>) self)
                .flatMap(self -> self.stream().findFirst())
                .map(this.objectService::getId)
                .orElse(null);

        return FormDescription.newFormDescription(UUID.nameUUIDFromBytes(REPRESENTATIONS_DEFAULT_FORM_DESCRIPTION_ID.getBytes()).toString())
                .label("Representations default form description") //$NON-NLS-1$
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(labelProvider)
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(variableManager -> false)
                .pageDescriptions(pageDescriptions)
                .groupDescriptions(groupDescriptions)
                .build();
        // @formatter:on
    }

    private PageDescription getPageDescription(List<GroupDescription> groupDescriptions) {
        // @formatter:off
        return PageDescription.newPageDescription("representationPageId") //$NON-NLS-1$
                .idProvider(variableManager -> "Representations Page") //$NON-NLS-1$
                .labelProvider(variableManager -> "Representations Page") //$NON-NLS-1$
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .groupDescriptions(groupDescriptions)
                .canCreatePredicate(variableManager -> true)
                .build();
        // @formatter:on
    }

    private GroupDescription getGroupDescription() {
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();
        Function<VariableManager, IStatus> itemClickHandlerProvider = variableManager -> new Success();

        // @formatter:off
        ListDescription listDescription = ListDescription.newListDescription("RepresentationsList") //$NON-NLS-1$
            .idProvider(new WidgetIdProvider())
            .labelProvider((variableManager) -> "Representations") //$NON-NLS-1$
            .itemsProvider(this.getItemsProvider())
            .itemIdProvider(this.getItemIdProvider())
            .itemLabelProvider(this.getItemLabelProvider())
            .itemImageURLProvider(this.getItemImageURLProvider())
            .itemDeletableProvider(this.getItemDeletableProvider())
            .itemClickHandlerProvider(itemClickHandlerProvider)
            .itemDeleteHandlerProvider(this.getItemDeleteHandlerProvider())
            .itemKindProvider(this.getItemKindProvider())
            .diagnosticsProvider((variableManager) -> List.of())
            .kindProvider((object) -> "") //$NON-NLS-1$
            .messageProvider((object) -> "") //$NON-NLS-1$
            .styleProvider(variableManager -> null)
            .build();
        // @formatter:on

        controlDescriptions.add(listDescription);

        // @formatter:off
        return GroupDescription.newGroupDescription("representationsGroupId") //$NON-NLS-1$
                .idProvider(variableManager -> "Representations Group") //$NON-NLS-1$
                .labelProvider(variableManager -> "Representations Group") //$NON-NLS-1$
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(controlDescriptions)
                .build();
        // @formatter:on
    }

    private Function<VariableManager, IStatus> getItemDeleteHandlerProvider() {
        return variableManager -> {
            // @formatter:off
            return variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class)
                    .map(RepresentationMetadata::getId)
                    .map(this::getSuccessStatus)
                    .orElse(new Failure("")); //$NON-NLS-1$
            // @formatter:on
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

    private Function<VariableManager, String> getItemImageURLProvider() {
        return variableManager -> {
            var optionalRepresentationMetadata = variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class);
            if (optionalRepresentationMetadata.isPresent()) {
                RepresentationMetadata representationMetadata = optionalRepresentationMetadata.get();

                // @formatter:off
                return this.representationImageProviders.stream()
                        .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.getKind()))
                        .flatMap(Optional::stream)
                        .findFirst()
                        .orElse(ImageConstants.RESOURCE_SVG);
                // @formatter:on
            }
            return ImageConstants.DEFAULT_SVG;
        };
    }

    private Function<VariableManager, String> getItemLabelProvider() {
        return variableManager -> {
            // @formatter:off
            return variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class)
                    .map(RepresentationMetadata::getLabel)
                    .orElse(null);
            // @formatter:on
        };
    }

    private Function<VariableManager, String> getItemKindProvider() {
        return variableManager -> {
            // @formatter:off
            return variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class)
                    .map(RepresentationMetadata::getKind)
                    .orElse(null);
            // @formatter:on
        };
    }

    private Function<VariableManager, String> getItemIdProvider() {
        return variableManager -> {
            // @formatter:off
            return variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class)
                    .map(RepresentationMetadata::getId)
                    .orElse(null);
            // @formatter:on
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
