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
package org.eclipse.sirius.web.spring.collaborative.trees;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.collaborative.api.services.IRepresentationConfiguration;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.web.collaborative.api.services.ISubscriptionManagerFactory;
import org.eclipse.sirius.web.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.web.collaborative.trees.api.ITreeEventProcessor;
import org.eclipse.sirius.web.collaborative.trees.api.ITreeService;
import org.eclipse.sirius.web.collaborative.trees.api.TreeConfiguration;
import org.eclipse.sirius.web.collaborative.trees.api.TreeCreationParameters;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;
import org.eclipse.sirius.web.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Used to create the tree event processors.
 *
 * @author sbegaudeau
 */
@Service
public class TreeEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationDescriptionService representationDescriptionService;

    private final ITreeService treeService;

    private final List<ITreeEventHandler> treeEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    public TreeEventProcessorFactory(IRepresentationDescriptionService representationDescriptionService, ITreeService treeService, List<ITreeEventHandler> treeEventHandlers,
            ISubscriptionManagerFactory subscriptionManagerFactory) {
        this.representationDescriptionService = Objects.requireNonNull(representationDescriptionService);
        this.treeService = Objects.requireNonNull(treeService);
        this.treeEventHandlers = Objects.requireNonNull(treeEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return ITreeEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof TreeConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext, Context context) {
        if (ITreeEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof TreeConfiguration) {
            TreeConfiguration treeConfiguration = (TreeConfiguration) configuration;

            // @formatter:off
            Optional<TreeDescription> optionalTreeDescription = this.representationDescriptionService.findRepresentationDescriptionById(IRepresentationDescriptionService.EXPLORER_TREE_DESCRIPTION)
                    .filter(TreeDescription.class::isInstance)
                    .map(TreeDescription.class::cast);
            // @formatter:on
            if (optionalTreeDescription.isPresent()) {
                TreeDescription treeDescription = optionalTreeDescription.get();

                // @formatter:off
                TreeCreationParameters treeCreationParameters = TreeCreationParameters.newTreeCreationParameters(treeConfiguration.getId())
                        .treeDescription(treeDescription)
                        .expanded(treeConfiguration.getExpanded())
                        .editingContext(editingContext)
                        .build();
                // @formatter:on

                IRepresentationEventProcessor treeEventProcessor = new TreeEventProcessor(this.treeService, treeCreationParameters, this.treeEventHandlers, this.subscriptionManagerFactory.create(),
                        new SimpleMeterRegistry());
                // @formatter:off
                return Optional.of(treeEventProcessor)
                        .filter(representationEventProcessorClass::isInstance)
                        .map(representationEventProcessorClass::cast);
                // @formatter:on
            }
        }
        return Optional.empty();
    }

}
