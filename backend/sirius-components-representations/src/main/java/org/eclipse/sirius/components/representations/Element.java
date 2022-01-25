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
package org.eclipse.sirius.components.representations;

import java.util.Objects;

/**
 * The building block of the virtual data structure used to create the representations.
 *
 * @author sbegaudeau
 */
public class Element {
    private Object type;

    private IProps props;

    public Element(Object type, IProps props) {
        this.type = Objects.requireNonNull(type);
        this.props = Objects.requireNonNull(props);
    }

    public Object getType() {
        return this.type;
    }

    public IProps getProps() {
        return this.props;
    }
}
