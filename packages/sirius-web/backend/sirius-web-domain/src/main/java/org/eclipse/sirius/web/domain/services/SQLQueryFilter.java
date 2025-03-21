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
package org.eclipse.sirius.web.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.sirius.web.domain.services.api.IQueryFilter;
import org.springframework.stereotype.Service;

/**
 * Replaces statement in a SQL Query to filter the query result based on different operation.
 * <p>
 *     Statement being replaced should have the following format : `#[attribute]`. [attribute] should be an attribute of an aggregate.
 *     It will be replaced by `AND #[context].#[attribute] #[operation] #[conditionValue]`. [context] is the SQL table name or an alias used in the query.
 * </p>
 * Supported operation are:
 * <ul>
 *     <li>contains: Will use `LIKE '%[value]%'`, with [value], the value provided to the filter</li>
 *     <li>equals: Will use `= '[value]'`, with [value], the value provided to the filter</li>
 * </ul>
 * @author gcoutable
 */
@Service
public class SQLQueryFilter implements IQueryFilter {

    private static final String AND_REPLACE_STATEMENT = "AND #[context].#[attribute] #[operation] #[conditionValue]";

    private static final Map<String, String> FILTER_OPERATION_TO_SQL_OPERATION = Map.of("contains", "LIKE", "equals", "=");

    private static final Map<String, String> FILTER_OPERATION_TO_SQL_VALUE = Map.of("contains", "'%#[value]%'", "equals", "'#[value]'");


    @Override
    public String filterQuery(String query, String context, List<String> attributes, Map<String, Object> filter) {
        String updatedQuery = query;
        List<String> filterStatements = new ArrayList<>();
        for (String attribute: attributes) {
            if (filter.containsKey(attribute)) {
                var filterOperation = filter.get(attribute);
                if (filterOperation instanceof Map<?, ?> filterOperationMap) {
                    filterOperationMap.forEach((operation, value) ->  {
                        String projectFilterStatement = AND_REPLACE_STATEMENT
                                .replace("#[context]", context)
                                .replace("#[attribute]", attribute)
                                .replace("#[operation]", FILTER_OPERATION_TO_SQL_OPERATION.get(operation))
                                .replace("#[conditionValue]", FILTER_OPERATION_TO_SQL_VALUE.get(operation))
                                .replace("#[value]", value.toString());
                        filterStatements.add(projectFilterStatement);
                    });
                }
                updatedQuery = updatedQuery.replace("#[" + attribute + "]", String.join("\\n", filterStatements));
            } else {
                updatedQuery = updatedQuery.replace("#[" + attribute + "]", "");
            }
        }
        return updatedQuery;
    }
}
