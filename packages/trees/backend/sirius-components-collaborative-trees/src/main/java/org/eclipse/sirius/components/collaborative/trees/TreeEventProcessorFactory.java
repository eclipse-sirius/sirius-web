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
package org.eclipse.sirius.components.collaborative.trees;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeService;
import org.eclipse.sirius.components.collaborative.trees.api.TreeConfiguration;
import org.eclipse.sirius.components.collaborative.trees.api.TreeCreationParameters;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Used to create the tree event processors.
 *
 * @author sbegaudeau
 */
@Service
public class TreeEventProcessorFactory implements IRepresentationEventProcessorFactory {

    public static final String TREE_ID = UUID.nameUUIDFromBytes("explorer_tree_description".getBytes()).toString();

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ITreeService treeService;

    private final List<ITreeEventHandler> treeEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    public TreeEventProcessorFactory(IRepresentationDescriptionSearchService representationDescriptionSearchService, ITreeService treeService, List<ITreeEventHandler> treeEventHandlers,
            ISubscriptionManagerFactory subscriptionManagerFactory, IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.treeService = Objects.requireNonNull(treeService);
        this.treeEventHandlers = Objects.requireNonNull(treeEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
    }

    @Override
    public boolean canHandle(IRepresentationConfiguration configuration) {
        return configuration instanceof TreeConfiguration;
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IRepresentationConfiguration configuration, IEditingContext editingContext) {
        if (configuration instanceof TreeConfiguration treeConfiguration) {

            Optional<TreeDescription> optionalTreeDescription = this.findTreeDescription(editingContext, treeConfiguration);
            if (optionalTreeDescription.isPresent()) {
                var treeDescription = optionalTreeDescription.get();

                TreeCreationParameters treeCreationParameters = TreeCreationParameters.newTreeCreationParameters(treeConfiguration.getId())
                        .treeDescription(treeDescription)
                        .activeFilterIds(treeConfiguration.getActiveFilterIds())
                        .expanded(treeConfiguration.getExpanded())
                        .editingContext(editingContext)
                        .build();

                IRepresentationEventProcessor treeEventProcessor = new TreeEventProcessor(editingContext, this.treeService, treeCreationParameters, this.treeEventHandlers,
                        this.subscriptionManagerFactory.create(), new SimpleMeterRegistry(), this.representationRefreshPolicyRegistry);
                return Optional.of(treeEventProcessor);
            }
        }
        return Optional.empty();
    }

    private Optional<TreeDescription> findTreeDescription(IEditingContext editingContext, TreeConfiguration treeConfiguration) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(TreeConfiguration.TREE_ID, treeConfiguration.getId());
        return this.representationDescriptionSearchService
                .findAll(editingContext).values().stream()
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast)
                .filter(treeDescription -> treeDescription.getCanCreatePredicate().test(variableManager))
                .findFirst();
    }

}
