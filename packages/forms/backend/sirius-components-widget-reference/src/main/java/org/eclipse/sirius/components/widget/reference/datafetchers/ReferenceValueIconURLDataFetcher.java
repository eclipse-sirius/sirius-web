/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widget.reference.datafetchers;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.components.widget.reference.ReferenceValue;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for ReferenceValue.iconURL, to rewrite the relative path of the image into an absolute path on the server.
 * <p>
 * If the <code>TreeItem.imageURL</code> is of the form <code>path/to/image.svg</code>, the rewritten value which will
 * be seen by the frontend will be <code>/api/images/path/to/image.svg</code>.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "ReferenceValue", field = "iconURL")
public class ReferenceValueIconURLDataFetcher implements IDataFetcherWithFieldCoordinates<String> {
    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        ReferenceValue item = environment.getSource();
        return URLConstants.IMAGE_BASE_PATH + item.getIconURL();
    }
}
