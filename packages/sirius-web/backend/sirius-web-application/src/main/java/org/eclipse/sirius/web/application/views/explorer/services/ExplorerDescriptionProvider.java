/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.trees.api.IDeleteTreeItemHandler;
import org.eclipse.sirius.components.collaborative.trees.api.IRenameTreeItemHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventProcessorFactory;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerChildrenProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerElementsProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerLabelService;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerServices;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description of the explorer.
 *
 * @author hmarchadour
 */
@Service
public class ExplorerDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String DESCRIPTION_ID = "explorer_tree_description";

    public static final String DOCUMENT_KIND = "siriusWeb://document";

    public static final String PREFIX = "explorer://";

    public static final String REPRESENTATION_NAME = "Explorer";

    public static final String EXISTING_REPRESENTATIONS = "existingRepresentations";

    private final ILabelService labelService;

    private final IExplorerElementsProvider explorerElementsProvider;

    private final IExplorerChildrenProvider explorerChildrenProvider;

    private final List<IRenameTreeItemHandler> renameTreeItemHandlers;

    private final List<IDeleteTreeItemHandler> deleteTreeItemHandlers;

    private final IExplorerServices explorerServices;

    private final IExplorerLabelService explorerLabelService;

    public ExplorerDescriptionProvider(ILabelService labelService, IExplorerElementsProvider explorerElementsProvider, IExplorerChildrenProvider explorerChildrenProvider, List<IRenameTreeItemHandler> renameTreeItemHandlers, List<IDeleteTreeItemHandler> deleteTreeItemHandlers, IExplorerServices explorerServices, IExplorerLabelService explorerLabelService) {
        this.labelService = Objects.requireNonNull(labelService);
        this.explorerElementsProvider = Objects.requireNonNull(explorerElementsProvider);
        this.explorerChildrenProvider = Objects.requireNonNull(explorerChildrenProvider);
        this.renameTreeItemHandlers = Objects.requireNonNull(renameTreeItemHandlers);
        this.deleteTreeItemHandlers = Objects.requireNonNull(deleteTreeItemHandlers);
        this.explorerServices = Objects.requireNonNull(explorerServices);
        this.explorerLabelService = Objects.requireNonNull(explorerLabelService);
    }


    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        var explorerTreeDescription = TreeDescription.newTreeDescription(DESCRIPTION_ID)
                .label(REPRESENTATION_NAME)
                .idProvider(this::getTreeId)
                .treeItemIdProvider(this::getTreeItemId)
                .kindProvider(this::getKind)
                .labelProvider(this::getLabel)
                .targetObjectIdProvider(variableManager -> variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).map(IEditingContext::getId).orElse(null))
                .parentObjectProvider(this::getParentObject)
                .treeItemIconURLsProvider(this::getImageURL)
                .editableProvider(this::isEditable)
                .deletableProvider(this::isDeletable)
                .selectableProvider(this::isSelectable)
                .elementsProvider(this.explorerElementsProvider::getElements)
                .hasChildrenProvider(this.explorerChildrenProvider::hasChildren)
                .childrenProvider(this.explorerChildrenProvider::getChildren)
                // This predicate will NOT be used while creating the explorer, but we don't want to see the description of the
                // explorer in the list of representations that can be created. Thus, we will return false all the time.
                .canCreatePredicate(variableManager -> false)
                .deleteHandler(this::getDeleteHandler)
                .renameHandler(this::getRenameHandler)
                .treeItemObjectProvider(this::getTreeItemObject)
                .treeItemLabelProvider(this::getLabel)
                .iconURLsProvider(variableManager -> List.of("/explorer/explorer.svg"))
                .build();
        return List.of(explorerTreeDescription);
    }

    private String getTreeId(VariableManager variableManager) {
        List<?> expandedObjects = variableManager.get(TreeRenderer.EXPANDED, List.class).orElse(List.of());
        List<?> activatedFilters = variableManager.get(TreeRenderer.ACTIVE_FILTER_IDS, List.class).orElse(List.of());
        return this.getExplorerTreeId(expandedObjects, activatedFilters);
    }

    private String getExplorerTreeId(List<?> expandedObjects, List<?> activatedFilters) {
        List<String> expandedObjectIds = expandedObjects.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        List<String> activatedFilterIds = activatedFilters.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(id -> URLEncoder.encode(id, StandardCharsets.UTF_8))
                .toList();

        return PREFIX + "?" + ExplorerEventProcessorFactory.TREE_DESCRIPTION_ID_PARAMETER + "=" + URLEncoder.encode(DESCRIPTION_ID, StandardCharsets.UTF_8) + "&expandedIds=[" + String.join(",", expandedObjectIds) + "]&activeFilterIds=[" + String.join(",", activatedFilterIds) + "]";
    }

    private String getTreeItemId(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        return this.explorerServices.getTreeItemId(self);
    }

    private String getKind(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        return this.explorerServices.getKind(self);
    }

    private StyledString getLabel(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        return this.labelService.getStyledLabel(self);
    }

    private boolean isEditable(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        return this.explorerLabelService.isEditable(self);
    }

    private boolean isDeletable(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        return this.explorerServices.isDeletable(self);
    }

    private boolean isSelectable(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        return this.explorerServices.isSelectable(self);
    }

    private List<String> getImageURL(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        return this.labelService.getImagePaths(self);
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
        return new Failure("");
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
        return new Failure("");
    }

    private Object getTreeItemObject(VariableManager variableManager) {
        var optionalTreeItemId = variableManager.get(TreeDescription.ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        return this.explorerServices.getTreeItemObject(optionalTreeItemId.orElse(null), optionalEditingContext.orElse(null));
    }

    private Object getParentObject(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        var optionalTreeItemId = variableManager.get(TreeDescription.ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        return this.explorerServices.getParent(self, optionalTreeItemId.orElse(null), optionalEditingContext.orElse(null));
    }

}
