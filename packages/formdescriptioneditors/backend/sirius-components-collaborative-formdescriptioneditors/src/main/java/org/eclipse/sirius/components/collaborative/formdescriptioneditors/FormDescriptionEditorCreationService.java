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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorCreationService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.IWidgetPreviewConverterProvider;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorComponent;
import org.eclipse.sirius.components.formdescriptioneditors.components.FormDescriptionEditorComponentProps;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.formdescriptioneditors.renderer.FormDescriptionEditorRenderer;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to create form description editors.
 *
 * @author arichard
 */
@Service
public class FormDescriptionEditorCreationService implements IFormDescriptionEditorCreationService {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IObjectService objectService;

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final List<IWidgetPreviewConverterProvider> customWidgetConverterProviders;

    private final Timer timer;

    public FormDescriptionEditorCreationService(IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationPersistenceService representationPersistenceService,
            IObjectService objectService, List<IWidgetDescriptor> widgetDescriptors, List<IWidgetPreviewConverterProvider> customWidgetConverterProviders, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.customWidgetConverterProviders = Objects.requireNonNull(customWidgetConverterProviders);

        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "formdescriptioneditor")
                .register(meterRegistry);
    }

    @Override
    public FormDescriptionEditor create(String label, Object targetObject, FormDescriptionEditorDescription formDescriptionEditorDescription, IEditingContext editingContext) {
        FormDescriptionEditor newFormDescriptionEditor = FormDescriptionEditor.newFormDescriptionEditor(UUID.randomUUID().toString())
                .label(label)
                .targetObjectId(this.objectService.getId(targetObject))
                .descriptionId(formDescriptionEditorDescription.getId())
                .pages(List.of()) // We don't store form description editor pages, it will be re-render by the FormDescriptionEditorProcessor.
                .build();

        this.representationPersistenceService.save(null, editingContext, newFormDescriptionEditor);

        return newFormDescriptionEditor;
    }

    @Override
    public FormDescriptionEditor refresh(IEditingContext editingContext, IFormDescriptionEditorContext formDescriptionEditorContext) {
        FormDescriptionEditor previousFormDescriptionEditor = formDescriptionEditorContext.getFormDescriptionEditor();
        var optionalObject = this.objectService.getObject(editingContext, previousFormDescriptionEditor.getTargetObjectId());
        var optionalFormDescriptionEditorDescription = this.representationDescriptionSearchService.findById(editingContext, previousFormDescriptionEditor.getDescriptionId())
                .filter(FormDescriptionEditorDescription.class::isInstance)
                .map(FormDescriptionEditorDescription.class::cast);

        if (optionalObject.isPresent() && optionalFormDescriptionEditorDescription.isPresent()) {
            Object object = optionalObject.get();
            FormDescriptionEditorDescription formDescriptionEditorDescription = optionalFormDescriptionEditorDescription.get();
            return this.doRender(previousFormDescriptionEditor.getLabel(), object, editingContext, formDescriptionEditorDescription,
                    Optional.of(formDescriptionEditorContext));
        }

        return null;
    }

    private FormDescriptionEditor doRender(String label, Object targetObject, IEditingContext editingContext, FormDescriptionEditorDescription formDescriptionEditorDescription,
            Optional<IFormDescriptionEditorContext> optionalFormDescriptionEditorContext) {
        long start = System.currentTimeMillis();

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, targetObject);
        variableManager.put(FormDescriptionEditor.LABEL, label);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));

        Optional<FormDescriptionEditor> optionalPreviousFormDescriptionEditor = optionalFormDescriptionEditorContext.map(IFormDescriptionEditorContext::getFormDescriptionEditor);

        var formDescriptionEditorComponentProps = new FormDescriptionEditorComponentProps(variableManager, formDescriptionEditorDescription, optionalPreviousFormDescriptionEditor, this.widgetDescriptors, this.customWidgetConverterProviders);
        Element element = new Element(FormDescriptionEditorComponent.class, formDescriptionEditorComponentProps);

        FormDescriptionEditor newFormDescriptionEditor = new FormDescriptionEditorRenderer(this.widgetDescriptors).render(element);

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
        return newFormDescriptionEditor;
    }

}
