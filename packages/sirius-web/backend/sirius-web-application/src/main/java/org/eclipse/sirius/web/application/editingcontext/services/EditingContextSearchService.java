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
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
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

    private final IProjectSearchService projectSearchService;

    private final IEditingDomainFactory editingDomainFactory;

    private final IEditingContextLoader editingContextLoader;

    private final Timer timer;

    public EditingContextSearchService(IProjectSearchService projectSearchService, IEditingDomainFactory editingDomainFactory, IEditingContextLoader editingContextLoader, MeterRegistry meterRegistry) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.editingDomainFactory = Objects.requireNonNull(editingDomainFactory);
        this.editingContextLoader = Objects.requireNonNull(editingContextLoader);
        this.timer = Timer.builder(TIMER_NAME).register(meterRegistry);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String editingContextId) {
        return new UUIDParser().parse(editingContextId)
                .map(this.projectSearchService::existsById)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IEditingContext> findById(String editingContextId) {
        return new UUIDParser().parse(editingContextId)
                .flatMap(this.projectSearchService::findById)
                .map(this::toEditingContext);
    }

    private IEditingContext toEditingContext(Project project) {
        long start = System.currentTimeMillis();

        this.logger.debug("Loading the editing context {}", project.getId());

        AdapterFactoryEditingDomain editingDomain = this.editingDomainFactory.createEditingDomain(project);
        EditingContext editingContext = new EditingContext(project.getId().toString(), editingDomain, new HashMap<>(), new ArrayList<>());
        this.editingContextLoader.load(editingContext, project.getId());

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);

        this.logger.atDebug()
                .setMessage("{} objects have been loaded in {} ms")
                .addArgument(() -> {
                    var iterator = editingDomain.getResourceSet().getAllContents();
                    var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
                    return stream.count();
                })
                .addArgument(end - start)
                .log();

        return editingContext;
    }

}
