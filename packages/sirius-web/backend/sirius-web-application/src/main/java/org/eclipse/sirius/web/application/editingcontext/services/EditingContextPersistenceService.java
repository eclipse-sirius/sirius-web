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
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Used to save the editing context.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextPersistenceService implements IEditingContextPersistenceService {

    private static final String TIMER_NAME = "siriusweb_editingcontext_save";

    private final List<IEditingContextSaver> editingContextSavers;

    private final Timer timer;

    private final Logger logger = LoggerFactory.getLogger(EditingContextPersistenceService.class);

    public EditingContextPersistenceService(List<IEditingContextSaver> editingContextSavers, MeterRegistry meterRegistry) {
        this.editingContextSavers = Objects.requireNonNull(editingContextSavers);
        this.timer = Timer.builder(TIMER_NAME).register(meterRegistry);
    }

    @Override
    @Transactional
    public void persist(ICause cause, IEditingContext editingContext) {
        long start = System.currentTimeMillis();

        this.editingContextSavers.stream()
                .filter(saver -> saver.canHandle(editingContext.getId()))
                .findFirst()
                .ifPresent(saver -> saver.save(cause, editingContext));

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);

        this.logger.atDebug()
                .setMessage("EditingContext {}: {}ms to persist the semantic data")
                .addArgument(editingContext.getId())
                .addArgument(() -> String.format("%1$6s", end - start))
                .log();
    }
}
