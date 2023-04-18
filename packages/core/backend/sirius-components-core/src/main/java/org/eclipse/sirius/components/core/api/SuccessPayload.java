/*******************************************************************************
 * Copyright (c) 2023, 2023 Obeo.
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
package org.eclipse.sirius.components.core.api;

import java.util.Objects;
import java.util.UUID;

/**
 * General purpose success payload.
 *
 * @author mcharfadi
 */
public record SuccessPayload(UUID id) implements IPayload {
    public SuccessPayload {
        Objects.requireNonNull(id);
    }
}
