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
 * @see org.eclipse.sirius.components.view.diagram.DiagramFactory
 * @model kind="package"
 * @generated
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
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    DiagramPackage eINSTANCE = org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl
     * <em>Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramDescription()
     * @generated
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
    int DIAGRAM_DESCRIPTION__AUTO_LAYOUT = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 0;

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
     * The number of structural features of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION_FEATURE_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The number of operations of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_DESCRIPTION_OPERATION_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramElementDescriptionImpl
     * <em>Element Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramElementDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramElementDescription()
     * @generated
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
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION = 5;

    /**
     * The number of structural features of the '<em>Element Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT = 6;

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
     * @see org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeDescription()
     * @generated
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
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Collapsible</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION__COLLAPSIBLE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 0;

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
     * The number of structural features of the '<em>Node Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION_FEATURE_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 10;

    /**
     * The number of operations of the '<em>Node Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_DESCRIPTION_OPERATION_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl <em>Edge
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeDescription()
     * @generated
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
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION;

    /**
     * The feature id for the '<em><b>Begin Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>End Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__END_LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Is Domain Based Edge</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Palette</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__PALETTE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Source Node Descriptions</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Target Node Descriptions</b></em>' reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Source Nodes Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Target Nodes Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__STYLE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Conditional Styles</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION__CONDITIONAL_STYLES = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 9;

    /**
     * The number of structural features of the '<em>Edge Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION_FEATURE_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 10;

    /**
     * The number of operations of the '<em>Edge Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_DESCRIPTION_OPERATION_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription
     * <em>Layout Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLayoutStrategyDescription()
     * @generated
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
     * @see org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getListLayoutStrategyDescription()
     * @generated
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION = 5;

    /**
     * The number of structural features of the '<em>List Layout Strategy Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT = LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>List Layout Strategy Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LIST_LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT = LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl <em>Free Form
     * Layout Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getFreeFormLayoutStrategyDescription()
     * @generated
     */
    int FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION = 6;

    /**
     * The number of structural features of the '<em>Free Form Layout Strategy Description</em>' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT = LAYOUT_STRATEGY_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Free Form Layout Strategy Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT = LAYOUT_STRATEGY_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.StyleImpl <em>Style</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.StyleImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getStyle()
     * @generated
     */
    int STYLE = 7;

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
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getBorderStyle()
     * @generated
     */
    int BORDER_STYLE = 8;

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
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription <em>Node Style
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.NodeStyleDescription
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeStyleDescription()
     * @generated
     */
    int NODE_STYLE_DESCRIPTION = 9;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__COLOR = STYLE__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__FONT_SIZE = STYLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__ITALIC = STYLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BOLD = STYLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__UNDERLINE = STYLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = STYLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BORDER_COLOR = STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BORDER_RADIUS = STYLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BORDER_SIZE = STYLE_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = STYLE_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__LABEL_COLOR = STYLE_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Width Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION = STYLE_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Height Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION = STYLE_FEATURE_COUNT + 11;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__SHOW_ICON = STYLE_FEATURE_COUNT + 12;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION__LABEL_ICON = STYLE_FEATURE_COUNT + 13;

    /**
     * The number of structural features of the '<em>Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION_FEATURE_COUNT = STYLE_FEATURE_COUNT + 14;

    /**
     * The number of operations of the '<em>Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_STYLE_DESCRIPTION_OPERATION_COUNT = STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalNodeStyleImpl
     * <em>Conditional Node Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.ConditionalNodeStyleImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getConditionalNodeStyle()
     * @generated
     */
    int CONDITIONAL_NODE_STYLE = 10;

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
    int CONDITIONAL_NODE_STYLE__STYLE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

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
    int CONDITIONAL_NODE_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.RectangularNodeStyleDescriptionImpl <em>Rectangular Node
     * Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.RectangularNodeStyleDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getRectangularNodeStyleDescription()
     * @generated
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION = 11;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__COLOR = NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__FONT_SIZE = NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__ITALIC = NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BOLD = NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__UNDERLINE = NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BORDER_COLOR = NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BORDER_SIZE = NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__LABEL_COLOR = NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Width Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION = NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Height Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION = NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__SHOW_ICON = NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__LABEL_ICON = NODE_STYLE_DESCRIPTION__LABEL_ICON;

    /**
     * The feature id for the '<em><b>With Header</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION__WITH_HEADER = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Rectangular Node Style Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Rectangular Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int RECTANGULAR_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl
     * <em>Image Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getImageNodeStyleDescription()
     * @generated
     */
    int IMAGE_NODE_STYLE_DESCRIPTION = 12;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__COLOR = NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__FONT_SIZE = NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__ITALIC = NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BOLD = NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__UNDERLINE = NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR = NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE = NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__LABEL_COLOR = NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Width Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION = NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Height Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION = NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__SHOW_ICON = NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__LABEL_ICON = NODE_STYLE_DESCRIPTION__LABEL_ICON;

    /**
     * The feature id for the '<em><b>Shape</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION__SHAPE = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Image Node Style Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Image Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int IMAGE_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl <em>Icon Label Node
     * Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getIconLabelNodeStyleDescription()
     * @generated
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION = 13;

    /**
     * The feature id for the '<em><b>Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__COLOR = NODE_STYLE_DESCRIPTION__COLOR;

    /**
     * The feature id for the '<em><b>Font Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__FONT_SIZE = NODE_STYLE_DESCRIPTION__FONT_SIZE;

    /**
     * The feature id for the '<em><b>Italic</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__ITALIC = NODE_STYLE_DESCRIPTION__ITALIC;

    /**
     * The feature id for the '<em><b>Bold</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BOLD = NODE_STYLE_DESCRIPTION__BOLD;

    /**
     * The feature id for the '<em><b>Underline</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__UNDERLINE = NODE_STYLE_DESCRIPTION__UNDERLINE;

    /**
     * The feature id for the '<em><b>Strike Through</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__STRIKE_THROUGH = NODE_STYLE_DESCRIPTION__STRIKE_THROUGH;

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
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

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
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__LABEL_COLOR = NODE_STYLE_DESCRIPTION__LABEL_COLOR;

    /**
     * The feature id for the '<em><b>Width Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION = NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Height Computation Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION = NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__SHOW_ICON = NODE_STYLE_DESCRIPTION__SHOW_ICON;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION__LABEL_ICON = NODE_STYLE_DESCRIPTION__LABEL_ICON;

    /**
     * The number of structural features of the '<em>Icon Label Node Style Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Icon Label Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ICON_LABEL_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl <em>Edge
     * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeStyle()
     * @generated
     */
    int EDGE_STYLE = 14;

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
    int EDGE_STYLE__FONT_SIZE = STYLE_FEATURE_COUNT + 0;

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
     * The feature id for the '<em><b>Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__LINE_STYLE = STYLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Source Arrow Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__SOURCE_ARROW_STYLE = STYLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Target Arrow Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__TARGET_ARROW_STYLE = STYLE_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Edge Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__EDGE_WIDTH = STYLE_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__SHOW_ICON = STYLE_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE__LABEL_ICON = STYLE_FEATURE_COUNT + 10;

    /**
     * The number of structural features of the '<em>Edge Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE_FEATURE_COUNT = STYLE_FEATURE_COUNT + 11;

    /**
     * The number of operations of the '<em>Edge Style</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_STYLE_OPERATION_COUNT = STYLE_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl
     * <em>Conditional Edge Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getConditionalEdgeStyle()
     * @generated
     */
    int CONDITIONAL_EDGE_STYLE = 15;

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
    int CONDITIONAL_EDGE_STYLE__COLOR = ViewPackage.CONDITIONAL_FEATURE_COUNT + 0;

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
     * The feature id for the '<em><b>Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__LINE_STYLE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Source Arrow Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__SOURCE_ARROW_STYLE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Target Arrow Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__TARGET_ARROW_STYLE = ViewPackage.CONDITIONAL_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Edge Width</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__EDGE_WIDTH = ViewPackage.CONDITIONAL_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Show Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__SHOW_ICON = ViewPackage.CONDITIONAL_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE__LABEL_ICON = ViewPackage.CONDITIONAL_FEATURE_COUNT + 11;

    /**
     * The number of structural features of the '<em>Conditional Edge Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE_FEATURE_COUNT = ViewPackage.CONDITIONAL_FEATURE_COUNT + 12;

    /**
     * The number of operations of the '<em>Conditional Edge Style</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CONDITIONAL_EDGE_STYLE_OPERATION_COUNT = ViewPackage.CONDITIONAL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramPaletteImpl
     * <em>Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPaletteImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramPalette()
     * @generated
     */
    int DIAGRAM_PALETTE = 16;

    /**
     * The feature id for the '<em><b>Drop Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE__DROP_TOOL = 0;

    /**
     * The feature id for the '<em><b>Node Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE__NODE_TOOLS = 1;

    /**
     * The feature id for the '<em><b>Tool Sections</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE__TOOL_SECTIONS = 2;

    /**
     * The number of structural features of the '<em>Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DIAGRAM_PALETTE_FEATURE_COUNT = 3;

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
     * @see org.eclipse.sirius.components.view.diagram.impl.NodePaletteImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodePalette()
     * @generated
     */
    int NODE_PALETTE = 17;

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
     * The feature id for the '<em><b>Node Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__NODE_TOOLS = 2;

    /**
     * The feature id for the '<em><b>Edge Tools</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__EDGE_TOOLS = 3;

    /**
     * The feature id for the '<em><b>Tool Sections</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE__TOOL_SECTIONS = 4;

    /**
     * The number of structural features of the '<em>Node Palette</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_PALETTE_FEATURE_COUNT = 5;

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
     * @see org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgePalette()
     * @generated
     */
    int EDGE_PALETTE = 18;

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
     * @see org.eclipse.sirius.components.view.diagram.impl.ToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getTool()
     * @generated
     */
    int TOOL = 19;

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
     * @see org.eclipse.sirius.components.view.diagram.impl.DeleteToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDeleteTool()
     * @generated
     */
    int DELETE_TOOL = 20;

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
    int DELETE_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Delete Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DropToolImpl <em>Drop
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.DropToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDropTool()
     * @generated
     */
    int DROP_TOOL = 21;

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
    int DROP_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Drop Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DROP_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeToolImpl <em>Edge
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.EdgeToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeTool()
     * @generated
     */
    int EDGE_TOOL = 22;

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
    int EDGE_TOOL__TARGET_ELEMENT_DESCRIPTIONS = TOOL_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Edge Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Edge Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeReconnectionToolImpl
     * <em>Edge Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.EdgeReconnectionToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeReconnectionTool()
     * @generated
     */
    int EDGE_RECONNECTION_TOOL = 23;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL__NAME = TOOL__NAME;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL__PRECONDITION_EXPRESSION = TOOL__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL__BODY = TOOL__BODY;

    /**
     * The number of structural features of the '<em>Edge Reconnection Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Edge Reconnection Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDGE_RECONNECTION_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.LabelEditToolImpl <em>Label
     * Edit Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.LabelEditToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLabelEditTool()
     * @generated
     */
    int LABEL_EDIT_TOOL = 24;

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
    int LABEL_EDIT_TOOL__INITIAL_DIRECT_EDIT_LABEL_EXPRESSION = TOOL_FEATURE_COUNT + 0;

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
    int LABEL_EDIT_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.NodeToolImpl <em>Node
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.NodeToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeTool()
     * @generated
     */
    int NODE_TOOL = 25;

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
    int NODE_TOOL__SELECTION_DESCRIPTION = TOOL_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Node Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL_FEATURE_COUNT = TOOL_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Node Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int NODE_TOOL_OPERATION_COUNT = TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.SourceEdgeEndReconnectionToolImpl <em>Source Edge End
     * Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.SourceEdgeEndReconnectionToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getSourceEdgeEndReconnectionTool()
     * @generated
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL = 26;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL__NAME = EDGE_RECONNECTION_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL__PRECONDITION_EXPRESSION = EDGE_RECONNECTION_TOOL__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL__BODY = EDGE_RECONNECTION_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Source Edge End Reconnection Tool</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL_FEATURE_COUNT = EDGE_RECONNECTION_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Source Edge End Reconnection Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int SOURCE_EDGE_END_RECONNECTION_TOOL_OPERATION_COUNT = EDGE_RECONNECTION_TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.impl.TargetEdgeEndReconnectionToolImpl <em>Target Edge End
     * Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.TargetEdgeEndReconnectionToolImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getTargetEdgeEndReconnectionTool()
     * @generated
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL = 27;

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
    int TARGET_EDGE_END_RECONNECTION_TOOL__PRECONDITION_EXPRESSION = EDGE_RECONNECTION_TOOL__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL__BODY = EDGE_RECONNECTION_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Target Edge End Reconnection Tool</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL_FEATURE_COUNT = EDGE_RECONNECTION_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Target Edge End Reconnection Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TARGET_EDGE_END_RECONNECTION_TOOL_OPERATION_COUNT = EDGE_RECONNECTION_TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl <em>Create
     * View</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getCreateView()
     * @generated
     */
    int CREATE_VIEW = 28;

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
    int CREATE_VIEW__PARENT_VIEW_EXPRESSION = ViewPackage.OPERATION_FEATURE_COUNT + 0;

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
    int CREATE_VIEW_OPERATION_COUNT = ViewPackage.OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.DeleteViewImpl <em>Delete
     * View</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.DeleteViewImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDeleteView()
     * @generated
     */
    int DELETE_VIEW = 29;

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
    int DELETE_VIEW__VIEW_EXPRESSION = ViewPackage.OPERATION_FEATURE_COUNT + 0;

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
    int DELETE_VIEW_OPERATION_COUNT = ViewPackage.OPERATION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDescriptionImpl
     * <em>Selection Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.SelectionDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getSelectionDescription()
     * @generated
     */
    int SELECTION_DESCRIPTION = 30;

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
     * @see org.eclipse.sirius.components.view.diagram.impl.ToolSectionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getToolSection()
     * @generated
     */
    int TOOL_SECTION = 31;

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
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramToolSectionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramToolSection()
     * @generated
     */
    int DIAGRAM_TOOL_SECTION = 32;

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
    int DIAGRAM_TOOL_SECTION__NODE_TOOLS = TOOL_SECTION_FEATURE_COUNT + 0;

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
    int DIAGRAM_TOOL_SECTION_OPERATION_COUNT = TOOL_SECTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.NodeToolSectionImpl <em>Node
     * Tool Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.NodeToolSectionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeToolSection()
     * @generated
     */
    int NODE_TOOL_SECTION = 33;

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
    int NODE_TOOL_SECTION__NODE_TOOLS = TOOL_SECTION_FEATURE_COUNT + 0;

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
    int NODE_TOOL_SECTION_OPERATION_COUNT = TOOL_SECTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeToolSectionImpl <em>Edge
     * Tool Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.impl.EdgeToolSectionImpl
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeToolSection()
     * @generated
     */
    int EDGE_TOOL_SECTION = 34;

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
    int EDGE_TOOL_SECTION__NODE_TOOLS = TOOL_SECTION_FEATURE_COUNT + 0;

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
    int EDGE_TOOL_SECTION_OPERATION_COUNT = TOOL_SECTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.ArrowStyle <em>Arrow Style</em>}'
     * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getArrowStyle()
     * @generated
     */
    int ARROW_STYLE = 35;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.LayoutDirection <em>Layout
     * Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.LayoutDirection
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLayoutDirection()
     * @generated
     */
    int LAYOUT_DIRECTION = 36;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.LineStyle <em>Line Style</em>}'
     * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.LineStyle
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLineStyle()
     * @generated
     */
    int LINE_STYLE = 37;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.NodeContainmentKind <em>Node
     * Containment Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.NodeContainmentKind
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeContainmentKind()
     * @generated
     */
    int NODE_CONTAINMENT_KIND = 38;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
     * <em>Synchronization Policy</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
     * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getSynchronizationPolicy()
     * @generated
     */
    int SYNCHRONIZATION_POLICY = 39;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription
     * <em>Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramDescription
     * @generated
     */
    EClass getDiagramDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#isAutoLayout <em>Auto Layout</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Auto Layout</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramDescription#isAutoLayout()
     * @see #getDiagramDescription()
     * @generated
     */
    EAttribute getDiagramDescription_AutoLayout();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getPalette <em>Palette</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Palette</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramDescription#getPalette()
     * @see #getDiagramDescription()
     * @generated
     */
    EReference getDiagramDescription_Palette();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getNodeDescriptions <em>Node
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramDescription#getNodeDescriptions()
     * @see #getDiagramDescription()
     * @generated
     */
    EReference getDiagramDescription_NodeDescriptions();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramDescription#getEdgeDescriptions <em>Edge
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Edge Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramDescription#getEdgeDescriptions()
     * @see #getDiagramDescription()
     * @generated
     */
    EReference getDiagramDescription_EdgeDescriptions();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription
     * <em>Element Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Element Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription
     * @generated
     */
    EClass getDiagramElementDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getName <em>Name</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getName()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EAttribute getDiagramElementDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getDomainType <em>Domain
     * Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Domain Type</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getDomainType()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EAttribute getDiagramElementDescription_DomainType();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getSemanticCandidatesExpression
     * <em>Semantic Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getSemanticCandidatesExpression()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EAttribute getDiagramElementDescription_SemanticCandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getPreconditionExpression
     * <em>Precondition Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getPreconditionExpression()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EAttribute getDiagramElementDescription_PreconditionExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getSynchronizationPolicy
     * <em>Synchronization Policy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Synchronization Policy</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getSynchronizationPolicy()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EAttribute getDiagramElementDescription_SynchronizationPolicy();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getLabelExpression <em>Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramElementDescription#getLabelExpression()
     * @see #getDiagramElementDescription()
     * @generated
     */
    EAttribute getDiagramElementDescription_LabelExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.NodeDescription <em>Node
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription
     * @generated
     */
    EClass getNodeDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isCollapsible <em>Collapsible</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Collapsible</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#isCollapsible()
     * @see #getNodeDescription()
     * @generated
     */
    EAttribute getNodeDescription_Collapsible();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getPalette <em>Palette</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Palette</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getPalette()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_Palette();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getChildrenLayoutStrategy <em>Children Layout
     * Strategy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Children Layout Strategy</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getChildrenLayoutStrategy()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_ChildrenLayoutStrategy();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getStyle()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getConditionalStyles()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_ConditionalStyles();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getChildrenDescriptions <em>Children
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Children Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getChildrenDescriptions()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_ChildrenDescriptions();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getBorderNodesDescriptions <em>Border Nodes
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Border Nodes Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getBorderNodesDescriptions()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_BorderNodesDescriptions();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getReusedChildNodeDescriptions <em>Reused
     * Child Node Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Reused Child Node Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getReusedChildNodeDescriptions()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_ReusedChildNodeDescriptions();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#getReusedBorderNodeDescriptions <em>Reused
     * Border Node Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Reused Border Node Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#getReusedBorderNodeDescriptions()
     * @see #getNodeDescription()
     * @generated
     */
    EReference getNodeDescription_ReusedBorderNodeDescriptions();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeDescription#isUserResizable <em>User Resizable</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>User Resizable</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeDescription#isUserResizable()
     * @see #getNodeDescription()
     * @generated
     */
    EAttribute getNodeDescription_UserResizable();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription <em>Edge
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription
     * @generated
     */
    EClass getEdgeDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getBeginLabelExpression <em>Begin Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Begin Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getBeginLabelExpression()
     * @see #getEdgeDescription()
     * @generated
     */
    EAttribute getEdgeDescription_BeginLabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getEndLabelExpression <em>End Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>End Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getEndLabelExpression()
     * @see #getEdgeDescription()
     * @generated
     */
    EAttribute getEdgeDescription_EndLabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#isIsDomainBasedEdge <em>Is Domain Based
     * Edge</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Is Domain Based Edge</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#isIsDomainBasedEdge()
     * @see #getEdgeDescription()
     * @generated
     */
    EAttribute getEdgeDescription_IsDomainBasedEdge();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getPalette <em>Palette</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Palette</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getPalette()
     * @see #getEdgeDescription()
     * @generated
     */
    EReference getEdgeDescription_Palette();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodeDescriptions <em>Source Node
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Source Node Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodeDescriptions()
     * @see #getEdgeDescription()
     * @generated
     */
    EReference getEdgeDescription_SourceNodeDescriptions();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodeDescriptions <em>Target Node
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Target Node Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodeDescriptions()
     * @see #getEdgeDescription()
     * @generated
     */
    EReference getEdgeDescription_TargetNodeDescriptions();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodesExpression <em>Source Nodes
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Source Nodes Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getSourceNodesExpression()
     * @see #getEdgeDescription()
     * @generated
     */
    EAttribute getEdgeDescription_SourceNodesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodesExpression <em>Target Nodes
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Target Nodes Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getTargetNodesExpression()
     * @see #getEdgeDescription()
     * @generated
     */
    EAttribute getEdgeDescription_TargetNodesExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getStyle <em>Style</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getStyle()
     * @see #getEdgeDescription()
     * @generated
     */
    EReference getEdgeDescription_Style();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeDescription#getConditionalStyles <em>Conditional
     * Styles</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Conditional Styles</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeDescription#getConditionalStyles()
     * @see #getEdgeDescription()
     * @generated
     */
    EReference getEdgeDescription_ConditionalStyles();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription
     * <em>Layout Strategy Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Layout Strategy Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription
     * @generated
     */
    EClass getLayoutStrategyDescription();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription <em>List Layout Strategy
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>List Layout Strategy Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription
     * @generated
     */
    EClass getListLayoutStrategyDescription();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription <em>Free Form Layout
     * Strategy Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Free Form Layout Strategy Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription
     * @generated
     */
    EClass getFreeFormLayoutStrategyDescription();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.Style <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.Style
     * @generated
     */
    EClass getStyle();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.sirius.components.view.diagram.Style#getColor
     * <em>Color</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Color</em>'.
     * @see org.eclipse.sirius.components.view.diagram.Style#getColor()
     * @see #getStyle()
     * @generated
     */
    EReference getStyle_Color();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.BorderStyle <em>Border
     * Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Border Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle
     * @generated
     */
    EClass getBorderStyle();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderColor <em>Border Color</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Border Color</em>'.
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderColor()
     * @see #getBorderStyle()
     * @generated
     */
    EReference getBorderStyle_BorderColor();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderRadius <em>Border Radius</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Border Radius</em>'.
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderRadius()
     * @see #getBorderStyle()
     * @generated
     */
    EAttribute getBorderStyle_BorderRadius();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderSize <em>Border Size</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Border Size</em>'.
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderSize()
     * @see #getBorderStyle()
     * @generated
     */
    EAttribute getBorderStyle_BorderSize();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderLineStyle <em>Border Line Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Border Line Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderLineStyle()
     * @see #getBorderStyle()
     * @generated
     */
    EAttribute getBorderStyle_BorderLineStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription
     * <em>Node Style Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Style Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeStyleDescription
     * @generated
     */
    EClass getNodeStyleDescription();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription#getLabelColor <em>Label Color</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Label Color</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeStyleDescription#getLabelColor()
     * @see #getNodeStyleDescription()
     * @generated
     */
    EReference getNodeStyleDescription_LabelColor();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription#getWidthComputationExpression <em>Width
     * Computation Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Width Computation Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeStyleDescription#getWidthComputationExpression()
     * @see #getNodeStyleDescription()
     * @generated
     */
    EAttribute getNodeStyleDescription_WidthComputationExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription#getHeightComputationExpression <em>Height
     * Computation Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Height Computation Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeStyleDescription#getHeightComputationExpression()
     * @see #getNodeStyleDescription()
     * @generated
     */
    EAttribute getNodeStyleDescription_HeightComputationExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription#isShowIcon <em>Show Icon</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Show Icon</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeStyleDescription#isShowIcon()
     * @see #getNodeStyleDescription()
     * @generated
     */
    EAttribute getNodeStyleDescription_ShowIcon();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription#getLabelIcon <em>Label Icon</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Icon</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeStyleDescription#getLabelIcon()
     * @see #getNodeStyleDescription()
     * @generated
     */
    EAttribute getNodeStyleDescription_LabelIcon();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle
     * <em>Conditional Node Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Node Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle
     * @generated
     */
    EClass getConditionalNodeStyle();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle#getStyle()
     * @see #getConditionalNodeStyle()
     * @generated
     */
    EReference getConditionalNodeStyle_Style();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription <em>Rectangular Node Style
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Rectangular Node Style Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription
     * @generated
     */
    EClass getRectangularNodeStyleDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription#isWithHeader <em>With
     * Header</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>With Header</em>'.
     * @see org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription#isWithHeader()
     * @see #getRectangularNodeStyleDescription()
     * @generated
     */
    EAttribute getRectangularNodeStyleDescription_WithHeader();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription
     * <em>Image Node Style Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Image Node Style Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription
     * @generated
     */
    EClass getImageNodeStyleDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription#getShape <em>Shape</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Shape</em>'.
     * @see org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription#getShape()
     * @see #getImageNodeStyleDescription()
     * @generated
     */
    EAttribute getImageNodeStyleDescription_Shape();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription <em>Icon Label Node Style
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Icon Label Node Style Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription
     * @generated
     */
    EClass getIconLabelNodeStyleDescription();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle <em>Edge
     * Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle
     * @generated
     */
    EClass getEdgeStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getLineStyle <em>Line Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Line Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#getLineStyle()
     * @see #getEdgeStyle()
     * @generated
     */
    EAttribute getEdgeStyle_LineStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getSourceArrowStyle <em>Source Arrow Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Source Arrow Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#getSourceArrowStyle()
     * @see #getEdgeStyle()
     * @generated
     */
    EAttribute getEdgeStyle_SourceArrowStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getTargetArrowStyle <em>Target Arrow Style</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Target Arrow Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#getTargetArrowStyle()
     * @see #getEdgeStyle()
     * @generated
     */
    EAttribute getEdgeStyle_TargetArrowStyle();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getEdgeWidth <em>Edge Width</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Edge Width</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#getEdgeWidth()
     * @see #getEdgeStyle()
     * @generated
     */
    EAttribute getEdgeStyle_EdgeWidth();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#isShowIcon
     * <em>Show Icon</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Show Icon</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#isShowIcon()
     * @see #getEdgeStyle()
     * @generated
     */
    EAttribute getEdgeStyle_ShowIcon();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeStyle#getLabelIcon <em>Label Icon</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Icon</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeStyle#getLabelIcon()
     * @see #getEdgeStyle()
     * @generated
     */
    EAttribute getEdgeStyle_LabelIcon();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle
     * <em>Conditional Edge Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Conditional Edge Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle
     * @generated
     */
    EClass getConditionalEdgeStyle();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DiagramPalette
     * <em>Palette</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Palette</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPalette
     * @generated
     */
    EClass getDiagramPalette();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramPalette#getDropTool <em>Drop Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Drop Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPalette#getDropTool()
     * @see #getDiagramPalette()
     * @generated
     */
    EReference getDiagramPalette_DropTool();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramPalette#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPalette#getNodeTools()
     * @see #getDiagramPalette()
     * @generated
     */
    EReference getDiagramPalette_NodeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramPalette#getToolSections <em>Tool Sections</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Tool Sections</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramPalette#getToolSections()
     * @see #getDiagramPalette()
     * @generated
     */
    EReference getDiagramPalette_ToolSections();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.NodePalette <em>Node
     * Palette</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Palette</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodePalette
     * @generated
     */
    EClass getNodePalette();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodePalette#getDeleteTool <em>Delete Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Delete Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodePalette#getDeleteTool()
     * @see #getNodePalette()
     * @generated
     */
    EReference getNodePalette_DeleteTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodePalette#getLabelEditTool <em>Label Edit Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Label Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodePalette#getLabelEditTool()
     * @see #getNodePalette()
     * @generated
     */
    EReference getNodePalette_LabelEditTool();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodePalette#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodePalette#getNodeTools()
     * @see #getNodePalette()
     * @generated
     */
    EReference getNodePalette_NodeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodePalette#getEdgeTools <em>Edge Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Edge Tools</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodePalette#getEdgeTools()
     * @see #getNodePalette()
     * @generated
     */
    EReference getNodePalette_EdgeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodePalette#getToolSections <em>Tool Sections</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Tool Sections</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodePalette#getToolSections()
     * @see #getNodePalette()
     * @generated
     */
    EReference getNodePalette_ToolSections();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.EdgePalette <em>Edge
     * Palette</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Palette</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette
     * @generated
     */
    EClass getEdgePalette();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getDeleteTool <em>Delete Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Delete Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getDeleteTool()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_DeleteTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getCenterLabelEditTool <em>Center Label Edit
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Center Label Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getCenterLabelEditTool()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_CenterLabelEditTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getBeginLabelEditTool <em>Begin Label Edit
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Begin Label Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getBeginLabelEditTool()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_BeginLabelEditTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getEndLabelEditTool <em>End Label Edit
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>End Label Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getEndLabelEditTool()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_EndLabelEditTool();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getNodeTools()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_NodeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getEdgeReconnectionTools <em>Edge Reconnection
     * Tools</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Edge Reconnection Tools</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getEdgeReconnectionTools()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_EdgeReconnectionTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgePalette#getToolSections <em>Tool Sections</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Tool Sections</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgePalette#getToolSections()
     * @see #getEdgePalette()
     * @generated
     */
    EReference getEdgePalette_ToolSections();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.Tool <em>Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.Tool
     * @generated
     */
    EClass getTool();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.diagram.Tool#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.diagram.Tool#getName()
     * @see #getTool()
     * @generated
     */
    EAttribute getTool_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.Tool#getPreconditionExpression <em>Precondition
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.Tool#getPreconditionExpression()
     * @see #getTool()
     * @generated
     */
    EAttribute getTool_PreconditionExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.Tool#getBody <em>Body</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.diagram.Tool#getBody()
     * @see #getTool()
     * @generated
     */
    EReference getTool_Body();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DeleteTool <em>Delete
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Delete Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DeleteTool
     * @generated
     */
    EClass getDeleteTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DropTool <em>Drop
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Drop Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DropTool
     * @generated
     */
    EClass getDropTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.EdgeTool <em>Edge
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeTool
     * @generated
     */
    EClass getEdgeTool();

    /**
     * Returns the meta object for the reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeTool#getTargetElementDescriptions <em>Target Element
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Target Element Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeTool#getTargetElementDescriptions()
     * @see #getEdgeTool()
     * @generated
     */
    EReference getEdgeTool_TargetElementDescriptions();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool
     * <em>Edge Reconnection Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Reconnection Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool
     * @generated
     */
    EClass getEdgeReconnectionTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.LabelEditTool <em>Label Edit
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Label Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.LabelEditTool
     * @generated
     */
    EClass getLabelEditTool();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.LabelEditTool#getInitialDirectEditLabelExpression <em>Initial
     * Direct Edit Label Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Initial Direct Edit Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.LabelEditTool#getInitialDirectEditLabelExpression()
     * @see #getLabelEditTool()
     * @generated
     */
    EAttribute getLabelEditTool_InitialDirectEditLabelExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.NodeTool <em>Node
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeTool
     * @generated
     */
    EClass getNodeTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.diagram.NodeTool#getSelectionDescription <em>Selection
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Selection Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeTool#getSelectionDescription()
     * @see #getNodeTool()
     * @generated
     */
    EReference getNodeTool_SelectionDescription();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool <em>Source Edge End Reconnection
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Source Edge End Reconnection Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool
     * @generated
     */
    EClass getSourceEdgeEndReconnectionTool();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool <em>Target Edge End Reconnection
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Target Edge End Reconnection Tool</em>'.
     * @see org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool
     * @generated
     */
    EClass getTargetEdgeEndReconnectionTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.CreateView <em>Create
     * View</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Create View</em>'.
     * @see org.eclipse.sirius.components.view.diagram.CreateView
     * @generated
     */
    EClass getCreateView();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.CreateView#getParentViewExpression <em>Parent View
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Parent View Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.CreateView#getParentViewExpression()
     * @see #getCreateView()
     * @generated
     */
    EAttribute getCreateView_ParentViewExpression();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.diagram.CreateView#getElementDescription <em>Element
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Element Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.CreateView#getElementDescription()
     * @see #getCreateView()
     * @generated
     */
    EReference getCreateView_ElementDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.CreateView#getSemanticElementExpression <em>Semantic Element
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Element Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.CreateView#getSemanticElementExpression()
     * @see #getCreateView()
     * @generated
     */
    EAttribute getCreateView_SemanticElementExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.CreateView#getVariableName <em>Variable Name</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Variable Name</em>'.
     * @see org.eclipse.sirius.components.view.diagram.CreateView#getVariableName()
     * @see #getCreateView()
     * @generated
     */
    EAttribute getCreateView_VariableName();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.CreateView#getContainmentKind <em>Containment Kind</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Containment Kind</em>'.
     * @see org.eclipse.sirius.components.view.diagram.CreateView#getContainmentKind()
     * @see #getCreateView()
     * @generated
     */
    EAttribute getCreateView_ContainmentKind();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DeleteView <em>Delete
     * View</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Delete View</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DeleteView
     * @generated
     */
    EClass getDeleteView();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.DeleteView#getViewExpression <em>View Expression</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>View Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DeleteView#getViewExpression()
     * @see #getDeleteView()
     * @generated
     */
    EAttribute getDeleteView_ViewExpression();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.SelectionDescription
     * <em>Selection Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Selection Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.SelectionDescription
     * @generated
     */
    EClass getSelectionDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDescription#getSelectionCandidatesExpression
     * <em>Selection Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Selection Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.diagram.SelectionDescription#getSelectionCandidatesExpression()
     * @see #getSelectionDescription()
     * @generated
     */
    EAttribute getSelectionDescription_SelectionCandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.diagram.SelectionDescription#getSelectionMessage <em>Selection
     * Message</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Selection Message</em>'.
     * @see org.eclipse.sirius.components.view.diagram.SelectionDescription#getSelectionMessage()
     * @see #getSelectionDescription()
     * @generated
     */
    EAttribute getSelectionDescription_SelectionMessage();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.ToolSection <em>Tool
     * Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Tool Section</em>'.
     * @see org.eclipse.sirius.components.view.diagram.ToolSection
     * @generated
     */
    EClass getToolSection();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.diagram.ToolSection#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.diagram.ToolSection#getName()
     * @see #getToolSection()
     * @generated
     */
    EAttribute getToolSection_Name();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.DiagramToolSection <em>Tool
     * Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Tool Section</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramToolSection
     * @generated
     */
    EClass getDiagramToolSection();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.DiagramToolSection#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @see org.eclipse.sirius.components.view.diagram.DiagramToolSection#getNodeTools()
     * @see #getDiagramToolSection()
     * @generated
     */
    EReference getDiagramToolSection_NodeTools();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.NodeToolSection <em>Node
     * Tool Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Node Tool Section</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeToolSection
     * @generated
     */
    EClass getNodeToolSection();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeToolSection#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeToolSection#getNodeTools()
     * @see #getNodeToolSection()
     * @generated
     */
    EReference getNodeToolSection_NodeTools();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.NodeToolSection#getEdgeTools <em>Edge Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Edge Tools</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeToolSection#getEdgeTools()
     * @see #getNodeToolSection()
     * @generated
     */
    EReference getNodeToolSection_EdgeTools();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.diagram.EdgeToolSection <em>Edge
     * Tool Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edge Tool Section</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeToolSection
     * @generated
     */
    EClass getEdgeToolSection();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.diagram.EdgeToolSection#getNodeTools <em>Node Tools</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Node Tools</em>'.
     * @see org.eclipse.sirius.components.view.diagram.EdgeToolSection#getNodeTools()
     * @see #getEdgeToolSection()
     * @generated
     */
    EReference getEdgeToolSection_NodeTools();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.ArrowStyle <em>Arrow
     * Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Arrow Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
     * @generated
     */
    EEnum getArrowStyle();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.LayoutDirection <em>Layout
     * Direction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Layout Direction</em>'.
     * @see org.eclipse.sirius.components.view.diagram.LayoutDirection
     * @generated
     */
    EEnum getLayoutDirection();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.LineStyle <em>Line
     * Style</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Line Style</em>'.
     * @see org.eclipse.sirius.components.view.diagram.LineStyle
     * @generated
     */
    EEnum getLineStyle();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.NodeContainmentKind <em>Node
     * Containment Kind</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Node Containment Kind</em>'.
     * @see org.eclipse.sirius.components.view.diagram.NodeContainmentKind
     * @generated
     */
    EEnum getNodeContainmentKind();

    /**
     * Returns the meta object for enum '{@link org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
     * <em>Synchronization Policy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for enum '<em>Synchronization Policy</em>'.
     * @see org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
     * @generated
     */
    EEnum getSynchronizationPolicy();

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
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramDescription()
         * @generated
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
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramElementDescriptionImpl <em>Element
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramElementDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramElementDescription()
         * @generated
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
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getDiagramElementDescription_LabelExpression();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl
         * <em>Node Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.NodeDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeDescription()
         * @generated
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
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl
         * <em>Edge Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.EdgeDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeDescription()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLayoutStrategyDescription()
         * @generated
         */
        EClass LAYOUT_STRATEGY_DESCRIPTION = eINSTANCE.getLayoutStrategyDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl <em>List Layout
         * Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.ListLayoutStrategyDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getListLayoutStrategyDescription()
         * @generated
         */
        EClass LIST_LAYOUT_STRATEGY_DESCRIPTION = eINSTANCE.getListLayoutStrategyDescription();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl <em>Free Form
         * Layout Strategy Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.FreeFormLayoutStrategyDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getFreeFormLayoutStrategyDescription()
         * @generated
         */
        EClass FREE_FORM_LAYOUT_STRATEGY_DESCRIPTION = eINSTANCE.getFreeFormLayoutStrategyDescription();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.StyleImpl
         * <em>Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.StyleImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getStyle()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.BorderStyle
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getBorderStyle()
         * @generated
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
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.NodeStyleDescription
         * <em>Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.NodeStyleDescription
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeStyleDescription()
         * @generated
         */
        EClass NODE_STYLE_DESCRIPTION = eINSTANCE.getNodeStyleDescription();

        /**
         * The meta object literal for the '<em><b>Label Color</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference NODE_STYLE_DESCRIPTION__LABEL_COLOR = eINSTANCE.getNodeStyleDescription_LabelColor();

        /**
         * The meta object literal for the '<em><b>Width Computation Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION = eINSTANCE.getNodeStyleDescription_WidthComputationExpression();

        /**
         * The meta object literal for the '<em><b>Height Computation Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION = eINSTANCE.getNodeStyleDescription_HeightComputationExpression();

        /**
         * The meta object literal for the '<em><b>Show Icon</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_STYLE_DESCRIPTION__SHOW_ICON = eINSTANCE.getNodeStyleDescription_ShowIcon();

        /**
         * The meta object literal for the '<em><b>Label Icon</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute NODE_STYLE_DESCRIPTION__LABEL_ICON = eINSTANCE.getNodeStyleDescription_LabelIcon();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalNodeStyleImpl <em>Conditional Node
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.ConditionalNodeStyleImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getConditionalNodeStyle()
         * @generated
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
         * '{@link org.eclipse.sirius.components.view.diagram.impl.RectangularNodeStyleDescriptionImpl <em>Rectangular
         * Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.RectangularNodeStyleDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getRectangularNodeStyleDescription()
         * @generated
         */
        EClass RECTANGULAR_NODE_STYLE_DESCRIPTION = eINSTANCE.getRectangularNodeStyleDescription();

        /**
         * The meta object literal for the '<em><b>With Header</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute RECTANGULAR_NODE_STYLE_DESCRIPTION__WITH_HEADER = eINSTANCE.getRectangularNodeStyleDescription_WithHeader();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl <em>Image Node Style
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.ImageNodeStyleDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getImageNodeStyleDescription()
         * @generated
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
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl <em>Icon Label Node
         * Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.IconLabelNodeStyleDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getIconLabelNodeStyleDescription()
         * @generated
         */
        EClass ICON_LABEL_NODE_STYLE_DESCRIPTION = eINSTANCE.getIconLabelNodeStyleDescription();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl
         * <em>Edge Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.EdgeStyleImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeStyle()
         * @generated
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
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl <em>Conditional Edge
         * Style</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.ConditionalEdgeStyleImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getConditionalEdgeStyle()
         * @generated
         */
        EClass CONDITIONAL_EDGE_STYLE = eINSTANCE.getConditionalEdgeStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.DiagramPaletteImpl
         * <em>Palette</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPaletteImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramPalette()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.impl.NodePaletteImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodePalette()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.impl.EdgePaletteImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgePalette()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.impl.ToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getTool()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.impl.DeleteToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDeleteTool()
         * @generated
         */
        EClass DELETE_TOOL = eINSTANCE.getDeleteTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.DropToolImpl <em>Drop
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.DropToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDropTool()
         * @generated
         */
        EClass DROP_TOOL = eINSTANCE.getDropTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeToolImpl <em>Edge
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.EdgeToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeTool()
         * @generated
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
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.EdgeReconnectionToolImpl <em>Edge Reconnection
         * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.EdgeReconnectionToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeReconnectionTool()
         * @generated
         */
        EClass EDGE_RECONNECTION_TOOL = eINSTANCE.getEdgeReconnectionTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.LabelEditToolImpl
         * <em>Label Edit Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.LabelEditToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLabelEditTool()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.impl.NodeToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeTool()
         * @generated
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
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.SourceEdgeEndReconnectionToolImpl <em>Source Edge End
         * Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.SourceEdgeEndReconnectionToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getSourceEdgeEndReconnectionTool()
         * @generated
         */
        EClass SOURCE_EDGE_END_RECONNECTION_TOOL = eINSTANCE.getSourceEdgeEndReconnectionTool();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.impl.TargetEdgeEndReconnectionToolImpl <em>Target Edge End
         * Reconnection Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.TargetEdgeEndReconnectionToolImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getTargetEdgeEndReconnectionTool()
         * @generated
         */
        EClass TARGET_EDGE_END_RECONNECTION_TOOL = eINSTANCE.getTargetEdgeEndReconnectionTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl
         * <em>Create View</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.impl.CreateViewImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getCreateView()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.impl.DeleteViewImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDeleteView()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.impl.SelectionDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getSelectionDescription()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.impl.ToolSectionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getToolSection()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramToolSectionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getDiagramToolSection()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.impl.NodeToolSectionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeToolSection()
         * @generated
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
         * @see org.eclipse.sirius.components.view.diagram.impl.EdgeToolSectionImpl
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getEdgeToolSection()
         * @generated
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
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.ArrowStyle <em>Arrow
         * Style</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.ArrowStyle
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getArrowStyle()
         * @generated
         */
        EEnum ARROW_STYLE = eINSTANCE.getArrowStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.LayoutDirection <em>Layout
         * Direction</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.LayoutDirection
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLayoutDirection()
         * @generated
         */
        EEnum LAYOUT_DIRECTION = eINSTANCE.getLayoutDirection();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.LineStyle <em>Line
         * Style</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.LineStyle
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getLineStyle()
         * @generated
         */
        EEnum LINE_STYLE = eINSTANCE.getLineStyle();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.NodeContainmentKind
         * <em>Node Containment Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.NodeContainmentKind
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getNodeContainmentKind()
         * @generated
         */
        EEnum NODE_CONTAINMENT_KIND = eINSTANCE.getNodeContainmentKind();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
         * <em>Synchronization Policy</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.SynchronizationPolicy
         * @see org.eclipse.sirius.components.view.diagram.impl.DiagramPackageImpl#getSynchronizationPolicy()
         * @generated
         */
        EEnum SYNCHRONIZATION_POLICY = eINSTANCE.getSynchronizationPolicy();

    }

} // DiagramPackage
