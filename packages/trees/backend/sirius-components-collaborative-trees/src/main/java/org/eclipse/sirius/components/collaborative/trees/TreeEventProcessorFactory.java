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
package org.eclipse.sirius.components.collaborative.trees;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.api.RepresentationEventProcessorFactoryConfiguration;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeService;
import org.eclipse.sirius.components.collaborative.trees.api.TreeCreationParameters;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Used to create the tree event processors.
 *
 * @author Jerome Gout
 */
@Service
public class TreeEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private static final String EXPANDED_IDS = "?expandedIds=";

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IObjectService objectService;

    private final ITreeService treeService;

    private final List<ITreeEventHandler> treeEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IURLParser urlParser;

    public TreeEventProcessorFactory(RepresentationEventProcessorFactoryConfiguration configuration, IObjectService objectService, ITreeService treeService, List<ITreeEventHandler> treeEventHandlers,
            IURLParser urlParser) {
        this.representationSearchService = Objects.requireNonNull(configuration.getRepresentationSearchService());
        this.representationDescriptionSearchService = Objects.requireNonNull(configuration.getRepresentationDescriptionSearchService());
        this.objectService = Objects.requireNonNull(objectService);
        this.treeService = Objects.requireNonNull(treeService);
        this.treeEventHandlers = Objects.requireNonNull(treeEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(configuration.getSubscriptionManagerFactory());
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(configuration.getRepresentationRefreshPolicyRegistry());
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return this.representationSearchService.existByIdAndKind(this.getTreeIdFromRepresentationId(representationId), List.of(Tree.KIND));
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        Optional<Tree> optionalTree = this.representationSearchService.findById(editingContext, this.getTreeIdFromRepresentationId(representationId), Tree.class);
        if (optionalTree.isPresent()) {
            Tree tree = optionalTree.get();
            Optional<TreeDescription> optionalTreeDescription = this.representationDescriptionSearchService.findById(editingContext, tree.getDescriptionId())
                    .filter(TreeDescription.class::isInstance)
                    .map(TreeDescription.class::cast);
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, tree.getTargetObjectId());
            if (optionalTreeDescription.isPresent() && optionalObject.isPresent()) {
                TreeDescription treeDescription = optionalTreeDescription.get();
                Object object = optionalObject.get();

                TreeCreationParameters treeCreationParameters = TreeCreationParameters.newTreeCreationParameters(representationId)
                        .treeDescription(treeDescription)
                        .activeFilterIds(List.of())
                        .expanded(this.getExpandedIdsFromRepresentationId(representationId))
                        .editingContext(editingContext)
                        .targetObject(object)
                        .build();

                IRepresentationEventProcessor treeEventProcessor = new TreeEventProcessor(editingContext, this.treeService, treeCreationParameters, this.treeEventHandlers,
                        this.subscriptionManagerFactory.create(), new SimpleMeterRegistry(), this.representationRefreshPolicyRegistry);
                return Optional.of(treeEventProcessor);
            }
        }
        return Optional.empty();
    }

    String getTreeIdFromRepresentationId(String representationId) {
        if (representationId.indexOf(EXPANDED_IDS) > 0) {
            return representationId.substring(0, representationId.indexOf(EXPANDED_IDS));
        }
        return "";
    }

    List<String> getExpandedIdsFromRepresentationId(String representationId) {
        if (representationId.indexOf(EXPANDED_IDS) > 0) {
            String rowExpanded = representationId.substring(representationId.indexOf(EXPANDED_IDS) + EXPANDED_IDS.length());
            return this.urlParser.getParameterEntries(rowExpanded);
        }
        return List.of();
    }

}
