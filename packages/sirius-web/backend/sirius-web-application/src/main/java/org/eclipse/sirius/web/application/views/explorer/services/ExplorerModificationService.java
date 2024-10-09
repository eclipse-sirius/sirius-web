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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.application.messages.ISiriusWebMessageService;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerModificationService;
import org.springframework.stereotype.Service;

/**
 * Service related to modification from Explorer.
 *
 * @author frouene
 */
@Service
public class ExplorerModificationService implements IExplorerModificationService {

    private final IObjectService objectService;

    private final ISiriusWebMessageService siriusWebMessageService;

    public ExplorerModificationService(IObjectService objectService, ISiriusWebMessageService siriusWebMessageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.siriusWebMessageService = Objects.requireNonNull(siriusWebMessageService);
    }

    @Override
    public IStatus moveObject(IEditingContext editingContext, Tree tree, List<String> objectToMoveIds, String containerId, int index) {
        List<EObject> objectsToMove = this.getObjectsToMove(editingContext, objectToMoveIds);
        Optional<EObject> targetContainer = this.getTargetContainer(editingContext, containerId, index);

        return targetContainer.map(eObject -> this.moveObjects(objectsToMove, eObject, tree, index)).orElse(new Failure(this.siriusWebMessageService.unavailableFeature()));
    }

    private List<EObject> getObjectsToMove(IEditingContext editingContext, List<String> objectToMoveIds) {
        return objectToMoveIds.stream()
                .map(objectToMoveId -> this.objectService.getObject(editingContext, objectToMoveId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .toList();
    }

    private Optional<EObject> getTargetContainer(IEditingContext editingContext, String containerId, int index) {
        Optional<EObject> containerEObjectOpt = this.objectService.getObject(editingContext, containerId)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
        if (index >= 0) {
            return containerEObjectOpt.map(EObject::eContainer);
        }
        return containerEObjectOpt;
    }

    private IStatus moveObjects(List<EObject> objectsToMove, EObject targetContainer, Tree tree, int index) {
        List<TreeItem> targetSiblings = tree.getChildren()
                .stream()
                .flatMap(children -> this.getContainerChild(children, this.objectService.getId(targetContainer)).stream())
                .toList();
        return objectsToMove.stream()
                .map(source -> {
                    Optional<EStructuralFeature> optionalFeature = this.getContainmentFeatureName(source, targetContainer)
                            .map(featureName -> targetContainer.eClass().getEStructuralFeature(featureName));
                    return optionalFeature
                            .map(feature -> this.moveObjectToFeature(source, targetContainer, feature, targetSiblings, index))
                            .orElseGet(() -> new Failure(this.siriusWebMessageService.unavailableFeature()));
                })
                .reduce((accStatus, currentStatus) -> {
                    var messages = new ArrayList<Message>();
                    if (accStatus instanceof Success accSuccess) {
                        messages.addAll(accSuccess.getMessages());
                    }
                    if (currentStatus instanceof Failure currentFailure) {
                        messages.addAll(currentFailure.getMessages());
                    }
                    return new Success(messages);
                })
                .orElse(new Failure(this.siriusWebMessageService.invalidDroppedObject()));
    }

    private IStatus moveObjectToFeature(EObject source, EObject targetContainer, EStructuralFeature feature, List<TreeItem> targetSiblings, int index) {
        if (feature.isMany()) {
            return this.moveObjectToManyFeature(source, targetContainer, feature, targetSiblings, index);
        }
        return this.moveObjectToSingleFeature(source, targetContainer, feature);
    }

    private IStatus moveObjectToManyFeature(EObject source, EObject targetContainer, EStructuralFeature feature, List<TreeItem> targetSiblings, int index) {
        EList<EObject> featureListEObject = (EList<EObject>) targetContainer.eGet(feature);
        if (index >= 0) {
            this.moveObjectToIndex(source, featureListEObject, targetSiblings, index);
        } else {
            EcoreUtil.remove(source);
            featureListEObject.add(source);
        }
        return new Success();
    }

    private void moveObjectToIndex(EObject source, EList<EObject> featureListEObject, List<TreeItem> targetSiblings, int index) {
        var firstListId = featureListEObject.stream().findFirst().map(this.objectService::getId).orElse(null);
        var offset = targetSiblings.stream().map(TreeItem::getId).toList().indexOf(firstListId);
        var featureIndex = index - offset;
        var sourceId = this.objectService.getId(source);
        var sourcePosBeforeMove = targetSiblings.stream().map(TreeItem::getId).toList().indexOf(sourceId);
        if (sourcePosBeforeMove >= 0) {
            if (sourcePosBeforeMove < index) {
                featureListEObject.move(featureIndex - 1, source);
            } else {
                if (featureIndex < 0) {
                    featureIndex = 0;
                }
                featureListEObject.move(featureIndex, source);
            }
        } else {
            EcoreUtil.remove(source);
            featureListEObject.add(featureIndex, source);
        }
    }

    private IStatus moveObjectToSingleFeature(EObject source, EObject targetContainer, EStructuralFeature feature) {
        if (targetContainer.eGet(feature) == null) {
            EcoreUtil.remove(source);
            targetContainer.eSet(feature, source);
            return new Success();
        }
        return new Failure(this.siriusWebMessageService.alreadySetFeature());
    }

    Optional<String> getContainmentFeatureName(EObject source, EObject target) {

        EClass containedObjectEClass = source.eClass();
        return target.eClass().getEAllContainments().stream()
                .filter(eReference -> containedObjectEClass.equals(eReference.getEReferenceType()) || containedObjectEClass.getEAllSuperTypes().stream()
                        .anyMatch(superType -> superType.equals(eReference.getEReferenceType())))
                .map(ENamedElement::getName)
                .findFirst();
    }

    List<TreeItem> getContainerChild(TreeItem treeItem, String containerId) {
        if (treeItem.getId().equals(containerId)) {
            return treeItem.getChildren();
        }

        List<TreeItem> result = new ArrayList<>();
        if (!treeItem.getChildren().isEmpty()) {
            for (TreeItem child : treeItem.getChildren()) {
                result.addAll(this.getContainerChild(child, containerId));
            }
        }
        return result;
    }
}
