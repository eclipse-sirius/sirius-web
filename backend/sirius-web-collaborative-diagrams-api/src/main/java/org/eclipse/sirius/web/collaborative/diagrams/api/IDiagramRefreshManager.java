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
package org.eclipse.sirius.web.collaborative.diagrams.api;

import java.util.UUID;

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.services.api.dto.IPayload;

import reactor.core.publisher.Flux;

/**
 * Interface of the diagram refresh manager.
 *
 * @author sbegaudeau
 */
public interface IDiagramRefreshManager {
    /**
     * The name of the variable used to store and retrieve the diagram refresh manager from a variable manager.
     */
    String DIAGRAM_REFRESH_MANAGER = "diagramRefreshManager"; //$NON-NLS-1$

    void initialize(UUID projectId, DiagramCreationParameters diagramCreationParameters);

    void refresh(UUID projectId, DiagramCreationParameters diagramCreationParameters);

    void setDiagram(Diagram diagram);

    Diagram getDiagram();

    Flux<IPayload> getFlux();

    void dispose();

    void preDestroy();
}
