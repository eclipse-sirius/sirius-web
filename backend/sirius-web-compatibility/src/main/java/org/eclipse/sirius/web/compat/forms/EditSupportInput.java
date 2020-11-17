/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.web.compat.forms;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;

/**
 * Wrapper for a semantic element to call {@link EditSupportServices} in a type-safe manner.
 *
 * @author pcdavid
 */
public class EditSupportInput {
    private final EObject target;

    public EditSupportInput(EObject target) {
        this.target = Objects.requireNonNull(target);
    }

    public EObject getTarget() {
        return this.target;
    }
}
