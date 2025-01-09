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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextLoader;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingDomainFactory;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to find and retrieve editing contexts.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextSearchService implements IEditingContextSearchService {

    private static final String TIMER_NAME = "siriusweb_editingcontext_load";

    private final Logger logger = LoggerFactory.getLogger(EditingContextSearchService.class);

    private final ISemanticDataSearchService semanticDataSearchService;

    private final IEditingDomainFactory editingDomainFactory;

    private final IEditingContextLoader editingContextLoader;

    private final Timer timer;

    public EditingContextSearchService(ISemanticDataSearchService semanticDataSearchService, IEditingDomainFactory editingDomainFactory, IEditingContextLoader editingContextLoader, MeterRegistry meterRegistry) {
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.editingDomainFactory = Objects.requireNonNull(editingDomainFactory);
        this.editingContextLoader = Objects.requireNonNull(editingContextLoader);
        this.timer = Timer.builder(TIMER_NAME).register(meterRegistry);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String editingContextId) {
        return new UUIDParser().parse(editingContextId)
                .filter(this.semanticDataSearchService::existsById)
                .isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IEditingContext> findById(String editingContextId) {
        return new UUIDParser().parse(editingContextId)
                .flatMap(this.semanticDataSearchService::findById)
                .map(semanticData -> this.toEditingContext(semanticData));
    }

    private IEditingContext toEditingContext(SemanticData semanticData) {
        long start = System.currentTimeMillis();

        AdapterFactoryEditingDomain editingDomain = this.editingDomainFactory.createEditingDomain();
        EditingContext editingContext = new EditingContext(semanticData.getId().toString(), editingDomain, new HashMap<>(), new ArrayList<>());
        this.editingContextLoader.load(editingContext, semanticData);

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);

        this.logger.atDebug()
                .setMessage("EditingContext {}: {}ms to load {} objects")
                .addArgument(semanticData.getId())
                .addArgument(() -> String.format("%1$6s", end - start))
                .addArgument(() -> {
                    var iterator = editingDomain.getResourceSet().getAllContents();
                    var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
                    return stream.count();
                })
                .log();

        return editingContext;
    }

}
