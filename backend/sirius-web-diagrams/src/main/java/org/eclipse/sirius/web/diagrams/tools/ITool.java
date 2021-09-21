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
package org.eclipse.sirius.web.diagrams.tools;

import java.util.function.Function;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLInterfaceType;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Interface implemented by all tools.
 *
 * @author hmarchadour
 */
@GraphQLInterfaceType(name = "Tool")
public interface ITool {

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    String getId();

    @GraphQLField
    @GraphQLNonNull
    String getLabel();

    Function<VariableManager, IStatus> getHandler();

    @GraphQLField
    @GraphQLNonNull
    String getImageURL();
}
