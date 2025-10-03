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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.emf.utils.SiriusEMFCopier;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.views.explorer.services.api.DuplicationSettings;
import org.eclipse.sirius.web.application.views.explorer.services.api.IDefaultObjectDuplicator;
import org.eclipse.sirius.web.application.views.explorer.services.api.IObjectDuplicator;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * The default implementation if IObectDuplicator, used only if no more specific implementation applies.
 *
 * @author pcdavid
 */
@Service
public class DefaultObjectDuplicator implements IDefaultObjectDuplicator {

    private final IMessageService messageService;

    public DefaultObjectDuplicator(IMessageService messageService) {
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IStatus duplicateObject(IEditingContext editingContext, EObject objectToDuplicate, EObject containerEObject, String containmentFeature, DuplicationSettings settings) {
        IStatus result = new Failure(this.messageService.objectDuplicationFailed());
        var optionalEditingDomain = Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain);

        if (optionalEditingDomain.isPresent()) {
            Optional<EReference> optionalEReference = containerEObject.eClass().getEAllContainments().stream()
                    .filter(reference -> containmentFeature.equals(reference.getName()))
                    .findFirst();

            Optional<EObject> optionalDuplicatedObject = Optional.empty();

            if (optionalEReference.isPresent()) {
                if (this.canFeatureBeSet(containerEObject, optionalEReference.get())) {
                    optionalDuplicatedObject = this.duplicateObject(objectToDuplicate, settings);
                    optionalDuplicatedObject.ifPresent(eObject -> this.addOrSetEObjet(optionalEditingDomain.get(), eObject, containerEObject, optionalEReference.get()));
                    if (optionalDuplicatedObject.isPresent() && optionalDuplicatedObject.get().eContainer() != null) {
                        result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(IObjectDuplicator.NEW_OBJECT, optionalDuplicatedObject.get()));
                    }
                } else {
                    result = new Failure(this.messageService.upperBoundaryReached(objectToDuplicate.eClass().getName(), containmentFeature));
                }
            }

            if (settings.updateIncomingReferences() && optionalDuplicatedObject.isPresent()) {
                this.updateIncomingReferences(optionalDuplicatedObject.get(), objectToDuplicate);
            }
        }
        return result;
    }

    private void updateIncomingReferences(EObject duplicatedEObject, EObject objectToDuplicate) {
        duplicatedEObject.eAdapters().stream()
                .filter(ECrossReferenceAdapter.class::isInstance)
                .map(ECrossReferenceAdapter.class::cast)
                .flatMap(eCrossReferenceAdapter -> eCrossReferenceAdapter.getInverseReferences(objectToDuplicate).stream())
                .filter(setting -> {
                    boolean canFeatureBeSet = false;
                    if (setting.getEStructuralFeature() instanceof EReference eReference
                            && eReference.isMany()
                            && !eReference.isContainment()
                            && eReference.isChangeable()) {
                        List<?> list = (List<?>) setting.getEObject().eGet(eReference);
                        int upperBound = eReference.getUpperBound();
                        canFeatureBeSet = upperBound == -1 || (upperBound > 0 && list.size() < upperBound);
                    }
                    return canFeatureBeSet;
                })
                .forEach(setting -> {
                    if (setting.getEStructuralFeature() instanceof EReference eReference && eReference.isMany()) {
                        List<EObject> list = (List<EObject>) setting.getEObject().eGet(eReference);
                        list.add(duplicatedEObject);
                    }
                });
    }

    private void addOrSetEObjet(EditingDomain editingDomain, EObject duplicatedObject, EObject container, EReference eReference) {
        Command command;
        if (eReference.isMany()) {
            command = new AddCommand(editingDomain, container, eReference, List.of(duplicatedObject));
        } else {
            command = new SetCommand(editingDomain, container, eReference, duplicatedObject);
        }
        editingDomain.getCommandStack().execute(command);

    }

    private Optional<EObject> duplicateObject(EObject eObjectToDuplicate, DuplicationSettings settings) {
        Optional<EObject> duplicateObject;
        if (settings.duplicateContent()) {
            EcoreUtil.Copier copier = new EcoreUtil.Copier();
            EObject duplicatedObject = copier.copy(eObjectToDuplicate);
            if (settings.copyOutgoingReferences()) {
                copier.copyReferences();
            }
            duplicateObject = Optional.ofNullable(duplicatedObject);
        } else {
            SiriusEMFCopier copier = new SiriusEMFCopier();
            EObject duplicatedObject = copier.copyWithoutContent(eObjectToDuplicate);
            if (settings.copyOutgoingReferences()) {
                copier.copyReferences();
            }
            duplicateObject = Optional.ofNullable(duplicatedObject);
        }

        return duplicateObject;
    }

    private boolean canFeatureBeSet(EObject owner, EReference eReference) {
        boolean canFeatureBeSet;
        if (eReference.isMany()) {
            List<?> list = (List<?>) owner.eGet(eReference);
            int upperBound = eReference.getUpperBound();
            canFeatureBeSet = upperBound == -1 || (upperBound > 0 && list.size() < upperBound);
        } else {
            canFeatureBeSet = eReference.isUnsettable() && !owner.eIsSet(eReference) || owner.eGet(eReference) == null;
        }
        return canFeatureBeSet;
    }
}
