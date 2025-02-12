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
import org.eclipse.sirius.components.papaya.Type;

/**
 * Used to create the java.lang package.
 *
 * @author sbegaudeau
 */
public class JavaLangFactory {

    public Package javaLang() {
        var javaLang = PapayaFactory.eINSTANCE.createPackage();
        javaLang.setName("java.lang");
        javaLang.getTypes().addAll(this.javaLangPrimitiveTypes());
        javaLang.getTypes().addAll(this.javaLangObjectTypes());

        return javaLang;
    }

    private List<Type> javaLangPrimitiveTypes() {
        var voidDataType = PapayaFactory.eINSTANCE.createDataType();
        voidDataType.setName("void");

        var byteDataType = PapayaFactory.eINSTANCE.createDataType();
        byteDataType.setName("byte");

        var shortDataType = PapayaFactory.eINSTANCE.createDataType();
        shortDataType.setName("short");

        var intDataType = PapayaFactory.eINSTANCE.createDataType();
        intDataType.setName("int");

        var longDataType = PapayaFactory.eINSTANCE.createDataType();
        longDataType.setName("long");

        var floatDataType = PapayaFactory.eINSTANCE.createDataType();
        floatDataType.setName("float");

        var doubleDataType = PapayaFactory.eINSTANCE.createDataType();
        doubleDataType.setName("double");

        var booleanDataType = PapayaFactory.eINSTANCE.createDataType();
        booleanDataType.setName("boolean");

        var charDataType = PapayaFactory.eINSTANCE.createDataType();
        charDataType.setName("char");

        return List.of(voidDataType, byteDataType, shortDataType, intDataType, longDataType, floatDataType, doubleDataType, booleanDataType, charDataType);
    }

    private List<Type> javaLangObjectTypes() {
        var objectClass = PapayaFactory.eINSTANCE.createClass();
        objectClass.setName("Object");

        var stringClass = PapayaFactory.eINSTANCE.createClass();
        stringClass.setName("String");

        var voidClass = PapayaFactory.eINSTANCE.createClass();
        voidClass.setName("Void");

        var byteClass = PapayaFactory.eINSTANCE.createClass();
        byteClass.setName("Byte");

        var shortClass = PapayaFactory.eINSTANCE.createClass();
        shortClass.setName("Short");

        var integerClass = PapayaFactory.eINSTANCE.createClass();
        integerClass.setName("Integer");

        var longClass = PapayaFactory.eINSTANCE.createClass();
        longClass.setName("Long");

        var floatClass = PapayaFactory.eINSTANCE.createClass();
        floatClass.setName("Float");

        var doubleClass = PapayaFactory.eINSTANCE.createClass();
        doubleClass.setName("Double");

        var booleanClass = PapayaFactory.eINSTANCE.createClass();
        booleanClass.setName("Boolean");

        var characterClass = PapayaFactory.eINSTANCE.createClass();
        characterClass.setName("Character");

        var autoCloseableInterface = PapayaFactory.eINSTANCE.createInterface();
        autoCloseableInterface.setName("AutoCloseable");

        var cloneableInterface = PapayaFactory.eINSTANCE.createInterface();
        cloneableInterface.setName("Cloneable");

        var comparableTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        comparableTTypeParameter.setName("T");
        var comparableInterface = PapayaFactory.eINSTANCE.createInterface();
        comparableInterface.setName("Comparable");
        comparableInterface.getTypeParameters().add(comparableTTypeParameter);

        var iterableTTypeParameter = PapayaFactory.eINSTANCE.createTypeParameter();
        iterableTTypeParameter.setName("T");
        var iterableInterface = PapayaFactory.eINSTANCE.createInterface();
        iterableInterface.setName("Iterable");
        iterableInterface.getTypeParameters().add(iterableTTypeParameter);

        return List.of(
                objectClass,
                stringClass,
                voidClass,
                byteClass,
                shortClass,
                integerClass,
                longClass,
                floatClass,
                doubleClass,
                booleanClass,
                characterClass,
                autoCloseableInterface,
                cloneableInterface,
                comparableInterface,
                iterableInterface
        );
    }
}
