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
package org.eclipse.sirius.web.application.views.explorer.handlers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.views.explorer.dto.EditingContextExplorerDescriptionsInput;
import org.eclipse.sirius.web.application.views.explorer.dto.EditingContextExplorerDescriptionsPayload;
import org.eclipse.sirius.web.application.views.explorer.dto.ExplorerDescriptionMetadata;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTreeDescriptionOrderer;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTreeDescriptionProvider;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Event handler to find all the explorer descriptions accessible from a given editing context.
 *
 * @author Jerome Gout
 */
@Service
public class EditingContextExplorerDescriptionEventHandler implements IEditingContextEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final List<IExplorerTreeDescriptionProvider> explorerTreeDescriptionProviders;

    private final List<IExplorerTreeDescriptionOrderer> explorerTreeDescriptionOrderers;

    public EditingContextExplorerDescriptionEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, List<IExplorerTreeDescriptionProvider> explorerTreeDescriptionProviders, List<IExplorerTreeDescriptionOrderer> explorerTreeDescriptionOrderers) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.explorerTreeDescriptionProviders = Objects.requireNonNull(explorerTreeDescriptionProviders);
        this.explorerTreeDescriptionOrderers = Objects.requireNonNull(explorerTreeDescriptionOrderers);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof EditingContextExplorerDescriptionsInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        List<ExplorerDescriptionMetadata> explorerDescriptions = List.of();
        if (input instanceof EditingContextExplorerDescriptionsInput) {
            explorerDescriptions = this.findAllExplorerTreeDescriptions(editingContext);
        }
        payloadSink.tryEmitValue(new EditingContextExplorerDescriptionsPayload(input.id(), explorerDescriptions));
    }

    List<ExplorerDescriptionMetadata> findAllExplorerTreeDescriptions(IEditingContext editingContext) {
        var optionalDefaultExplorerDescription = this.representationDescriptionSearchService.findById(editingContext, ExplorerDescriptionProvider.DESCRIPTION_ID)
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast);

        var explorers = this.explorerTreeDescriptionProviders.stream()
                .flatMap(provider -> provider.getDescriptions(editingContext).stream())
                .map(treeDescription -> new ExplorerDescriptionMetadata(treeDescription.getId(), treeDescription.getLabel()))
                .collect(Collectors.toList());

        optionalDefaultExplorerDescription.ifPresent(treeDescription -> explorers.add(new ExplorerDescriptionMetadata(treeDescription.getId(), treeDescription.getLabel())));

        this.explorerTreeDescriptionOrderers.forEach(orderer -> orderer.order(explorers));
        return explorers;
    }
}
