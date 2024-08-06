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

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.emf.utils.SiriusEMFCopier;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerModificationService;
import org.springframework.stereotype.Service;

/**
 * Service related to modification from Explorer.
 *
 * @author lfasani
 */
@Service
public class ExplorerModificationService implements IExplorerModificationService {
    private final IObjectService objectService;

    private final IFeedbackMessageService feedbackMessageService;

    public ExplorerModificationService(IEMFKindService emfKindService, ComposedAdapterFactory composedAdapterFactory, IObjectService objectService, IFeedbackMessageService feedbackMessageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }
    @Override
    public Optional<Object> duplicateObject(IEditingContext editingContext, String objectToDuplicateId, String containerId, String containmentFeature, Boolean duplicateContent,
            Boolean copyOutgoingReferences, Boolean updateIncomingReferences) {

        Optional<EObject> objectToDuplicateOpt = this.objectService.getObject(editingContext, objectToDuplicateId)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
        Optional<EObject> containerEObjectOpt = this.objectService.getObject(editingContext, containerId)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);

        Optional<Object> duplicatedObjectOptional = Optional.empty();
        if (objectToDuplicateOpt.isEmpty()) {
            this.feedbackMessageService.addFeedbackMessage(new Message(MessageFormat.format("The object with id \"{0}\" does not exist", objectToDuplicateId), MessageLevel.ERROR));
        } else if (containerEObjectOpt.isEmpty()) {
            this.feedbackMessageService.addFeedbackMessage(new Message(MessageFormat.format("The object with id \"{0}\" does not exist", containerId), MessageLevel.ERROR));
        } else {
            Optional<EClass> objetEClassOpt = objectToDuplicateOpt
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .map(EObject::eClass);
            var optionalEditingDomain = Optional.of(editingContext)
                    .filter(IEMFEditingContext.class::isInstance)
                    .map(IEMFEditingContext.class::cast)
                    .map(IEMFEditingContext::getDomain);

            if (optionalEditingDomain.isPresent() && objectToDuplicateOpt.isPresent() && containerEObjectOpt.isPresent()) {
                Optional<EReference> eReferenceOpt = containerEObjectOpt.get().eClass().getEAllContainments().stream()
                        .filter(reference -> containmentFeature.equals(reference.getName()))
                        .findFirst();

                if (eReferenceOpt.isPresent()) {
                    if (canFeatureBeSet(containerEObjectOpt.get(), eReferenceOpt.get())) {
                        duplicatedObjectOptional = duplicateObject(optionalEditingDomain.get(), objectToDuplicateOpt.get(), containerEObjectOpt.get(), eReferenceOpt.get(), duplicateContent,
                                copyOutgoingReferences)
                                .flatMap(eObject -> addOrSetEObjet(optionalEditingDomain.get(), eObject, containerEObjectOpt.get(), eReferenceOpt.get(), updateIncomingReferences));
                    } else {
                        this.feedbackMessageService.addFeedbackMessage(new Message(
                                MessageFormat.format("Unable to create a new instance of \"{0}\" in feature \"{1}\" because it has reached its upper-bound cardinality.", objetEClassOpt.get().getName(), containmentFeature), MessageLevel.WARNING));
                    }
                }

                if (updateIncomingReferences && duplicatedObjectOptional.isPresent() && duplicatedObjectOptional.get() instanceof EObject duplicatedEObject) {
                    updateIncomingReferences(duplicatedEObject, objectToDuplicateOpt.get());
                }
            }
        }


        return duplicatedObjectOptional;
    }

    private void updateIncomingReferences(EObject duplicatedEObject, EObject objectToDuplicate) {
        duplicatedEObject.eAdapters().stream()
                .filter(ECrossReferenceAdapter.class::isInstance)
                .map(ECrossReferenceAdapter.class::cast)
                .flatMap(eCrossReferenceAdapter -> eCrossReferenceAdapter.getInverseReferences(objectToDuplicate).stream())
                .filter(setting -> {
                    boolean canFeatureBeSet = false;
                    if (setting.getEStructuralFeature() instanceof EReference eReference && eReference.isMany() && !eReference.isContainment()) {
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

    private Optional<Object> addOrSetEObjet(EditingDomain editingDomain, EObject duplicatedObject, EObject container, EReference eReference, Boolean updateIncomingReferences) {
        Command command = null;
        if (eReference.isMany()) {
            command = new AddCommand(editingDomain, container, eReference, List.of(duplicatedObject));
        } else {
            command = new SetCommand(editingDomain, container, eReference, duplicatedObject);
        }
        editingDomain.getCommandStack().execute(command);
        if (command.getResult().size() == 1) {
            return Optional.of(command.getResult().iterator().next());
        }
        return Optional.empty();
    }

    private Optional<EObject> duplicateObject(EditingDomain editingdomain, EObject eObjectToDuplicate, EObject container, EReference eReference, Boolean duplicateContent,
            Boolean copyOutgoingReferences) {
        Optional<EObject> duplicateObject = Optional.empty();
        if (duplicateContent) {
            EcoreUtil.Copier copier = new EcoreUtil.Copier();
            EObject duplicatedObject = copier.copy(eObjectToDuplicate);
            if (copyOutgoingReferences) {
                copier.copyReferences();
            }

            duplicateObject = Optional.ofNullable(duplicatedObject);
        } else {
            SiriusEMFCopier copier = new SiriusEMFCopier();
            EObject duplicatedObject = copier.copyWithoutContent(eObjectToDuplicate);
            if (copyOutgoingReferences) {
                copier.copyReferences();
            }

            duplicateObject = Optional.ofNullable(duplicatedObject);
        }

        return duplicateObject;
    }

    private boolean canFeatureBeSet(EObject owner, EReference eReference) {
        boolean canFeatureBeSet = false;
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
