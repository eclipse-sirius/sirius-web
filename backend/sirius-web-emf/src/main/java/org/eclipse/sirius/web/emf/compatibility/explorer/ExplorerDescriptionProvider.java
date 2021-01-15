/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.emf.compatibility.explorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationImageProvider;
import org.eclipse.sirius.web.collaborative.trees.api.IExplorerDescriptionProvider;
import org.eclipse.sirius.web.compat.services.ImageConstants;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.emf.services.DocumentMetadataAdapter;
import org.eclipse.sirius.web.emf.services.EditingContext;
import org.eclipse.sirius.web.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.trees.description.TreeDescription;
import org.eclipse.sirius.web.trees.renderer.TreeRenderer;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description of the explorer.
 *
 * @author hmarchadour
 */
@Service
public class ExplorerDescriptionProvider implements IExplorerDescriptionProvider {

    private final IObjectService objectService;

    private final IRepresentationService representationService;

    private final List<IRepresentationImageProvider> representationImageProviders;

    public ExplorerDescriptionProvider(IObjectService objectService, IRepresentationService representationService, List<IRepresentationImageProvider> representationImageProviders) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationService = Objects.requireNonNull(representationService);
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
    }

    @Override
    public TreeDescription getDescription() {
        // This predicate will NOT be used while creating the explorer but we don't want to see the description of the
        // explorer in the list of representations that can be created. Thus, we will return false all the time.
        Predicate<VariableManager> canCreatePredicate = variableManager -> false;

        // @formatter:off
        return TreeDescription.newTreeDescription(IRepresentationDescriptionService.EXPLORER_TREE_DESCRIPTION)
                .label("Explorer") //$NON-NLS-1$
                .idProvider(new GetOrCreateRandomIdProvider())
                .treeItemIdProvider(this::getTreeItemId)
                .kindProvider(this::getKind)
                .labelProvider(this::getLabel)
                .editableProvider(this::isEditable)
                .imageURLProvider(this::getImageURL)
                .elementsProvider(this::getElements)
                .hasChildrenProvider(this::hasChildren)
                .childrenProvider(this::getChildren)
                .canCreatePredicate(canCreatePredicate)
                .build();
        // @formatter:on
    }

    private String getTreeItemId(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        String id = null;
        if (self instanceof RepresentationDescriptor) {
            id = ((RepresentationDescriptor) self).getId().toString();
        } else if (self instanceof Resource) {
            Resource resource = (Resource) self;
            id = resource.getURI().toString();
        } else if (self instanceof EObject) {
            id = this.objectService.getId(self);
        }
        return id;
    }

    private String getKind(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        return this.objectService.getKind(self);
    }

    private String getLabel(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        String label = ""; //$NON-NLS-1$
        if (self instanceof RepresentationDescriptor) {
            label = ((RepresentationDescriptor) self).getLabel();
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
        }
        return label;
    }

    private boolean isEditable(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        boolean editable = false;
        if (self instanceof RepresentationDescriptor) {
            editable = true;
        } else if (self instanceof Resource) {
            editable = true;
        } else if (self instanceof EObject) {
            editable = this.objectService.isLabelEditable(self);
        }
        return editable;

    }

    private String getImageURL(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        String imageURL = null;
        if (self instanceof EObject) {
            String imagePath = this.objectService.getImagePath(self);
            imageURL = imagePath;
        } else if (self instanceof RepresentationDescriptor) {
            RepresentationDescriptor representationDescriptor = (RepresentationDescriptor) self;

            // @formatter:off
            imageURL = this.representationImageProviders.stream()
                    .map(representationImageProvider -> representationImageProvider.getImageURL(representationDescriptor.getRepresentation()))
                    .flatMap(Optional::stream)
                    .findFirst()
                    .orElse(ImageConstants.RESOURCE_SVG);
            // @formatter:on
        } else if (self instanceof Resource) {
            imageURL = ImageConstants.RESOURCE_SVG;
        }
        return imageURL;
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

        Object editingContext = variableManager.getVariables().get(IEditingContext.EDITING_CONTEXT);

        if (editingContext instanceof IEditingContext) {
            IEditingContext context = (IEditingContext) editingContext;

            String id = this.getTreeItemId(variableManager);
            if (expandedIds.contains(id)) {
                Object self = variableManager.getVariables().get(VariableManager.SELF);

                if (self instanceof Resource) {
                    Resource resource = (Resource) self;
                    result.addAll(resource.getContents());
                } else if (self instanceof EObject) {
                    var representationDescriptors = new ArrayList<>(this.representationService.getRepresentationDescriptorsForObjectId(id));
                    representationDescriptors.sort((descriptor1, descriptor2) -> descriptor1.getLabel().compareTo(descriptor2.getLabel()));
                    result.addAll(representationDescriptors);
                    List<Object> contents = this.objectService.getContents(context, id);
                    result.addAll(contents);
                }
            }
        }
        return result;
    }
}
