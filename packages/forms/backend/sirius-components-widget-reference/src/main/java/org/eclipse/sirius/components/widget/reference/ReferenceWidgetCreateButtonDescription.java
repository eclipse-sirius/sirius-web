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

package org.eclipse.sirius.components.widget.reference;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Describes the presence of a clear button on a reference widget.
 *
 * @author tgiraudet
 */
public record ReferenceWidgetCreateButtonDescription(Function<VariableManager, Boolean> preconditionProvider) {

    public ReferenceWidgetCreateButtonDescription {
        Objects.requireNonNull(preconditionProvider);
    }
}
