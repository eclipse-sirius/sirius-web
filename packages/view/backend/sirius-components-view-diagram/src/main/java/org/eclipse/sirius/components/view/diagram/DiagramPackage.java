/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see org.eclipse.sirius.components.view.diagram.DiagramFactory
 */
public interface DiagramPackage extends EPackage {

    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "diagram";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/diagram";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "diagram";

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl
     * <em>Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramDescription()
     */
    int DIAGRAM_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__NAME = ViewPackage.REPRESENTATION_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__DOMAIN_TYPE = ViewPackage.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__PRECONDITION_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__TITLE_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION;

    /**
     * The feature id for the '<em><b>Auto Layout</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__AUTO_LAYOUT = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__PALETTE = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Node Descriptions</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Edge Descriptions</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Arrange Layout Direction</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION_FEATURE_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The number of operations of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION_OPERATION_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramElementDescriptionImpl
     * <em>Element Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramElementDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramElementDescription()
     */
    int DIAGRAM_ELEMENT_DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE = 1;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION = 3;

    /**
     * The feature id for the '<em><b>Synchronization Policy</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY = 4;

    /**
     * The number of structural features of the '<em>Element Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT = 5;

    /**
     * The number of operations of the '<em>Element Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl <em>Node
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeDescription()
     */
    int NODE_DESCRIPTION = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__NAME = DIAGRAM_ELEMENT_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__DOMAIN_TYPE = DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__PRECONDITION_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Synchronization Policy</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__SYNCHRONIZATION_POLICY = DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY;

    /**
     * The feature id for the '<em><b>Collapsible</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__COLLAPSIBLE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__PALETTE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Children Layout Strategy</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__STYLE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__CONDITIONAL_STYLES = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Children Descriptions</b></em>' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Border Nodes Descriptions</b></em>' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Reused Child Node Descriptions</b></em>' reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Reused Border Node Descriptions</b></em>' reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>User Resizable</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__USER_RESIZABLE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Default Width Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Default Height Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Keep Aspect Ratio</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__KEEP_ASPECT_RATIO = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 12;

    /**
     * The feature id for the '<em><b>Is Collapsed By Default Expression</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__IS_COLLAPSED_BY_DEFAULT_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 13;

    /**
     * The feature id for the '<em><b>Inside Label</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__INSIDE_LABEL = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 14;

    /**
     * The feature id for the '<em><b>Outside Labels</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__OUTSIDE_LABELS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 15;

    /**
     * The feature id for the '<em><b>Is Hidden By Default Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__IS_HIDDEN_BY_DEFAULT_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 16;

    /**
     * The feature id for the '<em><b>Is Faded By Default Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__IS_FADED_BY_DEFAULT_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 17;

    /**
     * The number of structural features of the '<em>Node Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION_FEATURE_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 18;

    /**
     * The number of operations of the '<em>Node Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION_OPERATION_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl <em>Edge
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeDescription()
     */
    int EDGE_DESCRIPTION = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__NAME = DIAGRAM_ELEMENT_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__DOMAIN_TYPE = DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__PRECONDITION_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Synchronization Policy</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SYNCHRONIZATION_POLICY = DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY;

    /**
     * The feature id for the '<em><b>Begin Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Center Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__CENTER_LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>End Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__END_LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Is Domain Based Edge</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__PALETTE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Source Node Descriptions</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Target Node Descriptions</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Source Nodes Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Target Nodes Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__STYLE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__CONDITIONAL_STYLES = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 10;

    /**
     * The number of structural features of the '<em>Edge Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION_FEATURE_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 11;

    /**
     * The number of operations of the '<em>Edge Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION_OPERATION_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription
     * <em>Layout Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLayoutStrategyDescription()
     */
    int LAYOUT_STRATEGY_DESCRIPTION = 4;

    /**
     * The number of structural features of the '<em>Layout Strategy Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT = 0;

    /**
     * The number of operations of the '<em>Layout Strategy Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl <em>List Layout
     * Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getListLayoutStrategyDescription()
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION = 5;

    /**
     * The feature id for the '<em><b>Are Child Nodes Draggable Expression</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION = LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Top Gap Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION = LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bottom Gap Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION = LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Growable Nodes</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES = LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>List Layout Strategy Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT = LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>List Layout Strategy Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT = LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl <em>Free Form
     * Layout Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getFreeFormLayoutStrategyDescription()
     */
    int FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION = 6;

    /**
     * The number of structural features of the '<em>Free Form Layout Strategy Description</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT = LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT;

    /**
     * The number of operations of the '<em>Free Form Layout Strategy Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT = LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.LabelDescriptionImpl <em>Label
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.LabelDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLabelDescription()
     */
    int LABEL_DESCRIPTION = 7;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__LABEL_EXPRESSION = 0;

    /**
     * The feature id for the '<em><b>Overflow Strategy</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__OVERFLOW_STRATEGY = 1;

    /**
     * The feature id for the '<em><b>Text Align</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION__TEXT_ALIGN = 2;

    /**
     * The number of structural features of the '<em>Label Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Label Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelDescriptionImpl
     * <em>Inside Label Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.InsideLabelDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getInsideLabelDescription()
     */
    int INSIDE_LABEL_DESCRIPTION = 8;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_DESCRIPTION__LABEL_EXPRESSION = LABEL_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Overflow Strategy</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_DESCRIPTION__OVERFLOW_STRATEGY = LABEL_DESCRIPTION__OVERFLOW_STRATEGY;

    /**
     * The feature id for the '<em><b>Text Align</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_DESCRIPTION__TEXT_ALIGN = LABEL_DESCRIPTION__TEXT_ALIGN;

    /**
     * The feature id for the '<em><b>Position</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_DESCRIPTION__POSITION = LABEL_DESCRIPTION_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_DESCRIPTION__STYLE = LABEL_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES = LABEL_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Inside Label Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_DESCRIPTION_FEATURE_COUNT = LABEL_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Inside Label Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_DESCRIPTION_OPERATION_COUNT = LABEL_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.OutsideLabelDescriptionImpl
     * <em>Outside Label Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.OutsideLabelDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getOutsideLabelDescription()
     */
    int OUTSIDE_LABEL_DESCRIPTION = 9;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_DESCRIPTION__LABEL_EXPRESSION = LABEL_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Overflow Strategy</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_DESCRIPTION__OVERFLOW_STRATEGY = LABEL_DESCRIPTION__OVERFLOW_STRATEGY;

    /**
     * The feature id for the '<em><b>Text Align</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_DESCRIPTION__TEXT_ALIGN = LABEL_DESCRIPTION__TEXT_ALIGN;

    /**
     * The feature id for the '<em><b>Position</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_DESCRIPTION__POSITION = LABEL_DESCRIPTION_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_DESCRIPTION__STYLE = LABEL_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES = LABEL_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Outside Label Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_DESCRIPTION_FEATURE_COUNT = LABEL_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The number of operations of the '<em>Outside Label Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_DESCRIPTION_OPERATION_COUNT = LABEL_DESCRIPTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.StyleImpl <em>Style</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.StyleImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getStyle()
     */
    int STYLE = 10;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int STYLE__COLOR = 0;

    /**
     * The number of structural features of the '<em>Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int STYLE_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int STYLE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.BorderStyle <em>Border Style</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getBorderStyle()
     */
    int BORDER_STYLE = 11;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BORDER_STYLE__BORDER_COLOR = 0;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BORDER_STYLE__BORDER_RADIUS = 1;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BORDER_STYLE__BORDER_SIZE = 2;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BORDER_STYLE__BORDER_LINE_STYLE = 3;

    /**
     * The number of structural features of the '<em>Border Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int BORDER_STYLE_FEATURE_COUNT = 4;

    /**
     * The number of operations of the '<em>Border Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int BORDER_STYLE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle <em>Node Label
     * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeLabelStyle
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeLabelStyle()
     */
    int NODE_LABEL_STYLE = 14;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__FONT_SIZE = ViewPackage.LABEL_STYLE__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__ITALIC = ViewPackage.LABEL_STYLE__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__BOLD = ViewPackage.LABEL_STYLE__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__UNDERLINE = ViewPackage.LABEL_STYLE__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__STRIKE_THROUGH = ViewPackage.LABEL_STYLE__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__BORDER_COLOR = ViewPackage.LABEL_STYLE_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__BORDER_RADIUS = ViewPackage.LABEL_STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__BORDER_SIZE = ViewPackage.LABEL_STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__BORDER_LINE_STYLE = ViewPackage.LABEL_STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__LABEL_COLOR = ViewPackage.LABEL_STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__BACKGROUND = ViewPackage.LABEL_STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__SHOW_ICON = ViewPackage.LABEL_STYLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE__LABEL_ICON = ViewPackage.LABEL_STYLE_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Node Label Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE_FEATURE_COUNT = ViewPackage.LABEL_STYLE_FEATURE_COUNT + 8;

    /**
     * The number of operations of the '<em>Node Label Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_LABEL_STYLE_OPERATION_COUNT = ViewPackage.LABEL_STYLE_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl
     * <em>Inside Label Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getInsideLabelStyle()
     */
    int INSIDE_LABEL_STYLE = 12;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__FONT_SIZE = NODE_LABEL_STYLE__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__ITALIC = NODE_LABEL_STYLE__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__BOLD = NODE_LABEL_STYLE__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__UNDERLINE = NODE_LABEL_STYLE__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__STRIKE_THROUGH = NODE_LABEL_STYLE__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__BORDER_COLOR = NODE_LABEL_STYLE__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__BORDER_RADIUS = NODE_LABEL_STYLE__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__BORDER_SIZE = NODE_LABEL_STYLE__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__BORDER_LINE_STYLE = NODE_LABEL_STYLE__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__LABEL_COLOR = NODE_LABEL_STYLE__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__BACKGROUND = NODE_LABEL_STYLE__BACKGROUND;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__SHOW_ICON = NODE_LABEL_STYLE__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__LABEL_ICON = NODE_LABEL_STYLE__LABEL_ICON;

    /**
     * The feature id for the '<em><b>With Header</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__WITH_HEADER = NODE_LABEL_STYLE_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Display Header Separator</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE__DISPLAY_HEADER_SEPARATOR = NODE_LABEL_STYLE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Inside Label Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE_FEATURE_COUNT = NODE_LABEL_STYLE_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Inside Label Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int INSIDE_LABEL_STYLE_OPERATION_COUNT = NODE_LABEL_STYLE_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.OutsideLabelStyleImpl
     * <em>Outside Label Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.OutsideLabelStyleImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getOutsideLabelStyle()
     */
    int OUTSIDE_LABEL_STYLE = 13;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__FONT_SIZE = NODE_LABEL_STYLE__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__ITALIC = NODE_LABEL_STYLE__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__BOLD = NODE_LABEL_STYLE__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__UNDERLINE = NODE_LABEL_STYLE__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__STRIKE_THROUGH = NODE_LABEL_STYLE__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__BORDER_COLOR = NODE_LABEL_STYLE__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__BORDER_RADIUS = NODE_LABEL_STYLE__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__BORDER_SIZE = NODE_LABEL_STYLE__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__BORDER_LINE_STYLE = NODE_LABEL_STYLE__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__LABEL_COLOR = NODE_LABEL_STYLE__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__BACKGROUND = NODE_LABEL_STYLE__BACKGROUND;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__SHOW_ICON = NODE_LABEL_STYLE__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE__LABEL_ICON = NODE_LABEL_STYLE__LABEL_ICON;

