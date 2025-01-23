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

import java.util.Objects;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * Represents the possible severities of a diagnostic and how it can be encoded in EMF Diagnostics and plain
 * strings.
 *
 * @author pcdavid
 */
public enum Severity {
    UNKNOWN("Unkown"), INFO("Info"), WARNING("Warning"), ERROR("Error");

    private String name;

    Severity(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public static Severity ofDiagnostic(Diagnostic diagnostic) {
        return switch (diagnostic.getSeverity()) {
            case org.eclipse.emf.common.util.Diagnostic.ERROR -> ERROR;
            case org.eclipse.emf.common.util.Diagnostic.WARNING -> WARNING;
            case org.eclipse.emf.common.util.Diagnostic.INFO -> INFO;
            default -> UNKNOWN;
        };
    }

    public static Severity ofString(String stringDiagnostic) {
        Severity result = UNKNOWN;
        String upper = stringDiagnostic.toUpperCase();
        if (upper.startsWith(ERROR.prefix())) {
            result = ERROR;
        } else if (upper.startsWith(WARNING.prefix())) {
            result = WARNING;
        } else if (upper.startsWith(INFO.prefix())) {
            result = INFO;
        }
        return result;
    }

    public String getName() {
        return this.name;
    }

    public String prefix() {
        return this.getName().toUpperCase() + ":";
    }
}