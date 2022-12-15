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
package org.eclipse.sirius.components.interpreter;

import java.util.List;
import java.util.Optional;

/**
 * Used to convert expression written using Sirius specific interpreters such as var: and feature: into proper AQL
 * expressions.
 *
 * @author sbegaudeau
 */
public class ExpressionConverter {

    /**
     * The prefix used by AQL expressions.
     */
    private static final String AQL_PREFIX = "aql:";

    /**
     * The prefix used by feature expressions.
     */
    private static final String FEATURE_PREFIX = "feature:";

    /**
     * List of "special" features that must be post-processed
     */
    private static final List<String> SPECIAL_FEATURE_NAMES = List.of("eContainer", "eContents", "eAllContents", "eClass", "eCrossReferences");

    /**
     * The prefix used by var expressions.
     */
    private static final String VAR_PREFIX = "var:";

    /**
     * The prefix used by service expressions.
     */
    private static final String SERVICE_PREFIX = "service:";

    private static final String QUOTE = "'";

    private static final String ESCAPED_QUOTE = "\\'";

    public String convertExpression(String expressionBody) {
        String processedExpression = Optional.ofNullable(expressionBody).orElse("");

        if (processedExpression.startsWith(VAR_PREFIX)) {
            // var:variableName -> aql:variableName
            String variableName = processedExpression.substring(VAR_PREFIX.length(), processedExpression.length());
            if (variableName.matches("[0-9]+")) {
                variableName = "arg" + variableName;
            }
            processedExpression = AQL_PREFIX + variableName;
        } else if (processedExpression.startsWith(FEATURE_PREFIX)) {
            // feature:featureName -> aql:self.featureName
            String featureName = processedExpression.substring(FEATURE_PREFIX.length(), processedExpression.length());
            if (SPECIAL_FEATURE_NAMES.contains(featureName)) {
                // feature:featureName -> aql:self.featureName()
                featureName = featureName + "()";
            }
            processedExpression = AQL_PREFIX + "self." + featureName;
        } else if (!processedExpression.startsWith(AQL_PREFIX) && !processedExpression.startsWith(SERVICE_PREFIX)) {
            // true -> aql:'true', let's go -> aql:'let\'s go'
            processedExpression = processedExpression.replace(QUOTE, ESCAPED_QUOTE);
            processedExpression = AQL_PREFIX + QUOTE + processedExpression + QUOTE;
        }

        return processedExpression;
    }

}
