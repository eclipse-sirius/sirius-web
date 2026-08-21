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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationRefresher;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationCreationService;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationDescriptionProvider;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationEventProcessor;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.validation.Validation;
import org.eclipse.sirius.components.validation.description.ValidationDescription;
import org.springframework.stereotype.Service;

/**
 * Used to refresh validation representations.
 *
 * @author sbegaudeau
 */
@Service
public class ValidationRefresher implements IRepresentationRefresher {

    private final IValidationDescriptionProvider validationDescriptionProvider;

    private final IValidationCreationService validationCreationService;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    public ValidationRefresher(IValidationDescriptionProvider validationDescriptionProvider, IValidationCreationService validationCreationService,
            IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        this.validationDescriptionProvider = Objects.requireNonNull(validationDescriptionProvider);
        this.validationCreationService = Objects.requireNonNull(validationCreationService);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IRepresentationEventProcessor representationEventProcessor, ChangeDescription changeDescription) {
        return representationEventProcessor instanceof IValidationEventProcessor
                && representationEventProcessor.getRepresentation() instanceof Validation
                && this.getRefreshPolicy().shouldRefresh(changeDescription);
    }

    @Override
    public void refresh(IEditingContext editingContext, IRepresentationEventProcessor representationEventProcessor, ChangeDescription changeDescription) {
        if (representationEventProcessor instanceof IValidationEventProcessor validationEventProcessor
                && validationEventProcessor.getRepresentation() instanceof Validation validation) {
            var refreshedValidation = this.validationCreationService.create(editingContext, this.validationDescriptionProvider.getDescription(), new ValidationContext(validation));
            validationEventProcessor.update(changeDescription.getCause(), refreshedValidation);
        }
    }

    private IRepresentationRefreshPolicy getRefreshPolicy() {
        ValidationDescription validationDescription = this.validationDescriptionProvider.getDescription();
        return this.representationRefreshPolicyRegistry.getRepresentationRefreshPolicy(validationDescription)
                .orElse(changeDescription -> ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind()));
    }
}
