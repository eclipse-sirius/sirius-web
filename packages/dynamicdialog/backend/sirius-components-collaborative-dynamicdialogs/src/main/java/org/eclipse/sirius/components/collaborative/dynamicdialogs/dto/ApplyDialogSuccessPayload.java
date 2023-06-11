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
package org.eclipse.sirius.components.collaborative.dynamicdialogs.dto;

import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload of the apply dialog mutation.
 *
 * @author lfasani
 */
public record ApplyDialogSuccessPayload(UUID id) implements IPayload {

    @Override
    public UUID id() {
        return this.id;
    }
}