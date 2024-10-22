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
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;
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

    private final List<ICellDescription> cellDescriptions;

    private final ILinesRequestor linesRequestor;

    private final TableRenderingCache cache;

    private final String parentElementId;

    public LineComponentProps(VariableManager variableManager, LineDescription lineDescription, List<ICellDescription> cellDescriptions, ILinesRequestor linesRequestor, TableRenderingCache cache, String parentElementId) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.lineDescription = Objects.requireNonNull(lineDescription);
        this.cellDescriptions = Objects.requireNonNull(cellDescriptions);
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

    public List<ICellDescription> getCellDescriptions() {
        return this.cellDescriptions;
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
