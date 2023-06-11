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
import org.eclipse.sirius.components.dynamicdialogs.DynamicDialog;

/**
 * The payload of the dynamic dialog query.
 *
 * @author lfasani
 */
public record DynamicDialogSuccessPayload(UUID id, DynamicDialog dynamicDialog) implements IPayload {

    @Override
    public UUID id() {
        return this.id;
    }
}