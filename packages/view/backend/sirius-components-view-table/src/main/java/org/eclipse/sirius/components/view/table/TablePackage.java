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
     * The feature id for the '<em><b>Enable Sub Rows</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__ENABLE_SUB_ROWS = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Row Filters</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__ROW_FILTERS = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Page Size Options Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__PAGE_SIZE_OPTIONS_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Default Page Size Index Expression</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION__DEFAULT_PAGE_SIZE_INDEX_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION_FEATURE_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 8;

    /**
     * The number of operations of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TABLE_DESCRIPTION_OPERATION_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.impl.ColumnDescriptionImpl <em>Column
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.ColumnDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getColumnDescription()
     */
    int COLUMN_DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__DOMAIN_TYPE = 1;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION = 3;

    /**
     * The feature id for the '<em><b>Header Index Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION = 4;

    /**
     * The feature id for the '<em><b>Header Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__HEADER_LABEL_EXPRESSION = 5;

    /**
     * The feature id for the '<em><b>Header Icon Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__HEADER_ICON_EXPRESSION = 6;

    /**
     * The feature id for the '<em><b>Initial Width Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__INITIAL_WIDTH_EXPRESSION = 7;

    /**
     * The feature id for the '<em><b>Is Resizable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__IS_RESIZABLE_EXPRESSION = 8;

    /**
     * The feature id for the '<em><b>Filter Widget Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__FILTER_WIDGET_EXPRESSION = 9;

    /**
     * The feature id for the '<em><b>Is Sortable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION__IS_SORTABLE_EXPRESSION = 10;

    /**
     * The number of structural features of the '<em>Column Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION_FEATURE_COUNT = 11;

    /**
     * The number of operations of the '<em>Column Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int COLUMN_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.impl.RowDescriptionImpl <em>Row
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.RowDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getRowDescription()
     */
    int ROW_DESCRIPTION = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Header Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__HEADER_LABEL_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Header Icon Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__HEADER_ICON_EXPRESSION = 3;

    /**
     * The feature id for the '<em><b>Header Index Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__HEADER_INDEX_LABEL_EXPRESSION = 4;

    /**
     * The feature id for the '<em><b>Initial Height Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__INITIAL_HEIGHT_EXPRESSION = 5;

    /**
     * The feature id for the '<em><b>Is Resizable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__IS_RESIZABLE_EXPRESSION = 6;

    /**
     * The feature id for the '<em><b>Context Menu Entries</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES = 7;

    /**
     * The feature id for the '<em><b>Depth Level Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__DEPTH_LEVEL_EXPRESSION = 8;

    /**
     * The feature id for the '<em><b>Has Children Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION__HAS_CHILDREN_EXPRESSION = 9;

    /**
     * The number of structural features of the '<em>Row Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION_FEATURE_COUNT = 10;

    /**
     * The number of operations of the '<em>Row Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.impl.CellDescriptionImpl <em>Cell
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.CellDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getCellDescription()
     */
    int CELL_DESCRIPTION = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__PRECONDITION_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Selected Target Object Expression</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__SELECTED_TARGET_OBJECT_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__VALUE_EXPRESSION = 3;

    /**
     * The feature id for the '<em><b>Tooltip Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__TOOLTIP_EXPRESSION = 4;

    /**
     * The feature id for the '<em><b>Cell Widget Description</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION__CELL_WIDGET_DESCRIPTION = 5;

    /**
     * The number of structural features of the '<em>Cell Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION_FEATURE_COUNT = 6;

    /**
     * The number of operations of the '<em>Cell Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.CellWidgetDescription <em>Cell Widget
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellWidgetDescription
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getCellWidgetDescription()
     */
    int CELL_WIDGET_DESCRIPTION = 4;

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
    int CELL_TEXTFIELD_WIDGET_DESCRIPTION = 5;

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
    int CELL_LABEL_WIDGET_DESCRIPTION = 6;

    /**
     * The feature id for the '<em><b>Icon Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CELL_LABEL_WIDGET_DESCRIPTION__ICON_EXPRESSION = CELL_WIDGET_DESCRIPTION_FEATURE_COUNT;

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
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.impl.RowContextMenuEntryImpl <em>Row
     * Context Menu Entry</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.RowContextMenuEntryImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getRowContextMenuEntry()
     */
    int ROW_CONTEXT_MENU_ENTRY = 7;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_CONTEXT_MENU_ENTRY__NAME = 0;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ROW_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Icon URL Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ROW_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION = 3;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_CONTEXT_MENU_ENTRY__BODY = 4;

    /**
     * The number of structural features of the '<em>Row Context Menu Entry</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_CONTEXT_MENU_ENTRY_FEATURE_COUNT = 5;

    /**
     * The number of operations of the '<em>Row Context Menu Entry</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_CONTEXT_MENU_ENTRY_OPERATION_COUNT = 0;

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
     * The meta object id for the '{@link org.eclipse.sirius.components.view.table.impl.RowFilterDescriptionImpl <em>Row
     * Filter Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.table.impl.RowFilterDescriptionImpl
     * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getRowFilterDescription()
     */
    int ROW_FILTER_DESCRIPTION = 9;
    /**
     * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_FILTER_DESCRIPTION__ID = 0;
    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION = 1;
    /**
     * The feature id for the '<em><b>Initial State Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION = 2;
    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    TablePackage eINSTANCE = org.eclipse.sirius.components.view.table.impl.TablePackageImpl.init();
    /**
     * The number of structural features of the '<em>Row Filter Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_FILTER_DESCRIPTION_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Row Filter Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ROW_FILTER_DESCRIPTION_OPERATION_COUNT = 0;

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
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.TableDescription#isEnableSubRows <em>Enable Sub Rows</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Enable Sub Rows</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableDescription#isEnableSubRows()
     * @see #getTableDescription()
     */
    EAttribute getTableDescription_EnableSubRows();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.table.TableDescription#getRowFilters <em>Row Filters</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Row Filters</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableDescription#getRowFilters()
     * @see #getTableDescription()
     */
    EReference getTableDescription_RowFilters();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.TableDescription#getPageSizeOptionsExpression <em>Page Size
     * Options Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Page Size Options Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableDescription#getPageSizeOptionsExpression()
     * @see #getTableDescription()
     */
    EAttribute getTableDescription_PageSizeOptionsExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.TableDescription#getDefaultPageSizeIndexExpression <em>Default
     * Page Size Index Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Default Page Size Index Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.TableDescription#getDefaultPageSizeIndexExpression()
     * @see #getTableDescription()
     */
    EAttribute getTableDescription_DefaultPageSizeIndexExpression();

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
     * '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getName <em>Name</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.ColumnDescription#getName()
     * @see #getColumnDescription()
     */
    EAttribute getColumnDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getDomainType <em>Domain Type</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Domain Type</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.ColumnDescription#getDomainType()
     * @see #getColumnDescription()
     */
    EAttribute getColumnDescription_DomainType();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getSemanticCandidatesExpression <em>Semantic
     * Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.ColumnDescription#getSemanticCandidatesExpression()
     * @see #getColumnDescription()
     */
    EAttribute getColumnDescription_SemanticCandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getPreconditionExpression <em>Precondition
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.ColumnDescription#getPreconditionExpression()
     * @see #getColumnDescription()
     */
    EAttribute getColumnDescription_PreconditionExpression();

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
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.ColumnDescription#getIsSortableExpression <em>Is Sortable
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Sortable Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.ColumnDescription#getIsSortableExpression()
     * @see #getColumnDescription()
     */
    EAttribute getColumnDescription_IsSortableExpression();

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
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.table.RowDescription#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowDescription#getName()
     * @see #getRowDescription()
     */
    EAttribute getRowDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowDescription#getSemanticCandidatesExpression <em>Semantic
     * Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowDescription#getSemanticCandidatesExpression()
     * @see #getRowDescription()
     */
    EAttribute getRowDescription_SemanticCandidatesExpression();

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
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.table.RowDescription#getContextMenuEntries <em>Context Menu
     * Entries</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Context Menu Entries</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowDescription#getContextMenuEntries()
     * @see #getRowDescription()
     */
    EReference getRowDescription_ContextMenuEntries();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowDescription#getDepthLevelExpression <em>Depth Level
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Depth Level Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowDescription#getDepthLevelExpression()
     * @see #getRowDescription()
     */
    EAttribute getRowDescription_DepthLevelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowDescription#getHasChildrenExpression <em>Has Children
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Has Children Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowDescription#getHasChildrenExpression()
     * @see #getRowDescription()
     */
    EAttribute getRowDescription_HasChildrenExpression();

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
     * '{@link org.eclipse.sirius.components.view.table.CellDescription#getName <em>Name</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellDescription#getName()
     * @see #getCellDescription()
     */
    EAttribute getCellDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.CellDescription#getPreconditionExpression <em>Precondition
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellDescription#getPreconditionExpression()
     * @see #getCellDescription()
     */
    EAttribute getCellDescription_PreconditionExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.CellDescription#getSelectedTargetObjectExpression <em>Selected
     * Target Object Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Selected Target Object Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.CellDescription#getSelectedTargetObjectExpression()
     * @see #getCellDescription()
     */
    EAttribute getCellDescription_SelectedTargetObjectExpression();

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
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.table.RowFilterDescription <em>Row
     * Filter Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Row Filter Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowFilterDescription
     */
    EClass getRowFilterDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowFilterDescription#getId <em>Id</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Id</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowFilterDescription#getId()
     * @see #getRowFilterDescription()
     */
    EAttribute getRowFilterDescription_Id();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowFilterDescription#getLabelExpression <em>Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowFilterDescription#getLabelExpression()
     * @see #getRowFilterDescription()
     */
    EAttribute getRowFilterDescription_LabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowFilterDescription#getInitialStateExpression <em>Initial State
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Initial State Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowFilterDescription#getInitialStateExpression()
     * @see #getRowFilterDescription()
     */
    EAttribute getRowFilterDescription_InitialStateExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry <em>Row
     * Context Menu Entry</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Row Context Menu Entry</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowContextMenuEntry
     */
    EClass getRowContextMenuEntry();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getName <em>Name</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowContextMenuEntry#getName()
     * @see #getRowContextMenuEntry()
     */
    EAttribute getRowContextMenuEntry_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getLabelExpression <em>Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowContextMenuEntry#getLabelExpression()
     * @see #getRowContextMenuEntry()
     */
    EAttribute getRowContextMenuEntry_LabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getIconURLExpression <em>Icon URL
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Icon URL Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowContextMenuEntry#getIconURLExpression()
     * @see #getRowContextMenuEntry()
     */
    EAttribute getRowContextMenuEntry_IconURLExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getPreconditionExpression <em>Precondition
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowContextMenuEntry#getPreconditionExpression()
     * @see #getRowContextMenuEntry()
     */
    EAttribute getRowContextMenuEntry_PreconditionExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getBody <em>Body</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.table.RowContextMenuEntry#getBody()
     * @see #getRowContextMenuEntry()
     */
    EReference getRowContextMenuEntry_Body();

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
         * The meta object literal for the '<em><b>Enable Sub Rows</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TABLE_DESCRIPTION__ENABLE_SUB_ROWS = eINSTANCE.getTableDescription_EnableSubRows();

        /**
         * The meta object literal for the '<em><b>Row Filters</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TABLE_DESCRIPTION__ROW_FILTERS = eINSTANCE.getTableDescription_RowFilters();

        /**
         * The meta object literal for the '<em><b>Page Size Options Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TABLE_DESCRIPTION__PAGE_SIZE_OPTIONS_EXPRESSION = eINSTANCE.getTableDescription_PageSizeOptionsExpression();

        /**
         * The meta object literal for the '<em><b>Default Page Size Index Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TABLE_DESCRIPTION__DEFAULT_PAGE_SIZE_INDEX_EXPRESSION = eINSTANCE.getTableDescription_DefaultPageSizeIndexExpression();

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
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__NAME = eINSTANCE.getColumnDescription_Name();

        /**
         * The meta object literal for the '<em><b>Domain Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__DOMAIN_TYPE = eINSTANCE.getColumnDescription_DomainType();

        /**
         * The meta object literal for the '<em><b>Semantic Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = eINSTANCE.getColumnDescription_SemanticCandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__PRECONDITION_EXPRESSION = eINSTANCE.getColumnDescription_PreconditionExpression();

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
         * The meta object literal for the '<em><b>Is Sortable Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute COLUMN_DESCRIPTION__IS_SORTABLE_EXPRESSION = eINSTANCE.getColumnDescription_IsSortableExpression();

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
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_DESCRIPTION__NAME = eINSTANCE.getRowDescription_Name();

        /**
         * The meta object literal for the '<em><b>Semantic Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = eINSTANCE.getRowDescription_SemanticCandidatesExpression();

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
         * The meta object literal for the '<em><b>Context Menu Entries</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ROW_DESCRIPTION__CONTEXT_MENU_ENTRIES = eINSTANCE.getRowDescription_ContextMenuEntries();

        /**
         * The meta object literal for the '<em><b>Depth Level Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_DESCRIPTION__DEPTH_LEVEL_EXPRESSION = eINSTANCE.getRowDescription_DepthLevelExpression();

        /**
         * The meta object literal for the '<em><b>Has Children Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_DESCRIPTION__HAS_CHILDREN_EXPRESSION = eINSTANCE.getRowDescription_HasChildrenExpression();

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
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute CELL_DESCRIPTION__NAME = eINSTANCE.getCellDescription_Name();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CELL_DESCRIPTION__PRECONDITION_EXPRESSION = eINSTANCE.getCellDescription_PreconditionExpression();

        /**
         * The meta object literal for the '<em><b>Selected Target Object Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CELL_DESCRIPTION__SELECTED_TARGET_OBJECT_EXPRESSION = eINSTANCE.getCellDescription_SelectedTargetObjectExpression();

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

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.table.impl.RowFilterDescriptionImpl <em>Row Filter
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.table.impl.RowFilterDescriptionImpl
         * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getRowFilterDescription()
         */
        EClass ROW_FILTER_DESCRIPTION = eINSTANCE.getRowFilterDescription();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_FILTER_DESCRIPTION__ID = eINSTANCE.getRowFilterDescription_Id();

        /**
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_FILTER_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getRowFilterDescription_LabelExpression();

        /**
         * The meta object literal for the '<em><b>Initial State Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_FILTER_DESCRIPTION__INITIAL_STATE_EXPRESSION = eINSTANCE.getRowFilterDescription_InitialStateExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.table.impl.RowContextMenuEntryImpl
         * <em>Row Context Menu Entry</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.table.impl.RowContextMenuEntryImpl
         * @see org.eclipse.sirius.components.view.table.impl.TablePackageImpl#getRowContextMenuEntry()
         */
        EClass ROW_CONTEXT_MENU_ENTRY = eINSTANCE.getRowContextMenuEntry();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_CONTEXT_MENU_ENTRY__NAME = eINSTANCE.getRowContextMenuEntry_Name();

        /**
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION = eINSTANCE.getRowContextMenuEntry_LabelExpression();

        /**
         * The meta object literal for the '<em><b>Icon URL Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION = eINSTANCE.getRowContextMenuEntry_IconURLExpression();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute ROW_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION = eINSTANCE.getRowContextMenuEntry_PreconditionExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference ROW_CONTEXT_MENU_ENTRY__BODY = eINSTANCE.getRowContextMenuEntry_Body();

    }

} // TablePackage
