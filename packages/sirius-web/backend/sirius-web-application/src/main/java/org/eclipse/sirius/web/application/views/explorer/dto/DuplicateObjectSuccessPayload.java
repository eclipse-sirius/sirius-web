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
package org.eclipse.sirius.web.application.views.explorer.dto;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Message;

import jakarta.validation.constraints.NotNull;

/**
 * The payload of the duplicate object mutation.
 *
 * @author lfasani
 */
public record DuplicateObjectSuccessPayload(@NotNull UUID id, @NotNull Object object, @NotNull List<Message> messages) implements IPayload {
}
