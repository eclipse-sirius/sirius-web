/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.events;

import java.util.Objects;
import java.util.UUID;

/**
 * Used to decorate an existing cause with metadata.
 *
 * @author gdaniel
 */
public record DecoratedCause(UUID id, String label, ICause causedBy) implements ICause {

    public DecoratedCause {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(causedBy);
    }
}
