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
package org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility;

import java.util.Objects;

/**
 * An Action triggered by a click on a manage visibility menu action.
 *
 * @author mcharfadi
 */
public record ManageVisibilityAction(String id, String label) {
    public ManageVisibilityAction {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
    }
}
