/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.portals.dto;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.portals.api.IPortalInput;

/**
 * The input for the Portal mutation to add a new view to a Portal.
 *
 * @author pcdavid
 */
public record AddPortalViewInput(UUID id, String editingContextId, String representationId, String viewRepresentationId) implements IPortalInput {
}
