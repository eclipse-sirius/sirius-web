/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.representations;

import org.eclipse.sirius.components.annotations.PublicApi;

/**
 * Common interface of all the components.
 *
 * <p>
 *     This concept is directly inspired by the JavaScript framework React.
 *     Components are a type of element used to compute dynamically new elements to render.
 *     They are used to parameterize the rendering with some business logic.
 *     For that, they will receive some {@link IProps props} which will contain the data and behavior used to configure
 *     the rendering.
 * </p>
 *
 * @author sbegaudeau
 * @since v0.1.0
 */
@PublicApi
public interface IComponent {
    /**
     * Used to compute dynamically new elements to be rendered for the representation.
     *
     * <p>
     *     Just like in React, a component can return only one top level element.
     *     In order to return multiple elements at once from a component, a {@link Fragment fragment} can be used to
     *     encapsulate multiple elements at once.
     * </p>
     *
     * @return The element computed by the component
     */
    Element render();
}
