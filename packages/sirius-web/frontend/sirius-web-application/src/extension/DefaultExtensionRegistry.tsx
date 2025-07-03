/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
  diagramDialogContributionExtensionPoint,
  diagramNodeActionOverrideContributionExtensionPoint,
  diagramPanelActionExtensionPoint,
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
  GQLOmniboxCommand,
  OmniboxButton,
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
  treeItemContextMenuEntryExtensionPoint,
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
import FileCopyIcon from '@mui/icons-material/FileCopy';
import Filter from '@mui/icons-material/Filter';
import FolderIcon from '@mui/icons-material/Folder';
import ImageIcon from '@mui/icons-material/Image';
import LinkIcon from '@mui/icons-material/Link';
import MenuIcon from '@mui/icons-material/Menu';
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import TableViewIcon from '@mui/icons-material/TableView';
import WarningIcon from '@mui/icons-material/Warning';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { Navigate, PathRouteProps, Link as RouterLink, useMatch } from 'react-router-dom';
import { DiagramFilter } from '../diagrams/DiagramFilter';
import { SiriusWebManageVisibilityNodeAction } from '../diagrams/nodeaction/SiriusWebManageVisibilityNodeAction';
import { ApolloLinkUndoRedoStack } from '../graphql/ApolloLinkUndoRedoStack';
import { ApolloClientOptionsConfigurer } from '../graphql/useCreateApolloClient.types';
import { apolloClientOptionsConfigurersExtensionPoint } from '../graphql/useCreateApolloClientExtensionPoints';
import { PublishStudioLibraryCommand } from '../libraries/PublishStudioLibraryCommand';
import { NavigationBarRightContributionProps } from '../navigationBar/NavigationBar.types';
import { navigationBarRightContributionExtensionPoint } from '../navigationBar/NavigationBarExtensionPoints';
import { NavigationBarMenuItemProps } from '../navigationBar/NavigationBarMenu.types';
import { navigationBarMenuEntryExtensionPoint } from '../navigationBar/NavigationBarMenuExtensionPoints';
import { ImportLibraryCommand } from '../omnibox/ImportLibraryCommand';
import { OnboardArea } from '../onboarding/OnboardArea';
import { routerExtensionPoint } from '../router/RouterExtensionPoints';
import { CheckboxCell } from '../table/CheckboxCell';
import { DisplayLibraryView } from '../views/display-library/DisplayLibraryView';
import { DownloadProjectMenuEntryContribution } from '../views/edit-project/EditProjectNavbar/DownloadProjectMenuEntryContribution';
import { editProjectNavbarMenuEntryExtensionPoint } from '../views/edit-project/EditProjectNavbar/EditProjectNavbarMenuExtensionPoints';
import { EditProjectView } from '../views/edit-project/EditProjectView';
import { DetailsView } from '../views/edit-project/workbench-views/details/DetailsView';
import { DiagramTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/DiagramTreeItemContextMenuContribution';
import { DocumentTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/DocumentTreeItemContextMenuContribution';
import { ExpandAllTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/ExpandAllTreeItemContextMenuContribution';
import { ObjectTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/ObjectTreeItemContextMenuContribution';
import { RepresentationTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/RepresentationTreeItemContextMenuContribution';
import { UpdateLibraryTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/UpdateLibraryTreeItemContextMenuContribution';
import { ExplorerView } from '../views/edit-project/workbench-views/explorer/ExplorerView';
import { QueryView } from '../views/edit-project/workbench-views/query/QueryView';
import { RelatedElementsView } from '../views/edit-project/workbench-views/related-elements/RelatedElementsView';
import { RepresentationsView } from '../views/edit-project/workbench-views/representations/RepresentationsView';
import { LibraryBrowserView } from '../views/library-browser/LibraryBrowserView';
import { NewProjectView } from '../views/new-project/NewProjectView';
import { createProjectAreaCardExtensionPoint } from '../views/project-browser/create-projects-area/CreateProjectAreaExtensionPoints';
import { NewProjectCard } from '../views/project-browser/create-projects-area/NewProjectCard';
import { ShowAllProjectTemplatesCard } from '../views/project-browser/create-projects-area/ShowAllProjectTemplatesCard';
import { UploadProjectCard } from '../views/project-browser/create-projects-area/UploadProjectCard';
import { projectContextMenuEntryExtensionPoint } from '../views/project-browser/list-projects-area/ProjectContextMenuExtensionPoints';
import { ProjectDownloadMenuItemExtension } from '../views/project-browser/list-projects-area/ProjectDownloadMenuItemExtension';
import { ProjectBrowserView } from '../views/project-browser/ProjectBrowserView';
import { ProjectImagesSettings } from '../views/project-settings/images/ProjectImagesSettings';
import { ProjectSettingsView } from '../views/project-settings/ProjectSettingsView';
import { ProjectSettingTabContribution } from '../views/project-settings/ProjectSettingsView.types';
import { projectSettingsTabExtensionPoint } from '../views/project-settings/ProjectSettingsViewExtensionPoints';
import { UploadProjectView } from '../views/upload-project/UploadProjectView';
import { checkboxCellDocumentTransform } from './CheckboxCelllDocumentTransform';
import { ellipseNodeStyleDocumentTransform } from './EllipseNodeDocumentTransform';
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
    side: 'left',
    title: 'Explorer',
    icon: <AccountTreeIcon />,
    component: ExplorerView,
  },
  {
    id: 'validation',
    side: 'left',
    title: 'Validation',
    icon: <WarningIcon />,
    component: ValidationView,
  },
  {
    id: 'details',
    side: 'right',
    title: 'Details',
    icon: <MenuIcon />,
    component: DetailsView,
  },
  {
    id: 'query',
    side: 'right',
    title: 'Query',
    icon: <PlayArrowIcon />,
    component: QueryView,
  },
  {
    id: 'representations',
    side: 'right',
    title: 'Representations',
    icon: <Filter />,
    component: RepresentationsView,
  },
  {
    id: 'related-elements',
    side: 'right',
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
  const match = useMatch('/projects/:projectId/edit/:representationId?/*');
  if (match) {
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
 * NavigationBar menu contributions
 *
 * Used to register actions in the navigation bar menu
 *
 *******************************************************************************/

export const ProjectsButtonContribution = ({}: NavigationBarMenuItemProps) => {
  return (
    <MenuItem component={RouterLink} to="/projects">
      <ListItemIcon>
        <FolderIcon />
      </ListItemIcon>
      <ListItemText primary="Projects" />
    </MenuItem>
  );
};

defaultExtensionRegistry.addComponent(navigationBarMenuEntryExtensionPoint, {
  identifier: `siriusweb_${navigationBarMenuEntryExtensionPoint.identifier}_projects`,
  Component: ProjectsButtonContribution,
});

export const LibrariesButtonContribution = ({}: NavigationBarMenuItemProps) => {
  return (
    <MenuItem component={RouterLink} to="/libraries">
      <ListItemIcon>
        <FileCopyIcon />
      </ListItemIcon>
      <ListItemText primary="Libraries" />
    </MenuItem>
  );
};

defaultExtensionRegistry.addComponent(navigationBarMenuEntryExtensionPoint, {
  identifier: `siriusweb_${navigationBarMenuEntryExtensionPoint.identifier}_libraries`,
  Component: LibrariesButtonContribution,
});

/*******************************************************************************
 *
 * Create project area cards
 *
 * Used to register all the type of cards in the create project area
 *
 *******************************************************************************/

defaultExtensionRegistry.addComponent(createProjectAreaCardExtensionPoint, {
  identifier: `siriusweb_${createProjectAreaCardExtensionPoint.identifier}_newProjectCard`,
  Component: NewProjectCard,
});
defaultExtensionRegistry.addComponent(createProjectAreaCardExtensionPoint, {
  identifier: `siriusweb_${createProjectAreaCardExtensionPoint.identifier}_uploadProjectCard`,
  Component: UploadProjectCard,
});
defaultExtensionRegistry.addComponent(createProjectAreaCardExtensionPoint, {
  identifier: `siriusweb_${createProjectAreaCardExtensionPoint.identifier}_showAllProjectTemplatesCard`,
  Component: ShowAllProjectTemplatesCard,
});

/*******************************************************************************
 *
 * Diagram panel
 *
 * Used to register new components in the diagram panel
 *
 *******************************************************************************/

defaultExtensionRegistry.addComponent(diagramPanelActionExtensionPoint, {
  identifier: `siriusweb_${diagramPanelActionExtensionPoint.identifier}_filter`,
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
 * Tree item context menu
 *
 * Used to register new components in the tree item context menu
 *
 *******************************************************************************/
defaultExtensionRegistry.addComponent(treeItemContextMenuEntryExtensionPoint, {
  identifier: `siriusweb_${treeItemContextMenuEntryExtensionPoint.identifier}_document`,
  Component: DocumentTreeItemContextMenuContribution,
});
defaultExtensionRegistry.addComponent(treeItemContextMenuEntryExtensionPoint, {
  identifier: `siriusweb_${treeItemContextMenuEntryExtensionPoint.identifier}_object`,
  Component: ObjectTreeItemContextMenuContribution,
});
defaultExtensionRegistry.addComponent(treeItemContextMenuEntryExtensionPoint, {
  identifier: `siriusweb_${treeItemContextMenuEntryExtensionPoint.identifier}_diagram`,
  Component: DiagramTreeItemContextMenuContribution,
});
defaultExtensionRegistry.addComponent(treeItemContextMenuEntryExtensionPoint, {
  identifier: `siriusweb_${treeItemContextMenuEntryExtensionPoint.identifier}_representation`,
  Component: RepresentationTreeItemContextMenuContribution,
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
      return entry.id.includes('updateLibrary');
    },
    component: UpdateLibraryTreeItemContextMenuContribution,
  },
  {
    canHandle: (entry: GQLTreeItemContextMenuEntry) => {
      return entry.id.includes('expandAll');
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
 * Edit project navbar context menu items
 *
 * Used to register menu items available on project's context menu in the edit project view
 *
 *******************************************************************************/
defaultExtensionRegistry.addComponent(editProjectNavbarMenuEntryExtensionPoint, {
  identifier: `siriusWeb_${editProjectNavbarMenuEntryExtensionPoint.identifier}_download`,
  Component: DownloadProjectMenuEntryContribution,
});

/*******************************************************************************
 *
 * Project action buttons
 *
 * Used to register menu items available on projects in the project browser viewx
 *
 *******************************************************************************/
defaultExtensionRegistry.addComponent(projectContextMenuEntryExtensionPoint, {
  identifier: `siriusWeb_${projectContextMenuEntryExtensionPoint.identifier}_download`,
  Component: ProjectDownloadMenuItemExtension,
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
    canHandle: (action: GQLOmniboxCommand) => {
      return action.id === 'publishStudio';
    },
    component: PublishStudioLibraryCommand,
  },
  {
    canHandle: (action: GQLOmniboxCommand) => {
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
    element: <NewProjectView />,
  },
  {
    path: '/upload/project/*',
    element: <UploadProjectView />,
  },
  {
    path: '/projects',
    element: <ProjectBrowserView />,
  },
  {
    path: '/projects/:projectId/edit/:representationId?/*',
    element: <EditProjectView />,
  },
  {
    path: '/projects/:projectId/settings/:tabId?',
    element: <ProjectSettingsView />,
  },
  {
    path: '/libraries',
    element: <LibraryBrowserView />,
  },
  {
    path: '/libraries/:namespace/:name/:version',
    element: <DisplayLibraryView />,
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

export { defaultExtensionRegistry };