    /**
     * The number of structural features of the '<em>Outside Label Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE_FEATURE_COUNT = NODE_LABEL_STYLE_FEATURE_COUNT;

    /**
     * The number of operations of the '<em>Outside Label Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int OUTSIDE_LABEL_STYLE_OPERATION_COUNT = NODE_LABEL_STYLE_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription <em>Node Style
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeStyleDescription
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeStyleDescription()
     */
    int NODE_STYLE_DESCRIPTION = 15;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BORDER_COLOR = BORDER_STYLE__BORDER_COLOR;
    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BORDER_COLOR = NODE_STYLE_DESCRIPTION__BORDER_COLOR;
    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR = NODE_STYLE_DESCRIPTION__BORDER_COLOR;
    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_COLOR = NODE_STYLE_DESCRIPTION__BORDER_COLOR;
    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BORDER_RADIUS = BORDER_STYLE__BORDER_RADIUS;
    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = NODE_STYLE_DESCRIPTION__BORDER_RADIUS;
    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = NODE_STYLE_DESCRIPTION__BORDER_RADIUS;
    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = NODE_STYLE_DESCRIPTION__BORDER_RADIUS;
    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BORDER_SIZE = BORDER_STYLE__BORDER_SIZE;
    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BORDER_SIZE = NODE_STYLE_DESCRIPTION__BORDER_SIZE;
    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE = NODE_STYLE_DESCRIPTION__BORDER_SIZE;
    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_SIZE = NODE_STYLE_DESCRIPTION__BORDER_SIZE;
    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = BORDER_STYLE__BORDER_LINE_STYLE;
    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;
    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;
    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;
    /**
     * The number of structural features of the '<em>Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION_FEATURE_COUNT = BORDER_STYLE_FEATURE_COUNT;
    /**
     * The feature id for the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BACKGROUND = NODE_STYLE_DESCRIPTION_FEATURE_COUNT;
    /**
     * The number of structural features of the '<em>Rectangular Node Style Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 1;
    /**
     * The feature id for the '<em><b>Shape</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__SHAPE = NODE_STYLE_DESCRIPTION_FEATURE_COUNT;
    /**
     * The feature id for the '<em><b>Position Dependent Rotation</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 1;
    /**
     * The number of structural features of the '<em>Image Node Style Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 2;
    /**
     * The feature id for the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BACKGROUND = NODE_STYLE_DESCRIPTION_FEATURE_COUNT;
    /**
     * The number of structural features of the '<em>Icon Label Node Style Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 1;
    /**
     * The number of operations of the '<em>Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION_OPERATION_COUNT = BORDER_STYLE_OPERATION_COUNT;
    /**
     * The number of operations of the '<em>Rectangular Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = NODE_STYLE_DESCRIPTION_OPERATION_COUNT;
    /**
     * The number of operations of the '<em>Image Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = NODE_STYLE_DESCRIPTION_OPERATION_COUNT;
    /**
     * The number of operations of the '<em>Icon Label Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = NODE_STYLE_DESCRIPTION_OPERATION_COUNT;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalNodeStyleImpl
     * <em>Conditional Node Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.ConditionalNodeStyleImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getConditionalNodeStyle()
     */
    int CONDITIONAL_NODE_STYLE = 16;
    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;
    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE__STYLE = ViewPackage.CONDITIONAL_FEATURE_COUNT;
    /**
     * The number of structural features of the '<em>Conditional Node Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;
    /**
     * The number of operations of the '<em>Conditional Node Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_NODE_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT;
    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalInsideLabelStyleImpl <em>Conditional Inside
     * Label Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.ConditionalInsideLabelStyleImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getConditionalInsideLabelStyle()
     */
    int CONDITIONAL_INSIDE_LABEL_STYLE = 17;
    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_INSIDE_LABEL_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;
    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_INSIDE_LABEL_STYLE__STYLE = ViewPackage.CONDITIONAL_FEATURE_COUNT;
    /**
     * The number of structural features of the '<em>Conditional Inside Label Style</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_INSIDE_LABEL_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;
    /**
     * The number of operations of the '<em>Conditional Inside Label Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_INSIDE_LABEL_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT;
    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalOutsideLabelStyleImpl <em>Conditional Outside
     * Label Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.ConditionalOutsideLabelStyleImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getConditionalOutsideLabelStyle()
     */
    int CONDITIONAL_OUTSIDE_LABEL_STYLE = 18;
    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_OUTSIDE_LABEL_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;
    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_OUTSIDE_LABEL_STYLE__STYLE = ViewPackage.CONDITIONAL_FEATURE_COUNT;
    /**
     * The number of structural features of the '<em>Conditional Outside Label Style</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_OUTSIDE_LABEL_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;
    /**
     * The number of operations of the '<em>Conditional Outside Label Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_OUTSIDE_LABEL_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT;
    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.RectangularNodeStyleDescriptionImpl <em>Rectangular Node
     * Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.RectangularNodeStyleDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getRectangularNodeStyleDescription()
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION = 19;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl
     * <em>Image Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getImageNodeStyleDescription()
     */
    int IMAGE_NODE_STYLE_DESCRIPTION = 20;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl <em>Icon Label Node
     * Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getIconLabelNodeStyleDescription()
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION = 21;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl <em>Edge
     * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeStyle()
     */
    int EDGE_STYLE = 22;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__COLOR = STYLE__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__FONT_SIZE = STYLE_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__ITALIC = STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__BOLD = STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__UNDERLINE = STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__STRIKE_THROUGH = STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__BORDER_COLOR = STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__BORDER_RADIUS = STYLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__BORDER_SIZE = STYLE_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__BORDER_LINE_STYLE = STYLE_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__LINE_STYLE = STYLE_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Source Arrow Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__SOURCE_ARROW_STYLE = STYLE_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Target Arrow Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__TARGET_ARROW_STYLE = STYLE_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Edge Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__EDGE_WIDTH = STYLE_FEATURE_COUNT + 12;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__SHOW_ICON = STYLE_FEATURE_COUNT + 13;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__LABEL_ICON = STYLE_FEATURE_COUNT + 14;

    /**
     * The feature id for the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__BACKGROUND = STYLE_FEATURE_COUNT + 15;

    /**
     * The number of structural features of the '<em>Edge Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE_FEATURE_COUNT = STYLE_FEATURE_COUNT + 16;

    /**
     * The number of operations of the '<em>Edge Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE_OPERATION_COUNT = STYLE_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl
     * <em>Conditional Edge Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getConditionalEdgeStyle()
     */
    int CONDITIONAL_EDGE_STYLE = 23;

    /**
     * The feature id for the '<em><b>Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__CONDITION = ViewPackage.CONDITIONAL__CONDITION;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__FONT_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__ITALIC = ViewPackage.CONDITIONAL_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__BOLD = ViewPackage.CONDITIONAL_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__UNDERLINE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__STRIKE_THROUGH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__BORDER_COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__BORDER_RADIUS = ViewPackage.CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__BORDER_SIZE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__BORDER_LINE_STYLE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__LINE_STYLE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Source Arrow Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__SOURCE_ARROW_STYLE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Target Arrow Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__TARGET_ARROW_STYLE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 12;

    /**
     * The feature id for the '<em><b>Edge Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__EDGE_WIDTH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 13;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__SHOW_ICON = ViewPackage.CONDITIONAL_FEATURE_COUNT + 14;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__LABEL_ICON = ViewPackage.CONDITIONAL_FEATURE_COUNT + 15;

    /**
     * The feature id for the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__BACKGROUND = ViewPackage.CONDITIONAL_FEATURE_COUNT + 16;

    /**
     * The number of structural features of the '<em>Conditional Edge Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 17;

    /**
     * The number of operations of the '<em>Conditional Edge Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramPaletteImpl
     * <em>Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPaletteImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramPalette()
     */
    int DIAGRAM_PALETTE = 24;

    /**
     * The feature id for the '<em><b>Drop Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE__DROP_TOOL = 0;

    /**
     * The feature id for the '<em><b>Drop Node Tool</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE__DROP_NODE_TOOL = 1;

    /**
     * The feature id for the '<em><b>Node Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE__NODE_TOOLS = 2;

    /**
     * The feature id for the '<em><b>Tool Sections</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE__TOOL_SECTIONS = 3;

    /**
     * The number of structural features of the '<em>Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE_FEATURE_COUNT = 4;

    /**
     * The number of operations of the '<em>Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.NodePaletteImpl <em>Node
     * Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.NodePaletteImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodePalette()
     */
    int NODE_PALETTE = 25;

    /**
     * The feature id for the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__DELETE_TOOL = 0;

    /**
     * The feature id for the '<em><b>Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__LABEL_EDIT_TOOL = 1;

    /**
     * The feature id for the '<em><b>Drop Node Tool</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__DROP_NODE_TOOL = 2;

    /**
     * The feature id for the '<em><b>Node Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__NODE_TOOLS = 3;

    /**
     * The feature id for the '<em><b>Edge Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__EDGE_TOOLS = 4;

    /**
     * The feature id for the '<em><b>Tool Sections</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__TOOL_SECTIONS = 5;

    /**
     * The number of structural features of the '<em>Node Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE_FEATURE_COUNT = 6;

    /**
     * The number of operations of the '<em>Node Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl <em>Edge
     * Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgePalette()
     */
    int EDGE_PALETTE = 26;

    /**
     * The feature id for the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__DELETE_TOOL = 0;

    /**
     * The feature id for the '<em><b>Center Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL = 1;

    /**
     * The feature id for the '<em><b>Begin Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL = 2;

    /**
     * The feature id for the '<em><b>End Label Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__END_LABEL_EDIT_TOOL = 3;

    /**
     * The feature id for the '<em><b>Node Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__NODE_TOOLS = 4;

    /**
     * The feature id for the '<em><b>Edge Reconnection Tools</b></em>' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__EDGE_RECONNECTION_TOOLS = 5;

    /**
     * The feature id for the '<em><b>Tool Sections</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE__TOOL_SECTIONS = 6;

    /**
     * The number of structural features of the '<em>Edge Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE_FEATURE_COUNT = 7;

    /**
     * The number of operations of the '<em>Edge Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_PALETTE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.ToolImpl <em>Tool</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.ToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getTool()
     */
    int TOOL = 27;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TOOL__NAME = 0;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TOOL__PRECONDITION_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TOOL__BODY = 2;

    /**
     * The number of structural features of the '<em>Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TOOL_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TOOL_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DeleteToolImpl <em>Delete
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.DeleteToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDeleteTool()
     */
    int DELETE_TOOL = 28;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TOOL__NAME = TOOL__NAME;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TOOL__PRECONDITION_EXPRESSION = TOOL__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TOOL__BODY = TOOL__BODY;

    /**
     * The number of structural features of the '<em>Delete Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT;

    /**
     * The number of operations of the '<em>Delete Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DropToolImpl <em>Drop
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.DropToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDropTool()
     */
    int DROP_TOOL = 29;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_TOOL__NAME = TOOL__NAME;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_TOOL__PRECONDITION_EXPRESSION = TOOL__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_TOOL__BODY = TOOL__BODY;

    /**
     * The number of structural features of the '<em>Drop Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DROP_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT;

    /**
     * The number of operations of the '<em>Drop Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeToolImpl <em>Edge
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.EdgeToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeTool()
     */
    int EDGE_TOOL = 30;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL__NAME = TOOL__NAME;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL__PRECONDITION_EXPRESSION = TOOL__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL__BODY = TOOL__BODY;

