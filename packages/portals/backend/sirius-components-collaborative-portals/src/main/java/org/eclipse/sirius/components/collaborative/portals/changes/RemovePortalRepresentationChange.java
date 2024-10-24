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
package org.eclipse.sirius.components.collaborative.portals.changes;

import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.portals.PortalView;
import org.eclipse.sirius.components.portals.PortalViewLayoutData;

/**
 * Remove Portal representation event.
 *
 * @author mcharfadi
 */
public record RemovePortalRepresentationChange(String representationId, PortalViewLayoutData oldLayout, PortalView oldPortalView) implements IRepresentationChangeEvent {
}
