/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import java.util.Comparator;
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
import org.eclipse.sirius.components.collaborative.trees.api.TreeConfiguration;
import org.eclipse.sirius.components.compatibility.services.ImageConstants;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.explorer.api.IDeleteTreeItemHandler;
import org.eclipse.sirius.web.services.explorer.api.IRenameTreeItemHandler;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description of the explorer.
 *
 * @author hmarchadour
 */
@Service
public class ExplorerDescriptionProvider implements IRepresentationDescriptionRegistryConfigurer {

    public static final String DESCRIPTION_ID = UUID.nameUUIDFromBytes("explorer_tree_description".getBytes()).toString();

    public static final String DOCUMENT_KIND = "siriusWeb://document";

    private final IObjectService objectService;

    private final IURLParser urlParser;

    private final IRepresentationService representationService;

    private final List<IRepresentationImageProvider> representationImageProviders;

    private final List<IRenameTreeItemHandler> renameTreeItemHandlers;

    private final List<IDeleteTreeItemHandler> deleteTreeItemHandlers;

    public ExplorerDescriptionProvider(IObjectService objectService, IURLParser urlParser, IRepresentationService representationService,
            List<IRepresentationImageProvider> representationImageProviders, List<IRenameTreeItemHandler> renameTreeItemHandlers, List<IDeleteTreeItemHandler> deleteTreeItemHandlers) {
        this.objectService = Objects.requireNonNull(objectService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.representationService = Objects.requireNonNull(representationService);
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
        this.renameTreeItemHandlers = Objects.requireNonNull(renameTreeItemHandlers);
        this.deleteTreeItemHandlers = Objects.requireNonNull(deleteTreeItemHandlers);
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        registry.add(this.getExplorerDescription());
    }

    public TreeDescription getExplorerDescription() {
        // This predicate will be called when the user tries to create a new representation, but in this case the
        // treeId variable is not defined, so it will never be instanciable in this way.
        // However the same predicate will also be called by TreeEventProcessorFactory to select the appropriate TreeDescription
        // when a frontend component subscribes to a treeEvent with a given treeId.
        Predicate<VariableManager> canCreatePredicate = variableManager -> {
            return variableManager.get(TreeConfiguration.TREE_ID, String.class).map(treeId -> treeId.startsWith("explorer://")).orElse(false);
        };

        return TreeDescription.newTreeDescription(DESCRIPTION_ID)
                .label("Explorer")
                .idProvider(new GetOrCreateRandomIdProvider())
                .treeItemIdProvider(this::getTreeItemId)
                .kindProvider(this::getKind)
                .labelProvider(this::getLabel)
                .iconURLProvider(this::getImageURL)
                .editableProvider(this::isEditable)
                .deletableProvider(this::isDeletable)
                .selectableProvider(this::isSelectable)
                .elementsProvider(this::getElements)
                .hasChildrenProvider(this::hasChildren)
                .childrenProvider(this::getChildren)
                .canCreatePredicate(canCreatePredicate)
                .deleteHandler(this::getDeleteHandler)
                .renameHandler(this::getRenameHandler)
                .build();
    }

    private String getTreeItemId(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        String id = null;
        if (self instanceof RepresentationMetadata representationMetadata) {
            id = representationMetadata.getId();
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
            }
            else {
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

        List<String> imageURL = List.of(ImageConstants.DEFAULT_SVG);
        if (self instanceof EObject) {
            imageURL = this.objectService.getImagePath(self);
        } else if (self instanceof RepresentationMetadata representationMetadata) {
            imageURL = this.representationImageProviders.stream()
                    .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.getKind()))
                    .flatMap(Optional::stream)
                    .toList();
        } else if (self instanceof Resource) {
            imageURL = List.of(ImageConstants.RESOURCE_SVG);
        }
        return imageURL;
    }

    private List<Resource> getElements(VariableManager variableManager) {
        var optionalEditingContext = Optional.of(variableManager.getVariables().get(IEditingContext.EDITING_CONTEXT));
        var optionalResourceSet = optionalEditingContext.filter(IEditingContext.class::isInstance)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet);

        if (optionalResourceSet.isPresent()) {
            return optionalResourceSet.get().getResources().stream()
                    .filter(res -> res.getURI() != null && IEMFEditingContext.RESOURCE_SCHEME.equals(res.getURI().scheme())
                            && !ColorPaletteService.SIRIUS_STUDIO_COLOR_PALETTES_URI.equals(res.getURI().toString()))
                    .sorted(Comparator.nullsLast(Comparator.comparing(this::getResourceLabel, String.CASE_INSENSITIVE_ORDER)))
                    .toList();
        }
        return new ArrayList<>();
    }

    private boolean hasChildren(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        boolean hasChildren = false;
        if (self instanceof Resource resource) {
            hasChildren = !resource.getContents().isEmpty();
        } else if (self instanceof EObject eObject) {
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
        if (objects instanceof List<?> list) {
            expandedIds = list.stream().filter(String.class::isInstance).map(String.class::cast).collect(Collectors.toUnmodifiableList());
        }

        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);

        if (optionalEditingContext.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();

            String id = this.getTreeItemId(variableManager);
            if (expandedIds.contains(id)) {
                Object self = variableManager.getVariables().get(VariableManager.SELF);

                if (self instanceof Resource resource) {
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

            var optionalHandler = this.deleteTreeItemHandlers.stream()
                    .filter(handler -> handler.canHandle(editingContext, treeItem))
                    .findFirst();

            if (optionalHandler.isPresent()) {
                IDeleteTreeItemHandler deleteTreeItemHandler = optionalHandler.get();
                return deleteTreeItemHandler.handle(editingContext, treeItem);
            }
        }
        return new Failure("");
    }

    private IStatus getRenameHandler(VariableManager variableManager, String newLabel) {
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        var optionalTreeItem = variableManager.get(TreeItem.SELECTED_TREE_ITEM, TreeItem.class);
        if (optionalEditingContext.isPresent() && optionalTreeItem.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();
            TreeItem treeItem = optionalTreeItem.get();

            var optionalHandler = this.renameTreeItemHandlers.stream()
                    .filter(handler -> handler.canHandle(editingContext, treeItem, newLabel))
                    .findFirst();

            if (optionalHandler.isPresent()) {
                IRenameTreeItemHandler renameTreeItemHandler = optionalHandler.get();
                return renameTreeItemHandler.handle(editingContext, treeItem, newLabel);
            }
        }
        return new Failure("");
    }
}
