/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsTool;

/**
 * A default tool which is executed without displaying a palette.
 *
 * @author mcharfadi
 */
public record DefaultConnectorToolResult(SingleClickOnTwoDiagramElementsTool tool) implements IConnectorToolsProviderResult {

    public DefaultConnectorToolResult {
        Objects.requireNonNull(tool);
    }
}
