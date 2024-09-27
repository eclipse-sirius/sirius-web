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
package org.eclipse.sirius.components.tables;

import java.util.UUID;

/**
 * Abstract class to be extended by all the cells of the table-based representation.
 *
 * @author arichard
 */
public abstract class AbstractCell {
    protected UUID id;

    protected UUID parentLineId;

    protected UUID columnId;

    protected String cellType;

    public UUID getId() {
        return this.id;
    }

    public UUID getParentLineId() {
        return this.parentLineId;
    }

    public UUID getColumnId() {
        return this.columnId;
    }

    public String getCellType() {
        return this.cellType;
    }
}
