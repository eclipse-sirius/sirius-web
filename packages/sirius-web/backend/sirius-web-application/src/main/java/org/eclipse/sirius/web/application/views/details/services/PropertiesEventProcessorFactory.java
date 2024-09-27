/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.application.views.details.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.api.RepresentationEventProcessorFactoryConfiguration;
import org.eclipse.sirius.components.collaborative.forms.FormEventProcessor;
import org.eclipse.sirius.components.collaborative.forms.api.FormCreationParameters;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormPostProcessor;
import org.eclipse.sirius.components.collaborative.forms.api.IPropertiesDefaultDescriptionProvider;
import org.eclipse.sirius.components.collaborative.forms.api.IPropertiesDescriptionService;
import org.eclipse.sirius.components.collaborative.forms.configuration.FormEventProcessorConfiguration;
import org.eclipse.sirius.components.collaborative.forms.configuration.FormEventProcessorFactoryConfiguration;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.springframework.stereotype.Service;

/**
 * Used to create the properties event processors.
 *
 * @author hmarchadour
 */
@Service
public class PropertiesEventProcessorFactory implements IRepresentationEventProcessorFactory {

    public static final String DETAILS_VIEW_ID = UUID.nameUUIDFromBytes("details-view".getBytes()).toString();

    private final IPropertiesDescriptionService propertiesDescriptionService;

    private final IPropertiesDefaultDescriptionProvider propertiesDefaultDescriptionProvider;

    private final IObjectService objectService;

    private final IRepresentationSearchService representationSearchService;

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final List<IFormEventHandler> formEventHandlers;

    private final List<ITableEventHandler> tableEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IFormPostProcessor formPostProcessor;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public PropertiesEventProcessorFactory(IPropertiesDescriptionService propertiesDescriptionService, IPropertiesDefaultDescriptionProvider propertiesDefaultDescriptionProvider, List<IWidgetDescriptor> widgetDescriptors,
            RepresentationEventProcessorFactoryConfiguration configuration, FormEventProcessorFactoryConfiguration formConfiguration) {
        this.propertiesDescriptionService = Objects.requireNonNull(propertiesDescriptionService);
        this.propertiesDefaultDescriptionProvider = Objects.requireNonNull(propertiesDefaultDescriptionProvider);
        this.objectService = Objects.requireNonNull(formConfiguration.getObjectService());
        this.representationSearchService = Objects.requireNonNull(configuration.getRepresentationSearchService());
        this.representationDescriptionSearchService = Objects.requireNonNull(configuration.getRepresentationDescriptionSearchService());
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.formEventHandlers = Objects.requireNonNull(formConfiguration.getFormEventHandlers());
        this.tableEventHandlers = Objects.requireNonNull(formConfiguration.getTableEventHandlers());
        this.subscriptionManagerFactory = Objects.requireNonNull(configuration.getSubscriptionManagerFactory());
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(configuration.getRepresentationRefreshPolicyRegistry());
        this.formPostProcessor = Objects.requireNonNull(formConfiguration.getFormPostProcessor());
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return representationId.startsWith("details://");
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        List<PageDescription> pageDescriptions = this.propertiesDescriptionService.getPropertiesDescriptions();

        Map<String, List<String>> parameters = new URLParser().getParameterValues(representationId);
        String objectIdsParam = parameters.get("objectIds").get(0);

        var objectIds = new URLParser().getParameterEntries(objectIdsParam);
        var objects = objectIds.stream()
                .map(objectId -> this.objectService.getObject(editingContext, objectId))
                .flatMap(Optional::stream)
                .toList();

        if (!objects.isEmpty()) {
            Optional<FormDescription> optionalFormDescription = Optional.empty();
            if (!pageDescriptions.isEmpty()) {
                optionalFormDescription = new DetailsViewFormDescriptionAggregator().aggregate(pageDescriptions, objects, this.objectService);
            }
            FormDescription formDescription = optionalFormDescription.orElse(this.propertiesDefaultDescriptionProvider.getFormDescription());

            FormCreationParameters formCreationParameters = FormCreationParameters.newFormCreationParameters(representationId)
                    .editingContext(editingContext)
                    .formDescription(formDescription)
                    .object(objects.get(0))
                    .selection(objects)
                    .build();

            IRepresentationEventProcessor formEventProcessor = new FormEventProcessor(
                    new FormEventProcessorConfiguration(editingContext, this.objectService, formCreationParameters, this.widgetDescriptors, this.formEventHandlers, this.tableEventHandlers),
                    this.subscriptionManagerFactory.create(),
                    this.representationSearchService,
                    this.representationDescriptionSearchService,
                    this.representationRefreshPolicyRegistry,
                    this.formPostProcessor);

            return Optional.of(formEventProcessor);
        }
        return Optional.empty();
    }
}
