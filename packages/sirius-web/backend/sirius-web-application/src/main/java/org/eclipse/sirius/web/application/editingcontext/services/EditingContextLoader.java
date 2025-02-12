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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextLoader;
import org.eclipse.sirius.web.application.editingcontext.services.api.ISemanticDataLoader;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to load an editing context.
 *
 * @author frouene
 */
@Service
public class EditingContextLoader implements IEditingContextLoader {

    private static final String TIMER_NAME = "siriusweb_editingcontext_load";

    private final Logger logger = LoggerFactory.getLogger(EditingContextLoader.class);

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final ISemanticDataLoader semanticDataLoader;

    private final List<IEditingContextRepresentationDescriptionProvider> representationDescriptionProviders;

    private final List<IEditingContextProcessor> editingContextProcessors;

    public EditingContextLoader(IProjectSemanticDataSearchService projectSemanticDataSearchService, ISemanticDataLoader semanticDataLoader, List<IEditingContextRepresentationDescriptionProvider> representationDescriptionProviders, List<IEditingContextProcessor> editingContextProcessors) {
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.semanticDataLoader = Objects.requireNonNull(semanticDataLoader);
        this.representationDescriptionProviders = Objects.requireNonNull(representationDescriptionProviders);
        this.editingContextProcessors = Objects.requireNonNull(editingContextProcessors);
    }

    @Override
    public boolean canHandle(String editingContextId) {
        return this.projectSemanticDataSearchService.findBySemanticDataId(AggregateReference.to(UUID.fromString(editingContextId))).isPresent();
    }

    public void load(EditingContext editingContext, SemanticData semanticData) {
        this.editingContextProcessors.forEach(processor -> processor.preProcess(editingContext));

        this.semanticDataLoader.load(editingContext);

        this.representationDescriptionProviders.forEach(representationDescriptionProvider -> {
            var representationDescriptions = representationDescriptionProvider.getRepresentationDescriptions(editingContext);
            representationDescriptions.forEach(representationDescription -> editingContext.getRepresentationDescriptions().put(representationDescription.getId(), representationDescription));
        });

        this.editingContextProcessors.forEach(processor -> processor.postProcess(editingContext));
    }
}
