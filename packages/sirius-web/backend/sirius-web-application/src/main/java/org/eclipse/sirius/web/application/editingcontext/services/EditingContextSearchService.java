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
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextLoader;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
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

    private final List<IEditingContextLoader> editingContextLoader;

    private final IProjectSearchService projectSearchService;

    private final Timer timer;

    public EditingContextSearchService(List<IEditingContextLoader> editingContextLoader, IProjectSearchService projectSearchService, MeterRegistry meterRegistry) {
        this.editingContextLoader = Objects.requireNonNull(editingContextLoader);
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.timer = Timer.builder(TIMER_NAME).register(meterRegistry);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String editingContextId) {
        var normalizedId = editingContextId.split("\\+");
        if (normalizedId.length == 2 && new UUIDParser().parse(normalizedId[0]).isPresent() && new UUIDParser().parse(normalizedId[1]).isPresent()) {
            return this.projectSearchService.existsById(normalizedId[0]);
        }
        return this.projectSearchService.existsById(editingContextId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IEditingContext> findById(String editingContextId) {
        long start = System.currentTimeMillis();

        var optionalEditingContext = this.editingContextLoader.stream()
                .filter(loader -> loader.canHandle(editingContextId))
                .findFirst()
                .map(loader -> loader.load(editingContextId));

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);

        if (optionalEditingContext.isPresent() && optionalEditingContext.get() instanceof EditingContext editingContext) {
            this.logger.atDebug()
                    .setMessage("EditingContext {}: {}ms to load {} objects")
                    .addArgument(editingContextId)
                    .addArgument(() -> String.format("%1$6s", end - start))
                    .addArgument(() -> {
                        var iterator = editingContext.getDomain().getResourceSet().getAllContents();
                        var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
                        return stream.count();
                    })
                    .log();
        }

        return optionalEditingContext;
    }

}
