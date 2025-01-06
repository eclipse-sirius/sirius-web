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
  DiagramDialogContribution,
  DiagramRepresentation,
  diagramDialogContributionExtensionPoint,
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
import { TableRepresentation } from '@eclipse-sirius/sirius-components-tables';
import { TreeRepresentation, treeItemContextMenuEntryExtensionPoint } from '@eclipse-sirius/sirius-components-trees';
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
import { Link as RouterLink, useMatch } from 'react-router-dom';
import { DiagramFilter } from '../diagrams/DiagramFilter';
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
import { DownloadProjectMenuEntryContribution } from '../views/edit-project/EditProjectNavbar/DownloadProjectMenuEntryContribution';
import { editProjectNavbarMenuEntryExtensionPoint } from '../views/edit-project/EditProjectNavbar/EditProjectNavbarMenuExtensionPoints';
import { DetailsView } from '../views/edit-project/workbench-views/details/DetailsView';
import { DiagramTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/DiagramTreeItemContextMenuContribution';
import { DocumentTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/DocumentTreeItemContextMenuContribution';
import { ObjectTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/ObjectTreeItemContextMenuContribution';
import { RepresentationTreeItemContextMenuContribution } from '../views/edit-project/workbench-views/explorer/context-menu-contributions/RepresentationTreeItemContextMenuContribution';
import { ExplorerView } from '../views/edit-project/workbench-views/explorer/ExplorerView';
import { QueryView } from '../views/edit-project/workbench-views/query/QueryView';
import { RelatedElementsView } from '../views/edit-project/workbench-views/related-elements/RelatedElementsView';
import { RepresentationsView } from '../views/edit-project/workbench-views/representations/RepresentationsView';
import { createProjectAreaCardExtensionPoint } from '../views/project-browser/create-projects-area/CreateProjectAreaExtensionPoints';
import { NewProjectCard } from '../views/project-browser/create-projects-area/NewProjectCard';
import { ShowAllProjectTemplatesCard } from '../views/project-browser/create-projects-area/ShowAllProjectTemplatesCard';
import { UploadProjectCard } from '../views/project-browser/create-projects-area/UploadProjectCard';
import { projectContextMenuEntryExtensionPoint } from '../views/project-browser/list-projects-area/ProjectContextMenuExtensionPoints';
import { ProjectDownloadMenuItemExtension } from '../views/project-browser/list-projects-area/ProjectDownloadMenuItemExtension';
import { ProjectImagesSettings } from '../views/project-settings/images/ProjectImagesSettings';
import { ProjectSettingTabContribution } from '../views/project-settings/ProjectSettingsView.types';
import { projectSettingsTabExtensionPoint } from '../views/project-settings/ProjectSettingsViewExtensionPoints';
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
    side: 'left',
    title: 'Explorer',
    icon: <AccountTreeIcon />,
    component: ExplorerView,
  },
  {
    side: 'left',
    title: 'Validation',
    icon: <WarningIcon />,
    component: ValidationView,
  },
  {
    side: 'right',
    title: 'Details',
    icon: <MenuIcon />,
    component: DetailsView,
  },
  {
    side: 'right',
    title: 'Query',
    icon: <PlayArrowIcon />,
    component: QueryView,
  },
  {
    side: 'right',
    title: 'Representations',
    icon: <Filter />,
    component: RepresentationsView,
  },
  {
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

export { defaultExtensionRegistry };
