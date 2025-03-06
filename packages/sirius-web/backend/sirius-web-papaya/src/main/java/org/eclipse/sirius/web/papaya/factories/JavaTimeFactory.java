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
package org.eclipse.sirius.web.papaya.factories;

import java.util.List;

import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.PapayaFactory;

/**
 * Used to create the java.time package.
 *
 * @author sbegaudeau
 */
public class JavaTimeFactory {

    public Package javaTime() {
        var temporalAccessorInterface = PapayaFactory.eINSTANCE.createInterface();
        temporalAccessorInterface.setName("TemporalAccessor");

        var temporalAdjusterInterface = PapayaFactory.eINSTANCE.createInterface();
        temporalAdjusterInterface.setName("TemporalAdjuster");

        var temporalInterface = PapayaFactory.eINSTANCE.createInterface();
        temporalInterface.setName("Temporal");

        var javaTimeTemporalTypes = List.of(temporalAccessorInterface, temporalAdjusterInterface, temporalInterface);

        var javaTimeTemporal = PapayaFactory.eINSTANCE.createPackage();
        javaTimeTemporal.setName("temporal");
        javaTimeTemporal.getTypes().addAll(javaTimeTemporalTypes);


        var instantClass = PapayaFactory.eINSTANCE.createClass();
        instantClass.setName("Instant");

        var javaTime = PapayaFactory.eINSTANCE.createPackage();
        javaTime.setName("java.time");
        javaTime.getTypes().add(instantClass);
        javaTime.getPackages().add(javaTimeTemporal);

        return javaTime;
    }
}
