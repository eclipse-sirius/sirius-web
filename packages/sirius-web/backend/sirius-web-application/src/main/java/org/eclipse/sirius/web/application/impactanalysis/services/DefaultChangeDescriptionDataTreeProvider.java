/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.impactanalysis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.ChangeKind;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;
import org.eclipse.emf.ecore.change.ResourceChange;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IContentService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragment;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragmentStyle;
import org.eclipse.sirius.components.datatree.DataTree;
import org.eclipse.sirius.components.datatree.DataTreeNode;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.impactanalysis.services.api.IDefaultChangeDescriptionDataTreeProvider;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.springframework.stereotype.Service;

/**
 * Provides a data tree representing a given change description.
 *
 * @author gdaniel
 */
@Service
public class DefaultChangeDescriptionDataTreeProvider implements IDefaultChangeDescriptionDataTreeProvider {

    private static final String FEATURE_SEPARATOR = ": ";

    private final IIdentityService identityService;

    private final ILabelService labelService;

    private final IContentService contentService;

    public DefaultChangeDescriptionDataTreeProvider(IIdentityService identityService, ILabelService labelService, IContentService contentService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.contentService = Objects.requireNonNull(contentService);
    }

    @Override
    public Optional<DataTree> getDataTree(IEditingContext editingContext, ChangeDescription changeDescription) {
        Optional<DataTree> result = Optional.empty();
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            List<String> allImpactedObjectIds = this.getAllImpactedObjectIds(changeDescription);
            List<DataTreeNode> dataTreeNodes = new ArrayList<>();
            dataTreeNodes.addAll(this.createAllResourcesDataTreeNodes(emfEditingContext.getDomain().getResourceSet(), changeDescription.getResourceChanges()));
            for (Resource resource : emfEditingContext.getDomain().getResourceSet().getResources()) {
                String resourceId = this.getId(resource);
                if (allImpactedObjectIds.contains(resourceId)) {
                    dataTreeNodes.add(new DataTreeNode(resourceId, null, this.getStyledLabel(resource), this.getIconURLs(resource), List.of(List.of())));
                    List<Object> children = this.getChildren(resource, changeDescription);
                    for (Object child : children) {
                        dataTreeNodes.addAll(this.getObjectChangeDataTreeNode(child, resource, changeDescription, allImpactedObjectIds));
                    }
                }
            }
            DataTree dataTree = new DataTree("impact_tree", dataTreeNodes);
            result = Optional.of(dataTree);
        }
        return result;
    }

    private List<DataTreeNode> createAllResourcesDataTreeNodes(ResourceSet resourceSet, List<ResourceChange> resourceChanges) {
        List<DataTreeNode> result = new ArrayList<>();
        for (ResourceChange resourceChange : resourceChanges) {
            for (ListChange listChange : resourceChange.getListChanges()) {
                if (listChange.getKind() == ChangeKind.ADD_LITERAL) {
                    Resource changedResource = resourceChange.getResource();
                    List<?> resourceContent = resourceChange.getValue();
                    result.addAll(this.createResourceDataTreeNodes(changedResource, resourceContent, listChange.getKind()));
                    resourceSet.getResources().stream()
                        .filter(resource -> Objects.equals(resource.getURI(), changedResource.getURI()))
                        .findFirst()
                        .ifPresent(resourceWithSameURI -> {
                            // There is a resource in the editing context with the same ID, this change is not
                            // tracked by the change description.
                            // This is for example the case when updating a library: the old version is removed, the
                            // new one is added, but the change description will only contain the removal.
                            result.addAll(this.createResourceDataTreeNodes(resourceWithSameURI, resourceWithSameURI.getContents(), ChangeKind.REMOVE_LITERAL));
                        });
                } else if (listChange.getKind() == ChangeKind.REMOVE_LITERAL) {
                    Resource changedResource = resourceChange.getResource();
                    List<?> resourceContent = resourceChange.getValue();
                    result.addAll(this.createResourceDataTreeNodes(changedResource, resourceContent, listChange.getKind()));
                } else if (listChange.getKind() == ChangeKind.MOVE_LITERAL) {
                    // MOVE_LITERAL is not supported for now.
                }
            }
        }
        return result;
    }

    private List<DataTreeNode> createResourceDataTreeNodes(Resource resource, List<?> resourceContent, ChangeKind changeKind) {
        List<DataTreeNode> result = new ArrayList<>();
        // We need to add a suffix to the ids to ensure they remain unique in case we display 2 resources with the same
        // ID. This is for example the case when updating a library, where both the old and new version have the
        // same ID.
        String idSuffix = "#" + changeKind.getName();
        String resourceId = this.getId(resource) + idSuffix;
        String resourceLabel = this.labelService.getStyledLabel(resource) + this.getLibraryLabelFragment(resource).orElse("");
        Optional<StyledString> optionalStyledString = this.getResourceNodeStyledString(resourceLabel, changeKind);
        Optional<List<List<String>>> optionalEndIconsURLs = this.getResourceNodeEndIconsURLs(changeKind);
        if (optionalStyledString.isPresent() && optionalEndIconsURLs.isPresent()) {
            result.add(new DataTreeNode(resourceId, null, optionalStyledString.get(), this.getIconURLs(resource), optionalEndIconsURLs.get()));
            for (Object resourceObject : resourceContent) {
                result.addAll(this.createResourceChangeContentDataTreeNode(resourceObject, resource, changeKind));
            }
        }
        return result;
    }

    private List<DataTreeNode> createResourceChangeContentDataTreeNode(Object object, Object parent, ChangeKind changeKind) {
        List<DataTreeNode> result = new ArrayList<>();
        String idSuffix = "#" + changeKind.getName();
        String objectId = this.getId(object) + idSuffix;
        String objectLabel = this.labelService.getStyledLabel(object).toString();
        Optional<StyledString> optionalStyledString = this.getResourceNodeStyledString(objectLabel, changeKind);
        Optional<List<List<String>>> optionalEndIconsURLs = this.getResourceNodeEndIconsURLs(changeKind);
        if (optionalStyledString.isPresent() && optionalEndIconsURLs.isPresent()) {
            result.add(new DataTreeNode(objectId, this.getId(parent) + idSuffix, optionalStyledString.get(), this.getIconURLs(object), optionalEndIconsURLs.get()));
            List<Object> children = this.contentService.getContents(object);
            for (Object child : children) {
                result.addAll(this.createResourceChangeContentDataTreeNode(child, object, changeKind));
            }
        }
        return result;
    }

    private Optional<StyledString> getResourceNodeStyledString(String label, ChangeKind changeKind) {
        Optional<StyledString> result = Optional.empty();
        if (changeKind == ChangeKind.ADD_LITERAL) {
            result = Optional.of(this.getDeletionStyledString(label));
        } else if (changeKind == ChangeKind.REMOVE_LITERAL) {
            result = Optional.of(this.getAdditionStyledString(label));
        }
        return result;
    }

    private Optional<List<List<String>>> getResourceNodeEndIconsURLs(ChangeKind changeKind) {
        Optional<List<List<String>>> result = Optional.empty();
        if (changeKind == ChangeKind.ADD_LITERAL) {
            result = Optional.of(this.getDeletionEndIconsURLs());
        } else if (changeKind == ChangeKind.REMOVE_LITERAL) {
            result = Optional.of(this.getAdditionEndIconsURLs());
        }
        return result;
    }

    private Optional<String> getLibraryLabelFragment(Resource resource) {
        return resource.eAdapters().stream()
            .filter(LibraryMetadataAdapter.class::isInstance)
            .map(LibraryMetadataAdapter.class::cast)
            .map(libraryMetadataAdapter -> " (" + libraryMetadataAdapter.getName() + "@" + libraryMetadataAdapter.getVersion() + ")")
            .findFirst();
    }

    private List<DataTreeNode> getObjectChangeDataTreeNode(Object object, Object parent, ChangeDescription changeDescription, List<String> allImpactObjectIds) {
        List<DataTreeNode> result = new ArrayList<>();
        String objectId = this.getId(object);
        if (this.isFeatureChange(object)
                || allImpactObjectIds.contains(objectId)) {
            result.add(new DataTreeNode(objectId, this.getId(parent), this.getStyledLabel(object), this.getIconURLs(object), this.getEndIconsURL(object, changeDescription)));
            List<Object> children = this.getChildren(object, changeDescription);
            for (Object child : children) {
                result.addAll(this.getObjectChangeDataTreeNode(child, object, changeDescription, allImpactObjectIds));
            }
        }
        return result;
    }

    private boolean isFeatureChange(Object object) {
        return object instanceof FeatureAddition
            || object instanceof FeatureDeletion
            || object instanceof FeatureModification;
    }

    private String getId(Object object) {
        String result;
        if (object instanceof FeatureAddition || object instanceof FeatureDeletion || object instanceof FeatureModification) {
            // For now these elements are always leaves of the tree, so they don't need to have an identifier we would
            // want to reuse as parent of other nodes. We just ensure the ID is unique.
            result = UUID.randomUUID().toString();
        } else {
            result = this.identityService.getId(object);
        }
        return result;
    }

    private List<String> getIconURLs(Object object) {
        List<String> result = List.of();
        if (this.isFeatureChange(object)) {
            if (object instanceof FeatureAddition featureAddition) {
                result = this.labelService.getImagePaths(featureAddition.newValue());
            } else if (object instanceof FeatureDeletion featureDeletion) {
                result = this.labelService.getImagePaths(featureDeletion.oldValue());
            } else if (object instanceof FeatureModification featureModification) {
                result = this.labelService.getImagePaths(featureModification.newValue());
            }
        } else {
            result = this.labelService.getImagePaths(object);
        }
        return result;
    }

    private StyledString getStyledLabel(Object object) {
        final StyledString result;
        if (this.isFeatureChange(object)) {
            String label = "";
            if (object instanceof FeatureAddition featureAddition) {
                label = featureAddition.feature() + FEATURE_SEPARATOR + this.getFeatureObjectLabel(featureAddition.newValue());
                result = this.getAdditionStyledString(label);
            } else if (object instanceof FeatureDeletion featureDeletion) {
                label = featureDeletion.feature() + FEATURE_SEPARATOR + this.getFeatureObjectLabel(featureDeletion.oldValue());
                result = this.getDeletionStyledString(label);
            } else if (object instanceof FeatureModification featureModification) {
                label = featureModification.feature() + FEATURE_SEPARATOR + this.getFeatureObjectLabel(featureModification.oldValue()) + " -> " + this.getFeatureObjectLabel(featureModification.newValue());
                result = this.getModificationStyledString(label);
            } else {
                result = this.getStyledString("", "#000000");
            }
        } else {
            result = this.labelService.getStyledLabel(object);
        }
        return result;
    }

    private StyledString getAdditionStyledString(String label) {
        return this.getStyledString(label, "#48752C");
    }

    private StyledString getDeletionStyledString(String label) {
        return this.getStyledString(label, "#BB271A");
    }

    private StyledString getModificationStyledString(String label) {
        return this.getStyledString(label, "#000000");

    }

    private StyledString getStyledString(String label, String foregroundColor) {
        return new StyledString(List.of(
                new StyledStringFragment(label, StyledStringFragmentStyle.newDefaultStyledStringFragmentStyle()
                        .foregroundColor(foregroundColor)
                        .build())
                ));
    }

    private String getFeatureObjectLabel(Object object) {
        String result;
        if (object instanceof EObject) {
            result = this.labelService.getStyledLabel(object).toString();
        } else {
            result = object.toString();
        }
        return result;
    }

    private List<List<String>> getEndIconsURL(Object object, ChangeDescription changeDescription) {
        List<List<String>> result = new ArrayList<>();
        if (object instanceof FeatureAddition featureAddition) {
            result = this.getAdditionEndIconsURLs();
        } else if (object instanceof FeatureDeletion featureDeletion) {
            result = this.getDeletionEndIconsURLs();
        } else if (object instanceof FeatureModification featureModification) {
            result = this.getModificationEndIconsURLs();
        } else if (changeDescription.getObjectChanges().keySet().contains(object)) {
            result.add(List.of("/impact-analysis/ChangeMarker.svg"));
        }
        return result;
    }

    private List<List<String>> getAdditionEndIconsURLs() {
        return List.of(List.of("/impact-analysis/FeatureAddition.svg"));
    }

    private List<List<String>> getDeletionEndIconsURLs() {
        return List.of(List.of("/impact-analysis/FeatureDeletion.svg"));
    }

    private List<List<String>> getModificationEndIconsURLs() {
        return List.of(List.of("/impact-analysis/FeatureModification.svg"));
    }

    private List<Object> getChildren(Object object, ChangeDescription changeDescription) {
        List<Object> children = new ArrayList<>();
        for (Entry<EObject, EList<FeatureChange>> changes : changeDescription.getObjectChanges().entrySet()) {
            if (changes.getKey().equals(object)) {
                for (FeatureChange featureChange : changes.getValue()) {
                    children.addAll(this.getChildren(changes.getKey(), featureChange));
                }
            }
        }
        children.addAll(this.contentService.getContents(object));
        return children;
    }

    private List<Object> getChildren(EObject changedEObject, FeatureChange featureChange) {
        final List<Object> children;
        if (featureChange.getFeature().isMany()) {
            children = this.getChildrenForMultiValuedFeatureChange(changedEObject, featureChange);
        } else {
            children = this.getChildrenForSingleValuedFeatureChange(changedEObject, featureChange);
        }
        return children;
    }

    private List<Object> getChildrenForMultiValuedFeatureChange(EObject changedEObject, FeatureChange featureChange) {
        List<Object> children = new ArrayList<>();
        if (featureChange.getListChanges().isEmpty()) {
            // No list change indicates that the feature was previously empty, in this case all the values present in
            // the feature are addition.
            List<Object> addedObjects = (List) changedEObject.eGet(featureChange.getFeature());
            for (Object addedObject : addedObjects) {
                children.add(new FeatureAddition(changedEObject, featureChange.getFeatureName(), addedObject));
            }
        }
        for (ListChange listChange : featureChange.getListChanges()) {
            if (listChange.getKind() == ChangeKind.ADD_LITERAL) {
                // The element has been removed.
                for (EObject referenceValue : listChange.getReferenceValues()) {
                    children.add(new FeatureDeletion(changedEObject, featureChange.getFeatureName(), referenceValue));
                }
            } else if (listChange.getKind() == ChangeKind.REMOVE_LITERAL) {
                // We know it is a list, the feature is multi-valued.
                List<?> changedEObjectFeatureValue = (List) changedEObject.eGet(featureChange.getFeature());
                // REMOVE indices get shifted if there are multiple remove as part of the same feature change.
                // For example, adding 2 elements to an empty list will produce 2 REMOVE changes for the index 0.
                // Since we are reading the updated model, we need to un-shift these indices to access the actual value
                // that was added.
                int previousRemoveCount = this.getPreviousRemoveCount(listChange, featureChange.getListChanges());
                if (changedEObjectFeatureValue.size() > listChange.getIndex() + previousRemoveCount) {
                    Object addedObject = changedEObjectFeatureValue.get(listChange.getIndex() + previousRemoveCount);
                    children.add(new FeatureAddition(changedEObject, featureChange.getFeatureName(), addedObject));
                }
            } else if (listChange.getKind() == ChangeKind.MOVE_LITERAL) {
                // MOVE_LITERAL is not supported for now.
            }
        }
        return children;
    }

    private int getPreviousRemoveCount(ListChange listChange, List<ListChange> listChanges) {
        int result = 0;
        for (ListChange previousListChange : listChanges) {
            if (Objects.equals(previousListChange, listChange)) {
                break;
            }
            if (previousListChange.getKind() == ChangeKind.REMOVE_LITERAL) {
                result++;
            }
        }
        return result;
    }

    private List<Object> getChildrenForSingleValuedFeatureChange(EObject changedEObject, FeatureChange featureChange) {
        List<Object> children = List.of();
        if (featureChange.getFeature() instanceof EReference) {
            children = this.getChildrenForSingleValuedEReferenceChange(changedEObject, featureChange);
        } else if (featureChange.getFeature() instanceof EAttribute) {
            children = this.getChildrenForSingleValuedEAttributeChange(changedEObject, featureChange);
        }
        return children;
    }

    private List<Object> getChildrenForSingleValuedEReferenceChange(EObject changedEObject, FeatureChange featureChange) {
        List<Object> children = new ArrayList<>();
        if (featureChange.getValue() == null || (featureChange.getValue() instanceof List valueList && valueList.isEmpty())) {
            Object changedEReferenceValue = changedEObject.eGet(featureChange.getFeature());
            if (changedEReferenceValue instanceof List changedReferenceValueList) {
                if (changedReferenceValueList.size() > 0) {
                    // An object was added, and since the change description contains a null or empty value, we know
                    // it has been added in first position.
                    children.add(new FeatureAddition(changedEObject, featureChange.getFeatureName(), changedReferenceValueList.get(0)));
                }
            } else {
                children.add(new FeatureAddition(changedEObject, featureChange.getFeatureName(), changedEReferenceValue));
            }
        } else {
            children.add(new FeatureDeletion(changedEObject, featureChange.getFeatureName(), featureChange.getValue()));
        }
        return children;
    }

    private List<Object> getChildrenForSingleValuedEAttributeChange(EObject changedEObject, FeatureChange featureChange) {
        List<Object> children = new ArrayList<>();
        Object changedEAttributeValue = changedEObject.eGet(featureChange.getFeature());
        if (featureChange.getValue() != null) {
            if (changedEAttributeValue != null) {
                children.add(new FeatureModification(changedEObject, featureChange.getFeatureName(), featureChange.getValue(), changedEAttributeValue));
            } else {
                children.add(new FeatureDeletion(changedEObject, featureChange.getFeatureName(), featureChange.getValue()));
            }
        } else {
            if (changedEAttributeValue != null) {
                children.add(new FeatureAddition(changedEObject, featureChange.getFeatureName(), changedEAttributeValue));
            }
            // Do not handle the case where changedEAttributeValue is null: if the old value and the new value are both
            // null there is no change.
        }
        return children;
    }

    private List<String> getAllImpactedObjectIds(ChangeDescription changeDescription) {
        List<String> result = new ArrayList<>();
        for (EObject changedObject : changeDescription.getObjectChanges().keySet()) {
            String changedObjectId = this.identityService.getId(changedObject);
            result.add(changedObjectId);
            Optional<Object> optionalParent = this.getParent(changedObject);
            while (optionalParent.isPresent()) {
                Object parent = optionalParent.get();
                String parentId = this.identityService.getId(parent);
                result.add(parentId);
                optionalParent = this.getParent(parent);
            }
        }
        return result;
    }

    private Optional<Object> getParent(Object object) {
        Optional<Object> result = Optional.empty();
        if (object instanceof EObject eObject) {
            Object semanticContainer = eObject.eContainer();
            if (semanticContainer == null) {
                semanticContainer = eObject.eResource();
            }
            result = Optional.ofNullable(semanticContainer);
        }
        return result;
    }

}
