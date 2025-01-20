/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.sirius.components.view.table;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.view.ViewPackage;

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
 * @see org.eclipse.sirius.components.view.table.TableFactory
 */
public interface TablePackage extends EPackage {

    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "table";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/table";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "table";

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl
     * <em>Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getTableDescription()
     */
    int TABLE_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__NAME = ViewPackage.REPRESENTATION_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__DOMAIN_TYPE = ViewPackage.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__PRECONDITION_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__TITLE_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION;

    /**
     * The feature id for the '<em><b>Icon Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__ICON_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__ICON_EXPRESSION;

    /**
     * The feature id for the '<em><b>Use Striped Rows Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Column Descriptions</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Row Description</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__ROW_DESCRIPTION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Cell Descriptions</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__CELL_DESCRIPTIONS = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION_FEATURE_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION_OPERATION_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.impl.TableElementDescriptionImpl
     * <em>Element Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.TableElementDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getTableElementDescription()
     */
    int TABLE_ELEMENT_DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_ELEMENT_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_ELEMENT_DESCRIPTION__DOMAIN_TYPE = 1;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION = 3;

    /**
     * The number of structural features of the '<em>Element Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT = 4;

    /**
     * The number of operations of the '<em>Element Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TABLE_ELEMENT_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.impl.ColumnDescriptionImpl <em>Column
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.ColumnDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getColumnDescription()
     */
    int COLUMN_DESCRIPTION = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__NAME = TABLE_ELEMENT_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__DOMAIN_TYPE = TABLE_ELEMENT_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = TABLE_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION = TABLE_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Header Index Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Header Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Header Icon Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Initial Width Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Is Resizable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Filter Widget Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Column Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION_FEATURE_COUNT = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The number of operations of the '<em>Column Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION_OPERATION_COUNT = TABLE_ELEMENT_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.impl.RowDescriptionImpl <em>Row
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.RowDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getRowDescription()
     */
    int ROW_DESCRIPTION = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__NAME = TABLE_ELEMENT_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__DOMAIN_TYPE = TABLE_ELEMENT_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = TABLE_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__PRECONDITION_EXPRESSION = TABLE_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Header Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Header Icon Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__HEADER_ICON_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Header Index Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Initial Height Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Is Resizable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Row Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION_FEATURE_COUNT = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of operations of the '<em>Row Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION_OPERATION_COUNT = TABLE_ELEMENT_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.impl.CellDescriptionImpl <em>Cell
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.CellDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getCellDescription()
     */
    int CELL_DESCRIPTION = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__NAME = TABLE_ELEMENT_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__DOMAIN_TYPE = TABLE_ELEMENT_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = TABLE_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__PRECONDITION_EXPRESSION = TABLE_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__VALUE_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Tooltip Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__TOOLTIP_EXPRESSION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Cell Widget Description</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Cell Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION_FEATURE_COUNT = TABLE_ELEMENT_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Cell Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION_OPERATION_COUNT = TABLE_ELEMENT_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.CellWidgetDescription <em>Cell Widget
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellWidgetDescription
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getCellWidgetDescription()
     */
    int CELL_WIDGET_DESCRIPTION = 5;

    /**
     * The number of structural features of the '<em>Cell Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_WIDGET_DESCRIPTION_FEATURE_COUNT = 0;

    /**
     * The number of operations of the '<em>Cell Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_WIDGET_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.table.impl.CellTextfieldWidgetDescriptionImpl <em>Cell Textfield
     * Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.CellTextfieldWidgetDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getCellTextfieldWidgetDescription()
     */
    int CELL_TEXTFIELD_WIDGET_DESCRIPTION = 6;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_TEXTFIELD_WIDGET_DESCRIPTION__BODY = CELL_WIDGET_DESCRIPTION_FEATURE_COUNT;

    /**
     * The number of structural features of the '<em>Cell Textfield Widget Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_TEXTFIELD_WIDGET_DESCRIPTION_FEATURE_COUNT = CELL_WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Cell Textfield Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_TEXTFIELD_WIDGET_DESCRIPTION_OPERATION_COUNT = CELL_WIDGET_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.impl.CellLabelWidgetDescriptionImpl
     * <em>Cell Label Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.CellLabelWidgetDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getCellLabelWidgetDescription()
     */
    int CELL_LABEL_WIDGET_DESCRIPTION = 7;

