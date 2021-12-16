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
package org.eclipse.sirius.web.spring.collaborative.selection;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.selection.description.SelectionDescription;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.web.spring.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.web.spring.collaborative.selection.api.ISelectionEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.selection.api.SelectionConfiguration;
import org.springframework.stereotype.Service;

/**
 * Used to create the selection event processors.
 *
 * @author arichard
 */
@Service
public class SelectionEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IObjectService objectService;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    public SelectionEventProcessorFactory(IRepresentationDescriptionSearchService representationDescriptionSearchService, IObjectService objectService,
            ISubscriptionManagerFactory subscriptionManagerFactory, IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.objectService = Objects.requireNonNull(objectService);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return ISelectionEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof SelectionConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (ISelectionEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof SelectionConfiguration) {
            SelectionConfiguration selectionConfiguration = (SelectionConfiguration) configuration;

            // @formatter:off
            Optional<SelectionDescription> optionalSelectionDescription = this.representationDescriptionSearchService
                    .findById(editingContext, selectionConfiguration.getSelectionId().toString())
                    .filter(SelectionDescription.class::isInstance)
                    .map(SelectionDescription.class::cast);
            // @formatter:on

            Optional<Object> optionalObject = this.objectService.getObject(editingContext, selectionConfiguration.getTargetObjectId());
            if (optionalSelectionDescription.isPresent() && optionalObject.isPresent()) {
                SelectionDescription selectionDescription = optionalSelectionDescription.get();
                Object object = optionalObject.get();
                String label = this.objectService.getLabel(object);

                IRepresentationEventProcessor selectionEventProcessor = new SelectionEventProcessor(editingContext, selectionDescription, selectionConfiguration.getId(), object, label,
                        this.subscriptionManagerFactory.create(), this.representationRefreshPolicyRegistry);

                // @formatter:off
                return Optional.of(selectionEventProcessor)
                        .filter(representationEventProcessorClass::isInstance)
                        .map(representationEventProcessorClass::cast);
                // @formatter:on

            }
        }
        return Optional.empty();
    }

}
