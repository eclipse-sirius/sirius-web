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
package org.eclipse.sirius.web.services.query;

import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.PapayaFactory;

/**
 * Custom services to manipulate Papaya objects.
 *
 * @author sbegaudeau
 */
public class PapayaQueryServices {

    public void newPackage(Component component, String name) {
        var papayaPackage = PapayaFactory.eINSTANCE.createPackage();
        papayaPackage.setName(name);
        component.getPackages().add(papayaPackage);
    }
}
