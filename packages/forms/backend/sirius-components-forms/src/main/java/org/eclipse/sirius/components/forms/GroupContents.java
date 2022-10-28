/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.forms;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * "Virtual" container used only during the rendering process to distinguish buttons from the actual contents of a
 * Group.
 *
 * @author pcdavid
 */
@Immutable
public final class GroupContents {
    public static final String TYPE = "GroupContents"; //$NON-NLS-1$

    private List<AbstractWidget> widgets;

    public GroupContents(List<AbstractWidget> widgets) {
        this.widgets = Objects.requireNonNull(widgets);
    }

    public List<AbstractWidget> getWidgets() {
        return this.widgets;
    }
}
