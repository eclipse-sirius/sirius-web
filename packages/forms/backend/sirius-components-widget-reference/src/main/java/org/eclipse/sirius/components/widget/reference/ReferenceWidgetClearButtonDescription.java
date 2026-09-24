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

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Describes the presence of a clear button on a reference widget.
 *
 * @author tgiraudet
 */
@Immutable
public final class ReferenceWidgetClearButtonDescription {

    private ReferenceWidgetClearButtonDescription() {
        // Prevent instantiation
    }

    public static Builder newReferenceWidgetClearButtonDescription() {
        return new Builder();
    }

    /**
     * Builder used to create the reference widget clear button description.
     *
     * @author tgiraudet
     */
    public static final class Builder {

        private Builder() {
            // Prevent instantiation
        }

        public ReferenceWidgetClearButtonDescription build() {
            return new ReferenceWidgetClearButtonDescription();
        }
    }

}
