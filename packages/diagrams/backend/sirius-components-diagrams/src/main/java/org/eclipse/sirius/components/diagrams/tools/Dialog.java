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
package org.eclipse.sirius.components.diagrams.tools;

import java.util.Objects;

/**
 * Represents the Dialog type of the SingleClickOnDiagramElementTool#dialog attribute.
 *
 * @author fbarbin
 */
public record Dialog(String dialogDescriptionId, String dialogDescriptionType) {

    public Dialog {
        Objects.requireNonNull(dialogDescriptionId);
        Objects.requireNonNull(dialogDescriptionType);
    }
}
