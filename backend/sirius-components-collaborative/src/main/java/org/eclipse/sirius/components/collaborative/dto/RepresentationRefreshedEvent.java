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
package org.eclipse.sirius.components.collaborative.dto;

import java.util.Objects;

import org.eclipse.sirius.components.representations.IRepresentation;

/**
 * An internal event indicating that a representation has been refreshed.
 *
 * @author sbegaudeau
 */
public class RepresentationRefreshedEvent {
    private final String projectId;

    private final IRepresentation representation;

    public RepresentationRefreshedEvent(String projectId, IRepresentation representation) {
        this.projectId = Objects.requireNonNull(projectId);
        this.representation = Objects.requireNonNull(representation);
    }

    public String getProjectId() {
        return this.projectId;
    }

    public IRepresentation getRepresentation() {
        return this.representation;
    }
}
