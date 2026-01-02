/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.application.views.representations.services;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.api.ChangeDescriptionParameters;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.api.IRepresentationsDescriptionProvider;
import org.eclipse.sirius.components.collaborative.forms.variables.FormVariableProvider;
import org.eclipse.sirius.components.core.CoreImageConstants;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.forms.TreeNode;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.components.ListComponent;
import org.eclipse.sirius.components.forms.components.TreeComponent;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.forms.description.ListDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.PortalView;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationIconURL;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataDeletionService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Provides the form description for the representations view.
 *
 * @author gcoutable
 */
@Service
public class RepresentationsFormDescriptionProvider implements IRepresentationsDescriptionProvider {

    public static final String PREFIX = "representations://";

    public static final String FORM_DESCRIPTION_ID = "representations_form_description";

    public static final String PAGE_LABEL = "Representations Page";

    public static final String GROUP_LABEL = "Representations Group";

    private final IIdentityService identityService;

    private final ILabelService labelService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationMetadataDeletionService representationMetadataDeletionService;

    private final IRepresentationSearchService representationSearchService;

    private final List<IRepresentationImageProvider> representationImageProviders;

    private final IMessageService messageService;

    public RepresentationsFormDescriptionProvider(IIdentityService identityService, ILabelService labelService, IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationMetadataDeletionService representationMetadataDeletionService, IRepresentationSearchService representationSearchService, List<IRepresentationImageProvider> representationImageProviders, IMessageService messageService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationMetadataDeletionService = Objects.requireNonNull(representationMetadataDeletionService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public FormDescription getRepresentationsDescription() {
        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.labelService::getStyledLabel)
                .map(Object::toString)
                .orElse(this.messageService.representationsViewTitle());

        return FormDescription.newFormDescription(FORM_DESCRIPTION_ID)
                .label("Representations default form description")
                .idProvider(this::getRepresentationsFormId)
                .labelProvider(labelProvider)
                .targetObjectIdProvider(this::getTargetObjectId)
                .canCreatePredicate(variableManager -> false)
                .pageDescriptions(List.of(this.getPageDescription()))
                .iconURLsProvider(variableManager -> List.of())
                .build();
    }

    private String getRepresentationsFormId(VariableManager variableManager) {
        List<?> selectedObjects = variableManager.get(FormVariableProvider.SELECTION.name(), List.class).orElse(List.of());
        List<String> selectedObjectIds = selectedObjects.stream()
                .map(this.identityService::getId)
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();
        return PREFIX + "?objectIds=[" + String.join(",", selectedObjectIds) + "]";
    }

    private PageDescription getPageDescription() {
        return PageDescription.newPageDescription("representationPageId")
                .idProvider(variableManager -> PAGE_LABEL)
                .labelProvider(variableManager -> PAGE_LABEL)
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .groupDescriptions(List.of(this.getGroupDescription()))
                .canCreatePredicate(variableManager -> true)
                .build();
    }

    private GroupDescription getGroupDescription() {
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();

        ListDescription listDescription = ListDescription.newListDescription("RepresentationsList")
                .idProvider(new WidgetIdProvider())
                .labelProvider(variableManager -> this.messageService.representationsViewTitle())
                .itemsProvider(this::getItems)
                .itemIdProvider(this::getItemId)
                .itemLabelProvider(this::getItemLabel)
                .itemKindProvider(this::getItemKind)
                .itemIconURLProvider(this::getItemIconURL)
                .itemDeletableProvider(variableManager -> true)
                .itemClickHandlerProvider(variableManager -> new Success())
                .itemDeleteHandlerProvider(this::deleteItem)
                .targetObjectIdProvider(this::getTargetObjectId)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .styleProvider(variableManager -> null)
                .build();

        IfDescription ifSemanticElementDescription = IfDescription.newIfDescription("ifSemanticElement")
                .targetObjectIdProvider(this::getTargetObjectId)
                .predicate(variableManager -> {
                    var optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
                    if (optionalSelf.isEmpty() || optionalSelf.get() instanceof RepresentationMetadata) {
                        return false;
                    } else {
                        return this.identityService.getId(optionalSelf.get()) != null;
                    }
                })
                .controlDescriptions(List.of(listDescription))
                .build();
        controlDescriptions.add(ifSemanticElementDescription);

        TreeDescription treeDescription = TreeDescription.newTreeDescription("PortalContentsTree")
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(this::getTargetObjectId)
                .labelProvider(variableManager -> variableManager.get("label", String.class).orElse("Portal") + " contents")
                .iconURLProvider(variableManager -> List.of())
                .childrenProvider(this::getChildren)
                .nodeIdProvider(this::getNodeId)
                .nodeLabelProvider(this::getNodeLabel)
                .nodeKindProvider(this::getNodeKind)
                .nodeIconURLProvider(this::getNodeIconURL)
                .nodeEndIconsURLProvider(variableManager -> List.of())
                .nodeSelectableProvider(variableManager -> true)
                .isCheckableProvider(variableManager -> false)
                .checkedValueProvider(variableManager -> false)
                .newCheckedValueHandler((variableManager, newValue) -> new Success())
                .expandedNodeIdsProvider(this::collectAllNodeIds)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .build();

        IfDescription ifPortalDescription = IfDescription.newIfDescription("ifPortal")
                .targetObjectIdProvider(this::getTargetObjectId)
                .predicate(variableManager -> variableManager.get(VariableManager.SELF, RepresentationMetadata.class).map(RepresentationMetadata::getKind).filter(Portal.KIND::equals).isPresent())
                .controlDescriptions(List.of(treeDescription))
                .build();
        controlDescriptions.add(ifPortalDescription);

        return GroupDescription.newGroupDescription("representationsGroupId")
                .idProvider(variableManager -> GROUP_LABEL)
                .labelProvider(variableManager -> GROUP_LABEL)
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(controlDescriptions)
                .build();
    }

    private String getTargetObjectId(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);
    }

