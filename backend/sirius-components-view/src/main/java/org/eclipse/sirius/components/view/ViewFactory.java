/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.view;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.ViewPackage
 * @generated
 */
public interface ViewFactory extends EFactory {
    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    ViewFactory eINSTANCE = org.eclipse.sirius.components.view.impl.ViewFactoryImpl.init();

    /**
     * Returns a new object of class '<em>View</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>View</em>'.
     * @generated
     */
    View createView();

    /**
     * Returns a new object of class '<em>Diagram Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Diagram Description</em>'.
     * @generated
     */
    DiagramDescription createDiagramDescription();

    /**
     * Returns a new object of class '<em>Node Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Node Description</em>'.
     * @generated
     */
    NodeDescription createNodeDescription();

    /**
     * Returns a new object of class '<em>Edge Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Edge Description</em>'.
     * @generated
     */
    EdgeDescription createEdgeDescription();

    /**
     * Returns a new object of class '<em>Node Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Node Style</em>'.
     * @generated
     */
    NodeStyle createNodeStyle();

    /**
     * Returns a new object of class '<em>Edge Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Edge Style</em>'.
     * @generated
     */
    EdgeStyle createEdgeStyle();

    /**
     * Returns a new object of class '<em>Label Edit Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Label Edit Tool</em>'.
     * @generated
     */
    LabelEditTool createLabelEditTool();

    /**
     * Returns a new object of class '<em>Delete Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Delete Tool</em>'.
     * @generated
     */
    DeleteTool createDeleteTool();

    /**
     * Returns a new object of class '<em>Node Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Node Tool</em>'.
     * @generated
     */
    NodeTool createNodeTool();

    /**
     * Returns a new object of class '<em>Edge Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Edge Tool</em>'.
     * @generated
     */
    EdgeTool createEdgeTool();

    /**
     * Returns a new object of class '<em>Drop Tool</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Drop Tool</em>'.
     * @generated
     */
    DropTool createDropTool();

    /**
     * Returns a new object of class '<em>Change Context</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Change Context</em>'.
     * @generated
     */
    ChangeContext createChangeContext();

    /**
     * Returns a new object of class '<em>Create Instance</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Create Instance</em>'.
     * @generated
     */
    CreateInstance createCreateInstance();

    /**
     * Returns a new object of class '<em>Set Value</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Set Value</em>'.
     * @generated
     */
    SetValue createSetValue();

    /**
     * Returns a new object of class '<em>Unset Value</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Unset Value</em>'.
     * @generated
     */
    UnsetValue createUnsetValue();

    /**
     * Returns a new object of class '<em>Delete Element</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Delete Element</em>'.
     * @generated
     */
    DeleteElement createDeleteElement();

    /**
     * Returns a new object of class '<em>Create View</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Create View</em>'.
     * @generated
     */
    CreateView createCreateView();

    /**
     * Returns a new object of class '<em>Delete View</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Delete View</em>'.
     * @generated
     */
    DeleteView createDeleteView();

    /**
     * Returns a new object of class '<em>Conditional Node Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Node Style</em>'.
     * @generated
     */
    ConditionalNodeStyle createConditionalNodeStyle();

    /**
     * Returns a new object of class '<em>Conditional Edge Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Edge Style</em>'.
     * @generated
     */
    ConditionalEdgeStyle createConditionalEdgeStyle();

    /**
     * Returns a new object of class '<em>Form Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Form Description</em>'.
     * @generated
     */
    FormDescription createFormDescription();

    /**
     * Returns a new object of class '<em>Textfield Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Textfield Description</em>'.
     * @generated
     */
    TextfieldDescription createTextfieldDescription();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    ViewPackage getViewPackage();

} // ViewFactory
