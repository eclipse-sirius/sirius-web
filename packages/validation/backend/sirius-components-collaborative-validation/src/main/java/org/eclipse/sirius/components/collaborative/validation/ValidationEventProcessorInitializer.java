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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.validation.api.IValidationCreationService;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationDescriptionProvider;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationEventProcessorInitializer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.validation.Validation;
import org.springframework.stereotype.Service;

/**
 * Used to perform the initial refresh of the validation representation for its event processor.
 *
 * @author sbegaudeau
 */
@Service
public class ValidationEventProcessorInitializer implements IValidationEventProcessorInitializer {

    private final IValidationDescriptionProvider validationDescriptionProvider;

    private final IValidationCreationService validationCreationService;

    public ValidationEventProcessorInitializer(IValidationDescriptionProvider validationDescriptionProvider, IValidationCreationService validationCreationService) {
        this.validationDescriptionProvider = Objects.requireNonNull(validationDescriptionProvider);
        this.validationCreationService = Objects.requireNonNull(validationCreationService);
    }

    @Override
    public Optional<Validation> getRefreshedRepresentation(IEditingContext editingContext, String representationId) {
        var validationDescription = this.validationDescriptionProvider.getDescription();
        return Optional.of(this.validationCreationService.create(editingContext, validationDescription, null));
    }
}
