/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.browser;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.browser.api.IModelBrowserMetadataProvider;
import org.eclipse.sirius.components.collaborative.browser.api.ModelBrowserMetadata;
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
 * Used to create the tree event processors in the context of model browser.
 *
 * @author Jerome Gout
 */
@Service
public class ModelBrowserEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ITreeService treeService;

    private final List<ITreeEventHandler> treeEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IURLParser urlParser;

    private final List<IModelBrowserMetadataProvider> modelBrowserMetadataProviders;

    public ModelBrowserEventProcessorFactory(IRepresentationDescriptionSearchService representationDescriptionSearchService, List<ITreeEventHandler> treeEventHandlers, ITreeService treeService,
            IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry, ISubscriptionManagerFactory subscriptionManagerFactory, IURLParser urlParser,
            List<IModelBrowserMetadataProvider> modelBrowserMetadataProviders) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.treeService = Objects.requireNonNull(treeService);
        this.treeEventHandlers = Objects.requireNonNull(treeEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.modelBrowserMetadataProviders = Objects.requireNonNull(modelBrowserMetadataProviders);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return representationId.startsWith(DefaultModelBrowsersTreeDescriptionProvider.PREFIX);
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        Optional<TreeDescription> optionalTreeDescription = this.findModelBrowserTreeDescription(editingContext, representationId);
        if (optionalTreeDescription.isPresent()) {
            var treeDescription = optionalTreeDescription.get();

            Map<String, List<String>> parameters = this.urlParser.getParameterValues(representationId);
            String expandedIdsParam = parameters.get("expandedIds").get(0);

            var expanded = this.urlParser.getParameterEntries(expandedIdsParam);

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

    private Optional<TreeDescription> findModelBrowserTreeDescription(IEditingContext editingContext, String representationId) {
        return this.extractModelBroswerId(representationId)
                   .flatMap(modelBrowserId -> this.findModelBrowserTreeDescriptionId(editingContext, modelBrowserId))
                   .flatMap(treeDescriptionId -> this.representationDescriptionSearchService.findById(editingContext, treeDescriptionId))
                   .filter(TreeDescription.class::isInstance)
                   .map(TreeDescription.class::cast);
    }

    private Optional<String> findModelBrowserTreeDescriptionId(IEditingContext editingContext, String modelBrowserId) {
        return this.modelBrowserMetadataProviders.stream()
                .flatMap(provider -> provider.getModelBrowsersMetadata(editingContext).stream())
                .filter(modelBrowserMetadata -> Objects.equals(modelBrowserMetadata.modelBrowserId(), modelBrowserId))
                .map(ModelBrowserMetadata::treeDescriptionId)
                .findFirst();
    }

    private Optional<String> extractModelBroswerId(String representationId) {
        if (representationId.startsWith(DefaultModelBrowsersTreeDescriptionProvider.PREFIX)) {
            String modelBrowserId = representationId.substring(DefaultModelBrowsersTreeDescriptionProvider.PREFIX.length());
            int endOffset = modelBrowserId.indexOf("?");
            if (endOffset != -1) {
                modelBrowserId = modelBrowserId.substring(0, endOffset);
            }
            return Optional.of(modelBrowserId);
        }
        return Optional.empty();
    }

}
