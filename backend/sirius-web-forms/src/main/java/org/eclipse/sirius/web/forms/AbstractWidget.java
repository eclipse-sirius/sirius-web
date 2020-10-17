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
package org.eclipse.sirius.web.forms;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInterfaceType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;

/**
 * Abstract class to be extended by all the widgets of the form-based representation.
 *
 * @author sbegaudeau
 */
@GraphQLInterfaceType(name = "Widget")
public abstract class AbstractWidget {
    protected String id;

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getId() {
        return this.id;
    }
}
