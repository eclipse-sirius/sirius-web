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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Component</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Component#getDependencies <em>Dependencies</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Component#getAllDependencies <em>All Dependencies</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Component#getUsedAsDependencyBy <em>Used As Dependency By</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Component#getComponents <em>Components</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Component#getAllComponents <em>All Components</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Component#getPackages <em>Packages</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Component#getPorts <em>Ports</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Component#getProvidedServices <em>Provided Services</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Component#getRequiredServices <em>Required Services</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getComponent()
 * @model
 * @generated
 */
public interface Component extends NamedElement {
    /**
     * Returns the value of the '<em><b>Dependencies</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Component}. It is bidirectional and its opposite is
     * '{@link org.eclipse.sirius.components.papaya.Component#getUsedAsDependencyBy <em>Used As Dependency By</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Dependencies</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getComponent_Dependencies()
     * @see org.eclipse.sirius.components.papaya.Component#getUsedAsDependencyBy
     * @model opposite="usedAsDependencyBy"
     * @generated
     */
    EList<Component> getDependencies();

    /**
     * Returns the value of the '<em><b>All Dependencies</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Component}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>All Dependencies</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getComponent_AllDependencies()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    EList<Component> getAllDependencies();

    /**
     * Returns the value of the '<em><b>Used As Dependency By</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Component}. It is bidirectional and its opposite is
     * '{@link org.eclipse.sirius.components.papaya.Component#getDependencies <em>Dependencies</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Used As Dependency By</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getComponent_UsedAsDependencyBy()
     * @see org.eclipse.sirius.components.papaya.Component#getDependencies
     * @model opposite="dependencies"
     * @generated
     */
    EList<Component> getUsedAsDependencyBy();

    /**
     * Returns the value of the '<em><b>Components</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Component}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Components</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getComponent_Components()
     * @model containment="true"
     * @generated
     */
    EList<Component> getComponents();

    /**
     * Returns the value of the '<em><b>All Components</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Component}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>All Components</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getComponent_AllComponents()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    EList<Component> getAllComponents();

    /**
     * Returns the value of the '<em><b>Packages</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Package}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Packages</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getComponent_Packages()
     * @model containment="true"
     * @generated
     */
    EList<org.eclipse.sirius.components.papaya.Package> getPackages();

    /**
     * Returns the value of the '<em><b>Ports</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.ComponentPort}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Ports</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getComponent_Ports()
     * @model containment="true"
     * @generated
     */
    EList<ComponentPort> getPorts();

    /**
     * Returns the value of the '<em><b>Provided Services</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.papaya.ProvidedService}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Provided Services</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getComponent_ProvidedServices()
     * @model containment="true"
     * @generated
     */
    EList<ProvidedService> getProvidedServices();

    /**
     * Returns the value of the '<em><b>Required Services</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.papaya.RequiredService}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Required Services</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getComponent_RequiredServices()
     * @model containment="true"
     * @generated
     */
    EList<RequiredService> getRequiredServices();

} // Component
