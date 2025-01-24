/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.view.fork.dto;

import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;

/**
 * Service used to insert needed document in a forked project.
 *
 * @author mcharfadi
 */
public record ForkSemanticDataUpdatedEvent(
        UUID id,
        ICause causedBy,
        String previousSourceId,
        String previousSourceElementId,
        String previousDescriptionId,
        String newSourceId,
        String newSourceElementId) implements ICause {
}
