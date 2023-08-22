/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widgets.reference;

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
 * @see org.eclipse.sirius.components.widgets.reference.ReferenceFactory
 * @model kind="package"
 * @generated
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
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl <em>Widget
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl
     * @see org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl#getReferenceWidgetDescription()
     * @generated
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
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
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
     * The feature id for the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Reference Owner Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Reference Name Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__BODY = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__STYLE = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Widget Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionStyleImpl <em>Widget
     * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionStyleImpl
     * @see org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl#getReferenceWidgetDescriptionStyle()
     * @generated
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
     * The number of structural features of the '<em>Widget Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT = FormPackage.WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Widget Description Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REFERENCE_WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT = FormPackage.WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.widgets.reference.impl.ConditionalReferenceWidgetDescriptionStyleImpl
     * <em>Conditional Reference Widget Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.widgets.reference.impl.ConditionalReferenceWidgetDescriptionStyleImpl
     * @see org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl#getConditionalReferenceWidgetDescriptionStyle()
     * @generated
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE = 2;

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    ReferencePackage eINSTANCE = org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl.init();

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
     * The number of structural features of the '<em>Conditional Reference Widget Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Conditional Reference Widget Description Style</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription <em>Widget Description</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Widget Description</em>'.
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription
     * @generated
     */
    EClass getReferenceWidgetDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceOwnerExpression
     * <em>Reference Owner Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Reference Owner Expression</em>'.
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceOwnerExpression()
     * @see #getReferenceWidgetDescription()
     * @generated
     */
    EAttribute getReferenceWidgetDescription_ReferenceOwnerExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceNameExpression
     * <em>Reference Name Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Reference Name Expression</em>'.
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceNameExpression()
     * @see #getReferenceWidgetDescription()
     * @generated
     */
    EAttribute getReferenceWidgetDescription_ReferenceNameExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getBody <em>Body</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @generated
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getBody()
     * @see #getReferenceWidgetDescription()
     */
    EReference getReferenceWidgetDescription_Body();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getStyle <em>Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getStyle()
     * @see #getReferenceWidgetDescription()
     * @generated
     */
    EReference getReferenceWidgetDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getConditionalStyles
     * <em>Conditional Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getConditionalStyles()
     * @see #getReferenceWidgetDescription()
     * @generated
     */
    EReference getReferenceWidgetDescription_ConditionalStyles();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle <em>Widget Description
     * Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Widget Description Style</em>'.
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle
     * @generated
     */
    EClass getReferenceWidgetDescriptionStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle#getColor
     * <em>Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Color</em>'.
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle#getColor()
     * @see #getReferenceWidgetDescriptionStyle()
     * @generated
     */
    EReference getReferenceWidgetDescriptionStyle_Color();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.widgets.reference.ConditionalReferenceWidgetDescriptionStyle
     * <em>Conditional Reference Widget Description Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Reference Widget Description Style</em>'.
     * @see org.eclipse.sirius.components.widgets.reference.ConditionalReferenceWidgetDescriptionStyle
     * @generated
     */
    EClass getConditionalReferenceWidgetDescriptionStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getIsEnabledExpression <em>Is
     * Enabled Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Enabled Expression</em>'.
     * @see org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getIsEnabledExpression()
     * @see #getReferenceWidgetDescription()
     * @generated
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
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl <em>Widget
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionImpl
         * @see org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl#getReferenceWidgetDescription()
         * @generated
         */
        EClass REFERENCE_WIDGET_DESCRIPTION = eINSTANCE.getReferenceWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Reference Owner Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REFERENCE_WIDGET_DESCRIPTION__REFERENCE_OWNER_EXPRESSION = eINSTANCE.getReferenceWidgetDescription_ReferenceOwnerExpression();

        /**
         * The meta object literal for the '<em><b>Reference Name Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REFERENCE_WIDGET_DESCRIPTION__REFERENCE_NAME_EXPRESSION = eINSTANCE.getReferenceWidgetDescription_ReferenceNameExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REFERENCE_WIDGET_DESCRIPTION__BODY = eINSTANCE.getReferenceWidgetDescription_Body();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REFERENCE_WIDGET_DESCRIPTION__STYLE = eINSTANCE.getReferenceWidgetDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference REFERENCE_WIDGET_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getReferenceWidgetDescription_ConditionalStyles();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionStyleImpl <em>Widget
         * Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.widgets.reference.impl.ReferenceWidgetDescriptionStyleImpl
         * @see org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl#getReferenceWidgetDescriptionStyle()
         * @generated
         */
        EClass REFERENCE_WIDGET_DESCRIPTION_STYLE = eINSTANCE.getReferenceWidgetDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference REFERENCE_WIDGET_DESCRIPTION_STYLE__COLOR = eINSTANCE.getReferenceWidgetDescriptionStyle_Color();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.widgets.reference.impl.ConditionalReferenceWidgetDescriptionStyleImpl
         * <em>Conditional Reference Widget Description Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @generated
         * @see org.eclipse.sirius.components.widgets.reference.impl.ConditionalReferenceWidgetDescriptionStyleImpl
         * @see org.eclipse.sirius.components.widgets.reference.impl.ReferencePackageImpl#getConditionalReferenceWidgetDescriptionStyle()
         */
        EClass CONDITIONAL_REFERENCE_WIDGET_DESCRIPTION_STYLE = eINSTANCE.getConditionalReferenceWidgetDescriptionStyle();

        /**
         * The meta object literal for the '<em><b>Is Enabled Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REFERENCE_WIDGET_DESCRIPTION__IS_ENABLED_EXPRESSION = eINSTANCE.getReferenceWidgetDescription_IsEnabledExpression();

    }

} // ReferencePackage
