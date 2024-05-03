/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.sirius.components.view.ViewFactory
 * @model kind="package"
 * @generated
 */
public interface ViewPackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "view";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/view";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "view";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    ViewPackage eINSTANCE = org.eclipse.sirius.components.view.impl.ViewPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ViewImpl <em>View</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ViewImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getView()
     * @generated
     */
    int VIEW = 0;

    /**
     * The feature id for the '<em><b>Descriptions</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int VIEW__DESCRIPTIONS = 0;

    /**
     * The feature id for the '<em><b>Color Palettes</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int VIEW__COLOR_PALETTES = 1;

    /**
     * The number of structural features of the '<em>View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int VIEW_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int VIEW_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ColorPaletteImpl <em>Color
     * Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ColorPaletteImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getColorPalette()
     * @generated
     */
    int COLOR_PALETTE = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLOR_PALETTE__NAME = 0;

    /**
     * The feature id for the '<em><b>Colors</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLOR_PALETTE__COLORS = 1;

    /**
     * The number of structural features of the '<em>Color Palette</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLOR_PALETTE_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Color Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLOR_PALETTE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.UserColorImpl <em>User Color</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.UserColorImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getUserColor()
     * @generated
     */
    int USER_COLOR = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int USER_COLOR__NAME = 0;

    /**
     * The number of structural features of the '<em>User Color</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int USER_COLOR_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>User Color</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int USER_COLOR_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.FixedColorImpl <em>Fixed Color</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.FixedColorImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getFixedColor()
     * @generated
     */
    int FIXED_COLOR = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FIXED_COLOR__NAME = USER_COLOR__NAME;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FIXED_COLOR__VALUE = USER_COLOR_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Fixed Color</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int FIXED_COLOR_FEATURE_COUNT = USER_COLOR_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Fixed Color</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FIXED_COLOR_OPERATION_COUNT = USER_COLOR_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl
     * <em>Representation Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRepresentationDescription()
     * @generated
     */
    int REPRESENTATION_DESCRIPTION = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REPRESENTATION_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REPRESENTATION_DESCRIPTION__DOMAIN_TYPE = 1;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION = 3;

    /**
     * The number of structural features of the '<em>Representation Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REPRESENTATION_DESCRIPTION_FEATURE_COUNT = 4;

    /**
     * The number of operations of the '<em>Representation Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int REPRESENTATION_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.LabelStyleImpl <em>Label Style</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.LabelStyleImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLabelStyle()
     * @generated
     */
    int LABEL_STYLE = 5;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE__FONT_SIZE = 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE__ITALIC = 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE__BOLD = 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE__UNDERLINE = 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE__STRIKE_THROUGH = 4;

    /**
     * The number of structural features of the '<em>Label Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE_FEATURE_COUNT = 5;

    /**
     * The number of operations of the '<em>Label Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_STYLE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.OperationImpl <em>Operation</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.OperationImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getOperation()
     * @generated
     */
    int OPERATION = 6;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OPERATION__CHILDREN = 0;

    /**
     * The number of structural features of the '<em>Operation</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OPERATION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Operation</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OPERATION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ChangeContextImpl <em>Change
     * Context</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ChangeContextImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getChangeContext()
     * @generated
     */
    int CHANGE_CONTEXT = 7;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHANGE_CONTEXT__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHANGE_CONTEXT__EXPRESSION = OPERATION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Change Context</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHANGE_CONTEXT_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Change Context</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CHANGE_CONTEXT_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.CreateInstanceImpl <em>Create
     * Instance</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.CreateInstanceImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getCreateInstance()
     * @generated
     */
    int CREATE_INSTANCE = 8;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_INSTANCE__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Type Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_INSTANCE__TYPE_NAME = OPERATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Reference Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_INSTANCE__REFERENCE_NAME = OPERATION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Variable Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_INSTANCE__VARIABLE_NAME = OPERATION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Create Instance</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_INSTANCE_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Create Instance</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_INSTANCE_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.SetValueImpl <em>Set Value</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.SetValueImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSetValue()
     * @generated
     */
    int SET_VALUE = 9;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SET_VALUE__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Feature Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SET_VALUE__FEATURE_NAME = OPERATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SET_VALUE__VALUE_EXPRESSION = OPERATION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Set Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SET_VALUE_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Set Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SET_VALUE_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.UnsetValueImpl <em>Unset Value</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.UnsetValueImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getUnsetValue()
     * @generated
     */
    int UNSET_VALUE = 10;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int UNSET_VALUE__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Feature Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int UNSET_VALUE__FEATURE_NAME = OPERATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Element Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int UNSET_VALUE__ELEMENT_EXPRESSION = OPERATION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Unset Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int UNSET_VALUE_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Unset Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int UNSET_VALUE_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.DeleteElementImpl <em>Delete
     * Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.DeleteElementImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDeleteElement()
     * @generated
     */
    int DELETE_ELEMENT = 11;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_ELEMENT__CHILDREN = OPERATION__CHILDREN;

    /**
     * The number of structural features of the '<em>Delete Element</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_ELEMENT_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Delete Element</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_ELEMENT_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.LetImpl <em>Let</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.LetImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLet()
     * @generated
     */
    int LET = 12;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LET__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Variable Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LET__VARIABLE_NAME = OPERATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LET__VALUE_EXPRESSION = OPERATION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Let</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LET_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Let</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LET_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.IfImpl <em>If</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.IfImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getIf()
     * @generated
     */
    int IF = 13;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IF__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Condition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IF__CONDITION_EXPRESSION = OPERATION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>If</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IF_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>If</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IF_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ConditionalImpl <em>Conditional</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ConditionalImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditional()
     * @generated
     */
    int CONDITIONAL = 14;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL__CONDITION = 0;

    /**
     * The number of structural features of the '<em>Conditional</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Conditional</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.impl.ForImpl <em>For</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ForImpl
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getFor()
     * @generated
     */
    int FOR = 15;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FOR__CHILDREN = OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FOR__EXPRESSION = OPERATION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Iterator Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FOR__ITERATOR_NAME = OPERATION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>For</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FOR_FEATURE_COUNT = OPERATION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>For</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FOR_OPERATION_COUNT = OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '<em>Identifier</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see java.lang.String
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getIdentifier()
     * @generated
     */
    int IDENTIFIER = 16;

    /**
     * The meta object id for the '<em>Interpreted Expression</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see java.lang.String
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getInterpretedExpression()
     * @generated
     */
    int INTERPRETED_EXPRESSION = 17;

    /**
     * The meta object id for the '<em>Domain Type</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see java.lang.String
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDomainType()
     * @generated
     */
    int DOMAIN_TYPE = 18;

    /**
     * The meta object id for the '<em>Color</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see java.lang.String
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getColor()
     * @generated
     */
    int COLOR = 19;

    /**
     * The meta object id for the '<em>Length</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLength()
     * @generated
     */
    int LENGTH = 20;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.View <em>View</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>View</em>'.
     * @see org.eclipse.sirius.components.view.View
     * @generated
     */
    EClass getView();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.View#getDescriptions <em>Descriptions</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.View#getDescriptions()
     * @see #getView()
     * @generated
     */
    EReference getView_Descriptions();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.View#getColorPalettes <em>Color Palettes</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Color Palettes</em>'.
     * @see org.eclipse.sirius.components.view.View#getColorPalettes()
     * @see #getView()
     * @generated
     */
    EReference getView_ColorPalettes();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ColorPalette <em>Color
     * Palette</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Color Palette</em>'.
     * @see org.eclipse.sirius.components.view.ColorPalette
     * @generated
     */
    EClass getColorPalette();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.ColorPalette#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.ColorPalette#getName()
     * @see #getColorPalette()
     * @generated
     */
    EAttribute getColorPalette_Name();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.ColorPalette#getColors <em>Colors</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Colors</em>'.
     * @see org.eclipse.sirius.components.view.ColorPalette#getColors()
     * @see #getColorPalette()
     * @generated
     */
    EReference getColorPalette_Colors();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.FixedColor <em>Fixed Color</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Fixed Color</em>'.
     * @see org.eclipse.sirius.components.view.FixedColor
     * @generated
     */
    EClass getFixedColor();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.FixedColor#getValue
     * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see org.eclipse.sirius.components.view.FixedColor#getValue()
     * @see #getFixedColor()
     * @generated
     */
    EAttribute getFixedColor_Value();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.UserColor <em>User Color</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>User Color</em>'.
     * @see org.eclipse.sirius.components.view.UserColor
     * @generated
     */
    EClass getUserColor();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.UserColor#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.UserColor#getName()
     * @see #getUserColor()
     * @generated
     */
    EAttribute getUserColor_Name();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.RepresentationDescription
     * <em>Representation Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Representation Description</em>'.
     * @see org.eclipse.sirius.components.view.RepresentationDescription
     * @generated
     */
    EClass getRepresentationDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RepresentationDescription#getName <em>Name</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.RepresentationDescription#getName()
     * @see #getRepresentationDescription()
     * @generated
     */
    EAttribute getRepresentationDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RepresentationDescription#getDomainType <em>Domain Type</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Domain Type</em>'.
     * @see org.eclipse.sirius.components.view.RepresentationDescription#getDomainType()
     * @see #getRepresentationDescription()
     * @generated
     */
    EAttribute getRepresentationDescription_DomainType();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RepresentationDescription#getPreconditionExpression <em>Precondition
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @see org.eclipse.sirius.components.view.RepresentationDescription#getPreconditionExpression()
     * @see #getRepresentationDescription()
     * @generated
     */
    EAttribute getRepresentationDescription_PreconditionExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.RepresentationDescription#getTitleExpression <em>Title
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Title Expression</em>'.
     * @see org.eclipse.sirius.components.view.RepresentationDescription#getTitleExpression()
     * @see #getRepresentationDescription()
     * @generated
     */
    EAttribute getRepresentationDescription_TitleExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.LabelStyle <em>Label Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Label Style</em>'.
     * @see org.eclipse.sirius.components.view.LabelStyle
     * @generated
     */
    EClass getLabelStyle();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.LabelStyle#getFontSize
     * <em>Font Size</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Font Size</em>'.
     * @see org.eclipse.sirius.components.view.LabelStyle#getFontSize()
     * @see #getLabelStyle()
     * @generated
     */
    EAttribute getLabelStyle_FontSize();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.LabelStyle#isItalic
     * <em>Italic</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Italic</em>'.
     * @see org.eclipse.sirius.components.view.LabelStyle#isItalic()
     * @see #getLabelStyle()
     * @generated
     */
    EAttribute getLabelStyle_Italic();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.LabelStyle#isBold
     * <em>Bold</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Bold</em>'.
     * @see org.eclipse.sirius.components.view.LabelStyle#isBold()
     * @see #getLabelStyle()
     * @generated
     */
    EAttribute getLabelStyle_Bold();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.LabelStyle#isUnderline
     * <em>Underline</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Underline</em>'.
     * @see org.eclipse.sirius.components.view.LabelStyle#isUnderline()
     * @see #getLabelStyle()
     * @generated
     */
    EAttribute getLabelStyle_Underline();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.LabelStyle#isStrikeThrough
     * <em>Strike Through</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Strike Through</em>'.
     * @see org.eclipse.sirius.components.view.LabelStyle#isStrikeThrough()
     * @see #getLabelStyle()
     * @generated
     */
    EAttribute getLabelStyle_StrikeThrough();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.Operation <em>Operation</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Operation</em>'.
     * @see org.eclipse.sirius.components.view.Operation
     * @generated
     */
    EClass getOperation();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.Operation#getChildren <em>Children</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Children</em>'.
     * @see org.eclipse.sirius.components.view.Operation#getChildren()
     * @see #getOperation()
     * @generated
     */
    EReference getOperation_Children();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.ChangeContext <em>Change
     * Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Change Context</em>'.
     * @see org.eclipse.sirius.components.view.ChangeContext
     * @generated
     */
    EClass getChangeContext();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.ChangeContext#getExpression
     * <em>Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Expression</em>'.
     * @see org.eclipse.sirius.components.view.ChangeContext#getExpression()
     * @see #getChangeContext()
     * @generated
     */
    EAttribute getChangeContext_Expression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.CreateInstance <em>Create
     * Instance</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Create Instance</em>'.
     * @see org.eclipse.sirius.components.view.CreateInstance
     * @generated
     */
    EClass getCreateInstance();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.CreateInstance#getTypeName
     * <em>Type Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Type Name</em>'.
     * @see org.eclipse.sirius.components.view.CreateInstance#getTypeName()
     * @see #getCreateInstance()
     * @generated
     */
    EAttribute getCreateInstance_TypeName();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.CreateInstance#getReferenceName <em>Reference Name</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Reference Name</em>'.
     * @see org.eclipse.sirius.components.view.CreateInstance#getReferenceName()
     * @see #getCreateInstance()
     * @generated
     */
    EAttribute getCreateInstance_ReferenceName();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.CreateInstance#getVariableName <em>Variable Name</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Variable Name</em>'.
     * @see org.eclipse.sirius.components.view.CreateInstance#getVariableName()
     * @see #getCreateInstance()
     * @generated
     */
    EAttribute getCreateInstance_VariableName();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.SetValue <em>Set Value</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Set Value</em>'.
     * @see org.eclipse.sirius.components.view.SetValue
     * @generated
     */
    EClass getSetValue();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.SetValue#getFeatureName
     * <em>Feature Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Feature Name</em>'.
     * @see org.eclipse.sirius.components.view.SetValue#getFeatureName()
     * @see #getSetValue()
     * @generated
     */
    EAttribute getSetValue_FeatureName();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.SetValue#getValueExpression
     * <em>Value Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.SetValue#getValueExpression()
     * @see #getSetValue()
     * @generated
     */
    EAttribute getSetValue_ValueExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.UnsetValue <em>Unset Value</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Unset Value</em>'.
     * @see org.eclipse.sirius.components.view.UnsetValue
     * @generated
     */
    EClass getUnsetValue();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.UnsetValue#getFeatureName
     * <em>Feature Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Feature Name</em>'.
     * @see org.eclipse.sirius.components.view.UnsetValue#getFeatureName()
     * @see #getUnsetValue()
     * @generated
     */
    EAttribute getUnsetValue_FeatureName();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.UnsetValue#getElementExpression <em>Element Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Element Expression</em>'.
     * @see org.eclipse.sirius.components.view.UnsetValue#getElementExpression()
     * @see #getUnsetValue()
     * @generated
     */
    EAttribute getUnsetValue_ElementExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.DeleteElement <em>Delete
     * Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Delete Element</em>'.
     * @see org.eclipse.sirius.components.view.DeleteElement
     * @generated
     */
    EClass getDeleteElement();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.Let <em>Let</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Let</em>'.
     * @see org.eclipse.sirius.components.view.Let
     * @generated
     */
    EClass getLet();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.Let#getVariableName
     * <em>Variable Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Variable Name</em>'.
     * @see org.eclipse.sirius.components.view.Let#getVariableName()
     * @see #getLet()
     * @generated
     */
    EAttribute getLet_VariableName();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.Let#getValueExpression
     * <em>Value Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @see org.eclipse.sirius.components.view.Let#getValueExpression()
     * @see #getLet()
     * @generated
     */
    EAttribute getLet_ValueExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.If <em>If</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>If</em>'.
     * @see org.eclipse.sirius.components.view.If
     * @generated
     */
    EClass getIf();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.If#getConditionExpression
     * <em>Condition Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Condition Expression</em>'.
     * @see org.eclipse.sirius.components.view.If#getConditionExpression()
     * @see #getIf()
     * @generated
     */
    EAttribute getIf_ConditionExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.Conditional <em>Conditional</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional</em>'.
     * @see org.eclipse.sirius.components.view.Conditional
     * @generated
     */
    EClass getConditional();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.Conditional#getCondition
     * <em>Condition</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Condition</em>'.
     * @see org.eclipse.sirius.components.view.Conditional#getCondition()
     * @see #getConditional()
     * @generated
     */
    EAttribute getConditional_Condition();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.For <em>For</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>For</em>'.
     * @see org.eclipse.sirius.components.view.For
     * @generated
     */
    EClass getFor();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.For#getExpression
     * <em>Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Expression</em>'.
     * @see org.eclipse.sirius.components.view.For#getExpression()
     * @see #getFor()
     * @generated
     */
    EAttribute getFor_Expression();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.For#getIteratorName
     * <em>Iterator Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Iterator Name</em>'.
     * @see org.eclipse.sirius.components.view.For#getIteratorName()
     * @see #getFor()
     * @generated
     */
    EAttribute getFor_IteratorName();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Identifier</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for data type '<em>Identifier</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     * @generated
     */
    EDataType getIdentifier();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Interpreted Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for data type '<em>Interpreted Expression</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     * @generated
     */
    EDataType getInterpretedExpression();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Domain Type</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for data type '<em>Domain Type</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     * @generated
     */
    EDataType getDomainType();

    /**
     * Returns the meta object for data type '{@link java.lang.String <em>Color</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for data type '<em>Color</em>'.
     * @see java.lang.String
     * @model instanceClass="java.lang.String"
     * @generated
     */
    EDataType getColor();

    /**
     * Returns the meta object for data type '<em>Length</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for data type '<em>Length</em>'.
     * @model instanceClass="int"
     * @generated
     */
    EDataType getLength();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ViewFactory getViewFactory();

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
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ViewImpl <em>View</em>}'
         * class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ViewImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getView()
         * @generated
         */
        EClass VIEW = eINSTANCE.getView();

        /**
         * The meta object literal for the '<em><b>Descriptions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference VIEW__DESCRIPTIONS = eINSTANCE.getView_Descriptions();

        /**
         * The meta object literal for the '<em><b>Color Palettes</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference VIEW__COLOR_PALETTES = eINSTANCE.getView_ColorPalettes();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ColorPaletteImpl <em>Color
         * Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ColorPaletteImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getColorPalette()
         * @generated
         */
        EClass COLOR_PALETTE = eINSTANCE.getColorPalette();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute COLOR_PALETTE__NAME = eINSTANCE.getColorPalette_Name();

        /**
         * The meta object literal for the '<em><b>Colors</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference COLOR_PALETTE__COLORS = eINSTANCE.getColorPalette_Colors();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.FixedColorImpl <em>Fixed
         * Color</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.FixedColorImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getFixedColor()
         * @generated
         */
        EClass FIXED_COLOR = eINSTANCE.getFixedColor();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute FIXED_COLOR__VALUE = eINSTANCE.getFixedColor_Value();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.UserColorImpl <em>User
         * Color</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.UserColorImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getUserColor()
         * @generated
         */
        EClass USER_COLOR = eINSTANCE.getUserColor();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute USER_COLOR__NAME = eINSTANCE.getUserColor_Name();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl
         * <em>Representation Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getRepresentationDescription()
         * @generated
         */
        EClass REPRESENTATION_DESCRIPTION = eINSTANCE.getRepresentationDescription();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute REPRESENTATION_DESCRIPTION__NAME = eINSTANCE.getRepresentationDescription_Name();

        /**
         * The meta object literal for the '<em><b>Domain Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute REPRESENTATION_DESCRIPTION__DOMAIN_TYPE = eINSTANCE.getRepresentationDescription_DomainType();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION = eINSTANCE.getRepresentationDescription_PreconditionExpression();

        /**
         * The meta object literal for the '<em><b>Title Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION = eINSTANCE.getRepresentationDescription_TitleExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.LabelStyleImpl <em>Label
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.LabelStyleImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLabelStyle()
         * @generated
         */
        EClass LABEL_STYLE = eINSTANCE.getLabelStyle();

        /**
         * The meta object literal for the '<em><b>Font Size</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_STYLE__FONT_SIZE = eINSTANCE.getLabelStyle_FontSize();

        /**
         * The meta object literal for the '<em><b>Italic</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_STYLE__ITALIC = eINSTANCE.getLabelStyle_Italic();

        /**
         * The meta object literal for the '<em><b>Bold</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_STYLE__BOLD = eINSTANCE.getLabelStyle_Bold();

        /**
         * The meta object literal for the '<em><b>Underline</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_STYLE__UNDERLINE = eINSTANCE.getLabelStyle_Underline();

        /**
         * The meta object literal for the '<em><b>Strike Through</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_STYLE__STRIKE_THROUGH = eINSTANCE.getLabelStyle_StrikeThrough();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.OperationImpl
         * <em>Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.OperationImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getOperation()
         * @generated
         */
        EClass OPERATION = eINSTANCE.getOperation();

        /**
         * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference OPERATION__CHILDREN = eINSTANCE.getOperation_Children();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ChangeContextImpl <em>Change
         * Context</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ChangeContextImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getChangeContext()
         * @generated
         */
        EClass CHANGE_CONTEXT = eINSTANCE.getChangeContext();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute CHANGE_CONTEXT__EXPRESSION = eINSTANCE.getChangeContext_Expression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.CreateInstanceImpl <em>Create
         * Instance</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.CreateInstanceImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getCreateInstance()
         * @generated
         */
        EClass CREATE_INSTANCE = eINSTANCE.getCreateInstance();

        /**
         * The meta object literal for the '<em><b>Type Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_INSTANCE__TYPE_NAME = eINSTANCE.getCreateInstance_TypeName();

        /**
         * The meta object literal for the '<em><b>Reference Name</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_INSTANCE__REFERENCE_NAME = eINSTANCE.getCreateInstance_ReferenceName();

        /**
         * The meta object literal for the '<em><b>Variable Name</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_INSTANCE__VARIABLE_NAME = eINSTANCE.getCreateInstance_VariableName();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.SetValueImpl <em>Set
         * Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.SetValueImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getSetValue()
         * @generated
         */
        EClass SET_VALUE = eINSTANCE.getSetValue();

        /**
         * The meta object literal for the '<em><b>Feature Name</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SET_VALUE__FEATURE_NAME = eINSTANCE.getSetValue_FeatureName();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SET_VALUE__VALUE_EXPRESSION = eINSTANCE.getSetValue_ValueExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.UnsetValueImpl <em>Unset
         * Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.UnsetValueImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getUnsetValue()
         * @generated
         */
        EClass UNSET_VALUE = eINSTANCE.getUnsetValue();

        /**
         * The meta object literal for the '<em><b>Feature Name</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute UNSET_VALUE__FEATURE_NAME = eINSTANCE.getUnsetValue_FeatureName();

        /**
         * The meta object literal for the '<em><b>Element Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute UNSET_VALUE__ELEMENT_EXPRESSION = eINSTANCE.getUnsetValue_ElementExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.DeleteElementImpl <em>Delete
         * Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.DeleteElementImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDeleteElement()
         * @generated
         */
        EClass DELETE_ELEMENT = eINSTANCE.getDeleteElement();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.LetImpl <em>Let</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.LetImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLet()
         * @generated
         */
        EClass LET = eINSTANCE.getLet();

        /**
         * The meta object literal for the '<em><b>Variable Name</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LET__VARIABLE_NAME = eINSTANCE.getLet_VariableName();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LET__VALUE_EXPRESSION = eINSTANCE.getLet_ValueExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.IfImpl <em>If</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.IfImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getIf()
         * @generated
         */
        EClass IF = eINSTANCE.getIf();

        /**
         * The meta object literal for the '<em><b>Condition Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute IF__CONDITION_EXPRESSION = eINSTANCE.getIf_ConditionExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ConditionalImpl
         * <em>Conditional</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ConditionalImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getConditional()
         * @generated
         */
        EClass CONDITIONAL = eINSTANCE.getConditional();

        /**
         * The meta object literal for the '<em><b>Condition</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute CONDITIONAL__CONDITION = eINSTANCE.getConditional_Condition();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.impl.ForImpl <em>For</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ForImpl
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getFor()
         * @generated
         */
        EClass FOR = eINSTANCE.getFor();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute FOR__EXPRESSION = eINSTANCE.getFor_Expression();

        /**
         * The meta object literal for the '<em><b>Iterator Name</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute FOR__ITERATOR_NAME = eINSTANCE.getFor_IteratorName();

        /**
         * The meta object literal for the '<em>Identifier</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see java.lang.String
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getIdentifier()
         * @generated
         */
        EDataType IDENTIFIER = eINSTANCE.getIdentifier();

        /**
         * The meta object literal for the '<em>Interpreted Expression</em>' data type. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @see java.lang.String
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getInterpretedExpression()
         * @generated
         */
        EDataType INTERPRETED_EXPRESSION = eINSTANCE.getInterpretedExpression();

        /**
         * The meta object literal for the '<em>Domain Type</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         *
         * @see java.lang.String
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getDomainType()
         * @generated
         */
        EDataType DOMAIN_TYPE = eINSTANCE.getDomainType();

        /**
         * The meta object literal for the '<em>Color</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see java.lang.String
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getColor()
         * @generated
         */
        EDataType COLOR = eINSTANCE.getColor();

        /**
         * The meta object literal for the '<em>Length</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.impl.ViewPackageImpl#getLength()
         * @generated
         */
        EDataType LENGTH = eINSTANCE.getLength();

    }

} // ViewPackage
