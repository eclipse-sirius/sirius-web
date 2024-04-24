/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

    private static final String IMAGES_ROOT_FOLDER = "/diagram-images";

    public static final String EDIT_SVG = IMAGES_ROOT_FOLDER + "/edit.svg";

    public static final String COLLAPSE_SVG = IMAGES_ROOT_FOLDER + "/collapse.svg";

    public static final String EXPAND_SVG = IMAGES_ROOT_FOLDER + "/expand.svg";

    public static final String SEMANTIC_DELETE_SVG = IMAGES_ROOT_FOLDER + "/semanticDelete.svg";

    public static final String GRAPHICAL_DELETE_SVG = IMAGES_ROOT_FOLDER + "/graphicalDelete.svg";

    public static final String PIN_SVG = IMAGES_ROOT_FOLDER + "/pin.svg";

    public static final String UNPIN_SVG = IMAGES_ROOT_FOLDER + "/unpin.svg";

    public static final String FADE_SVG = IMAGES_ROOT_FOLDER + "/fade.svg";

    public static final String REVEAL_FADED_ELEMENTS_SVG = IMAGES_ROOT_FOLDER + "/reveal-faded-elements.svg";

    private DiagramImageConstants() {
        // Prevent instantiation
    }
}
