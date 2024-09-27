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

import java.util.Objects;

import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.CellDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.renderer.TableRenderingCache;

/**
 * The props of the line component.
 *
 * @author arichard
 */
public class LineComponentProps implements IProps {

    private final VariableManager variableManager;

    private final LineDescription lineDescription;

    private final CellDescription cellDescription;

    private ILinesRequestor linesRequestor;

    private TableRenderingCache cache;

    private final String parentElementId;

    public LineComponentProps(VariableManager variableManager, LineDescription lineDescription, CellDescription cellDescription, ILinesRequestor linesRequestor, TableRenderingCache cache, String parentElementId) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.lineDescription = Objects.requireNonNull(lineDescription);
        this.cellDescription = Objects.requireNonNull(cellDescription);
        this.linesRequestor = Objects.requireNonNull(linesRequestor);
        this.cache = Objects.requireNonNull(cache);
        this.parentElementId = Objects.requireNonNull(parentElementId);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public LineDescription getLineDescription() {
        return this.lineDescription;
    }

    public CellDescription getCellDescription() {
        return this.cellDescription;
    }

    public ILinesRequestor getLinesRequestor() {
        return this.linesRequestor;
    }

    public TableRenderingCache getCache() {
        return this.cache;
    }

    public String getParentElementId() {
        return this.parentElementId;
    }
}
