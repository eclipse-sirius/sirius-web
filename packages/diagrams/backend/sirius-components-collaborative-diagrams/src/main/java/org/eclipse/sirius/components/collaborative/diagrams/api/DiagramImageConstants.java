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
package org.eclipse.sirius.components.collaborative.diagrams.api;

/**
 * Used to contain the paths of the diagram images.
 *
 * @author sbegaudeau
 */
public final class DiagramImageConstants {

    private static final String IMAGES_ROOT_FOLDER = "/diagram-images"; //$NON-NLS-1$

    public static final String EDIT_SVG = IMAGES_ROOT_FOLDER + "/edit.svg"; //$NON-NLS-1$

    public static final String SEMANTIC_DELETE_SVG = IMAGES_ROOT_FOLDER + "/semanticDelete.svg"; //$NON-NLS-1$

    public static final String GRAPHICAL_DELETE_SVG = IMAGES_ROOT_FOLDER + "/graphicalDelete.svg"; //$NON-NLS-1$

    private DiagramImageConstants() {
        // Prevent instantiation
    }
}
