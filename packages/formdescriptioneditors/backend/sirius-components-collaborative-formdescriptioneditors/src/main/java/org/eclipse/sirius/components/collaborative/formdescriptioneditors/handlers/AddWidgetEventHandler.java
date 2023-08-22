/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorEventHandler;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.AddWidgetInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages.ICollaborativeFormDescriptionEditorMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.formdescriptioneditors.IWidgetDescriptionProvider;
import org.eclipse.sirius.components.view.form.FlexDirection;
import org.eclipse.sirius.components.view.form.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;
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

    private final List<IWidgetDescriptionProvider> widgetDescriptionProviders;

    private final Counter counter;

    public AddWidgetEventHandler(IObjectService objectService, ICollaborativeFormDescriptionEditorMessageService messageService, List<IWidgetDescriptionProvider> widgetDescriptionProviders, MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.messageService = Objects.requireNonNull(messageService);
        this.widgetDescriptionProviders = Objects.requireNonNull(widgetDescriptionProviders);

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
        IPayload payload = new ErrorPayload(formDescriptionEditorInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formDescriptionEditorInput.representationId(), formDescriptionEditorInput);

        if (formDescriptionEditorInput instanceof AddWidgetInput) {
            String containerId = ((AddWidgetInput) formDescriptionEditorInput).containerId();
            String kind = ((AddWidgetInput) formDescriptionEditorInput).kind();
            int index = ((AddWidgetInput) formDescriptionEditorInput).index();
            boolean addWidget = this.addWidget(editingContext, formDescriptionEditorContext, containerId, kind, index);
            if (addWidget) {
                payload = new SuccessPayload(formDescriptionEditorInput.id());
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, formDescriptionEditorInput.representationId(), formDescriptionEditorInput);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private boolean addWidget(IEditingContext editingContext, IFormDescriptionEditorContext formDescriptionEditorContext, String containerId, String kind, int index) {
        boolean success = false;
        var optionalSelf = this.objectService.getObject(editingContext, containerId);
        if (optionalSelf.isPresent()) {
            Object container = optionalSelf.get();
            EClassifier eClassifier = this.getWidgetDescriptionType(kind);
            if (eClassifier instanceof EClass eClass) {
                var widgetDescription = EcoreUtil.create(eClass);
                if (widgetDescription instanceof FlexboxContainerDescription) {
                    ((FlexboxContainerDescription) widgetDescription).setFlexDirection(FlexDirection.get(kind));
                }
                if (widgetDescription instanceof WidgetDescription) {
                    this.createStyle((WidgetDescription) widgetDescription);
                    if (container instanceof GroupDescription) {
                        ((GroupDescription) container).getChildren().add(index, (WidgetDescription) widgetDescription);
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


    private EClassifier getWidgetDescriptionType(String kind) {
        for (IWidgetDescriptionProvider widgetDescriptionProvider : this.widgetDescriptionProviders) {
            var optionalType = widgetDescriptionProvider.getWidgetDescriptionType(kind);
            if (optionalType.isPresent()) {
                return optionalType.get();
            }
        }
        return FormPackage.eINSTANCE.getEClassifier(kind + "Description");
    }

    private void createStyle(WidgetDescription widgetDescription) {
        EStructuralFeature styleFeature = widgetDescription.eClass().getEStructuralFeature("style");
        if (styleFeature instanceof EReference) {
            EClassifier eClassifier = styleFeature.getEType();
            if (eClassifier instanceof EClass) {
                var widgetDescriptionStyle = FormFactory.eINSTANCE.create((EClass) eClassifier);
                if (eClassifier.isInstance(widgetDescriptionStyle)) {
                    widgetDescription.eSet(styleFeature, widgetDescriptionStyle);
                }
            }
        }
    }
}
