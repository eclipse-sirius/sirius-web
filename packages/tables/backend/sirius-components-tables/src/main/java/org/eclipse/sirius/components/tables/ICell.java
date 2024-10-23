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
 * Interface of all the cells of the table-based representation.
 *
 * @author arichard
 * @author lfasani
 */
public interface ICell {

    UUID getId();

    String getType();

    String getTargetObjectId();

    String getTargetObjectKind();

    UUID getColumnId();
}
