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
package org.eclipse.sirius.web.graphql.utils.types;

import graphql.schema.GraphQLScalarType;

/**
 * Custom {@link GraphQLScalarType} dedicated to Upload.
 *
 * @author SMonnier
 */
public class UploadScalarType {

    public static final String UPLOAD_TYPE = "Upload"; //$NON-NLS-1$

    // @formatter:off
    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name(UPLOAD_TYPE)
            .description("Upload type used for the file upload") //$NON-NLS-1$
            .coercing(new UploadScalarCoercing())
            .build();
    // @formatter:on
}
