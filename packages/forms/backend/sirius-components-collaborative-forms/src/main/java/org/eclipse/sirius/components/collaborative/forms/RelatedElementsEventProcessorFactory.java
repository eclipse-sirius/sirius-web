/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import org.eclipse.sirius.components.collaborative.api.RepresentationEventProcessorFactoryConfiguration;
import org.eclipse.sirius.components.collaborative.forms.api.FormCreationParameters;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.components.collaborative.forms.api.IRelatedElementsDescriptionProvider;
import org.eclipse.sirius.components.collaborative.forms.api.IWidgetSubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.forms.api.RelatedElementsConfiguration;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.springframework.stereotype.Service;

/**
 * Used to create the related elements event processors.
 *
 * @author pcdavid
 */
@Service
public class RelatedElementsEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRelatedElementsDescriptionProvider relatedElementsDescriptionProvider;

    private final IObjectService objectService;

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final List<IFormEventHandler> formEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    public RelatedElementsEventProcessorFactory(RepresentationEventProcessorFactoryConfiguration configuration, IRelatedElementsDescriptionProvider relatedElementsDescriptionProvider,
            IObjectService objectService, List<IWidgetDescriptor> widgetDescriptors, List<IFormEventHandler> formEventHandlers, IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory) {
        this.relatedElementsDescriptionProvider = Objects.requireNonNull(relatedElementsDescriptionProvider);
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.formEventHandlers = Objects.requireNonNull(formEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(configuration.getSubscriptionManagerFactory());
        this.widgetSubscriptionManagerFactory = Objects.requireNonNull(widgetSubscriptionManagerFactory);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(configuration.getRepresentationRefreshPolicyRegistry());
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof RelatedElementsConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof RelatedElementsConfiguration) {
            RelatedElementsConfiguration relatedElementsConfiguration = (RelatedElementsConfiguration) configuration;

            // @formatter:off
            var objects = relatedElementsConfiguration.getObjectIds().stream()
                    .map(objectId -> this.objectService.getObject(editingContext, objectId))
                    .flatMap(Optional::stream)
                    .toList();
            // @formatter:on
            if (!objects.isEmpty()) {
                FormDescription formDescription = this.relatedElementsDescriptionProvider.getFormDescription();
                // @formatter:off
                FormCreationParameters formCreationParameters = FormCreationParameters.newFormCreationParameters(relatedElementsConfiguration.getId())
                        .editingContext(editingContext)
                        .formDescription(formDescription)
                        .objects(objects)
                        .build();
                // @formatter:on

                IRepresentationEventProcessor formEventProcessor = new FormEventProcessor(editingContext, formCreationParameters, this.widgetDescriptors, this.formEventHandlers,
                        this.subscriptionManagerFactory.create(), this.widgetSubscriptionManagerFactory.create(), this.representationRefreshPolicyRegistry);

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
