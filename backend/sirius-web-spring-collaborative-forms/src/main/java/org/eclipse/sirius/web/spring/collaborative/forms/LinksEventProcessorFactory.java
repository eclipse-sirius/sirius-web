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
package org.eclipse.sirius.web.spring.collaborative.forms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.web.spring.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.forms.api.ILinksDescriptionService;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IWidgetSubscriptionManagerFactory;
import org.eclipse.sirius.web.spring.collaborative.forms.api.LinksConfiguration;
import org.springframework.stereotype.Service;

/**
 * Used to create the links event processors.
 *
 * @author ldelaigue
 */
@Service
public class LinksEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final ILinksDescriptionService linksDescriptionService;

    private final IObjectService objectService;

    private final List<IFormEventHandler> formEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory;

    public LinksEventProcessorFactory(ILinksDescriptionService linksDescriptionService, IObjectService objectService, List<IFormEventHandler> formEventHandlers,
            ISubscriptionManagerFactory subscriptionManagerFactory, IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory) {
        this.linksDescriptionService = Objects.requireNonNull(linksDescriptionService);
        this.objectService = Objects.requireNonNull(objectService);
        this.formEventHandlers = Objects.requireNonNull(formEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.widgetSubscriptionManagerFactory = Objects.requireNonNull(widgetSubscriptionManagerFactory);
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof LinksConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (this.canHandle(representationEventProcessorClass, configuration)) {
            LinksConfiguration linksConfiguration = (LinksConfiguration) configuration;

            List<FormDescription> formDescriptions = this.linksDescriptionService.getLinksDescriptions();
            if (!formDescriptions.isEmpty()) {
                Optional<Object> optionalObject = this.objectService.getObject(editingContext, linksConfiguration.getObjectId());
                if (optionalObject.isPresent()) {
                    Object object = optionalObject.get();
                    Optional<FormDescription> optionalFormDescription = new FormDescriptionAggregator().aggregate(formDescriptions, object, this.objectService);
                    if (optionalFormDescription.isPresent()) {
                        FormDescription formDescription = optionalFormDescription.get();
                        IRepresentationEventProcessor formEventProcessor = new FormEventProcessor(editingContext, formDescription, linksConfiguration.getId(), object, this.formEventHandlers,
                                this.subscriptionManagerFactory.create(), this.widgetSubscriptionManagerFactory.create());

                        // @formatter:off
                        return Optional.of(formEventProcessor)
                                .filter(representationEventProcessorClass::isInstance)
                                .map(representationEventProcessorClass::cast);
                        // @formatter:on
                    }
                }
            }
        }
        return Optional.empty();
    }

}
