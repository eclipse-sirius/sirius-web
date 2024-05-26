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
 * <li>{@link org.eclipse.sirius.components.papaya.Class#isFinal <em>Final</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Class#isStatic <em>Static</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Class#getExtends <em>Extends</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Class#getExtendedBy <em>Extended By</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Class#getConstructors <em>Constructors</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Class#getAttributes <em>Attributes</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Class#getOperations <em>Operations</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getClass_()
 * @model
 * @generated
 */
public interface Class extends Classifier, InterfaceImplementation {
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
     * Returns the value of the '<em><b>Final</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Final</em>' attribute.
     * @see #setFinal(boolean)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getClass_Final()
     * @model
     * @generated
     */
    boolean isFinal();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Class#isFinal <em>Final</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Final</em>' attribute.
     * @see #isFinal()
     * @generated
     */
    void setFinal(boolean value);

    /**
     * Returns the value of the '<em><b>Static</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Static</em>' attribute.
     * @see #setStatic(boolean)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getClass_Static()
     * @model
     * @generated
     */
    boolean isStatic();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Class#isStatic <em>Static</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Static</em>' attribute.
     * @see #isStatic()
     * @generated
     */
    void setStatic(boolean value);

    /**
     * Returns the value of the '<em><b>Extends</b></em>' reference. It is bidirectional and its opposite is
     * '{@link org.eclipse.sirius.components.papaya.Class#getExtendedBy <em>Extended By</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Extends</em>' reference.
     * @see #setExtends(Class)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getClass_Extends()
     * @see org.eclipse.sirius.components.papaya.Class#getExtendedBy
     * @model opposite="extendedBy"
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
     * Returns the value of the '<em><b>Extended By</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Class}. It is bidirectional and its opposite is
     * '{@link org.eclipse.sirius.components.papaya.Class#getExtends <em>Extends</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Extended By</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getClass_ExtendedBy()
     * @see org.eclipse.sirius.components.papaya.Class#getExtends
     * @model opposite="extends"
     * @generated
     */
    EList<Class> getExtendedBy();

    /**
     * Returns the value of the '<em><b>Constructors</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Constructor}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Constructors</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getClass_Constructors()
     * @model containment="true"
     * @generated
     */
    EList<Constructor> getConstructors();

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
