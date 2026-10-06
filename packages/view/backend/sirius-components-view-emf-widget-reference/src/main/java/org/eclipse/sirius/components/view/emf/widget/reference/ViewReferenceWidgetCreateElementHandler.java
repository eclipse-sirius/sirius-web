/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.view.emf.widget.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetCreateElementHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.form.api.IViewFormDescriptionSearchService;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Creates objects from View-based reference widgets.
 *
 * @author tgiraudet
 */
@Service
public class ViewReferenceWidgetCreateElementHandler implements IReferenceWidgetCreateElementHandler {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IEditService editService;

    private final IViewFormDescriptionSearchService viewFormDescriptionSearchService;

    private final IObjectSearchService objectSearchService;

    private final IReferenceMessageService messageService;

    private final ViewReferenceWidgetCreateElementExecutor createElementExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    public ViewReferenceWidgetCreateElementHandler(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IEditService editService,
            IViewFormDescriptionSearchService viewFormDescriptionSearchService, IObjectSearchService objectSearchService, IReferenceMessageService messageService,
            ViewReferenceWidgetCreateElementExecutor createElementExecutor, IFeedbackMessageService feedbackMessageService) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.editService = Objects.requireNonNull(editService);
        this.viewFormDescriptionSearchService = Objects.requireNonNull(viewFormDescriptionSearchService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.createElementExecutor = Objects.requireNonNull(createElementExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    @Override
    public boolean canHandle(FormDescription formDescription) {
        return this.viewRepresentationDescriptionPredicate.test(formDescription);
    }

    @Override
    public List<ChildCreationDescription> getRootCreationDescriptions(IEditingContext editingContext, String domainId, String referenceKind, String descriptionId) {
        return this.editService.getRootCreationDescriptions(editingContext, domainId, false, referenceKind);
    }

    @Override
    public List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String kind, String referenceKind, String descriptionId) {
        return this.editService.getChildCreationDescriptions(editingContext, kind, referenceKind);
    }

    @Override
    public IStatus createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId, ReferenceWidget referenceWidget) {
        Optional<IStatus> optionalStatus = Optional.empty();
        var optionalDescription = this.findReferenceWidgetDescription(editingContext, referenceWidget.getDescriptionId());
        if (optionalDescription.isPresent() && optionalDescription.get().getCreateButton() != null) {
            var description = optionalDescription.get();
            // Fallback to the default behavior
            if (description.getCreateButton().getBody().isEmpty()) {
                return this.toStatus(this.editService.createRootObject(editingContext, documentId, domainId, rootObjectCreationDescriptionId));
            }
            if (editingContext instanceof IEMFEditingContext emfEditingContext) {
                var resourceSet = emfEditingContext.getDomain().getResourceSet();
                var optionalResource = this.objectSearchService
                        .getObject(editingContext, documentId.toString())
                        .filter(Resource.class::isInstance)
                        .map(Resource.class::cast);
                var optionalType = Optional.ofNullable(resourceSet.getPackageRegistry().getEPackage(domainId))
                        .map(ePackage -> ePackage.getEClassifier(rootObjectCreationDescriptionId))
                        .filter(EClass.class::isInstance)
                        .map(EClass.class::cast)
                        .filter(eClass -> !eClass.isAbstract() && !eClass.isInterface());
                if (optionalResource.isPresent() && optionalType.isPresent()) {
                    optionalStatus = Optional.of(this.createElementExecutor.execute(emfEditingContext, referenceWidget, description,
                            optionalResource.get(), null, optionalType.get()));
                }
            }
        }
        return optionalStatus.orElseGet(this::failure);
    }

    @Override
    public IStatus createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId, ReferenceWidget referenceWidget) {
        Optional<IStatus> optionalStatus = Optional.empty();
        var optionalDescription = this.findReferenceWidgetDescription(editingContext, referenceWidget.getDescriptionId());
        if (optionalDescription.isPresent() && optionalDescription.get().getCreateButton() != null) {
            var description = optionalDescription.get();
            // Fallback to the default behavior
            if (description.getCreateButton().getBody().isEmpty()) {
                return this.toStatus(this.editService.createChild(editingContext, object, childCreationDescriptionId));
            }
            if (editingContext instanceof IEMFEditingContext emfEditingContext && object instanceof EObject parent) {
                var optionalDescriptor = emfEditingContext.getDomain().getNewChildDescriptors(parent, null).stream()
                        .filter(CommandParameter.class::isInstance)
                        .map(CommandParameter.class::cast)
                        .filter(parameter -> parameter.getEStructuralFeature() instanceof EReference reference && reference.isContainment()
                                && parameter.getValue() instanceof EObject child
                                && Objects.equals(childCreationDescriptionId, reference.getName() + "-" + child.eClass().getName()))
                        .findFirst();
                if (optionalDescriptor.isPresent()) {
                    var descriptor = optionalDescriptor.get();
                    optionalStatus = Optional.of(this.createElementExecutor.execute(emfEditingContext, referenceWidget, description,
                            parent, (EReference) descriptor.getEStructuralFeature(), descriptor.getEValue().eClass()));
                }
            }
        }
        return optionalStatus.orElseGet(this::failure);
    }

    private Optional<ReferenceWidgetDescription> findReferenceWidgetDescription(IEditingContext editingContext, String descriptionId) {
        return this.viewFormDescriptionSearchService.findFormElementDescriptionById(editingContext, descriptionId)
                .filter(ReferenceWidgetDescription.class::isInstance)
                .map(ReferenceWidgetDescription.class::cast);
    }

    private IStatus toStatus(Optional<Object> optionalObject) {
        return optionalObject.<IStatus>map(object -> new Success(ChangeKind.SEMANTIC_CHANGE, Map.of("object", object), this.feedbackMessageService.getFeedbackMessages()))
                .orElseGet(this::failure);
    }

    private IStatus failure() {
        List<Message> messages = new ArrayList<>(this.feedbackMessageService.getFeedbackMessages());
        messages.add(new Message(this.messageService.failedToExecuteCreateReferenceAction(), MessageLevel.ERROR));
        return new Failure(messages);
    }
}