    /**
     * The feature id for the '<em><b>Icon Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_LABEL_WIDGET_DESCRIPTION__ICON_EXPRESSION = CELL_WIDGET_DESCRIPTION_FEATURE_COUNT;

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    TablePackage eINSTANCE = org.eclipse.sirius.components.view.table.impl.TablePackageImpl.init();

    /**
     * The number of structural features of the '<em>Cell Label Widget Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_LABEL_WIDGET_DESCRIPTION_FEATURE_COUNT = CELL_WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Cell Label Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_LABEL_WIDGET_DESCRIPTION_OPERATION_COUNT = CELL_WIDGET_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.table.impl.CellTextareaWidgetDescriptionImpl <em>Cell Textarea Widget
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.CellTextareaWidgetDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getCellTextareaWidgetDescription()
     */
    int CELL_TEXTAREA_WIDGET_DESCRIPTION = 8;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_TEXTAREA_WIDGET_DESCRIPTION__BODY = CELL_WIDGET_DESCRIPTION_FEATURE_COUNT;

    /**
     * The number of structural features of the '<em>Cell Textarea Widget Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_TEXTAREA_WIDGET_DESCRIPTION_FEATURE_COUNT = CELL_WIDGET_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Cell Textarea Widget Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_TEXTAREA_WIDGET_DESCRIPTION_OPERATION_COUNT = CELL_WIDGET_DESCRIPTION_OPERATION_COUNT;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.table.TableDescription
     * <em>Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableDescription
     */
    EClass getTableDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.TableDescription#getUseStripedRowsExpression <em>Use Striped
     * Rows Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Use Striped Rows Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableDescription#getUseStripedRowsExpression()
     * @see #getTableDescription()
     */
    EAttribute getTableDescription_UseStripedRowsExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.table.TableDescription#getColumnDescriptions <em>Column
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Column Descriptions</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableDescription#getColumnDescriptions()
     * @see #getTableDescription()
     */
    EReference getTableDescription_ColumnDescriptions();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.table.TableDescription#getRowDescription <em>Row Description</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Row Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableDescription#getRowDescription()
     * @see #getTableDescription()
     */
    EReference getTableDescription_RowDescription();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.table.TableDescription#getCellDescriptions <em>Cell
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Cell Descriptions</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableDescription#getCellDescriptions()
     * @see #getTableDescription()
     */
    EReference getTableDescription_CellDescriptions();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.table.TableElementDescription
     * <em>Element Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Element Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableElementDescription
     */
    EClass getTableElementDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.TableElementDescription#getName <em>Name</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableElementDescription#getName()
     * @see #getTableElementDescription()
     */
    EAttribute getTableElementDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.TableElementDescription#getDomainType <em>Domain Type</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Domain Type</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableElementDescription#getDomainType()
     * @see #getTableElementDescription()
     */
    EAttribute getTableElementDescription_DomainType();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.TableElementDescription#getSemanticCandidatesExpression
     * <em>Semantic Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableElementDescription#getSemanticCandidatesExpression()
     * @see #getTableElementDescription()
     */
    EAttribute getTableElementDescription_SemanticCandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.TableElementDescription#getPreconditionExpression
     * <em>Precondition Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableElementDescription#getPreconditionExpression()
     * @see #getTableElementDescription()
     */
    EAttribute getTableElementDescription_PreconditionExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.table.ColumnDescription <em>Column
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Column Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.ColumnDescription
     */
    EClass getColumnDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getHeaderIndexLabelExpression <em>Header Index
     * Label Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Header Index Label Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.ColumnDescription#getHeaderIndexLabelExpression()
     * @see #getColumnDescription()
     */
    EAttribute getColumnDescription_HeaderIndexLabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getHeaderLabelExpression <em>Header Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Header Label Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.ColumnDescription#getHeaderLabelExpression()
     * @see #getColumnDescription()
     */
    EAttribute getColumnDescription_HeaderLabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getHeaderIconExpression <em>Header Icon
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Header Icon Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.ColumnDescription#getHeaderIconExpression()
     * @see #getColumnDescription()
     */
    EAttribute getColumnDescription_HeaderIconExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getInitialWidthExpression <em>Initial Width
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Initial Width Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.ColumnDescription#getInitialWidthExpression()
     * @see #getColumnDescription()
     */
    EAttribute getColumnDescription_InitialWidthExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getIsResizableExpression <em>Is Resizable
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Resizable Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.ColumnDescription#getIsResizableExpression()
     * @see #getColumnDescription()
     */
    EAttribute getColumnDescription_IsResizableExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getFilterWidgetExpression <em>Filter Widget
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Filter Widget Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.ColumnDescription#getFilterWidgetExpression()
     * @see #getColumnDescription()
     */
    EAttribute getColumnDescription_FilterWidgetExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.table.RowDescription <em>Row
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Row Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowDescription
     */
    EClass getRowDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowDescription#getHeaderLabelExpression <em>Header Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Header Label Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowDescription#getHeaderLabelExpression()
     * @see #getRowDescription()
     */
    EAttribute getRowDescription_HeaderLabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowDescription#getHeaderIconExpression <em>Header Icon
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Header Icon Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowDescription#getHeaderIconExpression()
     * @see #getRowDescription()
     */
    EAttribute getRowDescription_HeaderIconExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowDescription#getHeaderIndexLabelExpression <em>Header Index
     * Label Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Header Index Label Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowDescription#getHeaderIndexLabelExpression()
     * @see #getRowDescription()
     */
    EAttribute getRowDescription_HeaderIndexLabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowDescription#getInitialHeightExpression <em>Initial Height
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Initial Height Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowDescription#getInitialHeightExpression()
     * @see #getRowDescription()
     */
    EAttribute getRowDescription_InitialHeightExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowDescription#getIsResizableExpression <em>Is Resizable
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Resizable Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowDescription#getIsResizableExpression()
     * @see #getRowDescription()
     */
    EAttribute getRowDescription_IsResizableExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.table.CellDescription <em>Cell
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Cell Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellDescription
     */
    EClass getCellDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.CellDescription#getValueExpression <em>Value Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Value Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellDescription#getValueExpression()
     * @see #getCellDescription()
     */
    EAttribute getCellDescription_ValueExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.CellDescription#getTooltipExpression <em>Tooltip
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Tooltip Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellDescription#getTooltipExpression()
     * @see #getCellDescription()
     */
    EAttribute getCellDescription_TooltipExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.table.CellDescription#getCellWidgetDescription <em>Cell Widget
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Cell Widget Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellDescription#getCellWidgetDescription()
     * @see #getCellDescription()
     */
    EReference getCellDescription_CellWidgetDescription();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.table.CellWidgetDescription <em>Cell
     * Widget Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Cell Widget Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellWidgetDescription
     */
    EClass getCellWidgetDescription();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription
     * <em>Cell Textfield Widget Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Cell Textfield Widget Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription
     */
    EClass getCellTextfieldWidgetDescription();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription#getBody <em>Body</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription#getBody()
     * @see #getCellTextfieldWidgetDescription()
     */
    EReference getCellTextfieldWidgetDescription_Body();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.table.CellLabelWidgetDescription
     * <em>Cell Label Widget Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Cell Label Widget Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellLabelWidgetDescription
     */
    EClass getCellLabelWidgetDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.CellLabelWidgetDescription#getIconExpression <em>Icon
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Icon Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellLabelWidgetDescription#getIconExpression()
     * @see #getCellLabelWidgetDescription()
     */
    EAttribute getCellLabelWidgetDescription_IconExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription
     * <em>Cell Textarea Widget Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Cell Textarea Widget Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription
     */
    EClass getCellTextareaWidgetDescription();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription#getBody <em>Body</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription#getBody()
     * @see #getCellTextareaWidgetDescription()
     */
    EReference getCellTextareaWidgetDescription_Body();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    TableFactory getTableFactory();

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
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl
         * <em>Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.table.impl.TableDescriptionImpl
         * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getTableDescription()
         */
        EClass TABLE_DESCRIPTION = eINSTANCE.getTableDescription();

