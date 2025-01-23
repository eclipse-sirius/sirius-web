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
package org.eclipse.sirius.components.view.emf.form.converters.validation;

import java.util.function.Function;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * Used to compute the message of the diagnostic.
 *
 * @author sbegaudeau
 */
public class DiagnosticMessageProvider implements Function<Object, String> {
    @Override
    public String apply(Object diagnostic) {
        String result = "";
        if (diagnostic instanceof Diagnostic emfDiagnostic) {
            result = emfDiagnostic.getMessage();
        } else if (diagnostic instanceof String stringDiagnostic) {
            result = stringDiagnostic;
            String upper = stringDiagnostic.toUpperCase();
            for (Severity severity : Severity.values()) {
                if (upper.startsWith(severity.prefix())) {
                    result = stringDiagnostic.substring(severity.prefix().length()).trim();
                    break;
                }
            }
        }
        return result;
    }
}
