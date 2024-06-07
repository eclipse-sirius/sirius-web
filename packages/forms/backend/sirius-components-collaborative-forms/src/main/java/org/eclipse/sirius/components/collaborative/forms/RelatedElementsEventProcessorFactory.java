/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.api.RepresentationEventProcessorFactoryConfiguration;
import org.eclipse.sirius.components.collaborative.forms.api.FormCreationParameters;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormPostProcessor;
import org.eclipse.sirius.components.collaborative.forms.api.IRelatedElementsDescriptionProvider;
import org.eclipse.sirius.components.collaborative.forms.api.IWidgetSubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.forms.api.RelatedElementsConfiguration;
import org.eclipse.sirius.components.collaborative.forms.configuration.FormEventProcessorConfiguration;
import org.eclipse.sirius.components.collaborative.forms.configuration.FormEventProcessorFactoryConfiguration;
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

    private final IRepresentationSearchService representationSearchService;

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final List<IFormEventHandler> formEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IFormPostProcessor formPostProcessor;

    public RelatedElementsEventProcessorFactory(RepresentationEventProcessorFactoryConfiguration configuration, IRelatedElementsDescriptionProvider relatedElementsDescriptionProvider,
            List<IWidgetDescriptor> widgetDescriptors, FormEventProcessorFactoryConfiguration formConfiguration) {
        this.relatedElementsDescriptionProvider = Objects.requireNonNull(relatedElementsDescriptionProvider);
        this.objectService = Objects.requireNonNull(formConfiguration.getObjectService());
        this.representationSearchService = Objects.requireNonNull(configuration.getRepresentationSearchService());
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.formEventHandlers = Objects.requireNonNull(formConfiguration.getFormEventHandlers());
        this.subscriptionManagerFactory = Objects.requireNonNull(configuration.getSubscriptionManagerFactory());
        this.widgetSubscriptionManagerFactory = Objects.requireNonNull(formConfiguration.getWidgetSubscriptionManagerFactory());
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(configuration.getRepresentationRefreshPolicyRegistry());
        this.formPostProcessor = Objects.requireNonNull(formConfiguration.getFormPostProcessor());
    }

    @Override
    public boolean canHandle(IRepresentationConfiguration configuration) {
        return configuration instanceof RelatedElementsConfiguration;
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (configuration instanceof RelatedElementsConfiguration relatedElementsConfiguration) {

            var objects = relatedElementsConfiguration.getObjectIds().stream()
                    .map(objectId -> this.objectService.getObject(editingContext, objectId))
                    .flatMap(Optional::stream)
                    .toList();
            if (!objects.isEmpty()) {
                FormDescription formDescription = this.relatedElementsDescriptionProvider.getFormDescription();
                FormCreationParameters formCreationParameters = FormCreationParameters.newFormCreationParameters(relatedElementsConfiguration.getId())
                        .editingContext(editingContext)
                        .formDescription(formDescription)
                        .object(objects.get(0))
                        .selection(objects)
                        .build();

                IRepresentationEventProcessor formEventProcessor = new FormEventProcessor(
                        new FormEventProcessorConfiguration(editingContext, this.objectService, formCreationParameters, this.widgetDescriptors, this.formEventHandlers),
                        this.subscriptionManagerFactory.create(),
                        this.widgetSubscriptionManagerFactory.create(),
                        this.representationSearchService,
                        this.representationRefreshPolicyRegistry,
                        this.formPostProcessor);

                return Optional.of(formEventProcessor);
            }
        }
        return Optional.empty();
    }

}