        /**
         * The meta object literal for the '<em><b>Use Striped Rows Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TABLE_DESCRIPTION__USE_STRIPED_ROWS_EXPRESSION = eINSTANCE.getTableDescription_UseStripedRowsExpression();

        /**
         * The meta object literal for the '<em><b>Column Descriptions</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TABLE_DESCRIPTION__COLUMN_DESCRIPTIONS = eINSTANCE.getTableDescription_ColumnDescriptions();

        /**
         * The meta object literal for the '<em><b>Row Description</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TABLE_DESCRIPTION__ROW_DESCRIPTION = eINSTANCE.getTableDescription_RowDescription();

        /**
         * The meta object literal for the '<em><b>Cell Descriptions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TABLE_DESCRIPTION__CELL_DESCRIPTIONS = eINSTANCE.getTableDescription_CellDescriptions();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.table.impl.TableElementDescriptionImpl <em>Element
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.table.impl.TableElementDescriptionImpl
         * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getTableElementDescription()
         */
        EClass TABLE_ELEMENT_DESCRIPTION = eINSTANCE.getTableElementDescription();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute TABLE_ELEMENT_DESCRIPTION__NAME = eINSTANCE.getTableElementDescription_Name();

        /**
         * The meta object literal for the '<em><b>Domain Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute TABLE_ELEMENT_DESCRIPTION__DOMAIN_TYPE = eINSTANCE.getTableElementDescription_DomainType();

        /**
         * The meta object literal for the '<em><b>Semantic Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TABLE_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = eINSTANCE.getTableElementDescription_SemanticCandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TABLE_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION = eINSTANCE.getTableElementDescription_PreconditionExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.table.impl.ColumnDescriptionImpl
         * <em>Column Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.table.impl.ColumnDescriptionImpl
         * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getColumnDescription()
         */
        EClass COLUMN_DESCRIPTION = eINSTANCE.getColumnDescription();

