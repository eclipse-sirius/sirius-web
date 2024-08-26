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
package org.eclipse.sirius.components.papaya.provider.spec;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.provider.StyledString;

/**
 * Used to contain the various styles of StyledString used in Papaya.
 *
 * @author sbegaudeau
 */
public final class PapayaStyledStringStyles {
    public static final StyledString.Style DECORATOR_STYLE = StyledString.Style.newBuilder()
            .setForegroundColor(URI.createURI("color://rgb/120/120/120"))
            .toStyle();

    public static final StyledString.Style TYPE_STYLE = StyledString.Style.newBuilder()
            .setForegroundColor(URI.createURI("color://rgb/40/77/181"))
            .toStyle();

    public static final StyledString.Style GENERIC_TYPE_STYLE = StyledString.Style.newBuilder()
            .setForegroundColor(URI.createURI("color://rgb/40/100/120"))
            .toStyle();

    private PapayaStyledStringStyles() {
        // Prevent instantiation
    }
}
