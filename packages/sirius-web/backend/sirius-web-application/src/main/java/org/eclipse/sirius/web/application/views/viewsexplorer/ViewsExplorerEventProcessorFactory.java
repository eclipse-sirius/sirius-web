/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.views.viewsexplorer;

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
import org.eclipse.sirius.web.application.views.viewsexplorer.services.ViewsExplorerTreeDescriptionProvider;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Used to create the views explorer event processors.
 *
 * @author tgiraudet
 */
@Service
public class ViewsExplorerEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ITreeService treeService;

    private final List<ITreeEventHandler> treeEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IURLParser urlParser;

    private final MeterRegistry meterRegistry;

    public ViewsExplorerEventProcessorFactory(IRepresentationDescriptionSearchService representationDescriptionSearchService, ITreeService treeService, List<ITreeEventHandler> treeEventHandlers,
            ISubscriptionManagerFactory subscriptionManagerFactory, IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry, IURLParser urlParser, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.treeService = Objects.requireNonNull(treeService);
        this.treeEventHandlers = Objects.requireNonNull(treeEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return representationId.startsWith(ViewsExplorerTreeDescriptionProvider.PREFIX);
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        Map<String, List<String>> parameters = this.urlParser.getParameterValues(representationId);
        Optional<TreeDescription> optionalTreeDescription = this.representationDescriptionSearchService
                .findById(editingContext, ViewsExplorerTreeDescriptionProvider.DESCRIPTION_ID)
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast);

        var expandedIdsParameter = parameters.get("expandedIds");

        if (optionalTreeDescription.isPresent() && !expandedIdsParameter.isEmpty()) {
            var treeDescription = optionalTreeDescription.get();

            String expandedIdsParam = parameters.get("expandedIds").get(0);
            var expanded = this.urlParser.getParameterEntries(expandedIdsParam);

            TreeCreationParameters treeCreationParameters = TreeCreationParameters.newTreeCreationParameters(representationId)
                    .treeDescription(treeDescription)
                    .activeFilterIds(List.of())
                    .expanded(expanded)
                    .targetObject(editingContext)
                    .editingContext(editingContext)
                    .build();

            IRepresentationEventProcessor treeEventProcessor = new TreeEventProcessor(editingContext, this.treeService, treeCreationParameters, this.treeEventHandlers,
                    this.subscriptionManagerFactory.create(), this.meterRegistry, this.representationRefreshPolicyRegistry);
            return Optional.of(treeEventProcessor);
        }
        return Optional.empty();
    }
}
