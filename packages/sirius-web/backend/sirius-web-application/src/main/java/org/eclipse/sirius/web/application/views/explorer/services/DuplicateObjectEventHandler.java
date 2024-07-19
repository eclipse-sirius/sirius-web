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
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.emf.utils.SiriusEMFCopier;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectInput;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateObjectSuccessPayload;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to duplicate an object.
 *
 * @author lfasani
 */
@Service
public class DuplicateObjectEventHandler implements IEditingContextEventHandler {

    private static final String NEW_OBJECT = "newObject";

    private final IObjectService objectService;

    private final IMessageService messageService;

    private final Counter counter;

    public DuplicateObjectEventHandler(IObjectService objectService, IMessageService messageService, MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof DuplicateObjectInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        List<Message> messages = List.of(new Message(this.messageService.invalidInput(input.getClass().getSimpleName(), DuplicateObjectInput.class.getSimpleName()), MessageLevel.ERROR));
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        IPayload payload = new ErrorPayload(input.id(), messages);

        if (input instanceof DuplicateObjectInput duplicateObjectInput) {
            IStatus duplicationResult = this.duplicateObject(editingContext, duplicateObjectInput.objectId(), duplicateObjectInput.containerId(),
                    duplicateObjectInput.containmentFeatureName(), duplicateObjectInput.duplicateContent(), duplicateObjectInput.copyOutgoingReferences(),
                    duplicateObjectInput.updateIncomingReferences());

            if (duplicationResult instanceof Success success) {
                payload = new DuplicateObjectSuccessPayload(input.id(), success.getParameters().get(NEW_OBJECT), success.getMessages());
                changeDescription = new ChangeDescription(success.getChangeKind(), editingContext.getId(), input);
            } else if (duplicationResult instanceof Failure failure) {
                payload = new ErrorPayload(input.id(), failure.getMessages());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus duplicateObject(IEditingContext editingContext, String objectToDuplicateId, String containerId, String containmentFeature, boolean duplicateContent,
            boolean copyOutgoingReferences, boolean updateIncomingReferences) {

        IStatus result;
        Optional<EObject> objectToDuplicateOpt = this.objectService.getObject(editingContext, objectToDuplicateId)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
        Optional<EObject> containerEObjectOpt = this.objectService.getObject(editingContext, containerId)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);

        if (objectToDuplicateOpt.isEmpty()) {
            result = new Failure(this.messageService.objectDoesNotExist(objectToDuplicateId));
        } else if (containerEObjectOpt.isEmpty()) {
            result = new Failure(this.messageService.objectDoesNotExist(containerId));
        } else {
            result = this.duplicateObject(editingContext, containmentFeature, duplicateContent, copyOutgoingReferences, updateIncomingReferences,
                    containerEObjectOpt.get(), objectToDuplicateOpt.get());
        }

        return result;
    }

    private IStatus duplicateObject(IEditingContext editingContext, String containmentFeature, boolean duplicateContent, boolean copyOutgoingReferences, boolean updateIncomingReferences,
            EObject containerEObject, EObject objectToDuplicate) {
        IStatus result = new Failure(this.messageService.objectDuplicationFailed());
        var optionalEditingDomain = Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain);

        if (optionalEditingDomain.isPresent()) {
            Optional<EReference> eReferenceOpt = containerEObject.eClass().getEAllContainments().stream()
                    .filter(reference -> containmentFeature.equals(reference.getName()))
                    .findFirst();

            Optional<Object> duplicatedObjectOptional = Optional.empty();

            if (eReferenceOpt.isPresent()) {
                if (this.canFeatureBeSet(containerEObject, eReferenceOpt.get())) {
                    duplicatedObjectOptional = this.duplicateObject(objectToDuplicate, duplicateContent, copyOutgoingReferences)
                            .flatMap(eObject -> this.addOrSetEObjet(optionalEditingDomain.get(), eObject, containerEObject, eReferenceOpt.get()));
                    if (duplicatedObjectOptional.isPresent()) {
                        result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(NEW_OBJECT, duplicatedObjectOptional.get()));
                    }
                } else {
                    result = new Failure(this.messageService.upperBoundaryReached(objectToDuplicate.eClass().getName(), containmentFeature));
                }
            }

            if (updateIncomingReferences && duplicatedObjectOptional.isPresent() && duplicatedObjectOptional.get() instanceof EObject duplicatedEObject) {
                this.updateIncomingReferences(duplicatedEObject, objectToDuplicate);
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

    private Optional<Object> addOrSetEObjet(EditingDomain editingDomain, EObject duplicatedObject, EObject container, EReference eReference) {
        Command command;
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

    private Optional<EObject> duplicateObject(EObject eObjectToDuplicate, boolean duplicateContent, boolean copyOutgoingReferences) {
        Optional<EObject> duplicateObject;
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
