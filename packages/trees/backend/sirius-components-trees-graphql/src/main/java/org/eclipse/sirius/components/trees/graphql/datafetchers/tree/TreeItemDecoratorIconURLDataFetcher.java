/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.trees.graphql.datafetchers.tree;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.IImageURLSanitizer;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.components.trees.TreeItemDecorator;

import graphql.schema.DataFetchingEnvironment;

/**
 * Rewrites relative tree item decorator icon URLs to paths on the server.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "TreeItemDecorator", field = "iconURL")
public class TreeItemDecoratorIconURLDataFetcher implements IDataFetcherWithFieldCoordinates<String> {

    private final IImageURLSanitizer imageURLSanitizer;

    public TreeItemDecoratorIconURLDataFetcher(IImageURLSanitizer imageURLSanitizer) {
        this.imageURLSanitizer = Objects.requireNonNull(imageURLSanitizer);
    }

    @Override
    public String get(DataFetchingEnvironment environment) {
        TreeItemDecorator decorator = environment.getSource();
        return this.imageURLSanitizer.sanitize(URLConstants.IMAGE_BASE_PATH, decorator.iconURL());
    }
}
