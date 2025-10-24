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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.annotations.PublicApi;

/**
 * Common interface used by all the properties of the components.
 *
 * <p>
 *     This interface is a Java based port of the same concept in the JavaScript framework React.
 *     It is used to provide a set of properties to {@link Element elements} whether they are regular elements or
 *     {@link IComponent components}.
 * </p>
 *
 * <p>
 *     Each component and element will have a dedicated props which will be used as its signature.
 *     Just like in React, the special props <strong>children</strong> is used to perform the composition of elements
 * </p>
 *
 * {@snippet id="props" lang="typescript":
 * const Component = ({ someProperty, children }: ComponentProps) => {
 *     return (
 *         &lt;element someOtherProperty={someProperty}&gt;
 *             {children}
 *         &lt/element&gt;
 *     );
 * }
 * }
 *
 * @author sbegaudeau
 * @since v0.1.0
 */
@PublicApi
public interface IProps {

    /**
     * Provides the way to compose elements into a tree based structure for the rendering.
     *
     * @return The child elements
     */
    default List<Element> getChildren() {
        return new ArrayList<>();
    }
}
