/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.graphql.datafetchers.diagram;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to concatenate the server image URL to the single click on diagram element tool image path.
 *
 * @author hmarchadour
 */
@QueryDataFetcher(type = "SingleClickOnDiagramElementTool", field = "iconURL")
public class SingleClickOnDiagramElementToolIconURLDataFetcher implements IDataFetcherWithFieldCoordinates<List<String>> {

    @Override
    public List<String> get(DataFetchingEnvironment environment) throws Exception {
        List<String> iconURL = new ArrayList<>();
        Object source = environment.getSource();
        if (source instanceof org.eclipse.sirius.components.diagrams.tools.ITool tool) {
            iconURL = tool.getIconURL();
        } else if (source instanceof ITool tool) {
            iconURL = tool.iconURL();
        }
        return iconURL.stream().map(url -> URLConstants.IMAGE_BASE_PATH + url).toList();
    }
}
