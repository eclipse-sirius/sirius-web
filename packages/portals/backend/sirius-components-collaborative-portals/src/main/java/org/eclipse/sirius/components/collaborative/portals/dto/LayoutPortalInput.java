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

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.portals.api.IPortalInput;

/**
 * The input for the Portal mutation to update the views layout.
 *
 * @author pcdavid
 */
public record LayoutPortalInput(UUID id, String editingContextId, String representationId, List<PortalViewLayoutDataInput> layoutData) implements IPortalInput {
}
