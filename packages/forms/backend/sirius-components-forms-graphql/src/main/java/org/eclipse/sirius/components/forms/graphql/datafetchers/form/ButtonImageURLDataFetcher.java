/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.forms.graphql.datafetchers.form;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.forms.Button;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for Button.imageURL, to rewrite the relative path of the image into an absolute path on the server.
 * <p>
 * If the <code>Button.imageURL</code> is of the form <code>path/to/image.svg</code>, the rewritten value which will be
 * seen by the frontend will be <code>/api/images/path/to/image.svg</code>.
 *
 * @author arichard
 */
@QueryDataFetcher(type = "Button", field = "imageURL")
public class ButtonImageURLDataFetcher implements IDataFetcherWithFieldCoordinates<String> {
    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        Button item = environment.getSource();
        return URLConstants.IMAGE_BASE_PATH + item.getImageURL();
    }
}
