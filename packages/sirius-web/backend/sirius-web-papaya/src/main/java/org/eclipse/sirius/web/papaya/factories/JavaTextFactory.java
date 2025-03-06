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
 * Used to create the java.text package.
 *
 * @author sbegaudeau
 */
public class JavaTextFactory {

    public Package javaText() {
        var formatClass = PapayaFactory.eINSTANCE.createClass();
        formatClass.setName("Format");
        formatClass.setAbstract(true);

        var messageFormatClass = PapayaFactory.eINSTANCE.createClass();
        messageFormatClass.setName("MessageFormat");


        var javaTextTypes = List.of(formatClass, messageFormatClass);

        var javaText = PapayaFactory.eINSTANCE.createPackage();
        javaText.setName("java.text");
        javaText.getTypes().addAll(javaTextTypes);

        return javaText;
    }
}
