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

package org.eclipse.sirius.components.gantt;

import java.util.List;
import java.util.Objects;

/**
 * Concept of non-working days. Either holidays or weekend.
 *
 * @author ncouvert
 */

public record NonWorkingDays(List<String> holidays, List<String> weekends) {

    public NonWorkingDays {
        Objects.requireNonNull(holidays);
        Objects.requireNonNull(weekends);
    }
}
