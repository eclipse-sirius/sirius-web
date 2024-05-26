/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.papaya;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage
 * @generated
 */
public interface PapayaFactory extends EFactory {
    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    PapayaFactory eINSTANCE = org.eclipse.sirius.components.papaya.impl.PapayaFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Tag</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Tag</em>'.
     * @generated
     */
    Tag createTag();

    /**
     * Returns a new object of class '<em>Project</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Project</em>'.
     * @generated
     */
    Project createProject();

    /**
     * Returns a new object of class '<em>Iteration</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Iteration</em>'.
     * @generated
     */
    Iteration createIteration();

    /**
     * Returns a new object of class '<em>Task</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Task</em>'.
     * @generated
     */
    Task createTask();

    /**
     * Returns a new object of class '<em>Contribution</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Contribution</em>'.
     * @generated
     */
    Contribution createContribution();

    /**
     * Returns a new object of class '<em>Component</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Component</em>'.
     * @generated
     */
    Component createComponent();

    /**
     * Returns a new object of class '<em>Component Port</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Component Port</em>'.
     * @generated
     */
    ComponentPort createComponentPort();

    /**
     * Returns a new object of class '<em>Component Exchange</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Component Exchange</em>'.
     * @generated
     */
    ComponentExchange createComponentExchange();

    /**
     * Returns a new object of class '<em>Provided Service</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Provided Service</em>'.
     * @generated
     */
    ProvidedService createProvidedService();

    /**
     * Returns a new object of class '<em>Required Service</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Required Service</em>'.
     * @generated
     */
    RequiredService createRequiredService();

    /**
     * Returns a new object of class '<em>Package</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Package</em>'.
     * @generated
     */
    Package createPackage();

    /**
     * Returns a new object of class '<em>Generic Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Generic Type</em>'.
     * @generated
     */
    GenericType createGenericType();

    /**
     * Returns a new object of class '<em>Annotation</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Annotation</em>'.
     * @generated
     */
    Annotation createAnnotation();

    /**
     * Returns a new object of class '<em>Annotation Field</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Annotation Field</em>'.
     * @generated
     */
    AnnotationField createAnnotationField();

    /**
     * Returns a new object of class '<em>Type Parameter</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Type Parameter</em>'.
     * @generated
     */
    TypeParameter createTypeParameter();

    /**
     * Returns a new object of class '<em>Interface</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Interface</em>'.
     * @generated
     */
    Interface createInterface();

    /**
     * Returns a new object of class '<em>Class</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Class</em>'.
     * @generated
     */
    Class createClass();

    /**
     * Returns a new object of class '<em>Constructor</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Constructor</em>'.
     * @generated
     */
    Constructor createConstructor();

    /**
     * Returns a new object of class '<em>Attribute</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Attribute</em>'.
     * @generated
     */
    Attribute createAttribute();

    /**
     * Returns a new object of class '<em>Operation</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Operation</em>'.
     * @generated
     */
    Operation createOperation();

    /**
     * Returns a new object of class '<em>Parameter</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Parameter</em>'.
     * @generated
     */
    Parameter createParameter();

    /**
     * Returns a new object of class '<em>Record</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Record</em>'.
     * @generated
     */
    Record createRecord();

    /**
     * Returns a new object of class '<em>Record Component</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Record Component</em>'.
     * @generated
     */
    RecordComponent createRecordComponent();

    /**
     * Returns a new object of class '<em>Data Type</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Data Type</em>'.
     * @generated
     */
    DataType createDataType();

    /**
     * Returns a new object of class '<em>Enum</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Enum</em>'.
     * @generated
     */
    Enum createEnum();

    /**
     * Returns a new object of class '<em>Enum Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Enum Literal</em>'.
     * @generated
     */
    EnumLiteral createEnumLiteral();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    PapayaPackage getPapayaPackage();

} // PapayaFactory
