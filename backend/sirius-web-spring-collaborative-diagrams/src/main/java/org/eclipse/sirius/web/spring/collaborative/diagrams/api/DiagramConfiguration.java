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
package org.eclipse.sirius.web.spring.collaborative.diagrams.api;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration used to create a diagram event processor.
 *
 * @author sbegaudeau
 */
public class DiagramConfiguration implements IRepresentationConfiguration {

    private final UUID diagramId;

    public DiagramConfiguration(UUID diagramId) {
        this.diagramId = Objects.requireNonNull(diagramId);
    }

    @Override
    public UUID getId() {
        return this.diagramId;
    }

}
