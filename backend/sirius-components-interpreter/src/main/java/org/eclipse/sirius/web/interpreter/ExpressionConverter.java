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
package org.eclipse.sirius.web.interpreter;

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
    private static final String AQL_PREFIX = "aql:"; //$NON-NLS-1$

    /**
     * The prefix used by feature expressions.
     */
    private static final String FEATURE_PREFIX = "feature:"; //$NON-NLS-1$

    /**
     * List of "special" features that must be post-processed
     */
    private static final List<String> SPECIAL_FEATURE_NAMES = List.of("eContainer", "eContents", "eAllContents", "eClass", "eCrossReferences"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

    /**
     * The prefix used by var expressions.
     */
    private static final String VAR_PREFIX = "var:"; //$NON-NLS-1$

    /**
     * The prefix used by service expressions.
     */
    private static final String SERVICE_PREFIX = "service:"; //$NON-NLS-1$

    private static final String QUOTE = "'"; //$NON-NLS-1$

    private static final String ESCAPED_QUOTE = "\\'"; //$NON-NLS-1$

    public String convertExpression(String expressionBody) {
        String processedExpression = Optional.ofNullable(expressionBody).orElse(""); //$NON-NLS-1$

        if (processedExpression.startsWith(VAR_PREFIX)) {
            // var:variableName -> aql:variableName
            String variableName = processedExpression.substring(VAR_PREFIX.length(), processedExpression.length());
            if (variableName.matches("[0-9]+")) { //$NON-NLS-1$
                variableName = "arg" + variableName; //$NON-NLS-1$
            }
            processedExpression = AQL_PREFIX + variableName;
        } else if (processedExpression.startsWith(FEATURE_PREFIX)) {
            // feature:featureName -> aql:self.featureName
            String featureName = processedExpression.substring(FEATURE_PREFIX.length(), processedExpression.length());
            if (SPECIAL_FEATURE_NAMES.contains(featureName)) {
                // feature:featureName -> aql:self.featureName()
                featureName = featureName + "()"; //$NON-NLS-1$
            }
            processedExpression = AQL_PREFIX + "self." + featureName; //$NON-NLS-1$
        } else if (!processedExpression.startsWith(AQL_PREFIX) && !processedExpression.startsWith(SERVICE_PREFIX)) {
            // true -> aql:'true', let's go -> aql:'let\'s go'
            processedExpression = processedExpression.replace(QUOTE, ESCAPED_QUOTE);
            processedExpression = AQL_PREFIX + QUOTE + processedExpression + QUOTE;
        }

        return processedExpression;
    }

}
