/*******************************************************************************
 * Copyright (c) 2021, 2025 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.diagrams.events;

/**
 * Represent an event for the diagram.
 *
 * @author fbarbin
 */
@SuppressWarnings("checkstyle:InterfaceIsType")
public interface IDiagramEvent {

    /**
     * The name of the variable used to store and retrieve the diagram event from a variable manager.
     *
     * @technical-debt This variable has probably no reason to exist since the same content can be retrieved directly
     * from IDiagramContext#getDiagramEvents.
     */
    String DIAGRAM_EVENTS = "diagramEvents";
}