        /**
         * The meta object literal for the '<em><b>Header Index Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION = eINSTANCE.getColumnDescription_HeaderIndexLabelExpression();

        /**
         * The meta object literal for the '<em><b>Header Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION = eINSTANCE.getColumnDescription_HeaderLabelExpression();

        /**
         * The meta object literal for the '<em><b>Header Icon Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION = eINSTANCE.getColumnDescription_HeaderIconExpression();

        /**
         * The meta object literal for the '<em><b>Initial Width Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION = eINSTANCE.getColumnDescription_InitialWidthExpression();

        /**
         * The meta object literal for the '<em><b>Is Resizable Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION = eINSTANCE.getColumnDescription_IsResizableExpression();

        /**
         * The meta object literal for the '<em><b>Filter Widget Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION = eINSTANCE.getColumnDescription_FilterWidgetExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.table.impl.RowDescriptionImpl
         * <em>Row Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.table.impl.RowDescriptionImpl
         * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getRowDescription()
         */
        EClass ROW_DESCRIPTION = eINSTANCE.getRowDescription();

        /**
         * The meta object literal for the '<em><b>Header Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION = eINSTANCE.getRowDescription_HeaderLabelExpression();

        /**
         * The meta object literal for the '<em><b>Header Icon Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_DESCRIPTION__HEADER_ICON_EXPRESSION = eINSTANCE.getRowDescription_HeaderIconExpression();

        /**
         * The meta object literal for the '<em><b>Header Index Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION = eINSTANCE.getRowDescription_HeaderIndexLabelExpression();

        /**
         * The meta object literal for the '<em><b>Initial Height Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION = eINSTANCE.getRowDescription_InitialHeightExpression();

        /**
         * The meta object literal for the '<em><b>Is Resizable Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION = eINSTANCE.getRowDescription_IsResizableExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.table.impl.CellDescriptionImpl
         * <em>Cell Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.table.impl.CellDescriptionImpl
         * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getCellDescription()
         */
        EClass CELL_DESCRIPTION = eINSTANCE.getCellDescription();

        /**
         * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CELL_DESCRIPTION__VALUE_EXPRESSION = eINSTANCE.getCellDescription_ValueExpression();

        /**
         * The meta object literal for the '<em><b>Tooltip Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CELL_DESCRIPTION__TOOLTIP_EXPRESSION = eINSTANCE.getCellDescription_TooltipExpression();

        /**
         * The meta object literal for the '<em><b>Cell Widget Description</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION = eINSTANCE.getCellDescription_CellWidgetDescription();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.table.CellWidgetDescription
         * <em>Cell Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.table.CellWidgetDescription
         * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getCellWidgetDescription()
         */
        EClass CELL_WIDGET_DESCRIPTION = eINSTANCE.getCellWidgetDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.table.impl.CellTextfieldWidgetDescriptionImpl <em>Cell Textfield
         * Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.table.impl.CellTextfieldWidgetDescriptionImpl
         * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getCellTextfieldWidgetDescription()
         */
        EClass CELL_TEXTFIELD_WIDGET_DESCRIPTION = eINSTANCE.getCellTextfieldWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CELL_TEXTFIELD_WIDGET_DESCRIPTION__BODY = eINSTANCE.getCellTextfieldWidgetDescription_Body();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.table.impl.CellLabelWidgetDescriptionImpl <em>Cell Label Widget
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.table.impl.CellLabelWidgetDescriptionImpl
         * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getCellLabelWidgetDescription()
         */
        EClass CELL_LABEL_WIDGET_DESCRIPTION = eINSTANCE.getCellLabelWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Icon Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CELL_LABEL_WIDGET_DESCRIPTION__ICON_EXPRESSION = eINSTANCE.getCellLabelWidgetDescription_IconExpression();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.table.impl.CellTextareaWidgetDescriptionImpl <em>Cell Textarea
         * Widget Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.table.impl.CellTextareaWidgetDescriptionImpl
         * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getCellTextareaWidgetDescription()
         */
        EClass CELL_TEXTAREA_WIDGET_DESCRIPTION = eINSTANCE.getCellTextareaWidgetDescription();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CELL_TEXTAREA_WIDGET_DESCRIPTION__BODY = eINSTANCE.getCellTextareaWidgetDescription_Body();

    }

} // TablePackage
