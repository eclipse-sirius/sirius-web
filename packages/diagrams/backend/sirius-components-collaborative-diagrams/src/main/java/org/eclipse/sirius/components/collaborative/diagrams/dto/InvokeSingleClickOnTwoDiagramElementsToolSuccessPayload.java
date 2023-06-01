/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.WorkbenchSelection;

/**
 * The payload of the "Invoke single click on two diagram elements" mutation returned on success.
 *
 * @author pcdavid
 * @author hmarchadour
 */
public record InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload(UUID id, WorkbenchSelection newSelection, List<Message> messages) implements IPayload {

    public InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload {
        Objects.requireNonNull(id);
    }
}
