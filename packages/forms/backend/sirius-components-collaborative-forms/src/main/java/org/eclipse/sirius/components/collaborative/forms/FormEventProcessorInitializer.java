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
package org.eclipse.sirius.components.collaborative.forms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.forms.api.IFormCreationService;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventProcessorInitializer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.springframework.stereotype.Service;

/**
 * Used to perform the initial refresh of the form representation for its event processor.
 *
 * @author sbegaudeau
 */
@Service
public class FormEventProcessorInitializer implements IFormEventProcessorInitializer {

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IObjectSearchService objectSearchService;

    private final IFormCreationService formCreationService;

    public FormEventProcessorInitializer(IRepresentationSearchService representationSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IObjectSearchService objectSearchService, IFormCreationService formCreationService) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.formCreationService = Objects.requireNonNull(formCreationService);
    }

    @Override
    public Optional<FormContext> getRefreshedRepresentation(IEditingContext editingContext, String representationId) {
        return this.representationSearchService.findById(editingContext, representationId, Form.class)
                .flatMap(form -> this.representationDescriptionSearchService.findById(editingContext, form.getDescriptionId())
                        .filter(FormDescription.class::isInstance)
                        .map(FormDescription.class::cast)
                        .flatMap(formDescription -> this.objectSearchService.getObject(editingContext, form.getTargetObjectId())
                                .map(object -> {
                                    var context = new FormContext(representationId, form, formDescription, object, List.of());
                                    var refreshedForm = this.formCreationService.create(editingContext, formDescription, object, context);
                                    return context.withForm(refreshedForm);
                                })));
    }
}
