/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
package org.eclipse.sirius.components.view.diagram;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Selection Dialog Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionDialogTreeDescription
 * <em>Selection Dialog Tree Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#isMultiple <em>Multiple</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#isOptional <em>Optional</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getDefaultTitleExpression
 * <em>Default Title Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getNoSelectionTitleExpression <em>No
 * Selection Title Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getWithSelectionTitleExpression
 * <em>With Selection Title Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getDescriptionExpression
 * <em>Description Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getNoSelectionActionLabelExpression
 * <em>No Selection Action Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getNoSelectionActionDescriptionExpression
 * <em>No Selection Action Description Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getWithSelectionActionLabelExpression
 * <em>With Selection Action Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getWithSelectionActionDescriptionExpression
 * <em>With Selection Action Description Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getNoSelectionActionStatusMessageExpression
 * <em>No Selection Action Status Message Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionRequiredWithoutSelectionStatusMessageExpression
 * <em>Selection Required Without Selection Status Message Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionRequiredWithSelectionStatusMessageExpression
 * <em>Selection Required With Selection Status Message Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getNoSelectionConfirmButtonLabelExpression
 * <em>No Selection Confirm Button Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression
 * <em>Selection Required Without Selection Confirm Button Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionRequiredWithSelectionConfirmButtonLabelExpression
 * <em>Selection Required With Selection Confirm Button Label Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription()
 * @model
 * @generated
 */
public interface SelectionDialogDescription extends DialogDescription {
    /**
     * Returns the value of the '<em><b>Selection Dialog Tree Description</b></em>' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Selection Dialog Tree Description</em>' containment reference.
     * @see #setSelectionDialogTreeDescription(SelectionDialogTreeDescription)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_SelectionDialogTreeDescription()
     * @model containment="true"
     * @generated
     */
    SelectionDialogTreeDescription getSelectionDialogTreeDescription();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionDialogTreeDescription
     * <em>Selection Dialog Tree Description</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Selection Dialog Tree Description</em>' containment reference.
     * @see #getSelectionDialogTreeDescription()
     * @generated
     */
    void setSelectionDialogTreeDescription(SelectionDialogTreeDescription value);

    /**
     * Returns the value of the '<em><b>Multiple</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Multiple</em>' attribute.
     * @see #setMultiple(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_Multiple()
     * @model
     * @generated
     */
    boolean isMultiple();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#isMultiple
     * <em>Multiple</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Multiple</em>' attribute.
     * @see #isMultiple()
     * @generated
     */
    void setMultiple(boolean value);

    /**
     * Returns the value of the '<em><b>Optional</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Optional</em>' attribute.
     * @see #setOptional(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_Optional()
     * @model
     * @generated
     */
    boolean isOptional();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#isOptional
     * <em>Optional</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Optional</em>' attribute.
     * @see #isOptional()
     * @generated
     */
    void setOptional(boolean value);

    /**
     * Returns the value of the '<em><b>Default Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Default Title Expression</em>' attribute.
     * @see #setDefaultTitleExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_DefaultTitleExpression()
     * @model
     * @generated
     */
    String getDefaultTitleExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getDefaultTitleExpression
     * <em>Default Title Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Default Title Expression</em>' attribute.
     * @see #getDefaultTitleExpression()
     * @generated
     */
    void setDefaultTitleExpression(String value);

    /**
     * Returns the value of the '<em><b>No Selection Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>No Selection Title Expression</em>' attribute.
     * @see #setNoSelectionTitleExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_NoSelectionTitleExpression()
     * @model
     * @generated
     */
    String getNoSelectionTitleExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getNoSelectionTitleExpression
     * <em>No Selection Title Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>No Selection Title Expression</em>' attribute.
     * @see #getNoSelectionTitleExpression()
     * @generated
     */
    void setNoSelectionTitleExpression(String value);

    /**
     * Returns the value of the '<em><b>With Selection Title Expression</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>With Selection Title Expression</em>' attribute.
     * @see #setWithSelectionTitleExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_WithSelectionTitleExpression()
     * @model
     * @generated
     */
    String getWithSelectionTitleExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getWithSelectionTitleExpression
     * <em>With Selection Title Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>With Selection Title Expression</em>' attribute.
     * @see #getWithSelectionTitleExpression()
     * @generated
     */
    void setWithSelectionTitleExpression(String value);

    /**
     * Returns the value of the '<em><b>Description Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Description Expression</em>' attribute.
     * @see #setDescriptionExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_DescriptionExpression()
     * @model
     * @generated
     */
    String getDescriptionExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getDescriptionExpression
     * <em>Description Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Description Expression</em>' attribute.
     * @see #getDescriptionExpression()
     * @generated
     */
    void setDescriptionExpression(String value);

    /**
     * Returns the value of the '<em><b>No Selection Action Label Expression</b></em>' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>No Selection Action Label Expression</em>' attribute.
     * @see #setNoSelectionActionLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_NoSelectionActionLabelExpression()
     * @model
     * @generated
     */
    String getNoSelectionActionLabelExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getNoSelectionActionLabelExpression
     * <em>No Selection Action Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>No Selection Action Label Expression</em>' attribute.
     * @see #getNoSelectionActionLabelExpression()
     * @generated
     */
    void setNoSelectionActionLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>No Selection Action Description Expression</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>No Selection Action Description Expression</em>' attribute.
     * @see #setNoSelectionActionDescriptionExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_NoSelectionActionDescriptionExpression()
     * @model
     * @generated
     */
    String getNoSelectionActionDescriptionExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getNoSelectionActionDescriptionExpression
     * <em>No Selection Action Description Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>No Selection Action Description Expression</em>' attribute.
     * @see #getNoSelectionActionDescriptionExpression()
     * @generated
     */
    void setNoSelectionActionDescriptionExpression(String value);

