/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.web.application.views.explorer;

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
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Used to create the tree event processors.
 *
 * @author sbegaudeau
 */
@Service
public class ExplorerEventProcessorFactory implements IRepresentationEventProcessorFactory {

    public static final String TREE_DESCRIPTION_ID_PARAMETER = "treeDescriptionId";

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ITreeService treeService;

    private final List<ITreeEventHandler> treeEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IURLParser urlParser;

    public ExplorerEventProcessorFactory(IRepresentationDescriptionSearchService representationDescriptionSearchService, ITreeService treeService, List<ITreeEventHandler> treeEventHandlers,
            ISubscriptionManagerFactory subscriptionManagerFactory, IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry, IURLParser urlParser) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.treeService = Objects.requireNonNull(treeService);
        this.treeEventHandlers = Objects.requireNonNull(treeEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return representationId.startsWith("explorer://");
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        Map<String, List<String>> parameters = this.urlParser.getParameterValues(representationId);
        var treeDescriptionId = parameters.get(TREE_DESCRIPTION_ID_PARAMETER).get(0);
        Optional<TreeDescription> optionalTreeDescription = this.representationDescriptionSearchService
                .findById(editingContext, treeDescriptionId)
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast);

        if (optionalTreeDescription.isPresent()) {
            var treeDescription = optionalTreeDescription.get();

            String activeFilterIdsParam = parameters.get("activeFilterIds").get(0);
            var activeFilterIds = this.urlParser.getParameterEntries(activeFilterIdsParam);

            String expandedIdsParam = parameters.get("expandedIds").get(0);
            var expanded = this.urlParser.getParameterEntries(expandedIdsParam);

            TreeCreationParameters treeCreationParameters = TreeCreationParameters.newTreeCreationParameters(representationId)
                    .treeDescription(treeDescription)
                    .activeFilterIds(activeFilterIds)
                    .expanded(expanded)
                    .targetObject(editingContext)
                    .editingContext(editingContext)
                    .build();

            IRepresentationEventProcessor treeEventProcessor = new TreeEventProcessor(editingContext, this.treeService, treeCreationParameters, this.treeEventHandlers,
                    this.subscriptionManagerFactory.create(), new SimpleMeterRegistry(), this.representationRefreshPolicyRegistry);
            return Optional.of(treeEventProcessor);
        }
        return Optional.empty();
    }
}
