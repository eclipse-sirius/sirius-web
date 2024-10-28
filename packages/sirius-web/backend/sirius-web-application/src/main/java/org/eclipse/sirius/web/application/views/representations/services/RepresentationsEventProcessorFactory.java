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
package org.eclipse.sirius.web.application.views.representations.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
import org.eclipse.sirius.components.collaborative.forms.api.IRepresentationsDescriptionProvider;
import org.eclipse.sirius.components.collaborative.forms.configuration.FormEventProcessorConfiguration;
import org.eclipse.sirius.components.collaborative.forms.configuration.FormEventProcessorFactoryConfiguration;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
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

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final List<IFormEventHandler> formEventHandlers;

    private final List<ITableEventHandler> tableEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IFormPostProcessor formPostProcessor;

    private final IURLParser urlParser;

    public RepresentationsEventProcessorFactory(IRepresentationsDescriptionProvider representationsDescriptionProvider, ISubscriptionManagerFactory subscriptionManagerFactory,
            RepresentationEventProcessorFactoryConfiguration configuration, IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry, List<IWidgetDescriptor> widgetDescriptors,
            FormEventProcessorFactoryConfiguration formConfiguration, IURLParser urlParser) {
        this.representationsDescriptionProvider = Objects.requireNonNull(representationsDescriptionProvider);
        this.objectService = Objects.requireNonNull(formConfiguration.getObjectService());
        this.representationSearchService = Objects.requireNonNull(configuration.getRepresentationSearchService());
        this.representationDescriptionSearchService = Objects.requireNonNull(configuration.getRepresentationDescriptionSearchService());
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.formEventHandlers = Objects.requireNonNull(formConfiguration.getFormEventHandlers());
        this.tableEventHandlers = Objects.requireNonNull(formConfiguration.getTableEventHandlers());
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
        this.formPostProcessor = Objects.requireNonNull(formConfiguration.getFormPostProcessor());
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return representationId.startsWith("representations://");
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        Map<String, List<String>> parameters = this.urlParser.getParameterValues(representationId);
        String objectIdsParam = parameters.get("objectIds").get(0);

        var objectIds = this.urlParser.getParameterEntries(objectIdsParam);
        var objects = objectIds.stream()
            .map(objectId -> this.objectService.getObject(editingContext, objectId))
            .flatMap(Optional::stream)
            .toList();

        if (!objects.isEmpty()) {
            FormDescription formDescription = this.representationsDescriptionProvider.getRepresentationsDescription();
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
