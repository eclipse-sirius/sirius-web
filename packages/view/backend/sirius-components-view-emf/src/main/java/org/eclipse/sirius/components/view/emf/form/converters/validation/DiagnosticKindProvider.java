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
 * Used to compute the kind of the diagnostic.
 *
 * @author sbegaudeau
 */
public class DiagnosticKindProvider implements Function<Object, String> {
    @Override
    public String apply(Object diagnostic) {
        Severity severity = Severity.UNKNOWN;
        if (diagnostic instanceof Diagnostic emfDiagnostic) {
            severity = Severity.ofDiagnostic(emfDiagnostic);
        } else if (diagnostic instanceof String stringDiagnostic) {
            severity = Severity.ofString(stringDiagnostic);
        }
        return severity.getName();
    }
}
