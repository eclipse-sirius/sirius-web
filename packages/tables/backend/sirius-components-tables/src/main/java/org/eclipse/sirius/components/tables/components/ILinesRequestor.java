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

import org.eclipse.sirius.components.tables.Line;

import java.util.Optional;

/**
 * Used to find some information on a specific set of lines from a previous table.
 *
 * @author arichard
 */
public interface ILinesRequestor {
    Optional<Line> getByTargetObjectId(String targetObjectId);
}
