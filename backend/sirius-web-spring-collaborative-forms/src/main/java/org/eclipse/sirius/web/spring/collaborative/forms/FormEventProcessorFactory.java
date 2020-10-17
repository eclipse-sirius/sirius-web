/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.forms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.collaborative.api.services.IRepresentationConfiguration;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.web.collaborative.api.services.ISubscriptionManagerFactory;
import org.eclipse.sirius.web.collaborative.forms.api.FormConfiguration;
import org.eclipse.sirius.web.collaborative.forms.api.IDefaultFormDescriptionProvider;
import org.eclipse.sirius.web.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.web.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.web.collaborative.forms.api.IWidgetSubscriptionManagerFactory;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;
import org.springframework.stereotype.Service;

/**
 * Used to create the form event processors.
 *
 * @author sbegaudeau
 */
@Service
public class FormEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationDescriptionService representationDescriptionService;

    private final IDefaultFormDescriptionProvider defaultFormDescriptionProvider;

    private final IObjectService objectService;

    private final List<IFormEventHandler> formEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory;

    public FormEventProcessorFactory(IRepresentationDescriptionService representationDescriptionService, IDefaultFormDescriptionProvider defaultFormDescriptionProvider, IObjectService objectService,
            List<IFormEventHandler> formEventHandlers, ISubscriptionManagerFactory subscriptionManagerFactory, IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory) {
        this.representationDescriptionService = Objects.requireNonNull(representationDescriptionService);
        this.defaultFormDescriptionProvider = Objects.requireNonNull(defaultFormDescriptionProvider);
        this.objectService = Objects.requireNonNull(objectService);
        this.formEventHandlers = Objects.requireNonNull(formEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.widgetSubscriptionManagerFactory = Objects.requireNonNull(widgetSubscriptionManagerFactory);
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass) {
        return IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass);
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext, Context context) {
        if (IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof FormConfiguration) {
            FormConfiguration formConfiguration = (FormConfiguration) configuration;

            // @formatter:off
            List<FormDescription> formDescriptions = this.representationDescriptionService.getRepresentationDescriptions().stream()
                    .filter(FormDescription.class::isInstance)
                    .map(FormDescription.class::cast)
                    .collect(Collectors.toList());
            // @formatter:on
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, formConfiguration.getObjectId());
            if (optionalObject.isPresent()) {
                Object object = optionalObject.get();
                Optional<FormDescription> optionalFormDescription = Optional.empty();
                if (!formDescriptions.isEmpty()) {
                    optionalFormDescription = new FormDescriptionAggregator().aggregate(formDescriptions, object, this.objectService);
                }
                FormDescription formDescription = optionalFormDescription.orElse(this.defaultFormDescriptionProvider.getFormDescription());
                IRepresentationEventProcessor formEventProcessor = new FormEventProcessor(editingContext, formDescription, formConfiguration.getId(), object, this.formEventHandlers,
                        this.subscriptionManagerFactory.create(), this.widgetSubscriptionManagerFactory.create());

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