    private List<?> getItems(VariableManager variableManager) {
        Object object = variableManager.getVariables().get(VariableManager.SELF);
        var optionalSemanticDataId = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                .map(IEditingContext::getId)
                .flatMap(semanticDataId -> new UUIDParser().parse(semanticDataId));

        if (optionalSemanticDataId.isPresent()) {
            var semanticDataId = optionalSemanticDataId.get();
            String id = this.identityService.getId(object);
            return this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticDataAndTargetObjectId(AggregateReference.to(semanticDataId), id);
        }
        return List.of();
    }

    private String getItemId(VariableManager variableManager) {
        return variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class)
                .map(RepresentationMetadata::getId)
                .map(UUID::toString)
                .orElse(null);
    }

    private String getItemLabel(VariableManager variableManager) {
        return variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class)
                .map(RepresentationMetadata::getLabel)
                .orElse(null);
    }

    private String getItemKind(VariableManager variableManager) {
        return variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class)
                .map(RepresentationMetadata::getKind)
                .orElse(null);
    }

    private List<String> getItemIconURL(VariableManager variableManager) {
        List<String> result = List.of(CoreImageConstants.DEFAULT_SVG);

        var optionalRepresentationMetadata = variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class);
        if (optionalRepresentationMetadata.isPresent()) {
            RepresentationMetadata representationMetadata = optionalRepresentationMetadata.get();
            if (representationMetadata.getIconURLs().isEmpty()) {
                result = this.representationImageProviders.stream()
                        .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.getKind()))
                        .filter(Optional::isPresent)
                        .flatMap(Optional::stream)
                        .findFirst()
                        .map(List::of)
                        .orElse(List.of());
            } else {
                result = representationMetadata.getIconURLs().stream()
                        .map(RepresentationIconURL::url)
                        .toList();
            }
        }
        return result;
    }

    private IStatus deleteItem(VariableManager variableManager) {
        var optionalEditingContextId = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                .map(IEditingContext::getId);
        var optionalRepresentationId = variableManager.get(ListComponent.CANDIDATE_VARIABLE, RepresentationMetadata.class)
                .map(RepresentationMetadata::getId)
                .map(UUID::toString);

        if (optionalEditingContextId.isPresent() && optionalRepresentationId.isPresent()) {
            var editingContextId = optionalEditingContextId.get();
            var representationId = optionalRepresentationId.get();

            return this.deleteRepresentation(editingContextId, representationId);
        }

        return new Failure("");
    }

    private IStatus deleteRepresentation(String editingContextId, String representationId) {
        var optionalRepresentationUUID = new UUIDParser().parse(representationId);
        var input = new DeleteRepresentationInput(UUID.randomUUID(), editingContextId, representationId);
        if (optionalRepresentationUUID.isPresent()) {
            var representationUUID = optionalRepresentationUUID.get();
            var result = this.representationMetadataDeletionService.delete(input, representationUUID);
            if (result instanceof org.eclipse.sirius.web.domain.services.Success) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put(ChangeDescriptionParameters.REPRESENTATION_ID, representationId);
                return new Success(ChangeKind.REPRESENTATION_DELETION, parameters);
            }
        }
        return new Failure("");
    }

    private List<?> getChildren(VariableManager variableManager) {
        List<RepresentationMetadata> items = List.of();
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        if (optionalEditingContext.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();
            var optionalObject = variableManager.get(VariableManager.SELF, Object.class);
            if (optionalObject.isPresent()) {
                var object = optionalObject.get();
                String id = this.identityService.getId(object);
                if (object instanceof RepresentationMetadata representationMetadata && Portal.KIND.equals(representationMetadata.getKind())) {
                    var optionalPortal = this.representationSearchService.findById(editingContext, representationMetadata.getId().toString(), Portal.class);
                    if (optionalPortal.isPresent()) {
                        items = this.getPortalChildren(optionalPortal.get());
                    }
                } else if (id != null) {
                    var optionalSemanticDataId = optionalEditingContext
                            .map(IEditingContext::getId)
                            .flatMap(semanticDataId -> new UUIDParser().parse(semanticDataId));
                    if (optionalSemanticDataId.isPresent()) {
                        items = this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticDataAndTargetObjectId(AggregateReference.to(optionalSemanticDataId.get()), id).stream().toList();
                    }
                }
            }
        }
        return items;
    }

    private List<RepresentationMetadata> getPortalChildren(Portal portal) {
        return portal.getViews().stream()
                .map(PortalView::getRepresentationId)
                .flatMap(representationId -> new UUIDParser().parse(representationId).stream())
                .flatMap(representationId -> this.representationMetadataSearchService.findMetadataById(representationId).stream())
                .toList();
    }

    private String getNodeId(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, RepresentationMetadata.class)
                .map(RepresentationMetadata::getId)
                .map(UUID::toString)
                .orElse(null);
    }

    private String getNodeLabel(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, RepresentationMetadata.class)
                .map(RepresentationMetadata::getLabel)
                .orElse(null);
    }

    private String getNodeKind(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, RepresentationMetadata.class)
                .map(RepresentationMetadata::getKind)
                .orElse(null);
    }

    private List<String> getNodeIconURL(VariableManager variableManager) {
        var optionalRepresentationMetadata = variableManager.get(VariableManager.SELF, RepresentationMetadata.class);
        if (optionalRepresentationMetadata.isPresent()) {
            RepresentationMetadata representationMetadata = optionalRepresentationMetadata.get();

            return this.representationImageProviders.stream()
                    .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.getKind()))
                    .flatMap(Optional::stream)
                    .toList();
        }
        return List.of(CoreImageConstants.DEFAULT_SVG);
    }

    private List<String> collectAllNodeIds(VariableManager variableManager) {
        List<String> result = new ArrayList<>();
        for (var element : variableManager.get(TreeComponent.NODES_VARIABLE, List.class).orElse(List.of())) {
            if (element instanceof TreeNode node) {
                result.add(node.getId());
            }
        }
        return result;
    }
}