    /**
     * The feature id for the '<em><b>Target Element Descriptions</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS = TOOL_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Icon UR Ls Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL__ICON_UR_LS_EXPRESSION = TOOL_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Edge Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Edge Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeReconnectionToolImpl
     * <em>Edge Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.EdgeReconnectionToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeReconnectionTool()
     */
    int EDGE_RECONNECTION_TOOL = 31;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL__NAME = TOOL__NAME;
    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL__NAME = EDGE_RECONNECTION_TOOL__NAME;
    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL__NAME = EDGE_RECONNECTION_TOOL__NAME;
    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL__PRECONDITION_EXPRESSION = TOOL__PRECONDITION_EXPRESSION;
    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL__PRECONDITION_EXPRESSION = EDGE_RECONNECTION_TOOL__PRECONDITION_EXPRESSION;
    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL__PRECONDITION_EXPRESSION = EDGE_RECONNECTION_TOOL__PRECONDITION_EXPRESSION;
    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL__BODY = TOOL__BODY;
    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL__BODY = EDGE_RECONNECTION_TOOL__BODY;
    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL__BODY = EDGE_RECONNECTION_TOOL__BODY;
    /**
     * The number of structural features of the '<em>Edge Reconnection Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT;
    /**
     * The number of structural features of the '<em>Source Edge End Reconnection Tool</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL_FEATURE_COUNT = EDGE_RECONNECTION_TOOL_FEATURE_COUNT;
    /**
     * The number of structural features of the '<em>Target Edge End Reconnection Tool</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL_FEATURE_COUNT = EDGE_RECONNECTION_TOOL_FEATURE_COUNT;
    /**
     * The number of operations of the '<em>Edge Reconnection Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT;
    /**
     * The number of operations of the '<em>Source Edge End Reconnection Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL_OPERATION_COUNT = EDGE_RECONNECTION_TOOL_OPERATION_COUNT;
    /**
     * The number of operations of the '<em>Target Edge End Reconnection Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL_OPERATION_COUNT = EDGE_RECONNECTION_TOOL_OPERATION_COUNT;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.LabelEditToolImpl <em>Label
     * Edit Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.LabelEditToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLabelEditTool()
     */
    int LABEL_EDIT_TOOL = 32;
    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_EDIT_TOOL__NAME = TOOL__NAME;
    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_EDIT_TOOL__PRECONDITION_EXPRESSION = TOOL__PRECONDITION_EXPRESSION;
    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_EDIT_TOOL__BODY = TOOL__BODY;
    /**
     * The feature id for the '<em><b>Initial Direct Edit Label Expression</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_EDIT_TOOL__INITIAL_DIRECT_EDIT_LABEL_EXPRESSION = TOOL_FEATURE_COUNT;
    /**
     * The number of structural features of the '<em>Label Edit Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_EDIT_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 1;
    /**
     * The number of operations of the '<em>Label Edit Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LABEL_EDIT_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.NodeToolImpl <em>Node
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.NodeToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeTool()
     */
    int NODE_TOOL = 33;
    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL__NAME = TOOL__NAME;
    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL__PRECONDITION_EXPRESSION = TOOL__PRECONDITION_EXPRESSION;
    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL__BODY = TOOL__BODY;
    /**
     * The feature id for the '<em><b>Selection Description</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL__SELECTION_DESCRIPTION = TOOL_FEATURE_COUNT;
    /**
     * The feature id for the '<em><b>Icon UR Ls Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL__ICON_UR_LS_EXPRESSION = TOOL_FEATURE_COUNT + 1;
    /**
     * The number of structural features of the '<em>Node Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 2;
    /**
     * The number of operations of the '<em>Node Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT;
    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.SourceEdgeEndReconnectionToolImpl <em>Source Edge End
     * Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.SourceEdgeEndReconnectionToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getSourceEdgeEndReconnectionTool()
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL = 34;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.TargetEdgeEndReconnectionToolImpl <em>Target Edge End
     * Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.TargetEdgeEndReconnectionToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getTargetEdgeEndReconnectionTool()
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL = 35;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl <em>Create
     * View</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getCreateView()
     */
    int CREATE_VIEW = 36;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW__CHILDREN = ViewPackage.OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>Parent View Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW__PARENT_VIEW_EXPRESSION = ViewPackage.OPERATION_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Element Description</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW__ELEMENT_DESCRIPTION = ViewPackage.OPERATION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Semantic Element Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION = ViewPackage.OPERATION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Variable Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW__VARIABLE_NAME = ViewPackage.OPERATION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Containment Kind</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW__CONTAINMENT_KIND = ViewPackage.OPERATION_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Create View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW_FEATURE_COUNT = ViewPackage.OPERATION_FEATURE_COUNT + 5;

    /**
     * The number of operations of the '<em>Create View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_VIEW_OPERATION_COUNT = ViewPackage.OPERATION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DeleteViewImpl <em>Delete
     * View</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.DeleteViewImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDeleteView()
     */
    int DELETE_VIEW = 37;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_VIEW__CHILDREN = ViewPackage.OPERATION__CHILDREN;

    /**
     * The feature id for the '<em><b>View Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_VIEW__VIEW_EXPRESSION = ViewPackage.OPERATION_FEATURE_COUNT;

    /**
     * The number of structural features of the '<em>Delete View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DELETE_VIEW_FEATURE_COUNT = ViewPackage.OPERATION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Delete View</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_VIEW_OPERATION_COUNT = ViewPackage.OPERATION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDescriptionImpl
     * <em>Selection Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.SelectionDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getSelectionDescription()
     */
    int SELECTION_DESCRIPTION = 38;

    /**
     * The feature id for the '<em><b>Selection Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECTION_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION = 0;

    /**
     * The feature id for the '<em><b>Selection Message</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECTION_DESCRIPTION__SELECTION_MESSAGE = 1;

    /**
     * The number of structural features of the '<em>Selection Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SELECTION_DESCRIPTION_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Selection Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int SELECTION_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.ToolSectionImpl <em>Tool
     * Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.ToolSectionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getToolSection()
     */
    int TOOL_SECTION = 39;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TOOL_SECTION__NAME = 0;

    /**
     * The number of structural features of the '<em>Tool Section</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TOOL_SECTION_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Tool Section</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TOOL_SECTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramToolSectionImpl
     * <em>Tool Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramToolSectionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramToolSection()
     */
    int DIAGRAM_TOOL_SECTION = 40;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_TOOL_SECTION__NAME = TOOL_SECTION__NAME;

    /**
     * The feature id for the '<em><b>Node Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_TOOL_SECTION__NODE_TOOLS = TOOL_SECTION_FEATURE_COUNT;

    /**
     * The number of structural features of the '<em>Tool Section</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_TOOL_SECTION_FEATURE_COUNT = TOOL_SECTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Tool Section</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_TOOL_SECTION_OPERATION_COUNT = TOOL_SECTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.NodeToolSectionImpl <em>Node
     * Tool Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.NodeToolSectionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeToolSection()
     */
    int NODE_TOOL_SECTION = 41;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL_SECTION__NAME = TOOL_SECTION__NAME;

    /**
     * The feature id for the '<em><b>Node Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL_SECTION__NODE_TOOLS = TOOL_SECTION_FEATURE_COUNT;

    /**
     * The feature id for the '<em><b>Edge Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL_SECTION__EDGE_TOOLS = TOOL_SECTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Node Tool Section</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL_SECTION_FEATURE_COUNT = TOOL_SECTION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Node Tool Section</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL_SECTION_OPERATION_COUNT = TOOL_SECTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeToolSectionImpl <em>Edge
     * Tool Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.EdgeToolSectionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeToolSection()
     */
    int EDGE_TOOL_SECTION = 42;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL_SECTION__NAME = TOOL_SECTION__NAME;

    /**
     * The feature id for the '<em><b>Node Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL_SECTION__NODE_TOOLS = TOOL_SECTION_FEATURE_COUNT;

    /**
     * The number of structural features of the '<em>Edge Tool Section</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL_SECTION_FEATURE_COUNT = TOOL_SECTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Edge Tool Section</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL_SECTION_OPERATION_COUNT = TOOL_SECTION_OPERATION_COUNT;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DropNodeToolImpl <em>Drop Node
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.impl.DropNodeToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDropNodeTool()
     */
    int DROP_NODE_TOOL = 43;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_NODE_TOOL__NAME = TOOL__NAME;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_NODE_TOOL__PRECONDITION_EXPRESSION = TOOL__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_NODE_TOOL__BODY = TOOL__BODY;

