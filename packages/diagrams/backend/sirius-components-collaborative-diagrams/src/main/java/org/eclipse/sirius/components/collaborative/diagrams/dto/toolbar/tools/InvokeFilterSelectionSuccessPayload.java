/*******************************************************************************
 * Copyright (c) 2026 Obeo and others.
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
package org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Message;

/**
 * The payload of the "InvokeFilterSelectionMenuItem" mutation returned on success.
 *
 * @author mcharfadi
 */
public record InvokeFilterSelectionSuccessPayload(UUID id, List<String> newSelection, List<Message> messages) implements IPayload {

    public InvokeFilterSelectionSuccessPayload {
        Objects.requireNonNull(id);
    }
}
