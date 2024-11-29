/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.collaborative.tables;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * Payload used to indicate that the global filter value of a table has changed.
 *
 * @author frouene
 */
public record TableGlobalFilterValuePayload(UUID id, String globalFilterValue) implements IPayload {

    public TableGlobalFilterValuePayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(globalFilterValue);
    }
}
