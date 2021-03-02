/**
 */
package org.eclipse.sirius.web.view;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.sirius.web.view.ViewFactory
 * @model kind="package"
 * @generated
 */
public interface ViewPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "view";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/sirius-web/view";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "view";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ViewPackage eINSTANCE = org.eclipse.sirius.web.view.impl.ViewPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.sirius.web.view.impl.ViewImpl <em>View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirius.web.view.impl.ViewImpl
	 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getView()
	 * @generated
	 */
	int VIEW = 0;

	/**
	 * The feature id for the '<em><b>Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__DESCRIPTIONS = 0;

	/**
	 * The number of structural features of the '<em>View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.sirius.web.view.impl.RepresentationDescriptionImpl <em>Representation Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirius.web.view.impl.RepresentationDescriptionImpl
	 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getRepresentationDescription()
	 * @generated
	 */
	int REPRESENTATION_DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION_DESCRIPTION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION_DESCRIPTION__DOMAIN_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Title Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION = 2;

	/**
	 * The number of structural features of the '<em>Representation Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION_DESCRIPTION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Representation Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION_DESCRIPTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.sirius.web.view.impl.DiagramDescriptionImpl <em>Diagram Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirius.web.view.impl.DiagramDescriptionImpl
	 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getDiagramDescription()
	 * @generated
	 */
	int DIAGRAM_DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_DESCRIPTION__NAME = REPRESENTATION_DESCRIPTION__NAME;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_DESCRIPTION__DOMAIN_TYPE = REPRESENTATION_DESCRIPTION__DOMAIN_TYPE;

	/**
	 * The feature id for the '<em><b>Title Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_DESCRIPTION__TITLE_EXPRESSION = REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Node Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Edge Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Diagram Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_DESCRIPTION_FEATURE_COUNT = REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Diagram Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_DESCRIPTION_OPERATION_COUNT = REPRESENTATION_DESCRIPTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl <em>Diagram Element Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl
	 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getDiagramElementDescription()
	 * @generated
	 */
	int DIAGRAM_ELEMENT_DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = 1;

	/**
	 * The feature id for the '<em><b>Creation Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE = 2;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION = 3;

	/**
	 * The feature id for the '<em><b>Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ELEMENT_DESCRIPTION__STYLE = 4;

	/**
	 * The number of structural features of the '<em>Diagram Element Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Diagram Element Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ELEMENT_DESCRIPTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.sirius.web.view.impl.NodeDescriptionImpl <em>Node Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirius.web.view.impl.NodeDescriptionImpl
	 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getNodeDescription()
	 * @generated
	 */
	int NODE_DESCRIPTION = 4;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DESCRIPTION__DOMAIN_TYPE = DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE;

	/**
	 * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Creation Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DESCRIPTION__CREATION_MODE = DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DESCRIPTION__LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DESCRIPTION__STYLE = DIAGRAM_ELEMENT_DESCRIPTION__STYLE;

	/**
	 * The feature id for the '<em><b>Children Descriptions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Node Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DESCRIPTION_FEATURE_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Node Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DESCRIPTION_OPERATION_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl <em>Edge Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl
	 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getEdgeDescription()
	 * @generated
	 */
	int EDGE_DESCRIPTION = 5;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DESCRIPTION__DOMAIN_TYPE = DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE;

	/**
	 * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Creation Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DESCRIPTION__CREATION_MODE = DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE;

	/**
	 * The feature id for the '<em><b>Label Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DESCRIPTION__LABEL_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DESCRIPTION__STYLE = DIAGRAM_ELEMENT_DESCRIPTION__STYLE;

	/**
	 * The feature id for the '<em><b>Is Domain Based Edge</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source Node Descriptions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target Node Descriptions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Source Nodes Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Target Nodes Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Edge Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DESCRIPTION_FEATURE_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Edge Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DESCRIPTION_OPERATION_COUNT = DIAGRAM_ELEMENT_DESCRIPTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.sirius.web.view.impl.StyleImpl <em>Style</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirius.web.view.impl.StyleImpl
	 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getStyle()
	 * @generated
	 */
	int STYLE = 6;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STYLE__COLOR = 0;

	/**
	 * The number of structural features of the '<em>Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STYLE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STYLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.sirius.web.view.Mode <em>Mode</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirius.web.view.Mode
	 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getMode()
	 * @generated
	 */
	int MODE = 7;

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirius.web.view.View <em>View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View</em>'.
	 * @see org.eclipse.sirius.web.view.View
	 * @generated
	 */
	EClass getView();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.web.view.View#getDescriptions <em>Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Descriptions</em>'.
	 * @see org.eclipse.sirius.web.view.View#getDescriptions()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_Descriptions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirius.web.view.RepresentationDescription <em>Representation Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Representation Description</em>'.
	 * @see org.eclipse.sirius.web.view.RepresentationDescription
	 * @generated
	 */
	EClass getRepresentationDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.view.RepresentationDescription#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.sirius.web.view.RepresentationDescription#getName()
	 * @see #getRepresentationDescription()
	 * @generated
	 */
	EAttribute getRepresentationDescription_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.view.RepresentationDescription#getDomainType <em>Domain Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain Type</em>'.
	 * @see org.eclipse.sirius.web.view.RepresentationDescription#getDomainType()
	 * @see #getRepresentationDescription()
	 * @generated
	 */
	EAttribute getRepresentationDescription_DomainType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.view.RepresentationDescription#getTitleExpression <em>Title Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title Expression</em>'.
	 * @see org.eclipse.sirius.web.view.RepresentationDescription#getTitleExpression()
	 * @see #getRepresentationDescription()
	 * @generated
	 */
	EAttribute getRepresentationDescription_TitleExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirius.web.view.DiagramDescription <em>Diagram Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diagram Description</em>'.
	 * @see org.eclipse.sirius.web.view.DiagramDescription
	 * @generated
	 */
	EClass getDiagramDescription();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.web.view.DiagramDescription#getNodeDescriptions <em>Node Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Node Descriptions</em>'.
	 * @see org.eclipse.sirius.web.view.DiagramDescription#getNodeDescriptions()
	 * @see #getDiagramDescription()
	 * @generated
	 */
	EReference getDiagramDescription_NodeDescriptions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.web.view.DiagramDescription#getEdgeDescriptions <em>Edge Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edge Descriptions</em>'.
	 * @see org.eclipse.sirius.web.view.DiagramDescription#getEdgeDescriptions()
	 * @see #getDiagramDescription()
	 * @generated
	 */
	EReference getDiagramDescription_EdgeDescriptions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirius.web.view.DiagramElementDescription <em>Diagram Element Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diagram Element Description</em>'.
	 * @see org.eclipse.sirius.web.view.DiagramElementDescription
	 * @generated
	 */
	EClass getDiagramElementDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.view.DiagramElementDescription#getDomainType <em>Domain Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain Type</em>'.
	 * @see org.eclipse.sirius.web.view.DiagramElementDescription#getDomainType()
	 * @see #getDiagramElementDescription()
	 * @generated
	 */
	EAttribute getDiagramElementDescription_DomainType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.view.DiagramElementDescription#getSemanticCandidatesExpression <em>Semantic Candidates Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
	 * @see org.eclipse.sirius.web.view.DiagramElementDescription#getSemanticCandidatesExpression()
	 * @see #getDiagramElementDescription()
	 * @generated
	 */
	EAttribute getDiagramElementDescription_SemanticCandidatesExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.view.DiagramElementDescription#getCreationMode <em>Creation Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Mode</em>'.
	 * @see org.eclipse.sirius.web.view.DiagramElementDescription#getCreationMode()
	 * @see #getDiagramElementDescription()
	 * @generated
	 */
	EAttribute getDiagramElementDescription_CreationMode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.view.DiagramElementDescription#getLabelExpression <em>Label Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label Expression</em>'.
	 * @see org.eclipse.sirius.web.view.DiagramElementDescription#getLabelExpression()
	 * @see #getDiagramElementDescription()
	 * @generated
	 */
	EAttribute getDiagramElementDescription_LabelExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.sirius.web.view.DiagramElementDescription#getStyle <em>Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Style</em>'.
	 * @see org.eclipse.sirius.web.view.DiagramElementDescription#getStyle()
	 * @see #getDiagramElementDescription()
	 * @generated
	 */
	EReference getDiagramElementDescription_Style();

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirius.web.view.NodeDescription <em>Node Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Description</em>'.
	 * @see org.eclipse.sirius.web.view.NodeDescription
	 * @generated
	 */
	EClass getNodeDescription();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirius.web.view.NodeDescription#getChildrenDescriptions <em>Children Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children Descriptions</em>'.
	 * @see org.eclipse.sirius.web.view.NodeDescription#getChildrenDescriptions()
	 * @see #getNodeDescription()
	 * @generated
	 */
	EReference getNodeDescription_ChildrenDescriptions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirius.web.view.EdgeDescription <em>Edge Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edge Description</em>'.
	 * @see org.eclipse.sirius.web.view.EdgeDescription
	 * @generated
	 */
	EClass getEdgeDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.view.EdgeDescription#isIsDomainBasedEdge <em>Is Domain Based Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Domain Based Edge</em>'.
	 * @see org.eclipse.sirius.web.view.EdgeDescription#isIsDomainBasedEdge()
	 * @see #getEdgeDescription()
	 * @generated
	 */
	EAttribute getEdgeDescription_IsDomainBasedEdge();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.web.view.EdgeDescription#getSourceNodeDescriptions <em>Source Node Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Source Node Descriptions</em>'.
	 * @see org.eclipse.sirius.web.view.EdgeDescription#getSourceNodeDescriptions()
	 * @see #getEdgeDescription()
	 * @generated
	 */
	EReference getEdgeDescription_SourceNodeDescriptions();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.sirius.web.view.EdgeDescription#getTargetNodeDescriptions <em>Target Node Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Target Node Descriptions</em>'.
	 * @see org.eclipse.sirius.web.view.EdgeDescription#getTargetNodeDescriptions()
	 * @see #getEdgeDescription()
	 * @generated
	 */
	EReference getEdgeDescription_TargetNodeDescriptions();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.view.EdgeDescription#getSourceNodesExpression <em>Source Nodes Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Nodes Expression</em>'.
	 * @see org.eclipse.sirius.web.view.EdgeDescription#getSourceNodesExpression()
	 * @see #getEdgeDescription()
	 * @generated
	 */
	EAttribute getEdgeDescription_SourceNodesExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.view.EdgeDescription#getTargetNodesExpression <em>Target Nodes Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Nodes Expression</em>'.
	 * @see org.eclipse.sirius.web.view.EdgeDescription#getTargetNodesExpression()
	 * @see #getEdgeDescription()
	 * @generated
	 */
	EAttribute getEdgeDescription_TargetNodesExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirius.web.view.Style <em>Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Style</em>'.
	 * @see org.eclipse.sirius.web.view.Style
	 * @generated
	 */
	EClass getStyle();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.sirius.web.view.Style#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see org.eclipse.sirius.web.view.Style#getColor()
	 * @see #getStyle()
	 * @generated
	 */
	EAttribute getStyle_Color();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.sirius.web.view.Mode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Mode</em>'.
	 * @see org.eclipse.sirius.web.view.Mode
	 * @generated
	 */
	EEnum getMode();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ViewFactory getViewFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.sirius.web.view.impl.ViewImpl <em>View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirius.web.view.impl.ViewImpl
		 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getView()
		 * @generated
		 */
		EClass VIEW = eINSTANCE.getView();

		/**
		 * The meta object literal for the '<em><b>Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW__DESCRIPTIONS = eINSTANCE.getView_Descriptions();

		/**
		 * The meta object literal for the '{@link org.eclipse.sirius.web.view.impl.RepresentationDescriptionImpl <em>Representation Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirius.web.view.impl.RepresentationDescriptionImpl
		 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getRepresentationDescription()
		 * @generated
		 */
		EClass REPRESENTATION_DESCRIPTION = eINSTANCE.getRepresentationDescription();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPRESENTATION_DESCRIPTION__NAME = eINSTANCE.getRepresentationDescription_Name();

		/**
		 * The meta object literal for the '<em><b>Domain Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPRESENTATION_DESCRIPTION__DOMAIN_TYPE = eINSTANCE.getRepresentationDescription_DomainType();

		/**
		 * The meta object literal for the '<em><b>Title Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION = eINSTANCE
				.getRepresentationDescription_TitleExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.sirius.web.view.impl.DiagramDescriptionImpl <em>Diagram Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirius.web.view.impl.DiagramDescriptionImpl
		 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getDiagramDescription()
		 * @generated
		 */
		EClass DIAGRAM_DESCRIPTION = eINSTANCE.getDiagramDescription();

		/**
		 * The meta object literal for the '<em><b>Node Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIAGRAM_DESCRIPTION__NODE_DESCRIPTIONS = eINSTANCE.getDiagramDescription_NodeDescriptions();

		/**
		 * The meta object literal for the '<em><b>Edge Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIAGRAM_DESCRIPTION__EDGE_DESCRIPTIONS = eINSTANCE.getDiagramDescription_EdgeDescriptions();

		/**
		 * The meta object literal for the '{@link org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl <em>Diagram Element Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirius.web.view.impl.DiagramElementDescriptionImpl
		 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getDiagramElementDescription()
		 * @generated
		 */
		EClass DIAGRAM_ELEMENT_DESCRIPTION = eINSTANCE.getDiagramElementDescription();

		/**
		 * The meta object literal for the '<em><b>Domain Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE = eINSTANCE.getDiagramElementDescription_DomainType();

		/**
		 * The meta object literal for the '<em><b>Semantic Candidates Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = eINSTANCE
				.getDiagramElementDescription_SemanticCandidatesExpression();

		/**
		 * The meta object literal for the '<em><b>Creation Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM_ELEMENT_DESCRIPTION__CREATION_MODE = eINSTANCE.getDiagramElementDescription_CreationMode();

		/**
		 * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE
				.getDiagramElementDescription_LabelExpression();

		/**
		 * The meta object literal for the '<em><b>Style</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIAGRAM_ELEMENT_DESCRIPTION__STYLE = eINSTANCE.getDiagramElementDescription_Style();

		/**
		 * The meta object literal for the '{@link org.eclipse.sirius.web.view.impl.NodeDescriptionImpl <em>Node Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirius.web.view.impl.NodeDescriptionImpl
		 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getNodeDescription()
		 * @generated
		 */
		EClass NODE_DESCRIPTION = eINSTANCE.getNodeDescription();

		/**
		 * The meta object literal for the '<em><b>Children Descriptions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS = eINSTANCE.getNodeDescription_ChildrenDescriptions();

		/**
		 * The meta object literal for the '{@link org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl <em>Edge Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl
		 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getEdgeDescription()
		 * @generated
		 */
		EClass EDGE_DESCRIPTION = eINSTANCE.getEdgeDescription();

		/**
		 * The meta object literal for the '<em><b>Is Domain Based Edge</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE = eINSTANCE.getEdgeDescription_IsDomainBasedEdge();

		/**
		 * The meta object literal for the '<em><b>Source Node Descriptions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS = eINSTANCE.getEdgeDescription_SourceNodeDescriptions();

		/**
		 * The meta object literal for the '<em><b>Target Node Descriptions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS = eINSTANCE.getEdgeDescription_TargetNodeDescriptions();

		/**
		 * The meta object literal for the '<em><b>Source Nodes Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION = eINSTANCE.getEdgeDescription_SourceNodesExpression();

		/**
		 * The meta object literal for the '<em><b>Target Nodes Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION = eINSTANCE.getEdgeDescription_TargetNodesExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.sirius.web.view.impl.StyleImpl <em>Style</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirius.web.view.impl.StyleImpl
		 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getStyle()
		 * @generated
		 */
		EClass STYLE = eINSTANCE.getStyle();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STYLE__COLOR = eINSTANCE.getStyle_Color();

		/**
		 * The meta object literal for the '{@link org.eclipse.sirius.web.view.Mode <em>Mode</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirius.web.view.Mode
		 * @see org.eclipse.sirius.web.view.impl.ViewPackageImpl#getMode()
		 * @generated
		 */
		EEnum MODE = eINSTANCE.getMode();

	}

} //ViewPackage
