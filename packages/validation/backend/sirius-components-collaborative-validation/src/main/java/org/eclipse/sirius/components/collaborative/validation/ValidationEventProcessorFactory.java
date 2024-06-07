/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationDescriptionProvider;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationEventHandler;
import org.eclipse.sirius.components.collaborative.validation.api.ValidationConfiguration;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.validation.description.ValidationDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Used to create the validation event processor.
 *
 * @author gcoutable
 */
@Service
public class ValidationEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IValidationDescriptionProvider validationDescriptionProvider;

    private final List<IValidationEventHandler> validationEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    public ValidationEventProcessorFactory(IValidationDescriptionProvider validationDescriptionProvider, List<IValidationEventHandler> validationEventHandlers,
            ISubscriptionManagerFactory subscriptionManagerFactory, IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        this.validationDescriptionProvider = Objects.requireNonNull(validationDescriptionProvider);
        this.validationEventHandlers = Objects.requireNonNull(validationEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
    }

    @Override
    public boolean canHandle(IRepresentationConfiguration configuration) {
        return configuration instanceof ValidationConfiguration;
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IRepresentationConfiguration configuration, IEditingContext editingContext) {
        if (configuration instanceof ValidationConfiguration) {
            ValidationDescription validationDescription = this.validationDescriptionProvider.getDescription();

            ValidationContext validationContext = new ValidationContext(null);
            IRepresentationEventProcessor validationEventProcessor = new ValidationEventProcessor(editingContext, validationDescription, validationContext, this.validationEventHandlers,
                    this.subscriptionManagerFactory.create(), new SimpleMeterRegistry(), this.representationRefreshPolicyRegistry);

            return Optional.of(validationEventProcessor);
        }
        return Optional.empty();
    }

}
