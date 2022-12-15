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
package org.eclipse.sirius.components.interpreter;

import org.eclipse.emf.ecore.EObject;

/**
 * Service class to check the AQL interpreter can invoke registered Java services.
 *
 * @author pcdavid
 */
public class TestServices {
    private final String creationMessage;

    public TestServices() {
        this.creationMessage = "none";
    }

    public TestServices(String creationMessage) {
        this.creationMessage = creationMessage;
    }

    public String getCreationMessage(EObject self) {
        return this.creationMessage;
    }

}
