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
package org.eclipse.sirius.components.collaborative.validation;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationCreationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.validation.Validation;
import org.eclipse.sirius.components.validation.components.ValidationComponent;
import org.eclipse.sirius.components.validation.components.ValidationComponentProps;
import org.eclipse.sirius.components.validation.description.ValidationDescription;
import org.eclipse.sirius.components.validation.render.ValidationRenderer;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Used to create validation representations.
 *
 * @author sbegaudeau
 */
@Service
public class ValidationCreationService implements IValidationCreationService {

    private final Timer timer;

    public ValidationCreationService(MeterRegistry meterRegistry) {
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "validation")
                .register(meterRegistry);
    }

    @Override
    public Validation create(IEditingContext editingContext, ValidationDescription validationDescription, ValidationContext validationContext) {
        long start = System.currentTimeMillis();

        var variableManager = new VariableManager();
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);

        var optionalPreviousValidation = Optional.ofNullable(validationContext).map(ValidationContext::validation);
        var validationComponentProps = new ValidationComponentProps(variableManager, validationDescription, optionalPreviousValidation);
        var element = new Element(ValidationComponent.class, validationComponentProps);
        var validation = new ValidationRenderer().render(element);

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);

        return validation;
    }
}
