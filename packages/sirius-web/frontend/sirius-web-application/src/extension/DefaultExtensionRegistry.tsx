/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import {
  ExtensionRegistry,
  RepresentationComponentFactory,
  RepresentationMetadata,
  WorkbenchViewContribution,
  representationFactoryExtensionPoint,
  workbenchMainAreaExtensionPoint,
  workbenchViewContributionExtensionPoint,
} from '@eclipse-sirius/sirius-components-core';
import { DeckRepresentation } from '@eclipse-sirius/sirius-components-deck';
import {
  ActionProps,
  DiagramDialogContribution,
  DiagramNodeActionOverrideContribution,
  DiagramRepresentation,
  EdgeAppearanceSection,
  EdgeData,
  ImageNodeAppearanceSection,
  NodeData,
  PaletteAppearanceSectionContributionProps,
  RectangularNodeAppearanceSection,
  diagramDialogContributionExtensionPoint,
  diagramNodeActionOverrideContributionExtensionPoint,
  diagramToolbarActionExtensionPoint,
  paletteAppearanceSectionExtensionPoint,
} from '@eclipse-sirius/sirius-components-diagrams';
import { FormDescriptionEditorRepresentation } from '@eclipse-sirius/sirius-components-formdescriptioneditors';
import {
  FormRepresentation,
  GQLWidget,
  PropertySectionComponent,
  widgetContributionExtensionPoint,
} from '@eclipse-sirius/sirius-components-forms';
import { GanttRepresentation } from '@eclipse-sirius/sirius-components-gantt';
import {
  OmniboxButton,
  OmniboxCommand,
  OmniboxCommandOverrideContribution,
  omniboxCommandOverrideContributionExtensionPoint,
} from '@eclipse-sirius/sirius-components-omnibox';
import { PortalRepresentation } from '@eclipse-sirius/sirius-components-portals';
import { SelectionDialog } from '@eclipse-sirius/sirius-components-selection';
import {
  GQLCell,
  TableCellContribution,
  TableRepresentation,
  tableCellExtensionPoint,
} from '@eclipse-sirius/sirius-components-tables';
import {
  GQLTreeItemContextMenuEntry,
  TreeItemContextMenuOverrideContribution,
  TreeRepresentation,
  treeItemContextMenuEntryOverrideExtensionPoint,
} from '@eclipse-sirius/sirius-components-trees';
import { ValidationView } from '@eclipse-sirius/sirius-components-validation';
import {
  GQLReferenceWidget,
  ReferenceIcon,
  ReferencePreview,
  ReferencePropertySection,
} from '@eclipse-sirius/sirius-components-widget-reference';
import {
  GQLTableWidget,
  TableWidgetPreview,
  TableWidgetPropertySection,
} from '@eclipse-sirius/sirius-components-widget-table';
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import Filter from '@mui/icons-material/Filter';
import ImageIcon from '@mui/icons-material/Image';
import LinkIcon from '@mui/icons-material/Link';
import MenuIcon from '@mui/icons-material/Menu';
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import SearchIcon from '@mui/icons-material/Search';
import SettingsIcon from '@mui/icons-material/Settings';
import TableViewIcon from '@mui/icons-material/TableView';
import WarningIcon from '@mui/icons-material/Warning';
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { Navigate, PathRouteProps, matchRoutes, useLocation } from 'react-router-dom';
import { DiagramFilter } from '../diagrams/DiagramFilter';
import { SiriusWebManageVisibilityNodeAction } from '../diagrams/nodeaction/SiriusWebManageVisibilityNodeAction';
import { ApolloLinkUndoRedoStack } from '../graphql/ApolloLinkUndoRedoStack';
import { ApolloClientOptionsConfigurer } from '../graphql/useCreateApolloClient.types';
import { apolloClientOptionsConfigurersExtensionPoint } from '../graphql/useCreateApolloClientExtensionPoints';
import { PublishStudioLibraryCommand } from '../libraries/PublishStudioLibraryCommand';
import { NavigationBarRightContributionProps } from '../navigationBar/NavigationBar.types';
import { navigationBarRightContributionExtensionPoint } from '../navigationBar/NavigationBarExtensionPoints';
import { ImportLibraryCommand } from '../omnibox/ImportLibraryCommand';
import { OnboardArea } from '../onboarding/OnboardArea';
import { routerExtensionPoint } from '../router/RouterExtensionPoints';
import { CheckboxCell } from '../table/CheckboxCell';
import { ViewerContextProvider } from '../viewer/ViewerContext';
import { DisplayLibraryView } from '../views/display-library/DisplayLibraryView';
import { EditProjectView } from '../views/edit-project/EditProjectView';
import { DetailsView } from '../views/edit-project/workbench-views/details/DetailsView';
import { DownloadDocumentTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/download-document/DownloadDocumentTreeItemContextMenuContribution';
import { DuplicateObjectTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/duplicate-object/DuplicateObjectTreeItemContextMenuContribution';
import { DuplicateRepresentationTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/duplicate-representation/DuplicateRepresentationTreeItemContextMenuContribution';
import { ExpandAllTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/expand-all/ExpandAllTreeItemContextMenuContribution';
import { NewObjectTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/new-object/NewObjectTreeItemContextMenuContribution';
import { NewRepresentationTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/new-representation/NewRepresentationTreeItemContextMenuContribution';
import { NewRootObjectTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/new-root-object/NewRootObjectTreeItemContextMenuContribution';
import { UpdateLibraryTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/update-library/UpdateLibraryTreeItemContextMenuContribution';
import { ExplorerView } from '../views/edit-project/workbench-views/explorer/ExplorerView';
import { QueryView } from '../views/edit-project/workbench-views/query/QueryView';
import { RelatedElementsView } from '../views/edit-project/workbench-views/related-elements/RelatedElementsView';
import { RelatedViewsView } from '../views/edit-project/workbench-views/related-views/RelatedViewsView';
import { SearchView } from '../views/edit-project/workbench-views/search/SearchView';
import { LibraryBrowserView } from '../views/library-browser/LibraryBrowserView';
import { NewProjectView } from '../views/new-project/NewProjectView';
import { ProjectBrowserView } from '../views/project-browser/ProjectBrowserView';
import { ProjectImagesSettings } from '../views/project-settings/images/ProjectImagesSettings';
import { ProjectGeneralSettingsView } from '../views/project-settings/ProjectGeneralSettingsView';
import { ProjectSettingsView } from '../views/project-settings/ProjectSettingsView';
import { ProjectSettingTabContribution } from '../views/project-settings/ProjectSettingsView.types';
import { projectSettingsTabExtensionPoint } from '../views/project-settings/ProjectSettingsViewExtensionPoints';
import { UploadProjectView } from '../views/upload-project/UploadProjectView';
import { checkboxCellDocumentTransform } from './CheckboxCelllDocumentTransform';
import { ellipseNodeStyleDocumentTransform } from './ellipsenode/EllipseNodeDocumentTransform';
import { EllipseNodeAppearanceSection } from './ellipsenode/EllipseNodePaletteAppearanceSection';
import { referenceWidgetDocumentTransform } from './ReferenceWidgetDocumentTransform';
import { tableWidgetDocumentTransform } from './TableWidgetDocumentTransform';

const getType = (representation: RepresentationMetadata): string | null => {
  const query = representation.kind.substring(representation.kind.indexOf('?') + 1, representation.kind.length);
  const params = new URLSearchParams(query);
  const type = params.get('type');
  return type;
};

const defaultExtensionRegistry = new ExtensionRegistry();

/*******************************************************************************
 *
 * Workbench main area
 *
 * Used to register the component used by default when no representation is opened
 *
 *******************************************************************************/
defaultExtensionRegistry.addComponent(workbenchMainAreaExtensionPoint, {
  identifier: `siriusweb_${workbenchMainAreaExtensionPoint.identifier}`,
  Component: OnboardArea,
});

/*******************************************************************************
 *
 * Workbench views
 *
 * Used to register all the views available in the left and right of the workbench
 *
 *******************************************************************************/
const workbenchViewContributions: WorkbenchViewContribution[] = [
  {
    id: 'explorer',
    title: 'Explorer',
    icon: <AccountTreeIcon />,
    component: ExplorerView,
  },
  {
    id: 'validation',
    title: 'Validation',
    icon: <WarningIcon />,
    component: ValidationView,
  },
  {
    id: 'search',
    title: 'Search',
    icon: <SearchIcon />,
    component: SearchView,
  },
  {
    id: 'details',
    title: 'Details',
    icon: <MenuIcon />,
    component: DetailsView,
  },
  {
    id: 'query',
    title: 'Query',
    icon: <PlayArrowIcon />,
    component: QueryView,
  },
  {
    id: 'related-views',
    title: 'Related Views',
    icon: <Filter />,
    component: RelatedViewsView,
  },
  {
    id: 'related-elements',
    title: 'Related Elements',
    icon: <LinkIcon />,
    component: RelatedElementsView,
  },
];
defaultExtensionRegistry.putData(workbenchViewContributionExtensionPoint, {
  identifier: `siriusweb_${workbenchViewContributionExtensionPoint.identifier}`,
  data: workbenchViewContributions,
});

/*******************************************************************************
 *
 * Workbench representation factories
 *
 * Used to register all the type of representations that are supported in Sirius Web
 *
 *******************************************************************************/

const representationFactories: RepresentationComponentFactory[] = [
  (representationMetadata) => (getType(representationMetadata) === 'Diagram' ? DiagramRepresentation : null),
  (representationMetadata) => (getType(representationMetadata) === 'Form' ? FormRepresentation : null),
  (representationMetadata) =>
    getType(representationMetadata) === 'FormDescriptionEditor' ? FormDescriptionEditorRepresentation : null,
  (representationMetadata) => (getType(representationMetadata) === 'Gantt' ? GanttRepresentation : null),
  (representationMetadata) => (getType(representationMetadata) === 'Deck' ? DeckRepresentation : null),
  (representationMetadata) => (getType(representationMetadata) === 'Portal' ? PortalRepresentation : null),
  (representationMetadata) => (getType(representationMetadata) === 'Tree' ? TreeRepresentation : null),
  (representationMetadata) => (getType(representationMetadata) === 'Table' ? TableRepresentation : null),
];

defaultExtensionRegistry.putData(representationFactoryExtensionPoint, {
  identifier: `siriusweb_${representationFactoryExtensionPoint.identifier}`,
  data: representationFactories,
});

/*******************************************************************************
 *
 * NavigationBar contributions
 *
 * Used to register actions in the navigation bar
 *
 *******************************************************************************/

export const OmniboxButtonContribution = ({}: NavigationBarRightContributionProps) => {
  const location = useLocation();
  const routes = [{ path: '/projects/:projectId/edit/:representationId?/*' }, { path: '/projects' }];
  const matches = matchRoutes(routes, location);
  if (matches) {
    return <OmniboxButton size="small" />;
  }
  return null;
};

defaultExtensionRegistry.addComponent(navigationBarRightContributionExtensionPoint, {
  identifier: `siriusweb_${navigationBarRightContributionExtensionPoint.identifier}_omnibox`,
  Component: OmniboxButtonContribution,
});

/*******************************************************************************
 *
 * Diagram toolbar
 *
 * Used to register new components in the diagram toolbar
 *
 *******************************************************************************/

defaultExtensionRegistry.addComponent(diagramToolbarActionExtensionPoint, {
  identifier: `siriusweb_${diagramToolbarActionExtensionPoint.identifier}_filter`,
  Component: DiagramFilter,
});

/*******************************************************************************
 *
 * Diagram dialog
 *
 * Used to register new components in the diagram dialog
 *
 *******************************************************************************/
const diagramDialogContributions: DiagramDialogContribution[] = [
  {
    canHandle: (dialogDescriptionId: string) => {
      return dialogDescriptionId.startsWith('siriusComponents://selectionDialogDescription');
    },
    component: SelectionDialog,
  },
];

defaultExtensionRegistry.putData<DiagramDialogContribution[]>(diagramDialogContributionExtensionPoint, {
  identifier: `siriusweb_${diagramDialogContributionExtensionPoint.identifier}`,
  data: diagramDialogContributions,
});

/*******************************************************************************
 *
 * Tree item context menu overrides
 *
 * Used to register components in the tree item context menu that override
 * the default rendering of context menu items.
 *
 *******************************************************************************/
const treeItemContextMenuOverrideContributions: TreeItemContextMenuOverrideContribution[] = [
  {
    canHandle: (entry: GQLTreeItemContextMenuEntry) => {
      return entry.id.includes('new-root-object');
    },
    component: NewRootObjectTreeItemContextMenuContribution,
  },
  {
    canHandle: (entry: GQLTreeItemContextMenuEntry) => {
      return entry.id.includes('download-document');
    },
    component: DownloadDocumentTreeItemContextMenuContribution,
  },
  {
    canHandle: (entry: GQLTreeItemContextMenuEntry) => {
      return entry.id.includes('new-object');
    },
    component: NewObjectTreeItemContextMenuContribution,
  },
  {
    canHandle: (entry: GQLTreeItemContextMenuEntry) => {
      return entry.id.includes('new-representation');
    },
    component: NewRepresentationTreeItemContextMenuContribution,
  },
  {
    canHandle: (entry: GQLTreeItemContextMenuEntry) => {
      return entry.id.includes('duplicate-object');
    },
    component: DuplicateObjectTreeItemContextMenuContribution,
  },
  {
    canHandle: (entry: GQLTreeItemContextMenuEntry) => {
      return entry.id.includes('duplicate-representation');
    },
    component: DuplicateRepresentationTreeItemContextMenuContribution,
  },
  {
    canHandle: (entry: GQLTreeItemContextMenuEntry) => {
      return entry.id.includes('update-library');
    },
    component: UpdateLibraryTreeItemContextMenuContribution,
  },
  {
    canHandle: (entry: GQLTreeItemContextMenuEntry) => {
      return entry.id.includes('expand-all');
    },
    component: ExpandAllTreeItemContextMenuContribution,
  },
];

defaultExtensionRegistry.putData<TreeItemContextMenuOverrideContribution[]>(
  treeItemContextMenuEntryOverrideExtensionPoint,
  {
    identifier: `siriusweb_${treeItemContextMenuEntryOverrideExtensionPoint.identifier}`,
    data: treeItemContextMenuOverrideContributions,
  }
);

/*******************************************************************************
 *
 * Table custom cell contributions
 *
 * Used to register new cell in the tables
 *
 *******************************************************************************/
const tableCellContributions: TableCellContribution[] = [
  {
    canHandle: (cell: GQLCell) => {
      return cell.__typename === 'CheckboxCell';
    },
    component: CheckboxCell,
  },
];

defaultExtensionRegistry.putData<TableCellContribution[]>(tableCellExtensionPoint, {
  identifier: `siriusweb_${tableCellExtensionPoint.identifier}`,
  data: tableCellContributions,
});

/*******************************************************************************
 *
 * Apollo client options configurer
 *
 * Used to register new options configurer in the apollo client
 *
 *******************************************************************************/

const nodesApolloClientOptionsConfigurer: ApolloClientOptionsConfigurer = (currentOptions) => {
  const { documentTransform } = currentOptions;

  const newDocumentTransform = documentTransform
    ? documentTransform.concat(ellipseNodeStyleDocumentTransform)
    : ellipseNodeStyleDocumentTransform;
  return {
    ...currentOptions,
    documentTransform: newDocumentTransform,
  };
};

const widgetsApolloClientOptionsConfigurer: ApolloClientOptionsConfigurer = (currentOptions) => {
  const { documentTransform } = currentOptions;

  const newDocumentTransform = documentTransform
    ? documentTransform.concat(referenceWidgetDocumentTransform).concat(tableWidgetDocumentTransform)
    : referenceWidgetDocumentTransform.concat(tableWidgetDocumentTransform);
  return {
    ...currentOptions,
    documentTransform: newDocumentTransform,
  };
};

const tableCustomCellClientOptionsConfigurer: ApolloClientOptionsConfigurer = (currentOptions) => {
  const { documentTransform } = currentOptions;

  const newDocumentTransform = documentTransform
    ? documentTransform.concat(checkboxCellDocumentTransform)
    : checkboxCellDocumentTransform;
  return {
    ...currentOptions,
    documentTransform: newDocumentTransform,
  };
};

const undoRedoApolloClientOptionsConfigurer: ApolloClientOptionsConfigurer = (currentOptions) => {
  return {
    ...currentOptions,
    link: new ApolloLinkUndoRedoStack().concat(currentOptions.link),
  };
};

defaultExtensionRegistry.putData(apolloClientOptionsConfigurersExtensionPoint, {
  identifier: `siriusWeb_${apolloClientOptionsConfigurersExtensionPoint.identifier}`,
  data: [
    nodesApolloClientOptionsConfigurer,
    widgetsApolloClientOptionsConfigurer,
    tableCustomCellClientOptionsConfigurer,
    undoRedoApolloClientOptionsConfigurer,
  ],
});

/*******************************************************************************
 *
 * Custom widget
 *
 * Used to register new custom widget in form
 *
 *******************************************************************************/

const isReferenceWidget = (widget: GQLWidget): widget is GQLReferenceWidget => widget.__typename === 'ReferenceWidget';
const isTableWidget = (widget: GQLWidget): widget is GQLTableWidget => widget.__typename === 'TableWidget';

defaultExtensionRegistry.putData(widgetContributionExtensionPoint, {
  identifier: `siriusWeb_${widgetContributionExtensionPoint.identifier}`,
  data: [
    {
      name: 'ReferenceWidget',
      icon: <ReferenceIcon />,
      previewComponent: ReferencePreview,
      component: (widget: GQLWidget): PropertySectionComponent<GQLWidget> | null => {
        let propertySectionComponent: PropertySectionComponent<GQLWidget> | null = null;

        if (isReferenceWidget(widget)) {
          propertySectionComponent = ReferencePropertySection;
        }
        return propertySectionComponent;
      },
    },
    {
      name: 'TableWidget',
      icon: <TableViewIcon />,
      previewComponent: TableWidgetPreview,
      component: (widget: GQLWidget): PropertySectionComponent<GQLWidget> | null => {
        let propertySectionComponent: PropertySectionComponent<GQLWidget> | null = null;

        if (isTableWidget(widget)) {
          propertySectionComponent = TableWidgetPropertySection;
        }
        return propertySectionComponent;
      },
    },
  ],
});

/*******************************************************************************
 *
 * Project settings
 *
 * Used to register the settings pages for the projects
 *
 *******************************************************************************/
const defaultSettingPages: ProjectSettingTabContribution[] = [
  {
    id: 'general',
    title: 'General',
    icon: <SettingsIcon />,
    component: ProjectGeneralSettingsView,
  },
  {
    id: 'images',
    title: 'Images',
    icon: <ImageIcon />,
    component: ProjectImagesSettings,
  },
];

defaultExtensionRegistry.putData(projectSettingsTabExtensionPoint, {
  identifier: `siriusWeb_${projectSettingsTabExtensionPoint.identifier}`,
  data: defaultSettingPages,
});

/*******************************************************************************
 *
 * Omnibox command overrides
 *
 * Used to override the default rendering of omnibox commands
 *
 *******************************************************************************/

const omniboxCommandOverrides: OmniboxCommandOverrideContribution[] = [
  {
    canHandle: (action: OmniboxCommand) => {
      return action.id === 'publishStudio';
    },
    component: PublishStudioLibraryCommand,
  },
  {
    canHandle: (action: OmniboxCommand) => {
      return action.id === 'importLibrary';
    },
    component: ImportLibraryCommand,
  },
];

defaultExtensionRegistry.putData<OmniboxCommandOverrideContribution[]>(
  omniboxCommandOverrideContributionExtensionPoint,
  {
    identifier: `siriusweb_${omniboxCommandOverrideContributionExtensionPoint.identifier}`,
    data: omniboxCommandOverrides,
  }
);

/*******************************************************************************
 *
 * Router contributions
 *
 * Used to register the route of Sirius Web views
 *
 *******************************************************************************/

export const siriusWebRouterContributions: PathRouteProps[] = [
  {
    path: '/new/project/*',
    element: (
      <ViewerContextProvider>
        <NewProjectView />
      </ViewerContextProvider>
    ),
  },
  {
    path: '/upload/project/*',
    element: (
      <ViewerContextProvider>
        <UploadProjectView />
      </ViewerContextProvider>
    ),
  },
  {
    path: '/projects',
    element: (
      <ViewerContextProvider>
        <ProjectBrowserView />
      </ViewerContextProvider>
    ),
  },
  {
    path: '/projects/:projectId/edit/:representationId?/*',
    element: (
      <ViewerContextProvider>
        <EditProjectView />
      </ViewerContextProvider>
    ),
  },
  {
    path: '/projects/:projectId/settings/:tabId?',
    element: (
      <ViewerContextProvider>
        <ProjectSettingsView />
      </ViewerContextProvider>
    ),
  },
  {
    path: '/libraries',
    element: (
      <ViewerContextProvider>
        <LibraryBrowserView />
      </ViewerContextProvider>
    ),
  },
  {
    path: '/libraries/:namespace/:name/:version',
    element: (
      <ViewerContextProvider>
        <DisplayLibraryView />
      </ViewerContextProvider>
    ),
  },
  {
    path: '/',
    element: <Navigate to="/projects" replace />,
  },
];

defaultExtensionRegistry.putData(routerExtensionPoint, {
  identifier: `siriusweb_${routerExtensionPoint.identifier}`,
  data: siriusWebRouterContributions,
});

/*******************************************************************************
 *
 * Diagram node action command overrides
 *
 * Used to override the default rendering of diagram node action manage visibility
 *
 *******************************************************************************/
const diagramNodeActionOverrides: DiagramNodeActionOverrideContribution[] = [
  {
    canHandle: ({ action, diagramElementId }: ActionProps) => {
      return action.id === 'siriusweb_manage_visibility' && !!diagramElementId;
    },
    component: SiriusWebManageVisibilityNodeAction,
  },
];

defaultExtensionRegistry.putData<DiagramNodeActionOverrideContribution[]>(
  diagramNodeActionOverrideContributionExtensionPoint,
  {
    identifier: `siriusweb_${diagramNodeActionOverrideContributionExtensionPoint.identifier}`,
    data: diagramNodeActionOverrides,
  }
);

/*******************************************************************************
 *
 * Palette ellipse node appearance contribution
 *
 * Used to contribute custom node appearance section on the palette for ellipse nodes
 *
 *******************************************************************************/
const diagramElementPaletteAppearanceSectionContribution: PaletteAppearanceSectionContributionProps[] = [
  {
    canHandle: (diagramElementIds) => {
      const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
      return diagramElementIds.every((elementId) => store.getState().nodeLookup.get(elementId)?.type === 'ellipseNode');
    },
    component: EllipseNodeAppearanceSection,
  },
  {
    canHandle: (diagramElementIds) => {
      const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
      const canHandle = diagramElementIds.every(
        (elementId) =>
          store.getState().nodeLookup.get(elementId)?.data.nodeAppearanceData?.gqlStyle.__typename ===
          'RectangularNodeStyle'
      );

      return canHandle;
    },
    component: RectangularNodeAppearanceSection,
  },
  {
    canHandle: (diagramElementIds) => {
      const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
      return diagramElementIds.every(
        (elementId) =>
          store.getState().nodeLookup.get(elementId)?.data.nodeAppearanceData?.gqlStyle.__typename === 'ImageNodeStyle'
      );
    },
    component: ImageNodeAppearanceSection,
  },
  {
    canHandle: (diagramElementIds) => {
      const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
      return diagramElementIds.every((elementId) => !!store.getState().edgeLookup.get(elementId));
    },
    component: EdgeAppearanceSection,
  },
];

defaultExtensionRegistry.putData<PaletteAppearanceSectionContributionProps[]>(paletteAppearanceSectionExtensionPoint, {
  identifier: `siriusweb_${paletteAppearanceSectionExtensionPoint.identifier}`,
  data: diagramElementPaletteAppearanceSectionContribution,
});

export { defaultExtensionRegistry };
