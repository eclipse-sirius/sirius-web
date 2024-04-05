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
package org.eclipse.sirius.components.forms.tests.navigation;

import java.util.Objects;

import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Group;

/**
 * Used to navigate from a group.
 *
 * @author sbegaudeau
 */
public class GroupNavigator {

    private final Group group;

    public GroupNavigator(Group group) {
        this.group = Objects.requireNonNull(group);
    }

    public <T extends AbstractWidget> T findWidget(String label, Class<T> clazz) {
        return this.group.getWidgets().stream()
                .filter(widget -> widget.getLabel().equals(label))
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No widget found with the given label \"" + label + "\" and the given type \"" + clazz.getSimpleName() + "\""));
    }

    public Group getGroup() {
        return this.group;
    }
}
