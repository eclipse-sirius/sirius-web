/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.views.viewsexplorer.services;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.collaborative.trees.api.IDeleteTreeItemHandler;
import org.eclipse.sirius.components.collaborative.trees.api.IRenameTreeItemHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragment;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragmentStyle;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Provides the form description for the "views explorer" view.
 *
 * @author tgiraudet
 */
@Service
public class ViewsExplorerTreeDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String PREFIX = "viewsexplorer://";

    public static final String DESCRIPTION_ID = "views_explorer_tree_description";

    public static final String REPRESENTATION_NAME = "Views";

    private final IIdentityService identityService;

    private final ILabelService labelService;

    private final List<IRenameTreeItemHandler> renameTreeItemHandlers;

    private final List<IDeleteTreeItemHandler> deleteTreeItemHandlers;

    private final List<IRepresentationImageProvider> representationImageProviders;

    private final ViewsExplorerElementsProvider viewsElementsProvider;

    private final IMessageService messageService;

    public ViewsExplorerTreeDescriptionProvider(IIdentityService identityService, ILabelService labelService, List<IRenameTreeItemHandler> renameTreeItemHandlers,
            List<IDeleteTreeItemHandler> deleteTreeItemHandlers, List<IRepresentationImageProvider> representationImageProviders, ViewsExplorerElementsProvider viewsElementsProvider,
        IMessageService messageService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.renameTreeItemHandlers = Objects.requireNonNull(renameTreeItemHandlers);
        this.deleteTreeItemHandlers = Objects.requireNonNull(deleteTreeItemHandlers);
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
        this.viewsElementsProvider = Objects.requireNonNull(viewsElementsProvider);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        var description = TreeDescription.newTreeDescription(DESCRIPTION_ID)
                .label(REPRESENTATION_NAME)
                .idProvider(this::getTreeId)
                .kindProvider(this::getKind)
                .labelProvider(this::getLabel)
                .targetObjectIdProvider(this::getTargetObjectId)
                .treeItemIconURLsProvider(this::getImageURL)
                .treeItemIdProvider(this::getTreeItemId)
                .treeItemObjectProvider(this::getTreeItemObject)
                .elementsProvider(this::getElements)
                .childrenProvider(this::getChildren)
                .hasChildrenProvider(this::hasChildren)
                .canCreatePredicate(variableManager -> false)
                .deleteHandler(this::getDeleteHandler)
                .renameHandler(this::getRenameHandler)
                .treeItemLabelProvider(this::getLabel)
                .iconURLsProvider(variableManager -> List.of("/views-explorer/views-explorer.svg"))
                .editableProvider(this::isEditable)
                .deletableProvider(this::isDeletable)
                .selectableProvider(this::isSelectable)
                .parentObjectProvider(this::getParentObject)
                .build();

        return List.of(description);
    }

    private String getTreeId(VariableManager variableManager) {
        List<?> expandedObjects = variableManager.get(TreeRenderer.EXPANDED, List.class).orElse(List.of());
        List<String> expandedObjectIds = expandedObjects.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        return PREFIX + "?" + "&expandedIds=[" + String.join(",", expandedObjectIds) + "]";
    }

    private String getTreeItemId(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        String result;
        if (self instanceof RepresentationKind kind) {
            result = kind.name();
        } else if (self instanceof RepresentationDescriptionType type) {
            result = type.id();
        } else {
            result = this.identityService.getId(self);
        }
        return result;
    }

    private String getKind(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        var result = "";
        if (self instanceof RepresentationKind) {
            result = "siriusWeb://representationKind";
        } else if (self instanceof RepresentationDescriptionType) {
            result = "siriusWeb://representationDescriptionType";
        } else if (self instanceof RepresentationMetadata representationMetadata) {
            result = representationMetadata.getKind();
        }
        return result;
    }

    private StyledString getLabel(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        var result = StyledString.of("");
        if (self instanceof RepresentationKind kind) {
            String name = kind.name();
            String size = String.valueOf(kind.representationDescriptionTypes().stream().mapToLong(descType -> descType.representationsMetadata().size()).sum());
            result = this.getColoredLabel(name, size);
        } else if (self instanceof RepresentationDescriptionType descriptionType) {
            String name = descriptionType.descriptions().getLabel();
            String size = String.valueOf(descriptionType.representationsMetadata().size());
            result = this.getColoredLabel(name, size);
        } else {
            result = this.labelService.getStyledLabel(self);
        }
        return result;
    }

    private StyledString getColoredLabel(String label, String size) {
        return new StyledString(List.of(
            new StyledStringFragment("%s (%s)".formatted(label.toUpperCase(), size), StyledStringFragmentStyle.newDefaultStyledStringFragmentStyle()
                .foregroundColor("#261E588A")
                .build())
        ));
    }

    private String getTargetObjectId(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);
    }

    private List<String> getImageURL(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        List<String> result = null;
        if (self instanceof RepresentationKind representationKind) {
            result = this.getRepresentationImageURL(representationKind.id());
        } else if (self instanceof RepresentationDescriptionType representationDescriptionType) {
            String kind = representationDescriptionType.representationsMetadata().get(0).getKind();
            result = this.getRepresentationImageURL(kind);
        }

        if (result == null) {
            return this.labelService.getImagePaths(self);
        } else {
            return result;
        }
    }

    private List<String> getRepresentationImageURL(String kind) {
        return this.representationImageProviders.stream()
                .map(provider -> provider.getImageURL(kind))
                .flatMap(Optional::stream)
                .findFirst()
                .map(List::of)
                .orElse(List.of());
    }

    private IStatus getDeleteHandler(VariableManager variableManager) {
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        var optionalTreeItem = variableManager.get(TreeItem.SELECTED_TREE_ITEM, TreeItem.class);
        var optionalTree = variableManager.get(TreeDescription.TREE, Tree.class);

        if (optionalEditingContext.isPresent() && optionalTreeItem.isPresent() && optionalTree.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();
            TreeItem treeItem = optionalTreeItem.get();

            var optionalHandler = this.deleteTreeItemHandlers.stream()
                    .filter(handler -> handler.canHandle(editingContext, treeItem))
                    .findFirst();

            if (optionalHandler.isPresent()) {
                IDeleteTreeItemHandler deleteTreeItemHandler = optionalHandler.get();
                return deleteTreeItemHandler.handle(editingContext, treeItem, optionalTree.get());
            }
        }
        return new Failure(this.messageService.failedToDelete());
    }

    private IStatus getRenameHandler(VariableManager variableManager, String newLabel) {
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        var optionalTreeItem = variableManager.get(TreeItem.SELECTED_TREE_ITEM, TreeItem.class);
        var optionalTree = variableManager.get(TreeDescription.TREE, Tree.class);

        if (optionalEditingContext.isPresent() && optionalTreeItem.isPresent() && optionalTree.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();
            TreeItem treeItem = optionalTreeItem.get();

            var optionalHandler = this.renameTreeItemHandlers.stream()
                    .filter(handler -> handler.canHandle(editingContext, treeItem, newLabel))
                    .findFirst();

            if (optionalHandler.isPresent()) {
                IRenameTreeItemHandler renameTreeItemHandler = optionalHandler.get();
                return renameTreeItemHandler.handle(editingContext, treeItem, newLabel, optionalTree.get());
            }
        }
        return new Failure(this.messageService.failedToRename());
    }

    private Object getTreeItemObject(VariableManager variableManager) {
        var optionalTreeItemId = variableManager.get(TreeDescription.ID, String.class);

        if (optionalTreeItemId.isEmpty()) {
            return null;
        }

        String treeItemId = optionalTreeItemId.get();
        List<RepresentationKind> representationKinds = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                .map(this.viewsElementsProvider::getElements)
                .orElse(List.of());

        Object result = null;
        var kindIterator = representationKinds.iterator();
        while (result == null && kindIterator.hasNext()) {
            var representationKind = kindIterator.next();
            if (representationKind.name().equals(treeItemId)) {
                result = representationKind;
            } else {
                var descTypeIterator = representationKind.representationDescriptionTypes().iterator();
                while (result == null && descTypeIterator.hasNext()) {
                    var descType = descTypeIterator.next();
                    if (descType.id().equals(treeItemId)) {
                        result = descType;
                    } else {
                        var metadataIterator = descType.representationsMetadata().iterator();
                        while (result == null && metadataIterator.hasNext()) {
                            var metadata = metadataIterator.next();
                            if (metadata.getRepresentationMetadataId().toString().equals(treeItemId)) {
                                result = metadata;
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    private Object getParentObject(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        if (self instanceof RepresentationKind) {
            return null;
        }

        String selfId = this.identityService.getId(self);
        List<RepresentationKind> representationKinds = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                .map(this.viewsElementsProvider::getElements)
                .orElse(List.of());

        Object result = null;
        var kindIterator = representationKinds.iterator();
        while (result == null && kindIterator.hasNext()) {
            var representationKind = kindIterator.next();
            var descTypeIterator = representationKind.representationDescriptionTypes().iterator();
            while (result == null && descTypeIterator.hasNext()) {
                var descType = descTypeIterator.next();
                if (descType.id().equals(selfId)) {
                    result = representationKind;
                } else {
                    var metadataIterator = descType.representationsMetadata().iterator();
                    while (result == null && metadataIterator.hasNext()) {
                        var metadata = metadataIterator.next();
                        if (metadata.getRepresentationMetadataId().toString().equals(selfId)) {
                            result = descType;
                        }
                    }
                }
            }
        }

        return result;
    }

    private boolean isSelectable(VariableManager variableManager) {
        return true;
    }

    private boolean isEditable(VariableManager variableManager) {
        Object self = this.getSelf(variableManager);
        return self instanceof RepresentationMetadata;
    }

    private boolean isDeletable(VariableManager variableManager) {
        Object self = this.getSelf(variableManager);
        return self instanceof RepresentationMetadata;
    }

    private boolean hasChildren(VariableManager variableManager) {
        Object self = this.getSelf(variableManager);
        return self instanceof RepresentationDescriptionType || self instanceof RepresentationKind;
    }

    private List<?> getChildren(VariableManager variableManager) {
        List<String> expandedIds = new ArrayList<>();
        Object objects = variableManager.getVariables().get(TreeRenderer.EXPANDED);
        if (objects instanceof List<?> list) {
            expandedIds = list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .toList();
        }

        String id = this.getTreeItemId(variableManager);
        List<?> result = List.of();
        if (expandedIds.contains(id)) {
            Object self = this.getSelf(variableManager);
            if (self instanceof RepresentationKind representationKind) {
                result = representationKind.representationDescriptionTypes();
            } else if (self instanceof RepresentationDescriptionType representationDescriptionType) {
                result = representationDescriptionType.representationsMetadata();
            }
        }
        return result;
    }

    private Object getSelf(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class).orElse(null);
    }

    private List<?> getElements(VariableManager variableManager) {
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        return optionalEditingContext.map(viewsElementsProvider::getElements).orElse(List.of());
    }
}