    /**
     * The feature id for the '<em><b>Accepted Node Types</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_NODE_TOOL__ACCEPTED_NODE_TYPES = TOOL_FEATURE_COUNT;
    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    DiagramPackage eINSTANCE = org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl.init();
    /**
     * The number of structural features of the '<em>Drop Node Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_NODE_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 1;
    /**
     * The number of operations of the '<em>Drop Node Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_NODE_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.ArrowStyle <em>Arrow Style</em>}'
     * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getArrowStyle()
     */
    int ARROW_STYLE = 44;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.LayoutDirection <em>Layout
     * Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LayoutDirection
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLayoutDirection()
     */
    int LAYOUT_DIRECTION = 45;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.LineStyle <em>Line Style</em>}'
     * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LineStyle
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLineStyle()
     */
    int LINE_STYLE = 46;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.NodeContainmentKind <em>Node
     * Containment Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeContainmentKind
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeContainmentKind()
     */
    int NODE_CONTAINMENT_KIND = 47;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
     * <em>Synchronization Policy</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getSynchronizationPolicy()
     */
    int SYNCHRONIZATION_POLICY = 48;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.InsideLabelPosition <em>Inside
     * Label Position</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelPosition
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getInsideLabelPosition()
     */
    int INSIDE_LABEL_POSITION = 49;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.OutsideLabelPosition <em>Outside
     * Label Position</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.OutsideLabelPosition
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getOutsideLabelPosition()
     */
    int OUTSIDE_LABEL_POSITION = 50;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy <em>Label
     * Overflow Strategy</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLabelOverflowStrategy()
     */
    int LABEL_OVERFLOW_STRATEGY = 51;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection <em>Arrange
     * Layout Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getArrangeLayoutDirection()
     */
    int ARRANGE_LAYOUT_DIRECTION = 52;
    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.LabelTextAlign <em>Label Text
     * Align</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelTextAlign
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLabelTextAlign()
     */
    int LABEL_TEXT_ALIGN = 53;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription
     * <em>Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramDescription
     */
    EClass getDiagramDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#isAutoLayout <em>Auto Layout</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Auto Layout</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramDescription#isAutoLayout()
     * @see #getDiagramDescription()
     */
    EAttribute getDiagramDescription_AutoLayout();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getPalette <em>Palette</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Palette</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramDescription#getPalette()
     * @see #getDiagramDescription()
     */
    EReference getDiagramDescription_Palette();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getNodeDescriptions <em>Node
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Descriptions</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramDescription#getNodeDescriptions()
     * @see #getDiagramDescription()
     */
    EReference getDiagramDescription_NodeDescriptions();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getEdgeDescriptions <em>Edge
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Edge Descriptions</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramDescription#getEdgeDescriptions()
     * @see #getDiagramDescription()
     */
    EReference getDiagramDescription_EdgeDescriptions();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getArrangeLayoutDirection <em>Arrange
     * Layout Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Arrange Layout Direction</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramDescription#getArrangeLayoutDirection()
     * @see #getDiagramDescription()
     */
    EAttribute getDiagramDescription_ArrangeLayoutDirection();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription
     * <em>Element Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Element Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription
     */
    EClass getDiagramElementDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getName <em>Name</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getName()
     * @see #getDiagramElementDescription()
     */
    EAttribute getDiagramElementDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getDomainType <em>Domain
     * Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Domain Type</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getDomainType()
     * @see #getDiagramElementDescription()
     */
    EAttribute getDiagramElementDescription_DomainType();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getSemanticCandidatesExpression
     * <em>Semantic Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getSemanticCandidatesExpression()
     * @see #getDiagramElementDescription()
     */
    EAttribute getDiagramElementDescription_SemanticCandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getPreconditionExpression
     * <em>Precondition Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getPreconditionExpression()
     * @see #getDiagramElementDescription()
     */
    EAttribute getDiagramElementDescription_PreconditionExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getSynchronizationPolicy
     * <em>Synchronization Policy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Synchronization Policy</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getSynchronizationPolicy()
     * @see #getDiagramElementDescription()
     */
    EAttribute getDiagramElementDescription_SynchronizationPolicy();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.NodeDescription <em>Node
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription
     */
    EClass getNodeDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isCollapsible <em>Collapsible</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Collapsible</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#isCollapsible()
     * @see #getNodeDescription()
     */
    EAttribute getNodeDescription_Collapsible();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getPalette <em>Palette</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Palette</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getPalette()
     * @see #getNodeDescription()
     */
    EReference getNodeDescription_Palette();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getChildrenLayoutStrategy <em>Children Layout
     * Strategy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Children Layout Strategy</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getChildrenLayoutStrategy()
     * @see #getNodeDescription()
     */
    EReference getNodeDescription_ChildrenLayoutStrategy();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getStyle()
     * @see #getNodeDescription()
     */
    EReference getNodeDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getConditionalStyles()
     * @see #getNodeDescription()
     */
    EReference getNodeDescription_ConditionalStyles();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getChildrenDescriptions <em>Children
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Children Descriptions</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getChildrenDescriptions()
     * @see #getNodeDescription()
     */
    EReference getNodeDescription_ChildrenDescriptions();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getBorderNodesDescriptions <em>Border Nodes
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Border Nodes Descriptions</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getBorderNodesDescriptions()
     * @see #getNodeDescription()
     */
    EReference getNodeDescription_BorderNodesDescriptions();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getReusedChildNodeDescriptions <em>Reused
     * Child Node Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Reused Child Node Descriptions</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getReusedChildNodeDescriptions()
     * @see #getNodeDescription()
     */
    EReference getNodeDescription_ReusedChildNodeDescriptions();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getReusedBorderNodeDescriptions <em>Reused
     * Border Node Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Reused Border Node Descriptions</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getReusedBorderNodeDescriptions()
     * @see #getNodeDescription()
     */
    EReference getNodeDescription_ReusedBorderNodeDescriptions();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isUserResizable <em>User Resizable</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>User Resizable</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#isUserResizable()
     * @see #getNodeDescription()
     */
    EAttribute getNodeDescription_UserResizable();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getDefaultWidthExpression <em>Default Width
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Default Width Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getDefaultWidthExpression()
     * @see #getNodeDescription()
     */
    EAttribute getNodeDescription_DefaultWidthExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getDefaultHeightExpression <em>Default Height
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Default Height Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getDefaultHeightExpression()
     * @see #getNodeDescription()
     */
    EAttribute getNodeDescription_DefaultHeightExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isKeepAspectRatio <em>Keep Aspect
     * Ratio</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Keep Aspect Ratio</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#isKeepAspectRatio()
     * @see #getNodeDescription()
     */
    EAttribute getNodeDescription_KeepAspectRatio();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getIsCollapsedByDefaultExpression <em>Is
     * Collapsed By Default Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Collapsed By Default Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getIsCollapsedByDefaultExpression()
     * @see #getNodeDescription()
     */
    EAttribute getNodeDescription_IsCollapsedByDefaultExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getInsideLabel <em>Inside Label</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Inside Label</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getInsideLabel()
     * @see #getNodeDescription()
     */
    EReference getNodeDescription_InsideLabel();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getOutsideLabels <em>Outside Labels</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Outside Labels</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getOutsideLabels()
     * @see #getNodeDescription()
     */
    EReference getNodeDescription_OutsideLabels();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getIsHiddenByDefaultExpression <em>Is Hidden
     * By Default Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Hidden By Default Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getIsHiddenByDefaultExpression()
     * @see #getNodeDescription()
     */
    EAttribute getNodeDescription_IsHiddenByDefaultExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getIsFadedByDefaultExpression <em>Is Faded By
     * Default Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Faded By Default Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getIsFadedByDefaultExpression()
     * @see #getNodeDescription()
     */
    EAttribute getNodeDescription_IsFadedByDefaultExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription <em>Edge
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription
     */
    EClass getEdgeDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getBeginLabelExpression <em>Begin Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Begin Label Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getBeginLabelExpression()
     * @see #getEdgeDescription()
     */
    EAttribute getEdgeDescription_BeginLabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getCenterLabelExpression <em>Center Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Center Label Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getCenterLabelExpression()
     * @see #getEdgeDescription()
     */
    EAttribute getEdgeDescription_CenterLabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getEndLabelExpression <em>End Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>End Label Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getEndLabelExpression()
     * @see #getEdgeDescription()
     */
    EAttribute getEdgeDescription_EndLabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#isIsDomainBasedEdge <em>Is Domain Based
     * Edge</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Domain Based Edge</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#isIsDomainBasedEdge()
     * @see #getEdgeDescription()
     */
    EAttribute getEdgeDescription_IsDomainBasedEdge();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getPalette <em>Palette</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Palette</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getPalette()
     * @see #getEdgeDescription()
     */
    EReference getEdgeDescription_Palette();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodeDescriptions <em>Source Node
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Source Node Descriptions</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodeDescriptions()
     * @see #getEdgeDescription()
     */
    EReference getEdgeDescription_SourceNodeDescriptions();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodeDescriptions <em>Target Node
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Target Node Descriptions</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodeDescriptions()
     * @see #getEdgeDescription()
     */
    EReference getEdgeDescription_TargetNodeDescriptions();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodesExpression <em>Source Nodes
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Source Nodes Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodesExpression()
     * @see #getEdgeDescription()
     */
    EAttribute getEdgeDescription_SourceNodesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodesExpression <em>Target Nodes
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Target Nodes Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodesExpression()
     * @see #getEdgeDescription()
     */
    EAttribute getEdgeDescription_TargetNodesExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getStyle()
     * @see #getEdgeDescription()
     */
    EReference getEdgeDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getConditionalStyles()
     * @see #getEdgeDescription()
     */
    EReference getEdgeDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription
     * <em>Layout Strategy Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Layout Strategy Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription
     */
    EClass getLayoutStrategyDescription();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription <em>List Layout Strategy
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>List Layout Strategy Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription
     */
    EClass getListLayoutStrategyDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription#getAreChildNodesDraggableExpression
     * <em>Are Child Nodes Draggable Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Are Child Nodes Draggable Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription#getAreChildNodesDraggableExpression()
     * @see #getListLayoutStrategyDescription()
     */
    EAttribute getListLayoutStrategyDescription_AreChildNodesDraggableExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription#getTopGapExpression <em>Top Gap
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Top Gap Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription#getTopGapExpression()
     * @see #getListLayoutStrategyDescription()
     */
    EAttribute getListLayoutStrategyDescription_TopGapExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription#getBottomGapExpression
     * <em>Bottom Gap Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Bottom Gap Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription#getBottomGapExpression()
     * @see #getListLayoutStrategyDescription()
     */
    EAttribute getListLayoutStrategyDescription_BottomGapExpression();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription#getGrowableNodes <em>Growable
     * Nodes</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Growable Nodes</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription#getGrowableNodes()
     * @see #getListLayoutStrategyDescription()
     */
    EReference getListLayoutStrategyDescription_GrowableNodes();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription <em>Free Form Layout
     * Strategy Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Free Form Layout Strategy Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription
     */
    EClass getFreeFormLayoutStrategyDescription();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.LabelDescription <em>Label
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Label Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelDescription
     */
    EClass getLabelDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.LabelDescription#getLabelExpression <em>Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelDescription#getLabelExpression()
     * @see #getLabelDescription()
     */
    EAttribute getLabelDescription_LabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.LabelDescription#getOverflowStrategy <em>Overflow
     * Strategy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Overflow Strategy</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelDescription#getOverflowStrategy()
     * @see #getLabelDescription()
     */
    EAttribute getLabelDescription_OverflowStrategy();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.LabelDescription#getTextAlign <em>Text Align</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Text Align</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelDescription#getTextAlign()
     * @see #getLabelDescription()
     */
    EAttribute getLabelDescription_TextAlign();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.InsideLabelDescription
     * <em>Inside Label Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Inside Label Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelDescription
     */
    EClass getInsideLabelDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.InsideLabelDescription#getPosition <em>Position</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Position</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelDescription#getPosition()
     * @see #getInsideLabelDescription()
     */
    EAttribute getInsideLabelDescription_Position();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.InsideLabelDescription#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelDescription#getStyle()
     * @see #getInsideLabelDescription()
     */
    EReference getInsideLabelDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.InsideLabelDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelDescription#getConditionalStyles()
     * @see #getInsideLabelDescription()
     */
    EReference getInsideLabelDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.OutsideLabelDescription
     * <em>Outside Label Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Outside Label Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.OutsideLabelDescription
     */
    EClass getOutsideLabelDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.OutsideLabelDescription#getPosition <em>Position</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Position</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.OutsideLabelDescription#getPosition()
     * @see #getOutsideLabelDescription()
     */
    EAttribute getOutsideLabelDescription_Position();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.OutsideLabelDescription#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.OutsideLabelDescription#getStyle()
     * @see #getOutsideLabelDescription()
     */
    EReference getOutsideLabelDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.OutsideLabelDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.OutsideLabelDescription#getConditionalStyles()
     * @see #getOutsideLabelDescription()
     */
    EReference getOutsideLabelDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.Style <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.Style
     */
    EClass getStyle();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.sirius.components.view.diagram.Style#getColor
     * <em>Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Color</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.Style#getColor()
     * @see #getStyle()
     */
    EReference getStyle_Color();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.BorderStyle <em>Border
     * Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Border Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle
     */
    EClass getBorderStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderColor <em>Border Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Border Color</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderColor()
     * @see #getBorderStyle()
     */
    EReference getBorderStyle_BorderColor();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderRadius <em>Border Radius</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Border Radius</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderRadius()
     * @see #getBorderStyle()
     */
    EAttribute getBorderStyle_BorderRadius();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderSize <em>Border Size</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Border Size</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderSize()
     * @see #getBorderStyle()
     */
    EAttribute getBorderStyle_BorderSize();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderLineStyle <em>Border Line Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Border Line Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderLineStyle()
     * @see #getBorderStyle()
     */
    EAttribute getBorderStyle_BorderLineStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.InsideLabelStyle <em>Inside
     * Label Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Inside Label Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelStyle
     */
    EClass getInsideLabelStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.InsideLabelStyle#isWithHeader <em>With Header</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>With Header</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelStyle#isWithHeader()
     * @see #getInsideLabelStyle()
     */
    EAttribute getInsideLabelStyle_WithHeader();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.InsideLabelStyle#isDisplayHeaderSeparator <em>Display Header
     * Separator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Display Header Separator</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelStyle#isDisplayHeaderSeparator()
     * @see #getInsideLabelStyle()
     */
    EAttribute getInsideLabelStyle_DisplayHeaderSeparator();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.OutsideLabelStyle
     * <em>Outside Label Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Outside Label Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.OutsideLabelStyle
     */
    EClass getOutsideLabelStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle <em>Node
     * Label Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Label Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeLabelStyle
     */
    EClass getNodeLabelStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getLabelColor <em>Label Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Label Color</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getLabelColor()
     * @see #getNodeLabelStyle()
     */
    EReference getNodeLabelStyle_LabelColor();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getBackground <em>Background</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Background</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getBackground()
     * @see #getNodeLabelStyle()
     */
    EReference getNodeLabelStyle_Background();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#isShowIcon <em>Show Icon</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Show Icon</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeLabelStyle#isShowIcon()
     * @see #getNodeLabelStyle()
     */
    EAttribute getNodeLabelStyle_ShowIcon();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getLabelIcon <em>Label Icon</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Icon</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getLabelIcon()
     * @see #getNodeLabelStyle()
     */
    EAttribute getNodeLabelStyle_LabelIcon();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription
     * <em>Node Style Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Style Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeStyleDescription
     */
    EClass getNodeStyleDescription();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle
     * <em>Conditional Node Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Node Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle
     */
    EClass getConditionalNodeStyle();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle#getStyle()
     * @see #getConditionalNodeStyle()
     */
    EReference getConditionalNodeStyle_Style();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle
     * <em>Conditional Inside Label Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Inside Label Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle
     */
    EClass getConditionalInsideLabelStyle();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle#getStyle()
     * @see #getConditionalInsideLabelStyle()
     */
    EReference getConditionalInsideLabelStyle_Style();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle
     * <em>Conditional Outside Label Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Outside Label Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle
     */
    EClass getConditionalOutsideLabelStyle();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle#getStyle()
     * @see #getConditionalOutsideLabelStyle()
     */
    EReference getConditionalOutsideLabelStyle_Style();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription <em>Rectangular Node Style
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Rectangular Node Style Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription
     */
    EClass getRectangularNodeStyleDescription();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription#getBackground
     * <em>Background</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Background</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription#getBackground()
     * @see #getRectangularNodeStyleDescription()
     */
    EReference getRectangularNodeStyleDescription_Background();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription
     * <em>Image Node Style Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Image Node Style Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription
     */
    EClass getImageNodeStyleDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription#getShape <em>Shape</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Shape</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription#getShape()
     * @see #getImageNodeStyleDescription()
     */
    EAttribute getImageNodeStyleDescription_Shape();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription#isPositionDependentRotation
     * <em>Position Dependent Rotation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Position Dependent Rotation</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription#isPositionDependentRotation()
     * @see #getImageNodeStyleDescription()
     */
    EAttribute getImageNodeStyleDescription_PositionDependentRotation();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription <em>Icon Label Node Style
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Icon Label Node Style Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription
     */
    EClass getIconLabelNodeStyleDescription();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription#getBackground
     * <em>Background</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Background</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription#getBackground()
     * @see #getIconLabelNodeStyleDescription()
     */
    EReference getIconLabelNodeStyleDescription_Background();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle <em>Edge
     * Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle
     */
    EClass getEdgeStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getLineStyle <em>Line Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Line Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#getLineStyle()
     * @see #getEdgeStyle()
     */
    EAttribute getEdgeStyle_LineStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getSourceArrowStyle <em>Source Arrow Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Source Arrow Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#getSourceArrowStyle()
     * @see #getEdgeStyle()
     */
    EAttribute getEdgeStyle_SourceArrowStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getTargetArrowStyle <em>Target Arrow Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Target Arrow Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#getTargetArrowStyle()
     * @see #getEdgeStyle()
     */
    EAttribute getEdgeStyle_TargetArrowStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getEdgeWidth <em>Edge Width</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Edge Width</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#getEdgeWidth()
     * @see #getEdgeStyle()
     */
    EAttribute getEdgeStyle_EdgeWidth();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#isShowIcon
     * <em>Show Icon</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Show Icon</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#isShowIcon()
     * @see #getEdgeStyle()
     */
    EAttribute getEdgeStyle_ShowIcon();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getLabelIcon <em>Label Icon</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Icon</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#getLabelIcon()
     * @see #getEdgeStyle()
     */
    EAttribute getEdgeStyle_LabelIcon();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getBackground <em>Background</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Background</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#getBackground()
     * @see #getEdgeStyle()
     */
    EReference getEdgeStyle_Background();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle
     * <em>Conditional Edge Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Edge Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle
     */
    EClass getConditionalEdgeStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DiagramPalette
     * <em>Palette</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Palette</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPalette
     */
    EClass getDiagramPalette();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramPalette#getDropTool <em>Drop Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Drop Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPalette#getDropTool()
     * @see #getDiagramPalette()
     */
    EReference getDiagramPalette_DropTool();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramPalette#getDropNodeTool <em>Drop Node Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Drop Node Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPalette#getDropNodeTool()
     * @see #getDiagramPalette()
     */
    EReference getDiagramPalette_DropNodeTool();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramPalette#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPalette#getNodeTools()
     * @see #getDiagramPalette()
     */
    EReference getDiagramPalette_NodeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramPalette#getToolSections <em>Tool Sections</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Tool Sections</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPalette#getToolSections()
     * @see #getDiagramPalette()
     */
    EReference getDiagramPalette_ToolSections();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.NodePalette <em>Node
     * Palette</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Palette</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodePalette
     */
    EClass getNodePalette();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodePalette#getDeleteTool <em>Delete Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Delete Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodePalette#getDeleteTool()
     * @see #getNodePalette()
     */
    EReference getNodePalette_DeleteTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodePalette#getLabelEditTool <em>Label Edit Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Label Edit Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodePalette#getLabelEditTool()
     * @see #getNodePalette()
     */
    EReference getNodePalette_LabelEditTool();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodePalette#getDropNodeTool <em>Drop Node Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Drop Node Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodePalette#getDropNodeTool()
     * @see #getNodePalette()
     */
    EReference getNodePalette_DropNodeTool();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodePalette#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodePalette#getNodeTools()
     * @see #getNodePalette()
     */
    EReference getNodePalette_NodeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodePalette#getEdgeTools <em>Edge Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Edge Tools</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodePalette#getEdgeTools()
     * @see #getNodePalette()
     */
    EReference getNodePalette_EdgeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodePalette#getToolSections <em>Tool Sections</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Tool Sections</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodePalette#getToolSections()
     * @see #getNodePalette()
     */
    EReference getNodePalette_ToolSections();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.EdgePalette <em>Edge
     * Palette</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Palette</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette
     */
    EClass getEdgePalette();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getDeleteTool <em>Delete Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Delete Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getDeleteTool()
     * @see #getEdgePalette()
     */
    EReference getEdgePalette_DeleteTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getCenterLabelEditTool <em>Center Label Edit
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Center Label Edit Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getCenterLabelEditTool()
     * @see #getEdgePalette()
     */
    EReference getEdgePalette_CenterLabelEditTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getBeginLabelEditTool <em>Begin Label Edit
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Begin Label Edit Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getBeginLabelEditTool()
     * @see #getEdgePalette()
     */
    EReference getEdgePalette_BeginLabelEditTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getEndLabelEditTool <em>End Label Edit
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>End Label Edit Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getEndLabelEditTool()
     * @see #getEdgePalette()
     */
    EReference getEdgePalette_EndLabelEditTool();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getNodeTools()
     * @see #getEdgePalette()
     */
    EReference getEdgePalette_NodeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getEdgeReconnectionTools <em>Edge Reconnection
     * Tools</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Edge Reconnection Tools</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getEdgeReconnectionTools()
     * @see #getEdgePalette()
     */
    EReference getEdgePalette_EdgeReconnectionTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getToolSections <em>Tool Sections</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Tool Sections</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getToolSections()
     * @see #getEdgePalette()
     */
    EReference getEdgePalette_ToolSections();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.Tool <em>Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.Tool
     */
    EClass getTool();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.diagram.Tool#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.Tool#getName()
     * @see #getTool()
     */
    EAttribute getTool_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.Tool#getPreconditionExpression <em>Precondition
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.Tool#getPreconditionExpression()
     * @see #getTool()
     */
    EAttribute getTool_PreconditionExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.Tool#getBody <em>Body</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.Tool#getBody()
     * @see #getTool()
     */
    EReference getTool_Body();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DeleteTool <em>Delete
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Delete Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DeleteTool
     */
    EClass getDeleteTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DropTool <em>Drop
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Drop Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DropTool
     */
    EClass getDropTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.EdgeTool <em>Edge
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeTool
     */
    EClass getEdgeTool();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeTool#getTargetElementDescriptions <em>Target Element
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Target Element Descriptions</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeTool#getTargetElementDescriptions()
     * @see #getEdgeTool()
     */
    EReference getEdgeTool_TargetElementDescriptions();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeTool#getIconURLsExpression <em>Icon UR Ls
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Icon UR Ls Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeTool#getIconURLsExpression()
     * @see #getEdgeTool()
     */
    EAttribute getEdgeTool_IconURLsExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool
     * <em>Edge Reconnection Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Reconnection Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool
     */
    EClass getEdgeReconnectionTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.LabelEditTool <em>Label Edit
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Label Edit Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelEditTool
     */
    EClass getLabelEditTool();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.LabelEditTool#getInitialDirectEditLabelExpression <em>Initial
     * Direct Edit Label Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Initial Direct Edit Label Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelEditTool#getInitialDirectEditLabelExpression()
     * @see #getLabelEditTool()
     */
    EAttribute getLabelEditTool_InitialDirectEditLabelExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.NodeTool <em>Node
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeTool
     */
    EClass getNodeTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodeTool#getSelectionDescription <em>Selection
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Selection Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeTool#getSelectionDescription()
     * @see #getNodeTool()
     */
    EReference getNodeTool_SelectionDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeTool#getIconURLsExpression <em>Icon UR Ls
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Icon UR Ls Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeTool#getIconURLsExpression()
     * @see #getNodeTool()
     */
    EAttribute getNodeTool_IconURLsExpression();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool <em>Source Edge End Reconnection
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Source Edge End Reconnection Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool
     */
    EClass getSourceEdgeEndReconnectionTool();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool <em>Target Edge End Reconnection
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Target Edge End Reconnection Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool
     */
    EClass getTargetEdgeEndReconnectionTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.CreateView <em>Create
     * View</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Create View</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.CreateView
     */
    EClass getCreateView();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.CreateView#getParentViewExpression <em>Parent View
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Parent View Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.CreateView#getParentViewExpression()
     * @see #getCreateView()
     */
    EAttribute getCreateView_ParentViewExpression();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.diagram.CreateView#getElementDescription <em>Element
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Element Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.CreateView#getElementDescription()
     * @see #getCreateView()
     */
    EReference getCreateView_ElementDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.CreateView#getSemanticElementExpression <em>Semantic Element
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Element Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.CreateView#getSemanticElementExpression()
     * @see #getCreateView()
     */
    EAttribute getCreateView_SemanticElementExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.CreateView#getVariableName <em>Variable Name</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Variable Name</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.CreateView#getVariableName()
     * @see #getCreateView()
     */
    EAttribute getCreateView_VariableName();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.CreateView#getContainmentKind <em>Containment Kind</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Containment Kind</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.CreateView#getContainmentKind()
     * @see #getCreateView()
     */
    EAttribute getCreateView_ContainmentKind();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DeleteView <em>Delete
     * View</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Delete View</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DeleteView
     */
    EClass getDeleteView();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DeleteView#getViewExpression <em>View Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>View Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DeleteView#getViewExpression()
     * @see #getDeleteView()
     */
    EAttribute getDeleteView_ViewExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.SelectionDescription
     * <em>Selection Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Selection Description</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.SelectionDescription
     */
    EClass getSelectionDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDescription#getSelectionCandidatesExpression
     * <em>Selection Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Selection Candidates Expression</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.SelectionDescription#getSelectionCandidatesExpression()
     * @see #getSelectionDescription()
     */
    EAttribute getSelectionDescription_SelectionCandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDescription#getSelectionMessage <em>Selection
     * Message</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Selection Message</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.SelectionDescription#getSelectionMessage()
     * @see #getSelectionDescription()
     */
    EAttribute getSelectionDescription_SelectionMessage();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.ToolSection <em>Tool
     * Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Tool Section</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ToolSection
     */
    EClass getToolSection();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.diagram.ToolSection#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ToolSection#getName()
     * @see #getToolSection()
     */
    EAttribute getToolSection_Name();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DiagramToolSection <em>Tool
     * Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Tool Section</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramToolSection
     */
    EClass getDiagramToolSection();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramToolSection#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramToolSection#getNodeTools()
     * @see #getDiagramToolSection()
     */
    EReference getDiagramToolSection_NodeTools();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.NodeToolSection <em>Node
     * Tool Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Tool Section</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeToolSection
     */
    EClass getNodeToolSection();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeToolSection#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeToolSection#getNodeTools()
     * @see #getNodeToolSection()
     */
    EReference getNodeToolSection_NodeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeToolSection#getEdgeTools <em>Edge Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Edge Tools</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeToolSection#getEdgeTools()
     * @see #getNodeToolSection()
     */
    EReference getNodeToolSection_EdgeTools();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.EdgeToolSection <em>Edge
     * Tool Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Tool Section</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeToolSection
     */
    EClass getEdgeToolSection();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeToolSection#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.EdgeToolSection#getNodeTools()
     * @see #getEdgeToolSection()
     */
    EReference getEdgeToolSection_NodeTools();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DropNodeTool <em>Drop Node
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Drop Node Tool</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DropNodeTool
     */
    EClass getDropNodeTool();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.DropNodeTool#getAcceptedNodeTypes <em>Accepted Node
     * Types</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Accepted Node Types</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DropNodeTool#getAcceptedNodeTypes()
     * @see #getDropNodeTool()
     */
    EReference getDropNodeTool_AcceptedNodeTypes();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.ArrowStyle <em>Arrow
     * Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Arrow Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
     */
    EEnum getArrowStyle();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.LayoutDirection <em>Layout
     * Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Layout Direction</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LayoutDirection
     */
    EEnum getLayoutDirection();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.LineStyle <em>Line
     * Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Line Style</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LineStyle
     */
    EEnum getLineStyle();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.NodeContainmentKind <em>Node
     * Containment Kind</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Node Containment Kind</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.NodeContainmentKind
     */
    EEnum getNodeContainmentKind();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
     * <em>Synchronization Policy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Synchronization Policy</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
     */
    EEnum getSynchronizationPolicy();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.InsideLabelPosition
     * <em>Inside Label Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Inside Label Position</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelPosition
     */
    EEnum getInsideLabelPosition();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.OutsideLabelPosition
     * <em>Outside Label Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Outside Label Position</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.OutsideLabelPosition
     */
    EEnum getOutsideLabelPosition();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy
     * <em>Label Overflow Strategy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Label Overflow Strategy</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy
     */
    EEnum getLabelOverflowStrategy();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection
     * <em>Arrange Layout Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Arrange Layout Direction</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection
     */
    EEnum getArrangeLayoutDirection();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.LabelTextAlign <em>Label Text
     * Align</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Label Text Align</em>'.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelTextAlign
     */
    EEnum getLabelTextAlign();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    DiagramFactory getDiagramFactory();

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
         * '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl <em>Description</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramDescription()
         */
        EClass DIAGRAM_DESCRIPTION = eINSTANCE.getDiagramDescription();

        /**
         * The meta object literal for the '<em><b>Auto Layout</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_DESCRIPTION__AUTO_LAYOUT = eINSTANCE.getDiagramDescription_AutoLayout();

        /**
         * The meta object literal for the '<em><b>Palette</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_DESCRIPTION__PALETTE = eINSTANCE.getDiagramDescription_Palette();

        /**
         * The meta object literal for the '<em><b>Node Descriptions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS = eINSTANCE.getDiagramDescription_NodeDescriptions();

        /**
         * The meta object literal for the '<em><b>Edge Descriptions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS = eINSTANCE.getDiagramDescription_EdgeDescriptions();

        /**
         * The meta object literal for the '<em><b>Arrange Layout Direction</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_DESCRIPTION__ARRANGE_LAYOUT_DIRECTION = eINSTANCE.getDiagramDescription_ArrangeLayoutDirection();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramElementDescriptionImpl <em>Element
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramElementDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramElementDescription()
         */
        EClass DIAGRAM_ELEMENT_DESCRIPTION = eINSTANCE.getDiagramElementDescription();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__NAME = eINSTANCE.getDiagramElementDescription_Name();

        /**
         * The meta object literal for the '<em><b>Domain Type</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE = eINSTANCE.getDiagramElementDescription_DomainType();

        /**
         * The meta object literal for the '<em><b>Semantic Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = eINSTANCE.getDiagramElementDescription_SemanticCandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__PRECONDITION_EXPRESSION = eINSTANCE.getDiagramElementDescription_PreconditionExpression();

        /**
         * The meta object literal for the '<em><b>Synchronization Policy</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__SYNCHRONIZATION_POLICY = eINSTANCE.getDiagramElementDescription_SynchronizationPolicy();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl
         * <em>Node Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeDescription()
         */
        EClass NODE_DESCRIPTION = eINSTANCE.getNodeDescription();

        /**
         * The meta object literal for the '<em><b>Collapsible</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_DESCRIPTION__COLLAPSIBLE = eINSTANCE.getNodeDescription_Collapsible();

        /**
         * The meta object literal for the '<em><b>Palette</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__PALETTE = eINSTANCE.getNodeDescription_Palette();

        /**
         * The meta object literal for the '<em><b>Children Layout Strategy</b></em>' containment reference feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY = eINSTANCE.getNodeDescription_ChildrenLayoutStrategy();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__STYLE = eINSTANCE.getNodeDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getNodeDescription_ConditionalStyles();

        /**
         * The meta object literal for the '<em><b>Children Descriptions</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS = eINSTANCE.getNodeDescription_ChildrenDescriptions();

        /**
         * The meta object literal for the '<em><b>Border Nodes Descriptions</b></em>' containment reference list
         * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__BORDER_NODES_DESCRIPTIONS = eINSTANCE.getNodeDescription_BorderNodesDescriptions();

        /**
         * The meta object literal for the '<em><b>Reused Child Node Descriptions</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__REUSED_CHILD_NODE_DESCRIPTIONS = eINSTANCE.getNodeDescription_ReusedChildNodeDescriptions();

        /**
         * The meta object literal for the '<em><b>Reused Border Node Descriptions</b></em>' reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__REUSED_BORDER_NODE_DESCRIPTIONS = eINSTANCE.getNodeDescription_ReusedBorderNodeDescriptions();

        /**
         * The meta object literal for the '<em><b>User Resizable</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_DESCRIPTION__USER_RESIZABLE = eINSTANCE.getNodeDescription_UserResizable();

        /**
         * The meta object literal for the '<em><b>Default Width Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_DESCRIPTION__DEFAULT_WIDTH_EXPRESSION = eINSTANCE.getNodeDescription_DefaultWidthExpression();

        /**
         * The meta object literal for the '<em><b>Default Height Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_DESCRIPTION__DEFAULT_HEIGHT_EXPRESSION = eINSTANCE.getNodeDescription_DefaultHeightExpression();

        /**
         * The meta object literal for the '<em><b>Keep Aspect Ratio</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_DESCRIPTION__KEEP_ASPECT_RATIO = eINSTANCE.getNodeDescription_KeepAspectRatio();

        /**
         * The meta object literal for the '<em><b>Is Collapsed By Default Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_DESCRIPTION__IS_COLLAPSED_BY_DEFAULT_EXPRESSION = eINSTANCE.getNodeDescription_IsCollapsedByDefaultExpression();

        /**
         * The meta object literal for the '<em><b>Inside Label</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__INSIDE_LABEL = eINSTANCE.getNodeDescription_InsideLabel();

        /**
         * The meta object literal for the '<em><b>Outside Labels</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_DESCRIPTION__OUTSIDE_LABELS = eINSTANCE.getNodeDescription_OutsideLabels();

        /**
         * The meta object literal for the '<em><b>Is Hidden By Default Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_DESCRIPTION__IS_HIDDEN_BY_DEFAULT_EXPRESSION = eINSTANCE.getNodeDescription_IsHiddenByDefaultExpression();

        /**
         * The meta object literal for the '<em><b>Is Faded By Default Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_DESCRIPTION__IS_FADED_BY_DEFAULT_EXPRESSION = eINSTANCE.getNodeDescription_IsFadedByDefaultExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl
         * <em>Edge Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeDescription()
         */
        EClass EDGE_DESCRIPTION = eINSTANCE.getEdgeDescription();

        /**
         * The meta object literal for the '<em><b>Begin Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION = eINSTANCE.getEdgeDescription_BeginLabelExpression();

        /**
         * The meta object literal for the '<em><b>Center Label Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_DESCRIPTION__CENTER_LABEL_EXPRESSION = eINSTANCE.getEdgeDescription_CenterLabelExpression();

        /**
         * The meta object literal for the '<em><b>End Label Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_DESCRIPTION__END_LABEL_EXPRESSION = eINSTANCE.getEdgeDescription_EndLabelExpression();

        /**
         * The meta object literal for the '<em><b>Is Domain Based Edge</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE = eINSTANCE.getEdgeDescription_IsDomainBasedEdge();

        /**
         * The meta object literal for the '<em><b>Palette</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_DESCRIPTION__PALETTE = eINSTANCE.getEdgeDescription_Palette();

        /**
         * The meta object literal for the '<em><b>Source Node Descriptions</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS = eINSTANCE.getEdgeDescription_SourceNodeDescriptions();

        /**
         * The meta object literal for the '<em><b>Target Node Descriptions</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS = eINSTANCE.getEdgeDescription_TargetNodeDescriptions();

        /**
         * The meta object literal for the '<em><b>Source Nodes Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION = eINSTANCE.getEdgeDescription_SourceNodesExpression();

        /**
         * The meta object literal for the '<em><b>Target Nodes Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION = eINSTANCE.getEdgeDescription_TargetNodesExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_DESCRIPTION__STYLE = eINSTANCE.getEdgeDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getEdgeDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription
         * <em>Layout Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLayoutStrategyDescription()
         */
        EClass LAYOUT_STRATEGY_DESCRIPTION = eINSTANCE.getLayoutStrategyDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl <em>List Layout
         * Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getListLayoutStrategyDescription()
         */
        EClass LIST_LAYOUT_STRATEGY_DESCRIPTION = eINSTANCE.getListLayoutStrategyDescription();

        /**
         * The meta object literal for the '<em><b>Are Child Nodes Draggable Expression</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION = eINSTANCE.getListLayoutStrategyDescription_AreChildNodesDraggableExpression();

        /**
         * The meta object literal for the '<em><b>Top Gap Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION = eINSTANCE.getListLayoutStrategyDescription_TopGapExpression();

        /**
         * The meta object literal for the '<em><b>Bottom Gap Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION = eINSTANCE.getListLayoutStrategyDescription_BottomGapExpression();

        /**
         * The meta object literal for the '<em><b>Growable Nodes</b></em>' reference list feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES = eINSTANCE.getListLayoutStrategyDescription_GrowableNodes();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl <em>Free Form
         * Layout Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getFreeFormLayoutStrategyDescription()
         */
        EClass FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION = eINSTANCE.getFreeFormLayoutStrategyDescription();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.LabelDescriptionImpl
         * <em>Label Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.LabelDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLabelDescription()
         */
        EClass LABEL_DESCRIPTION = eINSTANCE.getLabelDescription();

        /**
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getLabelDescription_LabelExpression();

        /**
         * The meta object literal for the '<em><b>Overflow Strategy</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_DESCRIPTION__OVERFLOW_STRATEGY = eINSTANCE.getLabelDescription_OverflowStrategy();

        /**
         * The meta object literal for the '<em><b>Text Align</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_DESCRIPTION__TEXT_ALIGN = eINSTANCE.getLabelDescription_TextAlign();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelDescriptionImpl <em>Inside Label
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.InsideLabelDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getInsideLabelDescription()
         */
        EClass INSIDE_LABEL_DESCRIPTION = eINSTANCE.getInsideLabelDescription();

        /**
         * The meta object literal for the '<em><b>Position</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute INSIDE_LABEL_DESCRIPTION__POSITION = eINSTANCE.getInsideLabelDescription_Position();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference INSIDE_LABEL_DESCRIPTION__STYLE = eINSTANCE.getInsideLabelDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference INSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getInsideLabelDescription_ConditionalStyles();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.OutsideLabelDescriptionImpl <em>Outside Label
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.OutsideLabelDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getOutsideLabelDescription()
         */
        EClass OUTSIDE_LABEL_DESCRIPTION = eINSTANCE.getOutsideLabelDescription();

        /**
         * The meta object literal for the '<em><b>Position</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute OUTSIDE_LABEL_DESCRIPTION__POSITION = eINSTANCE.getOutsideLabelDescription_Position();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference OUTSIDE_LABEL_DESCRIPTION__STYLE = eINSTANCE.getOutsideLabelDescription_Style();

        /**
         * The meta object literal for the '<em><b>Conditional Styles</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference OUTSIDE_LABEL_DESCRIPTION__CONDITIONAL_STYLES = eINSTANCE.getOutsideLabelDescription_ConditionalStyles();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.StyleImpl
         * <em>Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.StyleImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getStyle()
         */
        EClass STYLE = eINSTANCE.getStyle();

        /**
         * The meta object literal for the '<em><b>Color</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference STYLE__COLOR = eINSTANCE.getStyle_Color();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.BorderStyle <em>Border
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.BorderStyle
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getBorderStyle()
         */
        EClass BORDER_STYLE = eINSTANCE.getBorderStyle();

        /**
         * The meta object literal for the '<em><b>Border Color</b></em>' reference feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EReference BORDER_STYLE__BORDER_COLOR = eINSTANCE.getBorderStyle_BorderColor();

        /**
         * The meta object literal for the '<em><b>Border Radius</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BORDER_STYLE__BORDER_RADIUS = eINSTANCE.getBorderStyle_BorderRadius();

        /**
         * The meta object literal for the '<em><b>Border Size</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute BORDER_STYLE__BORDER_SIZE = eINSTANCE.getBorderStyle_BorderSize();

        /**
         * The meta object literal for the '<em><b>Border Line Style</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute BORDER_STYLE__BORDER_LINE_STYLE = eINSTANCE.getBorderStyle_BorderLineStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl
         * <em>Inside Label Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.InsideLabelStyleImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getInsideLabelStyle()
         */
        EClass INSIDE_LABEL_STYLE = eINSTANCE.getInsideLabelStyle();

        /**
         * The meta object literal for the '<em><b>With Header</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute INSIDE_LABEL_STYLE__WITH_HEADER = eINSTANCE.getInsideLabelStyle_WithHeader();

        /**
         * The meta object literal for the '<em><b>Display Header Separator</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute INSIDE_LABEL_STYLE__DISPLAY_HEADER_SEPARATOR = eINSTANCE.getInsideLabelStyle_DisplayHeaderSeparator();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.OutsideLabelStyleImpl
         * <em>Outside Label Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.OutsideLabelStyleImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getOutsideLabelStyle()
         */
        EClass OUTSIDE_LABEL_STYLE = eINSTANCE.getOutsideLabelStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle <em>Node
         * Label Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.NodeLabelStyle
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeLabelStyle()
         */
        EClass NODE_LABEL_STYLE = eINSTANCE.getNodeLabelStyle();

        /**
         * The meta object literal for the '<em><b>Label Color</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference NODE_LABEL_STYLE__LABEL_COLOR = eINSTANCE.getNodeLabelStyle_LabelColor();

        /**
         * The meta object literal for the '<em><b>Background</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference NODE_LABEL_STYLE__BACKGROUND = eINSTANCE.getNodeLabelStyle_Background();

        /**
         * The meta object literal for the '<em><b>Show Icon</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_LABEL_STYLE__SHOW_ICON = eINSTANCE.getNodeLabelStyle_ShowIcon();

        /**
         * The meta object literal for the '<em><b>Label Icon</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_LABEL_STYLE__LABEL_ICON = eINSTANCE.getNodeLabelStyle_LabelIcon();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription
         * <em>Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.NodeStyleDescription
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeStyleDescription()
         */
        EClass NODE_STYLE_DESCRIPTION = eINSTANCE.getNodeStyleDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalNodeStyleImpl <em>Conditional Node
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.ConditionalNodeStyleImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getConditionalNodeStyle()
         */
        EClass CONDITIONAL_NODE_STYLE = eINSTANCE.getConditionalNodeStyle();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONDITIONAL_NODE_STYLE__STYLE = eINSTANCE.getConditionalNodeStyle_Style();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalInsideLabelStyleImpl <em>Conditional
         * Inside Label Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.ConditionalInsideLabelStyleImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getConditionalInsideLabelStyle()
         */
        EClass CONDITIONAL_INSIDE_LABEL_STYLE = eINSTANCE.getConditionalInsideLabelStyle();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONDITIONAL_INSIDE_LABEL_STYLE__STYLE = eINSTANCE.getConditionalInsideLabelStyle_Style();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalOutsideLabelStyleImpl <em>Conditional
         * Outside Label Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.ConditionalOutsideLabelStyleImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getConditionalOutsideLabelStyle()
         */
        EClass CONDITIONAL_OUTSIDE_LABEL_STYLE = eINSTANCE.getConditionalOutsideLabelStyle();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' containment reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CONDITIONAL_OUTSIDE_LABEL_STYLE__STYLE = eINSTANCE.getConditionalOutsideLabelStyle_Style();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.RectangularNodeStyleDescriptionImpl <em>Rectangular
         * Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.RectangularNodeStyleDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getRectangularNodeStyleDescription()
         */
        EClass RECTANGULAR_NODE_STYLE_DESCRIPTION = eINSTANCE.getRectangularNodeStyleDescription();

        /**
         * The meta object literal for the '<em><b>Background</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference RECTANGULAR_NODE_STYLE_DESCRIPTION__BACKGROUND = eINSTANCE.getRectangularNodeStyleDescription_Background();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl <em>Image Node Style
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getImageNodeStyleDescription()
         */
        EClass IMAGE_NODE_STYLE_DESCRIPTION = eINSTANCE.getImageNodeStyleDescription();

        /**
         * The meta object literal for the '<em><b>Shape</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute IMAGE_NODE_STYLE_DESCRIPTION__SHAPE = eINSTANCE.getImageNodeStyleDescription_Shape();

        /**
         * The meta object literal for the '<em><b>Position Dependent Rotation</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION = eINSTANCE.getImageNodeStyleDescription_PositionDependentRotation();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl <em>Icon Label Node
         * Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getIconLabelNodeStyleDescription()
         */
        EClass ICON_LABEL_NODE_STYLE_DESCRIPTION = eINSTANCE.getIconLabelNodeStyleDescription();

        /**
         * The meta object literal for the '<em><b>Background</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference ICON_LABEL_NODE_STYLE_DESCRIPTION__BACKGROUND = eINSTANCE.getIconLabelNodeStyleDescription_Background();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl
         * <em>Edge Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeStyle()
         */
        EClass EDGE_STYLE = eINSTANCE.getEdgeStyle();

        /**
         * The meta object literal for the '<em><b>Line Style</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_STYLE__LINE_STYLE = eINSTANCE.getEdgeStyle_LineStyle();

        /**
         * The meta object literal for the '<em><b>Source Arrow Style</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_STYLE__SOURCE_ARROW_STYLE = eINSTANCE.getEdgeStyle_SourceArrowStyle();

        /**
         * The meta object literal for the '<em><b>Target Arrow Style</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_STYLE__TARGET_ARROW_STYLE = eINSTANCE.getEdgeStyle_TargetArrowStyle();

        /**
         * The meta object literal for the '<em><b>Edge Width</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_STYLE__EDGE_WIDTH = eINSTANCE.getEdgeStyle_EdgeWidth();

        /**
         * The meta object literal for the '<em><b>Show Icon</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_STYLE__SHOW_ICON = eINSTANCE.getEdgeStyle_ShowIcon();

        /**
         * The meta object literal for the '<em><b>Label Icon</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_STYLE__LABEL_ICON = eINSTANCE.getEdgeStyle_LabelIcon();

        /**
         * The meta object literal for the '<em><b>Background</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_STYLE__BACKGROUND = eINSTANCE.getEdgeStyle_Background();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl <em>Conditional Edge
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getConditionalEdgeStyle()
         */
        EClass CONDITIONAL_EDGE_STYLE = eINSTANCE.getConditionalEdgeStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramPaletteImpl
         * <em>Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPaletteImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramPalette()
         */
        EClass DIAGRAM_PALETTE = eINSTANCE.getDiagramPalette();

        /**
         * The meta object literal for the '<em><b>Drop Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_PALETTE__DROP_TOOL = eINSTANCE.getDiagramPalette_DropTool();

        /**
         * The meta object literal for the '<em><b>Drop Node Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_PALETTE__DROP_NODE_TOOL = eINSTANCE.getDiagramPalette_DropNodeTool();

        /**
         * The meta object literal for the '<em><b>Node Tools</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_PALETTE__NODE_TOOLS = eINSTANCE.getDiagramPalette_NodeTools();

        /**
         * The meta object literal for the '<em><b>Tool Sections</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_PALETTE__TOOL_SECTIONS = eINSTANCE.getDiagramPalette_ToolSections();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.NodePaletteImpl
         * <em>Node Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.NodePaletteImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodePalette()
         */
        EClass NODE_PALETTE = eINSTANCE.getNodePalette();

        /**
         * The meta object literal for the '<em><b>Delete Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_PALETTE__DELETE_TOOL = eINSTANCE.getNodePalette_DeleteTool();

        /**
         * The meta object literal for the '<em><b>Label Edit Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_PALETTE__LABEL_EDIT_TOOL = eINSTANCE.getNodePalette_LabelEditTool();

        /**
         * The meta object literal for the '<em><b>Drop Node Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_PALETTE__DROP_NODE_TOOL = eINSTANCE.getNodePalette_DropNodeTool();

        /**
         * The meta object literal for the '<em><b>Node Tools</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_PALETTE__NODE_TOOLS = eINSTANCE.getNodePalette_NodeTools();

        /**
         * The meta object literal for the '<em><b>Edge Tools</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_PALETTE__EDGE_TOOLS = eINSTANCE.getNodePalette_EdgeTools();

        /**
         * The meta object literal for the '<em><b>Tool Sections</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_PALETTE__TOOL_SECTIONS = eINSTANCE.getNodePalette_ToolSections();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl
         * <em>Edge Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgePalette()
         */
        EClass EDGE_PALETTE = eINSTANCE.getEdgePalette();

        /**
         * The meta object literal for the '<em><b>Delete Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__DELETE_TOOL = eINSTANCE.getEdgePalette_DeleteTool();

        /**
         * The meta object literal for the '<em><b>Center Label Edit Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__CENTER_LABEL_EDIT_TOOL = eINSTANCE.getEdgePalette_CenterLabelEditTool();

        /**
         * The meta object literal for the '<em><b>Begin Label Edit Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__BEGIN_LABEL_EDIT_TOOL = eINSTANCE.getEdgePalette_BeginLabelEditTool();

        /**
         * The meta object literal for the '<em><b>End Label Edit Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__END_LABEL_EDIT_TOOL = eINSTANCE.getEdgePalette_EndLabelEditTool();

        /**
         * The meta object literal for the '<em><b>Node Tools</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__NODE_TOOLS = eINSTANCE.getEdgePalette_NodeTools();

        /**
         * The meta object literal for the '<em><b>Edge Reconnection Tools</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__EDGE_RECONNECTION_TOOLS = eINSTANCE.getEdgePalette_EdgeReconnectionTools();

        /**
         * The meta object literal for the '<em><b>Tool Sections</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_PALETTE__TOOL_SECTIONS = eINSTANCE.getEdgePalette_ToolSections();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.ToolImpl
         * <em>Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.ToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getTool()
         */
        EClass TOOL = eINSTANCE.getTool();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute TOOL__NAME = eINSTANCE.getTool_Name();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TOOL__PRECONDITION_EXPRESSION = eINSTANCE.getTool_PreconditionExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TOOL__BODY = eINSTANCE.getTool_Body();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.DeleteToolImpl
         * <em>Delete Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.DeleteToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDeleteTool()
         */
        EClass DELETE_TOOL = eINSTANCE.getDeleteTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.DropToolImpl <em>Drop
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.DropToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDropTool()
         */
        EClass DROP_TOOL = eINSTANCE.getDropTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeToolImpl <em>Edge
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.EdgeToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeTool()
         */
        EClass EDGE_TOOL = eINSTANCE.getEdgeTool();

        /**
         * The meta object literal for the '<em><b>Target Element Descriptions</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS = eINSTANCE.getEdgeTool_TargetElementDescriptions();

        /**
         * The meta object literal for the '<em><b>Icon UR Ls Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute EDGE_TOOL__ICON_UR_LS_EXPRESSION = eINSTANCE.getEdgeTool_IconURLsExpression();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeReconnectionToolImpl <em>Edge Reconnection
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.EdgeReconnectionToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeReconnectionTool()
         */
        EClass EDGE_RECONNECTION_TOOL = eINSTANCE.getEdgeReconnectionTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.LabelEditToolImpl
         * <em>Label Edit Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.LabelEditToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLabelEditTool()
         */
        EClass LABEL_EDIT_TOOL = eINSTANCE.getLabelEditTool();

        /**
         * The meta object literal for the '<em><b>Initial Direct Edit Label Expression</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LABEL_EDIT_TOOL__INITIAL_DIRECT_EDIT_LABEL_EXPRESSION = eINSTANCE.getLabelEditTool_InitialDirectEditLabelExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.NodeToolImpl <em>Node
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.NodeToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeTool()
         */
        EClass NODE_TOOL = eINSTANCE.getNodeTool();

        /**
         * The meta object literal for the '<em><b>Selection Description</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_TOOL__SELECTION_DESCRIPTION = eINSTANCE.getNodeTool_SelectionDescription();

        /**
         * The meta object literal for the '<em><b>Icon UR Ls Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_TOOL__ICON_UR_LS_EXPRESSION = eINSTANCE.getNodeTool_IconURLsExpression();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.SourceEdgeEndReconnectionToolImpl <em>Source Edge End
         * Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.SourceEdgeEndReconnectionToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getSourceEdgeEndReconnectionTool()
         */
        EClass SOURCE_EDGE_END_RECONNECTION_TOOL = eINSTANCE.getSourceEdgeEndReconnectionTool();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.TargetEdgeEndReconnectionToolImpl <em>Target Edge End
         * Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.TargetEdgeEndReconnectionToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getTargetEdgeEndReconnectionTool()
         */
        EClass TARGET_EDGE_END_RECONNECTION_TOOL = eINSTANCE.getTargetEdgeEndReconnectionTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl
         * <em>Create View</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getCreateView()
         */
        EClass CREATE_VIEW = eINSTANCE.getCreateView();

        /**
         * The meta object literal for the '<em><b>Parent View Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_VIEW__PARENT_VIEW_EXPRESSION = eINSTANCE.getCreateView_ParentViewExpression();

        /**
         * The meta object literal for the '<em><b>Element Description</b></em>' reference feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CREATE_VIEW__ELEMENT_DESCRIPTION = eINSTANCE.getCreateView_ElementDescription();

        /**
         * The meta object literal for the '<em><b>Semantic Element Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_VIEW__SEMANTIC_ELEMENT_EXPRESSION = eINSTANCE.getCreateView_SemanticElementExpression();

        /**
         * The meta object literal for the '<em><b>Variable Name</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_VIEW__VARIABLE_NAME = eINSTANCE.getCreateView_VariableName();

        /**
         * The meta object literal for the '<em><b>Containment Kind</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CREATE_VIEW__CONTAINMENT_KIND = eINSTANCE.getCreateView_ContainmentKind();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.DeleteViewImpl
         * <em>Delete View</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.DeleteViewImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDeleteView()
         */
        EClass DELETE_VIEW = eINSTANCE.getDeleteView();

        /**
         * The meta object literal for the '<em><b>View Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DELETE_VIEW__VIEW_EXPRESSION = eINSTANCE.getDeleteView_ViewExpression();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDescriptionImpl <em>Selection
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.SelectionDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getSelectionDescription()
         */
        EClass SELECTION_DESCRIPTION = eINSTANCE.getSelectionDescription();

        /**
         * The meta object literal for the '<em><b>Selection Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SELECTION_DESCRIPTION__SELECTION_CANDIDATES_EXPRESSION = eINSTANCE.getSelectionDescription_SelectionCandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Selection Message</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute SELECTION_DESCRIPTION__SELECTION_MESSAGE = eINSTANCE.getSelectionDescription_SelectionMessage();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.ToolSectionImpl
         * <em>Tool Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.ToolSectionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getToolSection()
         */
        EClass TOOL_SECTION = eINSTANCE.getToolSection();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute TOOL_SECTION__NAME = eINSTANCE.getToolSection_Name();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramToolSectionImpl <em>Tool Section</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramToolSectionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramToolSection()
         */
        EClass DIAGRAM_TOOL_SECTION = eINSTANCE.getDiagramToolSection();

        /**
         * The meta object literal for the '<em><b>Node Tools</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DIAGRAM_TOOL_SECTION__NODE_TOOLS = eINSTANCE.getDiagramToolSection_NodeTools();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.NodeToolSectionImpl
         * <em>Node Tool Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.NodeToolSectionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeToolSection()
         */
        EClass NODE_TOOL_SECTION = eINSTANCE.getNodeToolSection();

        /**
         * The meta object literal for the '<em><b>Node Tools</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_TOOL_SECTION__NODE_TOOLS = eINSTANCE.getNodeToolSection_NodeTools();

        /**
         * The meta object literal for the '<em><b>Edge Tools</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference NODE_TOOL_SECTION__EDGE_TOOLS = eINSTANCE.getNodeToolSection_EdgeTools();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeToolSectionImpl
         * <em>Edge Tool Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.EdgeToolSectionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeToolSection()
         */
        EClass EDGE_TOOL_SECTION = eINSTANCE.getEdgeToolSection();

        /**
         * The meta object literal for the '<em><b>Node Tools</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference EDGE_TOOL_SECTION__NODE_TOOLS = eINSTANCE.getEdgeToolSection_NodeTools();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.DropNodeToolImpl
         * <em>Drop Node Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.impl.DropNodeToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDropNodeTool()
         */
        EClass DROP_NODE_TOOL = eINSTANCE.getDropNodeTool();

        /**
         * The meta object literal for the '<em><b>Accepted Node Types</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DROP_NODE_TOOL__ACCEPTED_NODE_TYPES = eINSTANCE.getDropNodeTool_AcceptedNodeTypes();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.ArrowStyle <em>Arrow
         * Style</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getArrowStyle()
         */
        EEnum ARROW_STYLE = eINSTANCE.getArrowStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.LayoutDirection <em>Layout
         * Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.LayoutDirection
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLayoutDirection()
         */
        EEnum LAYOUT_DIRECTION = eINSTANCE.getLayoutDirection();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.LineStyle <em>Line
         * Style</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.LineStyle
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLineStyle()
         */
        EEnum LINE_STYLE = eINSTANCE.getLineStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.NodeContainmentKind
         * <em>Node Containment Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.NodeContainmentKind
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeContainmentKind()
         */
        EEnum NODE_CONTAINMENT_KIND = eINSTANCE.getNodeContainmentKind();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
         * <em>Synchronization Policy</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getSynchronizationPolicy()
         */
        EEnum SYNCHRONIZATION_POLICY = eINSTANCE.getSynchronizationPolicy();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.InsideLabelPosition
         * <em>Inside Label Position</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.InsideLabelPosition
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getInsideLabelPosition()
         */
        EEnum INSIDE_LABEL_POSITION = eINSTANCE.getInsideLabelPosition();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.OutsideLabelPosition
         * <em>Outside Label Position</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.OutsideLabelPosition
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getOutsideLabelPosition()
         */
        EEnum OUTSIDE_LABEL_POSITION = eINSTANCE.getOutsideLabelPosition();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy
         * <em>Label Overflow Strategy</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLabelOverflowStrategy()
         */
        EEnum LABEL_OVERFLOW_STRATEGY = eINSTANCE.getLabelOverflowStrategy();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection
         * <em>Arrange Layout Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getArrangeLayoutDirection()
         */
        EEnum ARRANGE_LAYOUT_DIRECTION = eINSTANCE.getArrangeLayoutDirection();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.LabelTextAlign <em>Label
         * Text Align</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         * @see org.eclipse.sirius.components.view.diagram.LabelTextAlign
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLabelTextAlign()
         */
        EEnum LABEL_TEXT_ALIGN = eINSTANCE.getLabelTextAlign();

    }

} // DiagramPackage
