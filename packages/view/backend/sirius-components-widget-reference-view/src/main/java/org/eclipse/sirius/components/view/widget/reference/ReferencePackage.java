/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.widget.reference;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.form.FormPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @model kind="package"
 * @generated
 * @see org.eclipse.sirius.components.view.widget.reference.ReferenceFactory
 */
public interface ReferencePackage extends EPackage {

    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "reference";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "https://www.eclipse.org/sirius/widgets/reference";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "reference";

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionImpl <em>Widget Description</em>}' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionImpl
     * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferencePackageImpl#getReferenceWidgetDescription()
     */
    int REFERENCE_WIDGET_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__NAME = FormPackage.WIDGET_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__LABEL_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Help Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__HELP_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION;

    /**
     * The feature id for the '<em><b>Diagnostics Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__DIAGNOSTICS_EXPRESSION = FormPackage.WIDGET_DESCRIPTION__DIAGNOSTICS_EXPRESSION;

    /**
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Reference Owner Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Reference Name Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__BODY = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Clear Button</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__CLEAR_BUTTON = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__STYLE = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Widget Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The number of operations of the '<em>Widget Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionStyleImpl <em>Widget Description Style</em>}' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferencePackageImpl#getReferenceWidgetDescriptionStyle()
     */
    int REFERENCE_WIDGET_DESCRIPTION_STYLE = 1;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_STYLE__FONT_SIZE = FormPackage.WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_STYLE__ITALIC = FormPackage.WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_STYLE__BOLD = FormPackage.WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_STYLE__UNDERLINE = FormPackage.WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_STYLE__STRIKE_THROUGH = FormPackage.WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_STYLE__COLOR = FormPackage.WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Widget Description Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Widget Description Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.widget.reference.impl.ConditionalReferenceWidgetDescriptionStyleImpl
     * <em>Conditional Reference Widget Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.impl.ConditionalReferenceWidgetDescriptionStyleImpl
     * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferencePackageImpl#getConditionalReferenceWidgetDescriptionStyle()
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE = 2;

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    ReferencePackage eINSTANCE = org.eclipse.sirius.components.view.widget.reference.impl.ReferencePackageImpl.init();

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE__COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Conditional Reference Widget Description Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Conditional Reference Widget Description Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetClearButtonDescriptionImpl
     * <em>Widget Clear Button Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetClearButtonDescriptionImpl
     * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferencePackageImpl#getReferenceWidgetClearButtonDescription()
     */
    int REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION = 3;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__PRECONDITION_EXPRESSION = 0;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__BODY = 1;

    /**
     * The number of structural features of the '<em>Widget Clear Button Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Widget Clear Button Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription <em>Widget Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the meta object for class '<em>Widget Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription
     */
    EClass getReferenceWidgetDescription();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getReferenceOwnerExpression
     * <em>Reference Owner Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Reference Owner Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getReferenceOwnerExpression()
     * @see #getReferenceWidgetDescription()
     */
    EAttribute getReferenceWidgetDescription_ReferenceOwnerExpression();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getReferenceNameExpression
     * <em>Reference Name Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Reference Name Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getReferenceNameExpression()
     * @see #getReferenceWidgetDescription()
     */
    EAttribute getReferenceWidgetDescription_ReferenceNameExpression();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getBody <em>Body</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getBody()
     * @see #getReferenceWidgetDescription()
     */
    EReference getReferenceWidgetDescription_Body();

    /**
     * Returns the meta object for the containment reference '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getClearButton <em>Clear Button</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Clear Button</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getClearButton()
     * @see #getReferenceWidgetDescription()
     */
    EReference getReferenceWidgetDescription_ClearButton();

    /**
     * Returns the meta object for the containment reference '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getStyle()
     * @see #getReferenceWidgetDescription()
     */
    EReference getReferenceWidgetDescription_Style();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getConditionalStyles
     * <em>Conditional Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getConditionalStyles()
     * @see #getReferenceWidgetDescription()
     */
    EReference getReferenceWidgetDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle <em>Widget Description Style</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for class '<em>Widget Description Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle
     */
    EClass getReferenceWidgetDescriptionStyle();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle#getColor
     * <em>Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Color</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle#getColor()
     * @see #getReferenceWidgetDescriptionStyle()
     */
    EReference getReferenceWidgetDescriptionStyle_Color();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle
     * <em>Conditional Reference Widget Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Reference Widget Description Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle
     */
    EClass getConditionalReferenceWidgetDescriptionStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription <em>Widget Clear Button Description</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Widget Clear Button Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription
     */
    EClass getReferenceWidgetClearButtonDescription();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription#getPreconditionExpression
     * <em>Precondition Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription#getPreconditionExpression()
     * @see #getReferenceWidgetClearButtonDescription()
     */
    EAttribute getReferenceWidgetClearButtonDescription_PreconditionExpression();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription#getBody
     * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetClearButtonDescription#getBody()
     * @see #getReferenceWidgetClearButtonDescription()
     */
    EReference getReferenceWidgetClearButtonDescription_Body();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getIsEnabledExpression
     * <em>Is Enabled Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription#getIsEnabledExpression()
     * @see #getReferenceWidgetDescription()
     */
    EAttribute getReferenceWidgetDescription_IsEnabledExpression();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ReferenceFactory getReferenceFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each operation of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     *
     * @generated
     */
    interface Literals {

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionImpl <em>Widget Description</em>}' class. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionImpl
         * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferencePackageImpl#getReferenceWidgetDescription()
         */
        EClass REFERENCE_WIDGET_DESCRIPTION = eINSTANCE.getReferenceWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Reference Owner Expression</b></em>' attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION = eINSTANCE.getReferenceWidgetDescription_ReferenceOwnerExpression();

        /**
         * The meta object literal for the '<em><b>Reference Name Expression</b></em>' attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION = eINSTANCE.getReferenceWidgetDescription_ReferenceNameExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REFERENCE_WIDGET_DESCRIPTION__BODY = eINSTANCE.getReferenceWidgetDescription_Body();

        /**
         * The meta object literal for the '<em><b>Clear Button</b></em>' containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REFERENCE_WIDGET_DESCRIPTION__CLEAR_BUTTON = eINSTANCE.getReferenceWidgetDescription_ClearButton();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REFERENCE_WIDGET_DESCRIPTION__STYLE = eINSTANCE.getReferenceWidgetDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getReferenceWidgetDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionStyleImpl
         * <em>Widget Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferencePackageImpl#getReferenceWidgetDescriptionStyle()
         */
        EClass REFERENCE_WIDGET_DESCRIPTION_STYLE = eINSTANCE.getReferenceWidgetDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REFERENCE_WIDGET_DESCRIPTION_STYLE__COLOR = eINSTANCE.getReferenceWidgetDescriptionStyle_Color();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.widget.reference.impl.ConditionalReferenceWidgetDescriptionStyleImpl
         * <em>Conditional Reference Widget Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.widget.reference.impl.ConditionalReferenceWidgetDescriptionStyleImpl
         * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferencePackageImpl#getConditionalReferenceWidgetDescriptionStyle()
         */
        EClass CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE = eINSTANCE.getConditionalReferenceWidgetDescriptionStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetClearButtonDescriptionImpl
         * <em>Widget Clear Button Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferenceWidgetClearButtonDescriptionImpl
         * @see org.eclipse.sirius.components.view.widget.reference.impl.ReferencePackageImpl#getReferenceWidgetClearButtonDescription()
         */
        EClass REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION = eINSTANCE.getReferenceWidgetClearButtonDescription();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__PRECONDITION_EXPRESSION = eINSTANCE.getReferenceWidgetClearButtonDescription_PreconditionExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REFERENCE_WIDGET_CLEAR_BUTTON_DESCRIPTION__BODY = eINSTANCE.getReferenceWidgetClearButtonDescription_Body();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getReferenceWidgetDescription_IsEnabledExpression();

    }

} // ReferencePackage
