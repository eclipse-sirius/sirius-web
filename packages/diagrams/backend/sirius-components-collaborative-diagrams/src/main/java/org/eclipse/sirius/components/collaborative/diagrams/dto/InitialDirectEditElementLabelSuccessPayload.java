/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The "initial direct edit element label" success payload.
 *
 * @author gcoutable
 */
public class InitialDirectEditElementLabelSuccessPayload implements IPayload {

    private final UUID id;

    private final String initialDirectEditElementLabel;

    public InitialDirectEditElementLabelSuccessPayload(UUID id, String initialDirectEditElementLabel) {
        this.id = Objects.requireNonNull(id);
        this.initialDirectEditElementLabel = Objects.requireNonNull(initialDirectEditElementLabel);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getInitialDirectEditElementLabel() {
        return this.initialDirectEditElementLabel;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, initialDirectEditElementLabel: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.initialDirectEditElementLabel);
    }

}
