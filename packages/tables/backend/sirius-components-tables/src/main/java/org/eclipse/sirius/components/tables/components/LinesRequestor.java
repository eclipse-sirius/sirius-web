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
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.tables.Line;

/**
 * Used to perform requests on some cached lines from the previous table.
 *
 * @author arichard
 */
public class LinesRequestor implements ILinesRequestor {

    private final Map<String, Line> targetObjectId2Lines;

    public LinesRequestor(List<Line> previousLines) {
        this.targetObjectId2Lines = previousLines.stream()
                .collect(Collectors.toMap(Line::getTargetObjectId, Function.identity()));
    }

    @Override
    public Optional<Line> getByTargetObjectId(String targetObjectId) {
        return Optional.ofNullable(this.targetObjectId2Lines.get(targetObjectId));
    }

}
