/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.representations;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The status to return when the representation description handler success.
 *
 * @author gcoutable
 */
public class Success implements IStatus {

    public static final String NEW_SELECTION = "newSelection"; //$NON-NLS-1$

    private final Map<String, Object> parameters;

    private final String changeKind;

    public Success() {
        this("", new HashMap<>()); //$NON-NLS-1$
    }

    public Success(String changeKind, Map<String, Object> parameters) {
        this.changeKind = Objects.requireNonNull(changeKind);
        this.parameters = Objects.requireNonNull(parameters);
    }

    public String getChangeKind() {
        return this.changeKind;
    }

    public Map<String, Object> getParameters() {
        return this.parameters;
    }

}
