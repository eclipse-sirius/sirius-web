/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Message;

/**
 * The "initial direct edit element label" success payload.
 *
 * @author gcoutable
 */
public record InvokeImpactAnalysisToolSuccessPayload(UUID id, ImpactAnalysisReport impactAnalysisReport, List<Message> messages) implements IPayload {

    public InvokeImpactAnalysisToolSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(impactAnalysisReport);
        Objects.requireNonNull(messages);
    }
}
