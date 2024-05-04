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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Class</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Class#isAbstract <em>Abstract</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Class#getImplements <em>Implements</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Class#getExtends <em>Extends</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Class#getAttributes <em>Attributes</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Class#getOperations <em>Operations</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getClass_()
 * @model
 * @generated
 */
public interface Class extends Classifier {
    /**
     * Returns the value of the '<em><b>Abstract</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Abstract</em>' attribute.
     * @see #setAbstract(boolean)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getClass_Abstract()
     * @model
     * @generated
     */
    boolean isAbstract();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Class#isAbstract <em>Abstract</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Abstract</em>' attribute.
     * @see #isAbstract()
     * @generated
     */
    void setAbstract(boolean value);

    /**
     * Returns the value of the '<em><b>Implements</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Interface}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Implements</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getClass_Implements()
     * @model
     * @generated
     */
    EList<Interface> getImplements();

    /**
     * Returns the value of the '<em><b>Extends</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Extends</em>' reference.
     * @see #setExtends(Class)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getClass_Extends()
     * @model
     * @generated
     */
    Class getExtends();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Class#getExtends <em>Extends</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Extends</em>' reference.
     * @see #getExtends()
     * @generated
     */
    void setExtends(Class value);

    /**
     * Returns the value of the '<em><b>Attributes</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Attribute}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Attributes</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getClass_Attributes()
     * @model containment="true"
     * @generated
     */
    EList<Attribute> getAttributes();

    /**
     * Returns the value of the '<em><b>Operations</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Operations</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getClass_Operations()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getOperations();

} // Class
