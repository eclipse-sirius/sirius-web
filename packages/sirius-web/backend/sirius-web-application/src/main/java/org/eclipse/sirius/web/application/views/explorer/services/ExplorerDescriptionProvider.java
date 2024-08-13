/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.collaborative.trees.api.IDeleteTreeItemHandler;
import org.eclipse.sirius.components.collaborative.trees.api.IRenameTreeItemHandler;
import org.eclipse.sirius.components.core.CoreImageConstants;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerChildrenProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerElementsProvider;
import org.eclipse.sirius.web.application.views.explorer.services.configuration.ExplorerDescriptionProviderConfiguration;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description of the explorer.
 *
 * @author hmarchadour
 */
@Service
public class ExplorerDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String TREE_DESCRIPTION_ID_PARAMETER = "treeDescriptionId";

    public static final String DESCRIPTION_ID = "explorer_tree_description";

    public static final String DOCUMENT_KIND = "siriusWeb://document";

    public static final String PREFIX = "explorer://";

    public static final String REPRESENTATION_NAME = "Explorer";

    private final IObjectService objectService;

    private final IURLParser urlParser;

    private final List<IRepresentationImageProvider> representationImageProviders;

    private final IExplorerElementsProvider explorerElementsProvider;

    private final IExplorerChildrenProvider explorerChildrenProvider;

    private final List<IRenameTreeItemHandler> renameTreeItemHandlers;

    private final List<IDeleteTreeItemHandler> deleteTreeItemHandlers;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;


    public ExplorerDescriptionProvider(ExplorerDescriptionProviderConfiguration explorerDescriptionProviderConfiguration, List<IRepresentationImageProvider> representationImageProviders, IExplorerElementsProvider explorerElementsProvider, IExplorerChildrenProvider explorerChildrenProvider, List<IRenameTreeItemHandler> renameTreeItemHandlers, List<IDeleteTreeItemHandler> deleteTreeItemHandlers) {
        this.objectService = explorerDescriptionProviderConfiguration.getObjectService();
        this.urlParser = explorerDescriptionProviderConfiguration.getUrlParser();
        this.representationMetadataSearchService = explorerDescriptionProviderConfiguration.getRepresentationMetadataSearchService();
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
        this.explorerElementsProvider = Objects.requireNonNull(explorerElementsProvider);
        this.explorerChildrenProvider = Objects.requireNonNull(explorerChildrenProvider);
        this.renameTreeItemHandlers = Objects.requireNonNull(renameTreeItemHandlers);
        this.deleteTreeItemHandlers = Objects.requireNonNull(deleteTreeItemHandlers);
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
                .iconURLProvider(this::getImageURL)
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
                .contextMenuEntries(List.of())
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

        return PREFIX + "?" + TREE_DESCRIPTION_ID_PARAMETER + "=" + URLEncoder.encode(DESCRIPTION_ID, StandardCharsets.UTF_8) + "&expandedIds=[" + String.join(",", expandedObjectIds) + "]&activeFilterIds=[" + String.join(",", activatedFilterIds) + "]";
    }

    private String getTreeItemId(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        String id = null;
        if (self instanceof RepresentationMetadata representationMetadata) {
            id = representationMetadata.getId().toString();
        } else if (self instanceof Resource resource) {
            id = resource.getURI().path().substring(1);
        } else if (self instanceof EObject) {
            id = this.objectService.getId(self);
        }
        return id;
    }

    private String getKind(VariableManager variableManager) {
        String kind = "";
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        if (self instanceof RepresentationMetadata representationMetadata) {
            kind = representationMetadata.getKind();
        } else if (self instanceof Resource) {
            kind = DOCUMENT_KIND;
        } else {
            kind = this.objectService.getKind(self);
        }
        return kind;
    }

    private StyledString getLabel(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        String label = "";
        if (self instanceof RepresentationMetadata representationMetadata) {
            label = representationMetadata.getLabel();
        } else if (self instanceof Resource resource) {
            label = this.getResourceLabel(resource);
        } else if (self instanceof EObject) {
            StyledString styledString = this.objectService.getStyledLabel(self);
            if (!styledString.toString().isBlank()) {
                return styledString;
            } else {
                var kind = this.objectService.getKind(self);
                label = this.urlParser.getParameterValues(kind).get(SemanticKindConstants.ENTITY_ARGUMENT).get(0);
            }
        }
        return StyledString.of(label);
    }

    private String getResourceLabel(Resource resource) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .findFirst()
                .map(ResourceMetadataAdapter::getName)
                .orElse(resource.getURI().lastSegment());
    }

    private boolean isEditable(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        boolean editable = false;
        if (self instanceof RepresentationMetadata) {
            editable = true;
        } else if (self instanceof Resource) {
            editable = true;
        } else if (self instanceof EObject) {
            editable = this.objectService.isLabelEditable(self);
        }
        return editable;

    }

    private boolean isDeletable(VariableManager variableManager) {
        return true;
    }

    private boolean isSelectable(VariableManager variableManager) {
        return true;
    }

    private List<String> getImageURL(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        List<String> imageURL = List.of(CoreImageConstants.DEFAULT_SVG);
        if (self instanceof EObject) {
            imageURL = this.objectService.getImagePath(self);
        } else if (self instanceof RepresentationMetadata representationMetadata) {
            imageURL = this.representationImageProviders.stream()
                    .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.getKind()))
                    .flatMap(Optional::stream)
                    .toList();
        } else if (self instanceof Resource) {
            imageURL = List.of("/explorer/Resource.svg");
        }
        return imageURL;
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
        Object result = null;
        var optionalTreeItemId = variableManager.get(TreeDescription.ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);

        if (optionalEditingContext.isPresent() && optionalTreeItemId.isPresent()) {
            var treeItemId = optionalTreeItemId.get();
            var editingContext = optionalEditingContext.get();
            var optionalObject = this.objectService.getObject(editingContext, treeItemId);
            if (optionalObject.isPresent()) {
                result = optionalObject.get();
            } else {
                var optionalEditingDomain = Optional.of(editingContext)
                        .filter(IEMFEditingContext.class::isInstance)
                        .map(IEMFEditingContext.class::cast)
                        .map(IEMFEditingContext::getDomain);

                if (optionalEditingDomain.isPresent()) {
                    var editingDomain = optionalEditingDomain.get();
                    ResourceSet resourceSet = editingDomain.getResourceSet();
                    URI uri = new JSONResourceFactory().createResourceURI(treeItemId);

                    result = resourceSet.getResources().stream()
                            .filter(resource -> resource.getURI().equals(uri))
                            .findFirst()
                            .orElse(null);
                }
            }
        }
        return result;
    }

    private Object getParentObject(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        var optionalTreeItemId = variableManager.get(TreeDescription.ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        Object result = null;

        if (self instanceof RepresentationMetadata && optionalTreeItemId.isPresent() && optionalEditingContext.isPresent()) {
            var optionalRepresentationMetadata = new UUIDParser().parse(optionalTreeItemId.get()).flatMap(this.representationMetadataSearchService::findMetadataById);
            var repId = optionalRepresentationMetadata.map(RepresentationMetadata::getTargetObjectId).orElse(null);
            result = this.objectService.getObject(optionalEditingContext.get(), repId);
        } else if (self instanceof EObject eObject) {
            Object semanticContainer = eObject.eContainer();
            if (semanticContainer == null) {
                semanticContainer = eObject.eResource();
            }
            result = semanticContainer;
        }
        return result;
    }
}
