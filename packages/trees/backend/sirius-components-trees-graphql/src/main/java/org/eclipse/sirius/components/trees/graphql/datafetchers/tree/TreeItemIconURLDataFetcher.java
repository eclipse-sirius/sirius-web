/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.IImageURLSanitizer;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.components.trees.TreeItem;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for TreeItem.iconURL, to rewrite the relative path of the image into an absolute path on the server.
 * <p>
 * If the <code>TreeItem.iconURL</code> is of the form <code>path/to/image.svg</code>, the rewritten value which will
 * be seen by the frontend will be <code>/api/images/path/to/image.svg</code>.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "TreeItem", field = "iconURL")
public class TreeItemIconURLDataFetcher implements IDataFetcherWithFieldCoordinates<List<String>> {

    private final IImageURLSanitizer imageURLSanitizer;

    public TreeItemIconURLDataFetcher(IImageURLSanitizer imageURLSanitizer) {
        this.imageURLSanitizer = Objects.requireNonNull(imageURLSanitizer);
    }

    @Override
    public List<String> get(DataFetchingEnvironment environment) throws Exception {
        TreeItem item = environment.getSource();
        return item.getIconURL().stream()
                .map(url -> this.imageURLSanitizer.sanitize(URLConstants.IMAGE_BASE_PATH, url))
                .toList();
    }
}
