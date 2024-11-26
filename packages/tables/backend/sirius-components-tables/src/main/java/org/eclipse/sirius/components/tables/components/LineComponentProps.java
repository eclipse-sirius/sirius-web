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
package org.eclipse.sirius.components.tables.components;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.CellDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.events.ITableEvent;
import org.eclipse.sirius.components.tables.renderer.TableRenderingCache;

/**
 * The props of the line component.
 *
 * @author arichard
 */
public record LineComponentProps(VariableManager variableManager, LineDescription lineDescription, CellDescription cellDescription, ILinesRequestor linesRequestor, TableRenderingCache cache, String parentElementId, List<ITableEvent> tableEvents) implements IProps {

    public LineComponentProps {
        Objects.requireNonNull(variableManager);
        Objects.requireNonNull(lineDescription);
        Objects.requireNonNull(cellDescription);
        Objects.requireNonNull(linesRequestor);
        Objects.requireNonNull(cache);
        Objects.requireNonNull(parentElementId);
        Objects.requireNonNull(tableEvents);

    }
}
