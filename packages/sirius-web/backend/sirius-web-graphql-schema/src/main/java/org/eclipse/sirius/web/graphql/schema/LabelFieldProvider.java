/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.graphql.schema;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNonNull;

/**
 * This class is used to create the definition of the label field of any kind of Object.
 * <p>
 * It will be used to add the following field to any Object:
 * </p>
 *
 * <pre>
 * label: String!
 * </pre>
 *
 * @author hmarchadour
 */
public class LabelFieldProvider {

    public static final String LABEL_FIELD = "label";

    public GraphQLFieldDefinition getField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(LABEL_FIELD)
                .type(new GraphQLNonNull(Scalars.GraphQLString))
                .build();
        // @formatter:on
    }

}
