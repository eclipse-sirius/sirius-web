/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams;

import java.util.Objects;

import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramRefreshManager;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.IDiagramRefreshManagerFactory;
import org.eclipse.sirius.web.diagrams.layout.api.ILayoutService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Creates the diagram refresh manager.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramRefreshManagerFactory implements IDiagramRefreshManagerFactory {

    private final IDiagramService diagramService;

    private final IRepresentationService representationService;

    private final ILayoutService layoutService;

    private final MeterRegistry meterRegistry;

    public DiagramRefreshManagerFactory(IDiagramService diagramService, IRepresentationService representationService, ILayoutService layoutService, MeterRegistry meterRegistry) {
        this.diagramService = Objects.requireNonNull(diagramService);
        this.representationService = Objects.requireNonNull(representationService);
        this.layoutService = Objects.requireNonNull(layoutService);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
    }

    @Override
    public IDiagramRefreshManager create() {
        return new DiagramRefreshManager(this.representationService, this.diagramService, this.layoutService, this.meterRegistry);
    }

}
