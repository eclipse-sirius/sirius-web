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
package org.eclipse.sirius.web.application.views.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.core.CoreImageConstants;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationIconURL;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description of a tree on Domain element. <br>
 * This is an example to demonstrate how to use a tree description which is not a tree explorer.
 *
 * @author Jerome Gout
 */
@Service
public class DomainTreeRepresentationDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String DESCRIPTION_ID = "domain_tree_description";

    public static final String SETTING = "setting:";

    public static final String SETTING_ID_SEPARATOR = "::";

    private final IObjectService objectService;

    private final IIdentityService identityService;

    private final ILabelService labelService;

    private final IURLParser urlParser;

    private final List<IRepresentationImageProvider> representationImageProviders;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    public DomainTreeRepresentationDescriptionProvider(IObjectService objectService, IIdentityService identityService, ILabelService labelService, IURLParser urlParser, List<IRepresentationImageProvider> representationImageProviders, IRepresentationMetadataSearchService representationMetadataSearchService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {

        var treeDescription = TreeDescription.newTreeDescription(DESCRIPTION_ID)
                .label("Domain Tree")
                .idProvider(new GetOrCreateRandomIdProvider())
                .canCreatePredicate(this::canCreate)
                .treeItemIdProvider(this::getTreeItemId)
                .kindProvider(this::getKind)
                .labelProvider(this::getLabel)
                .treeItemLabelProvider(this::getLabel)
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .treeItemIconURLsProvider(this::getImageURL)
                .editableProvider(variableManager -> false)
                .deletableProvider(variableManager -> false)
                .selectableProvider(variableManager -> true)
                .elementsProvider(this::getElements)
                .hasChildrenProvider(this::hasChildren)
                .childrenProvider(this::getChildren)
                .parentObjectProvider(this::getParentObject)
                .deleteHandler(variableManager -> new Success())
                .renameHandler((variableManager, newName) -> new Success())
                .treeItemObjectProvider(this::getTreeItemObject)
                .iconURLsProvider(variableManager -> List.of())
                .build();
        return List.of(treeDescription);
    }

    private boolean canCreate(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        return self instanceof Domain;
    }

    private String getTreeItemId(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        String id = null;
        if (self instanceof RepresentationMetadata representationMetadata) {
            id = representationMetadata.getId().toString();
        } else if (self instanceof Resource || self instanceof EObject) {
            id = this.identityService.getId(self);
        } else if (self instanceof Setting setting) {
            id = SETTING + this.identityService.getId(setting.getEObject()) + SETTING_ID_SEPARATOR + setting.getEStructuralFeature().getName();
        }
        return id;
    }

    private String getKind(VariableManager variableManager) {
        String kind = "";
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        if (self instanceof RepresentationMetadata representationMetadata) {
            kind = representationMetadata.getKind();
        } else if (self instanceof Setting) {
            kind = "setting";
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
        } else if (self instanceof EObject) {
            label = this.labelService.getStyledLabel(self).toString();
            if (label.isBlank()) {
                var kind = this.objectService.getKind(self);
                label = this.urlParser.getParameterValues(kind).get(SemanticKindConstants.ENTITY_ARGUMENT).get(0);
            }
        } else if (self instanceof Setting setting) {
            label = setting.getEStructuralFeature().getName();
        }
        return StyledString.of(label);
    }

    private List<String> getImageURL(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        List<String> imageURL = List.of(CoreImageConstants.DEFAULT_SVG);
        if (self instanceof EObject) {
            imageURL = this.labelService.getImagePaths(self);
        } else if (self instanceof RepresentationMetadata representationMetadata) {
            if (representationMetadata.getIconURLs().isEmpty()) {
                imageURL = this.representationImageProviders.stream()
                        .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.getKind()))
                        .flatMap(Optional::stream)
                        .toList();
            } else {
                imageURL = representationMetadata.getIconURLs().stream()
                        .map(RepresentationIconURL::url)
                        .toList();
            }
        }
        return imageURL;
    }

    private List<Object> getElements(VariableManager variableManager) {
        List<Object> elements = new ArrayList<>();
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        if (self instanceof Domain domain) {
            elements.add(domain);
        }
        return elements;
    }

    private boolean hasChildren(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);

        boolean hasChildren = false;
        if (self instanceof EObject eObject) {
            hasChildren = !eObject.eContents().isEmpty();
            var optionalSemanticDataId = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                    .map(IEditingContext::getId)
                    .flatMap(semanticDataId -> new UUIDParser().parse(semanticDataId));

            if (!hasChildren && optionalSemanticDataId.isPresent()) {
                String id = this.identityService.getId(eObject);
                var semanticDataId = optionalSemanticDataId.get();
                hasChildren = this.representationMetadataSearchService.existAnyRepresentationMetadataForSemanticDataAndTargetObjectId(AggregateReference.to(semanticDataId), id);
            }

            if (!hasChildren && self instanceof Entity) {
                hasChildren = true;
            }
        } else if (self instanceof Setting setting) {
            var value = setting.get(true);
            if (value instanceof Collection<?> collection) {
                hasChildren = !collection.isEmpty();
            }
        }
        return hasChildren;
    }

    public List<Object> getChildren(VariableManager variableManager) {
        List<Object> children = new ArrayList<>();
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        if (self != null) {
            children = this.getDefaultChildren(variableManager);
        }
        return children;
    }

    private List<Object> getDefaultChildren(VariableManager variableManager) {
        List<Object> result = new ArrayList<>();
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        List<String> expandedIds = new ArrayList<>();
        Object objects = variableManager.getVariables().get(TreeRenderer.EXPANDED);
        if (objects instanceof List<?> list) {
            expandedIds = list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .toList();
        }
        String id = this.getTreeItemId(variableManager);
        if (expandedIds.contains(id)) {
            if (self instanceof EObject) {
                var optionalSemanticDataId = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                        .map(IEditingContext::getId)
                        .flatMap(semanticDataId -> new UUIDParser().parse(semanticDataId));
                if (optionalSemanticDataId.isPresent()) {
                    var semanticDataId = optionalSemanticDataId.get();
                    var representationMetadata = new ArrayList<>(this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticDataAndTargetObjectId(AggregateReference.to(semanticDataId), id));
                    representationMetadata.sort(Comparator.comparing(RepresentationMetadata::getLabel));
                    result.addAll(representationMetadata);
                }
                List<Object> contents = this.objectService.getContents(self);
                if (self instanceof Entity entity) {
                    result.add(((InternalEObject) entity).eSetting(entity.eClass().getEStructuralFeature("superTypes")));
                }
                result.addAll(contents);
            } else if (self instanceof Setting setting) {
                var value = setting.get(true);
                if (value instanceof Collection<?> collection) {
                    result.addAll(collection);
                }
            }
        }
        return result;
    }

    private Object getTreeItemObject(VariableManager variableManager) {
        Object result = null;
        var optionalTreeItemId = variableManager.get(TreeDescription.ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);

        if (optionalEditingContext.isPresent() && optionalTreeItemId.isPresent()) {
            var treeItemId = optionalTreeItemId.get();
            var editingContext = optionalEditingContext.get();

            if (treeItemId.startsWith(SETTING)) {
                // the tree item is a setting, get the object and then the structural feature associated
                var objectId = treeItemId.substring(SETTING.length(), treeItemId.indexOf(SETTING_ID_SEPARATOR));
                var featureName = treeItemId.substring(treeItemId.indexOf(SETTING_ID_SEPARATOR) + SETTING_ID_SEPARATOR.length());
                var optObject = this.objectService.getObject(editingContext, objectId);
                if (optObject.isPresent()) {
                    InternalEObject internalObject = (InternalEObject) optObject.get();
                    result = internalObject.eSetting(internalObject.eClass().getEStructuralFeature(featureName));
                }
            } else {
                var optionalObject = this.objectService.getObject(editingContext, treeItemId);
                if (optionalObject.isPresent()) {
                    result = optionalObject.get();
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
            var optionalObject = this.objectService.getObject(optionalEditingContext.get(), repId);
            if (optionalObject.isPresent()) {
                result = optionalObject.get();
            }
        } else if (self instanceof EObject eObject) {
            Object semanticContainer = eObject.eContainer();
            if (semanticContainer == null) {
                semanticContainer = eObject.eResource();
            }
            result = semanticContainer;
        } else if (self instanceof Setting setting) {
            // the parent of the superTypes node is the object associated to this Setting
            result = setting.getEObject();
        }
        return result;
    }
}
