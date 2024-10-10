/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { PortalRepresentation } from '@eclipse-sirius/sirius-components-portals';
import { SelectionDialog } from '@eclipse-sirius/sirius-components-selection';
import { TreeRepresentation, treeItemContextMenuEntryExtensionPoint } from '@eclipse-sirius/sirius-components-trees';
import { ValidationView } from '@eclipse-sirius/sirius-components-validation';
import {
  GQLReferenceWidget,
  ReferenceIcon,
  ReferencePreview,
  ReferencePropertySection,
} from '@eclipse-sirius/sirius-components-widget-reference';
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import Filter from '@mui/icons-material/Filter';
import ImageIcon from '@mui/icons-material/Image';
import LinkIcon from '@mui/icons-material/Link';
import MenuIcon from '@mui/icons-material/Menu';
import WarningIcon from '@mui/icons-material/Warning';
import { DiagramFilter } from '../diagrams/DiagramFilter';
import { OperationCountLink } from '../graphql/ApolloLinkMutationsStack';
import { ApolloClientOptionsConfigurer } from '../graphql/useCreateApolloClient.types';
import { apolloClientOptionsConfigurersExtensionPoint } from '../graphql/useCreateApolloClientExtensionPoints';
import { OnboardArea } from '../onboarding/OnboardArea';
import { DiagramTreeItemContextMenuContribution } from '../views/edit-project/DiagramTreeItemContextMenuContribution';
import { DocumentTreeItemContextMenuContribution } from '../views/edit-project/DocumentTreeItemContextMenuContribution';
import { ObjectTreeItemContextMenuContribution } from '../views/edit-project/ObjectTreeItemContextMenuContribution';
import { DetailsView } from '../views/edit-project/workbench-views/DetailsView';
import { RelatedElementsView } from '../views/edit-project/workbench-views/RelatedElementsView';
import { RepresentationsView } from '../views/edit-project/workbench-views/RepresentationsView';
import { ExplorerView } from '../views/explorer/ExplorerView';
import { createProjectAreaCardExtensionPoint } from '../views/project-browser/create-projects-area/CreateProjectAreaExtensionPoints';
import { NewProjectCard } from '../views/project-browser/create-projects-area/NewProjectCard';
import { ShowAllProjectTemplatesCard } from '../views/project-browser/create-projects-area/ShowAllProjectTemplatesCard';
import { UploadProjectCard } from '../views/project-browser/create-projects-area/UploadProjectCard';
import { ProjectSettingTabContribution } from '../views/project-settings/ProjectSettingsView.types';
import { projectSettingsTabExtensionPoint } from '../views/project-settings/ProjectSettingsViewExtensionPoints';
import { ProjectImagesSettings } from '../views/project-settings/images/ProjectImagesSettings';
import { ellipseNodeStyleDocumentTransform } from './ElipseNodeDocumentTransform';
import { referenceWidgetDocumentTransform } from './ReferenceWidgetDocumentTransform';

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
];

defaultExtensionRegistry.putData(representationFactoryExtensionPoint, {
  identifier: `siriusweb_${representationFactoryExtensionPoint.identifier}`,
  data: representationFactories,
});

/*******************************************************************************
 *
 * Create project area cards
 *
 * Used to register all the type of cards in the create project area
 *
 *******************************************************************************/

defaultExtensionRegistry.addComponent(createProjectAreaCardExtensionPoint, {
  identifier: `siriusweb_${createProjectAreaCardExtensionPoint}_newProjectCard`,
  Component: NewProjectCard,
});
defaultExtensionRegistry.addComponent(createProjectAreaCardExtensionPoint, {
  identifier: `siriusweb_${createProjectAreaCardExtensionPoint}_uploadProjectCard`,
  Component: UploadProjectCard,
});
defaultExtensionRegistry.addComponent(createProjectAreaCardExtensionPoint, {
  identifier: `siriusweb_${createProjectAreaCardExtensionPoint}_showAllProjectTemplatesCard`,
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

/*******************************************************************************
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
    link: new OperationCountLink().concat(currentOptions.link),
  };
};

const widgetsApolloClientOptionsConfigurer: ApolloClientOptionsConfigurer = (currentOptions) => {
  const { documentTransform } = currentOptions;

  const newDocumentTransform = documentTransform
    ? documentTransform.concat(referenceWidgetDocumentTransform)
    : referenceWidgetDocumentTransform;
  return {
    ...currentOptions,
    documentTransform: newDocumentTransform,
  };
};

defaultExtensionRegistry.putData(apolloClientOptionsConfigurersExtensionPoint, {
  identifier: `siriusWeb_${apolloClientOptionsConfigurersExtensionPoint.identifier}`,
  data: [nodesApolloClientOptionsConfigurer, widgetsApolloClientOptionsConfigurer],
});

/*******************************************************************************
 *
 * Custom widget
 *
 * Used to register new custom widget in form
 *
 *******************************************************************************/

const isReferenceWidget = (widget: GQLWidget): widget is GQLReferenceWidget => widget.__typename === 'ReferenceWidget';

defaultExtensionRegistry.putData(widgetContributionExtensionPoint, {
  identifier: `siriusWeb_${widgetContributionExtensionPoint.identifier}_referenceWidget`,
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

export { defaultExtensionRegistry };
