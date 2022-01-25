/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.components.forms.components;

/**
 * The counter used to compute id in {@link GroupComponent}.
 *
 * <p>
 * This class should be used to add a integer at the end of widget ids to make widget ids inside a {@link FormComponent}
 * unique.
 * </p>
 * DISCLAIMER: This is a quick solution to duplicate id in {@link FormComponent} and meant to be improved later. This
 * solution might cause issues in Front-end if:
 * <ul>
 * <li>There are two users working on the same project</li>
 * <li>The first user focus one element in the a properties view</li>
 * <li>The second user do something the refresh the properties view for both users, the refresh removes the element the
 * first user is focusing</li>
 * <li>After the front-end has refreshed, the first user might have his focus on an other element, which is an
 * issue.</li>
 * </ul>
 *
 * @author gcoutable
 */
public final class WidgetIdCounter {
    private int counter;

    public int getCounter() {
        return this.counter;
    }

    public void increment() {
        this.counter++;
    }
}
