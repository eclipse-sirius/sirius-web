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
package org.eclipse.sirius.components.collaborative.widget.reference.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.widget.reference.ReferenceValue;

/**
 * The payload object for the query referenceValueOptions.
 *
 * @author frouene
 */
public record ReferenceValueOptionsQueryPayload(UUID id, List<ReferenceValue> options) implements IPayload {

    public ReferenceValueOptionsQueryPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(options);
    }

}
