/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.selection.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.trees.TreeEventProcessor;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeService;
import org.eclipse.sirius.components.collaborative.trees.api.TreeCreationParameters;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Used to create the tree event processors in the context of a selection dialog tree.
 *
 * @author fbarbin
 */
@Service
public class SelectionDialogTreeEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ITreeService treeService;

    private final List<ITreeEventHandler> treeEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IURLParser urlParser;

    public SelectionDialogTreeEventProcessorFactory(IRepresentationDescriptionSearchService representationDescriptionSearchService, List<ITreeEventHandler> treeEventHandlers, ITreeService treeService,
            IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry, ISubscriptionManagerFactory subscriptionManagerFactory, IURLParser urlParser) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.treeService = Objects.requireNonNull(treeService);
        this.treeEventHandlers = Objects.requireNonNull(treeEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return representationId.startsWith("selection://");
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        Optional<TreeDescription> optionalTreeDescription = this.findTreeDescription(editingContext, representationId);
        if (optionalTreeDescription.isPresent()) {
            var treeDescription = optionalTreeDescription.get();

            Map<String, List<String>> parameters = this.urlParser.getParameterValues(representationId);
            String expandedIds = parameters.get("expandedIds").get(0);
            var expanded = this.urlParser.getParameterEntries(expandedIds);

            TreeCreationParameters treeCreationParameters = TreeCreationParameters.newTreeCreationParameters(representationId)
                    .treeDescription(treeDescription)
                    .activeFilterIds(List.of())
                    .expanded(expanded)
                    .editingContext(editingContext)
                    .build();

            IRepresentationEventProcessor treeEventProcessor = new TreeEventProcessor(editingContext, this.treeService, treeCreationParameters, this.treeEventHandlers,
                    this.subscriptionManagerFactory.create(), new SimpleMeterRegistry(), this.representationRefreshPolicyRegistry);
            return Optional.of(treeEventProcessor);
        }

        return Optional.empty();
    }

    private Optional<TreeDescription> findTreeDescription(IEditingContext editingContext, String representationId) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, representationId);
        return this.representationDescriptionSearchService
                .findAll(editingContext).values().stream()
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast)
                .filter(treeDescription -> treeDescription.getCanCreatePredicate().test(variableManager))
                .findFirst();
    }
}
