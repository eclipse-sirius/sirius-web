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
package org.eclipse.sirius.components.collaborative.diagrams.changes;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;

/**
 * Diagram view delete change.
 *
 * @author mcharfadi
 */
public record ViewDeletionRequestChange(String representationId, ViewDeletionRequest viewDeletionRequest, Node deletedNode, Optional<Node> parentNode) implements IRepresentationChangeEvent {
}
