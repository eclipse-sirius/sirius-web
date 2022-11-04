/*******************************************************************************
 * Copyright (c) 2021, 2022 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.collaborative.diagrams;

/**
 * Description of the changes performed by diagrams event handlers.
 *
 * @author fbarbin
 */
public final class DiagramChangeKind {

    public static final String DIAGRAM_LAYOUT_CHANGE = "DIAGRAM_LAYOUT_CHANGE"; //$NON-NLS-1$

    public static final String DIAGRAM_ELEMENT_VISIBILITY_CHANGE = "DIAGRAM_ELEMENT_VISIBILITY_CHANGE"; //$NON-NLS-1$

    private DiagramChangeKind() {
        // Prevent instantiation
    }
}
