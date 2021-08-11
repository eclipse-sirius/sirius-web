/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.validation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.web.spring.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.web.spring.collaborative.validation.api.IValidationDescriptionProvider;
import org.eclipse.sirius.web.spring.collaborative.validation.api.IValidationEventHandler;
import org.eclipse.sirius.web.spring.collaborative.validation.api.IValidationEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.validation.api.ValidationConfiguration;
import org.eclipse.sirius.web.validation.description.ValidationDescription;
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

    private List<IValidationEventHandler> validationEventHandlers;

    private ISubscriptionManagerFactory subscriptionManagerFactory;

    public ValidationEventProcessorFactory(IValidationDescriptionProvider validationDescriptionProvider, List<IValidationEventHandler> validationEventHandlers,
            ISubscriptionManagerFactory subscriptionManagerFactory) {
        this.validationDescriptionProvider = Objects.requireNonNull(validationDescriptionProvider);
        this.validationEventHandlers = Objects.requireNonNull(validationEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return IValidationEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof ValidationConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (IValidationEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof ValidationConfiguration) {
            ValidationDescription validationDescription = this.validationDescriptionProvider.getDescription();

            ValidationContext validationContext = new ValidationContext(null);
            IRepresentationEventProcessor validationEventProcessor = new ValidationEventProcessor(editingContext, validationDescription, validationContext, this.validationEventHandlers,
                    this.subscriptionManagerFactory.create(), new SimpleMeterRegistry());

            // @formatter:off
            return Optional.of(validationEventProcessor)
                    .filter(representationEventProcessorClass::isInstance)
                    .map(representationEventProcessorClass::cast);
            // @formatter:on
        }

        return Optional.empty();
    }

}
