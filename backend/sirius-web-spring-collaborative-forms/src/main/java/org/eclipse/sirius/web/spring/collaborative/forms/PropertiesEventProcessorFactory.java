/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

import org.eclipse.sirius.web.collaborative.api.services.IRepresentationConfiguration;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.web.collaborative.api.services.ISubscriptionManagerFactory;
import org.eclipse.sirius.web.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.web.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.web.collaborative.forms.api.IPropertiesDefaultDescriptionProvider;
import org.eclipse.sirius.web.collaborative.forms.api.IWidgetSubscriptionManagerFactory;
import org.eclipse.sirius.web.collaborative.forms.api.PropertiesConfiguration;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.eclipse.sirius.web.services.api.representations.IPropertiesDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Used to create the properties event processors.
 *
 * @author hmarchadour
 */
@Service
public class PropertiesEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IPropertiesDescriptionProvider propertiesDescriptionProvider;

    private final IPropertiesDefaultDescriptionProvider propertiesDefaultDescriptionProvider;

    private final IObjectService objectService;

    private final List<IFormEventHandler> formEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory;

    public PropertiesEventProcessorFactory(IPropertiesDescriptionProvider propertiesDescriptionProvider, IPropertiesDefaultDescriptionProvider propertiesDefaultDescriptionProvider,
            IObjectService objectService, List<IFormEventHandler> formEventHandlers, ISubscriptionManagerFactory subscriptionManagerFactory,
            IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory) {
        this.propertiesDescriptionProvider = Objects.requireNonNull(propertiesDescriptionProvider);
        this.propertiesDefaultDescriptionProvider = Objects.requireNonNull(propertiesDefaultDescriptionProvider);
        this.objectService = Objects.requireNonNull(objectService);
        this.formEventHandlers = Objects.requireNonNull(formEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.widgetSubscriptionManagerFactory = Objects.requireNonNull(widgetSubscriptionManagerFactory);
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof PropertiesConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof PropertiesConfiguration) {
            PropertiesConfiguration propertiesConfiguration = (PropertiesConfiguration) configuration;

            List<FormDescription> formDescriptions = this.propertiesDescriptionProvider.getPropertiesDescriptions();
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, propertiesConfiguration.getObjectId());
            if (optionalObject.isPresent()) {
                Object object = optionalObject.get();
                Optional<FormDescription> optionalFormDescription = Optional.empty();
                if (!formDescriptions.isEmpty()) {
                    optionalFormDescription = new FormDescriptionAggregator().aggregate(formDescriptions, object, this.objectService);
                }
                FormDescription formDescription = optionalFormDescription.orElse(this.propertiesDefaultDescriptionProvider.getFormDescription());
                IRepresentationEventProcessor formEventProcessor = new FormEventProcessor(editingContext, formDescription, propertiesConfiguration.getId(), object, this.formEventHandlers,
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
