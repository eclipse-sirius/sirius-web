/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.services.explorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.collaborative.trees.api.IExplorerDescriptionProvider;
import org.eclipse.sirius.components.compatibility.services.ImageConstants;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IKindParser;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.documents.DocumentMetadataAdapter;
import org.eclipse.sirius.web.services.explorer.api.IDeleteTreeItemHandler;
import org.eclipse.sirius.web.services.explorer.api.IRenameTreeItemHandler;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description of the explorer.
 *
 * @author hmarchadour
 */
@Service
public class ExplorerDescriptionProvider implements IExplorerDescriptionProvider {

    public static final String DESCRIPTION_ID = UUID.nameUUIDFromBytes("explorer_tree_description".getBytes()).toString(); //$NON-NLS-1$

    public static final String DOCUMENT_KIND = "siriusWeb://document"; //$NON-NLS-1$

    private final IObjectService objectService;

    private final IKindParser kindParser;

    private final IRepresentationService representationService;

    private final List<IRepresentationImageProvider> representationImageProviders;

    private final List<IRenameTreeItemHandler> renameTreeItemHandlers;

    private final List<IDeleteTreeItemHandler> deleteTreeItemHandlers;

    public ExplorerDescriptionProvider(IObjectService objectService, IKindParser kindParser, IRepresentationService representationService,
            List<IRepresentationImageProvider> representationImageProviders, List<IRenameTreeItemHandler> renameTreeItemHandlers, List<IDeleteTreeItemHandler> deleteTreeItemHandlers) {
        this.objectService = Objects.requireNonNull(objectService);
        this.kindParser = Objects.requireNonNull(kindParser);
        this.representationService = Objects.requireNonNull(representationService);
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
        this.renameTreeItemHandlers = Objects.requireNonNull(renameTreeItemHandlers);
        this.deleteTreeItemHandlers = Objects.requireNonNull(deleteTreeItemHandlers);
    }

    @Override
    public TreeDescription getDescription() {
        // This predicate will NOT be used while creating the explorer but we don't want to see the description of the
        // explorer in the list of representations that can be created. Thus, we will return false all the time.
        Predicate<VariableManager> canCreatePredicate = variableManager -> false;

        // @formatter:off
        return TreeDescription.newTreeDescription(DESCRIPTION_ID)
                .label("Explorer") //$NON-NLS-1$
                .idProvider(new GetOrCreateRandomIdProvider())
                .treeItemIdProvider(this::getTreeItemId)
                .kindProvider(this::getKind)
                .labelProvider(this::getLabel)
                .imageURLProvider(this::getImageURL)
                .editableProvider(this::isEditable)
                .deletableProvider(this::isDeletable)
                .elementsProvider(this::getElements)
                .hasChildrenProvider(this::hasChildren)
                .childrenProvider(this::getChildren)
                .canCreatePredicate(canCreatePredicate)
                .deleteHandler(this::getDeleteHandler)
                .renameHandler(this::getRenameHandler)
                .build();
        // @formatter:on
    }

    private String getTreeItemId(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        String id = null;
        if (self instanceof RepresentationMetadata) {
            id = ((RepresentationMetadata) self).getId();
        } else if (self instanceof Resource) {
            Resource resource = (Resource) self;
            id = resource.getURI().toString();
        } else if (self instanceof EObject) {
            id = this.objectService.getId(self);
        }
        return id;
    }

    private String getKind(VariableManager variableManager) {
        String kind = ""; //$NON-NLS-1$
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        if (self instanceof RepresentationMetadata) {
            RepresentationMetadata representationMetadata = (RepresentationMetadata) self;
            kind = representationMetadata.getKind();
        } else if (self instanceof Resource) {
            kind = DOCUMENT_KIND;
        } else {
            kind = this.objectService.getKind(self);
        }
        return kind;
    }

