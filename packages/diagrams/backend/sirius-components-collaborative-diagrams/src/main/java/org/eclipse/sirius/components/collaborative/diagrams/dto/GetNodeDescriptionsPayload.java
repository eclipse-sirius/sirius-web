/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;

/**
 * The payload of the "Get child or border node descriptions" query returned on success.
 *
 * @author frouene
 */
public record GetNodeDescriptionsPayload(UUID id, List<NodeDescription> nodeDescriptions) implements IPayload {

    public GetNodeDescriptionsPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(nodeDescriptions);
    }
}
