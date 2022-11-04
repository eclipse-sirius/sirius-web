/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors.handlers;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorEventHandler;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.AddWidgetInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.AddWidgetSuccessPayload;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages.ICollaborativeFormDescriptionEditorMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.view.FlexDirection;
import org.eclipse.sirius.components.view.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.WidgetDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle the add widget event on Form Description Editor.
 *
 * @author arichard
 */
@Service
public class AddWidgetEventHandler implements IFormDescriptionEditorEventHandler {

    private final IObjectService objectService;

    private final ICollaborativeFormDescriptionEditorMessageService messageService;

    private final Counter counter;

    public AddWidgetEventHandler(IObjectService objectService, ICollaborativeFormDescriptionEditorMessageService messageService, MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IFormDescriptionEditorInput formDescriptionEditorInput) {
        return formDescriptionEditorInput instanceof AddWidgetInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IFormDescriptionEditorContext formDescriptionEditorContext,
            IFormDescriptionEditorInput formDescriptionEditorInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(formDescriptionEditorInput.getClass().getSimpleName(), AddWidgetInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formDescriptionEditorInput.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formDescriptionEditorInput.getRepresentationId(), formDescriptionEditorInput);

        if (formDescriptionEditorInput instanceof AddWidgetInput) {
            String containerId = ((AddWidgetInput) formDescriptionEditorInput).getContainerId();
            String kind = ((AddWidgetInput) formDescriptionEditorInput).getKind();
            int index = ((AddWidgetInput) formDescriptionEditorInput).getIndex();
            boolean addWidget = this.addWidget(editingContext, formDescriptionEditorContext, containerId, kind, index);
            if (addWidget) {
                payload = new AddWidgetSuccessPayload(formDescriptionEditorInput.getId());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formDescriptionEditorInput.getRepresentationId(), formDescriptionEditorInput);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private boolean addWidget(IEditingContext editingContext, IFormDescriptionEditorContext formDescriptionEditorContext, String containerId, String kind, int index) {
        boolean success = false;
        var optionalSelf = Optional.empty();
        if (containerId != null) {
            optionalSelf = this.objectService.getObject(editingContext, containerId);
        } else {
            optionalSelf = this.objectService.getObject(editingContext, formDescriptionEditorContext.getFormDescriptionEditor().getTargetObjectId());
        }
        if (optionalSelf.isPresent()) {
            Object container = optionalSelf.get();
            EClassifier eClassifier = ViewPackage.eINSTANCE.getEClassifier(kind + "Description"); //$NON-NLS-1$
            if (eClassifier instanceof EClass) {
                var widgetDescription = ViewFactory.eINSTANCE.create((EClass) eClassifier);
                if (widgetDescription instanceof FlexboxContainerDescription) {
                    ((FlexboxContainerDescription) widgetDescription).setFlexDirection(FlexDirection.get(kind));
                }
                if (widgetDescription instanceof WidgetDescription) {
                    this.createStyle((WidgetDescription) widgetDescription);
                    if (container instanceof FormDescription) {
                        ((FormDescription) container).getWidgets().add(index, (WidgetDescription) widgetDescription);
                        success = true;
                    } else if (container instanceof FlexboxContainerDescription) {
                        ((FlexboxContainerDescription) container).getChildren().add(index, (WidgetDescription) widgetDescription);
                        success = true;
                    }
                }
            }
        }
        return success;
    }

    private void createStyle(WidgetDescription widgetDescription) {
        EStructuralFeature styleFeature = widgetDescription.eClass().getEStructuralFeature("style"); //$NON-NLS-1$
        if (styleFeature instanceof EReference) {
            EClassifier eClassifier = styleFeature.getEType();
            if (eClassifier instanceof EClass) {
                var widgetDescriptionStyle = ViewFactory.eINSTANCE.create((EClass) eClassifier);
                if (eClassifier.isInstance(widgetDescriptionStyle)) {
                    widgetDescription.eSet(styleFeature, widgetDescriptionStyle);
                }
            }
        }
    }
}
