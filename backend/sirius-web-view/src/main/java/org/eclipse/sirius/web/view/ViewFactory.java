/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.sirius.web.view.ViewPackage
 * @generated
 */
public interface ViewFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ViewFactory eINSTANCE = org.eclipse.sirius.web.view.impl.ViewFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>View</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>View</em>'.
	 * @generated
	 */
	View createView();

	/**
	 * Returns a new object of class '<em>Diagram Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Diagram Description</em>'.
	 * @generated
	 */
	DiagramDescription createDiagramDescription();

	/**
	 * Returns a new object of class '<em>Node Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node Description</em>'.
	 * @generated
	 */
	NodeDescription createNodeDescription();

	/**
	 * Returns a new object of class '<em>Edge Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Edge Description</em>'.
	 * @generated
	 */
	EdgeDescription createEdgeDescription();

	/**
	 * Returns a new object of class '<em>Style</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Style</em>'.
	 * @generated
	 */
	Style createStyle();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ViewPackage getViewPackage();

} //ViewFactory
