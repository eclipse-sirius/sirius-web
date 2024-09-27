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
package org.eclipse.sirius.components.collaborative.tables;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.tables.Table;

/**
 * Payload used to indicate that the table representation has been refreshed.
 *
 * @author arichard
 */
public record TableRefreshedEventPayload(UUID id, Table table) implements IPayload {
    public TableRefreshedEventPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(table);
    }
}
