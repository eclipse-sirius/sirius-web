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
 * Used to create the java.io package.
 *
 * @author sbegaudeau
 */
public class JavaIoFactory {

    public Package javaIo() {
        var serializableInterface = PapayaFactory.eINSTANCE.createInterface();
        serializableInterface.setName("Serializable");

        var closeableInterface = PapayaFactory.eINSTANCE.createInterface();
        closeableInterface.setName("Closeable");

        var flushableInterface = PapayaFactory.eINSTANCE.createInterface();
        flushableInterface.setName("Flushable");

        var inputStreamClass = PapayaFactory.eINSTANCE.createClass();
        inputStreamClass.setName("InputStream");
        inputStreamClass.setAbstract(true);

        var outputStreamClass = PapayaFactory.eINSTANCE.createClass();
        outputStreamClass.setName("OutputStream");
        outputStreamClass.setAbstract(true);

        var byteArrayInputStreamClass = PapayaFactory.eINSTANCE.createClass();
        byteArrayInputStreamClass.setName("ByteArrayInputStream");

        var byteArrayOutputStreamClass = PapayaFactory.eINSTANCE.createClass();
        byteArrayOutputStreamClass.setName("ByteArrayOutputStream");

        var javaIoTypes = List.of(serializableInterface, closeableInterface, flushableInterface, inputStreamClass, outputStreamClass, byteArrayInputStreamClass, byteArrayOutputStreamClass);

        var javaIo = PapayaFactory.eINSTANCE.createPackage();
        javaIo.setName("java.io");
        javaIo.getTypes().addAll(javaIoTypes);

        return javaIo;
    }
}
