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

import java.util.Objects;

import org.eclipse.sirius.components.annotations.PublicApi;

/**
 * The building block of the virtual data structure used to create the representations.
 *
 * <p>
 *     Elements are directly inspired by the element API from the JavaScript framework React.
 *     They provide a generic building block to build an immutable tree based data structure.
 * </p>
 *
 * @author sbegaudeau
 * @since v0.1.0
 */
@PublicApi
public class Element {
    private Object type;

    private IProps props;

    public Element(Object type, IProps props) {
        this.type = Objects.requireNonNull(type);
        this.props = Objects.requireNonNull(props);
    }

    /**
     * Provides the type of element which will be rendered.
     *
     * <p>
     *     This type can be a string to indicate that the element is static and it will be used as a building block the
     *     of the data structure of the representation.
     *     It can also be a {@link Class} to indicate that the element is dynamic (i.e. a {@link IComponent component})
     *     that will need to be evaluated to find out if the renderer needs to consider new element to render
     * </p>
     *
     * @return The type of element to be rendered
     */
    public Object getType() {
        return this.type;
    }

    /**
     * Provides the props of the element which will be used to render it.
     *
     * <p>
     *     In the case of a static element, those props will be used to build the data structure of the representation.
     *     On the other hand, if the element is dynamic (i.e. a {@link IComponent component}), the props will be given to
     *     the component to compute new elements.
     * </p>
     *
     * @return The props of the element
     */
    public IProps getProps() {
        return this.props;
    }
}
