/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationEventHandler;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationEventProcessorInitializer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to create the validation event processor.
 *
 * @author gcoutable
 */
@Service
public class ValidationEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IValidationEventProcessorInitializer validationEventProcessorInitializer;

    private final List<IValidationEventHandler> validationEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    public ValidationEventProcessorFactory(IValidationEventProcessorInitializer validationEventProcessorInitializer, List<IValidationEventHandler> validationEventHandlers,
            ISubscriptionManagerFactory subscriptionManagerFactory) {
        this.validationEventProcessorInitializer = Objects.requireNonNull(validationEventProcessorInitializer);
        this.validationEventHandlers = Objects.requireNonNull(validationEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return representationId.startsWith("validation://");
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        return this.validationEventProcessorInitializer.getRefreshedRepresentation(editingContext, representationId)
                .map(validation -> {
                    var validationContext = new ValidationContext(validation);
                    return (IRepresentationEventProcessor) new ValidationEventProcessor(editingContext, validationContext, this.validationEventHandlers, this.subscriptionManagerFactory.create());
                });
    }

}
