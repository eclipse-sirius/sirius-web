/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import graphql.schema.DataFetchingEnvironment;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ConnectorTool;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;

import java.util.List;

/**
 * DataFetcher for the field iconURL on the type ConnectorTool.
 *
 * @author mcharfadi
 */
@QueryDataFetcher(type = "ConnectorTool", field = "iconURL")
public class ConnectorToolsIconURLsDataFetcher implements IDataFetcherWithFieldCoordinates<List<String>> {

    @Override
    public List<String> get(DataFetchingEnvironment environment) throws Exception {
        ConnectorTool tool = environment.getSource();
        return tool.iconURL().stream().map(url -> URLConstants.IMAGE_BASE_PATH + url).toList();
    }
}