    /**
     * Returns the value of the '<em><b>With Selection Action Label Expression</b></em>' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>With Selection Action Label Expression</em>' attribute.
     * @see #setWithSelectionActionLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_WithSelectionActionLabelExpression()
     * @model
     * @generated
     */
    String getWithSelectionActionLabelExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getWithSelectionActionLabelExpression
     * <em>With Selection Action Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>With Selection Action Label Expression</em>' attribute.
     * @see #getWithSelectionActionLabelExpression()
     * @generated
     */
    void setWithSelectionActionLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>With Selection Action Description Expression</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>With Selection Action Description Expression</em>' attribute.
     * @see #setWithSelectionActionDescriptionExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_WithSelectionActionDescriptionExpression()
     * @model
     * @generated
     */
    String getWithSelectionActionDescriptionExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getWithSelectionActionDescriptionExpression
     * <em>With Selection Action Description Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>With Selection Action Description Expression</em>' attribute.
     * @see #getWithSelectionActionDescriptionExpression()
     * @generated
     */
    void setWithSelectionActionDescriptionExpression(String value);

    /**
     * Returns the value of the '<em><b>No Selection Action Status Message Expression</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>No Selection Action Status Message Expression</em>' attribute.
     * @see #setNoSelectionActionStatusMessageExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_NoSelectionActionStatusMessageExpression()
     * @model
     * @generated
     */
    String getNoSelectionActionStatusMessageExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getNoSelectionActionStatusMessageExpression
     * <em>No Selection Action Status Message Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>No Selection Action Status Message Expression</em>' attribute.
     * @see #getNoSelectionActionStatusMessageExpression()
     * @generated
     */
    void setNoSelectionActionStatusMessageExpression(String value);

    /**
     * Returns the value of the '<em><b>Selection Required Without Selection Status Message Expression</b></em>'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Selection Required Without Selection Status Message Expression</em>' attribute.
     * @see #setSelectionRequiredWithoutSelectionStatusMessageExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_SelectionRequiredWithoutSelectionStatusMessageExpression()
     * @model
     * @generated
     */
    String getSelectionRequiredWithoutSelectionStatusMessageExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionRequiredWithoutSelectionStatusMessageExpression
     * <em>Selection Required Without Selection Status Message Expression</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Selection Required Without Selection Status Message Expression</em>'
     *            attribute.
     * @see #getSelectionRequiredWithoutSelectionStatusMessageExpression()
     * @generated
     */
    void setSelectionRequiredWithoutSelectionStatusMessageExpression(String value);

    /**
     * Returns the value of the '<em><b>Selection Required With Selection Status Message Expression</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Selection Required With Selection Status Message Expression</em>' attribute.
     * @see #setSelectionRequiredWithSelectionStatusMessageExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_SelectionRequiredWithSelectionStatusMessageExpression()
     * @model
     * @generated
     */
    String getSelectionRequiredWithSelectionStatusMessageExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionRequiredWithSelectionStatusMessageExpression
     * <em>Selection Required With Selection Status Message Expression</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Selection Required With Selection Status Message Expression</em>' attribute.
     * @see #getSelectionRequiredWithSelectionStatusMessageExpression()
     * @generated
     */
    void setSelectionRequiredWithSelectionStatusMessageExpression(String value);

    /**
     * Returns the value of the '<em><b>No Selection Confirm Button Label Expression</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>No Selection Confirm Button Label Expression</em>' attribute.
     * @see #setNoSelectionConfirmButtonLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_NoSelectionConfirmButtonLabelExpression()
     * @model
     * @generated
     */
    String getNoSelectionConfirmButtonLabelExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getNoSelectionConfirmButtonLabelExpression
     * <em>No Selection Confirm Button Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>No Selection Confirm Button Label Expression</em>' attribute.
     * @see #getNoSelectionConfirmButtonLabelExpression()
     * @generated
     */
    void setNoSelectionConfirmButtonLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Selection Required Without Selection Confirm Button Label Expression</b></em>'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Selection Required Without Selection Confirm Button Label Expression</em>'
     *         attribute.
     * @see #setSelectionRequiredWithoutSelectionConfirmButtonLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_SelectionRequiredWithoutSelectionConfirmButtonLabelExpression()
     * @model
     * @generated
     */
    String getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression
     * <em>Selection Required Without Selection Confirm Button Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Selection Required Without Selection Confirm Button Label Expression</em>'
     *            attribute.
     * @see #getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression()
     * @generated
     */
    void setSelectionRequiredWithoutSelectionConfirmButtonLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Selection Required With Selection Confirm Button Label Expression</b></em>'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Selection Required With Selection Confirm Button Label Expression</em>' attribute.
     * @see #setSelectionRequiredWithSelectionConfirmButtonLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getSelectionDialogDescription_SelectionRequiredWithSelectionConfirmButtonLabelExpression()
     * @model
     * @generated
     */
    String getSelectionRequiredWithSelectionConfirmButtonLabelExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDialogDescription#getSelectionRequiredWithSelectionConfirmButtonLabelExpression
     * <em>Selection Required With Selection Confirm Button Label Expression</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Selection Required With Selection Confirm Button Label Expression</em>'
     *            attribute.
     * @see #getSelectionRequiredWithSelectionConfirmButtonLabelExpression()
     * @generated
     */
    void setSelectionRequiredWithSelectionConfirmButtonLabelExpression(String value);

} // SelectionDialogDescription