    private String getLabel(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        String label = ""; //$NON-NLS-1$
        if (self instanceof RepresentationMetadata) {
            label = ((RepresentationMetadata) self).getLabel();
        } else if (self instanceof Resource) {
            Resource resource = (Resource) self;
            // @formatter:off
            label = resource.eAdapters().stream()
                    .filter(DocumentMetadataAdapter.class::isInstance)
                    .map(DocumentMetadataAdapter.class::cast)
                    .findFirst()
                    .map(DocumentMetadataAdapter::getName)
                    .orElse(resource.getURI().lastSegment());
            // @formatter:on
        } else if (self instanceof EObject) {
            label = this.objectService.getLabel(self);
            if (label.isBlank()) {
                var kind = this.objectService.getKind(self);
                label = this.kindParser.getParameterValues(kind).get(SemanticKindConstants.ENTITY_ARGUMENT).get(0);
            }
        }
        return label;
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

    private String getImageURL(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        String imageURL = null;
        if (self instanceof EObject) {
            imageURL = this.objectService.getImagePath(self);
        } else if (self instanceof RepresentationMetadata) {
            RepresentationMetadata representationMetadata = (RepresentationMetadata) self;

            // @formatter:off
            imageURL = this.representationImageProviders.stream()
                    .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.getKind()))
                    .flatMap(Optional::stream)
                    .findFirst()
                    .orElse(ImageConstants.RESOURCE_SVG);
            // @formatter:on
        } else if (self instanceof Resource) {
            imageURL = ImageConstants.RESOURCE_SVG;
        }
        return Optional.ofNullable(imageURL).orElse(ImageConstants.DEFAULT_SVG);
    }

    private List<Object> getElements(VariableManager variableManager) {
        var optionalEditingContext = Optional.of(variableManager.getVariables().get(IEditingContext.EDITING_CONTEXT));
        // @formatter:off
        var optionalResourceSet = optionalEditingContext.filter(IEditingContext.class::isInstance)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain)
                .map(EditingDomain::getResourceSet);
        // @formatter:on

        if (optionalResourceSet.isPresent()) {
            var resourceSet = optionalResourceSet.get();
            return new ArrayList<>(resourceSet.getResources());
        }
        return new ArrayList<>();
    }

    private boolean hasChildren(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        boolean hasChildren = false;
        if (self instanceof Resource) {
            Resource resource = (Resource) self;
            hasChildren = !resource.getContents().isEmpty();
        } else if (self instanceof EObject) {
            EObject eObject = (EObject) self;
            hasChildren = !eObject.eContents().isEmpty();

            if (!hasChildren) {
                String id = this.objectService.getId(eObject);
                hasChildren = this.representationService.hasRepresentations(id);
            }
        }
        return hasChildren;
    }

    private List<Object> getChildren(VariableManager variableManager) {
        List<Object> result = new ArrayList<>();

        List<String> expandedIds = new ArrayList<>();
        Object objects = variableManager.getVariables().get(TreeRenderer.EXPANDED);
        if (objects instanceof List<?>) {
            List<?> list = (List<?>) objects;
            expandedIds = list.stream().filter(String.class::isInstance).map(String.class::cast).collect(Collectors.toUnmodifiableList());
        }

        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);

        if (optionalEditingContext.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();

            String id = this.getTreeItemId(variableManager);
            if (expandedIds.contains(id)) {
                Object self = variableManager.getVariables().get(VariableManager.SELF);

                if (self instanceof Resource) {
                    Resource resource = (Resource) self;
                    result.addAll(resource.getContents());
                } else if (self instanceof EObject) {
                    var representationMetadata = new ArrayList<>(this.representationService.findAllByTargetObjectId(editingContext, id));
                    representationMetadata.sort((metadata1, metadata2) -> metadata1.getLabel().compareTo(metadata2.getLabel()));
                    result.addAll(representationMetadata);
                    List<Object> contents = this.objectService.getContents(editingContext, id);
                    result.addAll(contents);
                }
            }
        }
        return result;
    }

    private IStatus getDeleteHandler(VariableManager variableManager) {
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        var optionalTreeItem = variableManager.get(TreeItem.SELECTED_TREE_ITEM, TreeItem.class);
        if (optionalEditingContext.isPresent() && optionalTreeItem.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();
            TreeItem treeItem = optionalTreeItem.get();

            // @formatter:off
            var optionalHandler = this.deleteTreeItemHandlers.stream().filter(handler -> handler.canHandle(editingContext, treeItem))
                    .findFirst();
            // @formatter:on

            if (optionalHandler.isPresent()) {
                IDeleteTreeItemHandler deleteTreeItemHandler = optionalHandler.get();
                return deleteTreeItemHandler.handle(editingContext, treeItem);
            }
        }
        return new Failure(""); //$NON-NLS-1$
    }

    private IStatus getRenameHandler(VariableManager variableManager, String newLabel) {
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        var optionalTreeItem = variableManager.get(TreeItem.SELECTED_TREE_ITEM, TreeItem.class);
        if (optionalEditingContext.isPresent() && optionalTreeItem.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();
            TreeItem treeItem = optionalTreeItem.get();

            // @formatter:off
            var optionalHandler = this.renameTreeItemHandlers.stream().filter(handler -> handler.canHandle(editingContext, treeItem, newLabel))
                    .findFirst();
            // @formatter:on

            if (optionalHandler.isPresent()) {
                IRenameTreeItemHandler renameTreeItemHandler = optionalHandler.get();
                return renameTreeItemHandler.handle(editingContext, treeItem, newLabel);
            }
        }
        return new Failure(""); //$NON-NLS-1$
    }
}
