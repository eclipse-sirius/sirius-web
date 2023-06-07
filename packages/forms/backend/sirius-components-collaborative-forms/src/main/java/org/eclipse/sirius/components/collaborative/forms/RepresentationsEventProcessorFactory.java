/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.forms.api.FormCreationParameters;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.components.collaborative.forms.api.IRepresentationsDescriptionProvider;
import org.eclipse.sirius.components.collaborative.forms.api.IWidgetSubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.forms.api.RepresentationsConfiguration;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.springframework.stereotype.Service;

/**
 * Used to create the representations event processors.
 *
 * @author gcoutable
 */
@Service
public class RepresentationsEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationsDescriptionProvider representationsDescriptionProvider;

    private final IObjectService objectService;

    private final List<IFormEventHandler> formEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    public RepresentationsEventProcessorFactory(IRepresentationsDescriptionProvider representationsDescriptionProvider, IObjectService objectService, List<IFormEventHandler> formEventHandlers,
            ISubscriptionManagerFactory subscriptionManagerFactory, IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory,
            IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        this.representationsDescriptionProvider = Objects.requireNonNull(representationsDescriptionProvider);
        this.objectService = Objects.requireNonNull(objectService);
        this.formEventHandlers = Objects.requireNonNull(formEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.widgetSubscriptionManagerFactory = Objects.requireNonNull(widgetSubscriptionManagerFactory);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof RepresentationsConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof RepresentationsConfiguration) {
            RepresentationsConfiguration representationsConfiguration = (RepresentationsConfiguration) configuration;

            FormDescription formDescription = this.representationsDescriptionProvider.getRepresentationsDescription();
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, representationsConfiguration.getObjectId());
            if (optionalObject.isPresent()) {
                Object object = optionalObject.get();
                // @formatter:off
                FormCreationParameters formCreationParameters = FormCreationParameters.newFormCreationParameters(representationsConfiguration.getId())
                        .editingContext(editingContext)
                        .formDescription(formDescription)
                        .object(object)
                        .selection(List.of())
                        .build();
                // @formatter:on

                FormEventProcessor formEventProcessor = new FormEventProcessor(editingContext, formCreationParameters, List.of(), this.formEventHandlers, this.subscriptionManagerFactory.create(),
                        this.widgetSubscriptionManagerFactory.create(), this.representationRefreshPolicyRegistry);

                // @formatter:off
                return Optional.of(formEventProcessor)
                        .filter(representationEventProcessorClass::isInstance)
                        .map(representationEventProcessorClass::cast);
                // @formatter:on
            }
        }

        return Optional.empty();
    }

}
