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

import org.eclipse.sirius.components.forms.Page;

/**
 * Used to navigate from a page.
 *
 * @author sbegaudeau
 */
public class PageNavigator {

    private final Page page;

    public PageNavigator(Page page) {
        this.page = Objects.requireNonNull(page);
    }

    public GroupNavigator group(String label) {
        return this.page.getGroups().stream()
                .filter(group -> group.getLabel().equals(label))
                .findFirst()
                .map(GroupNavigator::new)
                .orElseThrow(() -> new IllegalArgumentException("No group found with the given label \"" + label + "\""));
    }

    public Page getPage() {
        return this.page;
    }
}
